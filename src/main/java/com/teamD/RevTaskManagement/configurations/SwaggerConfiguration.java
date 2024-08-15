package com.teamD.RevTaskManagement.configurations;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public GroupedOpenApi publicApiEmployee() {
        return GroupedOpenApi.builder()
                .group("employee")
                .pathsToMatch("/api/employee/**") // Specify the base path for API endpoints
                .build();
    }

    @Bean
    public GroupedOpenApi publicApiProject() {
        return GroupedOpenApi.builder()
                .group("Project")
                .pathsToMatch("/api/projects/**") // Specify the base path for API endpoints
                .build();
    }

    @Bean
    public GroupedOpenApi publicApiTask() {
        return GroupedOpenApi.builder()
                .group("Task")
                .pathsToMatch("/api/tasks/**") // Specify the base path for API endpoints
                .build();
    }

    @Bean
    public GroupedOpenApi publicApiClient() {
        return GroupedOpenApi.builder()
                .group("Client")
                .pathsToMatch("/api/clients/**") // Specify the base path for API endpoints
                .build();
    }


    @Bean
    public GroupedOpenApi publicApiComment() {
        return GroupedOpenApi.builder()
                .group("comment")
                .pathsToMatch("/api/comments/**") // Specify the base path for API endpoints
                .build();
    }

    @Bean
    public GroupedOpenApi publicApiMessage() {
        return GroupedOpenApi.builder()
                .group("message")
                .pathsToMatch("/api/messages/**") // Specify the base path for API endpoints
                .build();
    }

    @Bean
    public GroupedOpenApi publicApiProfile() {
        return GroupedOpenApi.builder()
                .group("profile")
                .pathsToMatch("/api/profile/**") // Specify the base path for API endpoints
                .build();
    }

    @Bean
    public GroupedOpenApi publicApiSkill() {
        return GroupedOpenApi.builder()
                .group("Skill")
                .pathsToMatch("/api/skill/**") // Specify the base path for API endpoints
                .build();
    }

    @Bean
    public GroupedOpenApi publicApiTimeline() {
        return GroupedOpenApi.builder()
                .group("timeline")
                .pathsToMatch("/api/timelines/**") // Specify the base path for API endpoints
                .build();
    }
}
