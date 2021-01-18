/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pooespol.apprestaurant.modelo;

import com.pooespol.apprestaurant.modelo.comida.Comida;
import com.pooespol.apprestaurant.modelo.login.Mesero;
import com.pooespol.apprestaurant.modelo.login.Usuario;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author Javier
 */
public class Restaurant {
    public static ArrayList<Usuario> usuarios;
    public static ArrayList<Mesa> mesas ;
    public static Restaurant restaurant = new Restaurant();
    public static ArrayList<Venta> ventas;
    public static ArrayList<Comida> comidas;
     
    //Getter

    public static ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }
    public static ArrayList<Mesa> getMesas() {
        return mesas;
    }
     public static ArrayList<Comida> getComidas() {
        return comidas;
     }
    public Restaurant(){
        usuarios= new ArrayList<Usuario>();
        mesas = new ArrayList<Mesa>();
        comidas = new ArrayList<Comida>();
    }
    
    public static LocalDate toLocalDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        //String date = "16/08/2016";
        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate ;
    }
    
    public static Mesa buscarMesa(int numeroMesa){
        for(Mesa m:mesas){
            if(m.getNumero()==numeroMesa){
                return m;
            }           
        }
        return null;
    }
    
    public static Mesero buscarMesero(String nombre){
        ArrayList<Mesero> meseros= new ArrayList<>();
        for(Usuario u:usuarios){
            if(u instanceof Mesero){
                Mesero mesero= (Mesero)u;
                meseros.add(mesero);
            }
        }
        for(Mesero m:meseros){
            if(m.getNombre().equals(nombre)){
                return m;
            }
        }return null;
    }
    public static void añadirComida (Comida c){
        comidas.add(c);
    }
}

