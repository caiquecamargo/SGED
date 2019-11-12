<!DOCTYPE html>
<jsp:useBean id="errorSTR" type="java.lang.String" scope="request"/>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<html lang="pt-br">
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>ORION SGED</title>
<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/style.css">
</head>
<body>
  <main class="container">
    <div class="logo">
      <img src="img/SGED.svg" alt="Logo SGED" class="img-principal">
      <img src="img/SGED-tiny.svg" alt="Logo SGED" class="img-tiny">
    </div>
    <div class="login">
      <img src="img/login_logo.svg" alt="Login logo">
      <form role="form" action="loginservlet" method="POST">
        <input type="text" placeholder="Email" name="txt_user">
        <input type="password" placeholder="Senha" name="txt_password">
        <label class="underform">
          <p class="erro">${errorSTR}</p>
          <a href="newuser">Solicitar Registro</a>
        </label>
        <button type="submit">LOGIN</button>
      </form>
    </div>
  </main>
</body>
</html>