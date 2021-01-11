package com.pooespol.apprestaurant;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.pooespol.apprestaurant.data.ComidaData;
import com.pooespol.apprestaurant.data.TipoComidaData;
import com.pooespol.apprestaurant.modelo.comida.Comida;
import com.pooespol.apprestaurant.modelo.comida.TipoComida;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
/**
 * FXML Controller class
 *
 * @author user
 */
public class TomaPedidoController implements Initializable {


    @FXML
    private FlowPane fpPedido;
    @FXML
    private ComboBox<TipoComida> cbComida;
    @FXML
    private FlowPane fpComida;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         try{
            List<TipoComida> tipos = TipoComidaData.leerTipoComida();
            cbComida.getItems().addAll(tipos);
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
        
    }    

    @FXML
    private void filtrarComida(ActionEvent event) {
        TipoComida tipo = cbComida.getValue();
        fpComida.getChildren().clear(); 
        try{
           ArrayList<Comida> comidas = ComidaData.leerComidaPorTipo(tipo); 
           for (Comida c: comidas){
               //vbox con imagen,nombre, precio, boton
               VBox vboxmenu = new VBox();
               vboxmenu.setAlignment(Pos.CENTER);
               //la imagen
                InputStream inputImg= App.class.getResource(c.getRutaImagen()).openStream();
                ImageView imgv = new ImageView(new Image(inputImg));
                vboxmenu.getChildren().add(imgv);
                
                //el nombre de la pelicula
                Label lnombre = new Label(c.getNombre());
              
                vboxmenu.getChildren().add(lnombre);
                //anio
                Label lprecio = new Label("$"+String.valueOf(c.getPrecio()));
                 vboxmenu.getChildren().add(lprecio);
                  vboxmenu.setPadding(new Insets(2,3,3,4));
                  
                
               fpComida.getChildren().add(vboxmenu);
           }
          
        }catch(IOException ex){
            System.out.println("Problemas técnicos");
        }
    }
    
}
