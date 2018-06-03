package com.jaenia;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jaeni
 */

@ServerEndpoint("/chat/{sala}/{username}")
public class ChatMultiSalasWSEndpoint {
    
    //salas
    private static Map<String, ArrayList<Usuario>> salas = 
            Collections.synchronizedMap(new HashMap<String, ArrayList<Usuario>>());
    
    private static List<Usuario> usuarios = 
            Collections.synchronizedList(new ArrayList<Usuario>());
    
    @OnOpen
    public void onOpen(Session s, @PathParam("sala")String sala,
            @PathParam("username")String un){
        Usuario user = new Usuario(s, un);
        //verifica se o usuário já está logado em outra sala
        if(salas.containsKey(sala)){
            if(!existeUsuario(un)){
                salas.get(sala).add(user);
            }else{
                user.setUsername("anonimo");
                System.out.println(user.getUsername());
                try {
                    s.getBasicRemote().sendText("Você logou com um username que já está sendo utilizado.\n"+
                            "Seu nome sera anônimo até que você o renomeie.");
                } catch (IOException ex) {
                    Logger.getLogger(ChatMultiSalasWSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                }
                salas.get(sala).add(user);
                System.out.println(user.getUsername());
            }    
        }else{
            if(!existeUsuario(un)){
                salas.put(sala, new ArrayList<Usuario>());
                salas.get(sala).add(user);
            }else{
                try {
                    s.getBasicRemote().sendText("Este usuário já está logado em outra sala.");
                } catch (IOException ex) {
                    Logger.getLogger(ChatMultiSalasWSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    @OnMessage
    public String OnMessage(Session s, @PathParam("sala")String sala, 
            @PathParam("username")String un, String message){
        String nomeUsuario = getUsernamePorSessao(s);
        if(message.startsWith("send -u")){
            enviar(s, sala, nomeUsuario, message);
        }else if(message.startsWith("send")){
            enviarParaTodos(sala, nomeUsuario, message);
        }else if(message.startsWith("rename")){
            renomear(s, sala, nomeUsuario, message);
        }      
        return null;
    }
    
    @OnClose
    public void onClose(Session s, @PathParam("sala")String sala, @PathParam("username")String un){
        ArrayList<Usuario> usuarios = salas.get(sala);
        for(Usuario u : usuarios){
            if(u.getSession().equals(s)){
                usuarios.remove(u);
            }
        }
    }
    
    //@OnError
    
    public boolean existeUsuario(String nome){
        for(String st: salas.keySet()){
                ArrayList<Usuario> usuarios = salas.get(st);
                for(Usuario u: usuarios){
                    if(u.getUsername().equals(nome)){
                        return true;
                    }
                }
            }
        return false;
    }
    
    public void enviarParaTodos(String sala, String usuario, String message){
        //trata mensagem
        String msgPura = message.substring(5);
        ArrayList<Usuario> usuarios = salas.get(sala);
        for(Usuario u : usuarios){
            try {
                u.getSession().getBasicRemote().sendText(usuario + " " + pegarHora() + 
                            " " + msgPura);
            } catch (IOException ex) {
                Logger.getLogger(ChatMultiSalasWSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void enviar(Session s, String sala, String usuario, String message){
        //trata mensagem
        String msgComNome = message.substring(8);
        String[] aux = msgComNome.split(" ");
        String nome = aux[0];
        int tamanhoNome = nome.length();
        String msgPura = msgComNome.substring(tamanhoNome + 1);
        
        //System.out.println(tamanhoNome + "\n" + msgComNome + "\n" + nome + "\n" + msgPura);
     
        ArrayList<Usuario> usuarios = salas.get(sala);
        if(!existeUsuario(nome)){
            try {
                s.getBasicRemote().sendText("O usuário não existe.");
            } catch (IOException ex) {
                Logger.getLogger(ChatMultiSalasWSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            for(Usuario u : usuarios){
                if(u.getUsername().equals(nome)){
                    try {
                        u.getSession().getBasicRemote().sendText(usuario + " " + pegarHora() + 
                            " reservadamente: " + msgPura);
                    } catch (IOException ex) {
                        Logger.getLogger(ChatMultiSalasWSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        
    }
    
    public void renomear(Session s, String sala, String user, String message){
        String novoNome = message.split(" ")[1];
        //verifica se já existe usuário com o nome informado
        if(existeUsuario(novoNome)){
            try {
                s.getBasicRemote().sendText("Nome de usuário já em uso");
            } catch (IOException ex) {
                Logger.getLogger(ChatMultiSalasWSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            for(String st: salas.keySet()){
                ArrayList<Usuario> usuarios = salas.get(st);
                for(Usuario u: usuarios){
                    if(u.getUsername().equals(user)){
                        u.setUsername(novoNome);
                        try {
                            s.getBasicRemote().sendText("Renomeado com sucesso");
                        } catch (IOException ex) {
                            Logger.getLogger(ChatMultiSalasWSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        notificarRenomeacaoTodos(sala, user, "O usuario " + user + " alterou seu nome para " + novoNome);
                    }
                }
            }
        }
    }
    
    public void notificarRenomeacaoTodos(String sala, String remet, String message){
        ArrayList<Usuario> usuarios = salas.get(sala);
        for(Usuario u : usuarios){
            try {
                u.getSession().getBasicRemote().sendText(message);
            } catch (IOException ex) {
                Logger.getLogger(ChatMultiSalasWSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public String pegarHora(){
        GregorianCalendar calendar = new GregorianCalendar();
        String hora = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
        String minutos = Integer.toString(calendar.get(Calendar.MINUTE));
        String horaCompleta = hora + ":" + minutos;
        return horaCompleta;
    }
    
    public String getUsernamePorSessao(Session s){
        for(String st: salas.keySet()){
                ArrayList<Usuario> usuarios = salas.get(st);
                for(Usuario u: usuarios){
                    if(u.getSession().equals(s)){
                        return u.getUsername();
                    }
                }
            }
        return "";
    }
}
