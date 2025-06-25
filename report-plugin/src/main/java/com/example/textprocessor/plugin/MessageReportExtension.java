package com.example.textprocessor.plugin;

public class MessageReportExtension {

    private boolean includeStatistics = true;
    private boolean includeFileStructure = true;
    private String reportFormat = "txt";
    private String outputDirectory = "build/reports";

    public boolean isIncludeStatistics() {
        return includeStatistics;
    }

    public void setIncludeStatistics(boolean includeStatistics) {
        this.includeStatistics = includeStatistics;
    }

    public boolean isIncludeFileStructure() {
        return includeFileStructure;
    }

    public void setIncludeFileStructure(boolean includeFileStructure) {
        this.includeFileStructure = includeFileStructure;
    }

    public String getReportFormat() {
        return reportFormat;
    }

    public void setReportFormat(String reportFormat) {
        this.reportFormat = reportFormat;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }
}