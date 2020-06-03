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
<%SelectCourseDAO select_course_dao=new DAO.SelectCourseDAOImpl();
		List<Course> list=new ArrayList<Course>();
		list=select_course_dao.get_all_courses();
		response.getWriter().print("list of courses:<br>");
		for (int i = 0; i < list.size(); i++) {
            response.getWriter().println(list.get(i).getCourse_id()+"  "+list.get(i).getCourse_name()+"  "+"<br>");
        }
		%>
<a href="schedule.jsp">go to schedule.jsp</a>
</body>
</html>