<html>
<head>
<title>Student Group</title>
<link href="style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="head">
<font size="200px">B.E. PROJECT PORTAL</font> 
</div>
<div class="content">
<div class="header">Student Group Portal</div>
<div class="sidebar">

<b>
<br><br>
&raquo; <a href="membersgroup.php" style="color:#500000;text-decoration:none">Home</a><br><br>
&raquo; <a href="checknotice.php" style="color:#500000;text-decoration:none">View Notices</a><br><br>
&raquo; <a href="topicselect.php" style="color:#500000;text-decoration:none">Topic Selection </a><br><br>
&raquo; <a href="GuideDomainCheckTask.html" style="color:#500000;text-decoration:none">View Deadline</a><br><br>
&raquo; <a href="" style="color:#500000;text-decoration:none">View Project Status</a><br><br>
<!--&raquo; <a href="" style="color:#500000;text-decoration:none">Add Notices</a><br><br>
&raquo; <a href="" style="color:#500000;text-decoration:none">Send Mail</a><br><br>-->
&raquo; <a href="grouplogout.php" style="color:#500000;text-decoration:none">Logout</a><br><br>


</br>

</div>
<div class= "main">
<h1>Check Notices</h1>
</div>
<div>
<p>
        <a href="list_files.php">See files</a>
    </p>

<?php

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "users";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
     die("Connection failed: " . $conn->connect_error);
} 

$sql = "SELECT title,category,content FROM notice";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
     // output data of each row
     while($row = $result->fetch_assoc()) {
		 echo "Date:".date("d/m/Y");
         echo "<br> Title: ". $row["title"]. "<br>" ."  Category: ". $row["category"]. "<br>"." Content: " . $row["content"] . "<br>";
		 echo "<br><br>";
     }
} else {
     echo "0 results";
}

$conn->close();
?>  

</div>

<div id="footer">
<br>

</div>
</body>
</html>

