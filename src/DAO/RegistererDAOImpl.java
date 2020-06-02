package DAO;

import Beans.Registerer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistererDAOImpl implements RegistererDAO {
    @Override
    public void insert(Registerer professor) throws Exception {

    }

    @Override
    public void update(Registerer professor) throws Exception {

    }

    @Override
    public Registerer findById(String r_id) throws Exception {
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Registerer registerer=new Registerer();
        String sql="select * from registerer_info where r_id=?";
        try {
            conn= DruidManager.getConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,r_id);
            rs=ps.executeQuery();
            while(rs.next()){
                registerer.setR_id(rs.getString("r_id"));
                registerer.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DruidManager.close(conn,ps,rs);
        }
        return registerer;
    }
}
