import java.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class MongoLove extends HttpServlet
{
protected void doGet(HttpServletRequest request , HttpServletResponse response) throws ServletException,IOException
{
response.setContentType("text/html");
PrintWriter pw = response.getWriter();

HttpSession session = request.getSession();


String age = request.getParameter("age");
String gender = request.getParameter("gender");
String occupation = request.getParameter("occupation");
String rating = request.getParameter("rating");
String reviewtext = request.getParameter("reviewtext");
String username = (String)session.getAttribute("username");
String productModelName = (String)session.getAttribute("productModelName");
String category = (String)session.getAttribute("category");
String price = (String)session.getAttribute("price");
String retailerName = (String)session.getAttribute("retailerName");
String retailerCity = (String)session.getAttribute("retailerCity");
String retailerState = (String)session.getAttribute("retailerState");
String retailerZip = (String)session.getAttribute("retailerZip");
String brand = (String)session.getAttribute("brand");
String manufacturerrebate = (String)session.getAttribute("manufacturerrebate");




HashMap<String, ArrayList<Review>> reviews= new HashMap<String, ArrayList<Review>>();
try
{
reviews=MongoDBDataStoreUtilities.selectReview();
}
catch(Exception e)
{ 
}
if(!reviews.containsKey(productModelName))
{
ArrayList<Review> arr = new ArrayList<Review>();
reviews.put(productModelName, arr);
}
ArrayList<Review> listReview = reviews.get(productModelName);
Review review = new Review(productModelName,username,category,price,retailerName,retailerCity,retailerState,retailerZip,brand,manufacturerrebate,age,gender,occupation,rating,reviewtext);
listReview.add(review);

for(String key : reviews.keySet())
{
pw.println(reviews.get(key));
}
}

}