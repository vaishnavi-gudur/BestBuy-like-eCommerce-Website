import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ComposerServlet extends HttpServlet
{
protected void doGet(HttpServletRequest request , HttpServletResponse response) throws ServletException,IOException
{
response.setContentType("text/html");
PrintWriter pw = response.getWriter();
pw.println(request.getAttribute("productinfo.productName"));
}
}