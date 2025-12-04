package crs.reporting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class StudentRepository {

    private Map<String, StudentInfo> studentMap = new HashMap<>();

    public static class StudentInfo {
        public String name;
        public String major;
        public String year;
        public String email;

        public StudentInfo(String name, String major, String year, String email) {
            this.name = name;
            this.major = major;
            this.year = year;
            this.email = email;
        }
    }

    public void loadStudents(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // skip header

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                String id = parts[0];
                String fullName = parts[1] + " " + parts[2];
                String major = parts[3];
                String year = parts[4];
                String email = parts[5];

                studentMap.put(id, new StudentInfo(fullName, major, year, email));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public StudentInfo getStudent(String id) {
        return studentMap.get(id);
    }
}
