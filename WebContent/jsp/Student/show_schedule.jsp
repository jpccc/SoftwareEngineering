<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  import="DAO.SelectCourseDAO" import="DAO.SelectCourseDAOImpl" import="java.util.List"
    import="java.util.ArrayList" import="Beans.Course" import="Beans.CourseSelection"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="http://localhost:8080/SoftwareEngineering/SelectCourseServlet?op=delete" method="post">


<% 
SelectCourseDAO select_course_dao=new DAO.SelectCourseDAOImpl();
List<CourseSelection> list=new ArrayList<CourseSelection>();
list=select_course_dao.get_schedule("li");
response.getWriter().print("your schedule:<br>");
for (int i = 0; i < list.size(); i++) {
%>
<input type="checkbox" name=<%=i %> value="<%=list.get(i).get_course_id()%> <%=list.get(i).get_reg_id() %> <%=list.get(i).get_student_id() %> <%=list.get(i).get_select_status() %>"  >
<%=list.get(i).get_course_id() %> <%=list.get(i).get_reg_id() %> <%=list.get(i).get_student_id() %> <%=list.get(i).get_select_status() %>
   delete course
<br>

<%

}

%>
<input type="submit" value="delete selected courses">
</form>
<a href="/SoftwareEngineering/jsp/Student/add_course.jsp">go to add_course.jsp</a>
</body>
</html>