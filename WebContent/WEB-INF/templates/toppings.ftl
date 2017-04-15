<!DOCTYPE html>
<!--Author Kalkidan Teklu -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Menu Toppings</title>
</head>
<body>
<!--Display error message if error exists -->
<#if error??>
 <div style="display:inline-block; float:left; color: red;">${message}</div><br>
</#if>

<#if createOrUpdate??>
		 <h1>Create Toppings</h1><br>
		  <form method="post" action="/team1foodorderapp/toppings?add=true" enctype="multipart/form-data">
		  	Topping Name:         <input type="text" value="" name="toppingName" id="toppingName" /><br>
		  	Topping Image:        <input type="file" name="file" id="file" /> <br/>
		  	Image Url/Save as: <br><input type="text" value="" name="url" id="url" /><br>
		  	Price:         <input type="text" value="" name="price" /><br>
		  	<button type="submit" class="button">Add Topping</button>
		  </form>
		  <#if update??>
		  	<form method="post" action="/team1foodorderapp/toppings?update=true&&id=${topping.id}" enctype="multipart/form-data">
			  	Catagory Name:         <input type="text" value="${topping.name}" name="catagoryName" id="catagoryName" /><br>
			  	Catagory Image:        <input type="file" name="file" id="file" /> <br/>
			  	Image Url/Save as: <br><input type="text" value="${topping.imageUrl}" name="url" id="url" /><br>
			  	Price:         <input type="text" value="${topping.price}" name="price" /><br>
			  	<button type="submit" class="button">Update Catagory</button>
		   </form>
		  </#if>
		<#if hasToppings??>
		<table>
		 <tr><td>Name<td><td></td> <td></td><td></td</tr>
		 <form method="">
		 <#list topppings as top>
		    <tr>
		    <td><span>"${top.name}"</span><td>
		    <td><span>"${top.price}"</span><td>
		    <td>
		    <a id="${top.id?string.computer}" class="button" href="/team1foodorderapp/toppings?updateId=${top.id?string.computer}" >Update</a>
			</td>
		    <td>
		    <a id="${top.id?string.computer}" class="button" href="/team1foodorderapp/toppings?deleteId=${top.id?string.computer}" >Delete</a>
			</td>
		    </tr>
		  </#list>
		  </table>
		  </form>
		</#if>

<#else>
		<#if createsubmenu??>
			<h2>Create Menu Related Items</h2>
		
				<ul style="list-style-type: none">
					<li><a href="/team1foodorderapp/menuitems?create=true">Create
							Menu Item</a></li>
					<li><a href="/team1foodorderapp/sides?create=true">Create
							Sides</a></li>
					<li><a href="/team1foodorderapp/customizableitems?create=true">Create
							Customizable Item</a></li>
				</ul>
		</#if>

</#if>


<form method="get">
<!--Display back button for user to go back to index.html page -->
  <button type="submit" formaction="/team1foodorderapp/index.html" name="home">Home</button>
</form>
</body>
</html>