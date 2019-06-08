<?php
	error_reporting(0);
	require "Databaseconnect.php";
	$email = $_POST['email'];
	$password = sha1($_POST['password']);
	$q = "SELECT * FROM users WHERE email='$email' AND password='$password'";
	$r = $connection -> query($q);
	if($r -> num_rows > 0) {
		while($row = $r -> fetch_assoc()) {
			echo $row['name'].",".$row['phone'];
		}
	}
	else {
		echo "failed";
	}
?>