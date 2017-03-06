<html>
<head>
<title> Student Group </title>
<link href="style.css" rel="stylesheet" type="text/css">
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
<body>
<div id="head">
<font size="200px">B.E. PROJECT PORTAL</font> 
</div>
<div class="content">
<div class="header">Student Group Portal</div>
<div class="sidebar">

<b>
<br><br>
&raquo; <a href="membersgroup.php" style="color:#500000;text-decoration:none">Home</a><br><br>
&raquo; <a href="checknotice.php" style="color:#500000;text-decoration:none">View Notices</a><br><br>
&raquo; <a href="guideselect.php" style="color:#500000;text-decoration:none">Topic Selection </a><br><br>
&raquo; <a href="" style="color:#500000;text-decoration:none">Guide Selection </a><br><br>
&raquo; <a href="GuideDomainCheckTask.html" style="color:#500000;text-decoration:none">View Deadline</a><br><br>
&raquo; <a href="" style="color:#500000;text-decoration:none">View Project Status</a><br><br>
<!--&raquo; <a href="" style="color:#500000;text-decoration:none">Add Notices</a><br><br>
&raquo; <a href="" style="color:#500000;text-decoration:none">Send Mail</a><br><br>-->
&raquo; <a href="grouplogout.php" style="color:#500000;text-decoration:none">Logout</a><br><br>


</br>

</div>
<div class= "main">
<h1>Topic Selection</h1>
</div>
<div>
<form method="post" action="topicselect.php">
<label><br>Project Topic:<br>
<br><input type="text" name="topic" value="Type in your project name" size="80"><br>
</label><br>
<label>Domain:<br>
<select name="domain">
  <option value="blank"></option>
  <option value="Cloud Computing" name="d1">Cloud Computing</option> 
  <option value="Data Mining" name="d2">Data Mining</option>
  <option value="Artificial Intelligence" name="d3">Artificial Intelligence</option>
  <option value="Networking" name="d4">Networking</option>
 
</select>
<br>
</label><br><br>
<label>Abstract<br><br>
<input disabled  maxlength="500" size="3" value="500" id="counter"><font size="0.5">Characters Remaining</font><br>
<textarea onkeyup="textCounter(this,'counter',500);" id="message" rows="10" cols="50" maxlength="500" name="abstract">
</textarea>
</label>
<br><br>
<button name="submit" value="Submit">Submit</button>
</form>
</div>
<!--<a href="sendguiderequest.php"><font size="1">Send Request</font></a>-->
<div id="footer">
<br>

</div>
</body>
</html>
<?php
if(isset($_POST['submit']))
{
$topic = mysql_real_escape_string($_POST['topic']);
$domain = mysql_real_escape_string($_POST['domain']);
$abstract = mysql_real_escape_string($_POST['abstract']);


if($topic && $domain && $abstract)
{
	
	$connect = mysql_connect("127.0.0.1","root","");
	mysql_select_db("users");
	$abc=
	$query = mysql_query("INSERT INTO sttopic (topic,domain,abstract) VALUES ('$topic','$domain','$abstract')")  ;
	//echo "<script>alert('Added successfully')</script>";
	echo "<script>alert('Your topic has been submitted.You cannot submit another topic unless it has been disapproved by guide or coordinator')</script>";
	
	


}
else
{
	echo "<script>alert('All fields required.')</script>";
}
}

?>
