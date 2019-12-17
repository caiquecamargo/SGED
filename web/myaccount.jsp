<!DOCTYPE html>
<jsp:useBean id="usuario" type="br.edu.ufabc.sged.model.Usuario" scope="session"/>
<jsp:useBean id="errorSTR" type="java.lang.String" scope="request"/>
<html lang="pt-br">
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>ORION SGED</title>
<link href="https://fonts.googleapis.com/css?family=Cinzel+Decorative&display=swap" rel="stylesheet">
<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/minhaconta.css">
</head>
<body>
  <div class="estrutura">
      <header class="header">
          <a href="home"><img src="img/SGED-tiny.svg" alt="Logo SGED"></a>
          <ul class="navegacao">
            <li id="blink"><p>Seja bem vindo ${usuario.getNome()}</p></li>
            <li><a href="myaccount">Minha conta</a></li>
            <li><a href="logoutservlet">Logout</a></li>
          </ul>
        </header>
    <main class="usuario">
      <h1>Minha Conta</h1>
      <form action="myaccount" method="POST">
        <input type="text" placeholder="${usuario.getId()}" name="txt_nome" disabled>
        <input type="text" placeholder="${usuario.getNome()}" name="txt_nome" disabled>
        <input type="text" placeholder="${usuario.getEmail()}" name="txt_email" disabled>
        <input type="password" value="${usuario.getSenha()}" name="txt_senha">
        <label class="underform">
          <p class="erro">${errorSTR}</p>
        </label>
        <button>Atualizar</button>
      </form>
    </main>
    <footer class="footer">
      <p>Criado por Caique de Camargo de Camargo como Projeto de Graduação em Computação da Universidade Federal do ABC</p>
    </footer>
  </div>
</body>
</html>