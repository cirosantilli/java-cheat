/*
Specification: <https://jcp.org/aboutJava/communityprocess/final/jsr315/>
*/

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ServletHelloWorld extends HttpServlet {

    private String message;

    public void init() throws ServletException {
        message = "Hello World";
    }

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException
    {
        // Content type header.
        response.setContentType("text/html");

        // Body
        PrintWriter out = response.getWriter();
        out.println("<h1>" + message + "</h1>");
    }

    public void destroy() {}
}
