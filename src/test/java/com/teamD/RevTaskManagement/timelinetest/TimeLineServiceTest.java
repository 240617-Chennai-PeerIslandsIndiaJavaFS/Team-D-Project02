package com.teamD.RevTaskManagement.timelinetest;

import com.teamD.RevTaskManagement.dao.TimelineDAO;
import com.teamD.RevTaskManagement.exceptions.TimelineNotFoundException;
import com.teamD.RevTaskManagement.model.Timeline;
import com.teamD.RevTaskManagement.service.TimeLineService;
import com.teamD.RevTaskManagement.enums.Milestone;
import com.teamD.RevTaskManagement.utilities.ModelUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TimeLineServiceTest {

    @Mock
    private TimelineDAO timelineRepository;

    @Mock
    private ModelUpdater modelUpdater;

    @InjectMocks
    private TimeLineService timelineService;

    private Timeline timeline;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        timeline = new Timeline();
        timeline.setTimelineId(1L);
        timeline.setMilestone(Milestone.IN_QUEUE); // Use an existing enum value
        timeline.setTimestamp(new Date());
        // Set other fields as needed
    }

    @Test
    void testCreateTimeline() {
        // Mocking the timelineRepository.save() method
        when(timelineRepository.save(timeline)).thenReturn(timeline);

        // Call the method to test
        Timeline createdTimeline = timelineService.createTimeline(timeline);

        // Verify the results
        assertNotNull(createdTimeline);
        assertEquals(1L, createdTimeline.getTimelineId());
        assertEquals(Milestone.IN_QUEUE, createdTimeline.getMilestone()); // Use an existing enum value

        // Verify that the repository method was called
        verify(timelineRepository, times(1)).save(timeline);
    }

    @Test
    void testGetTimelineById() {
        // Mocking the timelineRepository.findById() to return an Optional containing the timeline
        when(timelineRepository.findById(1L)).thenReturn(Optional.of(timeline));

        // Call the method to test
        Timeline foundTimeline = timelineService.getTimelineById(1L);

        // Verify the results
        assertNotNull(foundTimeline);
        assertEquals(1L, foundTimeline.getTimelineId());
        assertEquals(Milestone.IN_QUEUE, foundTimeline.getMilestone()); // Use an existing enum value

        // Verify that the repository method was called
        verify(timelineRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTimelineById_TimelineNotFound() {
        // Mocking the timelineRepository.findById() to return an empty Optional
        when(timelineRepository.findById(1L)).thenReturn(Optional.empty());

        // Verify that the exception is thrown
        assertThrows(TimelineNotFoundException.class, () -> timelineService.getTimelineById(1L));

        // Verify that the repository method was called
        verify(timelineRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateTimeline() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        // Mocking the timelineRepository.findById() to return an Optional containing the timeline
        Timeline timeline = new Timeline(); // Initialize as needed
        when(timelineRepository.findById(1L)).thenReturn(Optional.of(timeline));

        // Creating an updatedTimeline with new values
        Timeline updatedTimeline = new Timeline();
        updatedTimeline.setMilestone(Milestone.COMMENCED); // Use an existing enum value
        updatedTimeline.setTimestamp(new Date());

        // Mocking the timelineRepository.save() method to return the updated timeline
        when(timelineRepository.save(any(Timeline.class))).thenAnswer(invocation -> {
            Timeline savedTimeline = invocation.getArgument(0);
            savedTimeline.setTimelineId(1L);
            return savedTimeline;
        });

        // Call the method to test
        Timeline result = timelineService.updateTimeline(1L, updatedTimeline);

        // Verify the results
        assertNotNull(result);
        assertEquals(1L, result.getTimelineId());
        assertEquals(Milestone.COMMENCED, result.getMilestone()); // Use an existing enum value

        // Verify that the repository methods were called
        verify(timelineRepository, times(1)).findById(1L);
        verify(timelineRepository, times(1)).save(result);
    }

    @Test
    void testDeleteTimeline() {
        // Mocking the timelineRepository.findById() to return an Optional containing the timeline
        when(timelineRepository.findById(1L)).thenReturn(Optional.of(timeline));

        // Call the method to test
        timelineService.deleteTimeline(1L);

        // Verify that the repository methods were called
        verify(timelineRepository, times(1)).findById(1L);
        verify(timelineRepository, times(1)).delete(timeline);
    }

    @Test
    void testDeleteTimeline_TimelineNotFound() {
        // Mocking the timelineRepository.findById() to return an empty Optional
        when(timelineRepository.findById(1L)).thenReturn(Optional.empty());

        // Verify that the exception is thrown
        assertThrows(TimelineNotFoundException.class, () -> timelineService.deleteTimeline(1L));

        // Verify that the repository method was called
        verify(timelineRepository, times(1)).findById(1L);
    }

    @Test
    void testFetchAllTimelines() {
        // Creating a list of timelines
        Timeline timeline2 = new Timeline();
        timeline2.setTimelineId(2L);
        timeline2.setMilestone(Milestone.TESTING); // Use an existing enum value

        List<Timeline> timelines = Arrays.asList(timeline, timeline2);

        // Mocking the timelineRepository.findAll() method
        when(timelineRepository.findAll()).thenReturn(timelines);

        // Call the method to test
        List<Timeline> allTimelines = timelineService.fetchAllTimelines();

        // Verify the results
        assertNotNull(allTimelines);
        assertEquals(2, allTimelines.size());

        // Verify that the repository method was called
        verify(timelineRepository, times(1)).findAll();
    }
}
