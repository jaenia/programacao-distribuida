<!DOCTYPE html>
<html>
	<head>
		<title>Consulta - Filmes - Mini Projeto 3</title>
		<meta charset="utf-8">
	</head>
	<body>
		<center>
			<p>Sou o consulta.php</p>
			<form method="get" action="action.php">
					<p>Deixe em branco para listar todos os filmes</p>
			  		<input type="text" name="busca" placeholder="Busca"><br><br>
			  		<input type="hidden" name="op" value="consultar">
			 		<input type="submit" value="Buscar">
			</form> 
		</center>
	</body>
</html>