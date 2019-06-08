<?php
	error_reporting(0);
	require "Databaseconnect.php";
	$chapter = $_POST['chapter'];
	$userid = $_POST['phone'];
	$q = "SELECT * FROM unlocks WHERE chapter='$chapter' AND userid='$userid'";
	$r = $connection -> query($q);
	if($r -> num_rows > 0) {
		echo "unlocked";
	}
	else {
		echo "locked";
	}
?>