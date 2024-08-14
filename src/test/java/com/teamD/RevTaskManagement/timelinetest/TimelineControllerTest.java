package com.teamD.RevTaskManagement.timelinetest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamD.RevTaskManagement.controller.TimelineController;
import com.teamD.RevTaskManagement.model.Timeline;
import com.teamD.RevTaskManagement.service.TimeLineService;
import com.teamD.RevTaskManagement.exceptions.TimelineNotFoundException;
import com.teamD.RevTaskManagement.enums.Milestone;
import com.teamD.RevTaskManagement.utilities.BaseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TimelineControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TimeLineService timeLineService;

    @InjectMocks
    private TimelineController timelineController;

    private Timeline timeline;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(timelineController).build();

        timeline = new Timeline();
        timeline.setTimelineId(1L);
        timeline.setMilestone(Milestone.IN_QUEUE);
        timeline.setTimestamp(new Date());
    }

    @Test
    void testCreateTimeline() throws Exception {
        Timeline createdTimeline = new Timeline();
        createdTimeline.setTimelineId(1L);
        createdTimeline.setMilestone(Milestone.IN_QUEUE);
        createdTimeline.setTimestamp(new Date());

        BaseResponse<Timeline> response = new BaseResponse<>("Timeline created successfully", createdTimeline, 201);

        when(timeLineService.createTimeline(any(Timeline.class))).thenReturn(createdTimeline);

        mockMvc.perform(post("/api/timelines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(timeline)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.messages").value("Timeline created successfully"))
                .andExpect(jsonPath("$.data.timelineId").value(1))
                .andExpect(jsonPath("$.data.milestone").value("IN_QUEUE"))
                .andExpect(jsonPath("$.status").value(201));
    }

    @Test
    void testUpdateTimeline() throws Exception {
        Timeline updatedTimeline = new Timeline();
        updatedTimeline.setTimelineId(1L);
        updatedTimeline.setMilestone(Milestone.COMMENCED);
        updatedTimeline.setTimestamp(new Date());

        BaseResponse<Timeline> response = new BaseResponse<>("Timeline updated successfully", updatedTimeline, 200);

        when(timeLineService.updateTimeline(eq(1L), any(Timeline.class))).thenReturn(updatedTimeline);

        mockMvc.perform(put("/api/timelines/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedTimeline)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.messages").value("Timeline updated successfully"))
                .andExpect(jsonPath("$.data.milestone").value("COMMENCED"))
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void testUpdateTimeline_TimelineNotFound() throws Exception {
        when(timeLineService.updateTimeline(eq(1L), any(Timeline.class)))
                .thenThrow(new TimelineNotFoundException("Timeline not found with id: 1"));

        BaseResponse<String> response = new BaseResponse<>("Timeline not found with id: 1", null, 404);

        mockMvc.perform(put("/api/timelines/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new Timeline())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.messages").value("Timeline not found with id: 1"))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void testDeleteTimeline() throws Exception {
        doNothing().when(timeLineService).deleteTimeline(1L);

        // Perform the DELETE request
        mockMvc.perform(delete("/api/timelines/1"))
                .andExpect(status().isNoContent());  // Expect 204 No Content
    }


    @Test
    void testDeleteTimeline_TimelineNotFound() throws Exception {
        doThrow(new TimelineNotFoundException("Timeline not found with id: 1")).when(timeLineService).deleteTimeline(1L);

        // Perform the DELETE request
        mockMvc.perform(delete("/api/timelines/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.messages").value("Timeline not found with id: 1"))
                .andExpect(jsonPath("$.status").value(404));
    }


    @Test
    void testGetTimelineById() throws Exception {
        BaseResponse<Timeline> response = new BaseResponse<>("Timeline retrieved successfully", timeline, 200);

        when(timeLineService.getTimelineById(1L)).thenReturn(timeline);

        mockMvc.perform(get("/api/timelines/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.messages").value("Timeline retrieved successfully"))
                .andExpect(jsonPath("$.data.timelineId").value(1))
                .andExpect(jsonPath("$.data.milestone").value("IN_QUEUE"))
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void testGetTimelineById_TimelineNotFound() throws Exception {
        when(timeLineService.getTimelineById(1L))
                .thenThrow(new TimelineNotFoundException("Timeline not found with id: 1"));

        BaseResponse<String> response = new BaseResponse<>("Timeline not found with id: 1", null, 404);

        mockMvc.perform(get("/api/timelines/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.messages").value("Timeline not found with id: 1"))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void testGetAllTimelines() throws Exception {
        Timeline timeline2 = new Timeline();
        timeline2.setTimelineId(2L);
        timeline2.setMilestone(Milestone.TESTING);

        List<Timeline> timelines = Arrays.asList(timeline, timeline2);

        BaseResponse<List<Timeline>> response = new BaseResponse<>("Timelines retrieved successfully", timelines, 200);

        when(timeLineService.fetchAllTimelines()).thenReturn(timelines);

        mockMvc.perform(get("/api/timelines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.messages").value("Timelines retrieved successfully"))
                .andExpect(jsonPath("$.data[0].timelineId").value(1))
                .andExpect(jsonPath("$.data[1].timelineId").value(2))
                .andExpect(jsonPath("$.status").value(200));
    }
}