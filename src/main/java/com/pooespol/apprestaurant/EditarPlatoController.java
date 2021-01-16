/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pooespol.apprestaurant;

import com.pooespol.apprestaurant.data.TipoComidaData;
import com.pooespol.apprestaurant.modelo.Restaurant;
import com.pooespol.apprestaurant.modelo.comida.Comida;
import com.pooespol.apprestaurant.modelo.comida.TipoComida;
import java.io.IOException;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
/**
 * FXML Controller class
 *
 * @author user
 */
public class EditarPlatoController implements Initializable {


    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtPrecio;
    @FXML
    private ComboBox<TipoComida> cbTipos;
    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnGuardar;
    @FXML
    private Label lblMessage;
    @FXML
    private Button btnClear;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try{
            List<TipoComida> tipos = TipoComidaData.leerTipoComida();
            cbTipos.getItems().addAll(tipos);
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void RegresarScene(ActionEvent event) throws IOException{
        App.setRoot("IniciarAdmin");
    }

    @FXML
    private void GuardarCambios(ActionEvent event)throws IOException {
        //crear el nuevo objeto tipo comida
       Comida newComida = null;
        try{
           String nombre =getTxtNombre();
           double precio = getTxtPrecio();
           TipoComida tipo = getCbTipos();

        newComida = new Comida (nombre,precio,tipo);
       // Restaurant.añadirComida(newComida);
        
        if (newComida == null || nombre.equals("") || tipo.equals("")){
            throw new NullPointerException();
        }
        App.setRoot("IniciarAdmin");
        
        //System.out.println(nombre+precio+tipo);
        
       }catch(NullPointerException ex){
           lblMessage.setText("Algo ocurrió. No se han guardado cambios");
       }catch(InputMismatchException ex){
           lblMessage.setText("Formato no válido. No se han guardado cambios");
       }catch(NumberFormatException ex){
           lblMessage.setText("Formato no válido. No se han guardado cambios");
       
       }
        
        //validar que no sea null
        // agregarlo al array
        // sobreescribir el arhivo "comidas.txt"
        
       // return newComida;
    }
    public String getTxtNombre(){
        return txtNombre.getText();
    }
    public double getTxtPrecio(){
        return Double.parseDouble(txtPrecio.getText());
    }
    public TipoComida getCbTipos(){
        return cbTipos.getValue();
    }

    public void setTxtNombre(TextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public void setTxtPrecio(TextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    public void setCbTipos(ComboBox<TipoComida> cbTipos) {
        this.cbTipos = cbTipos;
    }
    

    @FXML
    private void LimpiarRegistro(ActionEvent event) {
        txtNombre.setText("");
        txtPrecio.setText("");
        //cbTipos.getItems().clear();
        lblMessage.setText("");
        
    }
    
   

   
    
}
