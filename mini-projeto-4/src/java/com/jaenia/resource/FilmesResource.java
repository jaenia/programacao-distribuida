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
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
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
        //resgatar filme atualizado
        List<FilmeResource> filmes2 = filmeDAO.consultarFilme(id);
        return Response.ok(filmes2.get(0), MediaType.APPLICATION_JSON).build();    
    }
    
    //remover filme
    @DELETE
    @Path("remover/{id}")
    @Produces(MediaType.TEXT_XML)
    public Response remover(@PathParam("id") String id){
        //resgatar o filme através do id informado
        List<FilmeResource> filmes = filmeDAO.consultarFilme(id);
        if(filmes.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        FilmeResource filme = filmes.get(0);
        filmeDAO.excluirFilme(id);
        return Response.ok(filme, MediaType.TEXT_XML).build();
    }
    
    //recuperar um filme pelo id
    @GET
    @Path("consultar/id/{formato}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
    public Response getFilmeId(@QueryParam("id") String id, @PathParam("formato") String formato){
        List<FilmeResource> filmes = filmeDAO.consultarFilme(id);
        //necessário para passar uma lista no response
        GenericEntity<List<FilmeResource>> lista = new GenericEntity<List<FilmeResource>>(filmes){};
        if(filmes.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if(formato.equals("json")){
            return Response.ok(lista, MediaType.APPLICATION_JSON).build();
        }else if(formato.equals("xml")){
            return Response.ok(lista, MediaType.TEXT_XML).build();
        }
        return null;
    }
    
    //recuperar um filme pelo titulo
    @GET
    @Path("consultar/titulo/{titulo}")
    @Produces(MediaType.TEXT_HTML)
    public Response getFilmeTitulo(@PathParam("titulo") String titulo){
        List<FilmeResource> filmes = filmeDAO.consultarFilme(titulo);
     
        if(filmes.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        FilmeResource filme = filmes.get(0);
        String filmeHTML = "<html><head><meta charset='utf-8'></head><body>"
                + "<p><strong>Id: </strong>" + filme.getId() + "</p>"
                + "<p><strong>Título: </strong>" + filme.getTitulo() + "</p>"
                + "<p><strong>Diretor: </strong>" + filme.getDiretor() + "</p>"
                + "<p><strong>Estúdio: </strong>" + filme.getEstudio() + "</p>"
                + "<p><strong>Gênero: </strong>" + filme.getGenero() + "</p>"
                + "<p><strong>Ano de lançamento: </strong>" + filme.getAnoLancamento() + "</p>"
                + "</body></html>";
                
        return Response.ok(filmeHTML, MediaType.TEXT_HTML).build();
    }
    
    //recuperar filmes por diretor
    @GET
    @Path("consultar/diretor/{diretor}")
    @Produces(MediaType.TEXT_HTML)
    public Response getFilmeDiretor(@PathParam("diretor") String diretor){
        List<FilmeResource> filmes = filmeDAO.consultarFilme(diretor);
     
        if(filmes.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        String filmeHTML = "<html><head><meta charset='utf-8'></head><body>";
                
        for(FilmeResource f : filmes){
            filmeHTML += "-----"
                    + "<p><strong>Id: </strong>" + f.getId() + "</p>"
                    + "<p><strong>Título: </strong>" + f.getTitulo() + "</p>"
                    + "<p><strong>Diretor: </strong>" + f.getDiretor() + "</p>"
                    + "<p><strong>Estúdio: </strong>" + f.getEstudio() + "</p>"
                    + "<p><strong>Gênero: </strong>" + f.getGenero() + "</p>"
                    + "<p><strong>Ano de lançamento: </strong>" + f.getAnoLancamento() + "</p>";

        }
        filmeHTML += "-----</body></html>";    
                
        return Response.ok(filmeHTML, MediaType.TEXT_HTML).build();
    }
    
    //recuperar filmes por gênero
    @GET
    @Path("consultar/genero/{genero}")
    @Produces(MediaType.TEXT_HTML)
    public Response getFilmeGenero(@PathParam("genero") String genero){
        List<FilmeResource> filmes = filmeDAO.consultarFilme(genero);
     
        if(filmes.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        String filmeHTML = "<html><head><meta charset='utf-8'></head><body>";
                
        for(FilmeResource f : filmes){
            filmeHTML += "-----"
                    + "<p><strong>Id: </strong>" + f.getId() + "</p>"
                    + "<p><strong>Título: </strong>" + f.getTitulo() + "</p>"
                    + "<p><strong>Diretor: </strong>" + f.getDiretor() + "</p>"
                    + "<p><strong>Estúdio: </strong>" + f.getEstudio() + "</p>"
                    + "<p><strong>Gênero: </strong>" + f.getGenero() + "</p>"
                    + "<p><strong>Ano de lançamento: </strong>" + f.getAnoLancamento() + "</p>";

        }
        filmeHTML += "-----</body></html>";    
                
        return Response.ok(filmeHTML, MediaType.TEXT_HTML).build();
    }
    //recuperar filmes por ano de lançamento
    @GET
    @Path("consultar/ano/{ano}")
    @Produces(MediaType.TEXT_HTML)
    public Response getFilmeAnoLancamento(@PathParam("ano") String ano){
        List<FilmeResource> filmes = filmeDAO.consultarFilme(ano);
     
        if(filmes.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        String filmeHTML = "<html><head><meta charset='utf-8'></head><body>";
                
        for(FilmeResource f : filmes){
            filmeHTML += "-----"
                    + "<p><strong>Id: </strong>" + f.getId() + "</p>"
                    + "<p><strong>Título: </strong>" + f.getTitulo() + "</p>"
                    + "<p><strong>Diretor: </strong>" + f.getDiretor() + "</p>"
                    + "<p><strong>Estúdio: </strong>" + f.getEstudio() + "</p>"
                    + "<p><strong>Gênero: </strong>" + f.getGenero() + "</p>"
                    + "<p><strong>Ano de lançamento: </strong>" + f.getAnoLancamento() + "</p>";

        }
        filmeHTML += "-----</body></html>";    
                
        return Response.ok(filmeHTML, MediaType.TEXT_HTML).build();
    }
    
    /**
     * Sub-resource locator method for {id}
     */
    /*@GET
    @Path("{id}")
    public FilmeResource getFilmeResource(@PathParam("id") String id) {
        List<FilmeResource> filmes = filmeDAO.consultarFilme(id);
        if(!filmes.isEmpty()){
            return filmes.get(0);
        }
        
        return null;
    }*/
}
