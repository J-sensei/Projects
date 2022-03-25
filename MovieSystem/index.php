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
		<div class="content">
		<div class="movieBox">
			<h1>Most Popular Movies</h1>
			<center><a class="link" href="movie.php">Discover more Movies</a></center>		
			<ul class="movie">
				<?php
					require 'connect.php';
					$output = '';
					$sql = $con->query("SELECT * FROM movies ORDER BY ranking DESC");
					if($sql){
					for($i = 1; $i <= 4; $i++){
							$movie = $sql->fetch_assoc();
							$id = $movie['id'];
							$name = $movie['name'];
							$img = $movie['link'];
							$output .= "<li>
											<a href='booking.php?movie=$id'><img src='$img' alt='name'></a>
											<div class='.desc'>
												<span>$name</span>
												<span class='ranking'>$i<span>
											</div>
										</li>";
						}
						echo $output;
					}
				?>
			</ul>
		<center><footer class="credit"><span>&copy; Group 4, Mini IT Project</span>
		</footer></center>
		</div>
		</div>
	</body>
</html>