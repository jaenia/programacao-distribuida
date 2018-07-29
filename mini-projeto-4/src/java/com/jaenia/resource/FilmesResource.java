/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaenia.resource;

import com.jaenia.dao.FilmeDAO;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author jaeni
 */
@Path("/filmes")
public class FilmesResource {

    @Context
    private UriInfo context;
    
    private FilmeDAO filmeDAO = new FilmeDAO();

    /**
     * Creates a new instance of FilmesResource
     */
    public FilmesResource() {
    }
    
    //criar filme
    @POST
    @Path("cadastrar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrar(@FormParam("titulo") String titulo,
            @FormParam("diretor") String diretor, @FormParam("estudio") String estudio,
            @FormParam("genero") String genero, @FormParam("anoLancamento") String anoLancamento){
        //verificar se recurso (filme) já existe
        List<FilmeResource> filmes = filmeDAO.consultarFilme(titulo);
        for(FilmeResource f : filmes){
           if(f.getTitulo().equals(titulo)){
               return Response.status(Response.Status.CONFLICT).build();
           }
        }
        
        //recurso (filme) ainda não existe, então um novo é criado
        FilmeResource filme = new FilmeResource();
        filme.setTitulo(titulo);
        filme.setDiretor(diretor);
        filme.setEstudio(estudio);
        filme.setGenero(genero);
        filme.setAnoLancamento(anoLancamento);
        
        filmeDAO.adicionarFilme(filme);
        return Response.ok(filme, MediaType.APPLICATION_JSON).build();
    }

    //atualizar filme
    @PUT
    @Path("renomear/{id}/{novoTitulo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizar(@PathParam("id") String id, @PathParam("novoTitulo") String novoTitulo){
        //resgatar o filme através do id informado
        List<FilmeResource> filmes = filmeDAO.consultarFilme(id);
        if(filmes.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        FilmeResource filme = filmes.get(0);
        filmeDAO.alterarTituloFilme(id, novoTitulo);
        List<FilmeResource> filmes2 = filmeDAO.consultarFilme(id);
        return Response.ok(filmes2.get(0), MediaType.APPLICATION_JSON).build();    
    }
    
    
    /**
     * Sub-resource locator method for {id}
     */
    @GET
    @Path("{id}")
    public FilmeResource getFilmeResource(@PathParam("id") String id) {
        List<FilmeResource> filmes = filmeDAO.consultarFilme(id);
        if(!filmes.isEmpty()){
            return filmes.get(0);
        }
        
        return null;
    }
}
