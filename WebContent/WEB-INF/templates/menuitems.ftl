<!DOCTYPE html>
<!--Author Kalkidan Teklu -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Menu Items</title>
<script>
function hideOrShowById(id, show){
	var element = document.getElementById(id);
	if(element){
		if(show){
			element.style.display = "";
		} else {
			element.style.display = "none";
		}
	}
}
</script>
</head>
<body>
<!--Display error message if error exists -->
<#if error??>
 <div style="display:inline-block; float:left; color: red;">${message}</div><br>
</#if>

<#if updateMenuItem??>
			<form method="post" action="/team1foodorderapp/menuitem?update=true" enctype="multipart/form-data">
 					<div class="w3-content w3-section" style="max-width:200px">
			    		<img class="mySlides" src="/team1foodorderapp/files/${menuitem.imageUrl}" style="width:100%">
			     	 </div>
			     	 Image:        <input type="file" name="file" id="file" /> <br/>
			     	 <select name="cats">
			     	 <#list catagories as cat>
			     		 <option value="${cat.id?string.computer}" ${(cat.id==menuitem.categoryId)?then('selected', '')}>Fiat</option>
			     	 </#list>
			     	 </select>
			     	 <br><br>
			     	 Name:         <input type="text" value=${menuitem.name} name="name" /><br>
			     	 Description: <input type="text" value=${menuitem.description} name="description" /><br>
			     	 <input type="radio" name="hasside" onclick="javascript:hideOrShowById('divSide', true);" value="yes" ${(menuitem.hasSide)?then('checked', '')}>Has Sides <br>
	  			     <input type="radio" onclick="javascript:hideOrShowById('divSide', false);" name="hasside" value="no" checked ${(menuitem.hasSide)?then('', 'checked')}> No Side<br>
			     	 <#if atleastoneside??>
			     	 	<div id="divSide">
				     	  <#list sides as side>
				     	  	<input type="checkbox" name="sidesformenu"  value="${side.id}" ${(side.selected)?then('checked', '')}>${side.name}<br>
				     	  </#list>
				     	</div>
  					 </#if>
  					 <input type="radio" name="hastoppings" onclick="javascript:hideOrShowById('divTopping', true);" value="yes" ${(menuitem.hasToppings)?then('checked', '')}>Has Toppings <br>
	  			     <input type="radio" onclick="javascript:hideOrShowById('divTopping', false);" name="hastoppings" value="no" checked ${(menuitem.hasToppings)?then('', 'checked')}> No Topping<br>
			     	 <#if atleastonetopping??>
			     	 	<div id="divTopping">
				     	  <#list toppings as topping>
				     	  	<input type="checkbox" name="sidesformenu"  value="${topping.id}" ${(topping.selected)?then('checked', '')}>${topping.name}<br>
				     	  </#list>
				     	</div>
  					 </#if>
  					 <input type="radio" name="hascustomizableitem" onclick="javascript:hideOrShowById('divCustomizableItem', true);" value="yes" ${(menuitem.customizable)?then('checked', '')}>Customizable <br>
	  			     <input type="radio" onclick="javascript:hideOrShowById('divCustomizableItem', false);" name="hascustomizableitems" value="no" checked ${(menuitem.customizable)?then('', 'checked')}> Not Customizable<br>
			     	 <#if atleastonecustomizableitem??>
			     	 	<div id="divCustomizableItem">
				     	  <#list customizableitems as customizableitem>
				     	  	<input type="checkbox" name="customizableitemformenu"  value="${customizableitem.id}" ${(customizableitem.selected)?then('checked', '')}>${customizableitem.name}<br>
				     	  </#list>
				     	 </div>
  					 </#if>
  					  Price:         <input type="text" value="${menuitem.price}" name="price" /><br>
  					  <button type="submit" class="button">Update Menu Item</button>
			     	 </form>
			     	 <#if hasOneMoreMenuItem??>
				     	 <table>
						 <tr><td>Name<td><td></td> <td></td></tr>
						 <form method="">
				     	 	<#list menuitems as menuitem>
				     	 		   <tr>
								    <td><span>${menuitem.name}"</span><td>
								    <td>
								    <a id="${menuitem.id?string.computer}" class="button" href="/team1foodorderapp/menuitems?updateId=${cat.id?string.computer}" >Update</a>
									</td>
								    <td>
								    <a id="${menuitem.id?string.computer}" class="button" href="/team1foodorderapp/menuitems?deleteId=${cat.id?string.computer}" >Delete</a>
									</td>
								    </tr>
				     	 	</#list>
			     	 	 </table>
		 				 </form>
			     	 </#if>
