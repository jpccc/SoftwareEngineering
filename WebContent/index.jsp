<%@ page import="Beans.Student" %>
<%@ page import="Beans.Professor" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="DAO.DruidManager" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="Beans.User" %>
<%@ page import="Beans.Registration" %><%--
  Created by IntelliJ IDEA.
  User: 李睿宸
  Date: 2020/6/1
  Time: 20:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%
    Connection conn=null;
    PreparedStatement ps=null;
    ResultSet rs=null;
    Registration registration=new Registration();
    String sql="select * from registration where reg_id=(select max(reg_id) from registration)";
    try {
        conn=DruidManager.getConnection();
        ps=conn.prepareStatement(sql);
        rs=ps.executeQuery();
        while(rs.next()){
            registration.setReg_id(rs.getInt("reg_id"));
            registration.setStatus(rs.getString("status"));
            registration.setYear(rs.getInt("year"));
            registration.setSemester(rs.getString("semester"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }finally {
        try {
            DruidManager.close(conn,ps,rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    session.setAttribute("registration",registration);
    User user= (User) session.getAttribute("user");
    if(user!=null){
        switch (user.type){
            case User.USER_STUDENT:{
                request.getRequestDispatcher("/jsp/Student/StudentPage.jsp").forward(request,response);
                break;
            }
            case User.USER_PROFESSOR:{
                request.getRequestDispatcher("/jsp/Professor/ProfessorPage.jsp").forward(request,response);
                break;
            }
            case User.USER_REGISTERER:{
                request.getRequestDispatcher("/jsp/Registerer/RegistererPage.jsp").forward(request,response);
                break;
            }
        }
    }
%>
<html >
<head>
    <meta charset="UTF-8">
    <title>教务管理系统</title>
    <link rel="stylesheet" href="css/login_style.css">
</head>

<body>
<main>
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
            <div class="error" style="color: red;font-size: medium">${error}</div>
        </div>
    </form>
</main>

<script  src="js/login_index.js"></script>
</body>
</html>

