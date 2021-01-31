/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pooespol.apprestaurant.modelo;

import com.pooespol.apprestaurant.modelo.comida.Comida;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class Pedido {
    private ArrayList<Comida> comidas;
    private Mesa mesa;
    private double total =0;
    private String cliente;
    public Pedido(Mesa m,String cliente){
        comidas = new ArrayList<>();
        mesa = m;
        this.cliente = cliente;
    }
    
    public void añadirComidaPedido(Comida c){
        comidas.add(c);
    }
    public  ArrayList<Comida> getComidas(){
        return comidas;
    }
    public Mesa getMesa(){
        return mesa;
    }
   
    public double getTotal(){
        double monto =0;
        if(comidas.size()>0){
            for(Comida c:comidas){
                monto+=c.getPrecio();
            }
        }return monto;
    }
    public boolean equals(Object o){
        if (o!=null){
            if(o instanceof Pedido){
                Pedido other = (Pedido)o;
                if(mesa.equals(other.mesa)&& cliente.equals(other.cliente)){
                    return true;
                }
            }
        }return false;
    }
}
