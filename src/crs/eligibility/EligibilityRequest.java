// Author: Hameem Arian
// TP: TP084996
// Feature: Eligibility Check & Enrollment

package crs.eligibility;

/**
 * Represents a single enrollment request for eligibility checking.
 * Implemented by Hameem Arian (TP084996).
 */
public record EligibilityRequest(
        String studentId,
        String courseId,
        String term,
        String requestType
) {
}
