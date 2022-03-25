<link rel="stylesheet" href="css/menu.css">
<script type="text/javascript" src="libraries/jquery-3.3.1.min.js"></script>
<ul class="menu">
	<li><a href="index.php">Home</a></li>
	<li><a href="movie.php">Movies</a></li>
	<li><a href="<?php if(isset($_COOKIE['username'])){echo 'account.php';}else{echo 'sign.php';} ?>"><?php if(isset($_COOKIE['username'])){echo $_COOKIE['username'];}else{echo 'Login';}?></a></li>		
	<?php if(isset($_COOKIE['username'])){ echo '<li><a class="logout" href="logout.php">Logout</a></li>';} ?>
	</ul>

<div id="destroy">
<div id="bar" class="containerBar" onclick="transform(this)">
	<div class="bar1"></div>
	<div class="bar2"></div>
	<div class="bar3"></div>
</div>
</div>
<ul class="hidden-menu">
	<li><a href="index.php">Home</a></li>
	<li><a href="movie.php">Movies</a></li>
	<li><a href="<?php if(isset($_COOKIE['username'])){echo 'account.php';}else{echo 'sign.php';} ?>"><?php if(isset($_COOKIE['username'])){echo $_COOKIE['username'];}else{echo 'Login';}?></a></li>		
	<?php if(isset($_COOKIE['username'])){ echo '<li><a class="logout" href="logout.php">Logout</a></li>';} ?>
</ul>
<div id="page-loading" class="loading"><span>Getting Page Information...</span></div>
<div id="loading" class="loading">
<span></span>
<span></span>
<span></span>
</div>
<script>
	var pageLoading = $('#page-loading').hide();
    $(document).ready(function() {
        pageLoading.remove();
    });
	var loading = $('#loading').hide();
	$(document)
	  .ajaxStart(function () {
		loading.show();
	  })
	  .ajaxStop(function () {
		loading.hide();
	  });

	function transform(x){
		x.classList.toggle("change");
		$('.hidden-menu').slideToggle();
	}
	
	var windowsize = $(window).width();

	$(window).resize(function() {
	  windowsize = $(window).width();
	  //reset button if screen resize
	  if (windowsize > 933) {
		  $('#destroy').html('<div id="bar" class="containerBar" onclick="transform(this)"><div class="bar1"></div><div class="bar2"></div><div class="bar3"></div></div>');
		  $('.hidden-menu').hide();
	  }
	});
</script>