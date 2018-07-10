/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaenia.dao;

import com.jaenia.hibernate.HibernateUtil;
import com.jaenia.model.Filme;
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
    
    public void adicionarFilme(Filme f){
        session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        session.save(f);
        session.getTransaction().commit();
        System.out.println("Filme adicionado com sucesso!");
        /*session.close();
        HibernateUtil.getSessionFactory().close();
        StandardServiceRegistryBuilder.destroy(HibernateUtil.getStandardServiceRegistry());*/
    }
    
    public void alterarTituloFilme(String antigoTitulo, String novoTitulo){
        List<Filme> filmes = new ArrayList<Filme>();
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        filmes = session.createQuery
                    ("select f from Filme f where f.titulo = :antigoTitulo").
                            setParameter("antigoTitulo", antigoTitulo).list();
        
        for(Filme f : filmes){
            f.setTitulo(novoTitulo);
            session.update(f);
            session.getTransaction().commit();
            System.out.println(f.toString());
        }
        
        System.out.println("Alteracao feita com sucesso!");
        
        /*session.close();
        HibernateUtil.getSessionFactory().close();
        StandardServiceRegistryBuilder.destroy(HibernateUtil.getStandardServiceRegistry());*/
    }
    
    public List<Filme> consultarFilme(String consulta){
        List<Filme> filmes = new ArrayList<Filme>();
        session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        
        if(consulta.equals("")){
            filmes = session.createQuery
                    ("select f from Filme f").list();
        }else{
            filmes = session.createQuery
                    ("select f from Filme f where f.titulo like :consulta OR "
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
        List<Filme> filmes = new ArrayList<Filme>();
        String dadosFilme = "";
        session = HibernateUtil.getSessionFactory().openSession();
        
        session.beginTransaction();
        
        filmes = session.createQuery
                    ("select f from Filme f where f.titulo = :titulo").
                            setParameter("titulo", titulo).list();
        
        for(Filme f : filmes){
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
