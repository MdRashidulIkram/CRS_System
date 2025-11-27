package crs.reporting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradesRepository {

    // Map: StudentID -> List of GradeRecord
    private Map<String, List<GradeRecord>> gradeMap = new HashMap<>();

    public static class GradeRecord {
        public String courseId;
        public String grade;

        public GradeRecord(String courseId, String grade) {
            this.courseId = courseId;
            this.grade = grade;
        }
    }

    public void loadGrades(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // skip header

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                String studentId = parts[0];
                String courseId = parts[1];
                String grade = parts[2];

                gradeMap.putIfAbsent(studentId, new ArrayList<>());
                gradeMap.get(studentId).add(new GradeRecord(courseId, grade));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<GradeRecord> getGrades(String studentId) {
        return gradeMap.getOrDefault(studentId, new ArrayList<>());
    }
}
