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
    public static ArrayList<Comida> comidas ;
    public static ArrayList<Comida> comidasInventario;
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
     public static ArrayList<Comida> getComidasInventario() {
        return comidasInventario;
     }
     public static ArrayList<Venta> getVentas(){
         return ventas;
     }
     
    public Restaurant(){
        usuarios= new ArrayList<Usuario>();
        mesas = new ArrayList<Mesa>();
        comidas = new ArrayList<Comida>();
        comidasInventario = new ArrayList<Comida>();
        ventas = new ArrayList<Venta>();
    }
    
    public static LocalDate toLocalDate(String date){
        LocalDate localDate = null;
        try{DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        //String date = "16/08/2016";
        //convert String to LocalDate
        localDate = LocalDate.parse(date, formatter);
        
        }catch(java.time.format.DateTimeParseException ex1){
                        System.out.println("porfavor ingrese un formato correcto");
                    }
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
    public static void añadirUsuario (Usuario c){
        usuarios.add(c);
    }
    public static void añadirMesa (Mesa c){
        mesas.add(c);
    }
    public static void añadirComidaInventario(Comida c){
        comidasInventario.add(c);
    }
    public static void borrarComida(Comida c){
        comidas.remove(c);
    }
    public static void borrarComidaInventario(Comida c){
        comidasInventario.remove(c);
    }
    public static void añadirVenta(Venta v){
        ventas.add(v);
    } 
    
}

