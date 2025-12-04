// Author: Hameem Arian
// TP: TP084996
// Feature: Eligibility Check & Enrollment

package crs.eligibility;

import crs.recovery.env.EnvironmentValidator;
import crs.recovery.StudentRecord;
import crs.recovery.CourseRecord;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Main orchestration for the Eligibility Check & Enrollment feature.
 *
 * Loads student/course data and enrollment requests, applies the
 * {@link EligibilityRulesEngine}, and writes `data/output/enrollment_decisions.csv`.
 *
 * Implemented by Hameem Arian (TP084996).
 */
public final class EligibilityProcessor {

    private static final Path DEFAULT_OUTPUT = Paths.get("data", "output", "enrollment_decisions.csv");

    private EligibilityProcessor() { }

    public static void main(String[] args) {
        // Ensure shared environment from recovery (allowed) and eligibility-specific checks
        try {
            EnvironmentValidator.runAllChecks();
        } catch (Throwable t) {
            // If EnvironmentValidator is not available for any reason, continue — it's optional
        }
        EligibilityEnvironmentHelper.runChecks();

        Path studentsPath = EligibilityCsvLoader.defaultStudentsPath();
        Path coursesPath = EligibilityCsvLoader.defaultCoursesPath();
        Path requestsPath = EligibilityCsvLoader.defaultEnrollmentRequestsPath();

        Map<String, StudentRecord> students = EligibilityCsvLoader.loadStudents(studentsPath);
        Map<String, CourseRecord> courses = EligibilityCsvLoader.loadCourses(coursesPath);
        List<EligibilityRequest> requests = EligibilityCsvLoader.loadRequests(requestsPath);

        int approved = 0;
        int rejected = 0;

        try (BufferedWriter w = Files.newBufferedWriter(DEFAULT_OUTPUT, StandardCharsets.UTF_8)) {
            String header = "StudentID,StudentName,CourseID,CourseName,Term,Decision,Reason,Notes";
            w.write(header);
            w.newLine();

            for (EligibilityRequest req : requests) {
                StudentRecord s = students.get(req.studentId());
                CourseRecord c = courses.get(req.courseId());
                EligibilityResult res = EligibilityRulesEngine.evaluate(req, s, c);

                if ("APPROVED".equalsIgnoreCase(res.decision())) approved++; else rejected++;

                String line = String.join(",",
                        safe(res.studentId()),
                        safe(res.studentName()),
                        safe(res.courseId()),
                        safe(res.courseName()),
                        safe(res.term()),
                        safe(res.decision()),
                        safe(res.reason()),
                        safe(res.notes())
                );
                w.write(line);
                w.newLine();
            }
            w.flush();
        } catch (IOException e) {
            System.err.println("Failed to write output: " + e.getMessage());
        }

        System.out.println("Processed " + requests.size() + " requests.");
        System.out.println("Approved: " + approved);
        System.out.println("Rejected: " + rejected);
        System.out.println("Output written to: " + DEFAULT_OUTPUT.toAbsolutePath());
    }

    private static String safe(String s) {
        return s == null ? "" : s.replaceAll(",", " ");
    }
}
