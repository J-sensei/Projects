<?php
	require '../connect.php';

	$user = $_COOKIE['id'];
	$ticket = $_POST['ticket'];
	$seat = $_POST['seat'];
	$date = $_POST['date'];
	$time = $_POST['time'];
	$movie = $_POST['movie'];
	$price = $_POST['price'];
	
	$ranking = 0;
	
	$search = $con->query("SELECT * FROM users WHERE id = $user");
	$result = $search->fetch_assoc();
	$wallet = $result['wallet'];
	
	if($wallet >= $price){
		for($i = 0; $i < $ticket; $i++){
			$currentSeat = $seat[$i];
			$sql = $con->query("INSERT INTO payments(movie, user, seat, date, time) VALUES($movie, $user, $currentSeat, '$date', '$time')");
			$ranking++;
			if(!$sql){
				die('Error');
			}
		}
		
		$searchMovie = $con->query("SELECT * FROM movies WHERE id = $movie");
		$rMovie = $searchMovie->fetch_assoc();
		$mRanking = $rMovie['ranking'];
		$ranking = $ranking + $mRanking;
		
		$mInsert = $con->query("UPDATE movies SET ranking = $ranking WHERE id = $movie");
		
		$wallet = $wallet - $price;
		$insert = $con->query("UPDATE users SET wallet = $wallet WHERE id = $user");
		echo 'Purchase Success.';
	}else{
		echo 'Not enough money to purchase tickets';
	}
?>