Vaishnavi


Search Drive

Drive
.
Folder Path
BE project
BEproj update 1
NEW 
Folders and views
My Drive
Shared with me
Google Photos
Recent
Starred
Trash
13 GB of 115 GB used
Upgrade storage
Get Drive for PC
.

HTML
123.html

Google Docs
123.html

Image
admin.png

PHP
adminindex.php

PHP
adminlogin.php

PHP
adminlogout.php

PHP
forgotguide.php

Image
group.png

Image
guide.png

PHP
guideindex.php

PHP
guidelogin.php

PHP
guidelogout.php

HTML
home.html

PHP
index1.php

PHP
membersadmin.php

PHP
membersguide.php

Compressed Archive
ours.rar

Binary File
rankDindex

HTML
rankDindex.html

PHP
registerguide.php

Style Sheet
style.css

HTML
try.html


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
		$login = mysql_query("SELECT * FROM users WHERE username = '$username'");
		while($log=mysql_fetch_assoc($login))
		{
			$dbusername = $log['username'];
			$dbpassword = $log['password'];
		}
		if($username==$dbusername && $password==$dbpassword)
		{
			$_SESSION['username'] = $dbusername;
			$_SESSION['password'] = $dbpassword;
			header("location:membersguide.php");
		}
		else
		{
			header("location:guideindex.php?notify=Incorrect username or password.");
		}
	}
	else
	{
		header("location:guideindex.php?notify=All fields are required.");
	}
}

?>
