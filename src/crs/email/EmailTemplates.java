package crs.email;

public class EmailTemplates {

    public static String accountCreated(String name, String role) {
        return "Dear " + name + ",\n\n" +
               "Your CRS account has been created.\n" +
               "Assigned Role: " + role + "\n\n" +
               "Regards,\nCRS System";
    }
    
    public static String accountEdited(String name) {
        return "Dear " + name + ",\n\n" +
               "Your CRS account has been edited.\n" +
               "Please log in to view changes.\n\n" +
               "Regards,\nCRS System";
    }
    
    public static String accountDeleted(String name) {
        return "Dear " + name + ",\n\n" +
               "Your CRS account has been deleted.\n" +
               "If you have any question, please reply to this email.\n\n" +
               "Regards,\nCRS System";
    }

    public static String passwordReset(String newPassword) {
        return "Hello\n\n" +
               "Your password has been reset.\n" +
               "New Password: " + newPassword + "\n\n" +
               "Please change it after logging in.\n\n" +
               "Regards,\nCRS System";
    }

    public static String recoveryUpdate(String studentName, String course, String milestone) {
        return "Hi " + studentName + ",\n\n" +
               "Your course recovery plan has been updated:\n" +
               "Course: " + course + "\n" +
               "Milestone: " + milestone + "\n\n" +
               "Regards,\nCRS System";
    }

    public static String academicReportNotification(String studentName) {
        return "Hello " + studentName + ",\n\n" +
               "Your academic performance report has been generated.\n" +
               "Please find the attached PDF.\n\n" +
               "Regards,\nCRS System";
    }
}