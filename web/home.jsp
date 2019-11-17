<!DOCTYPE html>
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
<link rel="stylesheet" href="css/dropzone.css">
</head>
<body>
  <div class="estrutura">
    <header class="header">
      <a href="home"><img src="img/SGED-tiny.svg" alt="Logo SGED"></a>
      <ul class="navegacao">
        <li class="mobile"><a href="additem">Adicionar novos itens</a></li>
        <li class="mobile"><a href="viewitem">Visualizar itens</a></li>
        <li class="mobile"><a href="addgroup">Adicionar grupos</a></li>
        <li class="mobile"><a href="editgroup">Editar grupos</a></li>
        <li class="mobile"><a href="adduser">Adicionar Pessoas</a></li>
        <li class="mobile"><a href="edituser">Editar Pessoas</a></li>
        <li id="blink"><p>Seja bem vindo ${usuario.getNome()}</p></li>
        <li><a href="minhaconta">Minha conta</a></li>
        <li><a href="logout">Logout</a></li>
      </ul>
    </header>
    <nav class="nav">
      <ul>
        <li><a href="additem">Adicionar novos itens</a></li>
        <li><a href="viewitem">Visualizar itens</a></li>
        <li><a href="addgroup">Adicionar grupos</a></li>
        <li><a href="editgroup">Editar grupos</a></li>
        <li><a href="adduser">Adicionar Pessoas</a></li>
        <li><a href="edituser">Editar Pessoas</a></li>
      </ul>
    </nav>
        <main class="content">
            <c:if test="${pagina == 'adicionar item'}">
                <form action="fileuploadservlet" method="POST" enctype="multipart/form-data" class="draganddrop">
                    <input type="file" name="file">
                    <input type="text" placeholder="Restricoes" name="txt_restricoes">
                    <button type="submit">Enviar</button>
                </form>
                <!--<form action="fileuploadservlet" class="dropzone"></form>-->
                <!-- <form action="additem" method="POST">
                  <input type="text" placeholder="Tipo" name="txt_tipo">
                  <input type="text" placeholder="Nome" name="txt_nome">
                  <input type="text" placeholder="Restri��es" name="txt_restricoes">
                  <button type="submit">Enviar</button>
                </form> -->
            </c:if>
            <c:if test="${pagina == 'visualizar item'}">
                <h1>Visuzalizar item</h1>
            </c:if>
            <c:if test="${pagina == 'adicionar grupo'}">
                <form action="addgroup" method="POST">
                  <input type="text" placeholder="Nome" name="txt_nome">
                  <input type="text" placeholder="Descricao" name="txt_descricao">
                  <input type="number" placeholder="Nivel" name="txt_nivel">
                  <button type="submit">Enviar</button>
                </form>
            </c:if>
            <c:if test="${pagina == 'editar grupo'}">
                <c:forEach var="grupo" items="${objectList}">
                    <p>${grupo.getNome()}</p>
                </c:forEach>
            </c:if>
            <c:if test="${pagina == 'adicionar usuario'}">
                <c:forEach var="user" items="${objectList}">
                    <p>${user.getNome()}</p>
                </c:forEach>
            </c:if>
            <c:if test="${pagina == 'editar usuario'}">
                <h1>Editar Usuario</h1>
            </c:if>
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