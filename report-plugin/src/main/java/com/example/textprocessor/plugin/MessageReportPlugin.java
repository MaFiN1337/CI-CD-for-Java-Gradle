package com.example.textprocessor.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class MessageReportPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getTasks().register("messageReport", MessageReportTask.class, task -> {
            task.setGroup("reporting");
            task.setDescription("Generates a report on the structure of the text-message-processor project");
        });

        project.getTasks().register("validateProject", ValidateProjectTask.class, task -> {
            task.setGroup("verification");
            task.setDescription("Validates the structure of the text-message-processor project");
        });

        project.getExtensions().create("messageProcessorReport", MessageReportExtension.class);
    }
}