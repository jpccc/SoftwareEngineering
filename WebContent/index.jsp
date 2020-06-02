<%@ page import="Beans.StudentInfo" %>
<%@ page import="com.lxb.Professor.Professor" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="DAO.DruidManager" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %><%--
  Created by IntelliJ IDEA.
  User: 李睿宸
  Date: 2020/6/1
  Time: 20:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%
    StudentInfo student= (StudentInfo) session.getAttribute("studentInfo");
    if(student!=null)request.getRequestDispatcher("/WEB-INF/jsp/StudentPage.jsp").forward(request,response);
    Professor professor= (Professor) session.getAttribute("professorInfo");
    if(professor!=null)request.getRequestDispatcher("/WEB-INF/jsp/ProfessorPage.jsp").forward(request,response);
%>
<html >
<head>
    <meta charset="UTF-8">
    <title>教务管理系统</title>
    <link rel="stylesheet" href="css/login_style.css">
</head>

<body>
<main>
    <%
        Connection conn=null;
        try {
            conn= DruidManager.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connection is:"+(conn==null));
        if(conn!=null) {
            try {
                PreparedStatement ps=conn.prepareStatement("show tables;");
                ResultSet rs=ps.executeQuery();
                while(rs.next()){
                    System.out.println(rs.getString(1));
                }
                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    %>
    <form class="form" action="LoginServlet" method="post">
        <div class="form__cover"></div>
        <div class="form__loader">
            <div class="spinner active">
                <svg class="spinner__circular" viewBox="25 25 50 50">
                    <circle class="spinner__path" cx="50" cy="50" r="20" fill="none" stroke-width="4" stroke-miterlimit="10"></circle>
                </svg>
            </div>
        </div>
        <div class="form__content">
            <h1>Authorization</h1>
            <div class="styled-input">
                <input type="text" class="styled-input__input" name="username">
                <div class="styled-input__placeholder"> <span class="styled-input__placeholder-text">Username</span> </div>
                <div class="styled-input__circle"></div>
            </div>
            <div class="styled-input">
                <input type="password" class="styled-input__input"name="password">
                <div class="styled-input__placeholder"> <span class="styled-input__placeholder-text">Password</span> </div>
                <div class="styled-input__circle"></div>
            </div>
            <input type="submit" class="styled-button"> <span class="styled-button__real-text-holder"> <span class="styled-button__real-text">Submit</span> <span class="styled-button__moving-block face"> <span class="styled-button__text-holder"> <span class="styled-button__text">Submit</span> </span> </span><span class="styled-button__moving-block back"> <span class="styled-button__text-holder"> <span class="styled-button__text">Submit</span> </span> </span> </span> </input>
        </div>
    </form>
</main>
<script  src="js/login_index.js"></script>
</body>
</html>

