<!DOCTYPE html>
<!--Author Kalkidan Teklu -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Menu Catagories</title>
<script>
function hideOrShowById(id, show){

}
</script>
</head>
<body>
<!--Display error message if error exists -->
<#if error??>
 <div style="display:inline-block; float:left; color: red;">${message}</div><br>
</#if>

<#if updateMenuItem??>
<form method="post" action="/team1foodorderapp/catagories?update=true" enctype="multipart/form-data">
 <div class="w3-content w3-section" style="max-width:200px">
			    		<img class="mySlides" onclick="location.href=&quot;/team1foodorderapp/cart?additemid=${menuitem.id?string.computer}&quot;" src="/team1foodorderapp/files/${menuitem.imageUrl}" style="width:100%">
			     	 </div>
			     	 Image:        <input type="file" name="file" id="file" /> <br/>
			     	 <select name="cats">
			     	 <#list catagories as cat>
			     		 <option value="${cat.id?string.computer}" ${(cat.id==menuitem.categoryId)?then('selected', '')}>Fiat</option>
			     	 </#list>
			     	 </select>
			     	 <br><br>
			     	 Name:         <input type="text" value=${menuitem.name} namde="name" /><br>
			     	 Description: <input type="text" value=${menuitem.description} namde="description" /><br>
			     	 <input type="radio" name="hasside" onclick="javascript:alert('bike');" value="yes" ${(menuitem.hasSide)?then('checked', '')}>Has Sides <br>
	  			     <input type="radio" onclick="javascript:alert('bike');" name="hasside" value="no" checked ${(menuitem.hasSide)?then('', 'checked')}> No Side<br>
			     	 <#if atleastoneside??>
				     	  <#list sides as side>
				     	  	<input type="checkbox" name="sidesformenu"  value="${side.id}" ${(side.selected)?then('checked', '')}>${side.name}<br>
				     	  </#list>
  					 </#if>
  					 <input type="radio" name="hastoppings" onclick="javascript:alert('bike');" value="yes" ${(menuitem.hasToppings)?then('checked', '')}>Has Toppings <br>
	  			     <input type="radio" onclick="javascript:alert('bike');" name="hastoppings" value="no" checked ${(menuitem.hasToppings)?then('', 'checked')}> No Topping<br>
			     	 <#if atleastonetopping??>
				     	  <#list toppings as topping>
				     	  	<input type="checkbox" name="sidesformenu"  value="${topping.id}" ${(topping.selected)?then('checked', '')}>${topping.name}<br>
				     	  </#list>
  					 </#if>
  					 <input type="radio" name="hascustomizableitem" onclick="javascript:alert('bike');" value="yes" ${(menuitem.customizable)?then('checked', '')}>Customizable <br>
	  			     <input type="radio" onclick="javascript:alert('bike');" name="hascustomizableitems" value="no" checked ${(menuitem.customizable)?then('', 'checked')}> Not Customizable<br>
			     	 <#if atleastonecustomizableitem??>
				     	  <#list customizableitems as customizableitem>
				     	  	<input type="checkbox" name="customizableitemformenu"  value="${customizableitem.id}" ${(customizableitem.selected)?then('checked', '')}>${customizableitem.name}<br>
				     	  </#list>
  					 </#if>
  					  Price:         <input type="text" value=${menuitem.price} namde="price" /><br>
			     	 </form>
<#elseif menuitemlist??>

<#elseif addmenuitem??>


<#else>
	<div style="display:inline-block;">${message}</div><br>
</#if>


<form method="get">
<!--Display back button for user to go back to index.html page -->
  <button type="submit" formaction="/team1foodorderapp/index.html" name="home">Home</button>
</form>
</body>
</html>