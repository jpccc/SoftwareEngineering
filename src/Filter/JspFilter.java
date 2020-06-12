package Filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URL;

public class JspFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req= (HttpServletRequest) servletRequest;
        HttpServletResponse res= (HttpServletResponse) servletResponse;
        HttpSession session=req.getSession();
        if(session.getAttribute("user")==null){
            req.setAttribute("error","µÇÂ¼³¬Ê±£¬ÇëÖØÐÂµÇÂ¼");
            req.getRequestDispatcher("/").forward(req,res);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
