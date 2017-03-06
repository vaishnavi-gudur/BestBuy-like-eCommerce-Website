<?php

session_start();
if(isset($_POST['login']))
{
	$username = mysql_real_escape_string($_POST['username']);
	$pass = mysql_real_escape_string($_POST['pass']);
	if($username && $pass)
	{
		$connect = mysql_connect("127.0.0.1","root","");
		mysql_select_db("users");
		$login = mysql_query("SELECT * FROM stgroup WHERE username = '$username'");
		while($log=mysql_fetch_assoc($login))
		{
			$dbusername = $log['username'];
			$dbpassword = $log['pass'];
		}
		if($username==$dbusername && $pass==$dbpassword)
		{
			$_SESSION['username'] = $dbusername;
			$_SESSION['pass'] = $dbpassword;
			header("location:membersgroup.php"); 
		}
		else
		{
			header("location:groupindex.php?notify=Incorrect username or password.");
		}
	}
	else
	{
		header("location:groupindex.php?notify=All fields are required.");
	}
}

?>