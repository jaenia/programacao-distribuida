/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaenia;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.util.Base64;
import javax.jws.WebResult;

/**
 *
 * @author jaeni
 */
@WebService(serviceName = "CriptoStringService")
public class CriptoStringService {

    @WebResult(name="textoCripto")
    @WebMethod(operationName="encrypt")
    public String criptografar(@WebParam(name = "texto")String texto){
        String textoCripto = Base64.getEncoder().encodeToString(texto.getBytes());
        return textoCripto;
    }
    
    @WebResult(name="textoDesCripto")
    @WebMethod(operationName="decrypt")
    public String descriptografar(@WebParam(name = "textoCripto")String texto){
        byte[] arrayBytesDescripto = Base64.getDecoder().decode(texto);
        String textoDescripto = new String(arrayBytesDescripto);
        return textoDescripto;
    }
    
    /**
     * This is a sample web service operation
     */
    @WebMethod(exclude = true)
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
}
