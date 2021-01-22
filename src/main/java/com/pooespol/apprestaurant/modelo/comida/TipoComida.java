/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pooespol.apprestaurant.modelo.comida;

/**
 *
 * @author user
 */
public class TipoComida {
    private String nombre;
    
    public  TipoComida(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    

    @Override
    public String toString() {
        return nombre ;
    }
    @Override
    public boolean equals(Object o){
        if (o!=null){
            if (o instanceof TipoComida){
                TipoComida other = (TipoComida)o;
                if (nombre.equals(other.nombre)){
                    return true;
                }
            }
        }return false;
    }
    /*
    public static void main (String[] args){
        TipoComida t1 = new TipoComida("Postres");
        TipoComida t2= new TipoComida("Postres");
        System.out.println("tipo comida "+t1.equals(t2));
    }*/
}
