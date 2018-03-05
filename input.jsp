<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
</head>
<body>
<h1>Registration Page</h1>

<form action="registration" method="POST">
      <table>
      	<div style="color: #FF0000;">${successfulMessage}</div>
        <tr>
          <td align="right">First name:</td>
          <td align="left"><input type="text"
              name="firstName"/></td>
        </tr>
        <tr>
          <td align="right">Last name:</td>
          <td align="left"><input type="text"
              name="lastName"/></td>
        </tr>
        <tr>
          <td align="right">Login ID:</td>
          <td align="left"><input type="text"
              name="loginID"/></td>
        </tr>
        <tr>
        <div style="color: #FF0000;">${errorMessage}</div>          
          <td align="right">Password:</td>
          <td align="left"><input type="text"
              name="password"/></td>
        </tr>
        <tr>
          <td align="right">E-mail:</td>
          <td align="left"><input type="text"
              name="email"/></td>
        </tr>
      </table>

      <p><input type="submit" value="Register"/> <a href="login.jsp">Login</a></p>
    </form>

</body>
</html>