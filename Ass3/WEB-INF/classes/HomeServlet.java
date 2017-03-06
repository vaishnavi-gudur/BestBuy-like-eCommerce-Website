import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class HomeServlet extends HttpServlet
{
	String temp="";
	
	
protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
{
response.setContentType("text/html;charset=UTF-8");
PrintWriter pw=response.getWriter();

//pw.println("leftnavigation.html");
pw.println("<!doctype html>");
pw.println("<html>");
pw.println("<head>");
pw.println("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
pw.println("<script type='text/javascript' src='javascript.js'></script>");
pw.println("<title>BestDeal</title>");
pw.println("<link rel='stylesheet' href='styles.css' type='text/css' />");

pw.println("<script src='http;//html5shiv.googlecode.com/svn/trunk/html5.js'></script>");
//-->
pw.println("</head>");
pw.println("<body onload='init()'>");

pw.println("<div id='container'>");
pw.println("<header>");
pw.println("<h1><a href='myhome'>Best<span>Deal</span></a></h1>");
pw.println("<h2>The Best You Can Get</h2>");
pw.println("</header>");

pw.println("<nav>");
pw.println("<ul>");
pw.println("<li class='start selected'><a href='myhomeservlet'>Home</a></li>");
pw.println("<li><a href='smartphone'>SmartPhones</a></li>");
pw.println("<li><a href='laptop'>Laptops</a></li>");
pw.println("<li><a href='tablet'>Tablets</a></li>");
pw.println("<li><a href='tv'>TV</a></li>");
//pw.println("<li><a href='#'>Contact</a></li>");
pw.println("<li><a href='abcd.html'>Login</a></li>");
pw.println("<li><input type='text' style='position:absolute;top:28%' size='40' id='complete-field' class='input' onkeyup='doComplete();' placeholder='Search Here.....'></li>");
pw.println("<div id='autofillform'>");
pw.println("<form name='autofillform' action='autocompletion'>");

pw.println("<div id='auto-row'>");
pw.println("<div id='complete-table'/>");
pw.println("</div>");
pw.println("</div>");
pw.println("</form>");
pw.println("</div>");
pw.println("</ul>");
pw.println("</nav>");
//pw.println("<img class='header-image' src='images/image.jpg' alt='Buildings' />");

pw.println("<div id='body'>");		

pw.println("<section id='content'>");
pw.println("<article>");
pw.println("<h2>Welcome to BestDeal</h2>");

//pw.println("<div class='article-info'>Posted on <time datetime='2013-05-14'>14 May</time> by <a href='#' rel='author'>Joe Bloggs</a></div>");

//pw.println("<ul class='styledlist'>");
//pw.println("<li>Firefox</li>");
//pw.println("<li>Opera</li>");
//pw.println("<li>IE</li>");
//pw.println("<li>Safari</li>");
//pw.println("<li>Chrome</li>");
//pw.println("</ul>");

pw.println("");
pw.println("");
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
			
		
		if (bcategory) {
			temp = temp + new String(ch, start, length)+"-";
			bcategory = false;
		}	
	
		
	}
	
};
try{
	SAXParser saxParser = factory.newSAXParser();
	saxParser.parse(context.getRealPath("products.xml"), handler);	
	Map<String,String> hm = new HashMap<String,String>();
	String arr[] = temp.split("-");
	MySQLDataStoreUtilities mys = new MySQLDataStoreUtilities();
	for(int i=0;i<arr.length; i++)
	{
		
		String arr1[] = arr[i].split(",");		
		hm.put(arr1[0],arr1[1]+arr1[2]+arr1[3]+arr1[4]+arr1[5]+arr1[6]+arr1[7]+arr1[8]+arr1[9]);
	
	String productName = arr1[1];

		if(!mys.selectProduct(productName))
		{
		mys.insertProduct(arr1[0],arr1[1],arr1[2],arr1[3],arr1[4],arr1[5],arr1[6],arr1[7],arr1[8],arr1[9]);
		}
	}
	}
	catch(Exception e)
	{
		
	}

pw.println("</article>");
pw.println("<article class='expanded'>");
//pw.println("<h2>Buy unbranded</h2>");
pw.println("");
pw.println("<img src='images/item.jpe' alt='Gadgets'/>");
pw.println("");

pw.println("");

pw.println("");
pw.println("<p><h3>We at BestDeal offer high quality,fast service.</h3></p>");	
pw.println("<p><h3>Price-match Guaranteed</h3></p>");

		String output="";
		output = DealMatch.get();
		pw.println(output);


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
		pw.println("<li><a href='pantv'>Panasonic</a></li>");
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