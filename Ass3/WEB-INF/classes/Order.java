import java.util.*;
import java.util.Map.*;
import java.io.*;
import java.util.Date;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Order extends HttpServlet
{
protected void doGet(HttpServletRequest request , HttpServletResponse response) throws ServletException , IOException
{
response.setContentType("text/html");
PrintWriter pw = response.getWriter();
HttpSession session = request.getSession();
String username = request.getParameter("username");
String productName = (String)session.getAttribute("productName");
String price = (String)session.getAttribute("price");
String creditCardNo = request.getParameter("credit");
String address = request.getParameter("address");
int ordid = Integer.parseInt(request.getParameter("orderid"));
//String deliveryDate = request.getParameter("deliveryDate");
session.setAttribute("creditCardNo",creditCardNo);
session.setAttribute("address",address);

HashMap<String, OrderPayment> hm = new HashMap<String, OrderPayment>();
MySQLDataStoreUtilities my = new MySQLDataStoreUtilities();


OrderPayment orders = new OrderPayment();
hm.put(username,orders);
my.insertOrder(username,productName,price,creditCardNo,address,ordid);
pw.println("Order Placed Successfully!!!");
pw.println("Your Order Number is :"+ordid);
pw.println("<br>");
pw.println("<br>");
Date dnow = new Date();
SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.yyyy");
pw.println("Order placed on:" + ft.format(dnow));
pw.println("<br>");
Calendar c = Calendar.getInstance();
c.setTime(new Date()); 
c.add(Calendar.DATE, 5); 
String output = ft.format(c.getTime());
pw.println("Expected Delivery On:");
pw.println(output);
pw.println("<a href='vieworder'>View Order</a>");


Connection conn = null;
try{ 
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bestdealdatabase","root","root");
		String selectOrderQuery = "update orders set deliveryDate=?"+"where orderid=?";
		PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
		pst.setString(1,output);
		pst.setInt(2,ordid);
		pst.executeUpdate();
		
		pw.println("Success");
		pw.println("<a href='welcome'>BAck</a>");
	}
	catch(Exception e)
	{}

}

}