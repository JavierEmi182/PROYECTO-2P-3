/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pooespol.apprestaurant.data;
import com.pooespol.apprestaurant.modelo.comida.Comida;
import com.pooespol.apprestaurant.modelo.comida.TipoComida;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import com.pooespol.apprestaurant.App;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.List;
/**
 *
 * @author user
 */
public class ComidaData {
    //static String ruta = "comida.txt";
    /**
     * Esta funcion lee el archivo "comida.txt" que se encuentra en resources y
     * retorna una List de las comidas
     * @return 
     * @throws java.io.IOException 
     */
    public static ArrayList<Comida> leerComida(String ruta) throws IOException{
        ArrayList<Comida> comidas = new ArrayList<>();
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
                    String[] partes = linea.split(";");
                    comidas.add(new Comida(partes[0],Double.parseDouble(partes[1]),partes[2],new TipoComida(partes[3])));
                }
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
                throw ex;
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                throw ex;
            } 
       
        return comidas;
    }
    /**
     * Devuelve un List de las comidas que coinciden con el tipo
     * @param tipo
     * @return 
     */
    public static ArrayList<Comida> leerComidaPorTipo(TipoComida tipo)throws IOException{
        ArrayList<Comida> comidas = new ArrayList<Comida>();
        
            ArrayList<Comida> comidasporfiltrar = leerComida("comida.txt");
            for (Comida c :comidasporfiltrar){
                if (c.getTipoComida().equals(tipo)){
                    comidas.add(c);
                }
            
        }
        
        return comidas;
        
    
    }
    /**
     * Escribe una comida dentro del archivo "comida.txt"
     * @param comida 
     */
   public static void escribirComida(Comida comida) {
        
        String ruta = "comida.txt";
        //List<Comida> comidas = new ArrayList<>();
        File file = new File(App.class.getResource(ruta).getFile());
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file,true))){
            bw.newLine();
            String linea = comida.getNombre()+";"+ comida.getPrecio()+";"+comida.getRutaImagen()+";"+comida.getTipoComida();
            bw.write(linea);
            bw.close();
            
        }  catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            
        }
        //return comidas;
    }
  
    public static void reescribirComidas(ArrayList<Comida> comidas,String ruta) 
            throws IOException, URISyntaxException{
        
        try(BufferedWriter bw = new BufferedWriter(
                new FileWriter(new File(App.class.getResource(ruta).toURI())))){
            for(Comida p: comidas){
                String linea = p.getNombre()+";"+String.valueOf(p.getPrecio())+";"+p.getRutaImagen()+";"+p.getTipoComida();
                bw.write(linea);
                bw.newLine();
            }
        }
    }
   
   
   
   
   
    /*
    public static void main (String[] args) throws IOException, URISyntaxException{
        
        //escribirComida(new Comida("pai",5,"tortaChocolate.jpeg",new TipoComida("Postres")));
        ArrayList <Comida> comidas = new ArrayList<>();
        comidas.add(new Comida("pai",5,"tortaChocolate.jpeg",new TipoComida("Postres")));
        comidas.add(new Comida("pai",5,"tortaChocolate.jpeg",new TipoComida("Postres")));
        reescribirPeliculas(comidas);
        //System.out.println(leerComida());
                
    }*/
    
    
}
