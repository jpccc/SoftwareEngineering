package Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        if(username==null||password==null){
            request.setAttribute("errMsg","网络错误，请重试");
            request.getRequestDispatcher("index.jsp").forward(request,response);
        }else{
            String dbPassword=null;
            String sql="select password from ";
            Connection conn=null;
            try{
                conn=DAO.DruidManager.getConnection();
                switch(username.charAt(0)){
                    case 'P':sql+="professor_info where p_id=?;";break;
                    case 'S':sql+="student_info where s_id=?;";break;
                    case 'R':sql+="registerer_info where r_id=?;";break;
                }
                PreparedStatement ps=conn.prepareStatement(sql);
                ps.setString(1,username);
                ResultSet rs=ps.executeQuery();
                while(rs.next()){
                    dbPassword=rs.getString(1);
                }
                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                if(conn!=null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(dbPassword!=null&&dbPassword.equals(password)){
                switch(username.charAt(0)){
                    case 'P':
                        request.getRequestDispatcher("index.jsp").forward(request,response);
                        break;
                    case 'S':request.getRequestDispatcher("index.jsp").forward(request,response);break;
                    case 'R':
                        request.getRequestDispatcher("index.jsp").forward(request,response);break;
                }
            }else{
                request.setAttribute("errMsg","账号或密码错误");
                request.getRequestDispatcher("index.jsp").forward(request,response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
