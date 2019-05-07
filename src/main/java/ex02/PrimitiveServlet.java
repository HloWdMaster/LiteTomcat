package ex02;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Create by leonardo on 2019/5/6
 */
public class PrimitiveServlet implements Servlet {


    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("init");
    }

    public ServletConfig getServletConfig() {
        return null;
    }

    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("from service");
        PrintWriter out = servletResponse.getWriter();
        out.println("Hello. Rose are red.");
        out.print("Violets are blue");

    }

    public String getServletInfo() {
        return null;
    }

    public void destroy() {
        System.out.println("destory");
    }
}
