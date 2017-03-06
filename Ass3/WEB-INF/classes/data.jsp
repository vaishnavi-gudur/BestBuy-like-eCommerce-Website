<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@page import="javax.servlet.*" %>
<%@page import="javax.servlet.http.*" %>

<!doctype html>
<html>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
<title>BestDeal</title>
<link rel='stylesheet' href='styles.css' type='text/css' />

<script src='http;//html5shiv.googlecode.com/svn/trunk/html5.js'></script>
</head>
<body>
<div id='container'>
<header>
<h1><a href='Home.jsp'>Best<span>Deal</span></a></h1>
<h2>The Best You Can Get</h2>
</header>
<nav>
<ul>
<li class='start selected'><a href='Home.jsp'>Home</a></li>
<li class=''><a href='#'>SmartPhones</a></li>
<li><a href='#'>Laptops</a></li>
<li><a href='#'>Tablets</a></li>
<li><a href='#'>TV</a></li>
<li><a href='#'>Contact</a></li>	
<li><a href='trending.jsp'>Trending</li>
<li class='end'><a href='logout'>Logout</a></li>
</ul>
</nav>


<div id='body'>	

<section id='content'>
<article>
<%
out.println("<form action='datanalytics' method='get'><input type='checkbox' name='queryCheckBox' value='product'/>"+
"Select&nbsp&nbsp&nbspProduct Name:&nbsp&nbsp<select name='product' value='product'><option value='SmartPhone'>SmartPhone</option>"+
"<option value='tablet'>Tablet</option><option value='laptop'>Laptop</option><option value='tv'>TV</option></select><br>"+
"<input type='checkbox' name='queryCheckBox' value='price'>Select&nbsp&nbsp&nbsp Product Price:&nbsp&nbsp"+
"<input type='text' name='price'><br>"+
"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"+
"<input type='radio' name='compareRating1' value='Equals'/>Equals<br>"+
"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"+
"<input type='radio' name='compareRating1' value='LessThan'/>Less Than<br>"+
"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"+
"<input type='radio' name='compareRating1' value='Greater Than'/>Greater Than<br><input type='checkbox' name='queryCheckBox' value='rating'>"+
"Select&nbsp&nbsp&nbsp"+

"Review Rating:&nbsp&nbsp&nbsp<select name='rating' value='rating'><option value='1'>1</option>"+
"<option value='2'>2</option><option value='3'>3</option><option value='4'>4</option><option value='5'>5</option></select>"+
"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"+
"<input type='radio' name='compareRating' value='Equals'/>Equals<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"+
"<input type='radio' name='compareRating' value='LessThan'/>Less Than<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"+
"<input type='radio' name='compareRating' value='GreaterThan'/>Greater Than<br>"+ "<input type='checkbox' name='queryCheckBox' value='city'/>" + "Select&nbsp&nbsp&nbspRetailer City:&nbsp&nbsp<input type='text' name='city'/>"+"<input type='submit' name='submit' value='Find Data'/>");
out.println("<input type='hidden' name='rating' value='rating'/><input type='hidden' name='city' value='city'/><input type='hidden' name='price' value='price'/><input type='hidden' name='product' value='product'/><input type='hidden' name='compareRating' value='compareRating'/>");
 out.println("");
out.println("");
%>

</article>
<article class='expanded'>
<%
out.println("");

out.println("");

out.println("");

out.println("");

out.println("");
out.println("");
%>
		</article>
        </section>
        
        <aside class='sidebar'>
		<ul>
        <li>
        <h4>SmartPhones</h4>
        <ul>
        <li><a href='samsung.jsp'>Samsung</a></li>
        <li><a href='htc.jsp'>HTC</a></li>
        <li><a href='apple.jsp'>Apple</a></li>
        <li><a href='sony.jsp'>SONY</a></li>
        <li><a href='acer.jsp'>Acer</a></li>
        </ul>
        </li>
                
        <li>
        <h4>Laptops</h4>
        <ul>
        <li><a href='dell.jsp'>Dell</a></li>
		<li><a href='lenovo.jsp'>Lenovo</a></li>
		<li><a href='hp.jsp'>HP</a></li>
		<li><a href='panasonic.jsp'>Panasonic</a></li>
		<li><a href='applaptop.jsp'>Apple</a></li>
        
        </li>
        </ul>
        </li>
                
        <li>
        <h4>Tablets</h4>
        <ul>
		<li><a href='lgtab.jsp'>LG</a></li>
		<li><a href='acertab.jsp'>Acer</a></li>
		<li><a href='asustab.jsp'>Asus</a></li>
		<li><a href='samsungtab.jsp'>Samsung</a></li>
		<li><a href='googletab.jsp'>Google</a></li>
        </li>
		</ul>
        </li>
                
        <li>
        <h4>TV</h4>
        <ul>
		<li><a href='pantv.jsp'>Panasonic</a></li>
		<li><a href='sonytv.jsp'>SONY</a></li>
		<li><a href='toshibatv.jsp'>Toshiba</a></li>
		<li><a href='insigniatv.jsp'>Insignia</a></li>
		<li><a href='hitachitv.jsp'>Hitachi</a></li>
        </li>
		</ul>
        </li>
        
        
        </aside>
    	<div class='clear'></div>
		</div>
		<footer>
        <div class='footer-bottom'>
        <p>&copy; YourSite 2013. <a href='http;//zypopwebtemplates.com/'>Free CSS Website Templates</a> by ZyPOP</p>
        
		</footer>
		</div>
		</body>
		</html>
