package Listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class OnLineListener implements HttpSessionListener {
    private static int count=0;
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        count++;
        System.out.println(httpSessionEvent.getSession().getId());
        httpSessionEvent.getSession().getServletContext().setAttribute("onLineCount",count);
    }
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        count--;
        httpSessionEvent.getSession().getServletContext().setAttribute("onLineCount",count);
    }

    public static int getCount(){
        return count;
    }
}
