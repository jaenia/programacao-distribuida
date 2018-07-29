/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaenia.app;

import com.jaenia.dao.FilmeDAO;
import com.jaenia.model.Filme;
import java.util.List;

/**
 *
 * @author jaeni
 */
public class TesteDAO {
    public static void main(String[] args){
        FilmeDAO filmeDAO = new FilmeDAO();
        
        //adicionar filme ok
        /*Filme f1 = new Filme("titulo4", "diretor4", "estudio4", "genero4", "ano4");
        filmeDAO.adicionarFilme(f1);*/
        
        //consultar filme ok
        List<Filme> resultadoConsulta = filmeDAO.consultarFilme("");
        for(Filme f : resultadoConsulta){
            System.out.println(f.toString());
        }
        
        //alterar nome do filme ok - verificar necessidade de adaptações 
        /*filmeDAO.alterarTituloFilme(18, "tituloTrocado");*/
        
        //excluir filme com retorno de dados
        /*String dados = filmeDAO.excluirFilme(18);
        System.out.println(dados);*/
             
    }
}
