<?php
	error_reporting(0);
	require "Databaseconnect.php";
	$phone = $_POST['phone'];
	$name = $_POST['name'];
	$email = $_POST['email'];
	$oldpassword = sha1($_POST['oldpassword']);
	$newpassword = sha1($_POST['newpassword']);
	if($oldpassword == "" || $newpassword == "") {
		$q = "SELECT * FROM users WHERE phone='$phone'";
		$r = $connection -> query($q);
		if($r -> num_rows > 0) {
			$q = "UPDATE users SET name='$name', email='$email' WHERE phone='$phone'";
			if($connection -> query($q) === true) {
				echo "success";
			}
			else {
				echo "failed";
			}
		}
	}
	else {
		$q = "SELECT * FROM users WHERE phone='$phone' AND password='$oldpassword'";
		$r = $connection -> query($q);
		if($r -> num_rows > 0) {
			$q = "UPDATE users SET name='$name', password='$newpassword', email='$email' WHERE phone='$phone' AND password='$oldpassword'";
			if($connection -> query($q) === true) {
				echo "success";
			}
			else {
				echo "failed";
			}
		}
	}
?>