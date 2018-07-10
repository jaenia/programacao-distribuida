<!DOCTYPE html>
<html>
	<head>
		<title>Cadastro - Filmes - Mini Projeto 3</title>
		<meta charset="utf-8">
	</head>
	<body>
		<center>
			<p>Sou o cadastro.php</p>
			<form method="post" action="action.php">
	  				Título<br>
			  		<input type="text" name="titulo" placeholder="Título"><br>
			  		Diretor:<br>
			  		<input type="text" name="diretor" placeholder="Diretor"><br>
			  		Estúdio:<br>
			  		<input type="text" name="estudio" placeholder="Estúdio"><br>
			  		Gênero:<br>
			  		<input type="text" name="genero" placeholder="Gênero"><br>
			  		Ano de lançamento:<br>
			  		<input type="text" name="ano" placeholder="Ano de lançamento"><br><br>
			  		<input type="hidden" name="op" value="cadastrar">
			 		<input type="submit" value="Cadastrar">
			</form> 
		</center>
	</body>
</html>