/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaenia.crud;

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
public class ConsultarObjeto {
    public static void main(String[] args) {
        List<FilmeResource> filmes = new ArrayList<FilmeResource>();
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        
        filmes = session.createQuery("select f from FilmeResource f").list();
       
        session.getTransaction().commit();
        
        for(FilmeResource f : filmes){
            System.out.println(f.getTitulo());
        }

        session.close();
        
        HibernateUtil.getSessionFactory().close();
        StandardServiceRegistryBuilder.destroy(HibernateUtil.getStandardServiceRegistry());
        //System.exit(0);
    }
}
