/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pooespol.apprestaurant.modelo;

import com.pooespol.apprestaurant.modelo.login.Mesero;
import java.time.LocalDate;

/**
 *
 * @author user
 */
public class Venta {
    private LocalDate date;
    private String nombreCliente;
    // falta numero de cuenta (no sé si se refiere a la cuenta de cliente o es variable static de la venta)
    private Mesa numeroMesa;
    private Mesero mesero;
    private double total;
}
