<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="Beans.Professor" %>
<%@ page import="Beans.Registration" %>
<%@ page import="Beans.Grade" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: 李睿宸
  Date: 2020/6/2
  Time: 14:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    if(session.getAttribute("selectReg")==null){
        session.setAttribute("selectReg",session.getAttribute("registration"));
    }
%>
<html>
<head>
    <title>提交成绩</title>
</head>
<body>
<div class="time_display" >
    <%
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String now = df.format(d);
        //professor test
        java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01");
        java.sql.Date birthday = new java.sql.Date(utilDate.getTime());
        Professor professor = new Professor("P1234567","NIKO",birthday,1,"0",1,"123456");
        request.getSession().setAttribute("professor",professor);
        Registration reg= (Registration) session.getAttribute("selectReg");
    %>
    当前时间：<%=now %>
</div>
<form action="/GradesServlet?method=queryCourses" method="post">
    <input type="text" name="year" value="<%=reg.getYear()%>"/>
    <input type="text" name="semester" value="<%=reg.getSemester()%>"/>
    <input type="submit" value="查询"/>
</form>
<div class="error" style="color: red;font-size: medium">${queryError}</div>
<div class="scroll_table">
    <table border="1" cellpadding=“0” cellspacing="0" align="center" width="70%">
        <c:if test="${not empty courseList}">
            <tr style="background-color: red">
                <th>注册号</th>
                <th>课程号</th>
                <th>系号</th>
                <th>课程名称</th>
                <th>课程状态</th>
            </tr>
            <c:forEach items="${courseList}" var="course">
                <tr >
                    <th>${course.reg_id}</th>
                    <th>${course.course_id}</th>
                    <th>${course.dept_id}</th>
                    <th>${course.course_name}</th>
                    <th>${course.status}</th>
                    <th><a href="/GradesServlet?method=queryStudents&course_name=${course.course_name}
                    &course_id=${course.course_id}&reg_id=${course.reg_id}">修改成绩</a></th>
                </tr>
            </c:forEach>
        </c:if>

        <c:if test="${not empty gradeList}">
            <form id="gradeForm" action="/GradesServlet?method=submitGrades">
                <tr>
                    <th>学生ID</th>
                    <th>课程名称</th>
                    <th>学生成绩</th>
                </tr>
               <%
                   String course_name=request.getParameter("course_name");
                   List<Grade> gradeList= (List<Grade>) request.getAttribute("gradeList");
                   for(int i=0;i<gradeList.size();i++){
                       out.println("<tr>");
                       out.println("<td>"+gradeList.get(i).getStudent_id()+"</td>");
                       out.println("<td>"+course_name+"</td>");
                       if(gradeList.get(i).getGrade()==null)
                           out.println("<td><input type='text' name='grade"+i+"'/></td>");
                       else out.println("<td><input type='text' name='grade"+i
                               +"' value='"+gradeList.get(i).getGrade()
                               +"'/></td>");
                   }
               %>
                <input type="button" onclick="saveGrades()" value="保存"/>
                <input type="submit" value="提交"/>
            </form>
        </c:if>
    </table>
</div>
</body>
<script type="javascript">
    function saveGrades() {
        let form=document.getElementById("gradeForm");
        form.action="/GradesServlet?method=saveGrades";
        form.submit();
    }
</script>
</html>
