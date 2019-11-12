<!DOCTYPE html>
<jsp:useBean id="usuario" type="br.edu.ufabc.sged.model.Usuario" scope="session"/>
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
  <div class="estrutura">
    <header class="header">
      <a href="home"><img src="img/SGED-tiny.svg" alt="Logo SGED"></a>
      <ul class="navegacao">
        <li><p>Seja bem vindo ${usuario.getNome()}</p></li>
        <li><a href="minhaconta">Minha conta</a></li>
        <li><a href="logout">Logout</a></li>
      </ul>
    </header>
    <nav class="nav">
      <ul>
        <li><a href="/">Adicionar novos itens</a></li>
        <li><a href="/">Visualizar itens</a></li>
        <li><a href="/">Adicionar grupos</a></li>
        <li><a href="/">Editar grupos</a></li>
        <li><a href="/">Adicionar Pessoas</a></li>
        <li><a href="/">Editar Pessoas</a></li>
      </ul>
    </nav>
    <main class="content">Conteudo</main>
    <footer class="footer">
      <p>Criado por Caique de Camargo de Camargo como Projeto de Graduação em Computação da Universidade Federal do ABC</p>
    </footer>
  </div>
</body>
</html>