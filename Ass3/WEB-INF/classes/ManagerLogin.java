import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class ManagerLogin extends HttpServlet {
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = response.getWriter();
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
		String usertype = request.getParameter("usertype");
		MySQLDataStoreUtilities mys = new MySQLDataStoreUtilities();
		
		//pw.println(username);
		//pw.println(password);
		if(mys.selectUser(username, password))
        {
			HttpSession session = request.getSession(true);
			session.setAttribute("username",username);
			
            RequestDispatcher rsd = request.getRequestDispatcher("welcomemanager");
            rsd.forward(request, response);
			//pw.println("Welcome&nbsp" +username);
        }
        else
        {
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
        //pw.println("<li class='end'><a href='#'>Contact</a></li>");
		pw.println("<li class='selected'><a href='#'>Login</a></li>");
		pw.println("<li><a href='trending'>Trending</a></li>");
        pw.println("</ul>");
		pw.println("</nav>");

		pw.println("<div id='body'>");
		
        pw.println("<section id='content'>");
		pw.println("</section>");
		pw.println("<aside class='sidebar'>");
		pw.println("<ul>");
        pw.println("<li>");
        pw.println("<h4>SmartPhones</h4>");
        pw.println("<ul>");
        pw.println("<li><a href='SmartServlet?brand=samsung'>Samsung</a></li>");
        pw.println("<li><a href='SmartServlet?brand=htc'>HTC</a></li>");
        pw.println("<li><a href='SmartServlet?brand=apple'>Apple</a></li>");
        pw.println("<li><a href='SmartServlet?brand=sony'>SONY</a></li>");
        pw.println("<li><a href='SmartServlet?brand=acer'>Acer</a></li>");
        pw.println("</ul>");
        pw.println("</li>");
                
        pw.println("<li>");
        pw.println("<h4>Laptops</h4>");
        pw.println("<ul>");
        pw.println("<li><a href='LaptopServlet?make=dell'>Dell</a></li>");
		pw.println("<li><a href='LaptopServlet?make=lenovo'>Lenovo</a></li>");
		pw.println("<li><a href='LaptopServlet?make=hp'>HP</a></li>");
		pw.println("<li><a href='LaptopServlet?make=panasonic'>Panasonic</a></li>");
		pw.println("<li><a href='LaptopServlet?make=apple'>Apple</a></li>");
        pw.println("</li>");
        pw.println("</ul>");
        pw.println("</li>");
                
        pw.println("<li>");
        pw.println("<h4>Tablets</h4>");
        pw.println("<ul>");
		pw.println("<li><a href='TabletServlet?brand=lg'>LG</a></li>");
		pw.println("<li><a href='TabletServlet?brand=acer'>Acer</a></li>");
		pw.println("<li><a href='TabletServlet?brand=asus'>Asus</a></li>");
		pw.println("<li><a href='TabletServlet?brand=samsung'>Samsung</a></li>");
		pw.println("<li><a href='TabletServlet?brand=google'>Google</a></li>");
        pw.println("</li>");
		pw.println("</ul>");
        pw.println("</li>");
                
        pw.println("<li>");
        pw.println("<h4>TV</h4>");
        pw.println("<ul>");
		pw.println("<li><a href='Tvservlet?brand=panasonic'>Panasonic</a></li>");
		pw.println("<li><a href='Tvservlet?brand=sony'>SONY</a></li>");
		pw.println("<li><a href='Tvservlet?brand=toshiba'>Toshiba</a></li>");
		pw.println("<li><a href='Tvservlet?brand=insignia'>Insignia</a></li>");
		pw.println("<li><a href='Tvservlet?brand=hitachi'>Hitachi</a></li>");
        pw.println("</li>");
		pw.println("</ul>");
        pw.println("</li>");
        
        
        pw.println("</aside>");
    	

	        
            pw.println("<p>&nbsp</p>");

            
            pw.println("<h3>Login</h3>");

            pw.println("<fieldset>");
                pw.println("<legend>Login</legend>");
				pw.println("<br>");
				pw.println("<p style='color:red'>Username or Password incorrect</p>");
				pw.println("<br>");
                pw.println("<form action='/Ass2/login' method='get'>");
                    pw.println("<p><label for='username'>UserID:</label>");
                    pw.println("<input name='username' id='username'  type='text' /></p>");
                    pw.println("<p><label for='password'>Password:</label>");
                    pw.println("<input name='password' id='password' type='password' /></p>");

                    
                    pw.println("<input type ='submit' value='login'/>");
                pw.println("</form>");
				pw.println("<br><a href='/Ass2/regiform'>New User?Sign Up here</a>");
				pw.println("<br><a href='managerlogin'>StoreManager Login</a>");
            pw.println("</fieldset>");
			
      		pw.println("</article>");
        pw.println("</section>");
        
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

           //RequestDispatcher rs = request.getRequestDispatcher("abcd.html");
           //rs.include(request, response);
		   //
        }
    }  
}