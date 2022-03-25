<?php
	$host = 'localhost';
	$username = 'root';
	$password = '';
	$database = 'movies_booking_system';
	
	$con = new mysqli($host, $username, $password, $database) or die(mysqli_error);
?>