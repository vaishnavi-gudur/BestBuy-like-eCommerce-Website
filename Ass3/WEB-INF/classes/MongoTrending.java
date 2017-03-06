import java.*;
import java.io.*;
import java.util.*;
import java.util.Iterator;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.AggregationOutput;

import com.mongodb.ServerAddress;

import javax.servlet.*;
import javax.servlet.http.*;

import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import org.bson.Document;

import static java.util.Arrays.asList;

public class MongoTrending extends HttpServlet
{
static DBCollection myReviews;
protected void doGet(HttpServletRequest request , HttpServletResponse response) throws ServletException,IOException
{
response.setContentType("text/html");
PrintWriter pw = response.getWriter();	
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
	
int returnLimit = 5;
MongoClient mongo;
mongo = new MongoClient("localhost", 27017);
DB db = mongo.getDB("CustomerReviews");
myReviews= db.getCollection("myReviews");
DBObject limit = new BasicDBObject();
limit.put("$limit",5);
DBObject sort = new BasicDBObject();
sort.put("rating",-1);
BasicDBObject regexQuery = new BasicDBObject();
regexQuery.put("rating",new BasicDBObject("$gte",4));
DBCursor dbCursor = myReviews.find(regexQuery).limit(returnLimit).sort(sort);
int i=1;
String temp1 = "";
pw.println("Top 5 Most Liked Products based on review rating:");
pw.println("<br>");
while(dbCursor.hasNext())
{
BasicDBObject dbObject = (BasicDBObject)dbCursor.next();
String product = dbObject.getString("productModelName");
String pcategory = dbObject.getString("category");
String prating = dbObject.getString("rating");

temp1="<table border='1px'><tr><td>Product Name</td><td>Category</td><td>Rating</td></tr><tr><td>"+product+"</td><td>"+pcategory+"</td><td>"+prating+"</td></tr>";
pw.println(temp1);
//i++;
}


//pw.println("<br>");

pw.println("<table>");
BasicDBObject groups = new BasicDBObject("_id", 0);
groups.put("count",new BasicDBObject("$sum",1));
groups.put("_id", "$productModelName");
BasicDBObject groupa = new BasicDBObject("$group", groups);
BasicDBObject sorti = new BasicDBObject();
BasicDBObject projects = new BasicDBObject("_id",0);
projects.put("value", "$_id");
projects.put("rating","$count");
BasicDBObject projectis = new BasicDBObject("$project", projects);
sorti.put("rating",-1);
BasicDBObject orderbyname =new BasicDBObject("$sort",sorti);
BasicDBObject limitby =new BasicDBObject("$limit",5);
BasicDBObject match = new BasicDBObject("$match",new BasicDBObject("rating",new BasicDBObject("$gte",3)));
AggregationOutput aggregates;
aggregates = myReviews.aggregate(groupa,projectis,orderbyname,limitby,match);
constructGroup(aggregates,pw);
pw.println("</table>");


pw.print("<table id='bestseller'>");
BasicDBObject groupFields = new BasicDBObject("_id", 0);
groupFields.put("count",new BasicDBObject("$sum",1));
groupFields.put("_id", "$retailerZip");
BasicDBObject group = new BasicDBObject("$group", groupFields);
BasicDBObject sortu = new BasicDBObject();
BasicDBObject projectFields = new BasicDBObject("_id",0);
projectFields.put("value", "$_id");
projectFields.put("rating","$count");
BasicDBObject projectu = new BasicDBObject("$project", projectFields);
sortu.put("rating",-1);
BasicDBObject orderbyte=new BasicDBObject("$sort",sortu);
BasicDBObject limiten=new BasicDBObject("$limit",5);
BasicDBObject matchin = new BasicDBObject("$match",new BasicDBObject("rating",new BasicDBObject("$gte",3)));
AggregationOutput aggregateu;
aggregateu = myReviews.aggregate(group,projectu,orderbyte,limiten,matchin);
constructGroupByContent(aggregateu,pw);
pw.println("<br>");
pw.print("</table>");
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
		
public void constructGroup(AggregationOutput aggregates, PrintWriter pw)
{
pw.println("<br>");
pw.println("Top 5 Most Liked products based on Number of Reviews:");
pw.println("<br>");

String tableData="";
for (DBObject result1 : aggregates.results()) {
BasicDBObject bobj = (BasicDBObject) result1;
tableData = "<table border='1px'><tr><td>ProductModelName:</td><td>Number of Ratings</td></tr><tr><td>"+bobj.getString("value")+"</td><td>"+bobj.getString("rating")+"</td></tr></table>";
pw.println(tableData);

}
//pw.println(tableData);
pw.println("<br>");

}


public void constructGroupByContent(AggregationOutput aggregateu, PrintWriter pw)
{
	pw.println("Top 5 Zip-Codes where Maximum Number of Products were Sold:");
pw.println("<br>");
String tborder="";

for (DBObject result : aggregateu.results()) {
BasicDBObject bobjq = (BasicDBObject) result;
/*String tableData = "<tr><td> "+bobj.getString("value")+"</td>&nbsp"
+ "<td>"+bobj.getString("rating")+"</td></tr>";
pw.println(tableData);*/
tborder = "<table border='1px'><tr><td>Zip Code</td><td>Maximum Number of Ratings</td></tr><tr><td>"+bobjq.getString("value")+"</td><td>"+bobjq.getString("rating")+"</td></tr></table>";
pw.println(tborder);
pw.println("");
pw.println("");
}
}

}




