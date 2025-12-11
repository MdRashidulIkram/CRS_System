package crs.reporting;

import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;

public class CourseRepository {

    private Map<String, CourseInfo> courseMap = new HashMap<>();

    public static class CourseInfo {
        public String name;
        public int credits;
        public String semester;

        public CourseInfo(String name, int credits, String semester) {
            this.name = name;
            this.credits = credits;
            this.semester = semester;
        }
    }

    // Load CSV into memory
    public void loadCourses(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // skip header

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                String courseId = parts[0];
                String courseName = parts[1];
                int credits = Integer.parseInt(parts[2]);
                String semester = parts[3];

                courseMap.put(courseId, new CourseInfo(courseName, credits, semester));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CourseInfo getCourse(String courseId) {
        return courseMap.get(courseId);
    }
}
