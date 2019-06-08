<?php
	error_reporting(0);
	require "Databaseconnect.php";
	$chapter = $_POST['chapter'];
	$userid = $_POST['phone'];
	$q = "INSERT INTO unlocks (chapter, userid) VALUES ('$chapter', '$userid')";
	$r = $connection -> query($q);
	if($r) {
		echo "success";
	}
	else {
		echo "failed";
	}
?>