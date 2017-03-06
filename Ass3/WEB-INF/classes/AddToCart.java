import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AddToCart extends HttpServlet {
	
	protected void doGet(HttpServletRequest request , HttpServletResponse response) throws ServletException,IOException
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession(true);
		
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
//pw.println("<img class='header-image' src='images/image.jpg' alt='Buildings' />");

pw.println("<div id='body'>");		

pw.println("<section id='content'>");
pw.println("<article>");

		CartManage shcart;
		shcart = (CartManage)session.getAttribute("cart");
		if(shcart == null);
		{
			shcart = new CartManage();
			session.setAttribute("cart",shcart);
		}
		
		String username = request.getParameter("username");
		String productName = request.getParameter("productName");
		String price = request.getParameter("price");
		session.setAttribute("productName",productName);
		session.setAttribute("price",price);
		shcart.addtoCart(productName,price);
		session.setAttribute("cart",shcart);
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<title>BestDeal-Cart</title>");
		pw.println("<head>");
		pw.println("<body>");
		pw.println("Item successfully added to cart!<br><br>");
		
		pw.println("<table>");
		
		HashMap<String,String> items = shcart.getItems();
		pw.println("<table border='1px'>");
		
		for (String k:items.keySet())
		{
			pw.println("<tr><td>"+k+" - </td><td>"+"$"+items.get(k)+"</td></tr>");//<td><form action='checkout' method='get'><input type='hidden' name='name' value="+arr1[1]+"><input type='hidden' name='price' value="+arr1[4]+"><input type='submit' name='proceed' value='Proceed To Checkout'></form></td></tr>");
			pw.println("<td><form action='checkout'><input type='submit' name='checkout' value='Checkout'><input type='hidden' name='productName' value="+k+"><input type='hidden' name='price' value="+items.get(k)+"><input type='hidden' name='username' value="+username+"></td><td></form></td></tr>");
		}

		pw.println("</table>");
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
	 

		pw.println("</body>");
		pw.println("</html>");
		
		
	}
	
	
	
	
}