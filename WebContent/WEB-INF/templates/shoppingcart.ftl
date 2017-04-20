<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

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
				href="catagories?createOrUpdate=true">Create Category</a></li>
		</ul>
		<ul class="nav navbar-nav">
			<li class="active"><a
				href="menuitems?create=true">Create Menu Item</a></li>
		</ul>
		<ul class="nav navbar-nav">
			<li class="active"><a
				href="customizableitems?create=true">Customize Item</a></li>
		</ul>
	</div>
</nav>
</head>

<meta charset="UTF-8">
<title>Welcome to Team1 Restaurant</title>
<link rel="icon" href="/team1foodorderapp/favicon.ico" />
<style>
table {
	border-collapse: collapse;
}

table, th, td {
	border: 1px solid black;
}

.button {
	background-color: ForestGreen;
	border-radius: 5px;
	color: white;
	text-decoration: none;
}

.logo {
	height: 100px;
}
.mySlides {display:none;}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

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
				href="catagories?createOrUpdate=true">Create Category</a></li>
		</ul>
		<ul class="nav navbar-nav">
			<li class="active"><a
				href="menuitems?create=true">Create Menu Item</a></li>
		</ul>
		<ul class="nav navbar-nav">
			<li class="active"><a
				href="customizableitems?create=true">Customize Item</a></li>
		</ul>
	</div>
</nav>
</head>

<meta charset="UTF-8">
<title>Welcome to Team1 Restaurant</title>
<link rel="icon" href="/team1foodorderapp/favicon.ico" />
<style>
table {
	border-collapse: collapse;
}

table, th, td {
	border: 1px solid black;
}

.button {
	background-color: ForestGreen;
	border-radius: 5px;
	color: white;
	text-decoration: none;
}

.logo {
	height: 100px;
}
.mySlides {display:none;}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Shopping Cart</title>
</head>
<body>
<!--Display error message if error exists -->
<#if error??>
 <div style="display:inline-block; float:left; color: red;">${message}</div><br>
</#if>

<#if !cart.empty>
	<div style="background-image: url(/team1foodorderapp/images/teamone-2017-04-02_13-32-51.png); height: 150px;"></div>
	<span style="background-image: url(/team1foodorderapp/images/shopping-cart.jpg); height: 80px;">${cart.totalPrice}</span>
	<a href="/team1foodorderapp/orders?addmore=true" >Add More Item</a><a href="/team1foodorderapp/orders?checkout=true" >CheckOut</a>
	<#list cart.order.orderItems as orderitem>
			<div>${orderitem.itemDescription}</div><div>${orderitem.price}</div>
			<a href="/team1foodorderapp/orders?edit=${orderitem.itemNumber}">Edit</a>
			<#list 1..50 as index>
	     		 <option value="${index}" ${(orderitem.size == index)?then('checked', '')}>${index}</option>
	     	 </#list>
	     	 <a href="/team1foodorderapp/orders?remove=${orderitem.itemNumber}">Remove</a>
	</#list>
	<div style="float:right;">
		<span>Subtotal:</span><span>${cart.subTotalPrice}</span><br>
		<span>Tax:</span><span>${cart.tax}</span><br>
		<span>Total:</span><span>${cart.totalPrice}</span><br>
	</div>
<#else>
	<div>No item in the cart</div>
</#if>
<form method="get">
<!--Display back button for user to go back to index.html page -->
  <button type="submit" formaction="/team1foodorderapp/index.html" name="home">Home</button>
</form>
</body>
</html>