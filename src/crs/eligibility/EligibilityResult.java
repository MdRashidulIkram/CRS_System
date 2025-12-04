// Author: Hameem Arian
// TP: TP084996
// Feature: Eligibility Check & Enrollment

package crs.eligibility;

/**
 * Represents the decision for a single enrollment request.
 * Implemented by Hameem Arian (TP084996).
 */
public record EligibilityResult(
        String studentId,
        String studentName,
        String courseId,
        String courseName,
        String term,
        String decision,
        String reason,
        String notes
) {
}
