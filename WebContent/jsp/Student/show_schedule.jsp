<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"  import="DAO.SelectCourseDAO" import="DAO.SelectCourseDAOImpl" import="java.util.List"
         import="java.util.ArrayList" import="Beans.Course" import="Beans.CourseSelection" import="Beans.Student"%>
<html>
<head>
   <title>学生课表</title>
   <link rel="stylesheet" href="css/select.css">
   <script>
      function show(){
         var x=document.getElementById("sidemenu");

         if(x.checked==true){
            document.getElementById("list").style.left="-180px";
            x.checked=false;
         }
         else{
            document.getElementById("list").style.left=0;
            x.checked=true;
         }
      }
      function submit() {
         window.confirm("确认退课?");
         var x=document.getElementById("course_form");
         x.submit();
      }
   </script>
</head>
<body background="images/stu_back.jpg">
<div class="head">
   <div class="block_left">
      <div>
         <hr id="line_l">
      </div>
   </div>
   <div class="block_right">
      <div class="head_title">
         <h2 id="title">学生课表</h2>
      </div>
      <div class="inerblock_right">
         <div>
            <hr id="line_r">
         </div>
      </div>
   </div>
</div>

<div class="navigate">
   <input type="checkbox" id="sidemenu">
   <aside id="list">
      <h2>课程列表</h2>
      <br/>
      <ul id="sideul">
         <a href="##">
            <li>学生首页</li>
         </a>
         <a href="/SoftwareEngineering/jsp/Student/show_schedule.jsp">
            <li>查询课表</li>
         </a>
         <a href="/SoftwareEngineering/jsp/Student/add_course.jsp">
            <li>选课页面</li>
         </a>
         <a href="##">
            <li>others</li>
         </a>
      </ul>
   </aside>
</div>

<form id="course_form" action="http://localhost:8080/SoftwareEngineering/SelectCourseServlet?op=delete" method="post">
   <div class="body">
      <div class="table_one">
         <table class="table_info" border="1">
            <caption>
               <script>
                  document.write(Date());
               </script>
            </caption>

            <div class="table_final">
               <tr class="text-propetty">
                  <th height="57" class="text-center">课程名</th>
                  <th class="text-center">课程起始时间</th>
                  <th class="text-center">课程结束时间</th>
                  <th class="text-center">WeekDay</th>
                  <th class="text-center">教师姓名</th>
                  <th class="text-center">已选人数</th>
                  <th class="text-center">删除课程</th>
               </tr>
               <%
                  SelectCourseDAO select_course_dao=new DAO.SelectCourseDAOImpl();
                  List<CourseSelection> list=new ArrayList<CourseSelection>();
                  Student student = (Student) session.getAttribute("user");
                  String s_id=student.getS_id();
                  list=select_course_dao.get_schedule(s_id);
                  int name=0;
                  for (int i = 0; i < list.size(); i++) {
                	  Course course=select_course_dao.check_course_from_selection(list.get(i));
               %>
               <tr class="text-value">
                  <td height="62"><%=course.getCourse_name()%></td>
                  <td><%=course.getStart_date()%></td>
                  <td><%=course.getEnd_date()%></td>
                  <td><%=course.getWeekday()%></td>
                  <td><%=course.getWeekday()%></td>
                  <td><%=course.getStudent_count()%></td>
                  <td>
                     <input  type="radio"  value="<%=list.get(i).get_course_id()%> <%=list.get(i).get_reg_id() %> <%=list.get(i).get_student_id() %> <%=list.get(i).get_select_status() %>" name=<%=name%>
                     />删除
                  </td>
               </tr>
               <%
                     name++;
                  }
               %>
            </div>
         </table>
      </div>
   </div>
</form>
<div class="bottom">
   <br>
   <button class="button_right" onclick="submit()">提交</button>
   <button class="button_left" onclick="show()">功能</button>
</div>
</body>
</html>