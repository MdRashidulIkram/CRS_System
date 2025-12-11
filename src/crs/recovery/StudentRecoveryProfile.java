package crs.recovery;

import java.util.List;

public class StudentRecoveryProfile {

    private String studentId;
    private String studentName;

    private List<RecoveryFailedComponent> failedComponents;
    private List<RecoveryRecommendation> recommendations;
    private List<RecoveryMilestone> milestones;
    private List<RecoveryProgress> progress;

    public StudentRecoveryProfile() {}

    public StudentRecoveryProfile(String studentId, String studentName,
                                  List<RecoveryFailedComponent> failedComponents,
                                  List<RecoveryRecommendation> recommendations,
                                  List<RecoveryMilestone> milestones,
                                  List<RecoveryProgress> progress) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.failedComponents = failedComponents;
        this.recommendations = recommendations;
        this.milestones = milestones;
        this.progress = progress;
    }

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

    public List<RecoveryFailedComponent> getFailedComponents() {
        return failedComponents;
    }

    public void setFailedComponents(List<RecoveryFailedComponent> failedComponents) {
        this.failedComponents = failedComponents;
    }

    public List<RecoveryRecommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<RecoveryRecommendation> recommendations) {
        this.recommendations = recommendations;
    }

    public List<RecoveryMilestone> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<RecoveryMilestone> milestones) {
        this.milestones = milestones;
    }

    public List<RecoveryProgress> getProgress() {
        return progress;
    }

    public void setProgress(List<RecoveryProgress> progress) {
        this.progress = progress;
    }
}
