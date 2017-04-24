<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Team One Food Ordering Shipping</title>
    <style>
        #checkoutsteps {list-style: none; margin: 0; padding: 0;}
        #checkoutsteps li {font-size: small; display: inline; color: #aaa; padding: 0 10px; border-right: 1px solid #999;}
        #checkoutsteps li.currentstep {color: #000;}
        #checkoutsteps li.last {border-right: none;}
        table.form-tbl {width: 100%;}
        table.form-tbl td, table.form-tbl th {padding: 4px 2px;}
        table.form-tbl th {width: 160px;}
        table.form-tbl input[type="text"], table.form-tbl textarea {width: 75%;}
    </style>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>    
</head>
    
<body>
<#if error??>
 <div style="display:inline-block; float:left; color: red;">${message}</div><br>
</#if>
<h1>Checkout-Team One Food ordering Shipping</h1> 
<p><em>*</em> = required field </p>

<ul id="checkoutsteps">
  <li><a href="/team1foodorderapp/orders?cartitems=true">Shopping Cart</a></li>
  <li><a href="/team1foodorderapp/orders?checkout=true">Shipping Address</a></li>
  <li class="currentstep">Billing &amp; Payment</li>
  <li>Confirmation</li>
  <li class="last">Receipt</li>
</ul>
  <h2>Billing Information</h2>
<form action="/team1foodorderapp/orders?billing=true" method="post">
  <table class="form-tbl" cellspacing="0">
    <tbody>
      <tr>
        <th>&nbsp;</th>
        <td>
          <input type="checkbox" name="populateinfo" onchange="javascript:showHidePaymentInfo()" id="populateinfo" />
          <label for="populateinfo">My billing information is the same as my shipping information</label>
        </td>
      </tr>
      <tr>
        <th><label>First Name <em>*</em></label></th>
          <td><input type ="text" name="firstName" value="${cart.order.shippingAddress.firstName}" /></td>
      </tr>
      <tr>
        <th><label>Last Name <em>*</em></label></th>
          <td><input type ="text" name="lastName" value="${cart.order.shippingAddress.lastName}" /></td>
      </tr>
      <tr>
        <th><label>Address <em>*</em></label></th>
        <td><input type ="text" name="address" value="${cart.order.shippingAddress.address}" /></td>
      </tr>
      <tr>
        <th><label>City <em>*</em></label></th>
        <td><input type ="text" name="city" value="${cart.order.shippingAddress.city}" /></td>
      </tr>
      <tr>
        <th><label>State/Region/Province<em>*</em></label></th>
        <td><input type ="text" name="state" value="${cart.order.shippingAddress.state}" /></td>
      </tr>
      <tr>
        <th><label>Zip/Postal Code<em>*</em></label></th>
        <td><input type ="text" name="zipcode" value="${cart.order.shippingAddress.zipcode}" /></td>
      </tr>
      <tr>
        <th><label>Country <em>*</em></label></th>
        <td><input type ="text" name="country" value="${cart.order.shippingAddress.country}" /></td>
      </tr>
      <tr>
        <th><label>Phone <em>*</em></label></th>
        <td><input type ="text" name="phone" value="${cart.order.shippingAddress.phone}" /></td>
      </tr>
      <tr>
        <th><label>Email <em>*</em></label></th>
        <td><input type ="text" name="email" value="${cart.order.shippingAddress.email}" /></td>
      </tr>
      <tr>
        <th><label>Confirm Email <em>*</em></th>
        <td><input id="email2" type="text" name="confirmemail"  value="${cart.order.shippingAddress.confirmemail}" /></td>
      </tr>
    </tbody>
  </table>
  <div id="paymentInfo">
  <h2>Payment Information</h2>
  <table class="form-tbl" cellspacing="0">
    <tbody>
      <tr>
        <th><label for="{#billing.form.fields.billCreditCardType.id}">Credit Card Type <em>*</em></label></th>
        <td><input type ="text" value /></td>
      </tr>
      <tr>
        <th><label for="{#billing.form.fields.billCreditCardNumber.id}">Card Number <em>*</em></label></th>
        <td><input type ="text" value /></td>
      </tr>
      <tr>
        <th><label for="{#billing.form.fields.billCreditCardExpirationMonth.id}">Expiration <em>*</em></label></th>
        <td><input type ="text" value /></td>
      </tr>
      <tr>
        <th><label for="{#billing.form.fields.billCreditCardCode.id}">Security Code <em>*</em></label></th>
        <td><input type ="text" value /><div class="smallText">3 or 4 digit code from the back of your credit card</div></td>
      </tr>
      <tr>
        <th>&nbsp;</th>
        <td><input type="submit" name="submit" value="Continue &raquo" /></td>
      </tr>
    </tbody>
  </table>
</div>
    </form>
</body>
</html>