/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pooespol.apprestaurant;

import com.pooespol.apprestaurant.data.MesasData;
import com.pooespol.apprestaurant.modelo.Mesa;
import com.pooespol.apprestaurant.modelo.Pedido;
import com.pooespol.apprestaurant.modelo.Restaurant;
import com.pooespol.apprestaurant.modelo.login.Mesero;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author user
 */
public class AbrirCuentaController implements Initializable {


    @FXML
    private TextField txtNombreCliente;
    @FXML
    private Button btnCrearCuenta;
   
    static Mesa mesa;
    @FXML
    private Label lblMessage;
    static Ellipse e;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    

    @FXML
    private void CrearCuenta(ActionEvent event) throws IOException, URISyntaxException {
        if (txtNombreCliente.getText()!=null){
           
           
            String cliente = txtNombreCliente.getText();
            Pedido pedido = new Pedido(mesa,cliente);
            IniciarMeseroController.añadirPedido(pedido);
            e.setFill(Color.GREEN);
            mesa.setMesero((Mesero)LoginController.usuario1);
            boolean x = true;
            mesa.setOcupada(x);
            lblMessage.setText("Cuenta creada exitosamente");
            System.out.println(Restaurant.mesas);
            MesasData.escribirMesas(Restaurant.mesas, "mesas.txt");
            //System.out.println(IniciarMeseroController.getPedidos());
            //cambiar el archivo de mesas, con mesero y ocupada true
            
        }
    }
    
    
    

}
