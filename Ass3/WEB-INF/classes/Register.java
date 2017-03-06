import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Register extends HttpServlet
{
protected void doGet(HttpServletRequest request , HttpServletResponse response) throws ServletException , IOException
{
response.setContentType("text/html");
PrintWriter pw = response.getWriter();

String username = request.getParameter("username");
String password = request.getParameter("password");
String usertype = request.getParameter("usertype");
HashMap<String,User> hm = new HashMap<String,User>();
MySQLDataStoreUtilities my = new MySQLDataStoreUtilities();


User user = new User();
hm.put(username,user);
my.insertUser(username,password,usertype);
pw.println("User registered successfully! Press Login to continue");
pw.println("<br>");
pw.println("<a href='abcd.html'>Login</a>");

}
}