<#elseif addmenuitemtocart??>
	<#list menuitems as menuitem>
		<form method="post" id=${menuitem.id} action="/team1foodorderapp/orders?addtocart=true&&menuid=${menuitem.id}" enctype="multipart/form-data">
	 					<div class="w3-content w3-section" style="max-width:200px">
				    		<img class="mySlides" src="/team1foodorderapp/files/${menuitem.imageUrl}" style="width:100%">
				     	 </div>
				     	 <#list catagories as cat>
				     	 	<#if cat.id==menuitem.categoryId >
				     	 		<div>${cat.name}</div>
				     	 	</#if>
				     	 </#list>
				     	 <br><br>
				     	 <div>${menuitem.name}</div><br>
				     	 <div>${menuitem.description}</div><br>
				     	 <#if menuitem.hasSide??>
					     	 <#if menuitem.sides??>
					     	 	<div id="divSide">
					     	 	<h2>Choose Sides:</h2>
						     	  <#list menuitem.sides as side>
						     	  	<input type="checkbox" name="sidesformenu"  value="${side.id}">${side.name}<br>
						     	  </#list>
						     	</div>
		  					 </#if>
	  					 </#if>
				     	 <#if menuitem.toppings??>
				     	 	<div id="divTopping">
				     	 	<h2>Choose Toppings</h2>
					     	  <#list menuitem.toppings as topping>
					     	  	<input type="checkbox" name="sidesformenu"  value="${topping.id}">${topping.name}<br>
					     	  </#list>
					     	</div>
	  					 </#if>
	  					 <#if menuitem.customizable??>
		  					 <input type="radio" name="hascustomizableitem" onclick="javascript:hideOrShowById('divCustomizableItem', true);" value="yes" ${(menuitem.customizable)?then('checked', '')}>Customizable <br>
			  			     <input type="radio" onclick="javascript:hideOrShowById('divCustomizableItem', false);" name="hascustomizableitems" value="no" checked ${(menuitem.customizable)?then('', 'checked')}> Not Customizable<br>
					     	 <#if menuitem.customizableItems??>
					     	 	<div id="divCustomizableItem">
					     	 	<h2>Customize item</h2>
						     	  <#list menuitem.customizableitems as customizableitem>
						     	  	<input type="checkbox" name="customizableitemformenu"  value="${customizableitem.id}">${customizableitem.name}<br>
						     	  </#list>
						     	 </div>
		  					 </#if>
	  					 </#if>
	  					  Price: <span>${menuitem.price}</span><br>
	  					  <button type="submit" class="button">Add To Cart</button>
				     	 </form>
	    </#list>
<#elseif addmenuitem??>
	<form method="post" action="/team1foodorderapp/menuitem?add=true" enctype="multipart/form-data">
					 Name:         <input type="text" value="" name="name" /><br>
			     	 Image:        <input type="file" name="file" id="file" /> <br/>
			     	 Image Url/Save as: <br><input type="text" value="" name="url" id="url" /><br>
			     	 <select name="cats">
			     	 <#list catagories as cat>
			     		 <option value="${cat.id}">${cat.name}</option>
			     	 </#list>
			     	 </select>
			     	 <br><br>
			     	 Description: <input type="text" value="" name="description" /><br>
			     	 <input type="radio" name="hasside" onclick="javascript:hideOrShowById('divSide', true);" value="yes" >Has Sides <br>
	  			     <input type="radio" onclick="javascript:hideOrShowById('divSide', false);" name="hasside" value="no" checked> No Side<br>
			     	 <#if atleastoneside??>
			     	 	<div id="divSide" style="display:none;">
				     	  <#list sides as side>
				     	  	<input type="checkbox" name="sidesformenu"  value="${side.id}" ${(side.selected)?then('checked', '')}>${side.name}<br>
				     	  </#list>
				     	</div>
  					 </#if>
  					 <input type="radio" name="hastoppings" onclick="javascript:hideOrShowById('divTopping', true);" value="yes" >Has Toppings <br>
	  			     <input type="radio" onclick="javascript:hideOrShowById('divTopping', false);" name="hastoppings" value="no" checked > No Topping<br>
			     	 <#if atleastonetopping??>
			     	 	<div id="divTopping" style="display:none;">
				     	  <#list toppings as topping>
				     	  	<input type="checkbox" name="sidesformenu"  value="${topping.id}" >${topping.name}<br>
				     	  </#list>
				     	</div>
  					 </#if>
  					 <input type="radio" name="hascustomizableitem" onclick="javascript:hideOrShowById('divCustomizableItem', true);" value="yes" >Customizable <br>
	  			     <input type="radio" onclick="javascript:hideOrShowById('divCustomizableItem', false);" name="hascustomizableitems" value="no" checked> Not Customizable<br>
			     	 <#if atleastonecustomizableitem??>
			     	 	<div id="divCustomizableItem" style="display:none;">
				     	  <#list customizableitems as customizableitem>
				     	  	<input type="checkbox" name="customizableitemformenu"  value="" >${customizableitem.name}<br>
				     	  </#list>
				     	 </div>
  					 </#if>
  					  Price:         <input type="text" value="" name="price" /><br>
  					  <button type="submit" class="button">Add Menu Item</button>
			     	 </form>
			     	 <#if hasOneMoreMenuItem??>
				     	 <table>
						 <tr><td>Name<td><td></td> <td></td></tr>
						 <form method="">
				     	 	<#list menuitems as menuitem>
				     	 		   <tr>
								    <td><span>${menuitem.name}"</span><td>
								    <td>
								    <a id="${menuitem.id?string.computer}" class="button" href="/team1foodorderapp/menuitems?updateId=${cat.id?string.computer}" >Update</a>
									</td>
								    <td>
								    <a id="${menuitem.id?string.computer}" class="button" href="/team1foodorderapp/menuitems?deleteId=${cat.id?string.computer}" >Delete</a>
									</td>
								    </tr>
				     	 	</#list>
			     	 	 </table>
		 				 </form>
			     	 </#if>
			     	 <#if createsubmenu??>
						<h2>Create Menu Related Items</h2>
							<ul style="list-style-type: none">
								<li><a href="/team1foodorderapp/toppings?create=true">Create
										Toppings</a></li>
								<li><a href="/team1foodorderapp/sides?create=true">Create
										Sides</a></li>
								<li><a href="/team1foodorderapp/customizableitems?create=true">Create
										Customizable Item</a></li>
							</ul>
					</#if>

<#else>
	<div style="display:inline-block;">${message}</div><br>
</#if>


<form method="get">
<!--Display back button for user to go back to index.html page -->
  <button type="submit" formaction="/team1foodorderapp/index.html" name="home">Home</button>
</form>
</body>
</html>