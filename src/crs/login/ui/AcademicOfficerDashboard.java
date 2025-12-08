package crs.login.ui;

import javax.swing.JButton;

public class AcademicOfficerDashboard extends BaseDashboard {
    
    public AcademicOfficerDashboard(User user) {
        super(user, "Academic Officer Dashboard");
    }
    /**
     * Academic Officer has one additional feature:
     * - Course Recovery Plan
     *
     * This method adds the extra button.
     */
    @Override
    protected void initRoleSpecificUI() {

        JButton btnCourseRecovery = new JButton("Course Recovery Plan");
        btnCourseRecovery.setBounds(330, 10, 240, 45);

        btnCourseRecovery.addActionListener(e -> {
            // TODO: Replace with your actual UI for Task 2
            //new CourseRecoveryUI(currentUser).setVisible(true);
            dispose();
        });

        roleSpecificPanel.add(btnCourseRecovery);
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
