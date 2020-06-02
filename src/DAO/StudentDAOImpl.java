package DAO;

import Beans.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StudentDAOImpl implements StudentDAO{
    StudentDAOImpl(){

    }

    @Override
    public void insert(Student student) throws Exception {

    }

    @Override
    public void update(Student student) throws Exception {

    }

    @Override
    public void delete(String s_id) throws Exception {

    }

    @Override
    public Student findById(String s_id) throws Exception {
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Student student=new Student();
        String sql="select * from student_info where s_id=?";
        try {
            conn= DruidManager.getConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,s_id);
            rs=ps.executeQuery();
            while(rs.next()){
                student.setS_id(rs.getString("s_id"));
                student.setS_name(rs.getString("s_name"));
                student.setBirthday(rs.getDate("birthday"));
                student.setIdentify_num(rs.getString("identify_num"));
                student.setStatus(rs.getString("status"));
                student.setDept_id(rs.getInt("dept_id"));
                student.setGraduate_date(rs.getDate("graduate_date"));
                student.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;

    }

    @Override
    public List<Student> queryAll() throws Exception {
        return null;
    }
}
