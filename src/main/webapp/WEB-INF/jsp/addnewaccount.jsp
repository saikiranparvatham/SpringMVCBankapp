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
<form:form action="addnewaccount" method="post" modelAttribute="savingsaccount"  >
<table>
<tr><td><label>Enter Account Holder Name</label></td>
<td><form:input path="bankAccount.accountHolderName"/><form:errors path="bankAccount.accountHolderName" cssClass="errors"/></td></tr>

<tr><td><label>Enter Initial Balance</label></td>
<td><form:input path="bankAccount.accountBalance"/></td>
<td><form:errors path="bankAccount.accountBalance" cssClass="errors"/></td></tr>

<tr><td>
Select whether salaried or not</td>
<td><form:radiobutton path="salary" value="y"/>salaried
<form:radiobutton path="salary" value="n"/>not salaried</td></tr>
<tr><td><input type="submit" name="submit" value="submit"></td></tr>
</table>
</form:form>

</body>
</html>