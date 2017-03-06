import java.util.HashMap;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;

public class LogonServlet extends HttpServlet {
   
    
    /** 
     * Initializes the servlet with some usernames/password
    */  /*public void init() {
                users.put(username,password);
    }
		

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
		HttpSession session = request.getSession(false);
		String username = request.getParameter("username");
        String password = request.getParameter("password");
		Map users = new HashMap();
		users.put(username,password);
		
        if(username != null && username.length() != 0) {
            username = username.trim();
        }
        if(password != null && password.length() != 0) {
            password = password.trim();
        }
        if(username != null &&
            password != null) {
                String realpassword = (String)users.get(username);
                if(realpassword != null &&
                    realpassword.equals(password)) {
						response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter();
				out.println(username);
				out.println(password);

						out.println("<html>");
        out.println("<head>");
		out.println("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
out.println("<title>BestDeal</title>");
out.println("<link rel='stylesheet' href='styles.css' type='text/css' />");

out.println("<script src='http;//html5shiv.googlecode.com/svn/trunk/html5.js'></script>");
//-->
out.println("</head>");
out.println("<div id='container'>");
out.println("<header>");
out.println("<h1><a href='myhome'>Best<span>Deal</span></a></h1>");
out.println("<h2>The Best You Can Get</h2>");
out.println("</header>");
out.println("<nav>");
out.println("<ul>");
out.println("<li class='start selected'><a href='myhome'>Home</a></li>");
out.println("<li class=''><a href='#'>SmartPhones</a></li>");
out.println("<li><a href='#'>Laptops</a></li>");
out.println("<li><a href='#'>Tablets</a></li>");
out.println("<li><a href='#'>TV</a></li>");
//out.println("<li><a href='#'>Contact</a></li>");
//out.println("<li><a href='#'>My Orders</li>");
//out.println("<li><a href='#'>Account</a></li>");
out.println("<li><a href='logoutservlet'>Logout</a></li>");
out.println("</ul>");
out.println("</nav>");
//out.println("<img class='header-image' src='images/image.jpg' alt='Buildings' />");

out.println("<div id='body'>");		

out.println("<section id='content'>");
out.println("<article>");
out.println("<p style='position:fixed;left:40%;top:40%'>Welcome\t"+username+"</p>");
	out.println("<p style='position:fixed;left:75%;top:40%'><a href='#' style='text-decoration:none'>Orders&nbsp&nbsp</a><a href='#' style='text-decoration:none'>Cart</a></p>");
		
		out.println("");
out.println("");



out.println("</article>");
out.println("<article class='expanded'>");
//out.println("<h2>Buy unbranded</h2>");
out.println("");
out.println("");

out.println("");

out.println("");


		out.println("");
		out.println("");
		out.println("</article>");
        out.println("</section>");
        
        out.println("<aside class='sidebar'>");
		out.println("<ul>");
        out.println("<li>");
        out.println("<h4>SmartPhones</h4>");
        out.println("<ul>");
        out.println("<li><a href='samsung'>Samsung</a></li>");
        out.println("<li><a href='htc'>HTC</a></li>");
        out.println("<li><a href='apple'>Apple</a></li>");
        out.println("<li><a href='sony'>SONY</a></li>");
        out.println("<li><a href='acer'>Acer</a></li>");
        out.println("</ul>");
        out.println("</li>");
                
        out.println("<li>");
        out.println("<h4>Laptops</h4>");
        out.println("<ul>");
        out.println("<li><a href='dell'>Dell</a></li>");
		out.println("<li><a href='lenovo'>Lenovo</a></li>");
		out.println("<li><a href='hp'>HP</a></li>");
		out.println("<li><a href='panasonic'>Panasonic</a></li>");
		out.println("<li><a href='applaptop'>Apple</a></li>");
        //out.println("<p style='margin; 0;'>Aenean nec massa a tortor auctor sodales sed a dolor. Duis vitae lorem sem. Proin at velit vel arcu pretium luctus.<a href='#' class='readmore'>Read More &raquo;</a></p>");
        out.println("</li>");
        out.println("</ul>");
        out.println("</li>");
                
        out.println("<li>");
        out.println("<h4>Tablets</h4>");
        out.println("<ul>");
		out.println("<li><a href='lgtab'>LG</a></li>");
		out.println("<li><a href='acertab'>Acer</a></li>");
		out.println("<li><a href='asustab'>Asus</a></li>");
		out.println("<li><a href='samsungtab'>Samsung</a></li>");
		out.println("<li><a href='googletab'>Google</a></li>");
        out.println("</li>");
		out.println("</ul>");
        out.println("</li>");
                
        out.println("<li>");
        out.println("<h4>TV</h4>");
        out.println("<ul>");
		out.println("<li><a href='panasonictv'>Panasonic</a></li>");
		out.println("<li><a href='sonytv'>SONY</a></li>");
		out.println("<li><a href='toshibatv'>Toshiba</a></li>");
		out.println("<li><a href='insigniatv'>Insignia</a></li>");
		out.println("<li><a href='hitachitv'>Hitachi</a></li>");
        out.println("</li>");
		out.println("</ul>");
        out.println("</li>");
        
        
        out.println("</aside>");
    	out.println("<div class='clear'></div>");
		out.println("</div>");
		out.println("<footer>");
        //out.println("<div class='footer-content'>");
        //out.println("<ul>");
        //out.println("<li><h4>Proin accumsan</h4></li>");
        //out.println("<li><a href='#'>Rutrum nulla a ultrices</a></li>");
        //out.println("<li><a href='#'>Blandit elementum</a></li>");
        //out.println("<li><a href='#'>Proin placerat accumsan</a></li>");
        //out.println("<li><a href='#'>Morbi hendrerit libero </a></li>");
        //out.println("<li><a href='#'>Curabitur sit amet tellus</a></li>");
        //out.println("</ul>");
            
            
        //out.println("<div class='clear'></div>");
        //out.println("</div>");
        out.println("<div class='footer-bottom'>");
        out.println("<p>&copy; YourSite 2013. <a href='http;//zypooutebtemplates.com/'>Free CSS Website Templates</a> by ZyPOP</p>");
        //out.println("</div>");
		out.println("</footer>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	             //showPage(response, "Login Failure! Username or password is incorrect");
                }
        }  else {
			response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter();
			out.println("<head>");
        out.println("<title>Login Servlet Result</title>");  
        out.println("</head>");
        out.println("<body>");
        out.println("Login Failure!  You must supply a username and password");
        out.println("</body>");
        out.println("</html>");
		out.close();
		request.getRequestDispatcher("login.html").include(request,response);
            //showPage(response, "Login Failure!  You must supply a username and password");
        }
		
    } 
    
    /**
     * Actually shows the <code>HTML</code> result page
     */
    /*protected void showPage(HttpServletResponse response, String message)
    throws ServletException, java.io.IOException {
        response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Login Servlet Result</title>");  
        out.println("</head>");
        out.println("<body>");
        out.println("Welcome" +userid);
        out.println("</body>");
        out.println("</html>");
        out.close();
 
    }*/
    
    /** Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
		HttpSession session = request.getSession();
		
		String userid = request.getParameter("userid");
        String password = request.getParameter("password");
        
        processRequest(request, response);
    } 

    /** Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
		
        processRequest(request, response);
    }
}
