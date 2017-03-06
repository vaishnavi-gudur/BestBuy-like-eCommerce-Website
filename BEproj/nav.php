<html>
<head>
<title> Admin </title>
<link href="style.css" rel="stylesheet" type="text/css">
<meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	
</head>
<body>
<div id="head">
<font size="200px">B.E. PROJECT PORTAL</font> 

 </div>
 <!--<div class="container">-->
      <!--<p>Home icon <span class="glyphicon glyphicon-home"></span></p>    
      <p>Home icon as a link:
        <a href="#">
          <span class="glyphicon glyphicon-home"></span>
        </a>
      </p>-->
     
      <!--<p>Home icon on a styled link button:
        <a href="#" class="btn btn-info btn-lg">
          <span class="glyphicon glyphicon-home"></span> Home
        </a>
      </p> -->
    <!--</div>-->
<div class="content">

        <button type="button" class="btn btn-default btn-sm">
		<a href="membersadmin.php">
          <span class="glyphicon glyphicon-home"></span>Home
		  </a>
        </button>
      
        <button type="button" class="btn btn-default btn-sm">
		<a href="adminlogout.php">
          <span class="glyphicon glyphicon-log-out"></span> Log out
		  </a>
        </button>
     
     
<div class="header">Admin</div>
<div class="sidebar">

<b>
<br><br>

<!--&raquo; <a href="addnotice.php" style="color:#500000;text-decoration:none">Add Notices</a><br><br>
&raquo; <a href="" style="color:#500000;text-decoration:none">Approve Topics</a><br><br>
&raquo; <a href="uploadsched.php" style="color:#500000;text-decoration:none">Upload Schedule</a><br><br>
&raquo; <a href="" style="color:#500000;text-decoration:none">Track Progress</a><br><br>
&raquo; <a href="" style="color:#500000;text-decoration:none">Project Guides' Info</a><br><br>
&raquo; <a href="" style="color:#500000;text-decoration:none">View confirmed topics</a><br><br>
&raquo; <a href="" style="color:#500000;text-decoration:none">View Rejected Topics</a><br><br>
&raquo; <a href="" style="color:#500000;text-decoration:none">Pending Requests</a><br><br>
&raquo; <a href="" style="color:#500000;text-decoration:none">Final List</a><br><br>

-->
<!--<div class="container">
<div class="row">
  <div class="col-sm-3">
    <div class="sidebar-nav">-->
      <!--<div class="navbar navbar-default" role="navigation">-->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
             <span class="icon-bar"></span>
             <span class="icon-bar"></span>
             <span class="icon-bar"></span>
             <span class="icon-bar"></span>
             <span class="icon-bar"></span>
          </button>
          <span class="visible-xs navbar-brand">Sidebar menu</span>
        </div>
        <div class="navbar-collapse collapse sidebar-navbar-collapse" style="color:#500000">
          <ul class="nav navbar-nav" style="color:#500000">
    
            <li><a href="#">Add Notices</a></li>
            <li><a href="#">Requests </a></li>
            <li><a href="#">Upload Schedule</a></li>
            <li><a href="#">Track Progress</a></li>
            <li><a href="#">Project Guides' Info</a></li>
            <li><a href="#">View Confirmed Topics</a></li>
            <li><a href="#">View Rejected Topics</a></li>
            <li><a href="#">Final List</a></li>
            
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>
  </div>
</br>

</div>
<div class= "main">
<h1>Home</h1>
<hr>

</div>
<?php

session_start();
if($_SESSION['username'] || $_SESSION['password'])
{
	$username = $_SESSION['username'];
	echo "Welcome!";
	//echo "<br><br><a href='adminlogout.php'>LogOut</a>";
	echo "<title>$username | BE PROJECT</title>";
}
else
{
	header("header:index.php?notify=Oops! Something went wrong.");
}

?>
<div id="footer">
<br>

</div>
</body>
</html>
