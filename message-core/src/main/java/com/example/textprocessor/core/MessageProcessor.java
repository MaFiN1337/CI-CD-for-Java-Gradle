package com.example.textprocessor.core;

import com.example.textprocessor.utils.MessageValidator;
import java.util.ArrayList;
import java.util.List;

public class MessageProcessor {

    private final List<String> processedMessages = new ArrayList<>();
    private final List<String> rejectedMessages = new ArrayList<>();

    public ProcessingResult processMessage(String message) {
        if (!MessageValidator.isValid(message)) {
            rejectedMessages.add(message);
            return new ProcessingResult(false, "Message failed basic validation", message);
        }

        if (!MessageValidator.containsNoForbiddenWords(message)) {
            rejectedMessages.add(message);
            return new ProcessingResult(false, "Message contains forbidden words", message);
        }

        String processedMessage = cleanMessage(message);
        processedMessages.add(processedMessage);

        return new ProcessingResult(true, "Message successfully processed", processedMessage);
    }

    private String cleanMessage(String message) {
        return message.trim()
                .replaceAll("\\s+", " ") // replace multiple spaces with a single space
                .replaceAll("[^\\w\\s\\p{L}\\p{M}.,!?-]", ""); // keep only letters, digits, and basic punctuation
    }

    public ProcessingStats getStats() {
        return new ProcessingStats(
                processedMessages.size(),
                rejectedMessages.size(),
                calculateAverageLength()
        );
    }

    private double calculateAverageLength() {
        if (processedMessages.isEmpty()) return 0.0;

        return processedMessages.stream()
                .mapToInt(MessageValidator::getEffectiveLength)
                .average()
                .orElse(0.0);
    }

    public List<String> getProcessedMessages() {
        return new ArrayList<>(processedMessages);
    }

    public List<String> getRejectedMessages() {
        return new ArrayList<>(rejectedMessages);
    }


    public static class ProcessingResult {
        private final boolean success;
        private final String message;
        private final String processedText;

        public ProcessingResult(boolean success, String message, String processedText) {
            this.success = success;
            this.message = message;
            this.processedText = processedText;
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public String getProcessedText() { return processedText; }

        @Override
        public String toString() {
            return String.format("ProcessingResult{success=%s, message='%s', text='%s'}",
                    success, message, processedText);
        }
    }

    public static class ProcessingStats {
        private final int processedCount;
        private final int rejectedCount;
        private final double averageLength;

        public ProcessingStats(int processedCount, int rejectedCount, double averageLength) {
            this.processedCount = processedCount;
            this.rejectedCount = rejectedCount;
            this.averageLength = averageLength;
        }

        public int getProcessedCount() { return processedCount; }
        public int getRejectedCount() { return rejectedCount; }
        public double getAverageLength() { return averageLength; }

        @Override
        public String toString() {
            return String.format("Statistics: Processed: %d, Rejected: %d, Average length: %.1f",
                    processedCount, rejectedCount, averageLength);
        }
    }
}