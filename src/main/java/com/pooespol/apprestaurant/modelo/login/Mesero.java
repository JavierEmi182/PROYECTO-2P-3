/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pooespol.apprestaurant.modelo.login;

/**
 *
 * @author Javier
 */
public class Mesero extends Usuario {
    private String nombre;
    //Constructor
    public Mesero(String correo, String contraseña) {
        super(correo, contraseña);
    }

    public String getNombre() {
        return nombre;
    }
    
    
    
    public void tomaPedido(){
        
    }

    
}
