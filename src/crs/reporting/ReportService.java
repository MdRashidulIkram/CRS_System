package crs.reporting;

import crs.reporting.models.ReportEntry;
import crs.reporting.models.StudentReport;
import java.util.List;

public class ReportService {

    private StudentRepository studentRepo;
    private CourseRepository courseRepo;
    private GradesRepository gradesRepo;

    public ReportService(StudentRepository sRepo,
            CourseRepository cRepo,
            GradesRepository gRepo) {

        this.studentRepo = sRepo;
        this.courseRepo = cRepo;
        this.gradesRepo = gRepo;
    }

    public StudentReport generateReport(String studentId) {

        StudentRepository.StudentInfo info = studentRepo.getStudent(studentId);

        if (info == null) {
            return null; // student not found
        }

        StudentReport report = new StudentReport(
                studentId,
                info.name,
                info.major,
                info.year
        );

        // Pull grades for student
        List<GradesRepository.GradeRecord> grades = gradesRepo.getGrades(studentId);

        for (GradesRepository.GradeRecord g : grades) {

            CourseRepository.CourseInfo cInfo = courseRepo.getCourse(g.courseId);
            if (cInfo == null) {
                continue;  // mismatched course, skip
            }
            double gradePoint = GradeUtility.gradeToPoints(g.grade);

            ReportEntry entry = new ReportEntry(
                    g.courseId,
                    cInfo.name,
                    cInfo.credits,
                    g.grade,
                    gradePoint,
                    cInfo.semester
            );

            report.addEntry(entry);
        }

        calculateCGPA(report);

        return report;
    }

    public double calculateCGPA(StudentReport report) {
        double totalGradePoints = 0;
        int totalCredits = 0;

        for (ReportEntry entry : report.getEntries()) {
            totalGradePoints += entry.getGradePoint() * entry.getCredits();
            totalCredits += entry.getCredits();
        }

        if (totalCredits == 0) {
            return 0.0;
        }

        double cgpa = totalGradePoints / totalCredits;
        report.setCgpa(cgpa);

        return cgpa;
    }
}
