package crs.recovery;

import crs.recovery.RecoveryService;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * CourseRecoveryUI — binds to RecoveryService and updates CSV-backed repository.
 * This class focuses on UI + bindings. Business logic lives in RecoveryService.
 */
public class CourseRecoveryUI extends javax.swing.JFrame {

    private final RecoveryService recoveryService;
    private javax.swing.JFrame parentWindow;

    public CourseRecoveryUI(RecoveryService recoveryService, javax.swing.JFrame parent) {
        this.recoveryService = recoveryService;
        this.parentWindow = parent;
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        lblStudentId = new javax.swing.JLabel();
        txtStudentId = new javax.swing.JTextField();
        btnLoad = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        tabPanel = new javax.swing.JTabbedPane();

        // FAILED COMPONENTS TAB
        panelFailed = new javax.swing.JPanel();
        jScrollFail = new javax.swing.JScrollPane();
        tableFailed = new javax.swing.JTable();

        // RECOMMENDATIONS TAB
        panelRecommend = new javax.swing.JPanel();
        jScrollRec = new javax.swing.JScrollPane();
        tableRec = new javax.swing.JTable();
        btnAddRec = new javax.swing.JButton();
        btnUpdateRec = new javax.swing.JButton();
        btnDeleteRec = new javax.swing.JButton();
        txtRecField = new javax.swing.JTextField();
        lblRec = new javax.swing.JLabel();

        // MILESTONES TAB
        panelMilestone = new javax.swing.JPanel();
        jScrollMil = new javax.swing.JScrollPane();
        tableMilestones = new javax.swing.JTable();
        lblWeek = new javax.swing.JLabel();
        lblTask = new javax.swing.JLabel();
        txtWeek = new javax.swing.JTextField();
        txtTask = new javax.swing.JTextField();
        btnAddMil = new javax.swing.JButton();
        btnUpdateMil = new javax.swing.JButton();
        btnDeleteMil = new javax.swing.JButton();
        comboStatus = new javax.swing.JComboBox<>();
        lblStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Course Recovery Plan");

        lblStudentId.setText("Student ID:");

        btnLoad.setText("Load");
        btnLoad.addActionListener((ActionEvent evt) -> loadStudentData());

        btnBack.setText("Back");
        btnBack.addActionListener(this::btnBackActionPerformed);

        // ===== Failed components table
        tableFailed.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"Course", "Component", "Score"}
        ) {
            public boolean isCellEditable(int row, int column) { return false; }
        });
        jScrollFail.setViewportView(tableFailed);

        javax.swing.GroupLayout panelFailedLayout = new javax.swing.GroupLayout(panelFailed);
        panelFailed.setLayout(panelFailedLayout);
        panelFailedLayout.setHorizontalGroup(
            panelFailedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollFail, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
        );
        panelFailedLayout.setVerticalGroup(
            panelFailedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollFail, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
        tabPanel.addTab("Failed Components", panelFailed);

        // ===== Recommendations UI
        tableRec.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"Course", "Recommendation"}
        ) {
            public boolean isCellEditable(int row, int column) { return false; }
        });
        jScrollRec.setViewportView(tableRec);

        lblRec.setText("Recommendation:");

        btnAddRec.setText("Add");
        btnAddRec.addActionListener((ActionEvent evt) -> addRecommendation());

        btnUpdateRec.setText("Update");
        btnUpdateRec.addActionListener((ActionEvent evt) -> updateRecommendation());

        btnDeleteRec.setText("Delete");
        btnDeleteRec.addActionListener((ActionEvent evt) -> deleteRecommendation());

        javax.swing.GroupLayout panelRecommendLayout = new javax.swing.GroupLayout(panelRecommend);
        panelRecommend.setLayout(panelRecommendLayout);
        panelRecommendLayout.setHorizontalGroup(
            panelRecommendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollRec, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
            .addGroup(panelRecommendLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRec)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRecField)
                .addGap(18, 18, 18)
                .addComponent(btnAddRec)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdateRec)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteRec)
                .addContainerGap())
        );
        panelRecommendLayout.setVerticalGroup(
            panelRecommendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRecommendLayout.createSequentialGroup()
                .addGroup(panelRecommendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRec)
                    .addComponent(txtRecField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddRec)
                    .addComponent(btnUpdateRec)
                    .addComponent(btnDeleteRec))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollRec, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addContainerGap())
        );
        tabPanel.addTab("Recommendations", panelRecommend);

        // ===== Milestones UI
        tableMilestones.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"Course", "Week", "Task", "Status"}
        ) {
            public boolean isCellEditable(int row, int column) { return false; }
        });
        jScrollMil.setViewportView(tableMilestones);

        lblWeek.setText("Study Week:");
        lblTask.setText("Task:");
        lblStatus.setText("Status:");
        comboStatus.setModel(new DefaultComboBoxModel<>(new String[]{"Not Started", "In Progress", "Completed"}));

        btnAddMil.setText("Add");
        btnAddMil.addActionListener((ActionEvent evt) -> addMilestone());

        btnUpdateMil.setText("Update");
        btnUpdateMil.addActionListener((ActionEvent evt) -> updateMilestone());

        btnDeleteMil.setText("Delete");
        btnDeleteMil.addActionListener((ActionEvent evt) -> deleteMilestone());

        javax.swing.GroupLayout panelMilestoneLayout = new javax.swing.GroupLayout(panelMilestone);
        panelMilestone.setLayout(panelMilestoneLayout);
        panelMilestoneLayout.setHorizontalGroup(
            panelMilestoneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollMil, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
            .addGroup(panelMilestoneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblWeek)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblTask)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTask)
                .addGap(18, 18, 18)
                .addComponent(lblStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAddMil)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdateMil)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteMil)
                .addContainerGap())
        );
        panelMilestoneLayout.setVerticalGroup(
            panelMilestoneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMilestoneLayout.createSequentialGroup()
                .addGroup(panelMilestoneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblWeek)
                    .addComponent(txtWeek, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTask)
                    .addComponent(txtTask, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStatus)
                    .addComponent(comboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddMil)
                    .addComponent(btnUpdateMil)
                    .addComponent(btnDeleteMil))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollMil, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addContainerGap())
        );
        tabPanel.addTab("Milestones", panelMilestone);

        // ==== Main layout
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabPanel)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblStudentId)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtStudentId, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStudentId)
                    .addComponent(txtStudentId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLoad)
                    .addComponent(btnBack))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabPanel)
                .addContainerGap())
        );

        pack();
    }

    // ================== Actions & Bindings ==================

    private void loadStudentData() {
        String id = txtStudentId.getText().trim();
        if (!id.matches("S\\d{3}")) {
            JOptionPane.showMessageDialog(this, "Invalid Student ID format. Use SXXX.");
            return;
        }

        // failed components
        List<RecoveryFailedComponent> failed = recoveryService.getFailedComponents(id);
        DefaultTableModel mFail = (DefaultTableModel) tableFailed.getModel();
        mFail.setRowCount(0);
        for (RecoveryFailedComponent f : failed) {
            mFail.addRow(new Object[]{f.getCourseCode(), f.getComponentName(), f.getGrade()});
        }

        // recommendations
        List<RecoveryRecommendation> recs = recoveryService.getRecommendations(id);
        DefaultTableModel mRec = (DefaultTableModel) tableRec.getModel();
        mRec.setRowCount(0);
        for (RecoveryRecommendation r : recs) {
            mRec.addRow(new Object[]{r.getCourseCode(), r.getRecommendationText()});
        }

        // milestones and progress
        List<RecoveryMilestone> ms = recoveryService.getMilestones(id);
        List<RecoveryProgress> pr = recoveryService.getProgress(id);
        DefaultTableModel mMil = (DefaultTableModel) tableMilestones.getModel();
        mMil.setRowCount(0);

        for (RecoveryMilestone mm : ms) {
            // find status from progress list (match by weekRange + course)
            String status = "Not Started";
            for (RecoveryProgress p : pr) {
                if (p.getWeekRange().equals(mm.getWeekRange()) && p.getCourseCode().equals(mm.getCourseCode())) {
                    status = p.isCompleted() ? "Completed" : "In Progress";
                    break;
                }
            }
            mMil.addRow(new Object[]{mm.getCourseCode(), mm.getWeekRange(), mm.getTask(), status});
        }
    }

    private void goBack() {
        this.dispose();
    }

    // --------- Recommendations CRUD ----------
    private void addRecommendation() {
        String id = txtStudentId.getText().trim();
        if (!validateStudentIdPresent(id)) return;

        String recText = txtRecField.getText().trim();
        if (recText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a recommendation.");
            return;
        }

        String courseCode = getSelectedCourseCodeOrAsk();
        if (courseCode == null) return;

        recoveryService.addRecommendation(id, courseCode, recText);
        JOptionPane.showMessageDialog(this, "Email sent successfully!");
        // reload table
        loadStudentData();
        txtRecField.setText("");
    }

    private void updateRecommendation() {
        String id = txtStudentId.getText().trim();
        if (!validateStudentIdPresent(id)) return;

        int row = tableRec.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a recommendation row to update.");
            return;
        }
        String courseCode = (String) tableRec.getValueAt(row, 0);
        String newText = txtRecField.getText().trim();
        if (newText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter new recommendation text.");
            return;
        }
        // replace (service removes by student+course and re-adds)
        recoveryService.updateRecommendation(id, courseCode, newText);
        JOptionPane.showMessageDialog(this, "Email sent successfully!");
        loadStudentData();
        txtRecField.setText("");
    }

    private void deleteRecommendation() {
        String id = txtStudentId.getText().trim();
        if (!validateStudentIdPresent(id)) return;

        int row = tableRec.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a recommendation row to delete.");
            return;
        }

        String courseCode = (String) tableRec.getValueAt(row, 0);
        recoveryService.removeRecommendation(id, courseCode);
        JOptionPane.showMessageDialog(this, "Email sent successfully!");
        loadStudentData();
    }

    // --------- Milestones CRUD ----------
    private void addMilestone() {
        String id = txtStudentId.getText().trim();
        if (!validateStudentIdPresent(id)) return;

        String week = txtWeek.getText().trim();
        String task = txtTask.getText().trim();
        if (week.isEmpty() || task.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter week and task.");
            return;
        }

        String courseCode = getSelectedCourseCodeOrAsk();
        if (courseCode == null) return;

        recoveryService.addMilestone(id, courseCode, week, task);
        JOptionPane.showMessageDialog(this, "Email sent successfully!");
        loadStudentData();
        txtWeek.setText("");
        txtTask.setText("");
    }

    private void updateMilestone() {
        String id = txtStudentId.getText().trim();
        if (!validateStudentIdPresent(id)) return;

        int row = tableMilestones.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a milestone row to update.");
            return;
        }

        String courseCode = (String) tableMilestones.getValueAt(row, 0);
        String week = txtWeek.getText().trim();
        String task = txtTask.getText().trim();
        String status = (String) comboStatus.getSelectedItem();

        if (week.isEmpty() || task.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter week and task.");
            return;
        }

        // remove old milestone by student + week
        recoveryService.removeMilestone(id, (String) tableMilestones.getValueAt(row, 1));
        // add new milestone with updated values
        recoveryService.addMilestone(id, courseCode, week, task);

        // update progress boolean if status == Completed
        boolean completed = "Completed".equalsIgnoreCase(status);
        recoveryService.updateProgress(id, courseCode, week, completed);
        JOptionPane.showMessageDialog(this, "Email sent successfully!");

        loadStudentData();
        txtWeek.setText("");
        txtTask.setText("");
    }

    private void deleteMilestone() {
        String id = txtStudentId.getText().trim();
        if (!validateStudentIdPresent(id)) return;

        int row = tableMilestones.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a milestone row to delete.");
            return;
        }

        String week = (String) tableMilestones.getValueAt(row, 1);
        recoveryService.removeMilestone(id, week);
        JOptionPane.showMessageDialog(this, "Email sent successfully!");
        loadStudentData();
    }

    // ---------------- Helper methods ----------------

    private boolean validateStudentIdPresent(String id) {
        if (!id.matches("S\\d{3}")) {
            JOptionPane.showMessageDialog(this, "Invalid Student ID format. Use SXXX.");
            return false;
        }
        return true;
    }

    /**
     * Try to get a course code from selected failed-components row.
     * If none selected, prompt the user to type the course code.
     */
    private String getSelectedCourseCodeOrAsk() {
        int sel = tableFailed.getSelectedRow();
        if (sel != -1) {
            return (String) tableFailed.getValueAt(sel, 0);
        } else {
            String course = JOptionPane.showInputDialog(this, "Enter Course Code (e.g. C204):");
            if (course == null || course.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Course code required.");
                return null;
            }
            return course.trim();
        }
    }

    // ================= VARIABLES =================

    private JButton btnAddMil;
    private JButton btnAddRec;
    private JButton btnBack;
    private JButton btnDeleteMil;
    private JButton btnDeleteRec;
    private JButton btnLoad;
    private JButton btnUpdateMil;
    private JButton btnUpdateRec;
    private JComboBox<String> comboStatus;
    private JLabel lblRec;
    private JLabel lblStatus;
    private JLabel lblStudentId;
    private JLabel lblTask;
    private JLabel lblWeek;
    private JScrollPane jScrollMil;
    private JScrollPane jScrollRec;
    private JScrollPane jScrollFail;
    private JPanel panelFailed;
    private JPanel panelMilestone;
    private JPanel panelRecommend;
    private JTabbedPane tabPanel;
    private JTable tableFailed;
    private JTable tableMilestones;
    private JTable tableRec;
    private JTextField txtRecField;
    private JTextField txtStudentId;
    private JTextField txtTask;
    private JTextField txtWeek;

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        if (parentWindow != null){
            parentWindow.setVisible(true);
        }
        this.dispose();
    }
}