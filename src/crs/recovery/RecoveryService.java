package crs.recovery;

import crs.recovery.RecoveryRepository;
import crs.reporting.StudentRepository;
import crs.reporting.StudentRepository.StudentInfo;

import java.util.*;

public class RecoveryService {

    private final RecoveryRepository repository;
    private final StudentRepository studentRepository;
    
    public RecoveryService(RecoveryRepository repository){
        this.repository = repository;
        this.studentRepository = null;
    }

    public RecoveryService(RecoveryRepository repository, StudentRepository studentRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
    }

    public StudentRecoveryProfile getRecoveryProfile(String studentId) {

        List<RecoveryFailedComponent> failed = repository.getFailedComponents(studentId);

        if (failed.isEmpty()) {
            return null; // student has no recovery required
        }

        List<RecoveryRecommendation> rec = repository.getRecommendations(studentId);
        List<RecoveryMilestone> ms = repository.getMilestones(studentId);
        List<RecoveryProgress> pr = repository.getProgress(studentId);

        // try to get student name from StudentRepository
        String studentName = "";
        StudentInfo s = studentRepository.getStudent(studentId);
        if (s != null) {
            studentName = s.name;
        }

        return new StudentRecoveryProfile(studentId, studentName, failed, rec, ms, pr);
    }

    // -------------------------------------------------------------------------
    // FAILED COMPONENTS — READ ONLY
    // -------------------------------------------------------------------------
    public List<RecoveryFailedComponent> getFailedComponents(String studentId) {
        return repository.getFailedComponents(studentId);
    }

    // -------------------------------------------------------------------------
    // RECOMMENDATIONS CRUD
    // -------------------------------------------------------------------------
    public void addRecommendation(String studentId, String courseCode, String text) {
        RecoveryRecommendation r = new RecoveryRecommendation(studentId, courseCode, text);
        repository.addRecommendation(r);
    }

    public void updateRecommendation(String studentId, String courseCode, String text) {
        // remove old
        repository.removeRecommendation(studentId, courseCode);

        // add new
        repository.addRecommendation(new RecoveryRecommendation(studentId, courseCode, text));
    }

    public void removeRecommendation(String studentId, String courseCode) {
        repository.removeRecommendation(studentId, courseCode);
    }

    // -------------------------------------------------------------------------
    // MILESTONES CRUD
    // -------------------------------------------------------------------------
    public void addMilestone(String studentId, String courseCode, String weekRange, String task) {
        RecoveryMilestone m = new RecoveryMilestone(studentId, courseCode, weekRange, task);
        repository.addMilestone(m);
    }

    public void removeMilestone(String studentId, String weekRange) {
        repository.removeMilestone(studentId, weekRange);
    }

    // -------------------------------------------------------------------------
    // PROGRESS UPDATES
    // -------------------------------------------------------------------------
    public void updateProgress(String studentId, String courseCode, String weekRange, boolean completed) {
        RecoveryProgress p = new RecoveryProgress(studentId, courseCode, weekRange, completed);
        repository.updateProgress(p);
    }

    // -------------------------------------------------------------------------
    // HELPERS
    // -------------------------------------------------------------------------
    /**
     * Gets recommended study tasks combined with progress status. Useful for
     * checkboxes UI.
     */
    public List<RecoveryMilestone> getMilestones(String studentId) {
        return repository.getMilestones(studentId);
    }

    public List<RecoveryRecommendation> getRecommendations(String studentId) {
        return repository.getRecommendations(studentId);
    }

    public List<RecoveryProgress> getProgress(String studentId) {
        return repository.getProgress(studentId);
    }

}
