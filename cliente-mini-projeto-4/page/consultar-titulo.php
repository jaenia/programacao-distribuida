<!DOCTYPE html>
<html>
	<head>
		<title>Consultar Título - Filmes - Mini Projeto 4</title>
		<meta charset="utf-8">
	</head>
	<body>
		<center>
			<p><strong>Sou o consultar-titulo.php</strong></p>
			<form method="get" action="../action.php">
					Busca (título):<br>
			  		<input type="text" name="titulo" placeholder="Título"><br><br>
			  		<input type="hidden" name="op" value="consultarTitulo">
			 		<input type="submit" value="Buscar">
			</form> 
		</center>
	</body>
</html>