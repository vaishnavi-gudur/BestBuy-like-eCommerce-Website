<html>
<head>
<title>Forgot Password</title>
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
	top: 2px;
	left:45%;
	font-size: 35px;
	}




}
</style>
</head>
</head>
<body>

<div id="header">
<font size="200px">B.E. PROJECT PORTAL</font> 
</div>
<br><br><br>
<h1 style="color: #500000"><center>Forgot Password</center></h1>
<div id="Sign-Up">
<form method="POST" action="forgotguide.php">
<p><input name="email" type="email" placeholder="Type your Email...">
<p style="font-size:3%"><input name="submit" type="submit" value="Submit">
</form>
</html>
<?php
if(isset($_POST['submit']))
{
$email = $_POST['email'];
$connect = mysql_connect("127.0.0.1","root","");
mysql_select_db("users");
$query = mysql_query("SELECT * FROM users WHERE email = '$email'");
while($row = mysql_fetch_assoc($query))
{
$username = $row['username'];
$password = $row['password'];
$email = $row['email'];
}
if($email==$email)
{
$from = "FROM:AutomationofBEProject";
$to = $email;
$subject ="Lost Password or Username";
$body = "Dear $username \n >Your username is: $username \n >Your password is:$password";
mail($to,$subject,$body,$from);
echo "Check your Inbox";
}
else
{
echo "Incorrect Email.";
}
}
else
{
echo "Enter your email.";
}
?>
</div>
<br><br><br><br><br><br><br><br><br><br><br><br><br>
<div id="footer">
<br>

</div>
</body>
</html>