package crs.recovery.models;

public class RecoveryMilestone {

    private String studentId;
    private String courseCode;
    private String weekRange;     // Example: "Week 1–2"
    private String task;          // Example: "Review all lecture topics"

    public RecoveryMilestone() {}

    public RecoveryMilestone(String studentId, String courseCode, String weekRange, String task) {
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.weekRange = weekRange;
        this.task = task;
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

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
