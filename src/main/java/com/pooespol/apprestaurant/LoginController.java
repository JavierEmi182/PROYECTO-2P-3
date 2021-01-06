/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pooespol.apprestaurant;

import com.pooespol.apprestaurant.modelo.Restaurant;
import static com.pooespol.apprestaurant.modelo.Restaurant.usuarios;
import com.pooespol.apprestaurant.modelo.login.Administrador;
import com.pooespol.apprestaurant.modelo.login.Mesero;
import com.pooespol.apprestaurant.modelo.login.Usuario;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

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
        ArrayList<Usuario> usuarios = Restaurant.getUsuarios();
        Administrador admin1 = new Administrador("admin@gmail.com","admin");
        System.out.println(admin1);
        System.out.println(usuarios);
        usuarios.add(admin1);
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
        Administrador admin1 = new Administrador("admin@gmail.com","admin");
        System.out.println(admin1);
        System.out.println(usuarios);
        usuarios.add(admin1);
        System.out.println(usuarios);
        //usuarios.add(admin1);
        //usuarios.add(new Administrador("admin@gmail.com","admin"));
        Mesero mesero1 = new Mesero("mesero@gmail.com","mesero");
        usuarios.add(mesero1);
               
        Usuario usuario =null;
        do{
        usuario = validarCredenciales(tfUsuario.getText(), tfContraseña.getText());
        }while(usuario == null);
        System.out.println(usuario);
        System.out.println(tfUsuario.getText());
        System.out.println(tfContraseña.getText());
        if (usuario instanceof Administrador){
            Administrador administrador = (Administrador)usuario;
            System.out.println("El admin: "+administrador+" inicio sesion.");
            App.setRoot("IniciarAdmin");
        }else if (usuario instanceof Mesero){
            Mesero mesero = (Mesero)usuario;
            App.setRoot("IniciarMesero");
        }else{
            lbMensajeLogin.setText("Usuario y/o contraseña no validas");
        }
    
}}
