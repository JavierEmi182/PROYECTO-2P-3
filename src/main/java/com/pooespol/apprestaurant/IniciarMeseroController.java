/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pooespol.apprestaurant;

import com.pooespol.apprestaurant.modelo.Mesa;
import com.pooespol.apprestaurant.modelo.login.Mesero;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
/**
 * FXML Controller class
 *
 * @author user
 */
public class IniciarMeseroController implements Initializable {


    @FXML
    private Label lbMeseroNombre;

    private Circle circulo;
    private Mesero mesero;
    private Label nmesa;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // nombre del mesero al lbl lblMesero.setText()
        
        if (LoginController.usuario1 instanceof Mesero){
            mesero = (Mesero)LoginController.usuario1;
            lbMeseroNombre.setText("Mesero "+ mesero.getNombre() );
        }
        
    }    

    @FXML
    private void disponibilidad(MouseEvent event) throws IOException {
         App.setRoot("TomaPedido");
         circulo = (Circle) ((Pane)event.getSource()).getChildren().get(0);
         if (circulo.getFill() == Color.YELLOW){
             nmesa = (Label)((Pane)event.getSource()).getChildren().get(1);
             int x = Integer.parseInt(nmesa.getText());
             Mesa mesaocup = new Mesa(0, x, mesero);
             
             circulo.setFill(Color.GREEN);
             ((Pane)event.getSource()).getChildren().set(0, circulo);
         }
         
    }
    
}
