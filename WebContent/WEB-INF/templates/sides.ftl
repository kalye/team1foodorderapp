<!DOCTYPE html>
<!--Author Kalkidan Teklu -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/team1foodorderapp/css/teamone.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css"> 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="icon" href="/team1foodorderapp/favicon.ico" />
<title>Menu Sides</title>
</head>
<body><center>
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
<!--Display error message if error exists -->
<#if error??>
 <div style="display:inline-block; float:left; color: red;">${message}</div><br>
</#if>

<#if createOrUpdate??>
		 <h1>Create Sides</h1><br>
		  <form method="post" action="/team1foodorderapp/sides?add=true" enctype="multipart/form-data">
		  	Topping Name:         <input type="text" value="" name="sideName" id="sideName" /><br>
		  	Topping Image:        <input type="file" name="file" id="file" /> <br/>
		  	Image Url/Save as: <br><input type="text" value="" name="url" id="url" /><br>
		  	Price:        <input type="text" name="price" value="" id="price" /> <br/>
		  	<button type="submit" class="button">Add Side</button>
		  </form>
		  <#if update??>
		  	<form method="post" action="/team1foodorderapp/sides?update=true&&id=${side.id}" enctype="multipart/form-data">
			  	Catagory Name:         <input type="text" value="${side.name}" name="sideName" id="sideName" /><br>
			  	Catagory Image:        <input type="file" name="file" id="file" /> <br/>
			  	Image Url/Save as: <br><input type="text" value="${side.imageUrl}" name="url" id="url" /><br>
			  	Price:        <input type="text" name="price" value="${side.price}" id="price" /> <br/>
			  	<button type="submit" class="button">Update Side</button>
		   </form>
		  </#if>
		<#if hasSides??>
		<table>
		 <tr><td>Name<td><td>Price</td> <td></td><td></td></tr>
		 <form method="">
		 <#list sides as side>
		    <tr>
		    <td><span>"${side.name}"</span><td>
		    <td>
		    <a id="${side.id?string.computer}" class="button" href="/team1foodorderapp/sides?updateId=${side.id?string.computer}" >Update</a>
			</td>
		    <td>
		    <a id="${side.id?string.computer}" class="button" href="/team1foodorderapp/sides?deleteId=${side.id?string.computer}" >Delete</a>
			</td>
		    </tr>
		  </#list>
		  </table>
		  </form>
		</#if>
		<#if createsubmenu??>
			<h2>Create Menu Related Items</h2>
		
				<ul style="list-style-type: none">
					<li><a href="/team1foodorderapp/menuitems?create=true">Create
							Menu Item</a></li>
					<li><a href="/team1foodorderapp/toppings?create=true">Create
								Toppings</a></li>
					<li><a href="/team1foodorderapp/customizableitems?create=true">Create
							Customizable Item</a></li>
				</ul>
		</#if>

<#else>
		<#if createsubmenu??>
			<h2>Create Menu Related Items</h2>
		
				<ul style="list-style-type: none">
					<li><a href="/team1foodorderapp/menuitems?create=true">Create
							Menu Item</a></li>
					<li><a href="/team1foodorderapp/toppings?create=true">Create
								Toppings</a></li>
					<li><a href="/team1foodorderapp/customizableitems?create=true">Create
							Customizable Item</a></li>
				</ul>
		</#if>

</#if>


<form method="get">
<!--Display back button for user to go back to index.html page -->
  <button type="submit" formaction="/team1foodorderapp/index.html" name="home">Home</button>
</form></center>
</body>
</html>