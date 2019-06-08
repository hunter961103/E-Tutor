<?php
	error_reporting(0);
	require "Databaseconnect.php";
	$email = $_POST['email'];
	$q = "SELECT * FROM users WHERE email='$email'";
	$r = $connection -> query($q);
	if($r -> num_rows > 0) {
		while($row = $r -> fetch_assoc()) {
			$from = "From: E-Tutor <noreply@etutor.com>";
			$to = $email;
			$subject = "Reset Password";
			$body = "Use the following link to reset your password :\n".
					"https://grouping.000webhostapp.com/etutor/Resetpassword.php?email=".$email."&password=".$row['password'];
			if(mail($to, $subject, $body, $from)) {
				echo "success";
			}
		}
	}
?>