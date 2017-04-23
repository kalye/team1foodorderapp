<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/team1foodorderapp/css/teamone.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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
.mySlides {
	display:none;
}
li {
    display: inline;
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Shopping Cart</title>
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
		<div class="shopping-cart">${cart.totalPrice?string.currency}</div>
	</div>
</nav>
<!--Display error message if error exists -->
<#if error??>
 <div style="display:inline-block; float:left; color: red;">${message}</div><br>
</#if>

<#if !cart.empty>
	<div style="height: 80px;">Total Price - ${cart.totalPrice?string.currency}</div>
	<ul style="list-style: none;">
		<li><a href="/team1foodorderapp/orders?addmore=true" style="color:#337ab7;">Add More Item</a></li>&nbsp;&nbsp;
		<li><a href="/team1foodorderapp/orders?checkout=true" style="color:#337ab7;">CheckOut</a></li>
	</ul>
	<hr>
	<#list cart.order.orderItems as orderitem>
		<form method="post" id="sizechange${orderitem.itemNumber?string.computer}" action="/team1foodorderapp/orders?changesizeforitem=${orderitem.itemNumber?string.computer}">
			<div>${orderitem.itemDescription}</div><div>${orderitem.price?string.currency}</div>
			 <select name="size" onchange="javascirpt: document.getElementById('sizechange${orderitem.itemNumber?string.computer}').submit();">
				<#list 1..50 as index>
		     		 <option value="${index}" ${(orderitem.size == index)?then("selected='selected'", '')}>${index}</option>
		     	 </#list>
		     </select>
		 </form>
	     	<ul style="list-style: none;">
		     	<li><a href="/team1foodorderapp/orders?edit=${orderitem.itemNumber?string.computer}" style="color:#337ab7;">Edit</a>&nbsp;&nbsp;&nbsp;&nbsp;
		     	</li><li> <a href="/team1foodorderapp/orders?remove=${orderitem.itemNumber?string.computer}" style="color:#337ab7;">Remove</a></li>
	     	</ul>
	     	<hr>
	</#list>
	<div style="float:right;">
		<span>Subtotal:</span><span class="right">${cart.subTotalPrice?string.currency}</span><br>
		<span>Tax:</span><span class="right">${cart.tax?string.currency}</span><br>
		<span>Total:</span><span class="right">${cart.totalPrice?string.currency}</span><br>
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