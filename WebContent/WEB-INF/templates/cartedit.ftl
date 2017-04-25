<!DOCTYPE html>
<!--Author Kalkidan Teklu -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/team1foodorderapp/css/teamone.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css"> 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="icon" href="/team1foodorderapp/favicon.ico" />
<title>Edit Cart Item</title>
<script>
function updateExternal(id, show){
	var element = document.getElementById(id);
	if(element){
		if(show){
			element.parentElement.style.display = '';
		} else {
			element.parentElement.style.display = "none";
			element.checked = false;
		}
	}
}
</script>
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

		<div class="shopping-cart" onclick="location.href=&quot;/team1foodorderapp/orders?cartitems=true&quot;"><a href="/team1foodorderapp/orders?cartitems=true">${cart.totalPrice?string.currency}</a></div>
	</div>
</nav>
<!--Display error message if error exists -->
<#if error??>
 <div style="display:inline-block; float:left; color: red;">${message}</div><br>
</#if>
<h2>Edit Cart Item </h2>
<#if !cart.empty>
	<#list cart.order.orderItems as orderitem>
		<#if orderitem.itemNumber == itemNumber>
			<form method="post" action="/team1foodorderapp/orders?edit=${orderitem.itemNumber?string.computer}">
				<div style="width:200px; height: 90px; background-image: url(/team1foodorderapp/files/${orderitem.menuItem.imageUrl}); background-repeat: no-repeat;">
				     	 </div>
				<div>Name: 	      ${orderitem.menuItem.name}</div><br>
				<div>Description: ${orderitem.menuItem.description}</div><br>
				<#if orderitem.menuItem.hasSide>
			     	 <#if sides??>
			     	 	<div id="divSide">
			     	 	<h2>Choose Sides:</h2>
				     	  <#list sides as side>
				     	  	<input type="checkbox" name="sidesformenu" ${(side.selected)?then('checked', '')}  value="${side.id}">${side.name}<br>
				     	  </#list>
				     	</div>
					 </#if>
			    </#if>
			    
			    <#if orderitem.menuItem.hasToppings>
		     	 	<div id="divTopping">
		     	 	<h2>Choose Toppings</h2>
		     	 	<#if toppings??>
			     	  <#list toppings as topping>
			     	  	<input type="checkbox" name="toppingsformenu" ${(topping.selected)?then('checked', '')}  value="${topping.id}">${topping.name}<br>
			     	  </#list>
			     	 </#if>
			     	</div>
				 </#if>
				 <#if orderitem.menuItem.customizable>
			     	 <#if customizableItems??>
			     	 	<div id="divCustomizableItem">
			     	 	<h2>Customize item</h2>
				     	  <#list customizableItems as customizableitem>
				     	  	<label><input type="checkbox" name="customizableitemsformenu" onchange="javascript:updateExternal('customizableitemsformenuextra${customizableitem.id}', this.checked);" ${(customizableitem.selected)?then('checked', '')} value="${customizableitem.id}">${customizableitem.name}</label>
				     	  	<label><input type="checkbox" style="${(customizableitem.selected)?then('', 'display:none')}" id="customizableitemsformenuextra${customizableitem.id}" name="customizableitemsformenuextra" ${(extracustomizableitems?seq_contains(customizableitem.id))?string("checked", "")} value="${customizableitem.id}">Add Extra<br></label>
				     	  </#list>
				     	 </div>
  					 </#if>
				 </#if>
				  Price: <span>${orderitem.price?string.currency}</span><br>
				  Number of order:<br>
				   <select name="size">
	  					  <#list 1..50 as index>
				     		 <option value="${index}" ${(orderitem.size == index)?then('checked', '')}>${index}</option>
				     	 </#list>
				   </select>	 
				  <button type="submit" class="button">Update Item</button>
			</form>
		</#if>
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