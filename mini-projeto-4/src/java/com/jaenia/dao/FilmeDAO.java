/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaenia.dao;

import com.jaenia.hibernate.HibernateUtil;
import com.jaenia.resource.FilmeResource;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 *
 * @author jaeni
 */
public class FilmeDAO {
    
    private static Session session;
    
    public void adicionarFilme(FilmeResource f){
        session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        session.save(f);
        session.getTransaction().commit();
        System.out.println("Filme adicionado com sucesso!");
        /*session.close();
        HibernateUtil.getSessionFactory().close();
        StandardServiceRegistryBuilder.destroy(HibernateUtil.getStandardServiceRegistry());*/
    }
    
    public FilmeResource alterarTituloFilme(String id, String novoTitulo){
        List<FilmeResource> filmes = new ArrayList<FilmeResource>();
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        /*filmes = session.createQuery
                    ("select f from FilmeResource f where f.titulo = :antigoTitulo").
                            setParameter("antigoTitulo", antigoTitulo).list();*/
        
        FilmeResource filme = (FilmeResource) session.load(FilmeResource.class, Integer.parseInt(id));
        filme.setTitulo(novoTitulo);
        session.update(filme);
        session.getTransaction().commit();
        
        /*for(FilmeResource f : filmes){
            f.setTitulo(novoTitulo);
            session.update(f);
            session.getTransaction().commit();
            System.out.println(f.toString());
        }*/
        
        return filme;
        
        /*session.close();
        HibernateUtil.getSessionFactory().close();
        StandardServiceRegistryBuilder.destroy(HibernateUtil.getStandardServiceRegistry());*/
    }
    
    public List<FilmeResource> consultarFilme(String consulta){
        List<FilmeResource> filmes = new ArrayList<FilmeResource>();
        session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        
        if(consulta.equals("")){
            filmes = session.createQuery
                    ("select f from FilmeResource f").list();
        }else{
            filmes = session.createQuery
                    ("select f from FilmeResource f where f.id like :consulta OR "
                            + "f.titulo like :consulta OR "
                            + "f.diretor like :consulta OR "
                            + "f.genero like :consulta OR f.anoLancamento like :consulta").
                            setParameter("consulta", consulta).list();
        }
       
        session.getTransaction().commit();
  
        /*session.close();
        
        HibernateUtil.getSessionFactory().close();
        StandardServiceRegistryBuilder.destroy(HibernateUtil.getStandardServiceRegistry());*/
        
        return filmes;
    }
    
    public String excluirFilme(String titulo){
        List<FilmeResource> filmes = new ArrayList<FilmeResource>();
        String dadosFilme = "";
        session = HibernateUtil.getSessionFactory().openSession();
        
        session.beginTransaction();
        
        filmes = session.createQuery
                    ("select f from FilmeResource f where f.titulo = :titulo").
                            setParameter("titulo", titulo).list();
        
        for(FilmeResource f : filmes){
            dadosFilme = "Título: " + f.getTitulo() + "\n" +
                "Diretor: " + f.getDiretor() + "\n" +
                "Estúdio: " + f.getEstudio() + "\n" +
                "Gênero: " + f.getGenero() + "\n" +
                "Ano de lançamento: " + f.getAnoLancamento();
            System.out.println(f.toString());
            session.delete(f);
            session.getTransaction().commit();
        } 
        
        System.out.println("Exclusão feita com sucesso!");
        /*session.close();
        
        HibernateUtil.getSessionFactory().close();
        StandardServiceRegistryBuilder.destroy(HibernateUtil.getStandardServiceRegistry());*/
        
        return "Dados do filme excluído: \n" + dadosFilme;
    }
}
