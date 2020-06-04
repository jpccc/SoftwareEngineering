package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Beans.Course;
import Beans.CourseSelection;
public class SelectCourseDAOImpl implements SelectCourseDAO{

	@Override
	public List<CourseSelection> get_schedule(String s_id) {
		// TODO Auto-generated method stub
		List<CourseSelection> res=new ArrayList<>();
		Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="select * from selection where student_id=?";
        try {
        	conn=JDBCUtil.getMysqlConnection();
    		System.out.println("Success connect Mysql server!");
			ps=conn.prepareStatement(sql);
	        ps.setString(1,s_id);
	        rs=ps.executeQuery();
	        while(rs.next()){
                CourseSelection course_selection=new CourseSelection();
                if(rs.getObject("student_id")!=null) {
                	course_selection.set_student_id(rs.getString("student_id"));
                	course_selection.set_course_id(rs.getString("course_id"));
                	course_selection.set_reg_id(rs.getInt("reg_id"));
                	course_selection.set_grade(rs.getString("grade"));
                	course_selection.set_select_status(rs.getString("select_status"));
                	course_selection.set_grade_status(rs.getString("grade_status"));
                	res.add(course_selection);
                }
            }
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
            try {
                DruidManager.close(conn,ps,rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}
        return res;
	}
	
	
	
	@Override
	public void add_course_selection(CourseSelection course_selection) throws Exception {
		// TODO Auto-generated method stub
		String sql = "insert into selection values(?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = null;
        Connection conn = null;
        	System.out.println("hahaha");
        	conn=JDBCUtil.getMysqlConnection();
        	String closeForeignKey = "SET @ORIG_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0";
        	Statement stmt = conn.createStatement();
    		Integer close = stmt.executeUpdate(closeForeignKey);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, course_selection.get_selection_id());
            preparedStatement.setString(2, course_selection.get_student_id());
            preparedStatement.setString(3, course_selection.get_course_id());
            preparedStatement.setInt(4, course_selection.get_reg_id());
            preparedStatement.setString(5,course_selection.get_grade());
            preparedStatement.setString(6, course_selection.get_select_status());
            preparedStatement.setString(7,course_selection.get_grade_status());
            preparedStatement.executeUpdate();
            
        
           
       
	}

	@Override
	public void delete_course_selection(String course_id,int reg_id) throws Exception {
		// TODO Auto-generated method stub
		String sql = "delete from selection where student_id=? and course_id=?";
        PreparedStatement ps = null;
        Connection conn=null;
        try {
        	conn=JDBCUtil.getMysqlConnection();
        	//Class.forName("com.mysql.jdbc.Driver");
        	//conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/course_selection","root","root");//java这个空填写的是你自己设的密码
            ps = conn.prepareStatement(sql);
            ps.setString(1, "li");
            ps.setString(2, course_id);
            //ps.setString(2, reg_id);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
	}



	@Override
	public List<Course> get_all_courses() {
		List<Course> res=new ArrayList<>();
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="select * from course_info";
        try {
        	conn=JDBCUtil.getMysqlConnection();
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                Course course=new Course();
                if(rs.getObject("reg_id")!=null)course.setReg_id(rs.getInt("reg_id"));
                course.setCourse_id(rs.getString("course_id"));
                course.setDept_id(rs.getInt("dept_id"));
                course.setCourse_name(rs.getString("course_name"));
                course.setStart_date(rs.getDate("start_date"));
                course.setEnd_date(rs.getDate("end_date"));
                course.setWeekday(rs.getInt("weekday"));
                course.setTimeslot_id(rs.getInt("timeslot_id"));
                course.setProfessor_id(rs.getString("professor_id"));
                course.setStudent_count(rs.getInt("student_count"));
                course.setStatus(rs.getString("status"));
                res.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                DruidManager.close(conn,ps,rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return res;
	}



	@Override
	public List<Course> check_course(String course_id) {
		List<Course> res=new ArrayList<>();
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="select * from course_info where course_id=?";
        try {
        	conn=JDBCUtil.getMysqlConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,course_id);
            rs=ps.executeQuery();
            while(rs.next()){
                Course course=new Course();
                //-1
                //list --size=0;
                if(rs.getObject("reg_id")!=null)course.setReg_id(rs.getInt("reg_id"));
                course.setCourse_id(rs.getString("course_id"));
                course.setDept_id(rs.getInt("dept_id"));
                course.setCourse_name(rs.getString("course_name"));
                course.setStart_date(rs.getDate("start_date"));
                course.setEnd_date(rs.getDate("end_date"));
                course.setWeekday(rs.getInt("weekday"));
                course.setTimeslot_id(rs.getInt("timeslot_id"));
                course.setProfessor_id(rs.getString("professor_id"));
                course.setStudent_count(rs.getInt("student_count"));
                course.setStatus(rs.getString("status"));
                res.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                DruidManager.close(conn,ps,rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return res;
	}



	



	@Override
	public Course check_course(String course_id, int reg_id) {
		Course res=new Course();
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="select * from course_info where course_id=? and reg_id=?";
        try {
        	conn=JDBCUtil.getMysqlConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,course_id);
            ps.setInt(2, reg_id);
            rs=ps.executeQuery();
            while(rs.next()){
                Course course=new Course();
                course.setReg_id(rs.getInt("reg_id"));
                course.setCourse_id(rs.getString("course_id"));
                course.setDept_id(rs.getInt("dept_id"));
                course.setCourse_name(rs.getString("course_name"));
                course.setStart_date(rs.getDate("start_date"));
                course.setEnd_date(rs.getDate("end_date"));
                course.setWeekday(rs.getInt("weekday"));
                course.setTimeslot_id(rs.getInt("timeslot_id"));
                course.setProfessor_id(rs.getString("professor_id"));
                course.setStudent_count(rs.getInt("student_count"));
                course.setStatus(rs.getString("status"));
                res=course;
                System.out.println(course.getCourse_id());
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                DruidManager.close(conn,ps,rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println(res.getCourse_id());
        return res;
	}



	@Override
	public String no_conflict(List<CourseSelection> schedule, CourseSelection course_selection) {
		int []a=new int[100];
		int scan=0;
		int future_time_slot=check_course(course_selection.get_course_id(),course_selection.get_reg_id()).getTimeslot_id();
		for (int i = 0; i < schedule.size(); i++) {
			String course_id=schedule.get(i).get_course_id();
			int reg_id=schedule.get(i).get_reg_id();
			Course course=check_course(course_id,reg_id);
			if(course.getCourse_id()!=null) {
				if(course.getTimeslot_id()==future_time_slot) {
					return "no";
				}
			}
		}
		return "yes";
	}



	@Override
	public String satisfy_prerequire(CourseSelection course_selection) {
		// TODO Auto-generated method stub
		return null;
	}
}
