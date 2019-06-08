<?php
	error_reporting(0);
	require "Databaseconnect.php";
	$chapter = $_POST['chapter'];
	$q = "SELECT * FROM videos WHERE chapter='$chapter'";
	$r = $connection -> query($q);
	if($r -> num_rows > 0) {
		$response['video'] = array();
		while($row = $r -> fetch_assoc()) {
			$videolist = array();
			$videolist[name] = $row['name'];
			$videolist[author] = $row['author'];
			$videolist[publisheddate] = $row['publisheddate'];
			$videolist[url] = $row['url'];
			array_push($response['video'], $videolist);
		}
		echo json_encode($response);
	}
	else {
		echo "nodata";
	}
?>