<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

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
	</div>
</nav>
<!--Display error message if error exists -->
<#if error??>
 <div style="display:inline-block; float:left; color: red;">${message}</div><br>
</#if>

<#if createOrUpdate??>
		 <h1>Create Customizable Item</h1><br>
		  <form method="post" action="/team1foodorderapp/customizableitems?add=true" enctype="multipart/form-data">
		  	Topping Name:         <input type="text" value="" name="customizableitemName" id="customizableitemName" /><br>
		  	Topping Image:        <input type="file" name="file" id="file" /> <br/>
		  	Image Url/Save as: <br><input type="text" value="" name="url" id="url" /><br>
		  	Price:         <input type="text" value="" name="price" /><br>
		  	<button type="submit" class="button">Add Customizableitem</button>
		  </form>
		  <#if update??>
		  	<form method="post" action="/team1foodorderapp/customizableitems?update=true&&id=${customizableitem.id}" enctype="multipart/form-data">
			  	Catagory Name:         <input type="text" value="${customizableitem.name}" name="customizableitemName" id="customizableitemName" /><br>
			  	Catagory Image:        <input type="file" name="file" id="file" /> <br/>
			  	Image Url/Save as: <br><input type="text" value="${customizableitem.imageUrl}" name="url" id="url" /><br>
			  	Price:         <input type="text" value="${customizableitem.price}" name="price" /><br>
			  	<button type="submit" class="button">Update Customizable Item</button>
		   </form>
		  </#if>
		<#if hasCustomizableitems??>
		<table>
		 <tr><td>Name<td><td>Price</td> <td></td><td></td</tr>
		 <form method="">
		 <#list customizableitems as cus>
		    <tr>
		    <td><span>"${cus.name}"</span><td>
		    <td><span>"${cus.price}"</span><td>
		    <td>
		    <a id="${cus.id?string.computer}" class="button" href="/team1foodorderapp/customizableitems?updateId=${cus.id?string.computer}" >Update</a>
			</td>
		    <td>
		    <a id="${cus.id?string.computer}" class="button" href="/team1foodorderapp/customizableitems?deleteId=${cus.id?string.computer}">Delete</a>
			</td>
		    </tr>
		  </#list>
		  </table>
		  </form>
		</#if>
		<#if createsubmenu??>
			<h2>Create Menu Related Items</h2>
		
				<ul style="list-style-type: none">
					<li><a href="/team1foodorderapp/menuitems?create=true" class="reglink">Create
							Menu Item</a></li>
					<li><a href="/team1foodorderapp/sides?create=true" class="reglink">Create
							Sides</a></li>
					<li><a href="/team1foodorderapp/toppings?create=true" class="reglink">Create
							Toppingss</a></li>
				</ul>
		</#if>

<#else>
		<#if createsubmenu??>
			<h2>Create Menu Related Items</h2>
		
				<ul style="list-style-type: none">
					<li><a href="/team1foodorderapp/menuitems?create=true" class="reglink">Create
							Menu Item</a></li>
					<li><a href="/team1foodorderapp/sides?create=true" class="reglink">Create
							Sides</a></li>
					<li><a href="/team1foodorderapp/toppings?create=true" class="reglink">Create
							Toppingss</a></li>
				</ul>
		</#if>

</#if>


<form method="get">
<!--Display back button for user to go back to index.html page -->
  <button type="submit" formaction="/team1foodorderapp/index.html" name="home">Home</button>
</form>
</body>
</html>