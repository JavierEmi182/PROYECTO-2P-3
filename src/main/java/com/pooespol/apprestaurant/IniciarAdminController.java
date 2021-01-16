/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pooespol.apprestaurant;

import com.pooespol.apprestaurant.data.ComidaData;
import com.pooespol.apprestaurant.data.VentaData;
import static com.pooespol.apprestaurant.data.VentaData.leerVentas;
import static com.pooespol.apprestaurant.modelo.Restaurant.restaurant;
import com.pooespol.apprestaurant.modelo.Venta;
import com.pooespol.apprestaurant.modelo.comida.Comida;
import com.pooespol.apprestaurant.modelo.comida.TipoComida;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Javier
 */
public class IniciarAdminController implements Initializable {
    private EditarPlatoController edt;

    @FXML
    private Button btGestionMenu;
    @FXML
    private FlowPane fpPantallaAdmin;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btReportesVentas;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    /**
     * El administrador puede ver información de los platos que hay en el menú
     * Ingresar nuevos platos, o editar los que ya existen
     * 
     */
    private void GestionMenu(MouseEvent event) {
        // se muestran el menu, debajo de cada menu hay una opcion que dice editar
        // al final hay un boton de "mas" que es para agregar un plato
        
        
        fpPantallaAdmin.getChildren().clear();
        
        try{
           ArrayList<Comida> comidas = ComidaData.leerComida(); 
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
                  vboxmenu.setPadding(new Insets(2,4,3,4));
                  
                 Button bteditar = new Button("EDITAR");
                 vboxmenu.getChildren().add(bteditar);
               fpPantallaAdmin.getChildren().add(vboxmenu);
               
               bteditar.setOnMouseClicked ( 
                       (MouseEvent) -> {
                   
                       editarPlato(c);
                   
                       }
               );
               
           }
           Button btagregar = new Button("Click para agregar nuevo plato");
           /*btagregar.setOnMouseClicked(
              (MouseEvent) ->{
               try {
                   crearPlato();
               } catch (IOException ex) {
                   ex.printStackTrace();
               }
              }
           
           );*/
           fpPantallaAdmin.getChildren().add(btagregar);
           btagregar.setAlignment(Pos.CENTER);
        }catch(IOException ex){
            System.out.println("Problemas técnicos");
        }
        
        
    }
    
    public  void editarPlato(Comida comida) {
        try {
            App.setRoot("editarPlato");
            /*
            para poner en la misma scene
            fpPantallaAdmin.getChildren().clear();
            
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("editarPlato.fxml"));
            Parent root = fxmlLoader.load();
            fpPantallaAdmin.getChildren().add(root);*/
            
            /*
            try{
            App.setRoot("editarPlato");
            
            
            
            comida.setNombre(edt.getTxtNombre());
            
            }catch(IOException ex){
            ex.printStackTrace();
            }*/
            
            /*
            comida.setNombre(editarPlato.getTxtNombre());
            comida.setPrecio(editarPlato.getTxtPrecio());
            comida.setTipoComida(editarPlato.getCbTipos());
            System.out.println(comida);*/
            
            /*
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("editarPlato.fxml"));
            EditarPlatoController editarPlato = fxmlLoader.getController();
            String nombre =editarPlato.getTxtNombre();
            double precio = editarPlato.getTxtPrecio();
            TipoComida tipo = editarPlato.getCbTipos();
            System.out.println(nombre+precio+tipo);*/
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
  
    }
    public void crearPlato()throws IOException{
        App.setRoot("editarPlato");
    }

    @FXML
    private void regresarPrincipalAdmin(MouseEvent event) throws IOException{
        App.setRoot("Login");
    }

    @FXML
    private void ReportesVentas(MouseEvent event) {
        fpPantallaAdmin.getChildren().clear();
        
        
        try{
            HBox barraSuperior= new HBox();
            barraSuperior.setAlignment(Pos.CENTER);
            BorderPane.setMargin(barraSuperior, new Insets(12,0,20,30));
            
            Label fechai = new Label("Fecha Inicio: ");
            fechai.setPadding(new Insets(0,10,0,30));
            Label fechaf = new Label("Fecha final: ");
            fechaf.setPadding(new Insets(0,30,0,10));
            TextField fechaInicio= new TextField();
            fechaInicio.setPadding(new Insets(0,30,0,10));
            TextField fechaFinal= new TextField();
            fechaFinal.setPadding(new Insets(0,30,0,10));
            Button btBuscar= new Button("Buscar");
            btBuscar.setPadding(new Insets(0,10,0,10));
            barraSuperior.getChildren().add(fechai);
            barraSuperior.getChildren().add(fechaInicio);
            barraSuperior.getChildren().add(fechaf);
            barraSuperior.getChildren().add(fechaFinal);
            barraSuperior.getChildren().add(btBuscar);
            
            BorderPane menu = new BorderPane();
            TableView<VentaStringProperties> tablaVentas= new TableView<VentaStringProperties>();
            BorderPane.setAlignment(tablaVentas, Pos.CENTER);
            BorderPane.setMargin(tablaVentas, new Insets(12,0,0,100));
            tablaVentas.columnResizePolicyProperty();
            ArrayList<Venta> ventasSinFiltrar = leerVentas();
            ObservableList<VentaStringProperties> ventas = FXCollections.observableArrayList();;
            System.out.println(ventasSinFiltrar);
            for(Venta v:ventasSinFiltrar){
                VentaStringProperties v1 = new VentaStringProperties(v);
                System.out.println(v1);
                ventas.add(v1);
                System.out.println(ventas);
            }
            
            TableColumn<VentaStringProperties,String> FechaCol = new TableColumn<VentaStringProperties,String>("fecha");
            FechaCol.setCellValueFactory(new PropertyValueFactory("fecha"));
            TableColumn<VentaStringProperties,String> MesaCol = new TableColumn<VentaStringProperties,String>("numeroMesa");
            MesaCol.setCellValueFactory(new PropertyValueFactory("numeroMesa"));
            TableColumn<VentaStringProperties,String> MeseroCol = new TableColumn<VentaStringProperties,String>("mesero");
            MeseroCol.setCellValueFactory(new PropertyValueFactory("mesero"));
            TableColumn<VentaStringProperties,String> nombreClienteCol = new TableColumn<VentaStringProperties,String>("nombreCliente");
            nombreClienteCol.setCellValueFactory(new PropertyValueFactory("nombreCliente"));
            TableColumn<VentaStringProperties,String> totalCol = new TableColumn<VentaStringProperties,String>("total");
            totalCol.setCellValueFactory(new PropertyValueFactory("total"));
            
            tablaVentas.getColumns().setAll(FechaCol,MesaCol,MeseroCol,nombreClienteCol,totalCol);
            tablaVentas.setItems(ventas);
            
            
            
            menu.setTop(barraSuperior);
            menu.setCenter(tablaVentas);
            //menu.getChildren().add(tablaVentas);
            fpPantallaAdmin.getChildren().add(menu);
            
        }catch(IOException ex){
            System.out.println("Problemas técnicos");
        }
        
    }

}
