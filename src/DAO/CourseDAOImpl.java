package DAO;

import Beans.Course;
import Beans.Professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl implements CourseDAO{
    @Override
    public List<Course> findQualified(int dept_id, int timeslot_id) {
        List<Course> all = new ArrayList<Course>();
        String sql = "select * from course_info where p_name=?";
        PreparedStatement ps = null;
        Connection conn=null;
        try {
            conn=JDBCUtil.getMysqlConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1,name);
            ResultSet rSet = ps.executeQuery();
            while (rSet.next()) {
                Professor professor = new Professor();
                professor.setP_id(rSet.getString(1));
                professor.setP_name(rSet.getString(2));
                professor.setBirthday(rSet.getDate(3));
                professor.setIdentify_num(rSet.getInt(4));
                professor.setStatus(rSet.getString(5));
                professor.setDept_id(rSet.getInt(6));
                professor.setPassword(rSet.getString(7));
                all.add(professor);
            }
            rSet.close();
            ps.close();
        } catch (Exception e) {
            throw new Exception("findByName操作出现异常");
        } finally {
            conn.close();
        }
        return all;
    }

    @Override
    public List<Course> findSelected(String p_id) {
        List<Professor> all = new ArrayList<Professor>();
        String sql = "select * from professor_info where p_name=?";
        PreparedStatement ps = null;
        Connection conn=null;
        try {
            conn=JDBCUtil.getMysqlConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1,name);
            ResultSet rSet = ps.executeQuery();
            while (rSet.next()) {
                Professor professor = new Professor();
                professor.setP_id(rSet.getString(1));
                professor.setP_name(rSet.getString(2));
                professor.setBirthday(rSet.getDate(3));
                professor.setIdentify_num(rSet.getInt(4));
                professor.setStatus(rSet.getString(5));
                professor.setDept_id(rSet.getInt(6));
                professor.setPassword(rSet.getString(7));
                all.add(professor);
            }
            rSet.close();
            ps.close();
        } catch (Exception e) {
            throw new Exception("findByName操作出现异常");
        } finally {
            conn.close();
        }
        return all;
    }
}
