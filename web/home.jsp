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
                            <form action="deleteitem" method="POST" class="form-trigger">
                                <input value="${item.getId()}" name="txt_id_item" class="notdisplay">
                                <input value="${item.getSrc()}" name="txt_src" class="notdisplay">
                                <input value="${item.getTipo()}" name="txt_tipo" class="notdisplay">
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
            <c:if test="${pagina == 'visualizar grupos'}">
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
                            <form class="form-trigger">
                                <input value="${grupo.getId()}" name="txt_id_grupo" class="notdisplay">
                                <button class="trigger-conteudo" type="submit" formaction="editgroup" formmethod="GET">Editar</button>
                                <button class="trigger-conteudo" type="submit" formaction="viewgroupmembers" formmethod="POST">Visualizar membros do grupo</button>
                                <button class="trigger-conteudo" type="submit" formaction="deletegroup" formmethod="POST">Excluir</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${pagina == 'editar grupo'}">
                <h1 class="titulo">Editar dados do grupo</h1>
                <form action="editgroup" class="editform">
                    <input value="${objectList[0].getId()}" name="txt_id_grupo" class="notdisplay">
                    <input type="text" value="${objectList[0].getNome()}" name="txt_nome">
                    <input type="text" value="${objectList[0].getDescricao()}" name="txt_descricao">
                    <input type="number" value="${objectList[0].getNivel()}" name="txt_nivel">
                    <button type="submit" formmethod="POST">Atualizar</button>
                </form>
            </c:if>
            <c:if test="${pagina == 'visualizar usuarios do grupo'}">
                <h1 class="titulo">Usuários do Grupo</h1>
                <div class="lista-item">
                    <div class="trigger-wrapper">
                        <div class="trigger-label">
                            <h3 class="column-name">Nome</h3>
                            <h3 class="column-name">Nivel de Acesso</h3>
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
                                <h3 class="trigger-tipo">${user.getNivel_de_acesso()}</h3>
                                <h3 class="trigger-src">${user.getEmail()}</h3>
                            </label>
                            <form action="edituser" method="GET" class="form-trigger">
                                <input value="${user.getId()}" name="txt_id_usuario" class="notdisplay">
                                <c:if test="${usuario.getNivel_de_acesso() < user.getNivel_de_acesso()}"><button class="trigger-conteudo" type="submit">Editar</button></c:if>
                                <c:if test="${usuario.getNivel_de_acesso() >= user.getNivel_de_acesso()}"><button class="trigger-conteudo" type="submit" disabled class="disabled">Editar</button></c:if>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${pagina == 'validar usuario'}">
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
                            <form action="validateuser" method="POST" class="form-trigger">
                                <input value="${user.getId()}" name="txt_id_usuario" class="notdisplay">
                                <input type="number" min="${user.getNivel_de_acesso()}" placeholder="Nivel de acesso" name="txt_nivel_de_acesso">
                                <button class="trigger-conteudo" type="submit">Habilitar</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${pagina == 'visualizar usuarios'}">
                <h1 class="titulo">Editar Usuario</h1>
                <div class="lista-item">
                    <div class="trigger-wrapper">
                        <div class="trigger-label">
                            <h3 class="column-name">Nome</h3>
                            <h3 class="column-name">Nivel de Acesso</h3>
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
                                <h3 class="trigger-tipo">${user.getNivel_de_acesso()}</h3>
                                <h3 class="trigger-src">${user.getEmail()}</h3>
                            </label>
                            <form action="edituser" method="GET" class="form-trigger">
                                <input value="${user.getId()}" name="txt_id_usuario" class="notdisplay">
                                <button class="trigger-conteudo" type="submit">Editar</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${pagina == 'editar usuario'}">
            <h1 class="titulo">Editar dados do usuario</h1>
            <form action="edituser" class="editform">
                <input value="${objectList[0].getId()}" name="txt_id_usuario" class="notdisplay">
                <input type="text" placeholder="${objectList[0].getNome()}" disabled>
                <input type="text" placeholder="${objectList[0].getEmail()}" disabled>
                <input type="number" placeholder="${objectList[0].getNivel_de_acesso()}" disabled>
                <button type="submit" method="POST" disabled>Atualizar</button>
            </form>
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