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
    public Mesero(String nombre,String correo, String contraseña) {
        super(correo, contraseña);
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
        
    }

    public void tomaPedido(){
        
    }

    public String toString(){
        return getNombre();
    }
    public boolean equals(Object o){
        if (o!= null){
            if (o instanceof Mesero){
            Mesero other = (Mesero)o;
            if(nombre.equals(other.nombre)){
                return true;
            }
        }
        }return false;
    }
}
