package DAO;

import Beans.Course;
import Beans.Grade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl implements CourseDAO{
    @Override
    public List<Course> findQualified(int dept_id, int reg_id) throws SQLException {
        List<Course> all = new ArrayList<Course>();
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="select * from course_info where dept_id=? and reg_id=? and professor_id='0'";
        try {
            conn=JDBCUtil.getMysqlConnection();
            ps=conn.prepareStatement(sql);
            ps.setInt(1,dept_id);
            ps.setInt(2,reg_id);
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
                all.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            conn.close();
        }
        return all;
    }

    @Override
    public List<Course> findSelected(String p_id,int reg_id) throws SQLException {
        List<Course> all = new ArrayList<Course>();
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="select * from course_info where professor_id=? and reg_id=?";
        try {
            conn=JDBCUtil.getMysqlConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,p_id);
            ps.setInt(2,reg_id);
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
                all.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            conn.close();
        }
        return all;
    }

    @Override
    public void update(Course course) throws Exception {
        String sql = "update course_info set reg_id=?,dept_id=?,course_name=?,start_date=?,end_date=?,weekday=?,timeslot_id=?,professor_id=?,student_count=?,status=?,price=? where course_id=?";
        PreparedStatement preparedStatement = null;
        Connection conn = null;
        try {
            conn = JDBCUtil.getMysqlConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,course.getReg_id());
            preparedStatement.setInt(2,course.getDept_id());
            preparedStatement.setString(3,course.getCourse_name());
            preparedStatement.setDate(4,course.getStart_date());
            preparedStatement.setDate(5,course.getEnd_date());
            preparedStatement.setInt(6,course.getWeekday());
            preparedStatement.setInt(7,course.getTimeslot_id());
            preparedStatement.setString(8,course.getProfessor_id());
            preparedStatement.setInt(9,course.getStudent_count());
            preparedStatement.setString(10,course.getStatus());
            preparedStatement.setFloat(11,course.getPrice());
            preparedStatement.setString(12,course.getCourse_id());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            throw new Exception("update course操作出现异常");
        } finally {
            if(conn!=null) {
                conn.close();
            }
        }
    }

    @Override
    public Course findById(String course_id) throws SQLException {
        String sql = "select * from course_info where course_id=?";
        PreparedStatement ps = null;
        Connection conn=null;
        Course course = new Course();
        try {
            conn=JDBCUtil.getMysqlConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,course_id);
            ResultSet rSet = ps.executeQuery();
            if (rSet.next()) {
                course.setReg_id(rSet.getInt(1));
                course.setCourse_id(rSet.getString(2));
                course.setDept_id(rSet.getInt(3));
                course.setCourse_name(rSet.getString(4));
                course.setStart_date(rSet.getDate(5));
                course.setEnd_date(rSet.getDate(6));
                course.setWeekday(rSet.getInt(7));
                course.setTimeslot_id(rSet.getInt(8));
                course.setProfessor_id(rSet.getString(9));
                course.setStudent_count(rSet.getInt(10));
                course.setStatus(rSet.getString(11));
                course.setPrice(rSet.getFloat("price"));
            }
            else{
                course.setCourse_id("-1");
            }
            rSet.close();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            conn.close();
        }
        return course;
    }

    @Override
    public Course findCourse(String course_id, int reg_id) {
        Course res=new Course();
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="select * from course_info where reg_id=? and course_id=?";
        try {
            conn=DruidManager.getConnection();
            ps=conn.prepareStatement(sql);
            ps.setInt(1,reg_id);
            ps.setString(2,course_id);
            rs=ps.executeQuery();
            if(rs.next()){
                res.setReg_id(rs.getInt("reg_id"));
                res.setCourse_id(rs.getString("course_id"));
                res.setDept_id(rs.getInt("dept_id"));
                res.setCourse_name(rs.getString("course_name"));
                res.setStart_date(rs.getDate("start_date"));
                res.setEnd_date(rs.getDate("end_date"));
                res.setWeekday(rs.getInt("weekday"));
                res.setTimeslot_id(rs.getInt("timeslot_id"));
                res.setProfessor_id(rs.getString("professor_id"));
                res.setStudent_count(rs.getInt("student_count"));
                res.setStatus(rs.getString("status"));
                res.setPrice(rs.getFloat("price"));
            }else{
                res.setCourse_id("null");
            }
        } catch (SQLException e) {
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
    public List<Grade> queryStudents4Course(int reg_id, String course_id) {
        List<Grade> res=new ArrayList<>();
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="select * from selection,student_info " +
                "where reg_id=? and course_id=? and selection.student_id = student_info.s_id";
        try {
            conn=DruidManager.getConnection();
            ps=conn.prepareStatement(sql);
            ps.setInt(1,reg_id);
            ps.setString(2,course_id);
            rs=ps.executeQuery();
            while(rs.next()){
                Grade grade=new Grade();
                grade.setReg_id(rs.getInt("reg_id"));
                grade.setStudent_id(rs.getString("student_id"));
                grade.setCourse_id(rs.getString("course_id"));
                grade.setGrade(rs.getString("grade"));
                grade.setStudent_name(rs.getString("s_name"));
                res.add(grade);
            }
        } catch (SQLException e) {
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
    public void saveGrades(List<Grade> grades) {
        Connection conn=null;
        PreparedStatement ps=null;
        String sql="update selection set grade=? where reg_id=? and course_id=? and student_id=?";
        try {
            conn=DruidManager.getConnection();
            ps=conn.prepareStatement(sql);
            for(Grade grade: grades){
                ps.setString(1,grade.getGrade());
                ps.setInt(2,grade.getReg_id());
                ps.setString(3,grade.getCourse_id());
                ps.setString(4,grade.getStudent_id());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                DruidManager.close(conn,ps,null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Course> findAll() {
        List<Course> res=new ArrayList<>();
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="select * from course_info";
        try {
            conn=DruidManager.getConnection();
            ps=conn.prepareStatement(sql);
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
                res.add(course);
            }
        } catch (SQLException e) {
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
    public List<Course> findAll(int flag) {
        List<Course> res=new ArrayList<>();
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="select * from course_info";
        try {
            conn=DruidManager.getConnection(DruidManager.OLDSYS_FLAG);
            if(conn==null)return res;
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                Course course=new Course();
                course.setCourse_id(rs.getString("course_id"));
                course.setDept_id(rs.getInt("dept_id"));
                course.setCourse_name(rs.getString("course_name"));
                course.setPrice(rs.getFloat("price"));
                res.add(course);
            }
        } catch (SQLException e) {
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
    public void insert(List<Course> cList) throws SQLException {
        List<Course> res=new ArrayList<>();
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="insert into course_info " +
                "(reg_id, course_id, dept_id, course_name, start_date, end_date, " +
                "weekday, timeslot_id,price) values (?,?,?,?,?,?,?,?,?)";
        conn=DruidManager.getConnection();
        ps=conn.prepareStatement(sql);
        for(Course course: cList){
            ps.setInt(1,course.getReg_id());
            ps.setString(2,course.getCourse_id());
            ps.setInt(3,course.getDept_id());
            ps.setString(4,course.getCourse_name());
            ps.setDate(5,course.getStart_date());
            ps.setDate(6,course.getEnd_date());
            ps.setInt(7,course.getWeekday());
            ps.setInt(8,course.getTimeslot_id());
            ps.setFloat(9,course.getPrice());
            ps.addBatch();
        }
        ps.executeBatch();
        DruidManager.close(conn,ps,rs);
    }

    @Override
    public void deleteByRegid(int reg_id) throws Exception {
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="delete from course_info where reg_id=?";
        conn=DruidManager.getConnection();
        ps=conn.prepareStatement(sql);
        ps.setInt(1,reg_id);
        ps.execute();
        DruidManager.close(conn,ps,rs);
    }

    @Override
    public List<Course> findTaught(String p_id, int reg_id){
        List<Course> res=new ArrayList<>();
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="select * from course_info where professor_id=? and reg_id=?";
        try {
            conn=DruidManager.getConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,p_id);
            ps.setInt(2,reg_id);
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
        } catch (SQLException e) {
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
}
