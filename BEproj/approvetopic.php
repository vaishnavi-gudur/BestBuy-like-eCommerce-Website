
<!DOCTYPE html>
<head>
</head>
<body>

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

$sql = "SELECT fname,lname,id,d1,topic,domain,abstract FROM guidedetail";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
     // output data of each row
     while($row = $result->fetch_assoc()) 
	 {
	
         echo "<br>"."Guide:"."Mr/Ms.". $row["fname"]. "&nbsp" . $row["lname"]. "<br>"." Topic: " . $row["topic"] . "<br>"."Domain:".$row["domain"]."<br>"."Abstract:".$row["abstract"];
		 echo "<br><br>";
		
     }
} else {
     echo "0 results";
}

$conn->close();
?>  
</body>
</html>