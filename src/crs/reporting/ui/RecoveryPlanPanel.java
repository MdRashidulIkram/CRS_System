// GUI integration for: Course Recovery Plan
// TP: TP084956

package crs.reporting.ui;

import crs.recovery.CourseRecoveryPlanGenerator;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * Simple panel that exposes the Course Recovery Plan generator via the GUI.
 *
 * GUI integration for: Course Recovery Plan
 * Implemented for: TP084956 (Sharjeel)
 */
public class RecoveryPlanPanel extends JPanel {

    private final JButton btnGenerate;
    private final JTextArea txtStatus;
    private final Path outputPath = Paths.get("data", "output", "course_recovery_plan.csv");

    public RecoveryPlanPanel() {
        super(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JLabel lbl = new JLabel("Generate Course Recovery Plan");
        add(lbl, BorderLayout.NORTH);

        btnGenerate = new JButton("Generate Recovery Plan");
        btnGenerate.addActionListener(this::onGenerate);

        txtStatus = new JTextArea();
        txtStatus.setEditable(false);
        txtStatus.setLineWrap(true);
        txtStatus.setWrapStyleWord(true);
        txtStatus.setPreferredSize(new Dimension(400, 200));

        JPanel center = new JPanel(new BorderLayout(4,4));
        center.add(btnGenerate, BorderLayout.NORTH);
        center.add(new JScrollPane(txtStatus), BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);
    }

    private void onGenerate(ActionEvent ev) {
        btnGenerate.setEnabled(false);
        txtStatus.setText("Running generator...\n");

        // Run generator off the EDT
        new Thread(() -> {
            try {
                // Call main; safe because CourseRecoveryPlanGenerator does not call System.exit
                CourseRecoveryPlanGenerator.main(new String[0]);

                // After generation, read output and report number of entries
                int entries = countOutputEntries();
                SwingUtilities.invokeLater(() -> txtStatus.append("Completed. Entries generated: " + entries + "\nOutput: " + outputPath.toAbsolutePath()));
            } catch (Throwable t) {
                SwingUtilities.invokeLater(() -> txtStatus.append("Failed: " + t.getMessage()));
            } finally {
                SwingUtilities.invokeLater(() -> btnGenerate.setEnabled(true));
            }
        }, "recovery-gen-thread").start();
    }

    private int countOutputEntries() {
        if (!Files.exists(outputPath)) return 0;
        try (BufferedReader r = Files.newBufferedReader(outputPath)) {
            int lines = 0;
            while (r.readLine() != null) lines++;
            return Math.max(0, lines - 1); // subtract header
        } catch (IOException e) {
            return 0;
        }
    }
}
