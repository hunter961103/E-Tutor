<?php
	$connection = new mysqli("localhost", "id7373992_admin", "12345", "id7373992_etutor");
	if($connection -> connect_error) {
		die("Connection failed: ".$connection -> connect_error);
	}
?>