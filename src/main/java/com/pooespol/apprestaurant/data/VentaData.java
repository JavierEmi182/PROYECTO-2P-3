/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pooespol.apprestaurant.data;

import com.pooespol.apprestaurant.App;
import com.pooespol.apprestaurant.modelo.Mesa;
import static com.pooespol.apprestaurant.modelo.Restaurant.restaurant;
import static com.pooespol.apprestaurant.modelo.Restaurant.toLocalDate;
import com.pooespol.apprestaurant.modelo.Venta;
import com.pooespol.apprestaurant.modelo.login.Mesero;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Javier
 */
public class VentaData {
    static String ruta = "ventas.txt";
    
    
    public static ArrayList<Venta> leerVentas() throws IOException{
        ArrayList<Venta> ventas = new ArrayList<>();
        //Usamos la clase BufferedReader para leer archivos de texto
        try{
            //GENERA EL URL RELATIVO AL ARCHIVO QUE VAMOS A LEER
            URL u = App.class.getResource(ruta);
            File file = new File(u.toURI());
            try(BufferedReader bf = new BufferedReader(new FileReader(file))){
                String linea;
                //leemos linea a linea hasta llegar la final del archivo
                while( (linea=bf.readLine())!=null ){
                    //dividir la en partes 
                    String[] partes = linea.split(";");
                    //Convertimos parte 2 en mesa
                    Mesa m = restaurant.buscarMesa(Integer.parseInt(partes[2]));
                    //Convertimos parte 3 en mesero
                    Mesero mes= restaurant.buscarMesero(partes[3]);
                    ventas.add(new Venta(toLocalDate(partes[0]),partes[1],m,mes,Double.parseDouble(partes[4])));
                }
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
                throw ex;
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                throw ex;
            } 
        }catch(Exception ex){
            System.out.println(ex);
        }
        return ventas;
    }
    
    public static ArrayList<Venta> leerVentasPorFecha(LocalDate inicio, LocalDate fin) throws IOException{
        ArrayList<Venta> sinfiltrar= leerVentas();
        ArrayList<Venta> filtrada= new ArrayList<Venta>();
        
        for(Venta v:sinfiltrar){
            if(v.getDate().isAfter(inicio)&&v.getDate().isBefore(fin)){
                filtrada.add(v);
            }
        }return null;
    }
}
