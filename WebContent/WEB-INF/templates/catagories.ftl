<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/team1foodorderapp/css/teamone.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

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
		<#if createOrUpdate??>
		
		<#else>
			<div class="shopping-cart">${cart.totalPrice?string.currency}</div>
		</#if>
	</div>
</nav>
</head>

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
<!--Display error message if error exists -->
<#if error??>
 <div style="display:inline-block; float:left; color: red;">${message}</div><br>
</#if>

<#if createOrUpdate??>
		 <h1>Create Catagory</h1><br>
		  <form method="post" action="/team1foodorderapp/catagories?add=true" enctype="multipart/form-data">
		  	Catagory Name:         <input type="text" value="" name="catagoryName" id="catagoryName" /><br>
		  	Catagory Image:        <input type="file" name="file" id="file" /> <br/>
		  	Image Url/Save as: <br><input type="text" value="" name="url" id="url" /><br>
		  	<button type="submit" class="button">Add Catagory</button>
		  </form>
		  <#if update??>
		  	<form method="post" action="/team1foodorderapp/catagories?update=true&&id=${category.id}&&nocache=${nocache?string.computer}" enctype="multipart/form-data">
			  	Catagory Name:         <input type="text" value="${category.name}" name="catagoryName" id="catagoryName" /><br>
			  	Catagory Image:        <input type="file" name="file" id="file" /> <br/>
			  	Image Url/Save as: <br><input type="text" value="${category.imageUrl}" name="url" id="url" /><br>
			  	<button type="submit" class="button">Update Catagory</button>
		   </form>
		  </#if>
		<#if hasCategory??>
		<table>
		 <tr><td>Name<td><td></td> <td></td></tr>
		 <form method="">
		 <#list catagories as cat>
		    <tr>
		    <td><span>${cat.name}"</span><td>
		    <td>
		    <a id="${cat.id?string.computer}" class="button" href="/team1foodorderapp/catagories?updateId=${cat.id?string.computer}" >Update</a>
			</td>
		    <td>
		    <a id="${cat.id?string.computer}" class="button" href="/team1foodorderapp/catagories?deleteId=${cat.id?string.computer}" >Delete</a>
			</td>
		    </tr>
		  </#list>
		  </table>
		  </form>
		</#if>

<#else>
		<#if hasCategory??>
			<table>
			 <tr><td><td><td></td></tr>
			 <form method="">
			 <#list catagories as cat>
			    <tr>
			    <td>
			   		 <div class="w3-content w3-section" onclick="javascript:location.href='/team1foodorderapp/orders?catagoryid=${cat.id?string.computer}';" style="width:200px; height: 90px; background-image: url(/team1foodorderapp/files/${cat.imageUrl}); background-repeat: no-repeat;">
			    		${cat.name}
			     	 </div>
			      
			    <td>
			    </tr>
			  </#list>
			  </table>
			  </form>
			  <#elseif message??>
			  	 ${message}
		</#if>
		<#if createsubmenu??>
			<h2>Create SubItem</h2>
		
				<ul style="list-style-type: none">
					<li><a href="/team1foodorderapp/menuitems?create=true">Create
							Menu Item</a></li>
					<li><a href="/team1foodorderapp/toppings?create=true">Create
							Toppings</a></li>
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