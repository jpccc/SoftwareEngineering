package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TSDAO {
	public TSDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
	public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/registration?characterEncoding=utf-8&useSSL=false&useUnicode=true&serverTimezone=UTC", "root",
                "root");
    }
	
	public boolean checkInProgress() {
		try (Connection c = getConnection(); Statement s = c.createStatement();) {
			   
            String sql = "select `status` from Registration where `reg_id`=0";
   
            ResultSet rs= s.executeQuery(sql);
            String result="";
            System.out.println("result:" + result);
            if(rs.next()) {
            	result=rs.getString(1);
            	System.out.println("result:" + result);
            }
            if(result.equals("InProgress")) {
            		return true;
            }
            else if(result.equals("notInProgress")){
            		return false;
            }else {
            	throw new SQLException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
	}
}
