package com.lxb.Course;

import java.util.List;

public interface CourseDAO {
    public List<Course> findQualified(int dept_id,int timeslot_id)
}
