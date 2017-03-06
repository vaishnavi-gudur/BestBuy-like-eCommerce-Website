import java.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class WriteReview extends HttpServlet
{

protected void doGet(HttpServletRequest request , HttpServletResponse response) throws ServletException , IOException
{
response.setContentType("text/html");
PrintWriter pw = response.getWriter();

HttpSession session = request.getSession();

String brand = request.getParameter("brand");
//String userID = request.getParameter("username");
String userID = (String)session.getAttribute("username");
String productModelName = request.getParameter("productName");
String category = request.getParameter("category");
String price = request.getParameter("price");
String retailerName = request.getParameter("retailername");
String retailerCity = request.getParameter("retailercity");
String retailerState = request.getParameter("retailerstate");
String retailerZip = request.getParameter("retailerzip");
String manufacturerRebate = request.getParameter("manufacturerrebate");

session.setAttribute("category",category);
session.setAttribute("productModelName",productModelName);
session.setAttribute("price",price);
session.setAttribute("retailerName",retailerName);
session.setAttribute("retailerCity",retailerCity);
session.setAttribute("retailerState",retailerState);
session.setAttribute("retailerZip",retailerZip);
session.setAttribute("brand",brand);
session.setAttribute("manufacturerRebate",manufacturerRebate);

pw.println("<!doctype html>");
pw.println("<html>");
pw.println("<head>");
pw.println("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
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
pw.println("<li class=''><a href='#'>SmartPhones</a></li>");
pw.println("<li><a href='#'>Laptops</a></li>");
pw.println("<li><a href='#'>Tablets</a></li>");
pw.println("<li><a href='#'>TV</a></li>");
//pw.println("<li><a href='#'>Contact</a></li>");
pw.println("<li>\t\t\t\t</li>");
pw.println("<li><a href='logout'>Logout</a></li>");
pw.println("</ul>");
pw.println("</nav>");

pw.println("<div id='body'>");		

pw.println("<section id='content'>");
pw.println("<article>");


//pw.println("<table border='0.5px'><form action='#' mathod='get'><tr><td>Product ModelName:</td><td>"+productModelName+"</td></tr><tr><td>Product Category</td><td>"+category+"</td></tr><tr><td>Product Price</td><td>"+price+"</td></tr><tr><td>Retailer Name</td><td>"+retailerName+"</td></tr><tr><td>Retailer Zip</td><td>"+retailerZip+"</td></tr><tr><td>Retailer City</td><td>"+retailerCity+"</td></tr><tr><td>Retailer State</td><td>"+retailerState+"</td></tr><tr><td>Product on Sale</td><td>Yes</td></tr><tr><td>Manufacturer Name</td><td>"+brand+"</td></tr><tr><td>Manufacturer Rebate</td><td>"+manufacturerRebate+"</td></tr><tr><td>UserID</td><td></td></tr></td></tr></table>");
pw.println("<table border='1px'><form action='mongostore' method='get'><tr><td>Product ModelName:</td><td>"+productModelName+"</td></tr><tr><td>Product Category</td><td>"+category+"</td></tr><tr><td>Product Price</td><td>"+price+"</td></tr><tr><td>Retailer Name</td><td>"+retailerName+"</td></tr><tr><td>Retailer Zip</td><td>"+retailerZip+"</td></tr><tr><td>Retailer City</td><td>"+retailerCity+"</td></tr><tr><td>Retailer State</td><td>"+retailerState+"</td></tr><tr><td>Product on Sale</td><td>Yes</td></tr><tr><td>Manufacturer Name</td><td>"+brand+"</td></tr><tr><td>Manufacturer Rebate</td><td>"+manufacturerRebate+"</td></tr><tr><td>UserID</td><td>"+userID+"</td></tr><tr><td>Age</td><td><input type='text' name='age'/></td></tr><tr><td>Gender</td><td><select name='gender'><option value='male'>Male</option><option value='female'>Female</option></select></td></tr><tr><td>Occupation</td><td><input type='text' name='occupation'/></td></tr><tr><td>Review Rating</td><td><select name='rating'><option value='1'>1</option><option value='2'>2</option><option value='3'>3</option><option value='4'>4</option><option value='5'>5</option></select></td></tr><tr><td>Review Text</td><td><textarea rows='4' cols='50' name='reviewtext'></textarea></td></tr>");
pw.println("<input type='hidden' name='age' value='age'/><input type='hidden' name='gender' value='gender'/><input type='hidden' name='occupation' value='occupation'/><input type='hidden' name='rating' value='rating'/><input type='hidden' name='reviewtext' value='reviewtext'/><tr><td><input type='submit' value='Submit'/></td></form></table>");	

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