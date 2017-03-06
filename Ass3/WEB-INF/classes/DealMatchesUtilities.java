import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DealMatchesUtilities extends HttpServlet
{
protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException 
{
		String output="";
		output = DealMatch.get();
		
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
		out.println(output);
}
}