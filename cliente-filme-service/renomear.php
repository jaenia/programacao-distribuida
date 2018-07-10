<!DOCTYPE html>
<html>
	<head>
		<title>Renomear - Filmes - Mini Projeto 3</title>
		<meta charset="utf-8">
	</head>
	<body>
		<center>
			<p>Sou o renomear.php</p>
			<form method="post" action="action.php">
				Antigo título:<br>	
		  		<input type="text" name="antigoTitulo" placeholder="Antigo título"><br>
		  		Novo título:<br>
		  		<input type="text" name="novoTitulo" placeholder="Novo título"><br><br>
		  		<input type="hidden" name="op" value="renomear">
		 		<input type="submit" value="Renomear">
			</form> 
		</center>
	</body>
</html>