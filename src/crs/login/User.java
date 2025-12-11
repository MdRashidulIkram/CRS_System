package crs.login;

public class User {
    private String id;
    private String name;
    private String email;
    private String username;
    private String password;
    private String role;
    private String status; // ACTIVE / INACTIVE

    public User(String id, String name, String email,
                String username, String password,
                String role, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    // --- Getters (encapsulation: fields are private) ---
    public String getId() { return id; }

    public String getName() { return name; }

    public String getEmail() { return email; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public String getRole() { return role; }

    public String getStatus() { return status; }

    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(status);
    }
}

class AcademicOfficer extends User {
    public AcademicOfficer(String id, String name, String email, String username, String password, String status) {
        super(id, name, email, username, password, "ACADEMIC_OFFICER", status);
    }
}

class CourseAdmin extends User {
    public CourseAdmin(String id, String name, String email, String username, String password, String status) {
        super(id, name, email, username, password, "COURSE_ADMIN", status);
    }
}

