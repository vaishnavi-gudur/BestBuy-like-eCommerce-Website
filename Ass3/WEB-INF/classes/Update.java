import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class Update extends HttpServlet
{
protected void doGet(HttpServletRequest request , HttpServletResponse response) throws ServletException,IOException
{
response.setContentType("text/html");
PrintWriter pw = response.getWriter();

HttpSession session = request.getSession();
String produ = (String)session.getAttribute("productName");

String uid = request.getParameter("id");
String uproductName = request.getParameter("productName");
String ubrand = request.getParameter("brand");
String ucond = request.getParameter("cond");
String uprice = request.getParameter("price");
String uretailername = request.getParameter("retailername");
String uretailercity = request.getParameter("retailercity");
String uretailerstate = request.getParameter("retailerstate");
String uretailerzip = request.getParameter("retailerzip");
String ucategory = request.getParameter("category");



Connection conn = null;
try{ 
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bestdealdatabase","root","root");
		String selectOrderQuery = "update products set id=?,productName=?,price=?,brand=?,category=?,cond=?,retailername=?,retailercity=?,retailerstate=?,retailerzip=?"+"where productName=?";
		PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
		pst.setString(1,uid);
		pst.setString(2,produ);
		pst.setString(3,uprice);
		pst.setString(4,ubrand);
		pst.setString(5,ucategory);
		pst.setString(6,ucond);
		pst.setString(7,uretailername);
		pst.setString(8,uretailercity);
		pst.setString(9,uretailerstate);
		pst.setString(10,uretailerzip);
		pst.setString(11,produ);
		pst.executeUpdate();
		
		pw.println("Success");
		pw.println("<a href='welcome'>BAck</a>");
	}
	catch(Exception e)
	{}
}
}