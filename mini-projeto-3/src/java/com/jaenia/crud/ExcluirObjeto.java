/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaenia.crud;

import com.jaenia.hibernate.HibernateUtil;
import com.jaenia.model.Filme;
import org.hibernate.Session;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 *
 * @author jaeni
 */
public class ExcluirObjeto {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        
        Filme filme = (Filme) session.load(Filme.class, 13); 

        session.delete(filme); //exclui o objeto da sessão
        session.getTransaction().commit();

        session.close();
        
        HibernateUtil.getSessionFactory().close();
        StandardServiceRegistryBuilder.destroy(HibernateUtil.getStandardServiceRegistry());
        //System.exit(0);
    }
}
