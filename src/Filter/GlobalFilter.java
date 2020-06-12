package Filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URL;

public class GlobalFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req= (HttpServletRequest) servletRequest;
        HttpServletResponse res= (HttpServletResponse) servletResponse;
        URL url=new URL(req.getRequestURL().toString());
        String str=url.getPath();
        str=str.substring(str.lastIndexOf('/')+1);
        HttpSession session=req.getSession();
        if(!str.equals("")&&
                !str.contains("LoginServlet")&&
                !str.contains(".css")&&
                !(str.contains(".js")&&!str.contains(".jsp"))&&
                !str.contains("index.jsp")&&
                session.getAttribute("user")==null){
            System.out.println("");
            req.setAttribute("error","µÇÂ¼³¬Ê±£¬ÇëÖØÐÂµÇÂ¼");
            req.getRequestDispatcher("/").forward(req,res);
        }
        else filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
