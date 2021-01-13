/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pooespol.apprestaurant;

import com.pooespol.apprestaurant.modelo.Venta;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;



/**
 *
 * @author Javier
 */
public class VentaStringProperties {
    private StringProperty Fecha;
        public void setFecha(String value) { fechaProperty().set(value); }
        public String getFecha() { return fechaProperty().get(); }
        public StringProperty fechaProperty() { 
            if (Fecha == null) Fecha = new SimpleStringProperty(this, "fecha");
            return Fecha; 
        }
 //LocalDate date, String nombreCliente, Mesa numeroMesa, Mesero mesero, double total
     private StringProperty nombreCliente;
        public void setnombreCliente(String value) { nombreClienteProperty().set(value); }
        public String getnombreCliente() { return nombreClienteProperty().get(); }
        public StringProperty nombreClienteProperty() { 
            if (nombreCliente == null) nombreCliente = new SimpleStringProperty(this, "nombreCliente");
            return nombreCliente; 
        }
        
    private StringProperty numeroMesa;
        public void setnumeroMesa(String value) { numeroMesaProperty().set(value); }
        public String getnumeroMesa() { return numeroMesaProperty().get(); }
        public StringProperty numeroMesaProperty() { 
            if (numeroMesa == null) numeroMesa = new SimpleStringProperty(this, "numeroMesa");
            return numeroMesa; 
        }   
    
    private StringProperty mesero;
        public void setmesero(String value) { meseroProperty().set(value); }
        public String getmesero() { return meseroProperty().get(); }
        public StringProperty meseroProperty() { 
            if (mesero == null) mesero = new SimpleStringProperty(this, "mesero");
            return mesero; 
        }
        
    private StringProperty total;
        public void settotal(String value) { totalProperty().set(value); }
        public String gettotal() { return totalProperty().get(); }
        public StringProperty totalProperty() { 
            if (total == null) total = new SimpleStringProperty(this, "total");
            return total; 
        }
        
    public VentaStringProperties(Venta v){
        //LocalDate date, String nombreCliente, Mesa numeroMesa, Mesero mesero, double total
        setFecha(String.valueOf(v.getDate()));
        setnombreCliente(String.valueOf(v.getNombreCliente()));
        setnumeroMesa(String.valueOf(v.getNumeroMesa().getNumero()));
        setmesero(String.valueOf(v.getMesero().getNombre()));       
        settotal(String.valueOf(v.getTotal()));
    }
    
}
