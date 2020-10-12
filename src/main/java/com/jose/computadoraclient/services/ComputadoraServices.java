/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jose.computadoraclient.services;

import com.jose.computadoraclient.entity.Computadora;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Jose Luis
 */
@Stateless
public class ComputadoraServices {
    final String URL="http://127.0.0.1:8081/clase1/resources/sistemas";
    Computadora computador;
    
     public Computadora buscarcomputadora()
    {
        String id=computador.getSerial().toString();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Serial",  "Your message: " + id) );
        return preparar()
                .path("/buscarcomputadora/"+id)
                .request(MediaType.APPLICATION_JSON)
                .get(Computadora.class);
    }
     
    protected WebTarget preparar()
    {
        return ClientBuilder.newClient().target(URL);
    }
    public Boolean crearComputadora(Computadora computador)
    {
        try
        {Client client=ClientBuilder.newClient();
            WebTarget preparo=client.target(URL+"/crearcomputadora");
            Invocation.Builder invocationBuiler=preparo
                    .request(MediaType.APPLICATION_JSON);
            Response respuesta=invocationBuiler
                    .post(Entity.entity(computador,MediaType.APPLICATION_JSON));
            if(respuesta.getStatus()!=201)
            {
                return false;
            }
        }catch(Exception e)
        {
            System.out.println("Error"+e.getLocalizedMessage());
            return false;
        }
        return true;    
    }
    public Computadora BusquedaSerial(String id)
    {
        return preparar()
                .path("/buscarserial")
                .queryParam("serialid",id)
                .request(MediaType.APPLICATION_JSON)
                .get(Computadora.class);
    }
}
