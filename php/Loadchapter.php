<?php
	error_reporting(0);
	require "Databaseconnect.php";
	$subject = $_POST['subject'];
	if($subject == "All") {
		$q = "SELECT * FROM chapters";
	}
	else {
		$q = "SELECT * FROM chapters WHERE subject='$subject'";
	}
	$r = $connection -> query($q);
	if($r -> num_rows > 0) {
		$response['chapter'] = array();
		while($row = $r -> fetch_assoc()) {
			$chapterlist = array();
			$chapterlist[name] = $row['name'];
			$chapterlist[description] = $row['description'];
			$chapterlist[price] = $row['price'];
			array_push($response['chapter'], $chapterlist);
		}
		echo json_encode($response);
	}
	else {
		echo "nodata";
	}
?>