<!DOCTYPE HTML>
<html>
	<head>
		<title>Movie Booking System</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1.0">
		<link rel="stylesheet" href="css/general.css">

		<?php require 'php/navbar.php' ?>
	</head>

	<body>
		<div class="box">
					<center><h1>Login / Register</h1></center>
		<center><div class="btn-group">
				<button id="sign-in" class="selected">Sign In</button>
				<button id="sign-up">Join Now</button>
		</div></center>
		<div id="sign-in-box">
			<form id="login" method="POST" autocomplete="off"> 
				<center><label id="message"></label></center>
				<div class="info">
					<input maxlength="40" id="login-username" type="text" name="username" autocomplete="off" required>
					<label>Username</label>
				</div>
				<div class="info">
					<input maxlength="40" id="login-password" type="password" name="password" autocomplete="off" required>
					<label>Password</label>
				</div>
				<input id="login-submit" type="submit" name="submit" value="Sign In">
			</form>
		</div>
		<div id="sign-up-box">
			<form id="register" method="POST" autocomplete="off"> 
				<center><label id="message2"></label></center>
				<div class="info">
					<input maxlength="40" id="register-username" type="text" name="username" autocomplete="off" required>
					<label>Username</label>
				</div>
				<div class="info">
					<input maxlength="40" id="register-password" type="password" name="password" autocomplete="off" required>
					<label>Password</label>
				</div>
				<div class="info">
					<input maxlength="40" id="register-rePassword" type="password" name="rePassword" autocomplete="off" required>
					<label>Retype Password</label>
				</div>
				<input id="register-submit" type="submit" name="submit" value="Sign Up">
			</form>
		</div>
		</div>
	</body>
	<script>
		var movie = <?php if(!empty($_GET['movie'])){echo $_GET['movie'];}else{echo 'false';}?>
	
		$(document).ready(function(){
			$('#sign-in').on('click',function(){
				$('#sign-in-box').slideDown();
				$('#sign-up-box').slideUp();
				$('#sign-up').removeClass('selected');
				$('#sign-in').addClass('selected');
				$('#message2').html('');
				$('#register')[0].reset();
			});
			$('#sign-up').on('click',function(){
				$('#sign-up-box').slideDown();
				$('#sign-in-box').slideUp();
				$('#sign-up').addClass('selected');
				$('#sign-in').removeClass('selected');
				$('#message').html('');
				$('#login')[0].reset();
			});
			
			$('#login').on('submit', function(){
				var username = $('#login-username').val();
				var password = $('#login-password').val();
				$.ajax({
					url:'php/login.php',
					method:'POST',
					data:{username:username, password:password},
					success:function(data){
						if(data == '1'){
							if(movie != false){
								window.location.replace('booking.php?movie='+movie);								
							}else{
								window.location.replace('index.php');
							}
						}else{
							$('#message').html(data);
						}
					}
				});
				return false;
			});
			$('#register').on('submit', function(){
				var username = $('#register-username').val();
				var password = $('#register-password').val();
				var rePassword = $('#register-rePassword').val();
				
				if(password.length < 8){
					$('#message2').html('Please enter more than 8 chracters');
				}else if(password.includes(username)){
					$('#message2').html('Password cannot contain username');					
				}else{
					$.ajax({
						url:'php/register.php',
						method:'POST',
						data:{username:username, password:password, rePassword:rePassword},
						success:function(data){
							if(data == 'Register success'){
								$('#register')[0].reset();
							}
							$('#message2').html(data);
						}
					});
				}
				return false;
			});
		});
	</script>
</html>