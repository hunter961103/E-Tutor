<?php
	error_reporting(0);
	require "Databaseconnect.php";
	$phone = $_POST['phone'];
	$q = "SELECT * FROM users WHERE phone='$phone'";
	$r = $connection -> query($q);
	if($r -> num_rows > 0) {
		$response['user'] = array();
		while($row = $result -> fetch_assoc()) {
			$user = array();
			$user[name] = $row['name'];
			$user[email] = $row['email'];
			array_push($response['user'], $user);
		}
		echo json_encode($response);
	}
	else {
		echo "nodata";
	}
?>