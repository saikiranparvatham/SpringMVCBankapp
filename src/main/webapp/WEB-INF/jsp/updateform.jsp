<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form:form action="update"  modelAttribute="account">
<table>
<tr><td>Account Number:</td>
<td><input type="text" name="accountNumber" value="${account.bankAccount.accountNumber}" readonly></td></tr>
<tr><td>Enter Account Holder Name</td>
<td><input type = "text" name="account_hn" value="${account.bankAccount.accountHolderName }"></td></tr>

<tr><td>
Select whether salaried or not</td>
<td><input type = "radio" name="y" value="y" ${requestScope.account.salary==true?"checked":""}>salaried
<input type = "radio" name="y" value="n" ${requestScope.account.salary==true?"":"checked"}>not salaried</td></tr>
<tr><td><input type="submit" name="update" value="update"></td></tr>
</table>
</form:form>
</body>
</html>