package crs.recovery.models;

public class RecoveryFailedComponent {

    private String studentId;
    private String courseCode;
    private String courseName;
    private String componentName; 
    private String reason;          
    private double grade;

    public RecoveryFailedComponent() {}

    public RecoveryFailedComponent(String studentId, String courseCode, String courseName,
                                   String componentName, String reason, double grade) {
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.componentName = componentName;
        this.reason = reason;
        this.grade = grade;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}