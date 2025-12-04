// Author: Sharjeel_TP084956
// File: DependencyReporter.java
// Feature: Environment & Dependency Checks

package crs.recovery.env;

import java.nio.file.Path;
import java.util.List;

/**
 * DependencyReporter
 *
 * Prints a human-readable report of which resources were present and which
 * were created by the environment validator. Implemented by Sharjeel_TP084956.
 */
public final class DependencyReporter {

    private DependencyReporter() {
        // utility
    }

    public static void reportFoundFiles(List<Path> foundFiles) {
        System.out.println("Found files:");
        for (Path p : foundFiles) {
            System.out.println("  - " + p.toAbsolutePath());
        }
    }

    public static void reportCreatedFolders(List<Path> createdFolders) {
        if (createdFolders.isEmpty()) return;
        System.out.println("Created folders:");
        for (Path p : createdFolders) {
            System.out.println("  - " + p.toAbsolutePath());
        }
    }

    public static void reportCreatedFiles(List<Path> createdFiles) {
        if (createdFiles.isEmpty()) return;
        System.out.println("Created placeholder files:");
        for (Path p : createdFiles) {
            System.out.println("  - " + p.toAbsolutePath());
        }
    }
}
