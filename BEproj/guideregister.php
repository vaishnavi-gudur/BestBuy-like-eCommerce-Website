<?php
session_start();
$connection = mysql_connect("localhost", "root", ""); // Establishing Connection with Server
$db = mysql_select_db("users", $connection); // Selecting Database from Server
if(isset($_POST['submit'])){ // Fetching variables of the form which travels in URL
$fname = $_POST['fname'];
$lname = $_POST['lname'];
$username = $_POST['username'];
$email = $_POST['email'];
$dept = $_POST['dept'];
$phone = $_POST['phone'];
$pass = $_POST['pass'];
$pass1 = $_POST['pass1'];
if($uname !=''||$email !=''){
//Insert Query of SQL
$query = mysql_query("insert into users(fname.lname,username,email,dept,phone,pass,pass1) values ('$fname','$lname','$username', '$email', '$dept', '$phone','$pass','$pass1')");
echo "<br/><br/><span>Data Inserted successfully...!!</span>";
}
else{
echo "<p>Insertion Failed <br/> Some Fields are Blank....!!</p>";
}
}
mysql_close($connection); // Closing Connection with Server
?>