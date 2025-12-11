package crs.eligibility;

import crs.eligibility.EligibilityService;
import crs.eligibility.EligibilityStatus;
import crs.login.AcademicOfficerDashboard;
import crs.login.CourseAdminDashboard;
import crs.login.User;

import java.util.List;
import javax.swing.table.DefaultTableModel;

public class EligibilityCheckUI extends javax.swing.JFrame {

    private final EligibilityService eligibilityService;
    private final User currentUser;

    public EligibilityCheckUI(User user, EligibilityService service) {
        initComponents();
        this.currentUser = user;
        this.eligibilityService = service;

        loadEligibilityTable();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tableEligibility = new javax.swing.JTable();
        btnBack = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        lblTitle = new javax.swing.JLabel();
        btnRegister = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableEligibility.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Student ID", "Name", "CGPA", "Failed Courses", "Eligible"
                }
        ));
        jScrollPane1.setViewportView(tableEligibility);
        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 620, 260));

        btnBack.setText("Back");
        btnBack.addActionListener(evt -> btnBackActionPerformed(evt));
        getContentPane().add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 100, 40));

        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(evt -> loadEligibilityTable());
        getContentPane().add(btnRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 360, 100, 40));

        btnRegister.setText("Register Student");
        btnRegister.addActionListener(evt -> btnRegisterActionPerformed(evt));
        getContentPane().add(btnRegister, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 360, 180, 40));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24));
        lblTitle.setText("Eligibility Check & Enrolment");
        getContentPane().add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }

    private void loadEligibilityTable() {
        DefaultTableModel model = (DefaultTableModel) tableEligibility.getModel();
        model.setRowCount(0);

        List<EligibilityStatus> results = eligibilityService.evaluateAllStudents();

        for (EligibilityStatus r : results) {
            model.addRow(new Object[]{
                r.getStudentId(),
                r.getName(),
                String.format("%.2f", r.getCgpa()),
                r.getFailedCourses(),
                r.isEligible() ? "YES" : "NO"
            });
        }
    }

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        // Role-based back navigation
        if (currentUser.getRole().equalsIgnoreCase("ACADEMIC_OFFICER")) {
            new AcademicOfficerDashboard(currentUser).setVisible(true);
        } else {
            new CourseAdminDashboard(currentUser).setVisible(true);
        }
        this.dispose();
    }

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {
        int row = tableEligibility.getSelectedRow();

        if (row == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select a student first.");
            return;
        }

        String eligibility = tableEligibility.getValueAt(row, 4).toString();
        String studentId = tableEligibility.getValueAt(row, 0).toString();

        if (!eligibility.equals("YES")) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "This student is NOT eligible for enrollment.",
                    "Not Eligible",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        javax.swing.JOptionPane.showMessageDialog(this,
                "Student " + studentId + " has been successfully registered!",
                "Enrollment Complete",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    // Variables declaration
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRegister;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTable tableEligibility;
}
