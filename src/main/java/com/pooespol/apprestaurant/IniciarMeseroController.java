/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pooespol.apprestaurant;

import com.pooespol.apprestaurant.modelo.Mesa;
import com.pooespol.apprestaurant.modelo.Restaurant;
import com.pooespol.apprestaurant.modelo.login.Mesero;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
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
    @FXML
    private Button btnCrearMesa;
    @FXML
    private Pane PanelMesas;
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

    public static void CrearVentana(String s){
        FXMLLoader loader = new FXMLLoader(App.class.getResource(s+".fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene sc = new Scene(root);
             Stage stage = new Stage();
             stage.setScene(sc);
             stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }}    

    @FXML
    private void disponibilidad(MouseEvent event) throws IOException {
         //App.setRoot("TomaPedido");
         
         circulo = (Circle) ((Pane)event.getSource()).getChildren().get(0);
         
         if (circulo.getFill() == Color.YELLOW){
             nmesa = (Label)((Pane)event.getSource()).getChildren().get(1);
             int x = Integer.parseInt(nmesa.getText());
             Mesa mesaocup = new Mesa(0, x, mesero);
             
             circulo.setFill(Color.GREENYELLOW);
             ((Pane)event.getSource()).getChildren().set(0, circulo);
             
             Restaurant.mesas.add(mesaocup);
             //CrearVentana("TomaPedido"); 
             App.setRoot("TomaPedido");
         }else if(circulo.getFill() == Color.GREENYELLOW){
             System.out.println("Hola");
         }else if(circulo.getFill() == Color.RED){ 
             System.out.println("No es tu mesa");
         }
         
    }

    @FXML
    private void CrearMesa(MouseEvent event) {
        Circle nuevocirculo = new Circle(30, Color.YELLOW);
       String n = String.valueOf(Restaurant.mesas.size()+1);
       Label numeromesa = new Label(n);
       StackPane st = new StackPane();
       st.getChildren().addAll(nuevocirculo,numeromesa);

       PanelMesas.getChildren().addAll(st);
       // Falta
    }
}
