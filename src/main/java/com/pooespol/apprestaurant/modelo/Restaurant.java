/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pooespol.apprestaurant.modelo;

import com.pooespol.apprestaurant.modelo.login.Usuario;
import java.util.ArrayList;

/**
 *
 * @author Javier
 */
public class Restaurant {
    public static ArrayList<Usuario> usuarios;
    public static ArrayList<Mesa> mesas ;
    public static Restaurant restaurant = new Restaurant();
    
    
    //Getter

    public static ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }
    public static ArrayList<Mesa> geMesas() {
        return mesas;
    }
    
    public Restaurant(){
        usuarios= new ArrayList<Usuario>();
        mesas = new ArrayList<Mesa>();
    }
}

