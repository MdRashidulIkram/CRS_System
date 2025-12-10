package crs.recovery.repository;

import crs.recovery.models.*;
import java.io.*;
import java.util.*;
import java.nio.file.*;

public class RecoveryRepository {

    private final String failedComponentsFile;
    private final String recommendationsFile;
    private final String milestonesFile;
    private final String progressFile;

    private List<RecoveryFailedComponent> failedComponents = new ArrayList<>();
    private List<RecoveryRecommendation> recommendations = new ArrayList<>();
    private List<RecoveryMilestone> milestones = new ArrayList<>();
    private List<RecoveryProgress> progress = new ArrayList<>();

    public RecoveryRepository(String failedComponentsFile,
                              String recommendationsFile,
                              String milestonesFile,
                              String progressFile) {
        this.failedComponentsFile = failedComponentsFile;
        this.recommendationsFile = recommendationsFile;
        this.milestonesFile = milestonesFile;
        this.progressFile = progressFile;
    }

    // -------------------------------------------------------------------------
    // LOADING METHODS
    // -------------------------------------------------------------------------
    
    public void loadFailedComponents() {
        failedComponents.clear();
        loadCsv(failedComponentsFile, line -> {
            String[] p = line.split(",");
            if (p.length < 6) return null;

            return new RecoveryFailedComponent(
                p[0], p[1], p[2], p[3], p[4], Double.parseDouble(p[5])
            );
        }).forEach(obj -> failedComponents.add((RecoveryFailedComponent) obj));
    }

    public void loadRecommendations() {
        recommendations.clear();
        loadCsv(recommendationsFile, line -> {
            String[] p = line.split(",", 3);
            if (p.length < 3) return null;

            return new RecoveryRecommendation(p[0], p[1], p[2]);
        }).forEach(obj -> recommendations.add((RecoveryRecommendation) obj));
    }

    public void loadMilestones() {
        milestones.clear();
        loadCsv(milestonesFile, line -> {
            String[] p = line.split(",", 4);
            if (p.length < 4) return null;

            return new RecoveryMilestone(p[0], p[1], p[2], p[3]);
        }).forEach(obj -> milestones.add((RecoveryMilestone) obj));
    }

    public void loadProgress() {
        progress.clear();
        loadCsv(progressFile, line -> {
            String[] p = line.split(",", 4);
            if (p.length < 4) return null;

            boolean completed = Boolean.parseBoolean(p[3]);

            return new RecoveryProgress(p[0], p[1], p[2], completed);
        }).forEach(obj -> progress.add((RecoveryProgress) obj));
    }

    // -------------------------------------------------------------------------
    // SAVING METHODS
    // -------------------------------------------------------------------------

    public void saveFailedComponents() {
        saveCsv(failedComponentsFile, writer -> {
            for (RecoveryFailedComponent c : failedComponents) {
                writer.println(String.join(",",
                        c.getStudentId(),
                        c.getCourseCode(),
                        c.getCourseName(),
                        c.getComponentName(),
                        c.getReason(),
                        String.valueOf(c.getGrade())
                ));
            }
        });
    }

    public void saveRecommendations() {
        saveCsv(recommendationsFile, writer -> {
            for (RecoveryRecommendation r : recommendations) {
                writer.println(String.join(",",
                        r.getStudentId(),
                        r.getCourseCode(),
                        r.getRecommendationText()
                ));
            }
        });
    }

    public void saveMilestones() {
        saveCsv(milestonesFile, writer -> {
            for (RecoveryMilestone m : milestones) {
                writer.println(String.join(",",
                        m.getStudentId(),
                        m.getCourseCode(),
                        m.getWeekRange(),
                        m.getTask()
                ));
            }
        });
    }

    public void saveProgress() {
        saveCsv(progressFile, writer -> {
            for (RecoveryProgress p : progress) {
                writer.println(String.join(",",
                        p.getStudentId(),
                        p.getCourseCode(),
                        p.getWeekRange(),
                        String.valueOf(p.isCompleted())
                ));
            }
        });
    }

    // -------------------------------------------------------------------------
    // CRUD OPERATIONS
    // -------------------------------------------------------------------------

    public List<RecoveryFailedComponent> getFailedComponents(String studentId) {
        List<RecoveryFailedComponent> list = new ArrayList<>();
        for (RecoveryFailedComponent c : failedComponents) {
            if (c.getStudentId().equalsIgnoreCase(studentId)) list.add(c);
        }
        return list;
    }

    public List<RecoveryRecommendation> getRecommendations(String studentId) {
        List<RecoveryRecommendation> list = new ArrayList<>();
        for (RecoveryRecommendation r : recommendations) {
            if (r.getStudentId().equalsIgnoreCase(studentId)) list.add(r);
        }
        return list;
    }

    public List<RecoveryMilestone> getMilestones(String studentId) {
        List<RecoveryMilestone> list = new ArrayList<>();
        for (RecoveryMilestone m : milestones) {
            if (m.getStudentId().equalsIgnoreCase(studentId)) list.add(m);
        }
        return list;
    }

    public List<RecoveryProgress> getProgress(String studentId) {
        List<RecoveryProgress> list = new ArrayList<>();
        for (RecoveryProgress p : progress) {
            if (p.getStudentId().equalsIgnoreCase(studentId)) list.add(p);
        }
        return list;
    }

    public void addRecommendation(RecoveryRecommendation r) {
        recommendations.add(r);
        saveRecommendations();
    }

    public void removeRecommendation(String studentId, String courseCode) {
        recommendations.removeIf(r ->
                r.getStudentId().equalsIgnoreCase(studentId)
                        && r.getCourseCode().equalsIgnoreCase(courseCode)
        );
        saveRecommendations();
    }

    public void addMilestone(RecoveryMilestone m) {
        milestones.add(m);
        saveMilestones();
    }

    public void removeMilestone(String studentId, String weekRange) {
        milestones.removeIf(m ->
                m.getStudentId().equalsIgnoreCase(studentId)
                        && m.getWeekRange().equalsIgnoreCase(weekRange)
        );
        saveMilestones();
    }

    public void updateProgress(RecoveryProgress p) {
        progress.removeIf(x ->
                x.getStudentId().equalsIgnoreCase(p.getStudentId())
                        && x.getCourseCode().equalsIgnoreCase(p.getCourseCode())
                        && x.getWeekRange().equalsIgnoreCase(p.getWeekRange())
        );
        progress.add(p);
        saveProgress();
    }

    // -------------------------------------------------------------------------
    // INTERNAL CSV UTILITIES
    // -------------------------------------------------------------------------

    private interface CsvMapper {
        Object map(String line);
    }

    private List<Object> loadCsv(String filePath, CsvMapper mapper) {
        List<Object> list = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            for (String line : lines) {
                if (line.contains("studentId")) continue; //skip header line
                if (line.trim().isEmpty()) continue;
                Object obj = mapper.map(line);
                if (obj != null) list.add(obj);
            }
        } catch (IOException e) {
            System.err.println("Error loading CSV: " + filePath);
        }
        return list;
    }

    private interface CsvWriter {
        void write(PrintWriter writer);
    }

    private void saveCsv(String filePath, CsvWriter writerLogic) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writerLogic.write(writer);
        } catch (IOException e) {
            System.err.println("Error saving CSV: " + filePath);
        }
    }

}