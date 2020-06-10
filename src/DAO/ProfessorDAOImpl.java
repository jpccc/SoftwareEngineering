package DAO;

import Beans.Professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAOImpl implements ProfessorDAO {
    @Override
    public void insert(Professor professor) throws Exception {
        String sql = "insert into professor_info values(?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = null;
        Connection conn = null;
        try {
            conn = JDBCUtil.getMysqlConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, professor.getP_id());
            preparedStatement.setString(2, professor.getP_name());
            preparedStatement.setDate(3, professor.getBirthday());
            preparedStatement.setString(4,professor.getIdentify_num());
            preparedStatement.setString(5, professor.getStatus());
            preparedStatement.setInt(6,professor.getDept_id());
            preparedStatement.setString(7,professor.getPassword());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            throw new Exception("insert professor操作出现异常");
        } finally {
            if(conn!=null) {
                conn.close();
            }
        }
    }

    @Override
    public void update(Professor professor) throws Exception {
        String sql = "update professor_info set p_name=?,birthday=?,identify_num=?,status=?,dept_id=?,password=? where p_id=?";
        PreparedStatement preparedStatement = null;
        Connection conn = null;
        try {
            conn = JDBCUtil.getMysqlConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, professor.getP_name());
            preparedStatement.setDate(2, professor.getBirthday());
            preparedStatement.setString(3,professor.getIdentify_num());
            preparedStatement.setString(4, professor.getStatus());
            preparedStatement.setInt(5,professor.getDept_id());
            preparedStatement.setString(6,professor.getPassword());
            preparedStatement.setString(7, professor.getP_id());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            throw new Exception("update professor操作出现异常");
        } finally {
            if(conn!=null) {
                conn.close();
            }
        }
    }

    @Override
    public void delete(String id) throws Exception {
        String sql = "delete from professor_info where p_id=?";
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
            conn.close();
        }
    }

    @Override
    public List<Professor> findByName(String name) throws Exception {
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
                professor.setIdentify_num(rSet.getString(4));
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
    public Professor findById(String id) throws Exception {
        String sql = "select * from professor_info where p_id=?";
        PreparedStatement ps = null;
        Connection conn=null;
        Professor professor = new Professor();
        try {
            conn=JDBCUtil.getMysqlConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,id);
            ResultSet rSet = ps.executeQuery();
            if (rSet.next()) {
                professor.setP_id(rSet.getString(1));
                professor.setP_name(rSet.getString(2));
                professor.setBirthday(rSet.getDate(3));
                professor.setIdentify_num(rSet.getString(4));
                professor.setStatus(rSet.getString(5));
                professor.setDept_id(rSet.getInt(6));
                professor.setPassword(rSet.getString(7));
            }
            else{
                professor.setP_id("null");
            }
            rSet.close();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn!=null)conn.close();
        }
        return professor;
    }

    @Override
    public List<Professor> showAll() throws Exception {
        List<Professor> all = new ArrayList<Professor>();
        String sql = "select * from professor_info";
        PreparedStatement ps = null;
        Connection conn=null;
        try {
            conn=JDBCUtil.getMysqlConnection();
            ps = conn.prepareStatement(sql);
            ResultSet rSet = ps.executeQuery();
            while (rSet.next()) {
                Professor professor = new Professor();
                professor.setP_id(rSet.getString(1));
                professor.setP_name(rSet.getString(2));
                professor.setBirthday(rSet.getDate(3));
                professor.setIdentify_num(rSet.getString(4));
                professor.setStatus(rSet.getString(5));
                professor.setDept_id(rSet.getInt(6));
                professor.setPassword(rSet.getString(7));
                all.add(professor);
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
