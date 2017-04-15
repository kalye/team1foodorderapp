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
	<span style="background-image: url(/team1foodorderapp/images/shopping-cart.jpg); height: 80px;">${cart.TotalPrice}</span>
	<a href="/team1foodorderapp/orders?addmore=true" >Add More Item</a><a href="/team1foodorderapp/orders?checkout=true" >CheckOut</a>
	<#list cart.order.orderItems as orderitem>
			<div>${orderitem.itemDescription}</div>
			<#list 1..50 as index>
	     		 <option value="${index}" ${(orderitem.itemNumber == index)?then('checked', '')}>${index}</option>
	     	 </#list>
	</#list>
<#else>
	<div>No item in the cart</div>
</#if>
<form method="get">
<!--Display back button for user to go back to index.html page -->
  <button type="submit" formaction="/team1foodorderapp/index.html" name="home">Home</button>
</form>
</body>
</html>