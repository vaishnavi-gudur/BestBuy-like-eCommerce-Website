import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DealUtility extends HttpServlet 
{
protected void doGet(HttpServletRequest request , HttpServletResponse response)throws ServletException,IOException
{
response.setContentType("text/html");
PrintWriter pw = response.getWriter();
HashMap<String,Product> selectedproducts=new HashMap<String,Product>();
try
{
pw.print("<div id='content'>");
pw.print("<div class='post'>");
pw.print("<h2 class='title'>");
pw.print("<a href='#'>Welcome to GameSpeed </a></h2>");
pw.print("<div class='entry'>");
pw.print("<br> <br>");
pw.print("<h2>The world trusts us to deliver SPEEDY service for video-gaming fans</h2>");
pw.print("<br> <br>");
pw.print("<h1>We beat our competitors in all aspects. Price-Match Guaranteed</h2>");
String line=null;
HashMap<String,Product> productmap=MySQLDataStoreUtilities.getData();

for(Map.Entry<String, Product> entry : productmap.entrySet())
{
if(selectedproducts.size()<3 && !selectedproducts.containsKey(entry.getKey()))
{
BufferedReader reader = new BufferedReader(new FileReader (new File("C:\\apache-tomcat-7.0.34\\webapps\\Ass3\\DealMatches.txt")));
line=reader.readLine();
if(line==null)
{ 
pw.print("<h2 align='center'>No Offers Found</h2>");
break;
}
else
{
do 
{
if(line.contains(entry.getKey()))
{
pw.print("<h2>"+line+"</h2>");
pw.print("<br>");
selectedproducts.put(entry.getKey(),entry.getValue());
break;
}
}while((line = reader.readLine()) != null);
}
}
}

Iterator it = selectedproducts.entrySet().iterator();
	 pw.println("<table><tr>");
	 
	 while (it.hasNext())
	{
		
		Map.Entry pi = (Map.Entry)it.next();
		Product p=(Product)pi.getValue();
		
		{
			pw.println("<td><table>");
			pw.println("<tr><td>" + p.getproductName()+"</td></tr>");
			pw.println("<tr><td>"+ p.getPrice()+"</td></tr>");
		    pw.println("<tr><td>" + "<img src='images/"+p.getproductName()+".png'></td></tr>");
		
		
		
		pw.println("<tr><td><form action='/BestDeal/ReviewPage'>"+
		"<input type='hidden' name='id' value='" + p.getId()+"'>"+
		"<input type='hidden' name='productName' value='" + p.getproductName()+"'>"+
		"<input type='hidden' name='price' value='" + p.getPrice()+"'>"+
		"<input type='hidden' name='retailerzip' value='" + p.getRetailerZip()+"'>"+
		"<input type='hidden' name='retailerstate' value='" + p.getRetailerState()+"'>"+
		"<input type='hidden' name='retailercity' value='" + p.getRetailerCity()+"'>"+
		"<input type='hidden' name='category' value='" + p.getCategory()+"'>"+
		"<input type='submit' value='Review'></form ></td></tr>");
		
		pw.println("<tr><td><form action='/BestDeal/CartServletMain'>"+
		"<input type='hidden' name='id' value='" + p.getId()+"'>"+
		"<input type='hidden' name='productName' value='" + p.getproductName()+"'>"+
		"<input type='hidden' name='price' value='" + p.getPrice()+"'>"+
		"<input type='hidden' name='retailerzip' value='" + p.getRetailerZip()+"'>"+
		"<input type='hidden' name='retailerstate' value='" + p.getRetailerState()+"'>"+
		"<input type='hidden' name='retailercity' value='" + p.getRetailerCity()+"'>"+
		"<input type='hidden' name='category' value='" + p.getCategory()+"'>"+
		"<input type='submit' value='BuyNow'></form ></td></tr>");
		
		pw.println("</table></td>");
		
		
		
		}
		
		
	}
	
	pw.println("</tr></table>");
}
catch(Exception e)
{}
	}
	}