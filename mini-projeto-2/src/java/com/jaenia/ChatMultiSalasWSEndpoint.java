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
 * @author jaenia
 */

@ServerEndpoint("/chat/{sala}/{usuario}")
public class ChatMultiSalasWSEndpoint {
    
    //salas com usuários
    private static Map<String, ArrayList<Usuario>> salas = 
            Collections.synchronizedMap(new HashMap<String, ArrayList<Usuario>>());
    
    @OnOpen
    public void onOpen(Session s, @PathParam("sala")String sala,
            @PathParam("usuario")String username){
        
        Usuario user = new Usuario(s, username);
        
        //verifica existência da sala
        if(salas.containsKey(sala)){
            if(!existeUsuario(username)){
                salas.get(sala).add(user);
                atualizarOnline(sala);
            }else{
                usuarioAnonimo(s, user);
                salas.get(sala).add(user);
                atualizarOnline(sala);
            }    
        }else{ 
            if(!existeUsuario(username)){ 
                salas.put(sala, new ArrayList<Usuario>()); 
                salas.get(sala).add(user);
                atualizarOnline(sala);
            }else{
                usuarioAnonimo(s, user);
                atualizarOnline(sala);
            }
        }
    }
    
    @OnMessage
    public String OnMessage(Session s, @PathParam("sala")String sala, String message){
        //pega o username por sessão pois o mesmo pode ser renomeado, o que não altera a url
        String nomeUsuario = getUsernamePorSessao(s);
        if(message.startsWith("send -u")){
            enviarReservada(s, sala, nomeUsuario, message);
        }else if(message.startsWith("send")){
            enviarParaTodos(sala, nomeUsuario, message);
        }else if(message.startsWith("rename")){
            renomear(s, sala, nomeUsuario, message);
        }      
        return null;
    }
    
    @OnClose
    public void onClose(Session s, @PathParam("sala")String sala){
        ArrayList<Usuario> usuarios = salas.get(sala);
        for(Usuario u : usuarios){
            if(u.getSession().equals(s)){
                usuarios.remove(u);
                atualizarOnline(sala);
            }
        } 
    }
    
    @OnError
    public void onError(Throwable t){
        System.out.println(t.getMessage());
    }
    
    //verifica existência do usuário em todas as salas
    public boolean existeUsuario(String username){
        for(String st: salas.keySet()){
                ArrayList<Usuario> usuarios = salas.get(st);
                for(Usuario u: usuarios){
                    if(u.getUsername().equals(username)){
                        return true;
                    }
                }
            }
        return false;
    }
    
    //verifica existência de usuário em uma sala específica
    public boolean existeUsuarioNaSala(String username, String sala){
        ArrayList<Usuario> usuarios = salas.get(sala);
        for(Usuario u : usuarios){
            if(u.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }
    
    //envia mensagem pública
    public void enviarParaTodos(String sala, String username, String message){
        //trata mensagem
        String msgPura = message.substring(5);
        ArrayList<Usuario> usuarios = salas.get(sala);
        for(Usuario u : usuarios){
            try {
                u.getSession().getBasicRemote().sendText(username + " " + getHora() + 
                            " : " + msgPura);
            } catch (IOException ex) {
                Logger.getLogger(ChatMultiSalasWSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //
    public void enviarReservada(Session s, String sala, String username, String message){
        //trata mensagem
        String msgComNome = message.substring(8);
        String[] aux = msgComNome.split(" ");
        String nome = aux[0];
        int tamanhoNome = nome.length();
        String msgPura = msgComNome.substring(tamanhoNome + 1);
     
        ArrayList<Usuario> usuarios = salas.get(sala);
        if(!existeUsuarioNaSala(nome, sala)){
            try {
                s.getBasicRemote().sendText("O usuário não existe na sala.");
            } catch (IOException ex) {
                Logger.getLogger(ChatMultiSalasWSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            for(Usuario u : usuarios){
                if(u.getUsername().equals(nome)){
                    try {
                        u.getSession().getBasicRemote().sendText(username + " " + getHora() + 
                            " reservadamente: " + msgPura);
                    } catch (IOException ex) {
                        Logger.getLogger(ChatMultiSalasWSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        
    }
    
    public void renomear(Session s, String sala, String username, String message){
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
                    if(u.getUsername().equals(username)){
                        u.setUsername(novoNome);
                        try {
                            s.getBasicRemote().sendText("Renomeado com sucesso");
                        } catch (IOException ex) {
                            Logger.getLogger(ChatMultiSalasWSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        notificarRenomeacaoTodos(sala, "O usuario " + username + 
                                " alterou seu nome para " + novoNome);
                        atualizarOnline(sala);
                    }
                }
            }
        }
    }
    
    public void notificarRenomeacaoTodos(String sala, String message){
        ArrayList<Usuario> usuarios = salas.get(sala);
        for(Usuario u : usuarios){
            try {
                u.getSession().getBasicRemote().sendText(message);
            } catch (IOException ex) {
                Logger.getLogger(ChatMultiSalasWSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //gera a informação dos usuários online por sala
    public void atualizarOnline(String sala){
        String usersOnline = "";
    
        //todos os usuários da sala
        ArrayList<Usuario> usuarios = salas.get(sala);
        
        //mensagem de usuários online
        for(Usuario u1 : usuarios){
            usersOnline += u1.getUsername() + "\n";
        }
        
        //envia mensagem para todos da sala
        for(Usuario u2 : usuarios){
            try {
                u2.getSession().getBasicRemote().sendText(usersOnline);
            } catch (IOException ex) {
                Logger.getLogger(ChatMultiSalasWSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public String getHora(){
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
    
    //seta o nome default para usuário existente o notifica-o da razão
    public void usuarioAnonimo(Session s, Usuario usuario){
        usuario.setUsername("anônimo");
        try {
            s.getBasicRemote().sendText("Você logou com um username que já está sendo utilizado.\n"+
                            "Seu nome sera anônimo até que você o renomeie.");
        } catch (IOException ex) {
            Logger.getLogger(ChatMultiSalasWSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
