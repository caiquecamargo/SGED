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
</head>
<body>
  <h1>Página error</h1>
  <h2>Erro: ${errorSTR}</h2>
</body>
</html>