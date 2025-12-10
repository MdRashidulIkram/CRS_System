package crs.recovery;

import crs.recovery.repository.RecoveryRepository;
import crs.recovery.service.RecoveryService;
import crs.util.ResourceUtil;

public final class RecoveryModuleFactory {

    private RecoveryModuleFactory() {}

    public static RecoveryService createService() {
        String failedFile = ResourceUtil.get("recovery_failed_components.csv");
        String recFile = ResourceUtil.get("recovery_recommendations.csv");
        String milestonesFile = ResourceUtil.get("recovery_milestones.csv");
        String progressFile = ResourceUtil.get("recovery_progress.csv");

        // If any resource missing ResourceUtil.get will return null — handle accordingly
        RecoveryRepository repo = new RecoveryRepository(
                failedFile != null ? failedFile : "recovery_failed_components.csv",
                recFile != null ? recFile : "recovery_recommendations.csv",
                milestonesFile != null ? milestonesFile : "recovery_milestones.csv",
                progressFile != null ? progressFile : "recovery_progress.csv"
        );

        // load all CSVs now
        repo.loadFailedComponents();
        repo.loadRecommendations();
        repo.loadMilestones();
        repo.loadProgress();

        return new RecoveryService(repo);
    }
}
