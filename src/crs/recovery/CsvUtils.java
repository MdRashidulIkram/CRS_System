// Author: Sharjeel_TP084956
// File: CsvUtils.java
// Feature: Course Recovery Plan

package crs.recovery;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CsvUtils {

    private CsvUtils() {
        // Private constructor to prevent instantiation
    }

    public static List<String[]> readAllLines(Path filePath) {
        if (!Files.exists(filePath)) {
            System.err.println("Error: Input file not found at " + filePath);
            return Collections.emptyList();
        }
        try (Stream<String> lines = Files.lines(filePath)) {
            return lines
                    .skip(1) // Skip header row
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .map(line -> Arrays.stream(line.split(","))
                            .map(String::trim)
                            .toArray(String[]::new))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + filePath + " - " + e.getMessage());
            throw new RuntimeException("Failed to read CSV file: " + filePath, e);
        }
    }
}
