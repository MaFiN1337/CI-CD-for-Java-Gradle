package com.example.textprocessor.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.Project;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ValidateProjectTask extends DefaultTask {

    private static final List<String> EXPECTED_MODULES = Arrays.asList(
            "message-app", "message-core", "message-utils"
    );

    @TaskAction
    public void validateProject() {
        Project project = getProject();

        boolean isValid = true;

        isValid &= checkMainFiles(project);

        isValid &= checkModules(project);

        isValid &= checkJavaFiles(project);

        if (isValid) {
            System.out.println("Success Project structure is valid!");
        } else {
            System.err.println("Failure Problems found in project structure!");
            throw new RuntimeException("Project validation failed");
        }
    }

    private boolean checkMainFiles(Project project) {
        System.out.println("\nChecking main files:");

        boolean valid = true;

        File settingsFile = new File(project.getRootDir(), "settings.gradle");
        if (settingsFile.exists()) {
            System.out.println("  Success settings.gradle");
        } else {
            System.out.println("  Failure settings.gradle not found");
            valid = false;
        }

        if (project.getBuildFile().exists()) {
            System.out.println("  Success build.gradle");
        } else {
            System.out.println("  Failure build.gradle not found");
            valid = false;
        }

        return valid;
    }

    private boolean checkModules(Project project) {
        System.out.println("\nChecking modules:");

        boolean valid = true;

        for (String expectedModule : EXPECTED_MODULES) {
            Project subproject = project.findProject(":" + expectedModule);
            if (subproject != null) {
                System.out.println("  Success " + expectedModule);

                if (subproject.getBuildFile().exists()) {
                    System.out.println("    Success build.gradle");
                } else {
                    System.out.println("    Failure build.gradle not found");
                    valid = false;
                }
            } else {
                System.out.println("  Failure " + expectedModule + " not found");
                valid = false;
            }
        }

        return valid;
    }

    private boolean checkJavaFiles(Project project) {
        System.out.println("\nChecking Java files:");

        boolean valid = true;

        valid &= checkJavaFile(project, "message-utils", "MessageValidator.java");
        valid &= checkJavaFile(project, "message-core", "MessageProcessor.java");
        valid &= checkJavaFile(project, "message-app", "MessageApp.java");

        return valid;
    }

    private boolean checkJavaFile(Project project, String moduleName, String fileName) {
        Project subproject = project.findProject(":" + moduleName);
        if (subproject == null) {
            return false;
        }

        File javaFile = findJavaFile(subproject.getProjectDir(), fileName);
        if (javaFile != null && javaFile.exists()) {
            System.out.println("  Success " + moduleName + "/" + fileName);
            return true;
        } else {
            System.out.println("  Failure " + moduleName + "/" + fileName + " not found");
            return false;
        }
    }

    private File findJavaFile(File dir, String fileName) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    File found = findJavaFile(file, fileName);
                    if (found != null) return found;
                } else if (file.getName().equals(fileName)) {
                    return file;
                }
            }
        }
        return null;
    }
}