package DAO;
import Beans.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO{
	 @Override
	 public void insert(Student student) throws Exception {
	        String sql = "insert into student_info values(?,?,?,?,?,?,?,?)";
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
	            preparedStatement.setDate(7,student.getGraduate_date());
	            preparedStatement.setString(8,student.getPassword());
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
                "set s_name=?,birthday=?,identify_num=?,status=?,dept_id=?,graduate_date=?,password=? " +
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
            preparedStatement.setDate(7,student.getGraduate_date());
            preparedStatement.setString(8, student.getS_id());
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

    public List<Student> findByName(String name) throws Exception {
        List<Student> all = new ArrayList<Student>();
        String sql = "select * from student_info where s_name=?";
        PreparedStatement ps = null;
        Connection conn=null;
        try {
            conn=JDBCUtil.getMysqlConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1,name);
            ResultSet rSet = ps.executeQuery();
            while (rSet.next()) {
                Student professor = new Student();
                professor.setS_id(rSet.getString(1));
                professor.setS_name(rSet.getString(2));
                professor.setBirthday(rSet.getDate(3));
                professor.setIdentify_num(rSet.getString(4));
                professor.setStatus(rSet.getString(5));
                professor.setDept_id(rSet.getInt(6));
                professor.setGraduate_date(rSet.getDate(7));
                professor.setPassword(rSet.getString(8));
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
    public List<Student> queryAll() throws Exception {
        return null;
    }
    @Override
    public List<Student> showAll() throws Exception {
        List<Student> all = new ArrayList<Student>();
        String sql = "select * from student_info";
        PreparedStatement ps = null;
        Connection conn=null;
        try {
            conn=JDBCUtil.getMysqlConnection();
            ps = conn.prepareStatement(sql);
            ResultSet rSet = ps.executeQuery();
            while (rSet.next()) {
                Student student = new Student();
                student.setS_id(rSet.getString(1));
                student.setS_name(rSet.getString(2));
                student.setBirthday(rSet.getDate(3));
                student.setIdentify_num(rSet.getString(4));
                student.setStatus(rSet.getString(5));
                student.setDept_id(rSet.getInt(6));
                student.setGraduate_date(rSet.getDate(7));
                student.setPassword(rSet.getString(8));
                all.add(student);
            }
            rSet.close();
            ps.close();
        } catch (Exception e) {
            throw new Exception("findAll操作出现异常");
        } finally {
            if(conn==null)conn.close();
        }
        return all;
    }
}
