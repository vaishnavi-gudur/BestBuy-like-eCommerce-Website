import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class LGServlet extends HttpServlet{
String temp = "";

protected void doGet(HttpServletRequest request , HttpServletResponse response) throws ServletException,IOException
{
response.setContentType("text/html");
PrintWriter pw = response.getWriter();

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



ServletContext context = request.getServletContext();
SAXParserFactory factory = SAXParserFactory.newInstance();

DefaultHandler handler = new DefaultHandler() {

	boolean bid = false;
	boolean bname = false;
	boolean bbrand = false;
	boolean bcondition = false;
	boolean bprice = false;
	boolean bretailername = false;
	boolean bretailerstate = false;
	boolean bretailercity = false;
	boolean bretailerzip = false;
	boolean bmanufacturerrebate = false;
	boolean bcategory = false;
	
	public void startElement(String uri, String localName,String qName,
                Attributes attributes) throws SAXException {

		System.out.println("Start Element :" + qName);
		
		if (qName.equalsIgnoreCase("ID")) {
			bid = true;
		}

		if (qName.equalsIgnoreCase("PRODUCTNAME")) {
			bname = true;
		}

		if (qName.equalsIgnoreCase("BRAND")) {
			bbrand = true;
		}

		if (qName.equalsIgnoreCase("CONDITION")) {
			bcondition = true;
		}

		if (qName.equalsIgnoreCase("PRICE")) {
			bprice = true;
		}
		
		if (qName.equalsIgnoreCase("RETAILERNAME")) {
			bretailername = true;
		}
		
		if (qName.equalsIgnoreCase("RETAILERCITY")) {
			bretailercity = true;
		}
		
		if (qName.equalsIgnoreCase("RETAILERSTATE")) {
			bretailerstate = true;
		}
		
		if (qName.equalsIgnoreCase("RETAILERZIP")) {
			bretailerzip = true;
		}

		if (qName.equalsIgnoreCase("MANUFACTURERREBATE")) {
			bmanufacturerrebate = true;
		}
		
		if (qName.equalsIgnoreCase("CATEGORY")) {
			bcategory = true;
		}

	}

	public void endElement(String uri, String localName,
		String qName) throws SAXException {

		//System.out.println("End Element :" + qName);

	}

	public void characters(char ch[], int start, int length) throws SAXException {

		if (bid) {
			temp = temp + new String(ch, start, length)+",";
			bid = false;
		}
		
		
		if (bname) {
			temp = temp + new String(ch, start, length)+",";
			bname = false;
		}

		if (bbrand) {
			temp = temp + new String(ch, start, length)+",";
			bbrand = false;
		}

		if (bcondition) {
			temp = temp + new String(ch, start, length)+",";
			bcondition = false;
		}

		if (bprice) {
			temp = temp + new String(ch, start, length)+",";
			bprice = false;
		}
	
		if (bretailername) {
			temp = temp + new String(ch, start, length)+",";
			bretailername = false;
		}
		
		if (bretailercity) {
			temp = temp + new String(ch, start, length)+",";
			bretailercity = false;
		}
		
		if (bretailerstate) {
			temp = temp + new String(ch, start, length)+",";
			bretailerstate = false;
		}
		
		if (bretailerzip) {
			temp = temp + new String(ch, start, length)+",";
			bretailerzip = false;
		}
		
		if (bmanufacturerrebate) {
			temp = temp + new String(ch, start, length)+",";
			bmanufacturerrebate = false;
		}	
		
		if (bcategory) {
			temp = temp + new String(ch, start, length)+"-";
			bcategory = false;
		}	
	
	}
	
};

try {
	SAXParser saxParser = factory.newSAXParser();
	//pw.println("HELLO");
	saxParser.parse(context.getRealPath("lgtab.xml"), handler);
	//pw.println(temp);
		
	Map<String,String> hm = new HashMap<String,String>();
	String arr[] = temp.split("-");
	for(int i=0;i<arr.length; i++)
	{
		
		String arr1[] = arr[i].split(",");
		//pw.println(arr1[1]);
		//pw.println(arr1[1]+","+arr1[2]+","+arr1[3]);
hm.put(arr1[0],"<html><head></head><body><form action='addtocart' method='get'><table border='1 px'><tr><td><img src='images/samsung.jpe'></td></tr><tr><td>Name:&nbsp&nbsp&nbsp&nbsp"+arr1[1]+"</td></tr><tr><td>Condition:&nbsp&nbsp&nbsp"+arr1[3]+"</td></tr><tr><td>Price:&nbsp&nbsp&nbsp&nbsp"+arr1[4]+"</td></tr><input type='hidden' name='productName' value= "+arr1[1]+"/><input type='hidden' name='brand' value="+arr1[2]+"><input type='hidden' name='price' value="+arr1[4]+"><input type='hidden' name='retailername' value="+arr1[5]+"><input type='hidden' name='retailercity' value="+arr1[6]+"><input type='hidden' name='retailerstate' value="+arr1[7]+"><input type='hidden' name='retailerzip' value="+arr1[8]+"><input type='hidden' name='manufacturerrebate' value="+arr1[9]+"><input type='hidden' name='category' value="+arr1[10]+"><tr><td><input type='submit' value='Add To Cart'></td></tr></form><form action='writereview' method='get'><input type='hidden' name='pid' value="+arr1[0]+"><input type='hidden' name='productName' value= "+arr1[1]+"><input type='hidden' name='brand' value="+arr1[2]+"><input type='hidden' name='price' value="+arr1[4]+"><input type='hidden' name='retailername' value="+arr1[5]+"><input type='hidden' name='retailercity' value="+arr1[6]+"><input type='hidden' name='retailerstate' value="+arr1[7]+"><input type='hidden' name='retailerzip' value="+arr1[8]+"><input type='hidden' name='manufacturerrebate' value="+arr1[9]+"><input type='hidden' name='category' value="+arr1[10]+"><tr><td><input type='submit' value='Write Review'></td></tr></form><tr><td><form action='mongofind' method='get'><input type='hidden' name='pid' value="+arr1[0]+"/><input type='hidden' name='productModelName' value= "+arr1[1]+"/><input type='hidden' name='brand' value="+arr1[2]+"/><input type='hidden' name='price' value="+arr1[4]+"/><input type='hidden' name='retailername' value="+arr1[5]+"/><input type='hidden' name='retailercity' value="+arr1[6]+"/><input type='hidden' name='retailerstate' value="+arr1[7]+"/><input type='hidden' name='retailerzip' value="+arr1[8]+"/><input type='hidden' name='manufacturerrebate' value="+arr1[9]+"/><input type='hidden' name='category' value="+arr1[10]+"/><input type='submit' value='View Review'/></form></td></tr></table>");
	}
	
	for(String key:hm.keySet())
	{
		//pw.println(key);
		//pw.println(key);
		pw.println(hm.get(key)+"<br/>");
		//pw.println(hm.get(key)+"<br/><br/>");
	}
	//String key = arr[0];
	//String values = arr[1];
	//hm.put(key,values);
	//pw.println("Name:" +key);
	//pw.println("<br>");
	//pw.println("Brand:"+hm.get(key));
	
	
	
	
} catch (Exception e) {
       e.printStackTrace();
     }
	 
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


