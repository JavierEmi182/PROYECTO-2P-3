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
import com.pooespol.apprestaurant.modelo.comida.Comida;
import com.pooespol.apprestaurant.modelo.login.Mesero;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author user
 */
public class IniciarMeseroController implements Initializable {


   

    private Circle circulo;
    private Mesero mesero;
    private Label nmesa;
    private Pane PanelMesas;
    @FXML
    private Label lblMesero;
    @FXML
    private Pane paneMesero;
    static ArrayList<Pedido> pedidos = new ArrayList<>();
    String cliente;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            // nombre del mesero al lbl lblMesero.setText()
            
            if (LoginController.usuario1 instanceof Mesero){
                mesero = (Mesero)LoginController.usuario1;
                lblMesero.setText(mesero.getNombre() );
                
            }
            //IniciarAdminController.CargarMesas(paneMesero);
            Restaurant.mesas= MesasData.leerMesas("mesas.txt");
            CargarMesas();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }    
    public void setCliente(String s){
        cliente = s;
    }
    public static void añadirPedido(Pedido p){
        pedidos.add(p);
    }
    public static ArrayList<Pedido> getPedidos(){
        return pedidos;
    }
    public static void setContadorComida(Comida c,Mesa m){
        for(Pedido p:pedidos){
            if(p.getMesa().equals(m)){
                for(Comida com: p.getComidas()){
                    if(c.equals(com)){
                        com.setContador();
                    }
                }
            }
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
        }
    }
    public Pane CargarMesas(){
        paneMesero.getChildren().clear();
        Pane pane = new Pane();
        pane.setPrefHeight(paneMesero.getHeight());
        pane.setPrefWidth(paneMesero.getWidth());
        
            
            try{
                System.out.println(Restaurant.mesas);
                for(Mesa m:Restaurant.mesas){
                    
                    StackPane sp = new StackPane();
                    double x= m.getX();
                    double y= m.getY();
                    int numeroMesa= m.getNumero();
                    
                    Ellipse elipse = new Ellipse(50,50);
                    if (m.isOcupada()==false){
                        elipse.setFill(Color.YELLOW);
                    }else if(m.getMesero().equals(mesero) && m.isOcupada()==true){
                        elipse.setFill(Color.GREEN);
                    }else{
                        elipse.setFill(Color.RED);
                    }
                    
                    Label lbNumMesa = new Label(String.valueOf(numeroMesa));
                    
                    sp.setLayoutX(x);
                    sp.setLayoutY(y);
                    
                    sp.getChildren().addAll(elipse,lbNumMesa);
                    pane.getChildren().add(sp);
                    
                    sp.setOnMouseClicked((MouseEvent)->{
                        //VALIDACIONES 
                        // QUE NO PERTENEZCA A OTRO MESERO
                        //verde 0x008000ff
                        //amarillo 0xffff00ff
                        //rojo  0xff0000ff
                        String color = elipse.getFill().toString();
                        
                        
                        if (color.equals("0xff0000ff")){//color rojo
                             ValidacionMesaController.meseroAtendiendo=m.getMesero();
                            CrearVentana("ValidacionMesa");
                           
                        }else if (color.equals("0xffff00ff")){//amarillo(disponible para crear cuentas)
                           
                            
                            
                            //primero setear varibles
                            //FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("AbrirCuenta.fxml"));
                           // Parent root = fxmlLoader.load();
                           // AbrirCuentaController jc = fxmlLoader.<AbrirCuentaController>getController();
                           //System.out.println(m.getMesero());
                          // m.setMesero((Mesero)LoginController.usuario1);
                          //  elipse.setFill(Color.GREEN);
                            //m.setOcupada(true);
                            
                            AbrirCuentaController.mesa=m;
                            AbrirCuentaController.e=elipse;
                            //jc.setMesa(m);
                            //luego cambiar
                            CrearVentana("AbrirCuenta");
                          //  System.out.println(m.getMesero());
                             //color verde deberia buscar en el array de pedidos por el numero de mesa 
                            
                        }else {//verde
                            //buscar en el array la mesa
                            // si tiene comida cargarla en pantalla
                            for (Pedido p:pedidos){
                                if(p.getMesa().equals(m)){
                                    if(p.getComidas().size()>0){
                                      TomaPedidoController.CrearPanelPedido(p);
                                  }else{
                                        try {
                                            TomaPedidoController.mesapedido=m;
                                            App.setRoot("TomaPedido");
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        }
                                  }
                                }
                              
                            
                            }
                            
                        }
                        //y cargar ingo en toma pedido si es que hay 
                    });
                    
                    
                }
                paneMesero.getChildren().add(pane);
            
            }catch(RuntimeException e){
                System.out.println(e.getMessage());
            }catch(Exception e){
                System.out.println("Error");
            }
            return pane;
    }
    
    











    

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
