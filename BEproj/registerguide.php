<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Project Guide Registration</title>
<link rel="stylesheet" type="text/css" href="view.css" media="all">
<script type="text/javascript" src="view.js"></script>

</head>
<div id="header">
<font size="200px">B.E. PROJECT PORTAL</font> 
</div>
<br>
<body id="main_body" >
	
	<!--<img id="top" src="top.png" alt="">-->
	<div id="form_container">
	
		<h1><a>Project Guide Registration</a></h1>
		<form id="form_1099123" class="appnitro"  method="post" action="registerguide.php">
					<div class="form_description">
			<h2>Project Guide Registration</h2>
			<p></p>
		</div>						
			<ul >
			
					<li id="li_1" >
		<label class="description" for="element_1">Name </label>
		<span>
			<input id="element_1_1" name= "fname" class="element text" maxlength="255" size="8" value=""/>
			<label>First</label>
		</span>
		<span>
			<input id="element_1_2" name= "lname" class="element text" maxlength="255" size="14" value=""/>
			<label>Last</label>
		</span> 
		</li>		<li id="li_2" >
		<label class="description" for="element_2">Username </label>
		<div>
			<input id="element_2" name="uname" class="element text medium" type="text" maxlength="255" value=""/> 
		</div> 
		</li>		<li id="li_7" >
		<label class="description" for="element_7">Department </label>
		<div>
		<select class="element select medium" id="element_7" name="dept"> 
			<option value="" selected="selected"></option>
<option value="IT" >IT</option>
<option value="Comps" >Comps</option>
<option value="Electronics" >Electronics</option>
<option value="EXTC" >EXTC</option>
<option value="Instrumentation" >Instrumentation</option>

		</select>
		</div> 
		</li>		<li id="li_3" >
		<label class="description" for="element_3">Phone </label>
		<div>
			<input id="element_3" name="phone" class="element text medium" type="text" maxlength="255" value=""/> 
		</div> 
		</li>		<li id="li_4" >
		<label class="description" for="element_4">Email </label>
		<div>
			<input id="element_4" name="email" class="element text medium" type="text" maxlength="255" value=""/> 
		</div> 
		</li>		<li id="li_5" >
		<label class="description" for="element_5">Password </label>
		<div>
			<input id="element_5" name="pass" class="element text medium" type="password" maxlength="255" value=""/> 
		</div> 
		</li>		<li id="li_6" >
		<label class="description" for="element_6">Confirm Password </label>
		<div>
			<input id="element_6" name="pass1" class="element text medium" type="password" maxlength="255" value=""/> 
		</div> 
		</li>
			
					<li class="buttons">
			    <input type="hidden" name="form_id" value="1099123" />
			    
				<input id="saveForm" class="button_text" type="submit" name="submit" value="Submit" />
		</li>
			</ul>
		</form>	

		
		<br><br><br><br>

<div id="footer">
<br>
</div>
	<!--<img id="bottom" src="bottom.png" alt="">-->
	</body>
	
	
</html>

