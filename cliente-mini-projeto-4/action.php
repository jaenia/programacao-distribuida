<?php
	include("vendor/autoload.php");
	use GuzzleHttp\Client;
	$cliente = new Client();
	/*$response = $cliente->get("http://localhost:8080/mp4/filmes/consultar/genero/Romance");
	echo $response->getStatusCode()." ".$response->getBody();*/

	if(isset($_POST["op"])){
		//criar um filme
		if($_POST["op"] == "cadastrar"){
			try{
				$response = $cliente->post("http://localhost:8080/mp4/filmes/cadastrar", [
					"form_params" => [
					    "titulo" => $_POST["titulo"],
					    "diretor" => $_POST["diretor"],
					    "estudio" => $_POST["estudio"],
						"genero" => $_POST["genero"],
						"anoLancamento" => $_POST["ano"]
					]
				]);
				echo "Http status: " .$response->getStatusCode()." - OK <br<br>";
				echo "Filme adicionado com sucesso!<br>";
				echo $response->getBody();
			}catch (GuzzleHttp\Exception\ClientException $e) {
				$response = $e->getResponse();	
				echo $response->getBody();
			}
		}else if($_POST["op"] == "atualizar"){
			try{
				$id = $_POST["id"];
				$novoTitulo = $_POST["novoTitulo"];
				$response = $cliente->put("http://localhost:8080/mp4/filmes/atualizar/$id/$novoTitulo");
				echo "Http status: " .$response->getStatusCode()." - OK <br><br>";
				echo "Filme alterado com sucesso!<br>";
				echo $response->getBody();
			}catch (GuzzleHttp\Exception\ClientException $e) {
				$response = $e->getResponse();
				echo $response->getBody();
			}		
		}else if($_POST["op"] == "excluir"){
			try{
				$id = $_POST["id"];
				$response = $cliente->delete("http://localhost:8080/mp4/filmes/remover/$id");
				echo "Http status: " .$response->getStatusCode()." - OK <br><br>";
				echo "Filme excluído com sucesso!<br>";
				echo $response->getBody();
			}catch (GuzzleHttp\Exception\ClientException $e) {
				$response = $e->getResponse();
				echo $response->getBody();
			}
		}else if($_POST["op"] == "consultarId"){
			try{
				$id = $_GET["id"];
				$formato = $_GET["formato"];
				$response = $cliente->delete("http://localhost:8080/mp4/filmes/consultar/id?id=$id&formato=$formato");
				echo "Http status: " .$response->getStatusCode()." - OK <br><br>";
				echo "Resultado da busca: <br>";
				echo $response->getBody();
			}catch (GuzzleHttp\Exception\ClientException $e) {
				$response = $e->getResponse();
				echo $response->getBody();	
			}
		}
	}

	if(isset($_GET["op"])){
		if($_GET["op"] == "consultarId"){
			try{
				$id = $_GET["id"];
				$formato = $_GET["formato"];
				$response = $cliente->get("http://localhost:8080/mp4/filmes/consultar/id?id=$id&formato=$formato");
				echo "Http status: " .$response->getStatusCode()." - OK <br><br>";
				echo "Resultado da busca: <br>";
				echo $response->getBody();
			}catch (GuzzleHttp\Exception\ClientException $e) {
				$response = $e->getResponse();
				echo $response->getBody();		
			}			
		}else if($_GET["op"] == "consultarTitulo"){
			try{
				$titulo = $_GET["titulo"];
				$response = $cliente->get("http://localhost:8080/mp4/filmes/consultar/titulo/$titulo");
				echo "Http status: " .$response->getStatusCode()." - OK <br><br>";
				echo "Resultado da busca: <br>";
				echo $response->getBody();
			}catch (GuzzleHttp\Exception\ClientException $e) {
				$response = $e->getResponse();
				echo $response->getBody();		
			}	
		}else if($_GET["op"] == "consultarDiretor"){
			try{
				$diretor = $_GET["diretor"];
				$response = $cliente->get("http://localhost:8080/mp4/filmes/consultar/diretor/$diretor");
				echo "Http status: " .$response->getStatusCode()." - OK <br><br>";
				echo "Resultado da busca: <br>";
				echo $response->getBody();
			}catch (GuzzleHttp\Exception\ClientException $e) {
				$response = $e->getResponse();
				echo $response->getBody();		
			}	
		}else if($_GET["op"] == "consultarGenero"){
			try{
				$genero = $_GET["genero"];
				$response = $cliente->get("http://localhost:8080/mp4/filmes/consultar/genero/$genero");
				echo "Http status: " .$response->getStatusCode()." - OK <br><br>";
				echo "Resultado da busca: <br>";
				echo $response->getBody();
			}catch (GuzzleHttp\Exception\ClientException $e) {
				$response = $e->getResponse();
				echo $response->getBody();		
			}	
		}else if($_GET["op"] == "consultarAno"){
			try{
				$ano = $_GET["ano"];
				$response = $cliente->get("http://localhost:8080/mp4/filmes/consultar/ano/$ano");
				echo "Http status: " .$response->getStatusCode()." - OK <br><br>";
				echo "Resultado da busca: <br>";
				echo $response->getBody();
			}catch (GuzzleHttp\Exception\ClientException $e) {
				$response = $e->getResponse();
				echo $response->getBody();		
			}	
		}
	}

	/*
<?php
	$ws = new SoapClient("http://desktop-89i9jlc:8080/mp3/FilmeService?wsdl");

	if(isset($_POST["op"])){
		if($_POST["op"] == "cadastrar"){
			$operacao = "adicionarFilme";
			$resultado = $ws->__soapCall($operacao, array("parameters" => array("titulo"=>$_POST["titulo"],
		                                                                           "diretor"=>$_POST["diretor"],
		                                                                           "estudio"=>$_POST["estudio"],
		                                                                           "genero"=>$_POST["genero"],
		                                                                           "anoLancamento"=>$_POST["ano"])));
			echo "Resultado de " . $operacao . ": ";
			var_dump($resultado->mensagem);
		}

		if($_POST["op"] == "renomear"){
			$operacao = "renomearFilme";
			$resultado = $ws->__soapCall($operacao, array("parameters" => array("antigoTitulo"=>$_POST["antigoTitulo"],
		                                                                           "novoTitulo"=>$_POST["novoTitulo"])));

			echo "Resultado de " . $operacao . ": ";
			var_dump($resultado->mensagem);
		}

		if($_POST["op"] == "excluir"){
			$operacao = "excluirFilme";
			$resultado = $ws->__soapCall($operacao, array("parameters" => array("titulo"=>$_POST["tituloExc"])));

			echo "Resultado de " . $operacao . ": ";
			var_dump($resultado->mensagem);

		}
	}

	if(isset($_GET["op"])){
		if($_GET["op"] == "consultar"){
			$operacao = "consultarFilme";
			$resultado = $ws->__soapCall($operacao, array("parameters" => array("consulta"=>$_GET["busca"])));

			echo "Resultado de " . $operacao . ": ";
			//var_dump($resultado->filmesEncontrados);

			if(is_array($resultado->filmesEncontrados)){
				echo "<table border=1>";
				echo "<tr>";
				echo "<th>Título</th>";
				echo "<th>Diretor</th>";
				echo "<th>Estúdio</th>";
				echo "<th>Gênero</th>";
				echo "<th>Ano de lançamento</th>";
				echo "</tr>";
				
				foreach ($resultado->filmesEncontrados as $key => $value) {
					echo "<tr>";
					echo "<td>$value->titulo</td>";
					echo "<td>$value->diretor</td>";
					echo "<td>$value->estudio</td>";
					echo "<td>$value->genero</td>";
					echo "<td>$value->anoLancamento</td>";
					echo "</tr>";
				}

				echo "</table>";
			}else{
				echo "<table border=1>";
				echo "<tr>";
				echo "<th>Título</th>";
				echo "<th>Diretor</th>";
				echo "<th>Estúdio</th>";
				echo "<th>Gênero</th>";
				echo "<th>Ano de lançamento</th>";
				echo "</tr>";
				echo "<tr>";
				echo "<td>".$resultado->filmesEncontrados->titulo."</td>";
				echo "<td>".$resultado->filmesEncontrados->diretor."</td>";
				echo "<td>".$resultado->filmesEncontrados->estudio."</td>";
				echo "<td>".$resultado->filmesEncontrados->genero."</td>";
				echo "<td>".$resultado->filmesEncontrados->anoLancamento."</td>";
				echo "</tr>";
				echo "</table>";
			}
		}
	}
?>*/
?>