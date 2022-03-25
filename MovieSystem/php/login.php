<?php
		require '../connect.php';
		
		if(isset($_POST['username']) && isset($_POST['password']) && !empty($_POST['username']) && !empty($_POST['password'])){
			$username = $con->real_escape_string($_POST['username']);
			$password = $con->real_escape_string($_POST['password']);
			
			$search = $con->query("SELECT * FROM users WHERE username = '$username'");//search from the table
			
			if($search->num_rows == 0){
				echo 'Username not exist!';
			}else{
				$user = $search->fetch_assoc(); 
				if(password_verify($password, $user['password'])){
				//if($password == $user['password']){
					session_start();
					$id = $user['id'];
					$duration = time() + 86400 * 30; //1 day
					setcookie("username", $username, $duration, '/');
					setcookie("id", $id, $duration, '/');
					echo '1';
				}else{
					echo 'Invalid Password !';
				}
			}
			
		}else{
			echo 'Please fill in the blank!';
		}
	?>