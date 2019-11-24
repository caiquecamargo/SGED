<!DOCTYPE html>
<jsp:useBean id="errorSTR" type="java.lang.String" scope="request"/>
<html lang="pt-br">
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>ORION SGED</title>
<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/newuser.css">
</head>
<body>
  <div class="estrutura">
    <header class="header">
      <a href="indexservlet"><img src="img/SGED-tiny.svg" alt="Logo SGED"></a>
      <ul class="navegacao">
        <li><a href="indexservlet">Voltar para a página inicial</a></li>
      </ul>
    </header>
    <main class="novousuario">
      <h1>Preencha o formulário para cadastro</h1>
      <form action="createuser" method="POST">
        <input type="text" placeholder="Nome Completo" name="txt_nome">
        <input type="text" placeholder="Email" name="txt_email">
        <input type="password" placeholder="Senha" name="txt_senha">
        <label class="underform">
          <p class="erro">${errorSTR}</p>
        </label>
        <button>Enviar Solicitação</button>
      </form>
    </main>
    <footer class="footer">
      <p>Criado por Caique de Camargo de Camargo como Projeto de Graduação em Computação da Universidade Federal do ABC</p>
    </footer>
  </div>
</body>
</html>