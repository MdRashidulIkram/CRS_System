// Author: Sharjeel_TP084956
// File: CourseRecoveryPlanGenerator.java
// Feature: Course Recovery Plan

package crs.recovery;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Orchestrates the generation of the Course Recovery Plan report.
 * <p>
 * This class reads student, course, and grade data from CSV files,
 * identifies students who require academic recovery based on configurable
 * rules, and writes a structured recovery plan to a new CSV file.
 * <p>
 * Expected locations:
 * <ul>
 *   <li>Inputs: src/resources/student_information.csv</li>
 *   <li>Inputs: src/resources/course_assessment_information.csv</li>
 *   <li>Inputs: src/resources/grades.csv</li>
 *   <li>Output: data/output/course_recovery_plan.csv</li>
 * </ul>
 *
 * Implemented as part of the Course Recovery Plan feature
 * by Sharjeel_TP084956.
 */
public class CourseRecoveryPlanGenerator {

    private static final int NUMERIC_PASS_THRESHOLD = 50;
    private static final Set<String> FAILING_LETTER_GRADES = Set.of("F", "E");

    public static void main(String[] args) {
        // Run environment checks and auto-create any missing folders/files
        crs.recovery.env.EnvironmentValidator.runAllChecks();
        // Define relative paths for input and output files.
        // Assumes the program is run from the project's root directory.
        Path studentsPath = Paths.get("src", "resources", "student_information.csv");
        Path coursesPath = Paths.get("src", "resources", "course_assessment_information.csv");
        Path gradesPath = Paths.get("src", "resources", "grades.csv");
        Path outputPath = Paths.get("data", "output", "course_recovery_plan.csv");

        System.out.println("Starting Course Recovery Plan generation...");
        System.out.println("Reading student data from: " + studentsPath.toAbsolutePath());
        System.out.println("Reading course data from: " + coursesPath.toAbsolutePath());
        System.out.println("Reading grade data from: " + gradesPath.toAbsolutePath());
        System.out.println("Output will be written to: " + outputPath.toAbsolutePath());

        try {
            CourseRecoveryPlanGenerator generator = new CourseRecoveryPlanGenerator();
            generator.generate(studentsPath, coursesPath, gradesPath, outputPath);
        } catch (Exception e) {
            System.err.println("A critical error occurred during plan generation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void generate(Path studentsPath, Path coursesPath, Path gradesPath, Path outputPath) {
        Map<String, StudentRecord> students = loadStudents(studentsPath);
        Map<String, CourseRecord> courses = loadCourses(coursesPath);
        List<GradeRecord> grades = loadGrades(gradesPath);

        if (students.isEmpty() || courses.isEmpty() || grades.isEmpty()) {
            System.err.println("One or more input files are empty or could not be read. Aborting.");
            return;
        }

        List<CourseRecoveryPlanEntry> plans = generateRecoveryPlans(students, courses, grades);

        try {
            // Ensure the output directory exists
            Files.createDirectories(outputPath.getParent());
            writeRecoveryPlansToCsv(plans, outputPath);
            System.out.println("\nSuccessfully generated " + plans.size()
                    + " recovery plan entries at " + outputPath.toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to write output file.", e);
        }
    }

    private Map<String, StudentRecord> loadStudents(Path studentsPath) {
        return CsvUtils.readAllLines(studentsPath).stream()
                .map(columns -> new StudentRecord(columns[0], columns[1], columns[2], columns[3], columns[4], columns[5]))
                .collect(Collectors.toMap(StudentRecord::studentId, Function.identity()));
    }

    private Map<String, CourseRecord> loadCourses(Path coursesPath) {
        return CsvUtils.readAllLines(coursesPath).stream()
                .map(columns -> new CourseRecord(columns[0], columns[1], Integer.parseInt(columns[2]), columns[3], columns[4], Integer.parseInt(columns[5]), Integer.parseInt(columns[6])))
                .collect(Collectors.toMap(CourseRecord::courseId, Function.identity()));
    }

    private List<GradeRecord> loadGrades(Path gradesPath) {
        return CsvUtils.readAllLines(gradesPath).stream()
                .map(columns -> new GradeRecord(columns[0], columns[1], columns.length > 2 ? columns[2] : ""))
                .collect(Collectors.toList());
    }

    private List<CourseRecoveryPlanEntry> generateRecoveryPlans(Map<String, StudentRecord> students, Map<String, CourseRecord> courses, List<GradeRecord> grades) {
        List<CourseRecoveryPlanEntry> plans = new ArrayList<>();

        for (GradeRecord grade : grades) {
            Optional<String> failureReasonOpt = determineFailureReason(grade.grade());

            if (failureReasonOpt.isPresent()) {
                String failureReason = failureReasonOpt.get();
                StudentRecord student = students.get(grade.studentId());
                CourseRecord course = courses.get(grade.courseId());

                if (student == null || course == null) {
                    System.err.println("Warning: Missing student or course record for grade: " + grade);
                    continue;
                }

                CourseRecoveryPlanEntry entry = new CourseRecoveryPlanEntry(
                        student.studentId(),
                        student.firstName() + " " + student.lastName(),
                        student.major(),
                        student.year(),
                        course.courseId(),
                        course.courseName(),
                        course.credits(),
                        course.semester(),
                        course.instructor(),
                        grade.grade(),
                        failureReason,
                        getRecommendedAction(failureReason),
                        "Week 1", // Default milestone week
                        getMilestoneTask(failureReason),
                        "Not Started" // Default status
                );
                plans.add(entry);
            }
        }
        return plans;
    }

    private Optional<String> determineFailureReason(String grade) {
        if (grade == null || grade.isBlank()) {
            return Optional.of("Missing grade");
        }

        try {
            int numericGrade = Integer.parseInt(grade);
            if (numericGrade < NUMERIC_PASS_THRESHOLD) {
                return Optional.of("Final grade below pass threshold");
            }
        } catch (NumberFormatException e) {
            // Not a numeric grade, check for failing letter grades
            if (FAILING_LETTER_GRADES.contains(grade.toUpperCase())) {
                return Optional.of("Failing letter grade");
            }
        }

        return Optional.empty(); // Grade is passing
    }

    private String getRecommendedAction(String failureReason) {
        switch (failureReason) {
            case "Final grade below pass threshold":
                return "Review lecture notes for Weeks 1-5 and book a consultation with the lecturer.";
            case "Failing letter grade":
                return "Complete the supplementary recovery assignment provided by the department.";
            case "Missing grade":
                return "Contact the course instructor immediately to clarify grade status.";
            default:
                return "Consult with academic advisor.";
        }
    }

    private String getMilestoneTask(String failureReason) {
        switch (failureReason) {
            case "Final grade below pass threshold":
                return "Revise core concepts and attempt practice questions.";
            case "Failing letter grade":
                return "Prepare for and sit the resit exam or complete alternative assessment.";
            case "Missing grade":
                return "Inquire about the missing assessment result and arrange for completion if necessary.";
            default:
                return "Develop a study plan.";
        }
    }

    private void writeRecoveryPlansToCsv(List<CourseRecoveryPlanEntry> plans, Path outputPath) throws IOException {
        final String HEADER = "StudentID,StudentName,Major,Year,CourseID,CourseName,Credits,Semester,Instructor,OriginalGrade,FailureReason,RecommendedAction,MilestoneWeek,MilestoneTask,Status";
        
        try (Writer writer = Files.newBufferedWriter(outputPath)) {
            writer.write(HEADER + "\n");
            for (CourseRecoveryPlanEntry entry : plans) {
                String line = String.join(",",
                        entry.studentId(),
                        "\"" + entry.studentName() + "\"",
                        entry.major(),
                        entry.year(),
                        entry.courseId(),
                        "\"" + entry.courseName() + "\"",
                        String.valueOf(entry.credits()),
                        entry.semester(),
                        "\"" + entry.instructor() + "\"",
                        entry.originalGrade(),
                        "\"" + entry.failureReason() + "\"",
                        "\"" + entry.recommendedAction() + "\"",
                        entry.milestoneWeek(),
                        "\"" + entry.milestoneTask() + "\"",
                        entry.status()
                );
                writer.write(line + "\n");
            }
        }
    }
}
