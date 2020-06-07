package DAO;

import Beans.Registrar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistererDAOImpl implements RegistererDAO {
    @Override
    public void insert(Registrar professor) throws Exception {

    }

    @Override
    public void update(Registrar professor) throws Exception {

    }

    @Override
    public Registrar findById(String r_id) throws Exception {
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Registrar registerer=new Registrar();
        String sql="select * from registerer_info where r_id=?";
        try {
            conn= DruidManager.getConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,r_id);
            rs=ps.executeQuery();
            if(rs.next()){
                registerer.setR_id(rs.getString("r_id"));
                registerer.setPassword(rs.getString("password"));
            }else{
                registerer.setR_id("null");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DruidManager.close(conn,ps,rs);
        }
        return registerer;
    }
}
