package crs.reporting;

import crs.reporting.ReportEntry;
import java.util.ArrayList;
import java.util.List;

public class StudentReport {
    private String studentId;
    private String studentName;
    private String major;
    private String year;
    private List<ReportEntry> entries = new ArrayList<>();
    private double cgpa;

    public StudentReport(String studentId, String studentName, String major, String year) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.major = major;
        this.year = year;
    }

    public void addEntry(ReportEntry entry) {
        entries.add(entry);
    }

    // Getters & Setters

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<ReportEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<ReportEntry> entries) {
        this.entries = entries;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }  
}
