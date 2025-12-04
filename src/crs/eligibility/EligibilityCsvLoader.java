// Author: Hameem Arian
// TP: TP084996
// Feature: Eligibility Check & Enrollment

package crs.eligibility;

import crs.recovery.CsvUtils;
import crs.recovery.StudentRecord;
import crs.recovery.CourseRecord;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Loads CSV inputs for the eligibility feature.
 *
 * This loader returns collections/maps that the processor and rules engine
 * operate on. It intentionally performs light parsing only and relies on
 * {@link crs.recovery.CsvUtils} for CSV line reading.
 *
 * Implemented by Hameem Arian (TP084996).
 */
public final class EligibilityCsvLoader {

    private EligibilityCsvLoader() {
        // utility
    }

    public static List<EligibilityRequest> loadRequests(Path enrollmentCsv) {
        List<EligibilityRequest> out = new ArrayList<>();
        List<String[]> lines = CsvUtils.readAllLines(enrollmentCsv);
        for (String[] cols : lines) {
            if (cols.length < 4) continue;
            out.add(new EligibilityRequest(cols[0], cols[1], cols[2], cols[3]));
        }
        return out;
    }

    public static Map<String, StudentRecord> loadStudents(Path studentsCsv) {
        Map<String, StudentRecord> m = new HashMap<>();
        List<String[]> lines = CsvUtils.readAllLines(studentsCsv);
        for (String[] cols : lines) {
            if (cols.length < 6) continue;
            // StudentID,FirstName,LastName,Major,Year,Email
            StudentRecord s = new StudentRecord(cols[0], cols[1], cols[2], cols[3], cols[4], cols[5]);
            m.put(cols[0], s);
        }
        return m;
    }

    public static Map<String, CourseRecord> loadCourses(Path coursesCsv) {
        Map<String, CourseRecord> m = new HashMap<>();
        List<String[]> lines = CsvUtils.readAllLines(coursesCsv);
        for (String[] cols : lines) {
            if (cols.length < 7) continue;
            try {
                int credits = Integer.parseInt(cols[2]);
                int examWeight = Integer.parseInt(cols[5]);
                int assignmentWeight = Integer.parseInt(cols[6]);
                CourseRecord c = new CourseRecord(cols[0], cols[1], credits, cols[3], cols[4], examWeight, assignmentWeight);
                m.put(cols[0], c);
            } catch (NumberFormatException e) {
                // Skip malformed row
            }
        }
        return m;
    }

    public static Path defaultEnrollmentRequestsPath() {
        return Paths.get("src", "resources", "enrollment_requests.csv");
    }

    public static Path defaultStudentsPath() {
        return Paths.get("src", "resources", "student_information.csv");
    }

    public static Path defaultCoursesPath() {
        return Paths.get("src", "resources", "course_assessment_information.csv");
    }
}
