package com.jaenia;


import javax.websocket.Session;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jaeni
 */
public class Usuario {
    private Session session;
    private String username;
    
    public Usuario(Session session, String username){
        this.session = session;
        this.username = username;
    }
    
    public Session getSession(){
        return session;
    }
    
    public String getUsername(){
        return username;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
}
