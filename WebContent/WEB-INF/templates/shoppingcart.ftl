<!DOCTYPE html>
<!--Author Kalkidan Teklu -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Shopping Cart</title>
</head>
<body>
<!--Display error message if error exists -->
<#if error??>
 <div style="display:inline-block; float:left; color: red;">${message}</div><br>
</#if>

<#if !cart.empty>
	<#list cart.order.orderItems as orderitem>
	
	</#list>
	<form method="get">
	<!--Display back button for user to go back to index.html page -->
	  <button type="submit" formaction="/team1foodorderapp/orders?checkout=true" name="checkout">Proceed to checkout</button>
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