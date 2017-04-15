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