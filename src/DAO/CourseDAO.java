package DAO;

import Beans.Course;
import Beans.Professor;

import java.sql.SQLException;
import java.util.List;

public interface CourseDAO {
    public List<Course> findQualified(int dept_id, int reg_id) throws SQLException;
    public List<Course> findSelected(String p_id,int reg_id) throws SQLException;
    public List<Course> findTaught(String p_id,int reg_id);
    public void update(Course course) throws Exception;
    public Course findById(String course_id) throws SQLException;
}
