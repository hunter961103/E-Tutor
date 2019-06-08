<?php
	error_reporting(0);
	require "Databaseconnect.php";
	$email = $_POST['email'];
	$oldpassword = $_POST['oldpassword'];
	$newpassword = sha1($_POST['newpassword']);
	$q = "UPDATE users SET password='$newpassword' WHERE email='$email' AND password='$oldpassword'";
	if($connection -> query($q) === true) {
		echo "<font color='green'><h2><br><br>SUCCESS. PLEASE LOGIN USING NEW PASSWORD</h2></font>";
	}
	else {
		echo "<font color='red'><h2><br><br>FAILED.</h2></font>";
	}
?>