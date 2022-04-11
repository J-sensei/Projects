<!DOCTYPE HTML>
<html>
	<head>
		<title>Movie Booking System</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1.0">

		<link rel="stylesheet" href="css/movie.css">
		<?php require 'php/navbar.php' ?>
	</head>
	
	<body>
		<div class="movieBox">
			<center><div class="btn-show">
				<button id="btn-show" class="selected">Now Showing</button>
				<button id="btn-soon">Coming Soon</button>
			</div></center>
			<center><div class="searchBar">
				<input type="text" name="search" id="search" placeholder="Search movies">
				Genre: <select id="genre">
							<option value="0">All</option>
							<?php
								require 'connect.php';
								
								$sql = $con->query('SELECT * FROM genre');
								$output = '';
								
								while($result = $sql->fetch_assoc()){
									$id = $result['id'];
									$genre = $result['genre'];
									$output .= "<option value='$id'>$genre</option>";
								}
								
								echo $output;
							?>
						</select>
			</div></center>
			<div id="result" class="movie"></div>
		</div>
	</body>
	<script>
		$(document).ready(function(){
			var search = '';
			var genre;
			var pageNum = 1;
			var showing = 0;
			
			$('#btn-show').on('click', function(){
				$('#search').val('');
				search = '';
				pageNum = 1;
				showing = 0;
				$('#btn-show').addClass('selected');
				$('#btn-soon').removeClass('selected');
				$.ajax({
					url:'php/search.php',
					method:'POST',
					data:{showing:showing},
					success:function(data){
						$('#result').html(data);
					}
				});
				$.ajax({
					url:'php/genre.php',
					method:'POST',
					data:{showing:showing},
					success:function(data){
						$('#genre').html(data);
					}
				});
			});
			
			$('#btn-soon').on('click', function(){
				$('#search').val('');
				search = '';
				pageNum = 1;
				showing = 1;
				$('#btn-show').removeClass('selected');
				$('#btn-soon').addClass('selected');	
				$.ajax({
					url:'php/search.php',
					method:'POST',
					data:{showing:showing},
					success:function(data){
						$('#result').html(data);
					}
				});	
				$.ajax({
					url:'php/genre.php',
					method:'POST',
					data:{showing:showing},
					success:function(data){
						$('#genre').html(data);
					}
				});				
			});
			
			$.ajax({
				url:'php/search.php',
				method:'POST',
				data:{showing:showing},
				success:function(data){
					$('#result').html(data);
				}
			});
			
			$('#search').on('keyup',function(){
				search = $('#search').val();	
				genre = '';
				$.ajax({
					url:'php/search.php',
					method:'POST',
					data:{showing:showing, search:search},
					success:function(data){
						$('#result').html(data);
					}
				});
			});
			
			$('select').change(function(){
				genre = $(this).val();
				//alert(genre);
				if(genre == 0){
					$.ajax({
						url:'php/search.php',
						method:'POST',
						data:{showing:showing},
						success:function(data){
							$('#result').html(data);
						}
					});					
				}else{
					$.ajax({
						url:'php/search.php',
						method:'POST',
						data:{showing:showing, genre:genre},
						success:function(data){
							$('#result').html(data);
						}
					});
				}
			});
			
			$(document).on('click', '#btn-page', function(){
				var page = $(this).attr('value');
				//alert(page + '  ' + pageNum);
				if(pageNum != page){
				pageNum = page;
			    //alert(pageNum);

				//alert('Page:' + page + 'Search: ' + search + 'Genre: ' + genre + 'Showing: ' + showing);
				$.ajax({
					url:'php/search.php',
					method:'POST',
					data:{showing:showing, page:page, genre:genre, search:search},
					success:function(data){
						//alert(data);
						$('#result').html(data);
						$('.page1').removeClass('selected');
						$('.page'+pageNum).addClass('selected');
					}
				});
				}
			});
		});
	</script>
</html>