<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Student Group Registration</title>
<link rel="stylesheet" type="text/css" href="view.css" media="all">
<script>
function textCounter(field,field2,maxlimit)
{
 var countfield = document.getElementById(field2);
 if ( field.value.length > maxlimit ) {
  field.value = field.value.substring( 0, maxlimit );
  return false;
 } else {
  countfield.value = maxlimit - field.value.length;
 }
}

</script>

</head>
<div id="header">
<font size="200px">B.E. PROJECT PORTAL</font> 
</div>
<body id="main_body" >
	

	<div id="form_container">
	
		<h1><a>Student Group Registration</a></h1>
		<form id="form_1103455" class="appnitro"  method="post" action="groupindex.php">
					<div class="form_description">
			<h2>Student Group Registration</h2>
			<p></p>
		</div>						
			<ul >
			
					<li id="li_1" >
		<label class="description" for="element_1">Group Username </label>
		<div>
			<input id="element_1" name="username" class="element text medium" type="text"  maxlength="16" 
                data-fv-stringlength-message="3 to 16 characters" value=""/> 
		</div> 
		</li>		<li id="li_2" >
		<label class="description" for="element_2">Email </label>
		<div>
			<input  id="element_2" name="email" class="element text medium" type="email" maxlength="255" value="" required/> 
		</div> 
		</li>		<li id="li_3" >
		<label class="description" for="element_3">Password </label>
		<div>
			<input id="element_3" name="pass" class="element text medium" type="password" maxlength="255" value="" required/> 
		</div> 
		</li>		<li id="li_4" >
		<label class="description" for="element_4">Confirm Password </label>
		<div>
			<input id="element_4" name="pass1" class="element text medium" type="password" maxlength="255" value="" required/> 
		</div> 
		</li>		<li class="section_break">
			<h3>Student Information</h3>
			<p></p>
		</li>		<li id="li_6" >
		<label class="description" for="element_6">Student 1 </label>
		<div>
			<input id="element_6" name="s1" class="element text medium" type="text" maxlength="8" value="" required/> 
		</div> 
		</li>		<li id="li_7" >
		<label class="description" for="element_7">Student 2 </label>
		<div>
			<input id="element_7" name="s2" class="element text medium" type="text" maxlength="8" value="" required/> 
		</div> 
		</li>		<li id="li_8" >
		<label class="description" for="element_8">Student 3 </label>
		<div>
			<input id="element_8" name="s3" class="element text medium" type="text" maxlength="8" value=""/> 
		</div> 
		</li>		<li id="li_9" >
		<label class="description" for="element_9">Student 4 </label>
		<div>
			<input id="element_9" name="s4" class="element text medium" type="text" maxlength="8" value=""/> 
		</div> 
		</li>
		<hr>
		<<!--hr><li class="section_break">
		<p> <b>Topic Selection:</b></p>
		<div>
<form method="post" action="groupindex.php">
<label><br><font size="1.5"><b>Project Topic:</b></font><br>
<br><input type="text" name="topic" value="Type in your project name" size="80"><br>
</label><br>
<label><font size="1.5"><b>Domain:</b></font><br>
<br><select name="domain">
  <option value="blank"></option>
  <option value="Cloud Computing" name="d1">Cloud Computing</option> 
  <option value="Data Mining" name="d2">Data Mining</option>
  <option value="Artificial Intelligence" name="d3">Artificial Intelligence</option>
  <option value="Networking" name="d4">Networking</option>
 
</select>
<br>
</label><br><br>
<label><font size="1.5"><b>Abstract:</b></font><br><br>
<input disabled  maxlength="500" size="3" value="500" id="counter"><font size="0.5">Characters Remaining</font><br>
<textarea onkeyup="textCounter(this,'counter',500);" id="message" rows="10" cols="50" maxlength="500" name="abstract">
</textarea>
</label>
<br><br>
</form>
</div>
</li>-->
			
					<li class="buttons">
			    <input type="hidden" name="form_id" value="1103455" />
			    
				<input id="saveForm" class="button_text" type="submit" name="submit" value="Submit" />
		</li>
			</ul>
		</form>	
		
	<div id="footer">
<br>
</div>
	</body>
</html>