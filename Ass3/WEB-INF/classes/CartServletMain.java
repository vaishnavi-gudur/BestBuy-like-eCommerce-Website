import java.io.*;
import org.w3c.dom.*;
import javax.servlet.*;
import javax.xml.parsers.*;
import javax.servlet.http.*;
import java.util.*;

public class CartServletMain extends HttpServlet{ 

    public boolean isTextNode(Node n){
        return n.getNodeName().equals("#text");
    }

	public String isDelete(String input,String index)
	{

		String newOrderItem="";
		String itemorder =input;
		int i=0;
		String[] myData = itemorder.split(";");
		for (String s: myData) {
			if(s.contains(index)){
				myData[i]="";
				}
				if(myData[i]!=""){
					newOrderItem+=myData[i]+";";
					}
			
			i=i+1;
		}
		i=0;
		
			return newOrderItem;
	}
	
	
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		int i=0;
		String CustomerName="";
		ArrayList<Console> ConsolesOrdered= new ArrayList<Console>();
		ArrayList<Console> ConsolesOrdered1= new ArrayList<Console>();
		
		ConsolesOrdered1=(ArrayList<Console>)request.getSession().getAttribute("ConsolesOrdered");
		if(ConsolesOrdered1!=null){
			ConsolesOrdered=ConsolesOrdered1;
		}
		String isDelete1 ="false";
		isDelete1=request.getParameter("isDelete");
		String tableoutput ="";
		String itemId = request.getParameter("itemId");
        String itemName = request.getParameter("itemName");
		String itemPrice=request.getParameter("itemPrice");
		String RetailerCity=request.getParameter("RetailerCity");
		String RetailerState=request.getParameter("RetailerState");
		String RetailerZip=request.getParameter("RetailerZip");
		
		String itemorder=itemId+","+itemName+","+itemPrice+";";
		String checkid="";
		checkid=request.getParameter("id");
		
			String test="";
			if(isDelete1!=null){
			if(isDelete1.equals("true")){
			int count=0;
			int deletecount=0;
			for (Console c: ConsolesOrdered) {
			  test=String.valueOf(c.getId());
			 if(checkid.equals(test)){
				 deletecount=count;
			 }
			 
			count=count+1;
		}
		ConsolesOrdered.remove(deletecount);
			}}
			else{
				
				Console console = new Console();
            console.setId(Integer.parseInt(itemId));
            console.setName(itemName);
			console.setPrice(Double.parseDouble(itemPrice));
			console.setRetailerCity(RetailerCity);
			console.setRetailerState(RetailerState);
			console.setRetailerState(RetailerZip);
			ConsolesOrdered.add(console);
				
				
			}
			
		request.getSession().setAttribute("ConsolesOrdered", ConsolesOrdered);
		
		int number=0;
		
		
		double Total=0.0;
		double itemsTotal=0.0;
		tableoutput +="<table id='OrderTable' border=0>";
		tableoutput+="<tr><th>Item</th><th>Price</th><th>Number of Item</th></tr>";
		i=0;
		
		
		for (Console c: ConsolesOrdered) {
			 i+=1;
			int k=i-1;
			int id=c.getId();
			double price=c.getPrice();
			
			String city=c.getRetailerCity();
			String state=c.getRetailerState();
			tableoutput +="<td style='width:40%'>"+c.getName()+"</td>";
			tableoutput +="<td style='width:20% align:Centre'><input type='number'size='2' name='iprice"+i+"' id='iprice"+i+"' readonly  value='"+c.getPrice()+"'></td>";
			
			tableoutput +="<td style='width:10%'><input type='number'size='2' min='0' name='numberofitems"+i+"' id='numberofitems"+i+"'  value='1'></td>";
			tableoutput +="<td style='width:10%'><a href='/BestDeal/CartServletMain?isDelete=true&id="+id+"'>Delete2</a></td></tr>";
		 }
		
		
		
		tableoutput +="<tr><td></td><td><button type='button' onclick='myFunction()'>Calculate Total</button></td><td style='width:20%'>Total:<input type='number'size='2' name='Total' id='Total' ></td></tr></table>";
		
		
		request.getSession().setAttribute("numberofOrder", i);
		
		
		
