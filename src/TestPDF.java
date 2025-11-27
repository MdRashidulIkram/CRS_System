
import crs.reporting.CourseRepository;
import crs.reporting.GradesRepository;
import crs.reporting.ReportService;
import crs.reporting.StudentRepository;
import crs.reporting.models.StudentReport;
import crs.reporting.pdf.PdfExporter;

public class TestPDF {
    public static void main(String[] args) {

        StudentRepository sr = new StudentRepository();
        sr.loadStudents("src/resources/student_information.csv");

        CourseRepository cr = new CourseRepository();
        cr.loadCourses("src/resources/course_assessment_information.csv");

        GradesRepository gr = new GradesRepository();
        gr.loadGrades("src/resources/grades.csv");

        ReportService rs = new ReportService(sr, cr, gr);

        StudentReport report = rs.generateReport("S020");

        PdfExporter exporter = new PdfExporter();
        exporter.exportReport(report, "S020_Report.pdf");

        System.out.println("PDF generated!");
    }
}
