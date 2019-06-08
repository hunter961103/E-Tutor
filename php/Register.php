<?php
	error_reporting(0);
	require "Databaseconnect.php";
	$name = $_POST['name'];
	$password = sha1($_POST['password']);
	$email = $_POST['email'];
	$phone = $_POST['phone'];
	$encoded_string = $_POST['encoded_string'];
	$image_name = $_POST['image_name'];
	$decoded_string = base64_decode($encoded_string);
	$path = "profile_images/".$image_name;
	$file = fopen($path, "wb");
	$is_written = fwrite($file, $decoded_string);
	fclose($file);
	if($name == "" || $password == "" || $email == "" || $phone == "") {
		echo "nodata";
	}
	else {
		if($is_written > 0) {
			$q = "INSERT INTO users VALUES ('$name', '$password', '$email', '$phone')";
			if($connection -> query($q) === true) {
				echo "success";
			}
			else {
				echo "failed";
			}
		}
	}
?>