<%@ page import="Beans.Student" %>
<%@ page import="Beans.Professor" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="DAO.DruidManager" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="Beans.User" %>
<%@ page import="Beans.Registration" %>
<%@ page import="DAO.RegistrationDAOImpl" %>
<%@ page import="java.util.Calendar" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%
    if(session.getAttribute("registration")==null){//获取最新的注册信息
        RegistrationDAOImpl regDao=new RegistrationDAOImpl();
        Registration registration=regDao.queryLatest();
        session.setAttribute("registration",registration);
    }
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
                request.getRequestDispatcher("/jsp/Registrar/RegistrarPage.jsp").forward(request,response);
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
            <div id="submit">
            <button type="button" class="styled-button" > <span class="styled-button__real-text-holder"> <span class="styled-button__real-text">Submit</span> <span class="styled-button__moving-block face"> <span class="styled-button__text-holder"> <span class="styled-button__text">Submit</span> </span> </span><span class="styled-button__moving-block back"> <span class="styled-button__text-holder"> <span class="styled-button__text">Submit</span> </span> </span> </span> </button>
            </div>
            <div class="error" style="color: #ff0000;font-size: medium">${error}</div>
        </div>
    </form>
</main>
<%session.removeAttribute("error");%>
<script  src="js/login_index.js"></script>
</body>
</html>

