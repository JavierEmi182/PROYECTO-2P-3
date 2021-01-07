/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pooespol.apprestaurant.modelo;
import com.pooespol.apprestaurant.modelo.login.Mesero;
/**
 *
 * @author user
 */
public class Mesa {
    private int numero;
    private int capacidad;
    private Mesero mesero;
    private boolean ocupada = false;
    
    public Mesa (int capacidad,int numero,Mesero mesero){
        this.numero = numero;
        this.capacidad = capacidad;
        this.mesero = mesero;
        ocupada = true;
              
    }
    public Mesa (int capacidad,int numero){
        this.numero = numero;
        this.capacidad = capacidad;
        ocupada = true;
              
    }

    public int getNumero() {
        return numero;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public Mesero getMesero() {
        return mesero;
    }

    public boolean isOcupada() {
        return ocupada;
    }
    

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public void setMesero(Mesero mesero) {
        this.mesero = mesero;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    @Override
    public String toString() {
        return "Mesa{" + "numero=" + numero + ", capacidad=" + capacidad + ", mesero=" + mesero + '}';
    }
    
    
}
