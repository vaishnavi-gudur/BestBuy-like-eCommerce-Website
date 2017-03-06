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
<h1 style="color: #500000"><center>Project Guide Login</center></h1>
<div id="Sign-Up">
<form method="POST" action="guideindex.php">
<center>Username:<input type="text" name="uname" value="Username"/></center>
<br>
<center>Password:<input type="password" name="pass" value="Password"/></center>
<br>
<center><input type="submit" vamue="submit" name="submit"></center>
</form>	

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
if(isset($_GET['notify']))
{
	echo $_GET['notify'];
}
?>
<?php

session_start();
if(isset($_SESSION['username']) || isset($_SESSION['password']))
{
	header("location:membersguide.php");
}
else
{
echo "<form action='guidelogin.php' method='POST'>
<p><center><input type='text' name='username' placeholder='Username'></center>
<p><center><input type='password' name='password' placeholder='Password'></center>
<p><center><input type='submit' name='login' value='Log In'></center>
</form>";
}
?>