package DAO;

import Beans.Course;
import Beans.Professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl implements CourseDAO{
    @Override
    public List<Course> findQualified(int dept_id, int timeslot_id) {
        List<Course> all = new ArrayList<Course>();

        return all;
    }

    @Override
    public List<Course> findSelected(String p_id) {
        List<Course> all = new ArrayList<>();

        return all;
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
