<?php
session_start();
if(isset($_POST['login']))
{
	$username = mysql_real_escape_string($_POST['username']);
	$password = mysql_real_escape_string($_POST['password']);
	if($username && $password)
	{
		$connect = mysql_connect("127.0.0.1","root","");
		mysql_select_db("users");
		$login = mysql_query("SELECT * FROM admin WHERE username = '$username'");
		while($log=mysql_fetch_assoc($login))
		{
			$dbusername = $log['username'];
			$dbpassword = $log['password'];
		}
		if($username==$dbusername && $password==$dbpassword)
		{
			$_SESSION['username'] = $dbusername;
			$_SESSION['password'] = $dbpassword;
			header("location:membersadmin.php");
		}
		else
		{
			header("location:adminindex.php?notify=Incorrect username or password.");
		}
	}
	else
	{
		header("location:adminindex.php?notify=All fields are required.");
	}
}

?>