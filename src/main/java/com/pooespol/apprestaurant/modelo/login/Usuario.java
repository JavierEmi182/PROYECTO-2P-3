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
public class Usuario {
    private String correo;
    private String contraseña;
    
    //Constructores

    public Usuario(String correo, String contraseña) {
        this.correo = correo;
        this.contraseña = contraseña;
    }
    //Getters and Setters

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    //Metodos
    public String toString(){
        return "Usuario: "+getCorreo()+", Contraseña: "+getContraseña();
    }
}
