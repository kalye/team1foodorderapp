<!DOCTYPE html>
<!--Author Kalkidan Teklu -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Menu Catagories</title>
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
		<#if hasCatagory??>
		<table>
		 <tr><td>Name<td><td>Year</td> <td></td></tr>
		 <form method="">
		 <#list catagories as cat>
		    <tr>
		    <td><input type="text" name="name" value="${cat.name}"/> <td>
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
			   		 <div class="w3-content w3-section" style="max-width:200px">
			    		<img class="mySlides" onclick="location.href=&quot;/team1foodorderapp/orders?catagoryid=${cat.id?string.computer}&quot;" src="/team1foodorderapp/files/${cat.imageUrl}" style="width:100%">
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
					<li><a href="/team1foodorderapp/menus?create=true">Create
							Menu Item</a></li>
					<li><a href="/team1foodorderapp/toppings?create=true">Create
							Toppings</a></li>
					<li><a href="/team1foodorderapp/sides?create=true">Create
							Sides</a></li>
					<li><a href="/team1foodorderapp/customizableitem?create=true">Create
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