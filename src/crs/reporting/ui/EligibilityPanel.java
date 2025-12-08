// GUI integration for: Eligibility Check & Enrollment
// Author: Hameem Arian
// TP: TP084996

package crs.reporting.ui;

import crs.eligibility.EligibilityProcessor;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * Panel that exposes the Eligibility Check & Enrollment pipeline via GUI.
 *
 * Purpose: integrate the console-based eligibility processor into the GUI
 * so administrators may run eligibility checks and view a concise summary.
 *
 * Implemented by Hameem Arian (TP084996).
 */
public final class EligibilityPanel extends JPanel {

    private final JButton btnRun;
    private final JTextArea txtStatus;
    private final Path outputPath = Paths.get("data", "output", "enrollment_decisions.csv");

    public EligibilityPanel() {
        super(new BorderLayout(8,8));
        setBorder(BorderFactory.createEmptyBorder(8,8,8,8));

        JLabel title = new JLabel("Eligibility Check & Enrollment");
        add(title, BorderLayout.NORTH);

        btnRun = new JButton("Run Eligibility Check");
        btnRun.addActionListener(this::onRun);

        txtStatus = new JTextArea();
        txtStatus.setEditable(false);
        txtStatus.setLineWrap(true);
        txtStatus.setWrapStyleWord(true);
        txtStatus.setPreferredSize(new Dimension(450, 240));

        JPanel center = new JPanel(new BorderLayout(4,4));
        center.add(btnRun, BorderLayout.NORTH);
        center.add(new JScrollPane(txtStatus), BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);
    }

    private void onRun(ActionEvent ev) {
        btnRun.setEnabled(false);
        appendStatus("Starting eligibility processor...\n");

        new Thread(() -> {
            try {
                EligibilityProcessor.main(new String[0]);

                // After run, parse output
                ResultSummary summary = parseOutput();
                SwingUtilities.invokeLater(() -> {
                    appendStatus(String.format("Processed %d requests. Approved: %d, Rejected: %d.\nOutput: %s\n",
                            summary.total, summary.approved, summary.rejected, outputPath.toAbsolutePath().toString()));

                    if (!summary.sample.isEmpty()) {
                        appendStatus("Sample rows:\n");
                        for (String row : summary.sample) appendStatus(row + "\n");
                    }
                });
            } catch (Throwable t) {
                SwingUtilities.invokeLater(() -> appendStatus("Eligibility run failed: " + t.getMessage() + "\n"));
            } finally {
                SwingUtilities.invokeLater(() -> btnRun.setEnabled(true));
            }
        }, "eligibility-run-thread").start();
    }

    private void appendStatus(String s) {
        txtStatus.append(s);
        txtStatus.setCaretPosition(txtStatus.getDocument().getLength());
    }

    private ResultSummary parseOutput() {
        ResultSummary r = new ResultSummary();
        if (!Files.exists(outputPath)) return r;
        try (BufferedReader br = Files.newBufferedReader(outputPath)) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null) {
                r.total++;
                String[] cols = line.split(",");
                if (cols.length >= 6) {
                    String decision = cols[5].trim();
                    if ("APPROVED".equalsIgnoreCase(decision)) r.approved++;
                    else if ("REJECTED".equalsIgnoreCase(decision)) r.rejected++;
                }
                if (r.sample.size() < 5) r.sample.add(line);
            }
        } catch (IOException e) {
            // ignore and return what we have
        }
        return r;
    }

    private static final class ResultSummary {
        int total = 0;
        int approved = 0;
        int rejected = 0;
        List<String> sample = new ArrayList<>();
    }
}
