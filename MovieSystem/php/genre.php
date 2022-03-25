<?php
	require '../connect.php';
	
	$showing = 0;
	
	if(isset($_POST['showing']) && !empty($_POST['showing'])){
		$showing = $_POST['showing'];
	}
	
	$sql = "SELECT DISTINCT genre.id, genre.genre FROM `movie_genre` JOIN movies ON movie_genre.movie = movies.id JOIN genre ON movie_genre.genre = genre.id WHERE movies.comingsoon = $showing";
	$output = '';
	$find = $con->query($sql);
	$output .= '<option value="0">All</option>';
	if($find->num_rows > 0){
		while($result = $find->fetch_assoc()){
			$id = $result['id'];
			$genre = $result['genre'];
			$output .= "<option value='$id'>$genre</option>";
		}
	}else{
		echo '<option>None</option>';
	}
	
	echo $output;
?>