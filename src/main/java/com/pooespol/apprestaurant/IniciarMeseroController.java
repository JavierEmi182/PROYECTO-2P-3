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
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
/**
 * FXML Controller class
 *
 * @author user
 */
public class IniciarMeseroController implements Initializable {


    @FXML
    private Label lbMeseroNombre;
    @FXML
    private Circle fg1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // nombre del mesero al lbl lblMesero.setText()
        
        if (LoginController.usuario1 instanceof Mesero){
            Mesero mesero = (Mesero)LoginController.usuario1;
            lbMeseroNombre.setText("Mesero "+ mesero.getNombre() );
        }
        
    }    

    @FXML
    private void disponibilidad(MouseEvent event) throws IOException {
       
            App.setRoot("TomaPedido");
        
    }
    
}
