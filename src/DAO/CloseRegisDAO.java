<<<<<<< HEAD
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Beans.Course;
import Beans.Schedule;
public class CloseRegisDAO {
	public CloseRegisDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
	public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/registration?characterEncoding=utf-8&useSSL=false&useUnicode=true&serverTimezone=UTC", "root",
                "123456");
    }
	public Map<String,Boolean> getCourseList(int reg_id) {
		try (Connection c = getConnection(); PreparedStatement s = c.prepareStatement("select course_id from course_info where reg_id= ? ;");) {	
			s.setInt(1, reg_id);
            ResultSet rs= s.executeQuery();        
            Map<String,Boolean> courseList=new HashMap<String,Boolean>();
            while(rs.next()) {
            	Course course=new Course();
            	course.setCourse_id(rs.getString(1));
            	courseList.put(course.getCourse_id(),false);
            }
            
            return courseList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return null;
	}
	public Map<String,Schedule> getScheduleList(int reg_id){
		try (Connection c = getConnection(); PreparedStatement s = c.prepareStatement("select student_id,course_id,select_status from selection where reg_id= ? ;");) {				   
            s.setInt(1, reg_id);
			ResultSet rs= s.executeQuery();        
            Map<String,Schedule> List=new HashMap<String,Schedule>();
            Map<String,ArrayList<String>> primaryList=new HashMap<String,ArrayList<String>>();
            Map<String,ArrayList<String>> alternateList=new HashMap<String,ArrayList<String>>();
            while(rs.next()) {
            	String student_id=rs.getString("student_id");
            	String course_id=rs.getString("course_id");
            	String select_status=rs.getString("select_status");
            	ArrayList<String> primary=primaryList.get(student_id);
            	ArrayList<String> alternate=alternateList.get(student_id);
            	if(primary==null) {
            		primary=new ArrayList<String>();
            		primaryList.put(student_id,primary);
            	}
            	if(alternate==null) {
            		alternate=new ArrayList<String>();
            		alternateList.put(student_id,alternate);
            	}
            	if(select_status.equals("primary")) {      			
            		primary.add(course_id);
            	}
            	if(select_status.equals("alternate")) {
            		alternate.add(course_id);
            	}
            }
            
            for(Map.Entry<String,ArrayList<String>> entry : primaryList.entrySet()) {
            	String student_id=entry.getKey();
            	if(List.get(student_id)==null) {
            		List.put(student_id, new Schedule(student_id,entry.getValue(),alternateList.get(student_id)));
            	}
            }
            return List;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return null;
	}
	public Map<Course,Integer> getBill(String student_id,int reg_id){
		try (Connection c = getConnection(); PreparedStatement s = c.prepareStatement("select course_id,price from selection where student_id= ? and reg_id= ? ;");) {				   
            s.setString(1, student_id);
            s.setInt(2, reg_id);
			ResultSet rs= s.executeQuery(); 
			Map<Course,Integer> bill=new HashMap<Course,Integer>();
            while(rs.next()) {
            	String course_id=rs.getString("course_id");
            	int cost=rs.getInt("price");
            	Course course=getCourse(course_id,reg_id);
            	bill.put(course, cost);
            }
            return bill;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return new HashMap<Course,Integer>();
	}
	public boolean haveTeacher(String course_id,int reg_id) {		
		try (Connection c = getConnection(); PreparedStatement s = c.prepareStatement("select professor_id from course_info where course_id= ? and reg_id= ? ;");) {
			s.setString(1, course_id); 
			s.setInt(2, reg_id);
            ResultSet rs= s.executeQuery();
            if(rs.next()) {
            	String result=rs.getString(1);
            	if(result!=null)
            		return true;
            	else
            		return false;
            
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
	}
	public Map<String,Integer> getStudentCountList(int reg_id) {
		try (Connection c = getConnection(); PreparedStatement s = c.prepareStatement("select course_id,student_count from course_info where reg_id= ? ;");) {			
            s.setInt(1, reg_id);
			ResultSet rs= s.executeQuery();        
            Map<String,Integer> List=new HashMap<String,Integer>();
            while(rs.next()) {
            	List.put(rs.getString(1), rs.getInt(2));
            }
            return List;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return null;
	}
	public Course getCourse(String course_id,int reg_id) {
		try (Connection c = getConnection(); PreparedStatement s = c.prepareStatement("select * from course_info where course_id= ? and reg_id= ? ;");) {	
			s.setString(1, course_id);
			s.setInt(2, reg_id);
			ResultSet rs= s.executeQuery(); 
			Course course=new Course();
			if(rs.next()) {
				course.setCourse_id(rs.getString("course_id"));
				course.setCourse_name(rs.getString("course_name"));
				course.setDept_id(rs.getInt("dept_id"));
				course.setEnd_date(rs.getDate("end_date"));
				course.setStart_date(rs.getDate("start_date"));
				course.setTimeslot_id(rs.getInt("timeslot_id"));
				course.setWeekday(rs.getInt("weekday"));
			}
            return course;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return null;
	}
	public boolean deleteCourse(String course_id,int reg_id) {
		try (Connection c = getConnection(); PreparedStatement s = c.prepareStatement("delete from course_info where course_id= ? and reg_id= ? ;");) {	
			s.setString(1, course_id);
            s.setInt(2, reg_id);
			int result=s.executeUpdate();           
            System.out.println("result:delete count=" + result);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
	}
	public boolean deleteSelection(String course_id,int reg_id) {
		try (Connection c = getConnection(); PreparedStatement s = c.prepareStatement("delete from selection where course_id= ? and reg_id= ? ;");) {	
            s.setString(1, course_id);
            s.setInt(2, reg_id);
            int result=s.executeUpdate();           
            System.out.println("result:delete count=" + result);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
	}
	public boolean closeRegistration(int reg_id) {
		try (Connection c = getConnection(); PreparedStatement s = c.prepareStatement("update registration set status='closed' where reg_id= ? ;");) {	
            s.setInt(1, reg_id);
            int result=s.executeUpdate();           
            System.out.println("result:delete count=" + result);
            if(result>0)
            	return true;
            else
            	return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
	}
	public int upgradeAlternate(int reg_id) {
		// TODO Auto-generated method stub
		try (Connection c = getConnection(); PreparedStatement s = c.prepareStatement("update selection set select_status='primary' where reg_id= ? and select_status='alternate';");) {	
            s.setInt(1, reg_id);
            int result=s.executeUpdate();           
            System.out.println("result:upgradeAlternate count=" + result);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return 0;
	}
}
=======
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Beans.Course;
import Beans.Schedule;
public class CloseRegisDAO {
	public CloseRegisDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
	public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/course_selection?characterEncoding=utf-8&useSSL=false&useUnicode=true&serverTimezone=UTC", "root",
                "010233");
    }
	public Map<String,Boolean> getCourseList(int reg_id) {
		try (Connection c = getConnection(); PreparedStatement s = c.prepareStatement("select course_id from course_info where reg_id= ? ;");) {	
			s.setInt(1, reg_id);
            ResultSet rs= s.executeQuery();        
            Map<String,Boolean> courseList=new HashMap<String,Boolean>();
            while(rs.next()) {
            	Course course=new Course();
            	course.setCourse_id(rs.getString(1));
            	courseList.put(course.getCourse_id(),false);
            }
            
            return courseList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return null;
	}
	public Map<String,Schedule> getScheduleList(int reg_id){
		try (Connection c = getConnection(); PreparedStatement s = c.prepareStatement("select student_id,course_id,select_status from selection where reg_id= ? ;");) {				   
            s.setInt(1, reg_id);
			ResultSet rs= s.executeQuery();        
            Map<String,Schedule> List=new HashMap<String,Schedule>();
            Map<String,ArrayList<String>> primaryList=new HashMap<String,ArrayList<String>>();
            Map<String,ArrayList<String>> alternateList=new HashMap<String,ArrayList<String>>();
            while(rs.next()) {
            	String student_id=rs.getString("student_id");
            	String course_id=rs.getString("course_id");
            	String select_status=rs.getString("select_status");
            	ArrayList<String> primary=primaryList.get(student_id);
            	ArrayList<String> alternate=alternateList.get(student_id);
            	if(primary==null) {
            		primary=new ArrayList<String>();
            		primaryList.put(student_id,primary);
            	}
            	if(alternate==null) {
            		alternate=new ArrayList<String>();
            		alternateList.put(student_id,alternate);
            	}
            	if(select_status.equals("primary")) {      			
            		primary.add(course_id);
            	}
            	if(select_status.equals("alternate")) {
            		alternate.add(course_id);
            	}
            }
            
            for(Map.Entry<String,ArrayList<String>> entry : primaryList.entrySet()) {
            	String student_id=entry.getKey();
            	if(List.get(student_id)==null) {
            		List.put(student_id, new Schedule(student_id,entry.getValue(),alternateList.get(student_id)));
            	}
            }
            return List;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return null;
	}
	public Map<Course,Integer> getBill(String student_id,int reg_id){
		try (Connection c = getConnection(); PreparedStatement s = c.prepareStatement("select course_id,price from selection where student_id= ? and reg_id= ? ;");) {				   
            s.setString(1, student_id);
            s.setInt(2, reg_id);
			ResultSet rs= s.executeQuery(); 
			Map<Course,Integer> bill=new HashMap<Course,Integer>();
            while(rs.next()) {
            	String course_id=rs.getString("course_id");
            	int cost=rs.getInt("price");
            	Course course=getCourse(course_id,reg_id);
            	bill.put(course, cost);
            }
            return bill;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return new HashMap<Course,Integer>();
	}
	public boolean haveTeacher(String course_id,int reg_id) {		
		try (Connection c = getConnection(); PreparedStatement s = c.prepareStatement("select professor_id from course_info where course_id= ? and reg_id= ? ;");) {
			s.setString(1, course_id); 
			s.setInt(2, reg_id);
            ResultSet rs= s.executeQuery();
            if(rs.next()) {
            	String result=rs.getString(1);
            	if(result!=null)
            		return true;
            	else
            		return false;
            
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
	}
	public Map<String,Integer> getStudentCountList(int reg_id) {
		try (Connection c = getConnection(); PreparedStatement s = c.prepareStatement("select course_id,student_count from course_info where reg_id= ? ;");) {			
            s.setInt(1, reg_id);
			ResultSet rs= s.executeQuery();        
            Map<String,Integer> List=new HashMap<String,Integer>();
            while(rs.next()) {
            	List.put(rs.getString(1), rs.getInt(2));
            }
            return List;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return null;
	}
	public Course getCourse(String course_id,int reg_id) {
		try (Connection c = getConnection(); PreparedStatement s = c.prepareStatement("select * from course_info where course_id= ? and reg_id= ? ;");) {	
			s.setString(1, course_id);
			s.setInt(2, reg_id);
			ResultSet rs= s.executeQuery(); 
			Course course=new Course();
			if(rs.next()) {
				course.setCourse_id(rs.getString("course_id"));
				course.setCourse_name(rs.getString("course_name"));
				course.setDept_id(rs.getInt("dept_id"));
				course.setEnd_date(rs.getDate("end_date"));
				course.setStart_date(rs.getDate("start_date"));
				course.setTimeslot_id(rs.getInt("timeslot_id"));
				course.setWeekday(rs.getInt("weekday"));
			}
            return course;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return null;
	}
	public boolean deleteCourse(String course_id,int reg_id) {
		try (Connection c = getConnection(); PreparedStatement s = c.prepareStatement("delete from course_info where course_id= ? and reg_id= ? ;");) {	
			s.setString(1, course_id);
            s.setInt(2, reg_id);
			int result=s.executeUpdate();           
            System.out.println("result:delete count=" + result);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
	}
	public boolean deleteSelection(String course_id,int reg_id) {
		try (Connection c = getConnection(); PreparedStatement s = c.prepareStatement("delete from selection where course_id= ? and reg_id= ? ;");) {	
            s.setString(1, course_id);
            s.setInt(2, reg_id);
            int result=s.executeUpdate();           
            System.out.println("result:delete count=" + result);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
	}
	public boolean closeRegistration(int reg_id) {
		try (Connection c = getConnection(); PreparedStatement s = c.prepareStatement("update registration set status='closed' where reg_id= ? ;");) {	
            s.setInt(1, reg_id);
            int result=s.executeUpdate();           
            System.out.println("result:delete count=" + result);
            if(result>0)
            	return true;
            else
            	return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
	}
}
>>>>>>> branch 'master' of https://github.com/jpccc/SoftwareEngineering.git
