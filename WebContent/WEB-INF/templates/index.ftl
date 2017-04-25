<link rel="stylesheet" href="/team1foodorderapp/css/teamone.css">

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="/team1foodorderapp/css/teamone.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<title>Welcome to Team1 Restaurant</title>
<link rel="icon" href="/team1foodorderapp/favicon.ico" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>

<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="index.html">
				Index</a>
		</div>
		<ul class="nav navbar-nav">
			<li class="active"><a
				href="index.html">Home</a></li>
		</ul>
						<ul class="nav navbar-nav">
			<li class="active"><a
				href="admin.html">Admin Login</a></li>
		</ul>
		<div class="shopping-cart" onclick="location.href=&quot;/team1foodorderapp/orders?cartitems=true&quot;"><a href="/team1foodorderapp/orders?cartitems=true">${cart.totalPrice?string.currency}</a></div>
	</div>
	
</nav>
	</div>
	<div class="w3-content w3-section" style="max-width:500px">
	  <img class="mySlides" onclick="location.href=&quot;/team1foodorderapp/orders?catagorylists=true&quot;" src="/team1foodorderapp/images/teamonemain.png" style="width:100%">
	  <img class="mySlides" onclick="location.href=&quot;/team1foodorderapp/orders?catagorylists=true&quot;" src="/team1foodorderapp/images/teamone46239.png" style="width:100%">
	  <img class="mySlides" onclick="location.href=&quot;/team1foodorderapp/orders?catagorylists=true&quot;" src="/team1foodorderapp/images/imagecat1.jpeg" style="width:100%">
	  <img class="mySlides" onclick="location.href=&quot;/team1foodorderapp/orders?catagorylists=true&quot;" src="/team1foodorderapp/images/imagecat2.jpg" style="width:100%">
	  <img class="mySlides" onclick="location.href=&quot;/team1foodorderapp/orders?catagorylists=true&quot;" src="/team1foodorderapp/images/imagecat4.jpg" style="width:100%">
	  <img class="mySlides" onclick="location.href=&quot;/team1foodorderapp/orders?catagorylists=true&quot;" src="/team1foodorderapp/images/imagecatveg.jpeg" style="width:100%">
	  <img class="mySlides" onclick="location.href=&quot;/team1foodorderapp/orders?catagorylists=true&quot;" src="/team1foodorderapp/images/imagescatsoup.jpg" style="width:100%">
	</div>

<script>
var myIndex = 0;
carousel();

function carousel() {
    var i;
    var x = document.getElementsByClassName("mySlides");
    for (i = 0; i < x.length; i++) {
       x[i].style.display = "none";  
    }
    myIndex++;
    if (myIndex > x.length) {myIndex = 1}    
    x[myIndex-1].style.display = "block";  
    setTimeout(carousel, 2000); // Change image every 2 seconds
}
</script>


</body>
</html>