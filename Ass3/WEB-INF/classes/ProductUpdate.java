import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class ProductUpdate extends HttpServlet
{
protected void doGet(HttpServletRequest request , HttpServletResponse response) throws ServletException,IOException
{
response.setContentType("text/html");
PrintWriter pw = response.getWriter();

String prodName = request.getParameter("productName");
Connection conn = null;
pw.println("<!doctype html>");
pw.println("<html>");
pw.println("<head>");
pw.println("<meta http-equiv='Content-Type' content='text/html'; charset='utf-8' />");
pw.println("<title>BestDeal</title>");
pw.println("<link rel='stylesheet' href='styles.css' type='text/css' />");

pw.println("<script src='http;//html5shiv.googlecode.com/svn/trunk/html5.js'></script>");
//-->
pw.println("</head>");
pw.println("<body>");
pw.println("<div id='container'>");
pw.println("<header>");
pw.println("<h1><a href='myhome'>Best<span>Deal</span></a></h1>");
pw.println("<h2>The Best You Can Get</h2>");
pw.println("</header>");
pw.println("<nav>");
pw.println("<ul>");
pw.println("<li class='start selected'><a href='myhome'>Home</a></li>");
pw.println("<li><a href='smartphone'>SmartPhones</a></li>");
pw.println("<li><a href='laptop'>Laptops</a></li>");
pw.println("<li><a href='tablet'>Tablets</a></li>");
pw.println("<li><a href='tv'>TV</a></li>");
pw.println("<li><a href='logout'>Logout</a></li>");
pw.println("</ul>");
pw.println("</nav>");
//pw.println("<img class='header-image' src='images/image.jpg' alt='Buildings' />");

pw.println("<div id='body'>");		

pw.println("<section id='content'>");
pw.println("<article>");
pw.println("<p style='position:relative;left:75%;top:40%'><a href='#' style='text-decoration:none'>Account&nbsp&nbsp</a><a href='vieworder' style='text-decoration:none'>Orders&nbsp&nbsp</a><a href='#' style='text-decoration:none'>Cart</a></p>");
try{ 
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bestdealdatabase","root","root");
		String selectOrderQuery = "select * from products where productName = '"+prodName+"'";
		PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
		ResultSet rs = pst.executeQuery();
		String ab="";
		while(rs.next())
		{
			String proid = rs.getString("id");
			String proName = rs.getString("productName");
			String proprice = rs.getString("price");
			String probrand = rs.getString("brand");
			String procategory = rs.getString("category");
			String procond = rs.getString("cond");
			String proretailername = rs.getString("retailername");
			String proretailercity = rs.getString("retailercity");
			String proretailerstate = rs.getString("retailerstate");
			String proretailerzip = rs.getString("retailerzip");
			
			pw.println("<form action='update' method='get'><table border='1px'><tr><td>ID:</td><td>"+proid+"</td><td><input type='text' name='id'></td></tr><tr><td>Product Name:</td><td>"+proName+"</td><td><input type='text' name='productName'></td></tr><tr><td>Price:</td><td>"+proprice+"</td><td><input type='text' name='price'></td></tr><tr><td>Brand:</td><td>"+probrand+"</td><td><input type='text' name='brand'></td></tr><tr><td>Category:</td><td>"+procategory+"</td><td><input type='text' name='category'></td></tr><tr><td>Condition:</td><td>"+procond+"</td><td><input type='text' name='cond'></td></tr><tr><td>Retailer Name:</td><td>"+proretailername+"</td><td><input type='text' name='retailername'></td></tr><tr><td>Retailer City:</td><td>"+proretailercity+"</td><td><input type='text' name='retailercity'></td></tr><tr><td>Retailer State:</td><td>"+proretailerstate+"</td><td><input type='text' name='retailerstate'></td></tr><tr><td>Retailer Zip:</td><td>"+proretailerzip+"</td><td><input type='text' name='retailerzip'></td></tr><tr><td><input type='submit' name='submit' value='Update'></td></tr><input type='hidden' name='id' value='id'><input type='hidden' name='productName' value='productName'><input type='hidden' name='brand' value='brand'><input type='hidden' name='cond' value='cond'><input type='hidden' name='category' value='category'><input type='hidden' name='retailername' value='retailername'><input type='hidden' name='retailercity' value='retailercity'><input type='hidden' name='retailerstate' value='retailerstate'><input type='hidden' name='retailerzip' value='retailerzip'><input type='hidden' name='price' value='price'></table></form>");			
		}
		//pw.println(ab);
}
catch(Exception e)
{}
pw.println("");
pw.println("");


pw.println("</article>");
pw.println("<article class='expanded'>");
//pw.println("<h2>Buy unbranded</h2>");
pw.println("");
//pw.println("<img src='images/item.jpeg' alt='Gadgets'/>");
pw.println("");

pw.println("");

pw.println("");


		pw.println("");
		pw.println("");
		pw.println("</article>");
        pw.println("</section>");
        
        pw.println("<aside class='sidebar'>");
		pw.println("<ul>");
        pw.println("<li>");
        pw.println("<h4>SmartPhones</h4>");
        pw.println("<ul>");
        pw.println("<li><a href='samsung'>Samsung</a></li>");
        pw.println("<li><a href='htc'>HTC</a></li>");
        pw.println("<li><a href='apple'>Apple</a></li>");
        pw.println("<li><a href='sony'>SONY</a></li>");
        pw.println("<li><a href='acer'>Acer</a></li>");
        pw.println("</ul>");
        pw.println("</li>");
                
        pw.println("<li>");
        pw.println("<h4>Laptops</h4>");
        pw.println("<ul>");
        pw.println("<li><a href='dell'>Dell</a></li>");
		pw.println("<li><a href='lenovo'>Lenovo</a></li>");
		pw.println("<li><a href='hp'>HP</a></li>");
		pw.println("<li><a href='panasonic'>Panasonic</a></li>");
		pw.println("<li><a href='applaptop'>Apple</a></li>");
        //pw.println("<p style='margin; 0;'>Aenean nec massa a tortor auctor sodales sed a dolor. Duis vitae lorem sem. Proin at velit vel arcu pretium luctus.<a href='#' class='readmore'>Read More &raquo;</a></p>");
        pw.println("</li>");
        pw.println("</ul>");
        pw.println("</li>");
                
        pw.println("<li>");
        pw.println("<h4>Tablets</h4>");
        pw.println("<ul>");
		pw.println("<li><a href='lgtab'>LG</a></li>");
		pw.println("<li><a href='acertab'>Acer</a></li>");
		pw.println("<li><a href='asustab'>Asus</a></li>");
		pw.println("<li><a href='samsungtab'>Samsung</a></li>");
		pw.println("<li><a href='googletab'>Google</a></li>");
        pw.println("</li>");
		pw.println("</ul>");
        pw.println("</li>");
                
        pw.println("<li>");
        pw.println("<h4>TV</h4>");
        pw.println("<ul>");
		pw.println("<li><a href='panasonictv'>Panasonic</a></li>");
		pw.println("<li><a href='sonytv'>SONY</a></li>");
		pw.println("<li><a href='toshibatv'>Toshiba</a></li>");
		pw.println("<li><a href='insigniatv'>Insignia</a></li>");
		pw.println("<li><a href='hitachitv'>Hitachi</a></li>");
        pw.println("</li>");
		pw.println("</ul>");
        pw.println("</li>");
        
        
        pw.println("</aside>");
    	pw.println("<div class='clear'></div>");
		pw.println("</div>");
		pw.println("<footer>");
        //pw.println("<div class='footer-content'>");
        //pw.println("<ul>");
        //pw.println("<li><h4>Proin accumsan</h4></li>");
        //pw.println("<li><a href='#'>Rutrum nulla a ultrices</a></li>");
        //pw.println("<li><a href='#'>Blandit elementum</a></li>");
        //pw.println("<li><a href='#'>Proin placerat accumsan</a></li>");
        //pw.println("<li><a href='#'>Morbi hendrerit libero </a></li>");
        //pw.println("<li><a href='#'>Curabitur sit amet tellus</a></li>");
        //pw.println("</ul>");
            
            
        //pw.println("<div class='clear'></div>");
        //pw.println("</div>");
        pw.println("<div class='footer-bottom'>");
        pw.println("<p>&copy; YourSite 2013. <a href='http;//zypopwebtemplates.com/'>Free CSS Website Templates</a> by ZyPOP</p>");
        //pw.println("</div>");
		pw.println("</footer>");
		pw.println("</div>");
		pw.println("</body>");
		pw.println("</html>");
}
}