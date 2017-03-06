import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

 
import org.bson.Document;
 
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

 import org.bson.Document;

    import com.mongodb.BasicDBObject;
    import com.mongodb.MongoClient;
    import com.mongodb.ServerAddress;
    import com.mongodb.client.MongoCollection;
    import com.mongodb.client.MongoCursor;
    import com.mongodb.client.MongoDatabase;
 
public class MongodbFind extends HttpServlet 
{ 
	static DBCollection myReviews;
	protected void doGet(HttpServletRequest request , HttpServletResponse response) throws ServletException , IOException
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		HttpSession session = request.getSession();
		int prodprice = Integer.parseInt(request.getParameter("price"));
		//pw.println(prodprice);
		MongoClient mongo;
		mongo = new MongoClient("localhost", 27017);
		DB db = mongo.getDB("CustomerReviews");
		myReviews= db.getCollection("myReviews");
		BasicDBObject query = new BasicDBObject("price",prodprice);
		
		DBCursor dbCursor = myReviews.find(query);
		
			

/*BasicDBObject dbObject = (BasicDBObject)dbCursor.next();
String proname = dbObject.getString("productModelName");
String uname = dbObject.getString("username");
String uage = dbObject.getString("age");
String ugender = dbObject.getString("gender");
String uoccupation = dbObject.getString("occupation");
String urating = dbObject.getString("rating");
String ureviewtext = dbObject.getString("reviewtext");*/

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
pw.println("<li class=''><a href='#'>SmartPhones</a></li>");
pw.println("<li><a href='#'>Laptops</a></li>");
pw.println("<li><a href='#'>Tablets</a></li>");
pw.println("<li><a href='#'>TV</a></li>");
//pw.println("<li><a href='#'>Contact</a></li>");
pw.println("<li>\t\t\t\t</li>");
pw.println("<li><a href='logout'>Logout</a></li>");
pw.println("</ul>");
pw.println("</nav>");
//pw.println("<img class='header-image' src='images/image.jpg' alt='Buildings' />");

pw.println("<div id='body'>");		

pw.println("<section id='content'>");
pw.println("<article>");

pw.println("Reviews");
pw.println("<br>");
pw.println("<br>");

int i=1;
			String temp="";
			while(dbCursor.hasNext())
			{
				BasicDBObject dbObject = (BasicDBObject)dbCursor.next();
				temp = "<table border='1px'><tr><td>ProductName:</td><td>"+dbObject.getString("productModelName")+"</td></tr><tr><td>Username:</td><td>"+dbObject.getString("username")+"</td></tr><tr><td>Age:</td><td>"+dbObject.getString("age")+"</td></tr><tr><td>Gender:</td><td>"+dbObject.getString("gender")+"</td></tr><tr><td>Occupation:</td><td>"+dbObject.getString("occupation")+"</td></tr><tr><td>Review rating</td><td>"+dbObject.getString("rating")+"</td></tr><tr><td>Review:</td><td>"+dbObject.getString("reviewtext")+"</td></tr></table>";
				pw.println(temp);
				pw.println("<br>");
				//pw.println(dbCursor.next());
				i++;
			}
//pw.println("<table border='1px'><tr><td>Product Name</td><td>"+proname+"</td></tr><tr><td>UserName</td><td>"+uname+"</td></tr><tr><td>Age</td><td>"+uage+"</td></tr><tr><td>Gender</td><td>"+ugender+"</td></tr><tr><td>Occupation</td><td>"+uoccupation+"</td></tr><tr><td>Rating</td><td>"+urating+"</td></tr><tr><td>Review</td><td>"+ureviewtext+"</td></tr></table>");
pw.println("<br>");
	
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
