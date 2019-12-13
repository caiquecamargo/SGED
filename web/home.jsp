<!DOCTYPE html>
<%@page import="br.edu.ufabc.sged.util.HomePageSelector" %>
<jsp:useBean id="usuario" type="br.edu.ufabc.sged.model.Usuario" scope="session"/>
<jsp:useBean id="errorSTR" type="java.lang.String" scope="request"/>
<jsp:useBean id="pagina" type="java.lang.String" scope="request"/>
<jsp:useBean id="objectList" type="java.util.List<Object>" scope="request"/>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<html lang="pt-br">
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>ORION SGED</title>
<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/home.css">
<link rel="stylesheet" href="css/menu.css">
</head>
<body>
  <div class="estrutura">
    <header class="header">
      <a href="home"><img src="img/SGED-tiny.svg" alt="Logo SGED"></a>
      <ul class="navegacao">
        <li class="mobile"><a href="additem">Adicionar novos itens</a></li>
        <li class="mobile"><a href="viewitem">Visualizar itens</a></li>
        <li class="mobile"><a href="addgroup">Adicionar grupos</a></li>
        <li class="mobile"><a href="viewgroups">Editar grupos</a></li>
        <li class="mobile"><a href="validateuser">Habilitar Usuario</a></li>
        <li class="mobile"><a href="viewusers">Editar Usuario</a></li>
        <li id="blink"><p>Seja bem vindo ${usuario.getNome()}</p></li>
        <li><a href="myaccount">Minha conta</a></li>
        <li><a href="logoutservlet">Logout</a></li>
      </ul>
    </header>
    <nav class="nav">
      <ul>
        <li><a href="additem">Adicionar novos itens</a></li>
        <li><a href="viewitem">Visualizar itens</a></li>
        <li><a href="addgroup">Adicionar grupos</a></li>
        <li><a href="viewgroups">Editar grupos</a></li>
        <li><a href="validateuser">Habilitar Usuario</a></li>
        <li><a href="viewusers">Editar Usuario</a></li>
      </ul>
    </nav>
        <main class="content">
            ${pagina}
            <div class="log"><p>Log: ${errorSTR}</p></div>
        </main>
    <footer class="footer">
      <p>Criado por Caique de Camargo como Projeto de Graduacao em Computacao da Universidade Federal do ABC</p>
    </footer>
  </div>
  <!-- JavaScript -->
  <script src="./js/scroll.js"></script>
  <script src="./js/jquery.js"></script>
  <script src="./js/menu.js"></script>
  <script src="./js/dropzone.js"></script>
  <!-- JavaScript -->
</body>
</html>