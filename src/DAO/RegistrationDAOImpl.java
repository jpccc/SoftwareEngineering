package DAO;

import Beans.Registration;

import java.sql.*;

public class RegistrationDAOImpl implements RegistrationDAO {
    @Override
    public Registration queryByTime(int year, String semester) {
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Registration registration=null;
        String sql="select * from registration where year=? and semester=?";
        try {
            conn=DruidManager.getConnection();
            ps=conn.prepareStatement(sql);
            ps.setInt(1,year);
            ps.setString(2,semester);
            rs=ps.executeQuery();
            while(rs.next()){
                registration=new Registration();
                registration.setReg_id(rs.getInt("reg_id"));
                registration.setStatus(rs.getString("status"));
                registration.setYear(rs.getInt("year"));
                registration.setSemester(rs.getString("semester"));
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
        return registration;
    }

    @Override
    public Registration queryLatest() {
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Registration registration=new Registration();
        String sql="select * from registration where reg_id=(select max(reg_id) from registration)";
        try {
            conn=DruidManager.getConnection();
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            if(rs.next()){
                registration.setReg_id(rs.getInt("reg_id"));
                registration.setStatus(rs.getString("status"));
                registration.setYear(rs.getInt("year"));
                registration.setSemester(rs.getString("semester"));
            }else{
                registration.setReg_id(-1);
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
        return registration;
    }

    @Override
    public int insert(Registration reg) {
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Registration registration=new Registration();
        String sql="insert into registration (status, year, semester) values (?,?,?)";
        int key=-1;
        try {
            conn=DruidManager.getConnection();
            ps=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,reg.getStatus());
            ps.setInt(2,reg.getYear());
            ps.setString(3,reg.getSemester());
            ps.execute();
            ResultSet re=ps.getGeneratedKeys();    //返回主键
            re.next();
            key=re.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                DruidManager.close(conn,ps,rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return key;
    }
}