		response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try{
		out.println("<head>"+
"<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"+
"<title>BestDeal-Laptops</title>"+
"<link rel='stylesheet' href='styles.css' type='text/css' />"+
"</head>"+
"<body onload = 'startTimer()'>"+
"<div id='container'>"+
"<header>"+
"<div id='Header_Container' >"+
"<h1 style='bottom:10px'><a href='index.html'>Best<span>Deal</span></a></h>	"+
"<form method='get' class='searchform' action='#' >"+
"<p><input type='text' size='32' value='' name='s' class='s' />	</p>"+
"</form>	"+
"</div>"+
"</header>"+
"<nav>"+
"<ul >"+
"<li class=''><a href='index.html'>Home</a></li>"+
"<li class=''><a href='/BestDeal/SaxParserServlet?Category=SmartPhone'>Smart Phones</a></li>"+
"<li class=''><a href='/BestDeal/SaxParserServlet?Category=Tablet'>Tablets</a></li>"+
 "<li class=''><a href='/BestDeal/SaxParserServlet?Category=Laptop'>Laptops</a></li>"+
  "<li class=''><a href='/BestDeal/SaxParserServlet?Category=TV'>Televisions</a></li>"+
"<li class=''><a href='/BestDeal/SaxParserServlet?Category=Contact'>Contact Us</a></li>"+
"<li style='float:right' class=''><a href='/BestDeal/DeleteOrderItemServlet'>LogOut</a></li>"+
"<li style='float:right' ><a>Cart("+i+")</a></li>"+
"<li  style='float:right' class='end'><a id='loginlink' href='login.html'>Hello,"+CustomerName+"</a></li>"+
			"<li style='float:right'><a image='images/Shopping_cart.png'  /> </a></li>"+
"</ul>"+
"</nav>"+
"<div id='body'>"+
"<section id='content'>"+
"<h2>"+
"<h1>Cart("+i+")</h1>"+
tableoutput+
"</h2>"+
"</section>"+
"<script>"+
"var flag='tru';"+
"function mydeleteFunction(i) {"+
"    document.getElementById('OrderTable').deleteRow(i);"+
"}"+

 " function myFunction() {"+
 "var tot=0;"+
	"for (i = 1; i <= "+i+"; i++) {"+
		"var y = document.getElementById('numberofitems'+i+'').value;"+
        "var z = document.getElementById('iprice'+i+'').value;"+
        "var x = y * z;"+
		"tot=tot + x;"+
		
		"}  "+
        "document.getElementById('Total').value = tot;"+
		"var a = document.createElement('a');"+
"var linkText = document.createTextNode('Check Out');"+
"a.appendChild(linkText);"+
"a.title = 'my title text';"+
"a.href = '/BestDeal/OrderServlet?Total='+tot;"+
		"if(flag=='tru')	{"+

"var tableRef =document.getElementById('OrderTable').getElementsByTagName('tbody')[0];"+
"var newRow   = tableRef.insertRow(tableRef.rows.length);"+
"var newCell  = newRow.insertCell(0);"+
		"newCell.appendChild(a);}"+
		"tot=0;"+
	"flag='';"+
		"}"+

    "</script>"+
"<aside class='sidebar'>"+
"<ul>"+
 "<li>"+
  "<h4>Smart Phones</h4>"+
  " <ul>"+
  "  <li><a href='/BestDeal/SaxParserServlet?Category=SmartPhone&Made=Apple'>Apple</a></li>"+
  "  <li><a href='/BestDeal/SaxParserServlet?Category=SmartPhone&Made=Samsung'>Samsung</a></li>"+
"</ul>"+
" </li>"+
 " <li>"+
  "<h4>Tablets</h4>"+
  "<ul>"+
 " <li><a href='/BestDeal/SaxParserServlet?Category=Tablet&Made=Apple'>Apple</a></li>"+
"<li><a href='/BestDeal/SaxParserServlet?Category=Tablet&Made=Samsung'>Samsung</a></li>"+
  "</ul>"+
  " </li>"+
  " <li>"+
  "<h4>Laptops</h4>"+
  " <ul>"+
   " <li><a href='/BestDeal/SaxParserServlet?Category=Laptop&Made=Apple'>Apple</a></li>"+
   " <li><a href='/BestDeal/SaxParserServlet?Category=Laptop&Made=Samsung'>Samsung</a></li>"+
"</ul>"+
 "</li>"+
 "<li>"+
 " <h4>Televisions</h4>"+
  " <ul>"+
   " <li><a href='/BestDeal/SaxParserServlet?Category=TV&Made=Panasonic'>Panasonic</a></li>"+
   " <li><a href='/BestDeal/SaxParserServlet?Category=TV&Made=Samsung'>Samsung</a></li>"+
"</ul>"+
" </li>  "+            
"</li>   "+            
"</ul>"+
"</aside>"+
 "<div class='clear'></div>"+
  "</div>"+
  
   "<footer>"+
"<div class='footer-bottom'>"+
"<p>&copy; YourSite 2013. <a href='http://zypopwebtemplates.com/'>Free CSS Website Templates</a> by ZyPOP</p>"+
 "</div>"+
"</footer>"+
"</div>"+
"</body>"+
	"</html>");
        }

        catch(Exception e){
            System.out.println(e);
        }
    }
}
