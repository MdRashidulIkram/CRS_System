package crs.recovery;

public class RecoveryRecommendation {

    private String studentId;
    private String courseCode;
    private String recommendationText;

    public RecoveryRecommendation() {}

    public RecoveryRecommendation(String studentId, String courseCode, String recommendationText) {
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.recommendationText = recommendationText;
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

    public String getRecommendationText() {
        return recommendationText;
    }

    public void setRecommendationText(String recommendationText) {
        this.recommendationText = recommendationText;
    }
}