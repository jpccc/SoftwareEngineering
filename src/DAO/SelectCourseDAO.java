package DAO;

import java.util.List;

import Beans.Course;
import Beans.CourseSelection;

public interface SelectCourseDAO {
	public List<CourseSelection> get_schedule(String s_id);
	public void add_course_selection(CourseSelection course_selection) throws Exception;
	public void delete_course_selection(String course_id,int reg_id,String student_id) throws Exception;
	public List<Course> get_all_courses();
	public List<Course> check_course(String course_id);
	public Course check_course(String course_id,int reg_id);
	public String no_conflict(List<CourseSelection> schedule,CourseSelection course_selection);
	public String satisfy_prerequire(List<CourseSelection> schedule,CourseSelection course_selection);
	public Course check_course_from_selection(CourseSelection course_selection);
	public String get_post_course_id(String course_id);
	public void add_student_num(String course_id,int reg_id);
	public void dec_student_num(String course_id,int reg_id);
}
