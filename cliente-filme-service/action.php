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
?>