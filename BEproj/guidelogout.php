<?php

session_start();
if($_SESSION['uname'] || $_SESSION['pass'])
{
	session_destroy();
	header("location:guideindex.php?notify=You are logged out.");
}

?>