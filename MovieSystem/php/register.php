<?php
		require '../connect.php';
		
		if(isset($_POST['username']) && isset($_POST['password']) && isset($_POST['rePassword']) && !empty($_POST['username']) && !empty($_POST['password']) && !empty($_POST['rePassword'])){
			$username = $con->real_escape_string($_POST['username']);
			$password = $con->real_escape_string($_POST['password']);
			$rePassword = $con->real_escape_string($_POST['rePassword']);
			if($password == $rePassword){
				$search = $con->query("SELECT * FROM users WHERE username = '$username'");//search from the table
				
				if($search->num_rows == 0){
					$password = $con->real_escape_string(password_hash($_POST['password'], PASSWORD_BCRYPT));
					//die($password);
					$sql = $con->query("INSERT INTO users(username, password) VALUES('$username', '$password')");
					
					if($sql){
						echo "Register success";
					}else{
						echo "Error";
					}
				}else{
					echo 'Username already exist';
				}
			}else{
				echo "Password doest not match";
			}
		}else{
			echo 'Please fill in the blank!';
		}
?>