package com.pooespol.apprestaurant;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.pooespol.apprestaurant.data.ComidaData;
import com.pooespol.apprestaurant.data.TipoComidaData;
import com.pooespol.apprestaurant.data.VentaData;
import com.pooespol.apprestaurant.modelo.Mesa;
import com.pooespol.apprestaurant.modelo.Pedido;
import com.pooespol.apprestaurant.modelo.Restaurant;
import com.pooespol.apprestaurant.modelo.Venta;
import com.pooespol.apprestaurant.modelo.comida.Comida;
import com.pooespol.apprestaurant.modelo.comida.TipoComida;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author user
 */
public class TomaPedidoController implements Initializable {


    @FXML
    private ComboBox<TipoComida> cbComida;
    @FXML
    private FlowPane fpComida;
    @FXML
    private Label lblMesaCliente;
    @FXML
    private Button btnFinalizarOrden;
    @FXML
    private VBox vboxPedido = new VBox();
    @FXML
    private Label lblTotal;
    
    static Pedido pedidoMesa;
    static Ellipse e;
    //ArrayList<Pedido> pedidos ;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         try{
            List<TipoComida> tipos = TipoComidaData.leerTipoComida();
            cbComida.getItems().addAll(tipos);   
        }catch(IOException ex){
            ex.printStackTrace();
        }
        if(pedidoMesa.getComidas().size()>0){
            for (Comida c: pedidoMesa.getComidas()){
                CrearPanelPedido(c);
                lblTotal.setText(String.valueOf(pedidoMesa.getTotal()));
            }
            
            System.out.println(pedidoMesa.getComidas());
        }
        
        //si el archivo de pedido es diferente de null se lo carga
    }    
    /*
    @FXML
    private void filtrarComida(ActionEvent event) {
        System.out.println(pedido);
        TipoComida tipo = cbComida.getValue();
        fpComida.getChildren().clear(); 
        try{
           ArrayList<Comida> comidas = ComidaData.leerComidaPorTipo(tipo); 
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
                  
               
               fpComida.getChildren().add(vboxmenu);
               //List<Comida> pedido = new ArrayList<>();
               imgv.setOnMouseClicked((MouseEvent)->{
                   c.setContador(c.getContador()+1);
                   
                   double anterior = Double.parseDouble(lblTotal.getText());
                   double actual = c.getPrecio();
                   lblTotal.setText((anterior+actual)+"");
                   
                   
                   if (!(pedido.contains(c))){
                   
                   pedido.add(c);
                   System.out.println(c);
                   HBox hcomida = new HBox();
                   Label nombre = new Label(c.getNombre()+"\nCantidad: 1");
                   nombre.setPadding(new Insets(0,0,5,5));
                   
                   Label precio = new Label("$"+c.getPrecio());
                   precio.setPadding(new Insets(0,5,5,0));
                   
                   hcomida.getChildren().addAll(nombre,precio);
                
                   vboxPedido.getChildren().add(hcomida);
                   }else{
                       
                       vboxPedido.getChildren().clear();
                       //System.out.println(pedido);
                       for (Comida com: pedido){
                       HBox hcomida = new HBox();
                       Label nombre = new Label(com.getNombre()+"\nCantidad: "+com.getContador());
                       nombre.setPadding(new Insets(0,0,5,5));

                       Label precio = new Label("$"+com.getPrecio());
                       precio.setPadding(new Insets(0,5,5,0));

                       hcomida.getChildren().addAll(nombre,precio);

                       vboxPedido.getChildren().add(hcomida);
                       }
                   }
                   
                   
                   
               });
           }
          
        }catch(IOException ex){
            System.out.println("Problemas técnicos");
        }
    }
    */
    /*
    @FXML
    private void filtrarComida(ActionEvent event){
        System.out.println(pedido);
        TipoComida tipo = cbComida.getValue();
        fpComida.getChildren().clear(); 
        try{
           ArrayList<Comida> comidas = ComidaData.leerComidaPorTipo(tipo); 
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
                  
               
               fpComida.getChildren().add(vboxmenu);
               //List<Comida> pedido = new ArrayList<>();
               imgv.setOnMouseClicked((MouseEvent)->{
                  // c.setContador(c.getContador()+1);
                   System.out.println(c);
                   double anterior = Double.parseDouble(lblTotal.getText());
                   double actual = c.getPrecio();
                   lblTotal.setText((anterior+actual)+"");
                   if (pedido.size()==0){
                       try {
                           pedido.add(new Comida(c.getNombre(),c.getPrecio(),c.getContador()+1));
                           System.out.println(c);
                           HBox hcomida = new HBox();
                           Label nombre = new Label(c.getNombre()+"\nCantidad: 1");
                           nombre.setPadding(new Insets(0,0,5,5));
                           
                           Label precio = new Label("$"+c.getPrecio());
                           precio.setPadding(new Insets(0,5,5,0));
                           
                           hcomida.getChildren().addAll(nombre,precio);
                           
                           vboxPedido.getChildren().add(hcomida);
                           ComidaData.reescribirPedido(pedido, "pedido1");
                       } catch (IOException ex) {
                           ex.printStackTrace();
                       } catch (URISyntaxException ex) {
                           ex.printStackTrace();
                       }
                   }else{
                       
                       try {
                           ArrayList<Comida> np= ComidaData.leerPedido("pedido1");
                           for (Comida comid: np){
                               if (!(pedido.contains(comid))){
                                   pedido.add(comid);
                               }
                           }
                  if (!(pedido.contains(c))){
                   
                   pedido.add(new Comida(c.getNombre(),c.getPrecio(),c.getContador()+1));
                   System.out.println(c);
                   HBox hcomida = new HBox();
                   Label nombre = new Label(c.getNombre()+"\nCantidad: 1");
                   nombre.setPadding(new Insets(0,0,5,5));
                   
                   Label precio = new Label("$"+c.getPrecio());
                   precio.setPadding(new Insets(0,5,5,0));
                   
                   hcomida.getChildren().addAll(nombre,precio);
                
                   vboxPedido.getChildren().add(hcomida);
                               try {
                                   ComidaData.reescribirPedido(pedido, "pedido1");
                               } catch (URISyntaxException ex) {
                                   ex.printStackTrace();
                               }
                   }else{
                       System.out.println(c);
                       vboxPedido.getChildren().clear();
                       //System.out.println(pedido);
                       for (Comida com: pedido){
                       HBox hcomida = new HBox();
                       Label nombre = new Label(com.getNombre()+"\nCantidad: "+com.getContador());
                       nombre.setPadding(new Insets(0,0,5,5));

                       Label precio = new Label("$"+com.getPrecio());
                       precio.setPadding(new Insets(0,5,5,0));

                       hcomida.getChildren().addAll(nombre,precio);

                       vboxPedido.getChildren().add(hcomida);
                       
                       }
                   }
                           
                           
                           
                           
                           
                       } catch (IOException ex) {
                           ex.printStackTrace();
                       }
                   }

               });
           }
          
        }catch(IOException ex){
            System.out.println("Problemas técnicos");
        }
    }*/
    @FXML
    private void filtrarComida(ActionEvent event) {
        
        TipoComida tipo = cbComida.getValue();
        fpComida.getChildren().clear(); 
        try{
           ArrayList<Comida> comidas = ComidaData.leerComidaPorTipo(tipo); 
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
                  
               
               fpComida.getChildren().add(vboxmenu);
               //List<Comida> pedido = new ArrayList<>();
               imgv.setOnMouseClicked((MouseEvent)->{
                  for(Pedido p:IniciarMeseroController.getPedidos()){
                      if(p.getMesa().equals(pedidoMesa.getMesa())){
                          if(!(p.getComidas().contains(c))){
                              p.añadirComidaPedido(c);
                              CrearPanelPedido(c);
                              
                          }else{
                              IniciarMeseroController.setContadorComida(c, pedidoMesa.getMesa());
                              vboxPedido.getChildren().clear();
                              for (Comida com: pedidoMesa.getComidas()){
                                  CrearPanelPedido(com);
                              }
                              
                          }
                          lblTotal.setText(String.valueOf(Double.parseDouble(lblTotal.getText())+c.getPrecio()));
                          System.out.println(p.getComidas());
                      }
                  }
                  
                  
                   
                   
               });
           }
          
        }catch(IOException ex){
            System.out.println("Problemas técnicos");
        }
    }
    
    public void CrearPanelPedido(Comida c){
      
       HBox hcomida = new HBox();
           Label nombre = new Label(c.getNombre()+"\nCantidad: "+c.getContador());
           nombre.setPadding(new Insets(0,0,5,5));

           Label precio = new Label("$"+c.getPrecio());
           precio.setPadding(new Insets(0,5,5,0));

           hcomida.getChildren().addAll(nombre,precio);

           vboxPedido.getChildren().add(hcomida);
       
    }
    
    
    @FXML
    private void FinalizarOrden(ActionEvent event) {
        VBox vc = new VBox();
        HBox hb = new HBox();
        Button bt1 = new Button("Cancel");
        Button bt2 = new Button("Accept");
        vc.setAlignment(Pos.CENTER);
        hb.setAlignment(Pos.CENTER);
        vc.setSpacing(25);
        hb.setSpacing(20);
        hb.getChildren().addAll(bt1,bt2);
        vc.getChildren().addAll(new Label("Desea Finalizar la orden?"),hb);
        Scene sc = new Scene(vc);
        Stage st = new Stage ();
        st.setScene(sc);
        st.setWidth(300);
        st.setHeight(200);
        st.show();
        bt1.setOnMouseClicked((MouseEvent)->{
            st.close();
        });
        bt2.setOnMouseClicked((MouseEvent)->{
            try {
                //crear la venta añadirla al array mandarla a escribir
                //Venta(LocalDate date, String nombreCliente, Mesa numeroMesa, Mesero mesero, double total)
                System.out.println(pedidoMesa.getTotal());
                System.out.println(pedidoMesa.getMesa());
                System.out.println(pedidoMesa.getCliente());
                System.out.println(pedidoMesa.getMesa().getMesero());
                //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
                //LocalDate localDate = LocalDate.parse(LocalDate.now().toString(), formatter);
                Venta venta = new Venta (LocalDate.now(),pedidoMesa.getCliente(),pedidoMesa.getMesa(),pedidoMesa.getMesa().getMesero(),pedidoMesa.getTotal());
                Restaurant.añadirVenta(venta);
                // 10/01/2021;1;Carlos Vera;1;Javier;18.00
                //desocupar la mesa (amarillo)
                boolean z = false;
                pedidoMesa.getMesa().setOcupada(z);
                pedidoMesa.getMesa().setMesero(null);
                IniciarMeseroController.borrarPedido(pedidoMesa);
                try {
                    VentaData.escribirVentas(Restaurant.getVentas(), "ventas.txt");
                } catch (URISyntaxException ex) {
                    ex.printStackTrace();
                }
                st.close();
                App.setRoot("IniciarMesero");
                
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    private void Regresar(ActionEvent event) throws IOException {
        App.setRoot("IniciarMesero");
        //volver a cargar contenido de pedido
    }
}
