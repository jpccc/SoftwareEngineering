package DAO;

import Beans.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StudentDAOImpl implements StudentDAO{
	 @Override
	 public void insert(Student student) throws Exception {
	        String sql = "insert into student_info values(?,?,?,?,?,?,?)";
	        PreparedStatement preparedStatement = null;
	        Connection conn = null;
	        try {
	            conn = DAO.DruidManager.getConnection();
	            preparedStatement = conn.prepareStatement(sql);
	            preparedStatement.setString(1, student.getS_id());
	            preparedStatement.setString(2, student.getS_name());
	            preparedStatement.setDate(3, student.getBirthday());
	            preparedStatement.setString(4,student.getIdentify_num());
	            preparedStatement.setString(5, student.getStatus());
	            preparedStatement.setInt(6,student.getDept_id());
	            preparedStatement.setString(7,student.getPassword());
	            preparedStatement.executeUpdate();
	            preparedStatement.close();
	        } catch (Exception e) {
	            throw new Exception("insert professor操作出现异常");
	        } finally {
	            DAO.DruidManager.close(conn,preparedStatement,null);
	        }
	    }


    @Override
    public void update(Student student) throws Exception {
        String sql = "update student_info " +
                "set s_name=?,birthday=?,identify_num=?,status=?,dept_id=?,password=? " +
                "where s_id=?";
        PreparedStatement preparedStatement = null;
        Connection conn = null;
        try {
            conn = DAO.DruidManager.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, student.getS_name());
            preparedStatement.setDate(2, student.getBirthday());
            preparedStatement.setString(3,student.getIdentify_num());
            preparedStatement.setString(4, student.getStatus());
            preparedStatement.setInt(5,student.getDept_id());
            preparedStatement.setString(6,student.getPassword());
            preparedStatement.setString(7, student.getS_id());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            throw new Exception("update student操作出现异常");
        } finally {
            DAO.DruidManager.close(conn,preparedStatement,null);
        }
    }


    @Override
    public void delete(String id) throws Exception {
        String sql = "delete from student_info where s_id=?";
        PreparedStatement ps = null;
        Connection conn=null;
        try {
            conn=JDBCUtil.getMysqlConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DAO.DruidManager.close(conn,ps,null);
        }
    }

    @Override
    public Student findById(String s_id) throws Exception {
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Student student= new Student();
        String sql="select * from student_info where s_id=?";
        try {
            conn= DruidManager.getConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,s_id);
            rs=ps.executeQuery();
            if(rs.next()){
                student.setS_id(rs.getString("s_id"));
                student.setS_name(rs.getString("s_name"));
                student.setBirthday(rs.getDate("birthday"));
                student.setIdentify_num(rs.getString("identify_num"));
                student.setStatus(rs.getString("status"));
                student.setDept_id(rs.getInt("dept_id"));
                student.setGraduate_date(rs.getDate("graduate_date"));
                student.setPassword(rs.getString("password"));
            }else{
                student.setS_id("null");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DruidManager.close(conn,ps,rs);
        }
        return student;

    }

    @Override
    public List<Student> queryAll() throws Exception {
        return null;
    }
}
