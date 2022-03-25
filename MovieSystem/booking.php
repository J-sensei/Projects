<!DOCTYPE HTML>
<?php
	if(isset($_GET['movie']) && !empty($_GET['movie'])){
			$movie = $_GET['movie'];
		if(isset($_COOKIE['username']) && isset($_COOKIE['id'])){
			require 'php/navbar.php';
			require 'connect.php';
		}else{
			echo "<script>document.location.href = 'sign.php?movie=$movie'</script>";
		}
	}else{
			echo "<script>document.location.href = 'index.php'</script>";
	}
?>
<html>
	<head>
		<title>Movie Booking System</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1.0">

		<link rel="stylesheet" href="css/movie.css">
		<link rel="stylesheet" href="css/booking.css">
	</head>
	
	<body onload="disable()">
	<div class="All">
		<div class="MovieDetail">
			<table>
			<?php
				$sql = $con->query("SELECT * FROM movies WHERE id=$movie");
				if($sql){
					$result = $sql->fetch_assoc();
					$img = $result['link'];
					$price = $result['price'];
					$name = $result['name'];
					$length = $result['length'];
					$coming = $result['comingsoon'];
					if($coming == 1 || $sql->num_rows == 0){
						die('Error');
					}
					$output = "<tr>
									<center><img class='poster' src='$img'></center>
								</tr>
								<tr>
									<th class='tName'>Name</th>
									<th class='tValue'>$name</th>
								</tr>
								<tr>
									<th class='tName'>Price</th>
									<th class='tValue'>RM $price</th>
								</tr>
								<tr>
									<th class='tName'>Playtime</th>
									<th class='tValue'>$length min</th>
								</tr>
								"; 
					$genre = $con->query("SELECT genre.genre FROM movie_genre JOIN genre ON genre.id = movie_genre.genre WHERE movie_genre.movie = $movie");
					$output2 ='';
					if($genre->num_rows > 0){	
					$length = $genre->num_rows;
					$i = 0;
						foreach($genre as $r){
							//$result = $r['genre'];
							if($i == $length - 1){
								$output2 .= $r['genre'];								
							}else{
								$output2 .= $r['genre'].', ';
							}
							$i++;
						}
					}
					
					$output .= "<tr>
									<th class='tName'>Genre</th>
									<th class='tValue'>".$output2."</th>
								</tr>";
				}
				echo $output;
				
			?>
			</table>
		</div>
		<div class="purchaseDetail">
		<div class="Form">
			Number of Seats: <input type="number" id="NumSeat" min="1"><br>
			Date: <input type="date" value="<?php echo date('Y-m-d');?>" min="<?php echo date('Y-m-d');?>" max="<?php echo date('Y-m-d', strtotime('+10 day'));?>" id="MovieDate"><br>
			Time: <select id="MovieTime"><br>
				<option value="11:00">11:00AM</option>
				<option value="14:00">2:00PM</option>
				<option value="20:00">8:00PM</option>
				<option value="00:00">12:00AM</option>
			</select><br>
			<button onclick="takeData()">Select Seat</button>   <button id="bSeat" class="inactive" onclick="goBack();">Back</button>
		</div>
		<div class="seatStruct">
			<center><span id="message"></span></center>
			<label class="screen">Screen</label>
			<table id="seatBlock">
				<?php
					$date = date('Y-m-d');
					$output = '';
					for($seat = 1; $seat <= 50; $seat ++){
	
						if($seat == 1 || $seat == 11 || $seat == 21 || $seat == 31 || $seat == 41){
							$output .= "<tr>";
						}
						if($seat == 6 || $seat == 16 || $seat == 26 || $seat == 36 || $seat == 46){
							$output .= "<td class='seatGap'>";
						}
						$sql = $con->query("SELECT * FROM payments WHERE seat = $seat AND movie = $movie AND time = '11:00' AND date = '$date'");
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

			</table>
			<center><button id="confirm-seat" onclick="getSeat()" class="inactive">Confirm Seat</button></center>
		</div>
		</div>
		</div>
		<div id="details">
		<center>
			<table>
				<tr>
					<span class="title">Confirm Your Movie Tickets</span>
				</tr>
				<tr>
					<th class="name">Name: </th>
					<th><?php echo $_COOKIE['username']; ?></th>
				</tr>
				<tr>
					<th class="name">Seat No: </th>
					<th id="seatNo"></th>
				</tr>
				<tr>
					<th class="name">Date: </th>
					<th id="date"></th>
				</tr>
				<tr>
					<th class="name">Time: </th>
					<th id="time"></th>
				</tr>
				<tr>
					<th class="name">Total Price: </th>
					<th id="price"></th>
				</tr>
				</center>
			</table>
			<center><button onclick="closeDetail()">Close</button>
			<button onclick="purchase()">Purchase</button></center>
		</div>
		<div id="success">
			<span id="success-message"></span><br>
			<button onclick="refresh()">OK</button>
			<span id="btn-fund"></span>
		</div>
	</body>
	<script>
	    //variables
		var ticket;
		var date = '<?php echo $date; ?>';
		var time = '11:00';
		var movie = <?php echo $movie ?>;
		var price = <?php echo $price ?>;
		var seat = [];		
		
		changeTime(date);
		
		function disable(){
			$('.seatStruct *').prop('disabled', true);
			$('#bSeat').prop('disabled', true);
		}
		
		function closeDetail(){
			$('#details').hide(100);
			$('.All').show(100);
			price = <?php echo $price ?>;
			seat = [];	
			$('.loading').css({position:'fixed'});
		}
		
		function takeData(){
			if($('#NumSeat').val().length == 0){
				$('#message').html('Please enter number of seats');
			}else if($('#NumSeat').val() <= 0){
				$('#message').html('Please enter positive value');			
			}else if($('#MovieDate').val() == 0){
				$('#message').html('Please select a date');
			}
			else{
				$('#message').html('Please Select Your Seat Now');
				$('.Form *').prop('disabled', true);
				$('.seatStruct *').prop('disabled', false);	
				$('.occupied').prop('disabled', true);
				$('#bSeat').prop('disabled', false);
				$('#bSeat').removeClass('inactive');
			}
		}
		
		function goBack(){
				$('.Form *').prop('disabled', false);
				$('.seatStruct *').prop('disabled', true);	
				$('.occupied').prop('disabled', true);
				$('#message').html('Please enter the number');
				$('#bSeat').addClass('inactive');
				$('#confirm-seat').addClass('inactive');
				$('input[type=checkbox]').each(function() 
				{ 
					this.checked = false; 
				});
		}
		
		function getSeat(){
			if($('input:checked').length == $('#NumSeat').val()){
				var seatDisplay = '';
				$('#seatBlock :checked').each(function(){
					seat.push($(this).val());
					if($('#NumSeat').val() > 1){
						if($('#NumSeat').val() != seat.length){
							seatDisplay += $(this).val() + ', ';
						}else{
							seatDisplay += $(this).val();							
						}
					}else{
						seatDisplay = $(this).val();
					}
				});
				ticket = $('#NumSeat').val();
				//date = $('#MovieDate').val();
				//time = $('#MovieTime').val();
				//alert(time);
				
				price = price * ticket;
				fixedPrice = price.toFixed(2);	
				
				$('#seatNo').html(seatDisplay);
				$('#date').html(date);
				$('#time').html(time);
				$('#price').html('RM' + fixedPrice);
				//$('.loading').show();
				$('.loading').css({top: 300, position:'absolute'});
				$('#details').show(100);
				$('.All').hide(100);
			}else{
				$('#message').html('Please Select Seat');				
			}
		}
		
		function purchase(){
			$.ajax({
				url:'php/pay.php',
				method:'POST',
				data:{ticket, seat, date, movie, time, price:fixedPrice},
				success:function(data){
					$('#success').show(100);
					$('#details').hide(100);
					$('.purchaseDetail').hide(100);
					$('.MovieDetail').hide(100);
					if(data == 'Not enough money to purchase tickets'){
						$('#btn-fund').html("<button onclick='goToFund()'>Fund my Wallet Now</button>");
					}
					$('#success-message').html(data);
				}
			});
		}
		function refresh(){
			document.location.href = "booking.php?movie=<?php echo $movie ?>";
		}
		
		function goToFund(){
			document.location.href = "account.php?fund=true&movie=<?php echo $movie;?>";
		}

		$(':checkbox').click(function(){
			if($('input:checked').length == $('#NumSeat').val()){
				$(':checkbox').prop('disabled', true);
				$(':checked').prop('disabled', false);
				$('.occupied').prop('disabled', true);
				$('#confirm-seat').removeClass('inactive');
				
			}else{
				$(':checkbox').prop('disabled', false);
				$(':checked').prop('disable', false);
				$('.occupied').prop('disabled', true);
		    	$('#confirm-seat').addClass('inactive');
			}
		});

		
		//check seat available
		$('#MovieDate').change(function(){
			date = $(this).val();
			changeTime(date);
			$.ajax({
				url:'php/checkSeat.php',
				method:'POST',
				data:{movie, date, time},
				success:function(data){
					$('#seatBlock').html(data);
					$('.seatStruct *').prop('disabled', true);
					$(':checkbox').click(function(){
						if($('input:checked').length == $('#NumSeat').val()){
							$(':checkbox').prop('disabled', true);
							$(':checked').prop('disabled', false);
							$('.occupied').prop('disabled', true);
							$('#confirm-seat').removeClass('inactive');
						}else{
							$(':checkbox').prop('disabled', false);
							$(':checked').prop('disable', false);
							$('.occupied').prop('disabled', true);
							$('#confirm-seat').addClass('inactive');
						}
					});
				}
			});
		});
		$('select').change(function(){
			time = $(this).val();
			$.ajax({
				url:'php/checkSeat.php',
				method:'POST',
				data:{movie, date, time},
				success:function(data){
					$('#seatBlock').html(data);
					$('.seatStruct *').prop('disabled', true);
					$(':checkbox').click(function(){
						if($('input:checked').length == $('#NumSeat').val()){
							$(':checkbox').prop('disabled', true);
							$(':checked').prop('disabled', false);
							$('.occupied').prop('disabled', true);
							$('#confirm-seat').removeClass('inactive');
						}else{
							$(':checkbox').prop('disabled', false);
							$(':checked').prop('disable', false);
							$('.occupied').prop('disabled', true);
							$('#confirm-seat').addClass('inactive');
						}
					});
				}
			});
		});
				/*<option value="11:00">11:00AM</option>
				<option value="14:00">2:00PM</option>
				<option value="20:00">8:00PM</option>
				<option value="00:00">12:00AM</option>*/
		function changeTime(date){
			var today = new Date();
			month = today.getMonth()+1;
			if(month < 10){month = '0' + month;}
			var current_date = today.getFullYear() + '-' + month + '-' + today.getDate();
			if(date == current_date){
				var current_time = today.getHours() + ":" + today.getMinutes();
				//alert(current_time);
				$('#MovieTime').html('');
				if(current_time > '20:00'){
					time = '00:00';
					$('#MovieTime').html('<option value="00:00">12:00AM</option>');
				}else if(current_time > '14:00'){
					time = '20:00';
					$('#MovieTime').html('<option value="20:00">8:00PM</option><option value="00:00">12:00AM</option>');
				}else if(current_time > '11:00'){
					time = '14:00';
					$('#MovieTime').html('<option value="14:00">2:00PM</option><option value="20:00">8:00PM</option><option value="00:00">12:00AM</option>');
				}else{
					time = '11:00';
					$('#MovieTime').html('<option value="11:00">11:00AM</option><option value="14:00">2:00PM</option><option value="20:00">8:00PM</option><option value="00:00">12:00AM</option>');
				}
			}else{
				time = '11:00';
				$('#MovieTime').html('<option value="11:00">11:00AM</option><option value="14:00">2:00PM</option><option value="20:00">8:00PM</option><option value="00:00">12:00AM</option>');
			}
			$.ajax({
				url:'php/checkSeat.php',
				method:'POST',
				data:{movie, date, time},
				success:function(data){
					$('#seatBlock').html(data);
					$('.seatStruct *').prop('disabled', true);
					$(':checkbox').click(function(){
						if($('input:checked').length == $('#NumSeat').val()){
							$(':checkbox').prop('disabled', true);
							$(':checked').prop('disabled', false);
							$('.occupied').prop('disabled', true);
							$('#confirm-seat').removeClass('inactive');
						}else{
							$(':checkbox').prop('disabled', false);
							$(':checked').prop('disable', false);
							$('.occupied').prop('disabled', true);
							$('#confirm-seat').addClass('inactive');
						}
					});
				}
			});
		}
		
	</script>
</html>