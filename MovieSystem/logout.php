<?php
	session_start();
	setcookie('username', '', time() - 3600, '/');
	setcookie('id', '', time() - 3600, '/');
	unset($_COOKIE['username']);
	unset($_COOKIE['id']);
	unset($_COOKIE['PHPSESSID']);
	session_destroy();
	header('location:index.php');
?>