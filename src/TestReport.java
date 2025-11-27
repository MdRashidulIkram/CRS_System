
import crs.reporting.GradesRepository;
import crs.reporting.StudentRepository;
import crs.reporting.CourseRepository;
import crs.reporting.ReportService;
import crs.reporting.models.StudentReport;
import crs.reporting.models.ReportEntry;

public class TestReport {
    public static void main(String[] args) {

        StudentRepository studentRepo = new StudentRepository();
        studentRepo.loadStudents("C:\\Users\\rashi\\OneDrive\\Classes\\Bismi Class\\student_information.csv");

        CourseRepository courseRepo = new CourseRepository();
        courseRepo.loadCourses("C:\\Users\\rashi\\OneDrive\\Classes\\Bismi Class\\course_assessment_information.csv");

        GradesRepository gradeRepo = new GradesRepository();
        gradeRepo.loadGrades("C:\\Users\\rashi\\OneDrive\\Classes\\Bismi Class\\grades.csv");

        ReportService rs = new ReportService(studentRepo, courseRepo, gradeRepo);

        StudentReport report = rs.generateReport("S020");

        System.out.println("Student: " + report.getStudentName());
        System.out.println("CGPA: " + report.getCgpa());

        for (ReportEntry e : report.getEntries()) {
            System.out.println(e.getCourseId() + " - " +
                e.getCourseName() + " - " +
                e.getGrade() + " (" + e.getGradePoint() + ")");
        }
    }
}