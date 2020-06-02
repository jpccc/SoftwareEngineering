package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CloseRigisDAO {
	public CloseRigisDAO() {
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
	public boolean commitCourses() {
		try (Connection c = getConnection(); Statement s = c.createStatement();) {	
            String sql = "update course_info set status='commited' where professor_id is not null and student_count>=3;";
            int result=s.executeUpdate(sql);           
            System.out.println("result:commit count=" + result);
            
            sql="where selection.course_id=course_info.course_id \r\n" + 
            		"and select_status='primary'\r\n" + 
            		"and course_info.professor_id is not null\r\n" + 
            		"and course_info.student_count>=3;";
            result=s.executeUpdate(sql);           
            System.out.println("result:commit in schedule count=" + result);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
	}
}
