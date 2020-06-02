package DAO;

import Beans.Course;

import java.util.List;

public interface CourseDAO {
    public List<Course> findQualified(int dept_id, int timeslot_id);
    public List<Course> findSelected(String p_id);
    public List<Course> findTaught(String p_id,int reg_id);
}
