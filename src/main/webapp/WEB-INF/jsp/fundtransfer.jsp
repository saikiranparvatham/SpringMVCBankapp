<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="fundtransferaction">
<table>
<tr><td>Enter the your account number :</td>
<td><input type="number" name="senderNumber"></td></tr>

<tr><td>Enter the receiver's account number :</td>
<td><input type="number" name="receiverNumber"></td></tr>

<tr><td>Enter the amount to send:</td>
<td><input type="number" name="amount"></td></tr>

<tr><td><input type="submit" name="submit" value="submit"></td></tr>
</table></form>
</body>
</html>