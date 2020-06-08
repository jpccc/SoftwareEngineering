package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Beans.Course;
import Beans.CourseSelection;
public class SelectCourseDAOImpl implements SelectCourseDAO{
	public static char[][] slot_falg=new char[8][7];
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
	public void delete_course_selection(String course_id,int reg_id,String student_id) throws Exception {
		// TODO Auto-generated method stub
		String sql = "delete from selection where student_id=? and course_id=?";
        PreparedStatement ps = null;
        Connection conn=null;
        try {
        	conn=JDBCUtil.getMysqlConnection();
        	//Class.forName("com.mysql.jdbc.Driver");
        	//conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/course_selection","root","root");
            ps = conn.prepareStatement(sql);
            ps.setString(1, student_id);
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


	public static int get(int num, int index)
    {
        return (num & (0x1 << index)) >> index;
    }
	
	@Override
	public String no_conflict(List<CourseSelection> schedule, CourseSelection course_selection) {
		System.out.println("Course Selection .course_id="+course_selection.get_course_id());
		String[][] schedule_flag=new String[8][7];
		for(int i=0;i<8;i++) {
			for(int j=0;j<7;j++){
				schedule_flag[i][j]="free";
			}
		}
		for (int i = 0; i < schedule.size(); i++) {
			Course course=check_course_from_selection(schedule.get(i));
			int weekday=course.getWeekday();
			int slot=course.getTimeslot_id();
			for(int m=0;m<7;m++) {
				if(get(weekday,m)==1) {
					for(int n=0;n<8;n++) {
						if(get(slot,n)==1) {
							schedule_flag[n][m]="buzy";
							System.out.println("time solt"+n+" "+m+" is buzy");
						}
					}
				}
			}
		}
		Course new_course=check_course(course_selection.get_course_id(),course_selection.get_reg_id());
		int new_weekday=new_course.getWeekday();
		int new_slot=new_course.getTimeslot_id();
		System.out.println("course_id: "+ new_course.getCourse_id()+" weekday: "+new_weekday +" slot: "+new_slot);
		for(int m=0;m<7;m++) {
			if(get(new_weekday,m)==1) {
				for(int n=0;n<8;n++) {
					if(get(new_slot,n)==1) {
						if(schedule_flag[n][m].equals("buzy")) {
							
							System.out.println("you can not add into slot "+n+" "+m+" !");
							return "no";
						}
					}
				}
			}
		}
		return "yes";
		/*
		int []a=new int[100];
		int scan=0;
		int future_time_slot=check_course(course_selection.get_course_id(),course_selection.get_reg_id()).getTimeslot_id();
		int future_weekday=check_course(course_selection.get_course_id(),course_selection.get_reg_id()).getWeekday();
		for (int i = 0; i < schedule.size(); i++) {
			String course_id=schedule.get(i).get_course_id();
			int reg_id=schedule.get(i).get_reg_id();
			Course course=check_course(course_id,reg_id);
			if(course.getCourse_id()!=null) {
				if(course.getWeekday()==future_weekday&& course.getTimeslot_id()==future_time_slot) {
					return "no";
				}
			}
		}
		return "yes";
		*/
		
	}







	@Override
	public String satisfy_prerequire(List<CourseSelection> schedule, CourseSelection course_selection) {
		Course res=new Course();
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="select prev_course_id from prev_require where post_course_id=?";
        try {
        	conn=JDBCUtil.getMysqlConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,course_selection.get_course_id());
            rs=ps.executeQuery();
            while(rs.next()){
            	int flag=0;
                String prev_course_id=rs.getString("prev_course_id");
                for(int i=0;i<schedule.size();i++) {
                	String selected_course_id=schedule.get(i).get_course_id();
                	if(selected_course_id.equals(prev_course_id)) {
                		flag=1;
                	}
                }
                if(flag==0) {
                	System.out.println("not finished prev_require courses!");
                	return "no";
                }
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
        System.out.println("finished pre_require courses");
        return "yes";
		
		
	}



	@Override
	public Course check_course_from_selection(CourseSelection course_selection) {
		Course res=new Course();
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="select course_info.* from course_info,selection where course_info.course_id=selection.course_id and course_info.reg_id=selection.reg_id and "
        		+ "selection.course_id=? and selection.reg_id=?";
        try {
        	conn=JDBCUtil.getMysqlConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,course_selection.get_course_id());
            ps.setInt(2, course_selection.get_reg_id());
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
	public String get_post_course_id(String course_id) {
		String res=null;
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="select post_course_id from prev_require where prev_course_id=?";
        try {
        	conn=JDBCUtil.getMysqlConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,course_id);
            rs=ps.executeQuery();
            while(rs.next()){
            	
                String post_course_id=rs.getString("post_course_id");
                res=post_course_id;
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
        System.out.println(res);
        return res;
	}



	@Override
	public void add_student_num(String course_id, int reg_id) {
		// TODO Auto-generated method stub
		String sql = "update course_info set student_count=student_count-1 where course_id=? and reg_id=?";
        PreparedStatement ps = null;
        Connection conn=null;
        try {
        	conn=JDBCUtil.getMysqlConnection();
        	//Class.forName("com.mysql.jdbc.Driver");
        	//conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/course_selection","root","root");//java�������д�������Լ��������
            ps = conn.prepareStatement(sql);
            ps.setString(1, course_id);
            ps.setInt(2, reg_id);
            //ps.setString(2, reg_id);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}



	@Override
	public void dec_student_num(String course_id, int reg_id) {
		// TODO Auto-generated method stub
		String sql = "update course_info set student_count=student_count-1 where course_id=? and reg_id=?";
        PreparedStatement ps = null;
        Connection conn=null;
        try {
        	conn=JDBCUtil.getMysqlConnection();
        	//Class.forName("com.mysql.jdbc.Driver");
        	//conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/course_selection","root","root");//java�������д�������Լ��������
            ps = conn.prepareStatement(sql);
            ps.setString(1, course_id);
            ps.setInt(2, reg_id);
            //ps.setString(2, reg_id);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		
	}
}
