package com.teamD.RevTaskManagement.controller;

import com.teamD.RevTaskManagement.model.Timeline;
import com.teamD.RevTaskManagement.service.TimeLineService;
import com.teamD.RevTaskManagement.exceptions.TimelineNotFoundException;
import com.teamD.RevTaskManagement.utilities.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timelines")
public class TimelineController {

    @Autowired
    private TimeLineService timeLineService;

    // Create a new timeline
    @PostMapping
    public ResponseEntity<BaseResponse<Timeline>> createTimeline(@RequestBody Timeline timeline) {
        Timeline createdTimeline = timeLineService.createTimeline(timeline);
        BaseResponse<Timeline> response = new BaseResponse<>("Timeline created successfully", createdTimeline, HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Update an existing timeline
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Timeline>> updateTimeline(@PathVariable Long id, @RequestBody Timeline updatedTimeline) {
        try {
            Timeline updated = timeLineService.updateTimeline(id, updatedTimeline);
            BaseResponse<Timeline> response = new BaseResponse<>("Timeline updated successfully", updated, HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (TimelineNotFoundException ex) {
            BaseResponse<Timeline> response = new BaseResponse<>(ex.getMessage(), null, HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Delete a timeline by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> deleteTimeline(@PathVariable Long id) {
        try {
            timeLineService.deleteTimeline(id);
            BaseResponse<String> response = new BaseResponse<>("Timeline deleted successfully", null, HttpStatus.NO_CONTENT.value());
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (TimelineNotFoundException ex) {
            BaseResponse<String> response = new BaseResponse<>(ex.getMessage(), null, HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Get a timeline by ID
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Timeline>> getTimelineById(@PathVariable Long id) {
        try {
            Timeline timeline = timeLineService.getTimelineById(id);
            BaseResponse<Timeline> response = new BaseResponse<>("Timeline retrieved successfully", timeline, HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (TimelineNotFoundException ex) {
            BaseResponse<Timeline> response = new BaseResponse<>(ex.getMessage(), null, HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Get all timelines
    @GetMapping
    public ResponseEntity<BaseResponse<List<Timeline>>> getAllTimelines() {
        List<Timeline> timelines = timeLineService.fetchAllTimelines();
        BaseResponse<List<Timeline>> response = new BaseResponse<>("Timelines retrieved successfully", timelines, HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<BaseResponse<List<Timeline>>> getAllTimelinesByTaskId(@PathVariable Long taskId) {
        List<Timeline> timelines = timeLineService.getAllTimelinesByTaskId(taskId);
        BaseResponse<List<Timeline>> response = new BaseResponse<>("Timelines fetched successfully", timelines, HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
