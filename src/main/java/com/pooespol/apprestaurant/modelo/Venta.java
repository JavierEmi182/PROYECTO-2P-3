/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pooespol.apprestaurant.modelo;

import com.pooespol.apprestaurant.modelo.Mesa;
import com.pooespol.apprestaurant.modelo.login.Mesero;
import java.time.LocalDate;

/**
 *
 * @author user
 */
public class Venta {
    private LocalDate date;
    private String nombreCliente;
    private static int numeroCuentaStatic=005;
    private int numeroCuenta;
    // falta numero de cuenta (no sé si se refiere a la cuenta de cliente o es variable static de la venta)
    private Mesa numeroMesa;
    private Mesero mesero;
    private double total;
    
    //Constructor

    public Venta(LocalDate date, String nombreCliente, Mesa numeroMesa, Mesero mesero, double total) {
        this.date = date;
        this.nombreCliente = nombreCliente;
        this.numeroMesa = numeroMesa;
        this.mesero = mesero;
        this.total = total;
        numeroCuentaStatic+=1;
        this.numeroCuenta=numeroCuenta;
    }
    
    public Venta(LocalDate date,int numeroCuenta, String nombreCliente, Mesa numeroMesa, Mesero mesero, double total) {
        this.date = date;
        this.numeroCuenta=numeroCuenta;
        this.nombreCliente = nombreCliente;
        this.numeroMesa = numeroMesa;
        this.mesero = mesero;
        this.total = total;
        
    }
    
    //Getters and setters

    public LocalDate getDate() {
        return date;
    }
    
    public int getNumeroCuenta(){
        return numeroCuenta;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Mesa getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(Mesa numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public Mesero getMesero() {
        return mesero;
    }

    public void setMesero(Mesero mesero) {
        this.mesero = mesero;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
}
