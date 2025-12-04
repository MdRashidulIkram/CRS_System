// Author: Sharjeel_TP084956
// File: EnvironmentValidator.java
// Feature: Environment & Dependency Checks

package crs.recovery.env;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * EnvironmentValidator
 *
 * Performs startup checks for required folders and CSV resources used by the
 * Course Recovery Plan module. If folders are missing they will be created and
 * if required CSVs are missing placeholder files with appropriate headers will
 * be created. Implemented by Sharjeel_TP084956.
 *
 * Usage: call EnvironmentValidator.runAllChecks() before running recovery logic.
 */
public final class EnvironmentValidator {

    private EnvironmentValidator() {
        // utility
    }

    public static void runAllChecks() {
        // Define expected paths
        Path resourcesFolder = Paths.get("src", "resources");
        Path dataOutputFolder = Paths.get("data", "output");

        Path studentCsv = resourcesFolder.resolve("student_information.csv");
        Path courseCsv = resourcesFolder.resolve("course_assessment_information.csv");
        Path gradesCsv = resourcesFolder.resolve("grades.csv");

        List<Path> foundFiles = new ArrayList<>();
        List<Path> createdFolders = new ArrayList<>();
        List<Path> createdFiles = new ArrayList<>();

        try {
            // Ensure resource folder exists
            if (AutoInstaller.createFolderIfMissing(resourcesFolder)) {
                createdFolders.add(resourcesFolder);
            }

            // Ensure output folder exists
            if (AutoInstaller.createFolderIfMissing(dataOutputFolder)) {
                createdFolders.add(dataOutputFolder);
            }

            // Create CSVs if missing with headers
            if (!studentCsv.toFile().exists()) {
                AutoInstaller.createCsvIfMissing(studentCsv, "StudentID,FirstName,LastName,Major,Year,Email");
                createdFiles.add(studentCsv);
            } else {
                foundFiles.add(studentCsv);
            }

            if (!courseCsv.toFile().exists()) {
                AutoInstaller.createCsvIfMissing(courseCsv, "CourseID,CourseName,Credits,Semester,Instructor,ExamWeight,AssignmentWeight");
                createdFiles.add(courseCsv);
            } else {
                foundFiles.add(courseCsv);
            }

            if (!gradesCsv.toFile().exists()) {
                AutoInstaller.createCsvIfMissing(gradesCsv, "StudentID,CourseID,Grade");
                createdFiles.add(gradesCsv);
            } else {
                foundFiles.add(gradesCsv);
            }

        } catch (IOException e) {
            System.err.println("Environment check failed: " + e.getMessage());
        }

        // Report
        DependencyReporter.reportFoundFiles(foundFiles);
        DependencyReporter.reportCreatedFolders(createdFolders);
        DependencyReporter.reportCreatedFiles(createdFiles);

        System.out.println("Environment validation complete.");
    }
}
