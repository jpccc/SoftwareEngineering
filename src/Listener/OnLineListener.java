package Listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class OnLineListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        Integer count;
        if(httpSessionEvent.getSession().getServletContext().getAttribute("onLineCount")!=null){
            count= (Integer) httpSessionEvent.getSession().getServletContext().getAttribute("onLineCount");
        }else{
            count=0;
        }
        System.out.println(count);
        System.out.println(httpSessionEvent.getSession().getId());
        count++;
        httpSessionEvent.getSession().getServletContext().setAttribute("onLineCount",count);
    }
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        Integer count=0;
        count= (Integer) httpSessionEvent.getSession().getServletContext().getAttribute("onLineCount");
        count--;
        httpSessionEvent.getSession().getServletContext().setAttribute("onLineCount",count);
    }
}
