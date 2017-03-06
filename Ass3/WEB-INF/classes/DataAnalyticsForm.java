import java.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

import com.mongodb.ServerAddress;

public class DataAnalyticsForm extends HttpServlet
{
static DBCollection myReviews;

protected void doGet(HttpServletRequest request , HttpServletResponse response) throws ServletException,IOException
{
	response.setContentType("text/html");
	PrintWriter pw = response.getWriter();
	int reviewRating = Integer.parseInt(request.getParameter("rating").trim());
	int pricereview = Integer.parseInt(request.getParameter("price").trim());
	String compareRating = request.getParameter("compareRating");
	String compareRating1 = request.getParameter("compareRating1");
	String prodname = request.getParameter("product");
	String retcity = request.getParameter("city");
	String[] filters = request.getParameterValues("queryCheckBox");

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
pw.println("<li><a href='#'>Contact</a></li>");
pw.println("<li>\t\t\t\t</li>");
pw.println("<li><a href='abc.html'>Login</a></li>");
pw.println("</ul>");
pw.println("</nav>");
//pw.println("<img class='header-image' src='images/image.jpg' alt='Buildings' />");

pw.println("<div id='body'>");		

pw.println("<section id='content'>");
pw.println("<article>");


MongoClient mongo;
mongo = new MongoClient("localhost", 27017);
DB db = mongo.getDB("CustomerReviews");
myReviews= db.getCollection("myReviews");
BasicDBObject query = new BasicDBObject();
boolean noFilter = false;
boolean filterByRating = false;
if(filters != null)
{
for (int i = 0; i < filters.length; i++) 
{
	//pw.println(filters[i]);
switch (filters[i])
{
case "rating":
filterByRating = true;

if (compareRating.equals("Equals")) 
{
query.put("rating", reviewRating);
}else if(compareRating.equals("LessThan"))
{
query.put("rating", new BasicDBObject("$lt", reviewRating));
}else
{
query.put("rating", new BasicDBObject("$gt",reviewRating));
}

break; 

case "price":
filterByRating = true;

if (compareRating1.equals("Equals")) 
{
query.put("price", pricereview);
}else if(compareRating1.equals("LessThan"))
{
query.put("price", new BasicDBObject("$lt", pricereview));
}else if(compareRating1.equals("GreaterThan"))
{
query.put("price", new BasicDBObject("$gt", pricereview));
}
else
{
	pw.println("Error");
}

break;

case "product":
filterByRating = true;
if(prodname.equals("SmartPhone"))
{
query.put("category",prodname);
}
else{
	pw.println("Error");
}

break;

case "city":
filterByRating = true;
if(retcity.equals("Chicago"))
{
query.put("retailerCity",retcity);
}
else
{
	pw.println("Error");
}
break;
}
}

//query.put("rating", new BasicDBObject("$lt", 3));
DBCursor dbCursor = myReviews.find(query);
constructTableContent(dbCursor, pw);

//}

}
}

public void constructTableContent(DBCursor dbCursor,PrintWriter pw)
{
String tableData = "";
pw.print("<table class='gridtable'>");
while (dbCursor.hasNext())
{
BasicDBObject bobj = (BasicDBObject) dbCursor.next();
tableData = "<tr><td align='center' colspan='2'>Review</td></tr><tr><td>Name: </td><td>" + bobj.getString("category") + "</td></tr>" + "<tr><td>Price:</td><td>" + bobj.getString("price") + "</td></tr>"
+ "<tr><td>Rating:</td><td>" + bobj.getString("rating") + "</td></tr>" + "</td><tr>" + "<tr><td>Review Text:</td><td>" + bobj.getString("reviewtext")+"</td><tr>" + "<tr><td>Retailer City:</td><td>" + bobj.getString("retailerCity")+"</td><tr>";

pw.print(tableData);
}
pw.print("</table>");
//No data found
if(dbCursor.count() == 0)
{
tableData = "<h2>No Data Found</h2>";
pw.print(tableData);
 pw.println("");
pw.println("");


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
}

