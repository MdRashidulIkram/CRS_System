package crs.recovery;

public class RecoveryProgress {

    private String studentId;
    private String courseCode;
    private String weekRange;
    private boolean completed;

    public RecoveryProgress() {}

    public RecoveryProgress(String studentId, String courseCode, String weekRange, boolean completed) {
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.weekRange = weekRange;
        this.completed = completed;
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

    public String getWeekRange() {
        return weekRange;
    }

    public void setWeekRange(String weekRange) {
        this.weekRange = weekRange;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
