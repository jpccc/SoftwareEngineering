package Beans;

public class CourseSelection {
	public CourseSelection() {
		selection_id=0;
		student_id=null;
		course_id=null;
		reg_id=0;
		grade=null;
		select_status="primary";
		grade_status="saved";
	}
	private int selection_id;
	private String student_id;
	private String course_id;
	private int reg_id;
	private String grade;
	private String select_status;
	private String grade_status;
	
	public void set_selection_id(int selection_id) {
		this.selection_id=selection_id;
	}
	public int get_selection_id() {
		return selection_id;
	}
	public void set_student_id(String student_id) {
		this.student_id=student_id;
	}
	public String get_student_id() {
		return student_id;
	}
	public void set_course_id(String course_id) {
		this.course_id=course_id;
	}
	public String get_course_id() {
		return course_id;
	}
	public void set_reg_id(int reg_id) {
		this.reg_id=reg_id;
	}
	public int get_reg_id() {
		return reg_id;
	}
	public void set_select_status(String select_status) {
		this.select_status=select_status;
	}
	public String get_select_status() {
		return select_status;
	}
	public void set_grade_status(String grade_status) {
		this.grade_status=grade_status;
	}
	public String get_grade_status() {
		return grade_status;
	}
	public void set_grade(String grade) {
		this.grade=grade;
	}
	public String get_grade() {
		return grade;
	}
}
