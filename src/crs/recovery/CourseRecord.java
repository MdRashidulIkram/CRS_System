// Author: Sharjeel_TP084956
// File: CourseRecord.java
// Feature: Course Recovery Plan

package crs.recovery;

public record CourseRecord(String courseId,
                           String courseName,
                           int credits,
                           String semester,
                           String instructor,
                           int examWeight,
                           int assignmentWeight) {
}
