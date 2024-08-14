package com.teamD.RevTaskManagement.service;

import com.teamD.RevTaskManagement.dao.TimelineDAO;
import com.teamD.RevTaskManagement.model.Timeline;
import com.teamD.RevTaskManagement.exceptions.TimelineNotFoundException;
import com.teamD.RevTaskManagement.utilities.ModelUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeLineService {

    @Autowired
    private TimelineDAO timelineRepository;

    @Autowired
    private ModelUpdater modelUpdater;

    // Create a new timeline
    public Timeline createTimeline(Timeline timeline) {
        return timelineRepository.save(timeline);
    }

    // Update an existing timeline using ModelUpdater
    public Timeline updateTimeline(Long id, Timeline updatedTimeline) throws TimelineNotFoundException {
        Timeline existingTimeline = timelineRepository.findById(id)
                .orElseThrow(() -> new TimelineNotFoundException("Timeline not found with id: " + id));

        // Use ModelUpdater to update fields
        modelUpdater.updateFields(existingTimeline, updatedTimeline);

        return timelineRepository.save(existingTimeline);
    }

    // Delete a timeline by ID
    public void deleteTimeline(Long id) throws TimelineNotFoundException {
        Timeline timeline = timelineRepository.findById(id)
                .orElseThrow(() -> new TimelineNotFoundException("Timeline not found with id: " + id));
        timelineRepository.delete(timeline);
    }

    // Get a timeline by ID
    public Timeline getTimelineById(Long id) throws TimelineNotFoundException {
        return timelineRepository.findById(id)
                .orElseThrow(() -> new TimelineNotFoundException("Timeline not found with id: " + id));
    }

    // Get all timelines
    public List<Timeline> fetchAllTimelines() {
        return timelineRepository.findAll();
    }
}