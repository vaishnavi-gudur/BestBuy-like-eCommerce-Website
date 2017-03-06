<!DOCTYPE html>
<html>
<head>
<title>Sign-Up</title>
<style>
#header {
    background-color:#500000 ;
    color:white;
    text-align:center;
    padding: 50px;
}
#nav {
position: relative;
    line-height:10px;
    background-color:white;
    height:700px;
    width:100px;
    float:center;
    padding:5px;	      
}
#nav1 {
height: 600px;
}
#section {
    width:350px;
    float:left;
    padding:10px;	 	 
}
#footer {
    position:relative;
    background-color:#500000 ;
    color:white;
    clear:both;
    text-align:center;
    padding:5px;
	
}
#Sign-Up {
	position:relative;
	margin-top: 5%;
	margin-left:3%;
	font-size: 35px;
	}

a {
    color: #500000;
	text-decoration:none;
}


}
</style>
</head>
<body>

<div id="header">
<font size="200px">B.E. PROJECT PORTAL</font> 
</div>
<br><br>
<h1 style="color: #500000"><center>Project Group Login</center></h1>
<div id="Sign-Up">
<?php
if(isset($_GET['notify']))
{
	echo $_GET['notify'];
}
?>
<?php

session_start();
if(isset($_SESSION['uname']) || isset($_SESSION['pass']))
{
	header("location:membersguide.php");
}
else
{
echo "<form action='guidelogin.php' method='POST'>
<p><center><input type='text' name='uname' placeholder='Username'></center>
<p><center><input type='password' name='pass' placeholder='Password'></center>
<p><center><input type='submit' name='login' value='Log In'></center>
</form>";
}
?>
</br>

<a href="registerguide.php"><center>Register</center></a>
<p><a href="forgotguide.php"><center>Forgot password or username?</center></a>
</div>


<br><br><br><br><br>

<div id="footer">
<br>

</div>

</body>
</html>
<?php
if(isset($_POST['submit']))
{
$uname = mysql_real_escape_string($_POST['uname']);
$email = mysql_real_escape_string($_POST['email']);
$pass = mysql_real_escape_string($_POST['pass']);
$pass1 = mysql_real_escape_string($_POST['pass1']);
$s1 = mysql_real_escape_string($_POST['s1']);
$s2 = mysql_real_escape_string($_POST['s2']);
$s3 = mysql_real_escape_string($_POST['s3']);
$s4 = mysql_real_escape_string($_POST['s4']);
if($uname && $email && $pass && $pass1 && $s1 && $s2 && $s3 && $s4)
{
	if($pass==$pass1)
	{
	$connect = mysql_connect("127.0.0.1","root","");
	mysql_select_db("users");
	$query = mysql_query("INSERT INTO group(uname,email,pass,pass1,s1,s2,s3,s4) VALUES ('$uname','$email','$pass','$pass1','$s1','$s2','$s3','$s4')");
	echo "<script>alert('You have been registered successfully')</script>";	
	}
	else{
		echo "<script>alert('Password must match.')</script>";
	
	}


}
else
{
	echo "<script>alert('All fields required.')</script>";
}
}

?>