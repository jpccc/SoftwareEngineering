<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  import="DAO.SelectCourseDAO" import="DAO.SelectCourseDAOImpl" import="java.util.List"
    import="java.util.ArrayList" import="Beans.Course"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="http://localhost:8080/SoftwareEngineering/SelectCourseServlet?op=add" method="post">


<% 
SelectCourseDAO select_course_dao=new DAO.SelectCourseDAOImpl();
List<Course> list=new ArrayList<Course>();
list=select_course_dao.get_all_courses();
response.getWriter().print("list of courses:<br>");
for (int i = 0; i < list.size(); i++) {
%>
<input type="checkbox" name=<%=i %> value="<%=list.get(i).getCourse_id() %> <%=list.get(i).getReg_id()%> <%=list.get(i).getCourse_name() %>"  >
<%=list.get(i).getCourse_id() %> <%=list.get(i).getReg_id()%> <%=list.get(i).getCourse_name() %> 
<input type="checkbox" name="primary<%=i %>" value="primary">primary
<input type="checkbox" name="second<%=i %>" value="second">second
<br>

<%
}
%>
<input type="submit" value="sub">
<a href="/SoftwareEngineering/jsp/Student/show_schedule.jsp">jmp to show_schedule.jsp</a>


</form>

<form action="http://localhost:8080/SoftwareEngineering/SelectCourseServlet?op=check" method=post>
		<textarea name="id"></textarea>
		<input type="submit" value="check info"/>
		</form>
check info:	<br>
		${c }
</body>
</html>