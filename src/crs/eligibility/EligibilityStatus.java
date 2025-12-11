package crs.eligibility;

public class EligibilityStatus {
    private String studentId;
    private String name;
    private double cgpa;
    private int failedCourses;
    private boolean eligible;

    public EligibilityStatus(String studentId, String name, double cgpa, int failedCourses, boolean eligible) {
        this.studentId = studentId;
        this.name = name;
        this.cgpa = cgpa;
        this.failedCourses = failedCourses;
        this.eligible = eligible;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public int getFailedCourses() {
        return failedCourses;
    }

    public void setFailedCourses(int failedCourses) {
        this.failedCourses = failedCourses;
    }

    public boolean isEligible() {
        return eligible;
    }

    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }
}
