<?php
	require '../connect.php';
	
	$id = $_COOKIE['id'];
	$amount = $_POST['amount'];
	
	$search = $con->query("SELECT * FROM users WHERE id=$id");
	$result = $search->fetch_assoc();
	$current_amount = $result['wallet'];
	
	$amount = $current_amount + $amount;//add money
	
	$sql = $con->query("UPDATE users SET wallet = $amount WHERE id=$id");
	if($sql){
		echo "RM".$_POST['amount']." added into your wallet";
	}else{
		echo 'Failed to add to your wallet, please try again';
	}
?>