<?php
	require '../connect.php';
	
	$movie = $_POST['movie'];
		$time = $_POST['time'];
		$date = $_POST['date'];
	$output = '';
	for($seat = 1; $seat <= 50; $seat ++){

		if($seat == 1 || $seat == 11 || $seat == 21 || $seat == 31 || $seat == 41){
			$output .= "<tr>";
		}
		if($seat == 6 || $seat == 16 || $seat == 26 || $seat == 36 || $seat == 46){
			$output .= "<td class='seatGap'>";
		}
		$sql = $con->query("SELECT * FROM payments WHERE seat = $seat AND movie = $movie AND time = '$time' AND date = '$date'");
		if($sql->num_rows > 0){
			$output .= "<td><label class='container'>$seat<input type='checkbox' class='seat occupied' value='$seat'><span class='check occupied'></span></label></td>";
		}else{
			$output .= "<td><label class='container'>$seat<input type='checkbox' class='seat' value='$seat'><span class='check'></span></label></td>";							
		}
		if($seat == 10 || $seat == 20 || $seat == 30 || $seat == 40){
			$output .= "</tr>";
		}
	}
	echo $output;
?>