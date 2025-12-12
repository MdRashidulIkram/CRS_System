package crs.login;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private static final String USERS_FILE = "users.txt";

    // -------------------- AUTHENTICATION --------------------
    public User authenticate(String email, String password, String role) {
        List<User> users = loadUsers();

        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)
                    && u.getPassword().equals(password)
                    && u.getRole().equalsIgnoreCase(role)
                    && u.isActive()) {
                return u;
            }
        }
        return null; // login fail
    }

    // -------------------- LOAD USERS --------------------
    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");

                // id | name | email | username | password | role | status
                if (p.length == 7) {
                    User u = createUserObj(p[0], p[1], p[2], p[3], p[4], p[5], p[6]);
                    users.add(u);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    // -------------------- SAVE USERS --------------------
    private void saveUsers(List<User> users) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(USERS_FILE))) {

            for (User u : users) {
                pw.println(
                        u.getId() + "|" +
                        u.getName() + "|" +
                        u.getEmail() + "|" +
                        u.getUsername() + "|" +
                        u.getPassword() + "|" +
                        u.getRole() + "|" +
                        u.getStatus()
                );
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // -------------------- ADD USER --------------------
    public void addUser(User user) {
        List<User> users = loadUsers();
        users.add(user);
        saveUsers(users);
    }

    // -------------------- UPDATE USER --------------------
    public void updateUser(User updatedUser) {
        List<User> users = loadUsers();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(updatedUser.getId())) {
                users.set(i, updatedUser);
                break;
            }
        }

        saveUsers(users);
    }

    // -------------------- DELETE USER --------------------
    public void deleteUser(String userId) {
        List<User> users = loadUsers();
        users.removeIf(u -> u.getId().equals(userId));
        saveUsers(users);
    }

    // -------------------- CREATE USER FACTORY (INHERITANCE) --------------------
    public User createUserObj(String id, String name, String email,
                              String username, String password,
                              String role, String status) {

        switch (role.toUpperCase()) {
            case "ACADEMIC_OFFICER":
                return new AcademicOfficer(id, name, email, username, password, status);

            case "COURSE_ADMIN":
                return new CourseAdmin(id, name, email, username, password, status);
            default:
                return new CourseAdmin(id, name, email, username, password, status);
        }
    }

    // -------------------- RESET PASSWORD --------------------
    public boolean resetPassword(String email, String role, String newPassword) {
        List<User> users = loadUsers();
        boolean updated = false;

        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);

            if (u.getEmail().equalsIgnoreCase(email)
                    && u.getRole().equalsIgnoreCase(role)
                    && u.isActive()) {

                User updatedUser = createUserObj(
                        u.getId(), u.getName(), u.getEmail(),
                        u.getUsername(), newPassword,
                        u.getRole(), u.getStatus()
                );

                users.set(i, updatedUser);
                updated = true;
                break;
            }
        }

        if (updated) saveUsers(users);

        return updated;
    }

    // -------------------- EXTRA: RETURN ALL USERS --------------------
    public List<User> loadAllUsers() {
        return loadUsers();
    }
}
