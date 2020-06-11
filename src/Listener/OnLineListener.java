package Listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class OnLineListener implements HttpSessionListener {
    private int count=0;
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        count++;
        httpSessionEvent.getSession().getServletContext().setAttribute("onLineCount",count);
    }
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        count--;
        httpSessionEvent.getSession().getServletContext().setAttribute("onLineCount",count);
    }
}
