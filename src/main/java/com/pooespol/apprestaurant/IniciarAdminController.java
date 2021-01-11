/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pooespol.apprestaurant;

import com.pooespol.apprestaurant.data.ComidaData;
import com.pooespol.apprestaurant.modelo.comida.Comida;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 * FXML Controller class
 *
 * @author Javier
 */
public class IniciarAdminController implements Initializable {


    @FXML
    private Button btGestionMenu;
    @FXML
    private FlowPane fpPantallaAdmin;
    @FXML
    private Button btnSalir;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    /**
     * El administrador puede ver información de los platos que hay en el menú
     * Ingresar nuevos platos, o editar los que ya existen
     * 
     */
    private void GestionMenu(MouseEvent event) {
        // se muestran el menu, debajo de cada menu hay una opcion que dice editar
        // al final hay un boton de "mas" que es para agregar un plato
        
        
        fpPantallaAdmin.getChildren().clear();
        
        try{
           ArrayList<Comida> comidas = ComidaData.leerComida(); 
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
                  
                 Button bteditar = new Button("EDITAR");
                 vboxmenu.getChildren().add(bteditar);
               fpPantallaAdmin.getChildren().add(vboxmenu);
           }
           Button btagregar = new Button("Agregar nuevo plato");
           fpPantallaAdmin.getChildren().add(btagregar);
        }catch(IOException ex){
            System.out.println("Problemas técnicos");
        }
        
        
    }

    @FXML
    private void regresarPrincipalAdmin(MouseEvent event) throws IOException{
        App.setRoot("Login");
    }

}
