import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Welcome extends HttpServlet {
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = response.getWriter();
		
		HttpSession session = request.getSession();       
        String username = request.getParameter("username");
		session.setAttribute("username",username);
		String user = (String)session.getAttribute("username");
		Connection conn = null;
		String type="";
		try{ 
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bestdealdatabase","root","root");
		String selectQuery = "select username,usertype from Accounts where username = '"+user+"'";
		PreparedStatement pst = conn.prepareStatement(selectQuery);
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{
			type = rs.getString("usertype");
		}
		pw.println(type);
		}
		catch(Exception e)
		{
		}
		
		pw.println("<!doctype html>");
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<meta http-equiv='Content-Type' content='text/html charset=utf-8'/>");
		pw.println("<title>BestDeal - Login</title>");
		pw.println("<link rel='stylesheet' href='styles.css' type='text/css'/>");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<div id='container'>");
		pw.println("<header>");
    	pw.println("<h1><a href='/'>Best<span>Deal</span></a></h1>");
        pw.println("<h2>The Best You Can Get</h2>");
		pw.println("</header>");
		pw.println("<nav>");
    	pw.println("<ul>");
        pw.println("<li class='start'><a href='myhome'>Home</a></li>");
        pw.println("<li><a href='#'>Smartphones</a></li>");
        pw.println("<li><a href='#'>Laptops</a></li>");
        pw.println("<li><a href='#'>Tablets</a></li>");
		pw.println("<li><a href='#'>TV</a></li>");
		pw.println("<li><a href='trending'>Trending</li>");
        pw.println("<li class='end'><a href='logout'>Logout</a></li>");
			//<!--<li class="selected"><a href="#">Login</a></li>-->
        pw.println("</ul>");
		pw.println("</nav>");

		pw.println("<div id='body'>");
		
        pw.println("<section id='content'>");
		String output="";
		output = DealMatch.get();
		if(type.equals("customer"))
		{
		pw.println(output);
		}
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
		pw.println("<li><a href='LaptopServlet?make=apple'>Apple</a></li>");
        pw.println("</li>");
        pw.println("</ul>");
        pw.println("</li>");
                
        pw.println("<li>");
        pw.println("<h4>Tablets</h4>");
        pw.println("<ul>");
		pw.println("<li><a href='lg'>LG</a></li>");
		pw.println("<li><a href='acer'>Acer</a></li>");
		pw.println("<li><a href='TabletServlet?brand=asus'>Asus</a></li>");
		pw.println("<li><a href='samsung'>Samsung</a></li>");
		pw.println("<li><a href='google'>Google</a></li>");
        pw.println("</li>");
		pw.println("</ul>");
        pw.println("</li>");
                
        pw.println("<li>");
        pw.println("<h4>TV</h4>");
        pw.println("<ul>");
		pw.println("<li><a href='panasonic'>Panasonic</a></li>");
		pw.println("<li><a href='sony'>SONY</a></li>");
		pw.println("<li><a href='Tvservlet?brand=toshiba'>Toshiba</a></li>");
		pw.println("<li><a href='insignia'>Insignia</a></li>");
		pw.println("<li><a href='Tvservlet?brand=hitachi'>Hitachi</a></li>");
        pw.println("</li>");
		pw.println("</ul>");
        pw.println("</li>");
        
        
        pw.println("</aside>"); 
	        
            //pw.println("<p>&nbsp</p>");
			
			pw.println("Welcome&nbsp&nbsp" +username); 
			
			pw.println("<br>");
			
			if(type.equals("manager"))
			{
				pw.println("<a href='data'>Data Analysis</a>");
				pw.println("<a href='addproducts'>Add Products</a>");
				pw.println("<a href='updateproducts'>Update Products</a>");
				pw.println("<a href='deleteproducts'>Delete Products</a>");
			}
			  if(type.equals("salesman"))
			{
				pw.println("<a href='products'>View Products</a>");
				pw.println("<a href='orders'>View Customer Orders</a>");				
			}

				
            
        pw.println("<div class='clear'></div>");
    pw.println("</div>");
    pw.println("<footer>");
        pw.println("<div class='footer-content'>");
            pw.println("<div class='clear'></div>");
        pw.println("</div>");
        pw.println("<div class='footer-bottom'>");
            pw.println("<p>&copy YourSite 2010. <a href='http://zypopwebtemplates.com/'>Free CSS Web Templates</a> by ZyPOP</p>");
         pw.println("</div>");
    pw.println("</footer>");
pw.println("</div>");
pw.println("</body>");
pw.println("</html>");

			}
}