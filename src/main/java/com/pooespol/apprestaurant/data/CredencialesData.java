/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pooespol.apprestaurant.data;

import com.pooespol.apprestaurant.App;
import com.pooespol.apprestaurant.modelo.comida.Comida;
import com.pooespol.apprestaurant.modelo.login.Administrador;
import com.pooespol.apprestaurant.modelo.login.Mesero;
import com.pooespol.apprestaurant.modelo.login.Usuario;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class CredencialesData {
    
    public static ArrayList<Usuario> leerAdministradores(String ruta) {
        ArrayList<Usuario> admin = new ArrayList<>();
        try(InputStream input = App.class.getResource(ruta).openStream();
                BufferedReader bf = new BufferedReader(
                                    new InputStreamReader(input,"UTF-8"))){
                String linea;
     
                while( (linea=bf.readLine())!=null ){
                    
                    String[] partes = linea.split(";");
                    System.out.println(partes[0]+" "+partes[1]);
                    //Usuario a = new Administrador(partes[0],partes[1]);
                    admin.add(new Administrador(partes[0],partes[1]));
                }
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
               
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
               
            } 
       
        return admin;
    }
    public static ArrayList<Usuario> leerMeseros(String ruta) {
        ArrayList<Usuario> meseros = new ArrayList<>();
        try(InputStream input = App.class.getResource(ruta).openStream();
                BufferedReader bf = new BufferedReader(
                                    new InputStreamReader(input,"UTF-8"))){
                String linea;
     
                while( (linea=bf.readLine())!=null ){
                    
                    String[] partes = linea.split(";");
                    Usuario p1 = new Mesero(partes[0],partes[1],partes[2]);
                    meseros.add(p1);
                }
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
                
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
              
            } 
       
        return meseros;
    }
}
