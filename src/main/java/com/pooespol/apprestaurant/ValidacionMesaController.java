/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pooespol.apprestaurant;

import com.pooespol.apprestaurant.modelo.login.Mesero;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import javafx.scene.control.Label;
/**
 * FXML Controller class
 *
 * @author user
 */
public class ValidacionMesaController implements Initializable {


    @FXML
    private Label lblAtencion;
     static Mesero meseroAtendiendo;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("IniciarMesero.fxml"));
            fxmlLoader.load();
            IniciarMeseroController jc = fxmlLoader.<IniciarMeseroController>getController();
            lblAtencion.setText(meseroAtendiendo.getNombre());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
   
 
    
}
