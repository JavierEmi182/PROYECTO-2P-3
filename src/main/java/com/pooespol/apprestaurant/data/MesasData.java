/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pooespol.apprestaurant.data;

import com.pooespol.apprestaurant.App;
import com.pooespol.apprestaurant.modelo.Mesa;
import static com.pooespol.apprestaurant.modelo.Restaurant.restaurant;
import com.pooespol.apprestaurant.modelo.login.Mesero;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 *
 * @author Javier
 */
public class MesasData {
    
    public static ArrayList<Mesa> leerMesas(String ruta) throws IOException{
        ArrayList<Mesa> mesas = new ArrayList<>();
        //Usamos la clase BufferedReader para leer archivos de texto
        try(InputStream input = App.class.getResource(ruta).openStream();
                BufferedReader bf = new BufferedReader(
                                    new InputStreamReader(input,"UTF-8"))){
            //GENERA EL URL RELATIVO AL ARCHIVO QUE VAMOS A LEER
           
            /*URL u = App.class.getResource(ruta);
            File file = new File(u.toURI());
            try(BufferedReader bf = new BufferedReader(new FileReader(file))){*/
                String linea;
                //leemos linea a linea hasta llegar la final del archivo
                while( (linea=bf.readLine())!=null ){
                    //System.out.println("tets");
                    //System.out.println(linea);
                    //dividir la en partes 
                    String[] partes = linea.split(",");
                    //numero,capacidad,mesero,ocupada,x,y
                    Mesero mesero;
                    if(restaurant.buscarMesero(partes[2])==null){
                        mesero=null;
                    }else{
                        mesero= restaurant.buscarMesero(partes[2]);
                    }
                    
                    mesas.add(new Mesa(Integer.parseInt(partes[0]),Integer.parseInt(partes[1]),mesero,Boolean.parseBoolean(partes[3]),Double.parseDouble(partes[4]),Double.parseDouble(partes[5])));
                }
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
                throw ex;
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                throw ex;
            } 
       
        return mesas;
    }
    
    public static void escribirMesas(ArrayList<Mesa> mesas,String ruta) 
            throws IOException, URISyntaxException{
        
        try(BufferedWriter bw = new BufferedWriter(
                new FileWriter(new File(App.class.getResource(ruta).toURI())))){
            
            for(Mesa m: mesas){
                String StringMesero ="null";
                try{
                if(m.getMesero()!=null){
                    StringMesero=String.valueOf(m.getMesero().getNombre());
                }
                String linea = String.valueOf(m.getNumero())+","+String.valueOf(m.getCapacidad())+","+StringMesero+","+String.valueOf(m.isOcupada())+","+String.valueOf(m.getX())+","+String.valueOf(m.getY());
                bw.write(linea);
                bw.newLine();
                }catch(NullPointerException ex1){
                    System.out.println("Mesa sin mesero");
                }
            }
        }
    }

}
