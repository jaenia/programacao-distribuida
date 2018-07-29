/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaenia.crud;

/**
 *
 * @author jaeni
 */
import com.jaenia.hibernate.HibernateUtil;
import com.jaenia.resource.FilmeResource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class AdicionarObjeto {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
       
        FilmeResource filme1 = new FilmeResource();
        filme1.setTitulo("Novo projeto");
        filme1.setDiretor("diretor1");
        filme1.setEstudio("estudio1");
        filme1.setGenero("genero1");
        filme1.setAnoLancamento("ano1");

        session.save(filme1);

        session.getTransaction().commit();

        session.close();
        
        HibernateUtil.getSessionFactory().close();
        StandardServiceRegistryBuilder.destroy(HibernateUtil.getStandardServiceRegistry());
        //System.exit(0);
    }
}

