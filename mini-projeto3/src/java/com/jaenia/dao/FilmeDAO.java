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
    
    public void alterarTituloFilme(int id, String novoTitulo){
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Filme filme = (Filme) session.load(Filme.class, id); 
        
        filme.setTitulo(novoTitulo);
        session.update(filme);
        session.getTransaction().commit();
        System.out.println("Alteracao feita com sucesso!");
        System.out.println(filme.toString());
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
                            + "f.diretor like :consulta OR f.estudio like :consulta OR "
                            + "f.genero like :consulta OR f.anoLancamento like :consulta").
                            setParameter("consulta", consulta).list();
        }
       
        session.getTransaction().commit();
  
        /*session.close();
        
        HibernateUtil.getSessionFactory().close();
        StandardServiceRegistryBuilder.destroy(HibernateUtil.getStandardServiceRegistry());*/
        
        return filmes;
    }
    
    public String excluirFilme(int id){
        session = HibernateUtil.getSessionFactory().openSession();
        
        Filme f = (Filme) session.load(Filme.class, id); 
        
        String dadosFilme = "Título: " + f.getTitulo() + "\n" +
                "Diretor: " + f.getDiretor() + "\n" +
                "Estúdio: " + f.getEstudio() + "\n" +
                "Gênero: " + f.getGenero() + "\n" +
                "Ano de lançamento: " + f.getAnoLancamento();
                            
        session.beginTransaction();
        session.delete(f); //exclui o objeto da sessão
        session.getTransaction().commit();
        System.out.println("Exclusão feita com sucesso!");
        /*session.close();
        
        HibernateUtil.getSessionFactory().close();
        StandardServiceRegistryBuilder.destroy(HibernateUtil.getStandardServiceRegistry());*/
        
        return "Dados do filme excluído: \n" + dadosFilme;
    }
}
