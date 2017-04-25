<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/team1foodorderapp/css/teamone.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>Checkout-Summary</title>
<link rel="icon" href="/team1foodorderapp/favicon.ico" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
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
#checkoutsteps {list-style: none; margin: 0; padding: 0;}
#checkoutsteps li {font-size: small; display: inline; color: #aaa; padding: 0 10px; border-right: 1px solid #999;}
#checkoutsteps li.currentstep {color: #000;}
#checkoutsteps li.last {border-right: none;}
table.form-tbl {width: 100%;}
table.form-tbl td, table.form-tbl th {padding: 4px 2px;}
table.form-tbl th {width: 160px;}
table.form-tbl input[type="text"], table.form-tbl textarea {width: 75%;}
</style>
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
		<div class="shopping-cart" onclick="location.href=&quot;/team1foodorderapp/orders?cartitems=true&quot;"><a href="/team1foodorderapp/orders?cartitems=true">${cart.totalPrice?string.currency}</a></div>
	</div>
</nav>
<!--Display error message if error exists -->
<#if error??>
 <div style="display:inline-block; float:left; color: red;">${message}</div><br>
</#if>

<ul id="checkoutsteps">
  <li><a href="/team1foodorderapp/orders?cartitems=true">Shopping Cart</a></li>
  <li><a href="/team1foodorderapp/orders?checkout=true">Shipping Address</a></li>
  <li><a href="/team1foodorderapp/orders?checkout=true">Billing &amp; Payment</a></li>
  <li class="currentstep">Confirmation</li>
  <li class="last">Receipt</li>
</ul>

<#if !cart.empty>
	<div style="height: 80px;">Total Price - ${cart.totalPrice?string.currency}</div>
	<hr>
	<h2>Purchase summary:</h2>
	<#list cart.order.orderItems as orderitem>
			<div style="width:200px; height: 90px; background-image: url(/team1foodorderapp/files/${orderitem.menuItem.imageUrl}); background-repeat: no-repeat;">
				     	 </div>
			<div>${orderitem.itemDescription}</div>
			<div>${orderitem.price?string.currency}</div>
		    <div>Numbers:${orderitem.size}</div>
	     	<hr>
	</#list>
	<div style="float:right;">
		<span>Subtotal:</span><span class="right">${cart.subTotalPrice?string.currency}</span><br>
		<span>Tax:</span><span class="right">${cart.tax?string.currency}</span><br>
		<span>Total:</span><span class="right">${cart.totalPrice?string.currency}</span><br>
	</div>
	<h2>Shipping address</h2>
	<table class="form-tbl" cellspacing="0">
    <tbody>
      <tr>
        <th><label>First Name </label></th>
          <td><div>${cart.order.shippingAddress.firstName}</div></td>
      </tr>
      <tr>
        <th><label>Last Name </label></th>
          <td><div>${cart.order.shippingAddress.lastName}</div></td>
      </tr>
      <tr>
        <th><label>Address </label></th>
        <td><div>${cart.order.shippingAddress.address}</div></td>
      </tr>
      <tr>
        <th><label>City </label></th>
        <td><div>"${cart.order.shippingAddress.city}"</div></td>
      </tr>
      <tr>
        <th><label>State/Region/Province</label></th>
        <td><div>${cart.order.shippingAddress.state}</div></td>
      </tr>
      <tr>
        <th><label>Zip/Postal Code</label></th>
        <td><div>"${cart.order.shippingAddress.zipcode}</div></td>
      </tr>
      <tr>
        <th><label>Country</label></th>
        <td><div>${cart.order.shippingAddress.country}</div></td>
      </tr>
      <tr>
        <th><label>Phone </label></th>
        <td><div>${cart.order.shippingAddress.phone}" </div></td>
      </tr>
      <tr>
        <th><label>Email</label></th>
        <td><div>${cart.order.shippingAddress.email}</div></td>
      </tr>
    </tbody>
  </table>
  <form method="get" action="/team1foodorderapp/orders?confirm=true">
  <input type="hidden" name="confirm" value="true"/>
  <input type="submit" name="submit" class="button" value="Confirm &raquo" />
  </form>
<#else>
	<div>No item in the cart</div>
</#if>
<form method="get">
<!--Display back button for user to go back to index.html page -->
  <button type="submit" formaction="/team1foodorderapp/index.html" name="home">Home</button>
</form>
</body>
</html>