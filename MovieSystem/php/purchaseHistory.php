<?php
	require '../connect.php';
	
	$output = '';
	$totalRows = 5;
	$id = $_COOKIE['id'];
	
	if(empty($_POST['page'])){
		$pageI = 1;
	}else{
		$pageI = $_POST['page'];
	}
	$page = ($pageI - 1) * $totalRows;
	
	$output .=	'	<table><tr class="head">
						<th>Reference Number</th>
						<th>Movie</th>
						<th>Seat No</th>
						<th>Date</th>
						<th>Time</th>
					</tr>';
	
	
	$sql = $con->query("SELECT payments.id, movies.name, payments.seat, payments.date, payments.time FROM payments JOIN movies ON payments.movie = movies.id WHERE payments.user  = $id ORDER BY payments.id DESC LIMIT $page, $totalRows");

	if($sql->num_rows > 0){
		while($result = $sql->fetch_assoc()){
			$num = $result['id'];
			$movie = $result['name'];
			$seat = $result['seat'];
			$date = $result['date'];
			$time = $result['time'];
			
			$output .="<tr>
							<th>$num</th>
							<th>$movie</th>
							<th>$seat</th>
							<th>$date</th>
							<th>$time</th>
							<th><button id='btn-refund'>Refund</button></th>
						</tr>";
		}
	}else{
		$output = 'No result found';
	}
	$output .= '</table><div id="page">';
	$search = $con->query("SELECT payments.id, movies.name, payments.seat, payments.date, payments.time FROM payments JOIN movies ON payments.movie = movies.id WHERE payments.user  = $id");	
	$totalPage = $search->num_rows;
	$total = ceil($totalPage/$totalRows);
	//update page btn every time user make a click, restrict btn numbers to 4
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
		for($i = 1; $i <= $total; $i++){
			if($i == 1){
				$output .= "<span id='btn-page' class='selected page1' value='$i'>$i</span>";	
			}else{
				$output .= '<span id="btn-page" class="page'.$i.'"value="'.$i.'">'.$i.'</span>';
			}
		}
	}
			/*for($i = 1; $i <= $total; $i++){
			$output .= '<span id="btn-page" value="'.$i.'">'.$i.'</span>';
			}*/
	$output .= '</div>';
	
	echo $output;
?>