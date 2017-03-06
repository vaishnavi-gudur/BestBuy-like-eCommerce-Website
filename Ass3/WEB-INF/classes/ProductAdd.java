import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class ProductAdd extends HttpServlet
{
protected void doGet(HttpServletRequest request , HttpServletResponse response) throws ServletException , IOException
{
response.setContentType("text/html");
PrintWriter pw = response.getWriter();

String id = request.getParameter("id");
String productName = request.getParameter("productName");
String brand = request.getParameter("brand");
String cond = request.getParameter("cond");
String price = request.getParameter("price");
String retailername = request.getParameter("retailername");
String retailercity = request.getParameter("retailercity");
String retailerstate = request.getParameter("retailerstate");
String retailerzip = request.getParameter("retailerzip");
String category = request.getParameter("category");


HashMap<String,Product> hm = new HashMap<String,Product>();
MySQLDataStoreUtilities my = new MySQLDataStoreUtilities();


Product product = new Product();
hm.put(id,product);
my.insertProduct(id,productName,brand,cond,price,retailername,retailercity,retailerstate,retailerzip,category);
pw.println("Product Added");
pw.println("<a href='welcome'>Go Back</a>");
}
}