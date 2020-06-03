package DAO;

import java.util.List;

import Beans.Course;
import Beans.CourseSelection;

public interface SelectCourseDAO {
	public List<CourseSelection> get_schedule(String s_id);
	public void add_course_selection(CourseSelection course_selection) throws Exception;
	public void delete_course_selection(String course_id,int reg_id) throws Exception;
	public List<Course> get_all_courses();
	public List<Course> check_course(String course_id);
}
