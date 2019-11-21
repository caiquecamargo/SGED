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
        <li><a href="logoutservlet">Logout</a></li>
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
                <h1 class="titulo">Adicionar novos itens</h1>
                <form action="fileuploadservlet" method="POST" enctype="multipart/form-data" class="draganddrop">
                    <input type="file" name="file">
                    <input type="text" placeholder="Restricoes" name="txt_restricoes">
                    <button type="submit">Enviar</button>
                </form>
            </c:if>
            <c:if test="${pagina == 'visualizar item'}">
                <h1 class="titulo">Seus arquivos</h1>
                <div class="lista-item">
                    <div class="trigger-wrapper">
                        <div class="trigger-label">
                            <h3 class="column-name">Nome</h3>
                            <h3 class="column-name">Tipo</h3>
                            <h3 class="column-name">SRC</h3>
                        </div>
                    </div>
                </div>
                <c:forEach var="item" items="${objectList}">
                    <div class="lista-item">
                        <input type="checkbox" class="trigger-input" id="${item.getId()}">
                        <div class="trigger-wrapper">
                            <label for="${item.getId()}" class="trigger-label">
                                <h3 class="trigger-nome">${item.getNome()}</h3>
                                <h3 class="trigger-tipo">${item.getTipo()}</h3>
                                <h3 class="trigger-src">${item.getSrc()}</h3>
                            </label>
                            <form action="excluiritem" method="POST" class="form-trigger">
                                <button class="trigger-conteudo" type="submit">Excluir</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${pagina == 'adicionar grupo'}">
                <h1 class="titulo">Adicionar novos grupos</h1>
                <form action="addgroup" method="POST" class="addgroup">
                  <input type="text" placeholder="Nome" name="txt_nome">
                  <input type="text" placeholder="Descricao" name="txt_descricao">
                  <input type="number" placeholder="Nivel" name="txt_nivel">
                  <button type="submit">Enviar</button>
                </form>
            </c:if>
            <c:if test="${pagina == 'editar grupo'}">
                <h1 class="titulo">Seus grupos</h1>
                <div class="lista-item">
                    <div class="trigger-wrapper">
                        <div class="trigger-label">
                            <h3 class="column-name">Nome</h3>
                            <h3 class="column-name">Nivel</h3>
                            <h3 class="column-name">Descricao</h3>
                        </div>
                    </div>
                </div>
                <c:forEach var="grupo" items="${objectList}">
                    <div class="lista-item">
                        <input type="checkbox" class="trigger-input" id="${grupo.getId()}">
                        <div class="trigger-wrapper">
                            <label for="${grupo.getId()}" class="trigger-label">
                                <h3 class="trigger-nome">${grupo.getNome()}</h3>
                                <h3 class="trigger-tipo">${grupo.getNivel()}</h3>
                                <h3 class="trigger-src">${grupo.getDescricao()}</h3>
                            </label>
                            <form action="excluiritem" method="POST" class="form-trigger">
                                <button class="trigger-conteudo" type="submit">Visualizar membros do grupo</button>
                                <button class="trigger-conteudo" type="submit">Excluir</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${pagina == 'adicionar usuario'}">
                <h1 class="titulo">Habilitar Usuario</h1>
                <div class="lista-item">
                    <div class="trigger-wrapper">
                        <div class="trigger-label">
                            <h3 class="column-name">Nome</h3>
                            <h3 class="column-name">Situacao</h3>
                            <h3 class="column-name">Email</h3>
                        </div>
                    </div>
                </div>
                <c:forEach var="user" items="${objectList}">
                    <div class="lista-item">
                        <input type="checkbox" class="trigger-input" id="${user.getId()}">
                        <div class="trigger-wrapper">
                            <label for="${user.getId()}" class="trigger-label">
                                <h3 class="trigger-nome">${user.getNome()}</h3>
                                <h3 class="trigger-tipo"><c:if test="${user.getSituacao() == 0}">Nao habilitado</c:if><c:if test="${user.getSituacao() == 1}">Habilitado</c:if></h3>
                                <h3 class="trigger-src">${user.getEmail()}</h3>
                            </label>
                            <form action="habilitarusuario" method="POST" class="form-trigger">
                                <input value="${user.getId()}" name="txt_id_usuario" class="notdisplay">
                                <input type="number" min="${user.getNivel_de_acesso()}" placeholder="Nivel de acesso" name="txt_nivel_de_acesso">
                                <button class="trigger-conteudo" type="submit">Habilitar</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${pagina == 'editar usuario'}">
                <h1 class="titulo">Editar Usuario</h1>
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