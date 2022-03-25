<?php
	require '../connect.php';
	
	$output = '';
	$totalRows = 4;
	$isSearch = false;
	$isGenre = false;
	$showing = 0;
	
	if(empty($_POST['page'])){
		$pageI = 1;
	}else{
		$pageI = $_POST['page'];
	}
	if(!empty($_POST['showing'])){
		$showing = $_POST['showing'];
	}
	
	if(!empty($_POST['page']) && !empty($_POST['genre'])){
		$isSearch = true;
	}
	if(!empty($_POST['page']) && !empty($_POST['search'])){
		$isSearch = true;
	}
	
	$page = ($pageI - 1) * $totalRows;
	
	$output .= '<ul>';

	if(isset($_POST['search']) && !$isSearch){
		$search = $_POST['search'];
		$sql = "SELECT * FROM movies WHERE name LIKE '%$search%' AND comingsoon = $showing LIMIT $page, $totalRows";
		$result = $con->query($sql);
		if($result->num_rows > 0){
			while($movie = $result->fetch_assoc()){
					$id = $movie['id'];
					$name = $movie['name'];
					//$price = $movie['price'];
					$img = $movie['link'];
					if($showing == 0){
					$output .= "<li>
									<a title='$name' href='booking.php?movie=$id'><img src='$img' alt='name'></a>
									<div class='.desc'>
										<span>$name</span>
									</div>
								</li>";
					}else{
					$output .= "<li>
									<img title='$name' src='$img' alt='name'>
									<div class='.desc'>
										<span>$name</span>
									</div>
								</li>";					
					}
				}
			$output .= '</ul><center><div id="page">';
			$search = $con->query("SELECT *	FROM movies WHERE name LIKE '%$search%' AND comingsoon = $showing");
			$totalPage = $search->num_rows;
			$total = ceil($totalPage/$totalRows);
			if($total > 4){
				$output .= "<span id='btn-page' value='1'>F</span>";
				$count = 0;
				for($i = 1; $i <= 4; $i++){
					if($pageI >= $total){
						$pageI = $pageI - 2;
					}else if($pageI >= ($total - 1)){
						$pageI = $pageI - 1;
					}
					if($pageI >= 2){
						$p = $pageI - 1;	
					}else{
						$p = 1;
					}
					$p += $count;
					if($p == 1){
						$output .= '<span id="btn-page" class="selected page'.$p.'" value="'.$p.'">'.$p.'</span>';					
					}else{
						$output .= '<span id="btn-page" class="page'.$p.'" value="'.$p.'">'.$p.'</span>';
					}
					$count ++;
				}	
				$output .= "<span id='btn-page' value='$total'>L</span>";
			}else{
				for($i = 1; $i <= $total; $i ++){
					if($i == 1){
						$output .= "<span id='btn-page' class='selected page1' value='$i'>$i</span>";						
					}else{
						$output .= "<span id='btn-page' class='page".$i."' value='$i'>$i</span>";
					}
				}
			}
			$output .= '</div></center>';
		}else{
			$output = '<li><span>No result found</span></li>';
		}
	}else if(isset($_POST['genre']) && !$isSearch){
			
			$genre = $_POST['genre'];
			
			$sql = $con->query("SELECT movies.id, movies.name, movies.link FROM movie_genre JOIN movies ON movies.id = movie_genre.movie JOIN genre ON genre.id = movie_genre.genre WHERE genre.id = $genre AND movies.comingsoon = $showing LIMIT $page, $totalRows");
			if($sql->num_rows > 0){
				while($movie = $sql->fetch_assoc()){
					$id = $movie['id'];
					$name = $movie['name'];
					$img = $movie['link'];
					if($showing == 0){
					$output .= "<li>
									<a title='$name' href='booking.php?movie=$id'><img src='$img' alt='name'></a>
									<div class='.desc'>
										<span>$name</span>
									</div>
								</li>";
					}else{
					$output .= "<li>
									<img title='$name' src='$img' alt='name'>
									<div class='.desc'>
										<span>$name</span>
									</div>
								</li>";					
					}
					}
			$output .= '</ul><center><div id="page">';
			$search = $con->query("SELECT movies.id, movies.name, movies.link FROM movie_genre JOIN movies ON movies.id = movie_genre.movie JOIN genre ON genre.id = movie_genre.genre WHERE genre.id = $genre AND movies.comingsoon = $showing");
			$totalPage = $search->num_rows;
			$total = ceil($totalPage/$totalRows);
			if($total > 4){
				$output .= "<span id='btn-page' value='1'>F</span>";
				$count = 0;
				for($i = 1; $i <= 4; $i++){
					if($pageI >= $total){
						$pageI = $pageI - 2;
					}else if($pageI >= ($total - 1)){
						$pageI = $pageI - 1;
					}
					if($pageI >= 2){
						$p = $pageI - 1;	
					}else{
						$p = 1;
					}
					$p += $count;
					if($p == 1){
						$output .= '<span id="btn-page" class="selected page'.$p.'" value="'.$p.'">'.$p.'</span>';					
					}else{
						$output .= '<span id="btn-page" class="page'.$p.'" value="'.$p.'">'.$p.'</span>';
					}
					$count ++;
				}
				$output .= "<span id='btn-page' value='$total'>L</span>";				
			}else{
				for($i = 1; $i <= $total; $i ++){
					if($i == 1){
						$output .= "<span id='btn-page' class='selected page1' value='$i'>$i</span>";						
					}else{
						$output .= "<span id='btn-page' class='page".$i."' value='$i'>$i</span>";
					}
				}
			}
			$output .= '</div></center>';
				
			}else{
				$output = '<li><span>No result found</span></li>';
			}
	}else if($isSearch){
			if(isset($_POST['genre']) && !empty($_POST['genre'])){
			$genre = $_POST['genre'];
			
			$sql = $con->query("SELECT movies.id, movies.name, movies.link FROM movie_genre JOIN movies ON movies.id = movie_genre.movie JOIN genre ON genre.id = movie_genre.genre WHERE genre.id = $genre AND movies.comingsoon = $showing LIMIT $page, $totalRows");
			
			if($sql->num_rows > 0){
				while($movie = $sql->fetch_assoc()){
					$id = $movie['id'];
					$name = $movie['name'];
					$img = $movie['link'];
					if($showing == 0){
					$output .= "<li>
									<a title='$name' href='booking.php?movie=$id'><img src='$img' alt='name'></a>
									<div class='.desc'>
										<span>$name</span>
									</div>
								</li>";
					}else{
					$output .= "<li>
									<img title='$name' src='$img' alt='name'>
									<div class='.desc'>
										<span>$name</span>
									</div>
								</li>";					
					}
					}
			$output .= '</ul><center><div id="page">';
			$search = $con->query("SELECT movies.id, movies.name, movies.link FROM movie_genre JOIN movies ON movies.id = movie_genre.movie JOIN genre ON genre.id = movie_genre.genre WHERE genre.id = $genre AND movies.comingsoon = $showing");
			$totalPage = $search->num_rows;
			$total = ceil($totalPage/$totalRows);
			if($total > 4){
				$output .= "<span id='btn-page' value='1'>F</span>";
				$count = 0;
				for($i = 1; $i <= 4; $i++){
					if($pageI >= $total){
						$pageI = $pageI - 2;
					}else if($pageI >= ($total - 1)){
						$pageI = $pageI - 1;
					}
					if($pageI >= 2){
						$p = $pageI - 1;	
					}else{
						$p = 1;
					}
					$p += $count;
					if($p == 1){
						$output .= '<span id="btn-page" class="selected page'.$p.'" value="'.$p.'">'.$p.'</span>';					
					}else{
						$output .= '<span id="btn-page" class="page'.$p.'" value="'.$p.'">'.$p.'</span>';
					}
					$count ++;
				}
				$output .= "<span id='btn-page' value='$total'>L</span>";				
			}else{
				for($i = 1; $i <= $total; $i ++){
					if($i == 1){
						$output .= "<span id='btn-page' class='selected page1' value='$i'>$i</span>";						
					}else{
						$output .= "<span id='btn-page' class='page".$i."' value='$i'>$i</span>";
					}
				}
			}
			$output .= '</div></center>';
			}else{
				$output = '<li><span>No result found</span></li>';
			}
			
			}else if(isset($_POST['search']) && !empty($_POST['search'])){
				$search = $_POST['search'];
				$sql = "SELECT * FROM movies WHERE name LIKE '%$search%' AND comingsoon = $showing LIMIT $page, $totalRows";
				$result = $con->query($sql);
				if($result->num_rows > 0){
					while($movie = $result->fetch_assoc()){
							$id = $movie['id'];
							$name = $movie['name'];
							//$price = $movie['price'];
							$img = $movie['link'];
							if($showing == 0){
							$output .= "<li>
											<a title='$name' href='booking.php?movie=$id'><img src='$img' alt='name'></a>
											<div class='.desc'>
												<span>$name</span>
											</div>
										</li>";
							}else{
							$output .= "<li>
											<img title='$name' src='$img' alt='name'>
											<div class='.desc'>
												<span>$name</span>
											</div>
										</li>";					
							}
						}
					$output .= '</ul><center><div id="page">';
					$search = $con->query("SELECT *	FROM movies WHERE name LIKE '%$search%' AND comingsoon = $showing");
					$totalPage = $search->num_rows;
					$total = ceil($totalPage/$totalRows);
			if($total > 4){
				$count = 0;
				$output .= "<span id='btn-page' value='1'>F</span>";
				for($i = 1; $i <= 4; $i++){
					if($pageI >= $total){
						$pageI = $pageI - 2;
					}else if($pageI >= ($total - 1)){
						$pageI = $pageI - 1;
					}
					if($pageI >= 2){
						$p = $pageI - 1;	
					}else{
						$p = 1;
					}
					$p += $count;
					if($p == 1){
						$output .= '<span id="btn-page" class="selected page'.$p.'" value="'.$p.'">'.$p.'</span>';					
					}else{
						$output .= '<span id="btn-page" class="page'.$p.'" value="'.$p.'">'.$p.'</span>';
					}
					$count ++;
				}
				$output .= "<span id='btn-page' value='$total'>L</span>";				
			}else{
				for($i = 1; $i <= $total; $i ++){
					if($i == 1){
						$output .= "<span id='btn-page' class='selected page1' value='$i'>$i</span>";						
					}else{
						$output .= "<span id='btn-page' class='page".$i."' value='$i'>$i</span>";
					}
				}
			}
					$output .= '</div></center>';
					}
			}
	}else{
		$sql = $con->query("SELECT * FROM movies WHERE comingsoon = $showing LIMIT $page, $totalRows");
		if($sql){
			while($movie = $sql->fetch_assoc()){
				$id = $movie['id'];
				$name = $movie['name'];
				//$price = $movie['price'];
				$img = $movie['link'];
				if($showing == 0){
				$output .= "<li>
								<a title='$name' href='booking.php?movie=$id'><img src='$img' alt='name'></a>
								<div class='.desc'>
									<span>$name</span>
								</div>
							</li>";
				}else{
				$output .= "<li>
								<img title='$name' src='$img' alt='name'>
								<div class='.desc'>
									<span>$name</span>
								</div>
							</li>";					
				}
			}
		}
			$output .= '</ul><center><div id="page">';
			$search = $con->query("SELECT *	 FROM movies WHERE comingsoon = $showing");
			$totalPage = $search->num_rows;
			$total = ceil($totalPage/$totalRows);
			if($total > 4){
				$count = 0;
				$output .= "<span id='btn-page' value='1'>F</span>";
				for($i = 1; $i <= 4; $i++){
					if($pageI >= $total){
						$pageI = $pageI - 2;
					}else if($pageI >= ($total - 1)){
						$pageI = $pageI - 1;
					}
					if($pageI >= 2){
						$p = $pageI - 1;	
					}else{
						$p = 1;
					}
					$p += $count;
					if($p == 1){
						$output .= '<span id="btn-page" class="selected page'.$p.'" value="'.$p.'">'.$p.'</span>';					
					}else{
						$output .= '<span id="btn-page" class="page'.$p.'" value="'.$p.'">'.$p.'</span>';
					}
					$count ++;
				}	
				$output .= "<span id='btn-page' value='$total'>L</span>";
			}else{
				for($i = 1; $i <= $total; $i ++){
					if($i == 1){
						$output .= "<span id='btn-page' class='selected page1' value='$i'>$i</span>";						
					}else{
						$output .= "<span id='btn-page' class='page".$i."' value='$i'>$i</span>";
					}
				}
			}
			$output .= '</div></center>';
	}
	echo $output;
	
?>