<?php
	require '../connect.php';
	
	$current = $con->real_escape_string($_POST['current']);
	$newpass = $con->real_escape_string($_POST['newpass']);
	$id = $_COOKIE['id'];
	
	$search = $con->query("SELECT * FROM users WHERE id = $id");
	
	if($search->num_rows > 0){
		$result = $search->fetch_assoc();
		
		if(password_verify($current, $result['password'])){
			
			$password = $con->real_escape_string(password_hash($newpass, PASSWORD_BCRYPT));

			$sql = $con->query("UPDATE users SET password = '$password' WHERE id = $id");
			
			if($sql){
				echo 'Password has changed';
			}else{
				echo 'Error:Cannot change Password';
			}
			
		}else{
			echo 'Wrong Password';
		}
		
	}else{
		echo 'Error: User not found';
	}
?>