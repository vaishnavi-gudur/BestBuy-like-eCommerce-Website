<!doctype html>
<html>

<head>
<meta http-equiv='Content-Type' content='text/html'; charset='utf-8' />
<title>BestDeal</title>
<link rel='stylesheet' href='styles.css' type='text/css' />

<script src='http;//html5shiv.googlecode.com/svn/trunk/html5.js'></script>
</head>
<body>
<div id='container'>
<header>
<h1><a href='myhome'>Best<span>Deal</span></a></h1>
<h2>The Best You Can Get</h2>
</header>
<nav>
<ul>
<li class='start selected'><a href='myhome'>Home</a></li>
<li><a href='smartphone'>SmartPhones</a></li>
<li><a href='laptop'>Laptops</a></li>
<li><a href='tablet'>Tablets</a></li>
<li><a href='tv'>TV</a></li>
<li><a href='logout'>Logout</a></li>
</ul>
</nav>
<div id='body'>		

<section id='content'>
<article>

<p style='position:relative;left:75%;top:40%'><a href='#' style='text-decoration:none'>Account&nbsp&nbsp</a><a href='vieworder' style='text-decoration:none'>Orders&nbsp&nbsp</a><a href='#' style='text-decoration:none'>Cart</a></p>

<table border='1 px' style='text-align:center'>
<tr>
<td><img src='images/samsung.jpe'></td>
</tr>
<tr>
<td>Product Name: ${requestScope.productinfo.productName}</td>
</tr>
<tr>
<td>ID: ${requestScope.productinfo.id}</td>
</tr>
<tr>
<td><form action='addtocart' method='get'><input type='hidden' name='productName' value='${requestScope.productinfo.productName}'><input type='submit' value='Add To Cart'></form></td>
</tr>
<tr>
<td><form action='writereview' method='get'><input type='hidden' name='productName' value='${requestScope.productinfo.productName}'><input type='submit' value='Write Review'></td>
</tr>
<tr>
<td><form action='viewreview' method='get'><input type='hidden' name='productName' value='${requestScope.productinfo.productName}'><input type='submit' value='View Review'/></td>
</tr>
</table>

</article>
<article class='expanded'>
		
		
		</article>
        </section>
        
        <aside class='sidebar'>
		<ul>
        <li>
        <h4>SmartPhones</h4>
        <ul>
        <li><a href='samsung'>Samsung</a></li>
        <li><a href='htc'>HTC</a></li>
        <li><a href='apple'>Apple</a></li>
        <li><a href='sony'>SONY</a></li>
        <li><a href='acer'>Acer</a></li>
        </ul>
        </li>
                
        <li>
        <h4>Laptops</h4>
        <ul>
        <li><a href='dell'>Dell</a></li>
		<li><a href='lenovo'>Lenovo</a></li>
		<li><a href='hp'>HP</a></li>
		<li><a href='panasonic'>Panasonic</a></li>
		<li><a href='applaptop'>Apple</a></li>
        </li>
        </ul>
        </li>
                
        <li>
        <h4>Tablets</h4>
        <ul>
		<li><a href='lgtab'>LG</a></li>
		<li><a href='acertab'>Acer</a></li>
		<li><a href='asustab'>Asus</a></li>
		<li><a href='samsungtab'>Samsung</a></li>
		<li><a href='googletab'>Google</a></li>
        </li>
		</ul>
        </li>
                
        <li>
        <h4>TV</h4>
        <ul>
		<li><a href='panasonictv'>Panasonic</a></li>
		<li><a href='sonytv'>SONY</a></li>
		<li><a href='toshibatv'>Toshiba</a></li>
		<li><a href='insigniatv'>Insignia</a></li>
		<li><a href='hitachitv'>Hitachi</a></li>
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