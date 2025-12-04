// Author: Hameem Arian
// TP: TP084996
// Feature: Eligibility Check & Enrollment

package crs.eligibility;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Ensures eligibility-specific CSV resources and output directories exist.
 * Implemented by Hameem Arian (TP084996).
 *
 * This helper will create a placeholder `enrollment_requests.csv` under
 * `src/resources` if it is missing (header only) and ensure `data/output`
 * exists for writing results.
 */
public final class EligibilityEnvironmentHelper {

    private EligibilityEnvironmentHelper() {
        // utility
    }

    public static void runChecks() {
        try {
            Path resources = Paths.get("src", "resources");
            if (!Files.exists(resources)) {
                Files.createDirectories(resources);
            }

            Path enrollment = EligibilityCsvLoader.defaultEnrollmentRequestsPath();
            if (!Files.exists(enrollment)) {
                String header = "StudentID,CourseID,Term,RequestType" + System.lineSeparator();
                Files.write(enrollment, List.of(header), StandardCharsets.UTF_8);
                System.out.println("Created placeholder: " + enrollment.toAbsolutePath());
            }

            Path output = Paths.get("data", "output");
            if (!Files.exists(output)) {
                Files.createDirectories(output);
                System.out.println("Created output folder: " + output.toAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("EligibilityEnvironmentHelper: failed to ensure environment: " + e.getMessage());
        }
    }
}
