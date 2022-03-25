<?php
	if(isset($_COOKIE['username']) && isset($_COOKIE['id'])){
		require 'php/navbar.php';
		require 'connect.php';
		
		$fund = false;
		
		if(isset($_GET['fund'])){
			$fund = true;
		}
		if(isset($_GET['movie'])){
			$movie = $_GET['movie'];
		}
	}else{
		echo "<script>document.location.href = 'sign.php'</script>";
	}
?>
<!DOCTYPE HTML>
<html>
	<head>
		<title>My Account</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1.0">
		<link rel="stylesheet" href="css/general.css">
		<link rel="stylesheet" href="css/account.css">

	</head>
	
	<body>
		<div class="my-content">
			<center><div class="btn-group">
				<button id="btn-fund" class="selected">Fund my wallet</button>
				<button id="btn-purchase">Purchase History</button>
				<button id="btn-password">Change Password</button>
				<a href="logout.php"><button>Logout</button></a>
			</div>
			<div class="account">
				<table class="account-details">
					<tr class="head">
						<th>Name</th>
						<th>Wallet</th>
					</tr>
					<?php
						$id = $_COOKIE['id'];
						$sql = $con->query("SELECT * FROM users WHERE id=$id");
						if($sql){
							$result = $sql->fetch_assoc();
							$name = $result['username'];
							$wallet = $result['wallet'];
							echo "<tr>
									<th>$name</th>
									<th>RM $wallet</th>
								</tr>";
						}
					?>
				</table>
			</div>
			<div id="fund" class="fund">
				<label>Fund my Wallet</label>
				<form id="fund-form" method="POST" autocomplete="off">
					Amount: RM <input type="number" id="amount" min="5" required>
					<input type="submit" value="Add">
				</form>
			</div>
			<div id="password" class="fund" style="display:none">
				<label>Change Password</label>
				<form id="password-form" method="POST" autocomplete="off">
					Current Password: <input id="current-password" type="password" style="margin-top:10px" required><br>
					New Password: <input id="new-password" type="password" style="margin-top:10px" required><br>
					<input type="submit" value="Change" style="margin-top:10px">
				</form>
			</div>
			<div class="purchase-history">

			</div>
			</center>
		</div>
		<div class="message">
		<center>
		<span id="msg"></span><br><button onclick="back()">Ok</button>
		</center>
		</div>
		<div class="message-password">
		<center>
		<span id="msg2"></span><br><button onclick="back3()">Ok</button>
		</center>
		</div>
		<div id="confirm" class="confirm">
		<center>
		<span>Are you sure want to refund "<span id="movie"></span>", tickets purchased within 3 days are eligible to refund)</span><br><button onclick="refund()">Yes</button>    <button onclick="back2()">No</button>
		</center>
		</div>
	</body>
	<script>
	//variables
		var pageNum = 1;
		var id;
		var movie;
		var date;
		var fund = <?php if(isset($_GET['fund'])){echo $fund;}else{echo 'false';}?>;
		var change = false;
		
		$.ajax({
			url:'php/purchaseHistory.php',
			method:'POST',
			data:{},
			success:function(data){
				$('.purchase-history').html(data);
			}
		});
		
		$(document).on('click', '#btn-refund', function(){
			id = $(this).closest('tr').children('th:eq(0)').text();
			movie = $(this).closest('tr').children('th:eq(1)').text();
			date = $(this).closest('tr').children('th:eq(3)').text();
			var seat = $(this).closest('tr').children('th:eq(2)').text();
			$('#movie').html(movie + ', Seat No.' + seat);
			$('.confirm').toggle(100);
		});
		
		$(document).on('click', '#btn-page', function(){
			var page = $(this).attr('value');
			pageNum = page;
			$.ajax({
				url:'php/purchaseHistory.php',
				method:'POST',
				data:{page:page},
				success:function(data){
					$('.purchase-history').html(data);
					$('.page1').removeClass('selected');
					$('.page'+pageNum).addClass('selected');
				}
			});
		});
		$('#btn-fund').click(function(){
			$('#fund').slideDown();
			$('.account').slideDown();
			$('#password').slideUp();
			$('.purchase-history').slideUp();
			$('#btn-fund').addClass('selected');
			$('#btn-purchase').removeClass('selected');
			$('#btn-password').removeClass('selected');
			$('#password-form')[0].reset();
		});
		$('#btn-purchase').click(function(){
			$('#fund').slideUp();
			$('.account').slideUp();
			$('#password').slideUp();
			$('.purchase-history').slideDown();
			$('#btn-fund').removeClass('selected');
			$('#btn-purchase').addClass('selected');
			$('#btn-password').removeClass('selected');
			$('#fund-form')[0].reset();
			$('#password-form')[0].reset();
		});
		$('#btn-password').click(function(){
			$('#fund').slideUp();
			$('.account').slideUp();
			$('#password').slideDown();
			$('.purchase-history').slideUp();
			$('#btn-fund').removeClass('selected');
			$('#btn-purchase').removeClass('selected');
			$('#btn-password').addClass('selected');
			$('#fund-form')[0].reset();
		});
		
		$('#fund').on('submit', function(){
			var amount = $('#amount').val();
			$.ajax({
				url:'php/fund.php',
				method:'POST',
				data:{amount:amount},
				success:function(data){
					$('#msg').html(data);
					$('.message').toggle(100);
				}
			});
			return false;
		});
		$('#password').on('submit', function(){
			var current = $('#current-password').val();
			var newpass = $('#new-password').val();
			
			if(current.length < 8 || newpass.length < 8){
				$('#msg2').html('Password cannot less than 8 characters');
				$('.message-password').toggle(100);
			}else{
				$.ajax({
					url:'php/change.php',
					method:'POST',
					data:{current:current, newpass:newpass},
					success:function(data){
						$('#msg2').html(data);
						$('.message-password').toggle(100);
						if(data == 'Password has changed'){
							change = true;
						}
					}
				});	
			}
			
			return false;
		});
		function refund(){
			$('.confirm').toggle(100);
			$.ajax({
				url:'php/refund.php',
				method:'POST',
				data:{id:id, movie:movie, date:date},
				success:function(data){
					$('#msg').html(data);
					$('.message').toggle(100);
				}
			});			
		}
		function back(){
			$('.message').toggle(100);
			if(fund){
				document.location.href = "booking.php?movie=<?php if(isset($_GET['movie'])){echo $movie;}else{echo '0';}?>";				
			}else{
				document.location.href = "account.php";
			}
		}
		function back2(){
			$('#confirm').toggle(100);			
		}
		function back3(){
			$('.message-password').toggle(100);	
			if(change){
				document.location.href = "account.php";	
			}		
		}
	</script>
</html>