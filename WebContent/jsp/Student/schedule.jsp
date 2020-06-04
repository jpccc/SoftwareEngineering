<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  import="DAO.SelectCourseDAO" import="DAO.SelectCourseDAOImpl" import="java.util.List"
    import="java.util.ArrayList" import="Beans.CourseSelection"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
		
		<%SelectCourseDAO select_course_dao=new DAO.SelectCourseDAOImpl();
		List<CourseSelection> list=new ArrayList<CourseSelection>();
		list=select_course_dao.get_schedule("li");
		response.getWriter().print("your schedule:<br>");
		for (int i = 0; i < list.size(); i++) {
            response.getWriter().println(list.get(i).get_student_id()+"  "+list.get(i).get_course_id()+"  "+list.get(i).get_reg_id()+"<br>");
        }
		%>
    	
    	<form action="http://localhost:8080/SoftwareEngineering/SelectCourseServlet?op=add" method=post>
		<textarea name="id"></textarea>
		<input type="submit" value="add" >
		</form>
		
		<br>
		<form action="http://localhost:8080/SoftwareEngineering/SelectCourseServlet?op=delete" method=post>
		<textarea name="id"></textarea>
		<input type="submit" value="delete"/>
		</form>
		
		<br>
		<form action="http://localhost:8080/SoftwareEngineering/SelectCourseServlet?op=check" method=post>
		<textarea name="id"></textarea>
		<input type="submit" value="check info"/>
		</form>
		
		<a href="show_course_list.jsp">show course_list</a>
		${message } <br>
		
		check info:	<br>
		${c }






</body>
</html>