package com.example.textprocessor.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.Project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageReportTask extends DefaultTask {

    @TaskAction
    public void generateReport() {
        Project project = getProject();

        System.out.println("Generating report for project: " + project.getName());

        StringBuilder report = new StringBuilder();
        report.append("=== TEXT MESSAGE PROCESSOR - PROJECT REPORT ===\n");
        report.append("Generated: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))).append("\n\n");

        report.append("GENERAL INFORMATION:\n");
        report.append("Project name: ").append(project.getName()).append("\n");
        report.append("Version: ").append(project.getVersion()).append("\n");
        report.append("Group: ").append(project.getGroup()).append("\n");
        report.append("Path: ").append(project.getProjectDir().getAbsolutePath()).append("\n\n");

        report.append("MODULES:\n");
        if (project.getSubprojects().isEmpty()) {
            report.append("  This project has no submodules\n");
        } else {
            project.getSubprojects().forEach(subproject -> {
                report.append("  - ").append(subproject.getName());

                File buildFile = subproject.getBuildFile();
                if (buildFile.exists()) {
                    report.append(" Success");
                } else {
                    report.append(" Failure (no build.gradle)");
                }
                report.append("\n");
            });
        }
        report.append("\n");

        report.append("FILE STRUCTURE:\n");
        analyzeProjectStructure(project.getProjectDir(), report, 0);

        report.append("\n🔧 GRADLE TASKS:\n");
        project.getTasks().forEach(task -> {
            if (task.getGroup() != null) {
                report.append("  [").append(task.getGroup()).append("] ")
                        .append(task.getName()).append(" - ")
                        .append(task.getDescription() != null ? task.getDescription() : "no description")
                        .append("\n");
            }
        });

        saveReport(project, report.toString());

        System.out.println("Report generated successfully!");
    }

    private void analyzeProjectStructure(File dir, StringBuilder report, int depth) {
        if (depth > 3) return;

        String indent = "  ".repeat(depth);

        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().startsWith(".")) continue;

                if (file.isDirectory()) {
                    if (isImportantDirectory(file.getName())) {
                        report.append(indent).append("Directory ").append(file.getName()).append("/\n");
                        analyzeProjectStructure(file, report, depth + 1);
                    }
                } else if (isImportantFile(file.getName())) {
                    report.append(indent).append("File ").append(file.getName()).append("\n");
                }
            }
        }
    }

    private boolean isImportantDirectory(String name) {
        return name.equals("src") || name.equals("main") || name.equals("java") ||
                name.equals("resources") || name.startsWith("message-") || name.equals("report-plugin");
    }

    private boolean isImportantFile(String name) {
        return name.endsWith(".java") || name.endsWith(".gradle") ||
                name.equals("settings.gradle") || name.equals("README.md");
    }

    private void saveReport(Project project, String reportContent) {
        try {
            File reportsDir = new File(project.getBuildDir(), "reports");
            reportsDir.mkdirs();

            File reportFile = new File(reportsDir, "message-processor-report.txt");

            try (FileWriter writer = new FileWriter(reportFile)) {
                writer.write(reportContent);
            }

            System.out.println("Report saved: " + reportFile.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error saving report: " + e.getMessage());
        }
    }
}