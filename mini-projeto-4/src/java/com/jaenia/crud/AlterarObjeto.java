/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaenia.crud;

import com.jaenia.hibernate.HibernateUtil;
import com.jaenia.resource.FilmeResource;
import org.hibernate.Session;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 *
 * @author jaeni
 */
public class AlterarObjeto {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        
        FilmeResource filme = (FilmeResource) session.load(FilmeResource.class, 1); 
        
        filme.setTitulo("tituloAlterado1");

        session.update(filme);
        session.getTransaction().commit();

        session.close();
        
        HibernateUtil.getSessionFactory().close();
        StandardServiceRegistryBuilder.destroy(HibernateUtil.getStandardServiceRegistry());
        //System.exit(0);
    }
}
