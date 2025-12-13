package crs.recovery;

import crs.email.EmailService;
import crs.recovery.RecoveryRepository;
import crs.reporting.StudentRepository;
import crs.reporting.StudentRepository.StudentInfo;

import java.util.*;

public class RecoveryService {

    private final RecoveryRepository repository;
    private final StudentRepository studentRepository;
    private final EmailService emailService;

    public RecoveryService(RecoveryRepository repository, StudentRepository studentRepository, EmailService emailService) {
        this.repository = repository;
        this.studentRepository = studentRepository;
        this.emailService = emailService;
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

    private void sendRecoveryEmail(String studentId, RecoveryUpdateType type) {
        
        if (studentRepository == null || emailService == null) {
            return;
        }

        StudentInfo s = studentRepository.getStudent(studentId);
        if (s == null || s.email == null) {
            return;
        }

        String subject = "Course Recovery Plan Update";

        StringBuilder body = new StringBuilder();
        body.append("Dear ").append(s.name).append(",\n\n");
        body.append("Your course recovery plan has been updated.\n\n");

        // ------------------------------------------------------------------
        // RECOMMENDATION UPDATE
        // ------------------------------------------------------------------
        if (type == RecoveryUpdateType.RECOMMENDATION) {

            body.append("Updated Recovery Recommendations:\n");

            List<RecoveryRecommendation> recs
                    = repository.getRecommendations(studentId);

            if (recs.isEmpty()) {
                body.append("No recommendations available.\n");
            } else {
                for (RecoveryRecommendation r : recs) {
                    body.append("- ")
                            .append(r.getCourseCode())
                            .append(" : ")
                            .append(r.getRecommendationText())
                            .append("\n");
                }
            }
        }

        // ------------------------------------------------------------------
        // MILESTONE UPDATE
        // ------------------------------------------------------------------
        if (type == RecoveryUpdateType.MILESTONE) {

            body.append("Updated Recovery Milestones:\n");

            List<RecoveryMilestone> milestones
                    = repository.getMilestones(studentId);

            if (milestones.isEmpty()) {
                body.append("No milestones defined.\n");
            } else {
                for (RecoveryMilestone m : milestones) {
                    body.append("- ")
                            .append(m.getCourseCode())
                            .append(" | ")
                            .append(m.getWeekRange())
                            .append(" : ")
                            .append(m.getTask())
                            .append("\n");
                }
            }
        }

        body.append("\nPlease follow the timeline carefully.\n\n");
        body.append("Regards,\nCRS Academic System");

        emailService.sendEmail(s.email, subject, body.toString());
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
        sendRecoveryEmail(studentId, RecoveryUpdateType.RECOMMENDATION);
    }

    public void updateRecommendation(String studentId, String courseCode, String text) {
        // remove old
        repository.removeRecommendation(studentId, courseCode);

        // add new
        repository.addRecommendation(new RecoveryRecommendation(studentId, courseCode, text));
        sendRecoveryEmail(studentId, RecoveryUpdateType.RECOMMENDATION);
    }

    public void removeRecommendation(String studentId, String courseCode) {
        repository.removeRecommendation(studentId, courseCode);
        sendRecoveryEmail(studentId, RecoveryUpdateType.RECOMMENDATION);
    }

    // -------------------------------------------------------------------------
    // MILESTONES CRUD
    // -------------------------------------------------------------------------
    public void addMilestone(String studentId, String courseCode, String weekRange, String task) {
        RecoveryMilestone m = new RecoveryMilestone(studentId, courseCode, weekRange, task);
        repository.addMilestone(m);
        sendRecoveryEmail(studentId, RecoveryUpdateType.MILESTONE);
    }

    public void removeMilestone(String studentId, String weekRange) {
        repository.removeMilestone(studentId, weekRange);
        sendRecoveryEmail(studentId, RecoveryUpdateType.MILESTONE);
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
