/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pooespol.apprestaurant;

import com.pooespol.apprestaurant.data.ComidaData;
import com.pooespol.apprestaurant.data.TipoComidaData;
import com.pooespol.apprestaurant.data.VentaData;
import static com.pooespol.apprestaurant.data.VentaData.leerVentas;
import static com.pooespol.apprestaurant.data.VentaData.leerVentasPorFecha;
import com.pooespol.apprestaurant.modelo.Restaurant;
import static com.pooespol.apprestaurant.modelo.Restaurant.restaurant;
import static com.pooespol.apprestaurant.modelo.Restaurant.toLocalDate;
import com.pooespol.apprestaurant.modelo.Venta;
import com.pooespol.apprestaurant.modelo.comida.Comida;
import com.pooespol.apprestaurant.modelo.comida.TipoComida;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    

    @FXML
    private Button btGestionMenu;
    @FXML
    private  FlowPane fpPantallaAdmin;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btReportesVentas;
    
    private TextField fechaInicio= new TextField();
    private TextField fechaFinal=new TextField();;
    private HBox barraSuperior;
    private Button btBuscar;
    private BorderPane menu;
    private TableView<VentaStringProperties> tablaVentas;
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
        
        MostrarGestionMenu();
    }
    public void MostrarGestionMenu(){
       
       fpPantallaAdmin.getChildren().clear();
        
        try{
            //System.out.println("aqui 1 ");
           ArrayList<Comida> comidas = ComidaData.leerComida(); 
           //System.out.println("aqui 2 ");
           for (Comida c: comidas){
               //vbox con imagen,nombre, precio, boton
               VBox vboxmenu = new VBox();
               vboxmenu.setAlignment(Pos.CENTER);
               //la imagen
                InputStream inputImg= App.class.getResource(c.getRutaImagen()).openStream();
                ImageView imgv = new ImageView(new Image(inputImg));
                vboxmenu.getChildren().add(imgv);
                
                
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
                   try {
                       // System.out.println(c);
                       
                       PantallaEditarPlato(c,true);
                       /*
                       c.setNombre(newComida.getNombre());
                       c.setPrecio(newComida.getPrecio());
                       c.setTipoComida(newComida.getTipoComida());*/
                   } catch (IOException ex) {
                       ex.printStackTrace();
                   }
                       }
               );
           }         
           Button btagregar = new Button("Click para agregar nuevo plato");
           fpPantallaAdmin.getChildren().add(btagregar);
           btagregar.setAlignment(Pos.CENTER);
           btagregar.setOnMouseClicked(
              (MouseEvent) ->{
               try {
                   Comida comida = new Comida();
                   PantallaEditarPlato(comida,false);
                   /*
                   Restaurant.añadirComida(newComida);
                   ComidaData.escribirComida(newComida);
                   System.out.println(newComida);
                   System.out.println(Restaurant.getComidas());*/
               } catch (IOException ex) {
                   ex.printStackTrace();
               }   
              }
           );
           
        }catch(IOException ex){
            System.out.println("Problemas técnicos");
        }
    }
    public void PantallaEditarPlato(Comida comida, boolean editar) throws IOException {
           
           //System.out.println(comida);
           fpPantallaAdmin.getChildren().clear();
            //container principal
            fpPantallaAdmin.setAlignment(Pos.CENTER);
            VBox vcontainer = new VBox();
            vcontainer.setAlignment(Pos.CENTER);
            vcontainer.setSpacing(40);
            
            Label lnombre = new Label("Nombre:");
            TextField txtNombre =new TextField();
            HBox hNombre = new HBox(lnombre,txtNombre);
            hNombre.setAlignment(Pos.CENTER);
            hNombre.setSpacing(30);
            
            Label lprecio = new Label("Precio");
            TextField txtPrecio = new TextField();
            HBox hPrecio = new HBox(lprecio,txtPrecio);
            hPrecio.setAlignment(Pos.CENTER);
            hPrecio.setSpacing(30);
            
            Label lTipo = new Label("Tipo");
            ComboBox<TipoComida> cbTipo = new ComboBox();
            List<TipoComida> tipos = TipoComidaData.leerTipoComida();
            cbTipo.getItems().addAll(tipos);
            HBox hTipo = new HBox(lTipo,cbTipo);
            hTipo.setAlignment(Pos.CENTER);
            hTipo.setSpacing(30);
            
            if (editar==true){
                txtNombre.setText(comida.getNombre());
                txtPrecio.setText(String.valueOf(comida.getPrecio()));
            }
    
            Button btnRegresar = new Button("Regresar");
            Button btnLimpiar = new Button("Limpiar");
            Button btnGuardar = new Button("Guardar");
            HBox hBtn=new HBox(btnRegresar,btnLimpiar,btnGuardar);
            hBtn.setSpacing(20);
            Label lblMessage = new Label();
            lblMessage.setAlignment(Pos.CENTER);
            vcontainer.getChildren().addAll(hNombre,hPrecio,hTipo,hBtn,lblMessage);
            fpPantallaAdmin.getChildren().add(vcontainer);
            //MANEJADORES PARA LOS BOTONES GUARDAR, LIMPIAR, REGRESAR
            btnGuardar.setOnMouseClicked(
            (MouseEvent)->{
            try{
               String nombre =txtNombre.getText();
               double precio =Double.parseDouble(txtPrecio.getText());
               TipoComida tipo = cbTipo.getValue();

               if ( nombre.equals("") || tipo.equals("")){
               throw new NullPointerException();
               }
               
               comida.setNombre(nombre);
               comida.setPrecio(precio);
               comida.setTipoComida(tipo);
               if (editar == false){
                   //System.out.println(Restaurant.getComidas());
                   Restaurant.añadirComida(comida);
                   ComidaData.escribirComida(comida);
                   //System.out.println(comida);
                   //System.out.println(Restaurant.getComidas());
                   //se escribe la nueva linea de comida
               }else{
                   //se escribe todo el contenido de nuevo o se borra la linea y se la reemplaza
                   //System.out.println(comida);
               }
               
               lblMessage.setText("Cambios guardados exitosamente");
               //System.out.println(comida);

               }catch(NullPointerException ex){
                   lblMessage.setText("Algo ocurrió. No se han guardado cambios");
               }catch(InputMismatchException ex){
                   lblMessage.setText("Formato no válido. No se han guardado cambios");
               }catch(NumberFormatException ex){
                   lblMessage.setText("Formato no válido. No se han guardado cambios");

               }    
            }
            
            );
            
            btnRegresar.setOnMouseClicked((MouseEvent)->{
                
                MostrarGestionMenu();
            });
            
            btnLimpiar.setOnMouseClicked((MouseEvent)->{
                txtNombre.setText("");
                txtPrecio.setText("");
                cbTipo.getItems().clear();
                cbTipo.getItems().addAll(tipos);
                lblMessage.setText("");
            });
           
            
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
            //TextField fechaInicio= new TextField();
            fechaInicio.setPadding(new Insets(0,30,0,10));
            //TextField fechaFinal= new TextField();
            fechaFinal.setPadding(new Insets(0,30,0,10));
            Button btBuscar= new Button("Buscar");
            btBuscar.addEventHandler(MouseEvent.MOUSE_CLICKED, FiltrarVentas);
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
        }}
        EventHandler<MouseEvent> FiltrarVentas = new EventHandler<MouseEvent>(){  
        public void handle(MouseEvent event){
                LocalDate FechaI= toLocalDate(fechaInicio.getText());
                LocalDate FechaF= toLocalDate(fechaFinal.getText());
                try {
                    ArrayList<Venta> filtrada = leerVentasPorFecha(FechaI, FechaF);
                    ObservableList<VentaStringProperties> ventas = FXCollections.observableArrayList();;
                    for(Venta v:filtrada){
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
                } catch (IOException ex) {
                    ex.printStackTrace();
                    }
                }};
            
        
        
    }


