<?php
	require '../connect.php';
	
	$user = $_COOKIE['id'];
	$id = $_POST['id'];
	$movie = $_POST['movie'];
	$date = $_POST['date'];
	$currentDate = date('Y-m-d', strtotime('+3 day'));
	
	if($date > $currentDate){
		//search movie price
		$search = $con->query("SELECT * FROM movies WHERE name = '$movie'");
		$result = $search->fetch_assoc();
		$price = $result['price'];
		$ranking = $result['ranking'];

		$search2 = $con->query("SELECT * FROM users WHERE id = $user");
		$result2 = $search2->fetch_assoc();
		$wallet= $result2['wallet'];
		
		$wallet = $wallet + $price;
		$ranking = $ranking - 1;
		
		//delete payment
		$sql = $con->query("DELETE FROM payments WHERE id = $id");
		
		$fundback = $con->query("UPDATE users SET wallet = $wallet WHERE id = $user");
		$rankback = $con->query("UPDATE movies SET ranking = $ranking WHERE name = '$movie'");
		
		if($sql && $fundback && $rankback){
			echo "RM$price has been refund to your account";
		}else{
			echo 'Failed to refund';
		}
	}else{
		echo 'This ticket is not eligible to refund';
	}
?>