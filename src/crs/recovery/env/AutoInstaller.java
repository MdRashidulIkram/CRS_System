// Author: Sharjeel_TP084956
// File: AutoInstaller.java
// Feature: Environment & Dependency Checks

package crs.recovery.env;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

/**
 * AutoInstaller
 *
 * Utility class responsible for creating folders and placeholder CSV files
 * when they are missing. Implemented by Sharjeel_TP084956.
 *
 * This class is used by EnvironmentValidator during startup checks.
 */
public final class AutoInstaller {

    private AutoInstaller() {
        // utility
    }

    /**
     * Create the given folder if it does not exist.
     *
     * @param p Path to the folder
     * @return true if the folder was created, false if it already existed
     * @throws IOException on filesystem errors
     */
    public static boolean createFolderIfMissing(Path p) throws IOException {
        Objects.requireNonNull(p);
        if (Files.exists(p)) {
            return false;
        }
        Files.createDirectories(p);
        return true;
    }

    /**
     * Create a CSV file with the provided header line if the file is missing.
     * If the file exists, nothing is changed.
     *
     * @param p Path to the CSV file
     * @param headerLine The header line to write (without newline)
     * @return true if the file was created, false if it already existed
     * @throws IOException on filesystem errors
     */
    public static boolean createCsvIfMissing(Path p, String headerLine) throws IOException {
        Objects.requireNonNull(p);
        Objects.requireNonNull(headerLine);
        if (Files.exists(p)) {
            return false;
        }
        // Ensure parent directories exist
        if (p.getParent() != null) {
            Files.createDirectories(p.getParent());
        }
        Files.write(p, (headerLine + System.lineSeparator()).getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE_NEW);
        return true;
    }
}
