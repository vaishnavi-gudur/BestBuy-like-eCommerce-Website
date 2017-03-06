import java.util.*;
import java.util.Map.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
 
public class DeleteOrder extends HttpServlet
{
protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
{
response.setContentType("text/html");
HttpSession session = request.getSession();
String user = (String)session.getAttribute("username");
PrintWriter pw = response.getWriter();
Connection conn = null;
Statement stmt = null;
try
{
Class.forName("com.mysql.jdbc.Driver").newInstance();
conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bestdealdatabase","root","root");
String deleteOrderQuery = "delete from Orders where username = '"+user+"'";
stmt = conn.createStatement();
stmt.executeUpdate(deleteOrderQuery);
pw.println("Success!!");
}
catch(Exception e)
{
}
}
}











