<!DOCTYPE html>
<html>
<head>
<title>Admin Login</title>
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




}
</style>
</head>
<body>

<div id="header">
<font size="200px">B.E. PROJECT PORTAL</font> 
</div>
<br><br>
<h1 style="color: #500000"><center>Admin Login</center></h1>
<div id="Sign-Up">
<?php
if(isset($_GET['notify']))
{
	echo $_GET['notify'];
}
?>
<?php

if(isset($_SESSION['username']) || isset($_SESSION['password']))
{
	header("location:membersadmin.php");
}
else
{
echo "<form action='adminlogin.php' method='POST'>
<p><center><input type='text' name='username' placeholder='Username'></center>
<p><center><input type='password' name='password' placeholder='Password'></center>
<p><center><input type='submit' name='login' value='Log In'></center>
</form>"  ;
} 
?>


</br>

</div>


<br><br><br><br><br>

<div id="footer">
<br>

</div>

</body>
</html>
