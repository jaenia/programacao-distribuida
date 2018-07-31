<?php
	include("../vendor/autoload.php");
	use GuzzleHttp\Client;
	$cliente = new Client();
	/*$id = $_POST["id"];
	$novoTitulo = $_POST["novoTitulo"];
	$_SERVER["REQUEST_METHOD"] = "PUT";*/
	//var_dump($partes);
	//echo $uri;
	$response = $cliente->put("http://localhost:8080/mp4/filmes/atualizar/2/Love");
	echo $response->getStatusCode()." ".$response->getBody();
?>