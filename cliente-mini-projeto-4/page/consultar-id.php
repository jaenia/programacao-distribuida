<!DOCTYPE html>
<html>
	<head>
		<title>Consultar ID - Filmes - Mini Projeto 4</title>
		<meta charset="utf-8">
	</head>
	<body>
		<center>
			<p><strong>Sou o consultar-id.php</strong></p>
			<form method="get" action="../action.php">
					Busca (id):<br>
			  		<input type="text" name="id" placeholder="Id"><br>
			  		Formato (JSON ou XML):<br>
			  		<select name="formato">
			  			<option value="json">JSON</option>
			  			<option value="xml">XML</option>
			  		</select><br><br>
			  		<input type="hidden" name="op" value="consultarId">
			 		<input type="submit" value="Buscar">
			</form> 
		</center>
	</body>
</html>