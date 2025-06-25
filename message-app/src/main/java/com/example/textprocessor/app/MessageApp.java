package com.example.textprocessor.app;

import com.example.textprocessor.core.MessageProcessor;
import com.example.textprocessor.core.MessageProcessor.ProcessingResult;
import com.example.textprocessor.core.MessageProcessor.ProcessingStats;

import java.util.Scanner;

public class MessageApp {

    private static final MessageProcessor processor = new MessageProcessor();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Text Message Processor ===");
        System.out.println("Welcome to message text processor!");
        System.out.println();

        showMenu();

        boolean running = true;
        while (running) {
            System.out.print("Choose option (1-5): ");
            final String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    processNewMessage();
                    break;
                case "2":
                    processBatchMessages();
                    break;
                case "3":
                    showStatistics();
                    break;
                case "4":
                    showAllMessages();
                    break;
                case "5":
                    running = false;
                    System.out.println("Bye!");
                    break;
                default:
                    System.out.println("Wrong option. Try again.");
                    showMenu();
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
                showMenu();
            }
        }

        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n=== MENU ===");
        System.out.println("1. Process new message");
        System.out.println("2. Process multiple messages");
        System.out.println("3. Show statistics");
        System.out.println("4. Show all messages");
        System.out.println("5. Exit");
        System.out.println();
    }

    private static void processNewMessage() {
        System.out.print("Enter message for processing: ");
        final String message = scanner.nextLine();

        final ProcessingResult result = processor.processMessage(message);

        System.out.println("\n--- Processing Result ---");
        if (result.isSuccess()) {
            System.out.println("Success: " + result.getMessage());
            System.out.println("Processed message: \"" + result.getProcessedText() + "\"");
        } else {
            System.out.println("Failure " + result.getMessage());
            System.out.println("Original message: \"" + result.getProcessedText() + "\"");
        }
    }

    private static void processBatchMessages() {
        System.out.println("Enter messages (empty line to finish):");

        int processed = 0;
        String message;

        while (!(message = scanner.nextLine()).isEmpty()) {
            final ProcessingResult result = processor.processMessage(message);
            processed++;

            System.out.printf("%d. %s %s%n",
                    processed,
                    result.isSuccess() ? "Success" : "Failure",
                    result.getMessage()
            );
        }

        System.out.println("\nProcessed " + processed + " messages.");
    }

    private static void showStatistics() {
        final ProcessingStats stats = processor.getStats();

        System.out.println("\n=== STATISTICS ===");
        System.out.println(stats);

        final int total = stats.getProcessedCount() + stats.getRejectedCount();
        if (total > 0) {
            final double successRate = (double) stats.getProcessedCount() / total * 100;
            System.out.printf("Success rate: %.1f%%%n", successRate);
        }
    }

    private static void showAllMessages() {
        System.out.println("\n=== ALL MESSAGES ===");

        System.out.println("\n Processed messages:");
        final var processed = processor.getProcessedMessages();
        if (processed.isEmpty()) {
            System.out.println("  (none)");
        } else {
            for (int i = 0; i < processed.size(); i++) {
                System.out.printf("  %d. %s%n", i + 1, processed.get(i));
            }
        }

        System.out.println("\n Rejected messages:");
        final var rejected = processor.getRejectedMessages();
        if (rejected.isEmpty()) {
            System.out.println("  (none)");
        } else {
            for (int i = 0; i < rejected.size(); i++) {
                System.out.printf("  %d. %s%n", i + 1, rejected.get(i));
            }
        }
    }
}