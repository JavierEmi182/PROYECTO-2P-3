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
    private String tipoComida;

    public Comida(String nombre, double precio, String rutaImagen,String tipoComida) {
        this.nombre = nombre;
        this.precio = precio;
        this.rutaImagen = rutaImagen;
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

    public String getTipoComida() {
        return tipoComida;
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

    public void setTipoComida(String tipoComida) {
        this.tipoComida = tipoComida;
    }
    

    @Override
    public String toString() {
        return "Nombre: " + nombre + "\n Precio: " + precio ;
    }
    
    
    
    
}
