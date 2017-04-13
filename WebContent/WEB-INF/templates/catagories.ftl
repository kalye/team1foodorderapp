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
  <form method="post" action="/team1foodorderapp/catagories?add" enctype="multipart/form-data">
  	Name: <input type="text" value="" name="name" /><br>
  	File: <input type="file" name="file" id="file" /> <br/>
  	Image Url/Save as: <br><input type="text" value="" name="url" /><br>
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
<#if hasCatagory??>
<table>
 <tr><td>Name<td><td>Year</td> <td></td></tr>
 <form method="">
 <#list catagories as cat>
    <tr>
    <td>
   		 <div class="w3-content w3-section" style="max-width:200px">
    		<img class="mySlides" onclick="location.href=&quot;/team1foodorderapp/menuitems?catagoryid=${cat.id?string.computer}&quot;" src="${cat.imageUrl}" style="width:100%">
    		${cat.name}
     	 </div>
      
    <td>
    </tr>
  </#list>
  </table>
  </form>
</#if>

</#if>
<form method="get">
<!--Display back button for user to go back to index.html page -->
  <button type="submit" formaction="/team1foodorderapp/index.html" name="home">Home</button>
</form>
</body>
</html>