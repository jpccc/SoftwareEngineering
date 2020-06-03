package DAO;

import Beans.Registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationDAOImpl implements RegistrationDAO {
    @Override
    public Registration queryByTime(int year, String semester) {
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Registration registration=new Registration();
        String sql="select * from registration where year=? and semester=?";
        try {
            conn=DruidManager.getConnection();
            ps=conn.prepareStatement(sql);
            ps.setInt(1,year);
            ps.setString(2,semester);
            rs=ps.executeQuery();
            while(rs.next()){
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
            while(rs.next()){
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
}
