package DAO;

import Beans.Course;
import Beans.Grade;

import java.sql.SQLException;
import java.util.List;

public interface CourseDAO {
    public List<Course> findQualified(int dept_id, int reg_id) throws SQLException;
    public List<Course> findSelected(String p_id,int reg_id) throws SQLException;
    public List<Course> findTaught(String p_id,int reg_id);
    public void update(Course course) throws Exception;
    public Course findById(String course_id) throws SQLException;
    public Course findCourse(String course_id,int reg_id);
    public List<Grade> queryStudents4Course(int reg_id, String course_id);
    public void saveGrades(List<Grade> grades);
    public List<Course> findAll();
    public void insert(List<Course> cList) throws SQLException;
    public void deleteByRegid(int reg_id) throws Exception;
}
