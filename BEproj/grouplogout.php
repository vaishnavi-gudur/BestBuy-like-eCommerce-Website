<?php

session_start();
if($_SESSION['username'] || $_SESSION['pass'])
{
	session_destroy();
	header("location:groupindex.php?notify=You are logged out.");
}

?>