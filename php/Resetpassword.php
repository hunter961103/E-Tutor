<?php
	$email = $_GET["email"];
	$password = $_GET["password"];
	if(strlen($password) < 1) {
		header("Location: Fault.html");
		exit;
	}
?>
<!DOCTYPE html>
<html>
<head>
	<title>Reset Password</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<style>
	input[type=password], select {
		width: 100%;
		padding: 12px 20px;
		margin: 8px 0;
		display: inline-block;
		border: 1px solid #ccc;
		border-radius: 4px;
		box-sizing: border-box;
	}

	input[type=submit] {
		width: 100%;
		background-color: #4caf50;
		color: white;
		padding: 14px 20px;
		margin: 8px 0;
		border: none;
		border-radius: 4px;
		cursor: pointer;
	}

	input[type=submit]:hover {
		background-color: #45a049;
	}

	div {
		border-radius: 5px;
		background-color: #f2f2f2;
		padding: 20px;
	}
</style>
<body>
	<h2>E-Tutor</h2>
	<br>
	<p>Reset Password for <?php echo $email ?></p>
	<br>
	<form action="Changepassword.php" method="post">
		New Password: <br><input type="password" name="newpassword" required><br>
		<input type="hidden" name="email" value ="<?php echo $email; ?>">
		<input type="hidden" name="oldpassword" value ="<?php echo $password; ?>">
		<input type="submit" value="Submit">
	</form>
</body>
</html>