import java.*;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class AutoComplete extends HttpServlet
{
protected void doGet(HttpServletRequest request , HttpServletResponse response)throws ServletException,IOException
{
	String act = request.getParameter("action");
	String sid = request.getParameter("searchId");
try
{
StringBuffer sb = new StringBuffer();
boolean namesAdded = false;
if (act.equals("complete"))
{
if (!sid.equals(""))
{ 
AjaxUtility a=new AjaxUtility();
sb=a.readData(sid);
if(sb!=null || !sb.equals(""))
{
namesAdded=true;
}
if (namesAdded)
{
response.setContentType("text/xml");
response.getWriter().write("<products>" + sb.toString() + "</products>");
}
}
}
}
catch(Exception e)
{}
}
}