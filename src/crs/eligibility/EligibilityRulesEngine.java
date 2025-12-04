// Author: Hameem Arian
// TP: TP084996
// Feature: Eligibility Check & Enrollment

package crs.eligibility;

import crs.recovery.StudentRecord;
import crs.recovery.CourseRecord;

/**
 * Contains pure logic for checking eligibility of enrollment requests.
 *
 * This class receives student and course records (no file I/O) and returns
 * an {@link EligibilityResult} describing whether the request is APPROVED or
 * REJECTED and why.
 *
 * Implemented by Hameem Arian (TP084996).
 */
public final class EligibilityRulesEngine {

    private EligibilityRulesEngine() {
        // Utility class
    }

    /**
     * Evaluate a single enrollment request.
     *
     * @param req the enrollment request
     * @param student the student record (may be null if not found)
     * @param course the course record (may be null if not found)
     * @return an {@link EligibilityResult} with decision and reason
     */
    public static EligibilityResult evaluate(EligibilityRequest req, StudentRecord student, CourseRecord course) {
        String studentId = req.studentId();
        String courseId = req.courseId();
        String term = req.term();

        if (student == null) {
            return new EligibilityResult(studentId, "", courseId, "", term,
                    "REJECTED", "Student not found in registry.", "");
        }

        if (course == null) {
            String studentName = student.firstName() + " " + student.lastName();
            return new EligibilityResult(studentId, studentName, courseId, "", term,
                    "REJECTED", "Course not found in catalog.", "");
        }

        String studentName = student.firstName() + " " + student.lastName();
        String courseName = course.courseName();

        // Rule: major compatibility (simple heuristic)
        String studentMajor = student.major() == null ? "" : student.major();
        String courseNameLc = courseName == null ? "" : courseName.toLowerCase();

        if ((courseNameLc.contains("computer") || courseNameLc.contains("software") || courseNameLc.contains("data") || courseNameLc.contains("algorithm"))
                && !(studentMajor.toLowerCase().contains("computer") || studentMajor.toLowerCase().contains("engineering") || studentMajor.toLowerCase().contains("software"))) {
            return new EligibilityResult(studentId, studentName, courseId, courseName, term,
                    "REJECTED", "Student major is incompatible with course requirements.", "Consider program transfer or advisor approval.");
        }

        // Rule: minimum year for heavier courses (credits >= 4 require Sophomore+)
        try {
            int credits = course.credits();
            int yearLevel = mapYearToLevel(student.year());
            if (credits >= 4 && yearLevel < 2) {
                return new EligibilityResult(studentId, studentName, courseId, courseName, term,
                        "REJECTED", "Course requires minimum year: Sophomore or above.", "Consider talking to your advisor.");
            }
        } catch (Exception e) {
            // If parsing fails, continue with permissive default
        }

        // Default: approve
        return new EligibilityResult(studentId, studentName, courseId, courseName, term,
                "APPROVED", "Eligible based on configured rules.", "Enrollment accepted.");
    }

    private static int mapYearToLevel(String year) {
        if (year == null) return 0;
        switch (year.toLowerCase()) {
            case "freshman":
                return 1;
            case "sophomore":
                return 2;
            case "junior":
                return 3;
            case "senior":
                return 4;
            default:
                // Try numeric parse
                try {
                    return Integer.parseInt(year);
                } catch (NumberFormatException ex) {
                    return 0;
                }
        }
    }
}
