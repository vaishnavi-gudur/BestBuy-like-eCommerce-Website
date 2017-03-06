<html>
<head>
<title> Student Group </title>
<link href="style.css" rel="stylesheet" type="text/css">
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
&raquo; <a href="topicselect.php" style="color:#500000;text-decoration:none">Topic Selection </a><br><br>
&raquo; <a href="guideselect.php" style="color:#500000;text-decoration:none">Guide Selection </a><br><br>
&raquo; <a href="GuideDomainCheckTask.html" style="color:#500000;text-decoration:none">View Deadline</a><br><br>
&raquo; <a href="" style="color:#500000;text-decoration:none">View Project Status</a><br><br>
<!--&raquo; <a href="" style="color:#500000;text-decoration:none">Add Notices</a><br><br>
&raquo; <a href="" style="color:#500000;text-decoration:none">Send Mail</a><br><br>-->
&raquo; <a href="grouplogout.php" style="color:#500000;text-decoration:none">Logout</a><br><br>


</br>

</div>
<div class= "main">
<h1>Home</h1>




</div>
<?php

session_start();
if($_SESSION['username'] || $_SESSION['pass'])
{
	$uname = $_SESSION['username'];
	echo "You are logged in as  ". $_SESSION['username'];
	//echo "<a href='guidelogout.php'><p style='position:fixed;left:75%'>LogOut</a>";
	//echo "<title> $username | BE PROJECT</title>";
}
else
{
	header("header:groupindex.php?notify=Oops! Something went wrong.");
}

?>
<br><br><br>
<?php

echo "Guide Allotted: " . "<br>";
echo "Project Topic:" . "<br>";
echo "Domain selected:" . "<br>";
?>
<div id="footer">
<br>

</div>
</body>
</html>
