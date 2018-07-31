<!DOCTYPE html>
<html>
	<head>
		<title>Atualizar - Filmes - Mini Projeto 4</title>
		<meta charset="utf-8">
	</head>
	<body>
		<center>
			<p><strong>Sou o atualizar.php</strong></p>
			<form method="post" action="../action.php">
				Id:<br>
				<input type="text" name="id" placeholder="Id"><br>
				Novo título:<br>
				<input type="text" name="novoTitulo" placeholder="Novo título"><br><br>
				<input type="hidden" name="op" value="atualizar">
				<input type="submit" value="Renomear">
		</center>
	</body>
</html>
