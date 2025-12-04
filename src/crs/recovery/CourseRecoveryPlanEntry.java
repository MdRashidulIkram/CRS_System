// Author: Sharjeel_TP084956
// File: CourseRecoveryPlanEntry.java
// Feature: Course Recovery Plan

package crs.recovery;

public record CourseRecoveryPlanEntry(String studentId,
                                      String studentName,
                                      String major,
                                      String year,
                                      String courseId,
                                      String courseName,
                                      int credits,
                                      String semester,
                                      String instructor,
                                      String originalGrade,
                                      String failureReason,
                                      String recommendedAction,
                                      String milestoneWeek,
                                      String milestoneTask,
                                      String status) {
}
