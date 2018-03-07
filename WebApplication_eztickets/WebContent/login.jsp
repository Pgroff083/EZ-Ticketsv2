<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LOGIN</title>
</head>
<body>
<h1>Login Page</h1>

<form action="login" method="POST">
      <table>
      	<div style="color: #FF0000;">${successfulMessage}</div>
      	<div style="color: #FF0000;">${errorMessage}</div>
        <tr>
          <td align="right">E-mail:</td>
          <td align="left"><input type="text"
              name="email"/></td>
        </tr>                  
          <td align="right">Password:</td>
          <td align="left"><input type="text"
              name="password"/></td>
        </tr>        
      </table>

      <p><input type="submit" value="Login"/> <a href="input.jsp">Registration</a></p>
    </form>

</body>
</html>