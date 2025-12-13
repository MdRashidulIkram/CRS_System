package crs.recovery;

import crs.email.EmailService;
import crs.reporting.StudentRepository;
import crs.util.ResourceUtil;
import java.io.File;

public final class RecoveryModuleFactory {

    private RecoveryModuleFactory() {
    }

    public static RecoveryService createService() {
        String base = System.getProperty("user.dir") + "/data/";

        File dataDir = new File(base);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        RecoveryRepository repo = new RecoveryRepository(
                base + "recovery_failed_components.csv",
                base + "recovery_recommendations.csv",
                base + "recovery_milestones.csv",
                base + "recovery_progress.csv"
        );

        // load all CSVs now
        repo.loadFailedComponents();
        repo.loadRecommendations();
        repo.loadMilestones();
        repo.loadProgress();

        StudentRepository studentRepo = new StudentRepository();
        String studentFile = ResourceUtil.get("student_information.csv");
        if (studentFile != null) {
            studentRepo.loadStudents(studentFile);
        }

        EmailService emailService = new EmailService();

        return new RecoveryService(repo, studentRepo, emailService);
    }
}
