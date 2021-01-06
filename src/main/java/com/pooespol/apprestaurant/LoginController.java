/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pooespol.apprestaurant;

import com.pooespol.apprestaurant.modelo.Restaurant;
import com.pooespol.apprestaurant.modelo.login.Administrador;
import com.pooespol.apprestaurant.modelo.login.Mesero;
import com.pooespol.apprestaurant.modelo.login.Usuario;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Javier
 */
public class LoginController implements Initializable {

    @FXML
    private Button btlogin;
    @FXML
    private Label lbMensajeLogin;
    @FXML
    private TextField tfUsuario;
    @FXML
    private TextField tfContraseña;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public Usuario validarCredenciales(String usuario1, String contrasena1){
        Usuario us = null;
        if (!(usuario1.equals(""))&& !(contrasena1.equals(""))){
            ArrayList<Usuario> usuarios = Restaurant.getUsuarios();
            for (Usuario usuario : usuarios){
            if(usuario.getCorreo().equals(usuario1) && usuario.getContraseña().equals(contrasena1)){
                System.out.println("Credenciales validas");
                us = usuario;
                }
            }
        } 
        if (us == null){
                System.out.println("Credenciales invalidas. Vuelva a ingresar");
        }
        return us;
    }
    
    @FXML
    private void realizarLogin(MouseEvent event) throws IOException {
        String us =tfUsuario.getText();
        String con= tfContraseña.getText();
        
        Usuario usuario =null;
        do{
        usuario = validarCredenciales(us, con);
        }while(usuario == null);
        if (usuario instanceof Administrador){
            Administrador administrador = (Administrador)usuario;
            App.setRoot("IniciarAdmin");
        }else{
            Mesero mesero = (Mesero)usuario;
            App.setRoot("IniciarMesero");
    }
    
}}
