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
    private double x;
    private double y;
    
    public Mesa (int capacidad,int numero,Mesero mesero){
        this.numero = numero;
        this.capacidad = capacidad;
        this.mesero = mesero;
        ocupada = true;
              
    }
    
    public Mesa(int numero, int capacidad, Mesero mesero, boolean ocupada, double x, double y){
        this.numero=numero;
        this.capacidad=capacidad;
        this.mesero=mesero;
        this.ocupada=ocupada;
        this.x=x;
        this.y=y;
    }
    
    public Mesa(int numero, int capacidad, boolean ocupada, double x, double y){
        this.numero=numero;
        this.capacidad=capacidad;
        this.ocupada=ocupada;
        this.x=x;
        this.y=y;
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
        try{
        return mesero;
        }catch(NullPointerException ex){
            return null;
        }
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    
    public void setX(double x) {
        this.x = x;
    }
    
    public void setY(double y) {
        this.y = y;
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
        return "Mesa{" + "numero=" + numero + ", capacidad=" + capacidad + ", mesero=" + mesero + '}'+ocupada;
    }
    public boolean equals(Object o){
        if (o!=null){
            if (o instanceof Mesa){
                Mesa other = (Mesa)o;
                if(String.valueOf(numero).equals(String.valueOf(other.numero))){
                    return true;
                }
            }
        }return false;
    }
    
}
