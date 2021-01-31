/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pooespol.apprestaurant.modelo.comida;

/**
 *
 * @author Javier
 */
public class Comida {
    private String nombre;
    private double precio;
    private String rutaImagen;
    private TipoComida tipoComida;
    private int contador = 1;
    public Comida(String nombre, double precio, String rutaImagen,TipoComida tipoComida) {
        this.nombre = nombre;
        this.precio = precio;
        this.rutaImagen = rutaImagen;
        this.tipoComida = tipoComida;
    }
    public Comida (String nombre, double precio, int contador){
        this.nombre = nombre;
        this.precio = precio;
        this.contador=contador;
    }

    public Comida(String nombre, double precio, TipoComida tipoComida) {
        this.nombre = nombre;
        this.precio = precio;
       
        this.tipoComida = tipoComida;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public TipoComida getTipoComida() {
        return tipoComida;
    }
    
    public int getContador (){
        return contador;
    }
    public void setContador(){
        contador +=1;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public void setTipoComida(TipoComida tipoComida) {
        this.tipoComida = tipoComida;
    }
    

    @Override
    public String toString() {
        return  nombre +"  "+contador ;
    }
    
    @Override
    public boolean equals(Object o){
        if(o != null){
            if (o instanceof Comida){
                Comida other= (Comida)o;
                if (nombre.equals(other.nombre)){
                    return true;
                }         
            }
        }return false;
    }
    /*
    public static void main (String[] args){
        Comida c1 = new Comida("pa",3,".jpf",new TipoComida("Postres"));
        Comida c2= new Comida("pa",3,".jpf",new TipoComida("Postres"));
        System.out.println(c1.equals(c2));
    }*/
    
    
}
