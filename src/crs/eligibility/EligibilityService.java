package crs.eligibility;

import crs.reporting.StudentRepository;
import crs.reporting.CourseRepository;
import crs.reporting.GradesRepository;
import crs.reporting.GradesRepository.GradeRecord;
import crs.reporting.CourseRepository.CourseInfo;
import crs.reporting.GradeUtility;
import crs.eligibility.EligibilityStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class EligibilityService {

    private StudentRepository studentRepo;
    private CourseRepository courseRepo;
    private GradesRepository gradesRepo;

    public EligibilityService(StudentRepository sRepo, CourseRepository cRepo, GradesRepository gRepo) {
        this.studentRepo = sRepo;
        this.courseRepo = cRepo;
        this.gradesRepo = gRepo;
    }

    // -------------------- Grade Conversion --------------------
    private double convertGradeToPoint(String grade) {
        return GradeUtility.gradeToPoints(grade);
    }

    // -------------------- Compute CGPA --------------------
    private double computeCgpa(List<GradeRecord> gradeList) {
        double totalPoints = 0;
        int totalCredits = 0;

        for (GradeRecord gr : gradeList) {
            CourseInfo ci = courseRepo.getCourse(gr.courseId);
            if (ci == null) continue;

            double gradePoint = convertGradeToPoint(gr.grade);

            totalPoints += gradePoint * ci.credits;
            totalCredits += ci.credits;
        }

        if (totalCredits == 0) return 0.0;
        return totalPoints / totalCredits;
    }

    // -------------------- Count Failed (F) --------------------
    private int countFailedCourses(List<GradeRecord> gradeList) {
        int count = 0;
        for (GradeRecord gr : gradeList) {
            if (gr.grade.equalsIgnoreCase("F")) {
                count++;
            }
        }
        return count;
    }

    // -------------------- Evaluate All Students --------------------
    public List<EligibilityStatus> evaluateAllStudents() {

        List<EligibilityStatus> results = new ArrayList<>();

        for (Map.Entry<String, StudentRepository.StudentInfo> entry : studentRepo.getStudentMap().entrySet()) {
            String studentId = entry.getKey();
            StudentRepository.StudentInfo sInfo = entry.getValue();

            List<GradeRecord> grades = gradesRepo.getGrades(studentId);

            if (grades.isEmpty()) continue;

            double cgpa = computeCgpa(grades);
            int failedCourses = countFailedCourses(grades);

            boolean eligible = (cgpa >= 2.0 && failedCourses <= 3);

            results.add(new EligibilityStatus(
                    studentId,
                    sInfo.name,
                    cgpa,
                    failedCourses,
                    eligible
            ));
        }
        
        results.sort(Comparator.comparing(EligibilityStatus::getStudentId));       
        return results;
    }
}