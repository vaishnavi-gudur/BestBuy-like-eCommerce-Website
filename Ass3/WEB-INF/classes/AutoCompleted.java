import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AutoCompleted extends HttpServlet
{
private ProductData proData = new ProductData();
private HashMap products = proData.getProducts();
private ServletContext context;

@Override
public void init(ServletConfig config) throws ServletException {
    this.context = config.getServletContext();
}
protected void doGet(HttpServletRequest request , HttpServletResponse response) throws ServletException,IOException
{
String action = request.getParameter("action");
    String targetId = request.getParameter("id");
    StringBuffer sb = new StringBuffer();

    if (targetId != null) {
        targetId = targetId.trim().toLowerCase();
    } else {
        context.getRequestDispatcher("/error.jsp").forward(request, response);
    }

    boolean namesAdded = false;
    if (action.equals("complete")) {

        // check if user sent empty string
        if (!targetId.equals("")) {

            Iterator it = products.keySet().iterator();

            while (it.hasNext()) {
                String id = (String) it.next();
                Product product = (Product) products.get(id);

                if ( // targetId matches first name
                     product.getproductName().toLowerCase().startsWith(targetId)) {

                    sb.append("<product>");
                    sb.append("<id>" + product.getId() + "</id>");
                    sb.append("<productName>" + product.getproductName() + "</productName>");
                    sb.append("</product>");
                    namesAdded = true;
                }
            }
        }

        if (namesAdded) {
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write("<products>" + sb.toString() + "</products>");
        } else {
            //nothing to show
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
    if (action.equals("lookup")) {

        // put the target composer in the request scope to display 
        if ((targetId != null) && products.containsKey(targetId.trim())) {
            request.setAttribute("product", products.get(targetId));
            context.getRequestDispatcher("/composer.jsp").forward(request, response);
        }
    }
}
}
