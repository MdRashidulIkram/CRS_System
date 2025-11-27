package crs.reporting.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import crs.reporting.models.StudentReport;
import crs.reporting.models.ReportEntry;

import java.io.FileOutputStream;
import java.util.stream.Stream;

public class PdfExporter {

    public void exportReport(StudentReport report, String filePath) {

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Academic Performance Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Student info
            Font infoFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            document.add(new Paragraph("Student Name: " + report.getStudentName(), infoFont));
            document.add(new Paragraph("Student ID: " + report.getStudentId(), infoFont));
            document.add(new Paragraph("Major: " + report.getMajor(), infoFont));
            document.add(new Paragraph("Year: " + report.getYear(), infoFont));
            
            document.add(Chunk.NEWLINE);

            // Table
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);

            addTableHeader(table);
            addRows(table, report);

            document.add(table);

            // CGPA
            document.add(Chunk.NEWLINE);
            Font cgpaFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Paragraph cgpaText = new Paragraph("CGPA: " + String.format("%.2f", report.getCgpa()), cgpaFont);
            cgpaText.setAlignment(Element.ALIGN_RIGHT);
            document.add(cgpaText);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Course ID", "Course Name", "Credits", "Semester", "Grade", "Grade Point")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, StudentReport report) {
        for (ReportEntry entry : report.getEntries()) {
            table.addCell(entry.getCourseId());
            table.addCell(entry.getCourseName());
            table.addCell(String.valueOf(entry.getCredits()));
            table.addCell(entry.getSemester());
            table.addCell(entry.getGrade());
            table.addCell(String.valueOf(entry.getGradePoint()));
        }
    }
}