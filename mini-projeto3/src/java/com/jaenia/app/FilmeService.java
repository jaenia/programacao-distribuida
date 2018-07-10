/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaenia.app;

import com.jaenia.dao.FilmeDAO;
import com.jaenia.model.Filme;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;

/**
 *
 * @author jaeni
 */
@WebService(serviceName = "FilmeService")
public class FilmeService {

    private FilmeDAO filmeDAO = new FilmeDAO();
    
    @WebResult(name="mensagem")
    public String adicionarFilme(@WebParam(name="titulo")String titulo, @WebParam(name="diretor")String diretor, 
            @WebParam(name="estudio")String estudio, @WebParam(name="genero")String genero, 
            @WebParam(name="anoLancamento")String anoLancamento){
        
        Filme f = new Filme(titulo, diretor, estudio, genero, anoLancamento);
        filmeDAO.adicionarFilme(f);
        
        return "Filme adicionado com sucesso!";
    } 
    
    @WebResult(name="mensagem")
    public String renomearFilme(@WebParam(name="antigoTitulo") String antigoTitulo, 
            @WebParam(name="novoTitulo") String novoTitulo){
        
        filmeDAO.alterarTituloFilme(antigoTitulo, novoTitulo);
        return "Titulo do filme alterado com sucesso!";
    }
    
    @WebResult(name="filmesEncontrados")
    public List<Filme> consultarFilme(@WebParam(name="consulta") String consulta){
        return filmeDAO.consultarFilme(consulta);
    }
    
    @WebResult(name="mensagem")
    public String excluirFilme(@WebParam(name="titulo") String titulo){
        return filmeDAO.excluirFilme(titulo);
    }
    
    /**
     * This is a sample web service operation
     */
    @WebMethod(exclude=true)
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
}
