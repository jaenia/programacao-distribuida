<!DOCTYPE html>
<html>
	<head>
		<title>Consultar Gênero - Filmes - Mini Projeto 4</title>
		<meta charset="utf-8">
	</head>
	<body>
		<center>
			<p><strong>Sou o consultar-genero.php</strong></p>
			<form method="get" action="../action.php">
					Busca (genero):<br>
			  		<input type="text" name="genero" placeholder="Gênero"><br><br>
			  		<input type="hidden" name="op" value="consultarGenero">
			 		<input type="submit" value="Buscar">
			</form> 
		</center>
	</body>
</html>