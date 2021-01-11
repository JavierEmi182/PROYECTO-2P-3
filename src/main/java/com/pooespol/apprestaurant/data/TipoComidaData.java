/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pooespol.apprestaurant.data;

import com.pooespol.apprestaurant.App;
import com.pooespol.apprestaurant.modelo.comida.TipoComida;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class TipoComidaData {
    static String ruta1 = "tipoComida.txt";
    
    /**
     * esta funcion lee el archivo "tipoComida "y devuelve un ArrayList con los tipos de Comida que hay
     * @return
     * @throws IOException 
     */
    public static List<TipoComida> leerTipoComida() throws IOException{
        List<TipoComida> tipocomidas = new ArrayList<>();
        //Usamos la clase BufferedReader para leer archivos de texto
        try{
            //GENERA EL URL RELATIVO AL ARCHIVO QUE VAMOS A LEER
            URL u = App.class.getResource(ruta1);
            File file = new File(u.toURI());
            try(BufferedReader bf = new BufferedReader(new FileReader(file))){
                String linea;
                //leemos linea a linea hasta llegar la final del archivo
                while( (linea=bf.readLine())!=null ){
                    //System.out.println("tets");
                    //System.out.println(linea);
                    //dividir la en partes 
                  
                    tipocomidas.add(new TipoComida(linea));
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
        return tipocomidas;
    }
    
}
