package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Beans.Course;
import Beans.CourseSelection;
public class SelectCourseDAOImpl implements SelectCourseDAO{
    public static char[][] slot_falg=new char[8][7];
    @Override
    public List<CourseSelection> get_schedule(String s_id,int reg_id) {
        // TODO Auto-generated method stub
        List<CourseSelection> res=new ArrayList<>();
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="select * from selection where student_id=? and reg_id=?";
        try {
            conn=JDBCUtil.getMysqlConnection();
            System.out.println("Success connect Mysql server!");
            ps=conn.prepareStatement(sql);
            ps.setString(1,s_id);
            ps.setInt(2, reg_id);
            rs=ps.executeQuery();
            while(rs.next()){
                CourseSelection course_selection=new CourseSelection();
                if(rs.getObject("student_id")!=null) {
                    course_selection.set_student_id(rs.getString("student_id"));
                    course_selection.set_course_id(rs.getString("course_id"));
                    course_selection.set_reg_id(rs.getInt("reg_id"));
                    course_selection.set_grade(rs.getString("grade"));
                    course_selection.set_select_status(rs.getString("select_status"));
                    course_selection.setPrice(rs.getFloat("price"));
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
        preparedStatement.setFloat(6,course_selection.getPrice());
        preparedStatement.setString(7, course_selection.get_select_status());
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
    public List<Course> get_all_courses(int reg_id) {
        List<Course> res=new ArrayList<>();
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="select * from course_info where reg_id=? ";
        try {
            conn=JDBCUtil.getMysqlConnection();
            ps=conn.prepareStatement(sql);
            ps.setInt(1, reg_id);
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
                course.setPrice(rs.getFloat("price"));
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
    public List<Course> get_all_courses(int reg_id,String stu_id) {
        List<Course> res=new ArrayList<>();
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="select * from course_info where reg_id=? and course_id not in(select course_id from selection where student_id=? and reg_id=?)";
        try {
            conn=JDBCUtil.getMysqlConnection();
            ps=conn.prepareStatement(sql);
            ps.setInt(1, reg_id);
            ps.setString(2,stu_id);
            ps.setInt(3,reg_id);
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
                course.setPrice(rs.getFloat("price"));
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
                course.setPrice(rs.getFloat("price"));
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
                course.setPrice(rs.getFloat("price"));
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
        Course new_course=check_course(course_selection.get_course_id(),course_selection.get_reg_id());
        int new_weekday=new_course.getWeekday();
        int new_slot=new_course.getTimeslot_id();

        for (int i = 0; i < schedule.size(); i++) {
            Course course=check_course_from_selection(schedule.get(i));
            int weekday=course.getWeekday();
            int slot=course.getTimeslot_id();
            if(new_course.getStart_date().before(course.getEnd_date())&&
                    course.getStart_date().before(new_course.getEnd_date())){
                if( ( (~(new_weekday^weekday))&weekday )!=0x00000000 ) {
                    return "no";
                }
                if( ( (~(new_slot^slot))&slot )!=0x00000000 ) {
                    return "no";
                }
            }
        }

        return "yes";
    }







    @Override
    public String satisfy_prerequire(String student_id,int reg_id, CourseSelection course_selection) {
        Course res=new Course();
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="select pre_cid from prev_require where post_cid=?";
        try {
            conn=DruidManager.getConnection(DruidManager.OLDSYS_FLAG);
            ps=conn.prepareStatement(sql);
            ps.setString(1,course_selection.get_course_id());
            rs=ps.executeQuery();
            while(rs.next()){
                int flag=0;
                String prev_course_id=rs.getString("pre_cid");
                System.out.println("from select course servlet: prev_cid="+prev_course_id);
                Connection conn2=conn=JDBCUtil.getMysqlConnection();
                String sql2="select course_id from selection where reg_id<? and student_id=?";
                PreparedStatement ps2=conn2.prepareStatement(sql2);
                ps2.setInt(1, reg_id);
                ps2.setString(2, student_id);
                ResultSet rs2=ps2.executeQuery();
                while(rs2.next()) {
                	String c_id=rs2.getString("course_id");
                	if(c_id.equals(prev_course_id)) {
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
                course.setPrice(rs.getFloat("price"));
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
        String sql="select post_cid from prev_require where pre_cid=?";
        try {
            conn=JDBCUtil.getMysqlConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,course_id);
            rs=ps.executeQuery();
            while(rs.next()){

                String post_course_id=rs.getString("post_cid");
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
        String sql = "update course_info set student_count=student_count+1 where course_id=? and reg_id=?";
        PreparedStatement ps = null;
        Connection conn=null;
        try {
            conn=JDBCUtil.getMysqlConnection();
            //Class.forName("com.mysql.jdbc.Driver");
            //conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/course_selection","root","root");//java???????§Õ????????????????
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
            //conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/course_selection","root","root");//java???????§Õ????????????????
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
