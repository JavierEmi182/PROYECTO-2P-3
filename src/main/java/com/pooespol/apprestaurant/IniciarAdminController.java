/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pooespol.apprestaurant;

import com.pooespol.apprestaurant.data.ComidaData;
import com.pooespol.apprestaurant.IniciarAdminController.cargarMesasRunnable;
import static com.pooespol.apprestaurant.data.ComidaData.reescribirComidas;
import com.pooespol.apprestaurant.data.MesasData;
import com.pooespol.apprestaurant.data.TipoComidaData;
import com.pooespol.apprestaurant.data.VentaData;
import static com.pooespol.apprestaurant.data.VentaData.leerVentas;
import static com.pooespol.apprestaurant.data.VentaData.leerVentasPorFecha;
import com.pooespol.apprestaurant.modelo.Mesa;
import com.pooespol.apprestaurant.modelo.Restaurant;
import static com.pooespol.apprestaurant.modelo.Restaurant.mesas;
import static com.pooespol.apprestaurant.modelo.Restaurant.restaurant;
import static com.pooespol.apprestaurant.modelo.Restaurant.toLocalDate;
import static com.pooespol.apprestaurant.modelo.Restaurant.ventas;
import com.pooespol.apprestaurant.modelo.Venta;
import com.pooespol.apprestaurant.modelo.comida.Comida;
import com.pooespol.apprestaurant.modelo.comida.TipoComida;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.interfaces.XECKey;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

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
    @FXML
    private Button btmonitoreo;
    @FXML
    private Button btDiseñoPlano;
    
    public static boolean pausarHilo=false;
    public static boolean detenerHilo;
    
    public static boolean click=false;
    public static boolean drag=false;
    public static boolean clickEliminarEditar=false;
    String opcion;
    @FXML
    private VBox vb1;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fpPantallaAdmin.getChildren().clear();
        cargarVentas();
        //fpPantallaAdmin.getChildren().clear();
        
        //fpPantallaAdmin.setPadding(new Insets(0,0,50,0));
        
        //BorderPane.setMargin(fpPantallaAdmin,new Insets(0,0,25,0)); //480
        
       /* try {
            CargarMesasSinEventos();
            //CargarMesas(fpPantallaAdmin);
        } catch (IOException ex) {
            ex.printStackTrace();
        }*/
        
        
        Thread t = new Thread(new cargarMesasRunnable());
        t.start();
        if(pausarHilo){
            t.stop();
        }else{
            t.resume();
            //t.notify();
        }
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
       vb1.getChildren().clear();
       pausarHilo=true;
        try{
            //System.out.println("aqui 1 ");
           ArrayList<Comida> comidas = ComidaData.leerComida("comida.txt"); 
           //System.out.println("aqui 2 ");
           for (Comida c: comidas){
               //vbox con imagen,nombre, precio, boton
               VBox vboxmenu = new VBox();
               vboxmenu.setAlignment(Pos.CENTER);
               //la imagen
                InputStream inputImg= App.class.getResource(c.getRutaImagen()).openStream();
               
                ImageView imgv = new ImageView(new Image(inputImg));
                Button bteditar = new Button("EDITAR");
                vboxmenu.getChildren().add(bteditar);
                vboxmenu.getChildren().add(imgv);
                
                
                Label lnombre = new Label(c.getNombre());
              
                vboxmenu.getChildren().add(lnombre);
                //anio
                Label lprecio = new Label("$"+String.valueOf(c.getPrecio()));
                vboxmenu.getChildren().add(lprecio);
                vboxmenu.setPadding(new Insets(2,4,2,4));
                  
                
                fpPantallaAdmin.getChildren().add(vboxmenu);
                vboxmenu.setSpacing(5);
               bteditar.setOnMouseClicked ( 
                       (MouseEvent) -> {
                   try {
                      
                       
                       PantallaEditarPlato(c);
                       
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
                 PantallaCrearPlato();
              }
           
           );
        }catch(IOException ec){
            
        }   
        //pausarHilo=false;
    }
    public void PantallaEditarPlato(Comida comida) throws IOException {
        pausarHilo=true;
     //   fpPantallaAdmin.getChildren().remove(vb1);
         //  System.out.println(comida);
        //   System.out.println(Restaurant.getComidas());
           
           fpPantallaAdmin.getChildren().clear();
           vb1.getChildren().clear();
           fpPantallaAdmin.setAlignment(Pos.CENTER);
            //container principal
            
            
            VBox vcontainer = new VBox();
            vcontainer.setAlignment(Pos.CENTER);
            vcontainer.setSpacing(15);
            
            InputStream inputImg= App.class.getResource(comida.getRutaImagen()).openStream();
            ImageView imgv = new ImageView(new Image(inputImg));
            
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
            TextField txtTipo = new TextField("Tipo de Comida");
            HBox hTipo = new HBox(lTipo,txtTipo);
            hTipo.setAlignment(Pos.CENTER);
            hTipo.setSpacing(30);
            
            Label cambiarTipo = new Label("Seleccione para cambiar el tipo de comida");
            ComboBox<TipoComida> cbtTipo = new ComboBox();
            List<TipoComida> tipos = TipoComidaData.leerTipoComida();  
            cbtTipo.setItems(FXCollections.observableArrayList(tipos));
            //cbtTipo.getItems().addAll(tipos);
            HBox cambiarhbox = new HBox(cambiarTipo,cbtTipo);
            cambiarhbox.setAlignment(Pos.CENTER);
            cambiarhbox.setSpacing(30);
            /*
            cbtTipo.setOnMouseClicked((ActionEvent)->{
            String sc=cbtTipo.getValue().toString();
            txtTipo.setText(sc);
            });*/
            
            txtNombre.setText(comida.getNombre());
            txtPrecio.setText(String.valueOf(comida.getPrecio()));
            txtTipo.setText(comida.getTipoComida().getNombre());
    
            Button btnRegresar = new Button("Regresar");
            Button btnLimpiar = new Button("Limpiar");
            Button btnGuardar = new Button("Guardar");
            HBox hBtn=new HBox(btnRegresar,btnLimpiar,btnGuardar);
            hBtn.setSpacing(20);
            Label lblMessage = new Label();
            lblMessage.setAlignment(Pos.CENTER);
            vcontainer.getChildren().addAll(imgv,hNombre,hPrecio,hTipo,cambiarhbox,hBtn,lblMessage);
            fpPantallaAdmin.getChildren().add(vcontainer);
            //MANEJADORES PARA LOS BOTONES GUARDAR, LIMPIAR, REGRESAR
            btnGuardar.setOnMouseClicked(
            (MouseEvent)->{
            try{
               //String sc=cbtTipo.getValue().toString();
               //txtTipo.setText(sc);
               Restaurant.getComidas().remove(comida);
               String nombre =txtNombre.getText();
               double precio =Double.parseDouble(txtPrecio.getText());
               String tipo="";
               if(cbtTipo.getValue()!=null){
                   txtTipo.setText(cbtTipo.getValue().toString());
                   tipo =cbtTipo.getValue().toString();
               }else{
                   tipo= txtTipo.getText();
               }
                      
                

               if ( nombre.equals("") || tipo.equals("")){
               throw new NullPointerException();
               }
               
               comida.setNombre(nombre);
               comida.setPrecio(precio);
               comida.setTipoComida(new TipoComida(tipo));
               Restaurant.añadirComida(comida);
               ComidaData.reescribirComidas(Restaurant.getComidas(),"comida.txt");
               
               System.out.println(comida);
               System.out.println(Restaurant.getComidas());
               
               lblMessage.setText("Cambios guardados exitosamente");
               //System.out.println(comida);

               }catch(NullPointerException ex){
                   lblMessage.setText("Algo ocurrió. No se han guardado cambios");
               }catch(InputMismatchException ex){
                   lblMessage.setText("Formato no válido. No se han guardado cambios");
               }catch(NumberFormatException ex){
                   lblMessage.setText("Formato no válido. No se han guardado cambios");

               } catch (IOException ex) {
                   ex.printStackTrace();
               } catch (URISyntaxException ex) {
                   ex.printStackTrace();
               }    
            }
            
            );
            
            btnRegresar.setOnMouseClicked((MouseEvent)->{
                
                MostrarGestionMenu();
            });
            
            btnLimpiar.setOnMouseClicked((MouseEvent)->{
                txtNombre.setText("");
                txtPrecio.setText("");
                txtTipo.setText("");
                cbtTipo.getItems().clear();
               cbtTipo.getItems().addAll(tipos);
                
                lblMessage.setText("");
            });
           
         //pausarHilo=false;   
    }
    public void PantallaCrearPlato(){
        try {
            pausarHilo=true;
            fpPantallaAdmin.getChildren().clear();
            vb1.getChildren().clear();
            fpPantallaAdmin.getChildren().remove(vb1);
            //principal container
            fpPantallaAdmin.setAlignment(Pos.CENTER);
            
            VBox vcontainer = new VBox();
            
            vcontainer.setAlignment(Pos.CENTER);
            vcontainer.setSpacing(40);
            
            Label ltitulo = new Label ("Busque la comida que desea agregar");
            
            ComboBox<Comida> cbComida= new ComboBox();
            List<Comida> comidaPorAgregar = ComidaData.leerComida("comidaSinAgregar.txt");
            //cbComida.getItems().addAll(comidaPorAgregar);
            cbComida.setItems(FXCollections.observableArrayList(comidaPorAgregar));
            Button bcomida = new Button("Buscar");
            HBox hcomida = new HBox(cbComida,bcomida);
            hcomida.setSpacing(20);
            hcomida.setAlignment(Pos.CENTER);
            Pane bpane = new Pane();
            /*bpane.setMaxWidth(fpPantallaAdmin.getMaxWidth());
            bpane.setMaxHeight(fpPantallaAdmin.getMaxHeight()-20);*/
           Button btnRegresar = new Button("Regresar");
            Button btnGuardar = new Button("Agregar Plato");
            HBox btns = new HBox(btnRegresar,btnGuardar);
            btns.setSpacing(30);
             btns.setAlignment(Pos.BOTTOM_CENTER);
             Label lmessage = new Label();
            //vcontainer.getChildren().add(btns);
            vcontainer.getChildren().addAll(ltitulo,hcomida,bpane,btns,lmessage);
            cbComida.setOnAction((ActionEvent)->{
               bpane.getChildren().clear();
            });
            bpane.getChildren().clear();
            bcomida.setOnMouseClicked((MouseEvent)->{
                
                HBox hinfo = new HBox();
                //hinfo.getChildren().clear();
               Comida c = cbComida.getValue();
               String ruta ="";
               for (Comida comida: comidaPorAgregar){
                   if (c.equals(comida)){
                       ruta = c.getRutaImagen();
                   }
               }
               InputStream inputImg;
                try {
                    inputImg = App.class.getResource(ruta).openStream();
                    ImageView imgv = new ImageView(new Image(inputImg));
                    Label linfo = new Label("Nombre: "+c.getNombre()+"\nPrecio: "+c.getPrecio()+"\nTipo de comida: "+c.getTipoComida());
                   linfo.setAlignment(Pos.CENTER);
                    hinfo.getChildren().addAll(imgv,linfo);
                    hinfo.setSpacing(25);
                    bpane.getChildren().add(hinfo);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
               
               
            });
            
            fpPantallaAdmin.getChildren().add(vcontainer);
            btnRegresar.setOnAction((MouseEvent)->{
                MostrarGestionMenu();
            });
            btnGuardar.setOnAction((MouseEvent)->{
                //agregar al array restaurante
                
                Comida comidanew = cbComida.getValue();
                if (comidanew!=null){
                   
                   Restaurant.añadirComida(comidanew);
                   
                    try {
                        //ComidaData.escribirComida(comidanew); esta funcion da problemas, se reescribe todo nomas
                        ComidaData.reescribirComidas(Restaurant.getComidas(),"comida.txt");
                        //borrar del array de comidas por añadir
                        Restaurant.borrarComidaInventario(comidanew);
                    //reescribir el archivo "comidas sin agregar" pasarle un arraylisy
                    ComidaData.reescribirComidas(Restaurant.getComidasInventario(),"comidaSinAgregar.txt");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                    
                    lmessage.setText("Nueva comida registrada");
                    
                }else{
                    lmessage.setText("Debe seleccionar una comida");
                }
                //eliminarlo de comida sin agregar 
            });
            
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //pausarHilo=false;
    }

    @FXML
    private void regresarPrincipalAdmin(MouseEvent event) throws IOException{
        App.setRoot("Login");
    }

    @FXML
    private void ReportesVentas(MouseEvent event) {
        cargarVentas();
    }
        public void cargarVentas(){
        pausarHilo=true;
        fpPantallaAdmin.getChildren().clear();
        vb1.getChildren().clear();
        
        try{
            HBox barraSuperior= new HBox();
            barraSuperior.setAlignment(Pos.CENTER);
            //BorderPane.setMargin(barraSuperior, new Insets(60,0,20,30));  //12    47   
            
            Label fechai = new Label("Fecha Inicio: ");
            fechai.setPadding(new Insets(0,10,0,30));
            Label fechaf = new Label("Fecha final: ");
            fechaf.setPadding(new Insets(0,30,0,10));
            //TextField fechaInicio= new TextField();
            fechaInicio.setText("dd/mm/yyyy");
            fechaFinal.setText("dd/mm/yyyy");
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
            tablaVentas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            ArrayList<Venta> ventasSinFiltrar = leerVentas();
            ObservableList<VentaStringProperties> ventas = FXCollections.observableArrayList();;
            //System.out.println(ventasSinFiltrar);
            for(Venta v:ventasSinFiltrar){
                VentaStringProperties v1 = new VentaStringProperties(v);
                //System.out.println(v1);
                ventas.add(v1);
                //System.out.println(ventas);
            }
            
            TableColumn<VentaStringProperties,String> FechaCol = new TableColumn<VentaStringProperties,String>("fecha");
            FechaCol.setCellValueFactory(new PropertyValueFactory("fecha"));
            
            TableColumn<VentaStringProperties,String> NumCol = new TableColumn<VentaStringProperties,String>("numeroCuenta");
            NumCol.setCellValueFactory(new PropertyValueFactory("numeroCuenta"));
            
            TableColumn<VentaStringProperties,String> MesaCol = new TableColumn<VentaStringProperties,String>("numeroMesa");
            MesaCol.setCellValueFactory(new PropertyValueFactory("numeroMesa"));
            TableColumn<VentaStringProperties,String> MeseroCol = new TableColumn<VentaStringProperties,String>("mesero");
            MeseroCol.setCellValueFactory(new PropertyValueFactory("mesero"));
            TableColumn<VentaStringProperties,String> nombreClienteCol = new TableColumn<VentaStringProperties,String>("nombreCliente");
            nombreClienteCol.setCellValueFactory(new PropertyValueFactory("nombreCliente"));
            TableColumn<VentaStringProperties,String> totalCol = new TableColumn<VentaStringProperties,String>("total");
            totalCol.setCellValueFactory(new PropertyValueFactory("total"));
            
            tablaVentas.getColumns().setAll(FechaCol,NumCol,MesaCol,MeseroCol,nombreClienteCol,totalCol);
            tablaVentas.setItems(ventas);
            
            
            
            menu.setTop(barraSuperior);
            menu.setCenter(tablaVentas);
            //menu.getChildren().add(tablaVentas);
            fpPantallaAdmin.getChildren().add(menu);
            System.out.println(ventas.size());
            }catch(IOException ex){
            System.out.println("Problemas técnicos");
        }catch(java.time.format.DateTimeParseException ex1){
                        System.out.println("porfavor ingrese un formato correcto");
        }catch(java.lang.NumberFormatException ex4){
            
        }
        //pausarHilo=false;
    }
        EventHandler<MouseEvent> FiltrarVentas = new EventHandler<MouseEvent>(){  
        public void handle(MouseEvent event){
            vb1.getChildren().clear();
            fpPantallaAdmin.getChildren().clear();
            
            HBox barraSuperior= new HBox();
            barraSuperior.setAlignment(Pos.CENTER);
            //BorderPane.setMargin(barraSuperior, new Insets(35,0,20,30));
            //barraSuperior.setPadding(new Insets(25,0,0,0));
            fpPantallaAdmin.setPadding(new Insets(0,0,25,0));
            
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
            
            //<columnResizePolicy><TableView fx:constant="CONSTRAINED_RESIZE_POLICY"/></columnResizePolicy>
            tablaVentas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            
                LocalDate FechaI= toLocalDate(fechaInicio.getText());
                LocalDate FechaF= toLocalDate(fechaFinal.getText());
                try {
                    ArrayList<Venta> filtrada = leerVentasPorFecha(FechaI, FechaF);
                    ObservableList<VentaStringProperties> ventas = FXCollections.observableArrayList();;
                    for(Venta v:filtrada){
                        VentaStringProperties v1 = new VentaStringProperties(v);
                        //System.out.println(v1);
                        ventas.add(v1);
                        //System.out.println(ventas);
                    }
                    
                    TableColumn<VentaStringProperties,String> FechaCol = new TableColumn<VentaStringProperties,String>("fecha");
                    FechaCol.setCellValueFactory(new PropertyValueFactory("fecha"));
                    TableColumn<VentaStringProperties,String> NumCol = new TableColumn<VentaStringProperties,String>("numeroCuenta");
                    NumCol.setCellValueFactory(new PropertyValueFactory("numeroCuenta"));
                    TableColumn<VentaStringProperties,String> MesaCol = new TableColumn<VentaStringProperties,String>("numeroMesa");
                    MesaCol.setCellValueFactory(new PropertyValueFactory("numeroMesa"));
                    TableColumn<VentaStringProperties,String> MeseroCol = new TableColumn<VentaStringProperties,String>("mesero");
                    MeseroCol.setCellValueFactory(new PropertyValueFactory("mesero"));
                    TableColumn<VentaStringProperties,String> nombreClienteCol = new TableColumn<VentaStringProperties,String>("nombreCliente");
                    nombreClienteCol.setCellValueFactory(new PropertyValueFactory("nombreCliente"));
                    TableColumn<VentaStringProperties,String> totalCol = new TableColumn<VentaStringProperties,String>("total");
                    totalCol.setCellValueFactory(new PropertyValueFactory("total"));
            
                    tablaVentas.getColumns().setAll(FechaCol,NumCol,MesaCol,MeseroCol,nombreClienteCol,totalCol);
                    tablaVentas.setItems(ventas);
            
            
            
                    menu.setTop(barraSuperior);
                    menu.setCenter(tablaVentas);
                    //menu.getChildren().add(tablaVentas);
                    fpPantallaAdmin.getChildren().add(menu);
                    System.out.println(ventas.size());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    }catch(java.time.format.DateTimeParseException ex1){
                        System.out.println("porfavor ingrese un formato correcto");
                    }
                }
        ;
        };

    
    private void CreacionMesas0(MouseEvent event) {
        vb1.getChildren().clear();
        ArrayList<String> listaAcciones= new ArrayList<String>();
        
        listaAcciones.add("Agregar mesas");
        listaAcciones.add("Editar o Eliminar");
        listaAcciones.add("Mover");
        
        ComboBox cbAccion = new ComboBox();
        cbAccion.getItems().addAll(listaAcciones);
        
        pausarHilo=true;
        fpPantallaAdmin.getChildren().clear();
        //Pane pane =CargarMesas(fpPantallaAdmin);
        Pane pane =CargarMesas();
        pane.setPrefHeight(fpPantallaAdmin.getHeight());
        pane.setPrefWidth(fpPantallaAdmin.getWidth());
        vb1.getChildren().add(cbAccion);
        cbAccion.setOnAction((ActionEvent)->{
            if (cbAccion.getValue()=="Editar o Eliminar"){
            clickEliminarEditar=true;
            click=false;
            drag=false;
        }else if (cbAccion.getValue()=="Mover"){
            clickEliminarEditar=false;
            click=false;
            drag=true;
        }else if(cbAccion.getValue()=="Agregar Mesa"){
            clickEliminarEditar=false;
            click=true;
            drag=false;
        }
  
        if(click){
            
            pane.setOnMouseClicked((MouseEvent) -> {
                
            click=true;
            clickEliminarEditar=false;
            drag=false;
            
            VBox cuadro1 =  new VBox();
            
            Stage stage = new Stage();
            
            //try{ 
            
            cuadro1.setAlignment(Pos.CENTER);

            
            Label etiqueta3 = new Label("INGRESE CAPACIDAD");
            TextField capacidad = new TextField("2");
            HBox h3 = new HBox(etiqueta3,capacidad);
            h3.setSpacing(30);
            Label lbMensaje= new Label();
            Label lbMensaje1= new Label();
            
            Button boton = new Button("CREAR");
            cuadro1.getChildren().addAll(h3,boton);
            cuadro1.setSpacing(30);
            cuadro1.setPadding(new Insets(7,7,7,7));
            Scene ventana = new Scene(cuadro1);
            stage.setScene(ventana);
            stage.setWidth(400);
            stage.setHeight(400);
            
                        
            
            
            if (click){
                stage.show();
            }
            Point2D posicion = new Point2D(MouseEvent.getSceneX(), MouseEvent.getSceneY());
            double posicionx = posicion.getX();
            double posiciony = posicion.getY();
            
            boton.setOnMouseClicked((MouseEvent e)->{
                lbMensaje1.setText("");
                //try {
                        if(Integer.parseInt(capacidad.getText())<=0){
                            throw new NumberFormatException();  
                        }
                        boolean condicionDistancia=false;
                        
                int contador=0;
                for(Mesa m:mesas){
                    Point2D posm= new Point2D(m.getX()+50,m.getY()+92);
                    posicion.add(-50, -85);
                    
                    if(posm.distance(posicion)>125){
                     condicionDistancia=true;
                     contador+=1;
                     System.out.println(posm);
                     System.out.println(posm.distance(posicion));
                     
                    }
                    
                    }
                    System.out.println(contador);
                    System.out.println(mesas.size());
                    System.out.println(posicion);          
                if(condicionDistancia&&(contador==mesas.size())){
                    
                Restaurant.mesas.add(new Mesa(Restaurant.mesas.size()+1,Integer.parseInt(capacidad.getText()), false, posicionx-50,posiciony-85));
                System.out.println(mesas);
                try {
                            MesasData.escribirMesas(mesas, "mesas.txt");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } catch (URISyntaxException ex) {
                            ex.printStackTrace();
                        }
                        
                Ellipse elipse = new Ellipse(50,50);
                elipse.setFill(Color.YELLOW);
                String n = String.valueOf(Restaurant.mesas.size());
                Label numeromesa = new Label(n);
                StackPane st = new StackPane();
                st.getChildren().addAll(elipse,numeromesa);                  
                
                Platform.runLater( ()->{
                    
                    fpPantallaAdmin.getChildren().clear();
                    pane.getChildren().add(st);
                    st.setLayoutX(posicionx-50);
                    st.setLayoutY(posiciony-85);
                    
                    fpPantallaAdmin.getChildren().add(pane);
                    
                    
                 });
                
                stage.close();
                }else{
                        lbMensaje.setText("Porfavor ingrese la mesa mas distanciada de las otras");
                        cuadro1.getChildren().add(lbMensaje);
                    }});
            });
            click=false;
            clickEliminarEditar=false;
            drag=false;
            
        }
        else if(clickEliminarEditar){
            pane.setOnMouseClicked((MouseEvent) -> {
            click=false;
            clickEliminarEditar=true;
            drag=false;
            
            
            Point2D posicion = new Point2D(MouseEvent.getSceneX(), MouseEvent.getSceneY());
            double posicionx = posicion.getX();
            double posiciony = posicion.getY();
            
            boolean condicionDistancia=false;
            boolean condicionClickMesa=false;
                int contador=0;
                for(Mesa m:mesas){
                    if(m!=null){
                    Point2D posm= new Point2D(m.getX()+50,m.getY()+92);
                    posicion.add(-50, -85);
                    
                    if(posm.distance(posicion)<=25){
                        condicionClickMesa=true;
                    }
                    
                    else if(posm.distance(posicion)>125){
                     condicionDistancia=true;
                     contador+=1;
                     System.out.println(posm);
                     System.out.println(posm.distance(posicion));
                     
                    }
                }
                    }
                    System.out.println(contador);
                    System.out.println(mesas.size());
                    System.out.println(posicion);          
                
                //stage.close();
                if(condicionClickMesa){
                
                VBox vb=new VBox();
                Stage stageEditar = new Stage();
                
                Label lbNuevaCapacidad= new Label("Ingrese nueva capacidad");
                TextField tfEditarCapacidad= new TextField();
                HBox hb = new HBox();
                Button btEditar= new Button("Editar");
                Button btEliminar = new Button("Eliminar");
                hb.getChildren().add(btEditar);
                hb.getChildren().add(btEliminar);
                vb.getChildren().add(lbNuevaCapacidad);
                vb.getChildren().add(tfEditarCapacidad);
                vb.getChildren().add(hb);
                vb.setAlignment(Pos.CENTER);
                hb.setAlignment(Pos.CENTER);
                
                vb.setSpacing(30);
                vb.setPadding(new Insets(7,7,7,7));
                Scene vtEditar = new Scene(vb);
                stageEditar.setScene(vtEditar);
                stageEditar.setWidth(400);
                stageEditar.setHeight(400);
                stageEditar.show();
                
                btEditar.setOnMouseClicked((MouseEvent e2)->{
                    int capacidadNueva= Integer.parseInt(tfEditarCapacidad.getText());
                    for(Mesa m:mesas){
                        double x = m.getX()+50;
                        double y = m.getY()+92;
                        Point2D ptmesa= new Point2D(x,y);
                        if(ptmesa.distance(posicion)<25){
                            System.out.println(m);
                            System.out.println(mesas);
                            m.setCapacidad(capacidadNueva);
                            System.out.println(m);
                            System.out.println(mesas);
                        }
                        stageEditar.close();
                    }
                    try {
                        MesasData.escribirMesas(mesas, "mesas.txt");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                });
                
                btEliminar.setOnMouseClicked((MouseEvent e3)->{
                    boolean borrar=false;
                    Mesa mesaBorrar=null;
                    for(Mesa m:mesas){
                        if(m!=null){
                        double x = m.getX()+50;
                        double y = m.getY()+92;
                        Point2D ptmesa= new Point2D(x,y);
                        if(ptmesa.distance(posicion)<25){
                            
                            
                                //Platform.runLater( ()->{
                                    //try{
                                    borrar=true;
                                    mesaBorrar=m;
                                    //MesasData.escribirMesas(mesas, "mesas.txt");
                                    //CargarMesas();
                                    //}catch(IOException ex){
                                    //    System.out.println("Problemas tecnicos");
                                    //}catch(URISyntaxException ex2){
                                //        System.out.println("Problemas tecnicos");
                                    //}
                                //});
                                
                        }
                        stageEditar.close();
                    }}
                    if(borrar&&(mesaBorrar!=null)){
                    mesas.remove(mesaBorrar);
                    }
                    try {
                        MesasData.escribirMesas(mesas, "mesas.txt");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        System.out.println("Aqui1");
                    } catch (URISyntaxException ex) {
                        ex.printStackTrace();
                        System.out.println("Aqui2");
                    }
                    
                    Platform.runLater( ()->{
                    fpPantallaAdmin.getChildren().clear();
                        try {
                            CargarMesasSinEventos(true);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    });
                    
                });
                
                
                }
                
                    
        });
            click=false;
            clickEliminarEditar=false;
            drag=false;
        }//aqui comence
        else if(drag){
            pane.setOnMouseClicked((MouseEvent) -> {
            click=false;
            clickEliminarEditar=false;
            drag=true;
            
            Point2D posicion = new Point2D(MouseEvent.getSceneX(), MouseEvent.getSceneY());
            double posicionx = posicion.getX();
            double posiciony = posicion.getY();
            
            int numMesa=0;
            boolean ocupada=false;
            Mesa m0 =null;
            
            //ArrayList
            
                for(Mesa m:mesas){
                    if(m!=null){
                        double x = m.getX()+50;
                        double y = m.getY()+92;
                        Point2D ptmesa= new Point2D(x,y);
                        ocupada=m.isOcupada();
                        if(ptmesa.distance(posicion)<25){
                            
                            //m.getCapacidad();
                            numMesa=m.getNumero();
                            m0=m;
                            
                        }
                    }}
                
                mesas.add(m0);
                
                try {
                                MesasData.escribirMesas(mesas, "mesas.txt");
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            } catch (URISyntaxException ex) {
                                ex.printStackTrace();
                            }   
                Ellipse elipse = new Ellipse(50,50);
                
                if(ocupada){
                    elipse.setFill(Color.RED);
                }else{
                    elipse.setFill(Color.YELLOW);
                }
                
                
                String n = String.valueOf(numMesa);
                
                Label numeromesa = new Label(n);
                StackPane st = new StackPane();
                st.getChildren().addAll(elipse,numeromesa);                  
                
                int z=numMesa;
                Platform.runLater( ()->{
                    
                    fpPantallaAdmin.getChildren().clear();
                    //pane.getChildren().add(st);
                    //st.setLayoutX(posicionx-50);
                    //st.setLayoutY(posiciony-85);
                    
                    
                    ArrastrarMesas(st, z);
                    fpPantallaAdmin.getChildren().clear();
                    fpPantallaAdmin.getChildren().add(pane);
                    
                    
                 });
                
                }
            );
            click=false;
            clickEliminarEditar=false;
            drag=false;
        }
        //vb1.getChildren().add(cbAccion);
        });
        
        
    }   
    //Evento Arrastrar
    
    public void ArrastrarMesas(StackPane sp, int nmesa){
        
        sp.setOnMouseDragged((MouseEvent ev) -> {
            double x = ev.getSceneX()-50;
            //click=false;
            double y = ev.getSceneY()-85;
         //   sp.setLayoutX(x);
        //    sp.setLayoutY(y);
            Mesa mEliminar = null;
            for(Mesa m : Restaurant.getMesas()){
                if(m!=null){
                if(m.getNumero()==nmesa){
                    mEliminar=m;
                    m.setX(x);
                    m.setY(y);
                    
            }}
            }
            //mesas.remove(mEliminar);
            try {
                            MesasData.escribirMesas(Restaurant.getMesas(), "mesas.txt");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }catch (URISyntaxException ex) {
                            ex.printStackTrace();
                    }
           //click=true;
            
        });
        //click=true;
    }
    //public Pane CargarMesas(Pane fp){
    public Pane CargarMesas(){
        //fpPantallaAdmin.setPadding(new Insets(0,0,25,0));
        fpPantallaAdmin.getChildren().clear();
        vb1.getChildren().clear();
        //fpPantallaAdmin.setAlignment(Pos.CENTER);
        Stage stage = new Stage();
        //fp.getChildren().clear();
        Pane pane = new Pane();
        //pane.setPrefHeight(fp.getHeight());
        pane.setPrefHeight(fpPantallaAdmin.getHeight());
        //pane.setPrefWidth(fp.getWidth());
        pane.setPrefHeight(fpPantallaAdmin.getWidth());
        
            
            try{
                for(Mesa m:Restaurant.mesas){
                    StackPane sp = new StackPane();
                    double x= m.getX();
                    double y= m.getY();
                    int numeroMesa= m.getNumero();
                    
                    Ellipse elipse = new Ellipse(50,50);
                    if(m.isOcupada()==true){
                        elipse.setFill(Color.RED);
                    }else{
                    elipse.setFill(Color.YELLOW);}
                    
                    Label lbNumMesa = new Label(String.valueOf(numeroMesa));
                    
                    sp.setLayoutX(x);
                    sp.setLayoutY(y);
                    
                    sp.getChildren().addAll(elipse,lbNumMesa);
                    //ArrastrarMesas(sp, numeroMesa);
                    pane.getChildren().add(sp);
                    
                }
                //fp.getChildren().add(pane);
                fpPantallaAdmin.getChildren().add(pane);
            
            }catch(RuntimeException e){
                System.out.println(e.getMessage());
            }catch(Exception e){
                System.out.println("Error");
            }
            return pane;
    }

    class cargarMesasRunnable implements Runnable{
        
        @Override
        public void run(){
            try{
                while(!pausarHilo){
        Platform.runLater(()->{
                fpPantallaAdmin.getChildren().clear();
            try {
                mesas=MesasData.leerMesas("mesas.txt");
                CargarMesasSinEventos(true);
                System.out.println(mesas);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            });
        System.out.println("Inicie");
        Thread.sleep(2000);
        System.out.println("Me levante");
        if(pausarHilo){
            
        }}
        }catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
        //cargarMesasSinEventos();
    }}
    
    //public Pane CargarMesasSinEventos(Pane fp){
    public Pane CargarMesasSinEventos(boolean t) throws IOException{
        
        vb1.getChildren().clear();
        mesas=MesasData.leerMesas("mesas.txt");
       
        fpPantallaAdmin.getChildren().clear();
        Pane pane = new Pane();
        pane.setPrefHeight(fpPantallaAdmin.getHeight());
        pane.setPrefWidth(fpPantallaAdmin.getWidth());
        
            
            try{
                for(Mesa m:Restaurant.mesas){
                    StackPane sp = new StackPane();
                    double x= m.getX();
                    double y= m.getY();
                    int numeroMesa= m.getNumero();
                    
                    Ellipse elipse = new Ellipse(50,50);
                    if(m.isOcupada()==true){
                        elipse.setFill(Color.RED);
                    }else{
                    elipse.setFill(Color.YELLOW);}
                    
                    Label lbNumMesa = new Label(String.valueOf(numeroMesa));
                    
                    sp.setLayoutX(x);
                    sp.setLayoutY(y);
                    
                    sp.getChildren().addAll(elipse,lbNumMesa);
                    pane.getChildren().add(sp);
                    
                }
                fpPantallaAdmin.getChildren().add(pane);
            
            }catch(RuntimeException e){
                System.out.println(e.getMessage());
            }catch(Exception e){
                System.out.println("Error");
            }
            
       HBox hbNumComensales = new HBox();
       HBox hbTotalFacturado = new HBox();
       
       Label lbText1 = new Label("Número de Comensales: ");
       Label lbNumComensales= new Label();
       
       Label lbText2 = new Label("Total Facturado: $");
       Label lbTotalFacturado= new Label();
       
       
       int contadorComensales=0;
       
       
       int totalVentas=0;
       
       //vb1.getChildren().add(hbNumComensales);
       //vb1.getChildren().add(hbTotalFacturado);
       
        try {
            mesas= MesasData.leerMesas("mesas.txt");
            } catch (IOException ex) {
            System.out.println("Archivo no encontrado");
        }
        
        //try {
        ArrayList<Venta> ventas= VentaData.leerVentasPorFecha(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
           // } catch (IOException ex) {
           // System.out.println("Archivo no encontrado");
        //}
        
        for(Mesa m:mesas){
            contadorComensales+=m.getCapacidad();           
        }
        
        for(Venta v:ventas){
            totalVentas+=v.getTotal();
        }
        
        lbNumComensales.setText(String.valueOf(contadorComensales));
        lbTotalFacturado.setText(String.valueOf(totalVentas));
        
        hbTotalFacturado.getChildren().add(lbText2);
        hbTotalFacturado.getChildren().add(lbTotalFacturado);
        hbNumComensales.getChildren().add(lbText1);
        hbNumComensales.getChildren().add(lbNumComensales);
        vb1.getChildren().add(hbNumComensales);
        vb1.getChildren().add(hbTotalFacturado);
        if (t==false){
            vb1.getChildren().clear();
        }
                return pane;
        }
    
    
    
    @FXML
   private void MostrarMonitoreo(MouseEvent event) throws IOException {
       pausarHilo=false;
       vb1.getChildren().clear();
       Thread t = new Thread(new cargarMesasRunnable());
        t.start();
        if(pausarHilo){
            t.stop();
        }else{
            t.resume();
            //t.notify();
        }
       
       fpPantallaAdmin.getChildren().clear();
       //CargarMesasSinEventos(fpPantallaAdmin);
       CargarMesasSinEventos(true);
       
      
        
    }
   @FXML
   private void CreacionMesas(MouseEvent event) throws IOException{
       
       disenoPlano();
   }
   
   public void disenoPlano() throws IOException{
        pausarHilo=true;
        fpPantallaAdmin.getChildren().clear();
        vb1.getChildren().clear();
        CargarMesasSinEventos(false);
        ArrayList<String> listaAcciones= new ArrayList<String>();
        listaAcciones.add("Agregar mesas");
        listaAcciones.add("Editar o Eliminar");
        listaAcciones.add("Mover");
        ComboBox cbAccion = new ComboBox();
        cbAccion.getItems().addAll(listaAcciones);
        vb1.getChildren().add(cbAccion);
       
        
        Pane pane = new Pane();
        pane.setPrefHeight(fpPantallaAdmin.getHeight());
        pane.setPrefWidth(fpPantallaAdmin.getWidth());
        
        Pane pane1 = new Pane();
        pane1.setPrefHeight(fpPantallaAdmin.getHeight());
        pane1.setPrefWidth(fpPantallaAdmin.getWidth());
                
        Pane pane2 = new Pane();
        pane2.setPrefHeight(fpPantallaAdmin.getHeight());
        pane2.setPrefWidth(fpPantallaAdmin.getWidth());
        
        
        
        cbAccion.setOnAction((ActionEvent)->{
            
           
                opcion = String.valueOf(cbAccion.getValue());
               System.out.println(opcion);
                    
                    if (opcion.equals("Agregar mesas")){
                        pane.getChildren().clear();
                        pane2.getChildren().clear();
                        //opcion= null;
                        click = true;
                        drag = false;
                        
                        if (click == true){
                            if(drag==false){
                                
                            
                                fpPantallaAdmin.getChildren().clear();
                        
                        
                            try {
                        Restaurant.mesas= MesasData.leerMesas("mesas.txt");
                        //System.out.println(Restaurant.getMesas());
                        for (Mesa m:mesas ){
                            StackPane sp = new StackPane();
                            double d= m.getX();
                            double f= m.getY();
                            
                            int numeroMesa= m.getNumero();
                            
                            Ellipse elipse = new Ellipse(50,50);
                            if(m.isOcupada()==true){
                                elipse.setFill(Color.RED);
                            }else{
                                elipse.setFill(Color.YELLOW);}
                            
                            Label lbNumMesa = new Label(String.valueOf(numeroMesa));
                            
                            sp.setLayoutX(d);
                            sp.setLayoutY(f);
                            
                            sp.getChildren().addAll(elipse,lbNumMesa);
                            pane1.getChildren().add(sp);
                            
                 pane1.setOnMouseClicked((MouseEvent) -> {

              

                VBox cuadro1 =  new VBox();
                cuadro1.setSpacing(10);

                Stage stage = new Stage();

                //try{ 

                cuadro1.setAlignment(Pos.CENTER);


                Label etiqueta3 = new Label("INGRESE CAPACIDAD");
                TextField capacidad = new TextField("2");
                HBox h3 = new HBox(etiqueta3,capacidad);
                h3.setSpacing(30);
                Label lbMensaje= new Label();
                Label lbMensaje1= new Label();

                Button boton = new Button("CREAR");
                cuadro1.getChildren().addAll(h3,boton);
                cuadro1.setSpacing(30);
                cuadro1.setPadding(new Insets(7,7,7,7));
                Scene ventana = new Scene(cuadro1);
                stage.setScene(ventana);
                stage.setWidth(400);
                stage.setHeight(400);



                stage.show();
                
                Point2D posicion = new Point2D(MouseEvent.getSceneX(), MouseEvent.getSceneY());
                double posicionx = posicion.getX();
                double posiciony = posicion.getY();

                boton.setOnMouseClicked((MouseEvent e)->{
                    lbMensaje1.setText("");
                    //try {
                            if(Integer.parseInt(capacidad.getText())<=0){
                                throw new NumberFormatException();  
                            }
                            boolean condicionDistancia=false;

                    int contador=0;
                    for(Mesa me:mesas){
                        Point2D posm= new Point2D(me.getX()+50,me.getY()+92);
                        posicion.add(-50, -85);

                        if(posm.distance(posicion)>125){
                         condicionDistancia=true;
                         contador+=1;
                         System.out.println(posm);
                         System.out.println(posm.distance(posicion));

                        }

                        }
                        System.out.println(contador);
                        System.out.println(mesas.size());
                        System.out.println(posicion);          
                    if(condicionDistancia&&(contador==mesas.size())){

                    Restaurant.añadirMesa(new Mesa(Restaurant.mesas.size()+1,Integer.parseInt(capacidad.getText()), false, posicionx-50,posiciony-85));
                   
                    try {
                                MesasData.escribirMesas(Restaurant.mesas, "mesas.txt");
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            } catch (URISyntaxException ex) {
                                ex.printStackTrace();
                            }

                    Ellipse eli = new Ellipse(50,50);
                    eli.setFill(Color.YELLOW);
                    String n = String.valueOf(Restaurant.mesas.size());
                    Label numeromesa = new Label(n);
                    StackPane st = new StackPane();
                    st.getChildren().addAll(eli,numeromesa);                  

                  

                        fpPantallaAdmin.getChildren().clear();
                        pane1.getChildren().add(st);
                        st.setLayoutX(posicionx-50);
                        st.setLayoutY(posiciony-85);

                        fpPantallaAdmin.getChildren().add(pane1);


                     

                    stage.close();
                    }else{
                            lbMensaje.setText("Porfavor ingrese la mesa mas distanciada de las otras");
                            cuadro1.getChildren().add(lbMensaje);
                        }});
                });           
                            
                            
                            
                            
                            
                            
                            
                            
                       
                        }
                        fpPantallaAdmin.getChildren().add(pane1);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                            }          
                        }
              
                    
                    }else if (opcion.equals("Editar o Eliminar")){
                        pane.getChildren().clear();
                        pane1.getChildren().clear();
                        fpPantallaAdmin.getChildren().clear();
                        
                    try {
                        Restaurant.mesas= MesasData.leerMesas("mesas.txt");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                     //   System.out.println(Restaurant.getMesas());
                        for (Mesa m:mesas ){
                            StackPane sp = new StackPane();
                            double x= m.getX();
                            double y= m.getY();
                            
                            int numeroMesa= m.getNumero();
                            
                            Ellipse elipse = new Ellipse(50,50);
                            if(m.isOcupada()==true){
                                elipse.setFill(Color.RED);
                            }else{
                                elipse.setFill(Color.YELLOW);}
                            
                            Label lbNumMesa = new Label(String.valueOf(numeroMesa));
                            
                          sp.setLayoutX(x);
                           sp.setLayoutY(y);
                            
                            sp.getChildren().addAll(elipse,lbNumMesa);
                            pane2.getChildren().add(sp);
                            
                            
                 sp.setOnMouseClicked((MouseEvent) -> {
           
            
            
            Point2D posicion = new Point2D(MouseEvent.getSceneX(), MouseEvent.getSceneY());
            double posicionx = posicion.getX();
            double posiciony = posicion.getY();
            
            boolean condicionDistancia=false;
            boolean condicionClickMesa=false;
                int contador=0;
               
                    if(m!=null){
                    Point2D posm= new Point2D(m.getX()+50,m.getY()+92);
                    posicion.add(-50, -85);
                    
                    if(posm.distance(posicion)<=25){
                        condicionClickMesa=true;
                    }
                    
                    else if(posm.distance(posicion)>125){
                     condicionDistancia=true;
                     contador+=1;
                     System.out.println(posm);
                     System.out.println(posm.distance(posicion));
                     
                    
                }
                    }
                    System.out.println(contador);
                    System.out.println(mesas.size());
                    System.out.println(posicion);          
                
                //stage.close();
                if(condicionClickMesa){
                
                VBox vb=new VBox();
                Stage stageEditar = new Stage();
                
                Label lbNuevaCapacidad= new Label("Ingrese nueva capacidad");
                TextField tfEditarCapacidad= new TextField();
                HBox hb = new HBox();
                Button btEditar= new Button("Editar");
                Button btEliminar = new Button("Eliminar");
                hb.getChildren().add(btEditar);
                hb.getChildren().add(btEliminar);
                vb.getChildren().add(lbNuevaCapacidad);
                vb.getChildren().add(tfEditarCapacidad);
                vb.getChildren().add(hb);
                vb.setAlignment(Pos.CENTER);
                hb.setAlignment(Pos.CENTER);
                
                vb.setSpacing(30);
                vb.setPadding(new Insets(7,7,7,7));
                Scene vtEditar = new Scene(vb);
                stageEditar.setScene(vtEditar);
                stageEditar.setWidth(400);
                stageEditar.setHeight(400);
                stageEditar.show();
                
                btEditar.setOnMouseClicked((MouseEvent e2)->{
                    try{
                    int capacidadNueva= Integer.parseInt(tfEditarCapacidad.getText());
                    
                        double xb = m.getX()+50;
                        double yb = m.getY()+92;
                        Point2D ptmesa= new Point2D(xb,yb);
                        if(ptmesa.distance(posicion)<25){
                            System.out.println(m);
                            System.out.println(mesas);
                            
                            m.setCapacidad(capacidadNueva);
                            System.out.println(m);
                            System.out.println(mesas);
                        }
                        stageEditar.close();
                    }catch(InputMismatchException ec){
                        System.out.println("No se puede");
                    }catch(NumberFormatException ex){
                        System.out.println("No se puede");
                    }
                    
                    try {
                        MesasData.escribirMesas(Restaurant.mesas, "mesas.txt");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                });
                
                btEliminar.setOnMouseClicked((MouseEvent e3)->{
                    boolean borrar=false;
                    Mesa mesaBorrar=null;
                    
                        if(m!=null){
                        double xu = m.getX()+50;
                        double yu = m.getY()+92;
                        Point2D ptmesa= new Point2D(xu,yu);
                        if(ptmesa.distance(posicion)<25){
                            mesaBorrar =m;
                            borrar = true;
                            /*
                               Platform.runLater( ()->{
                                    try{
                                    borrar=true;
                                    mesaBorrar=m;
                                    MesasData.escribirMesas(mesas, "mesas.txt");
                                    CargarMesas();
                                    }catch(IOException ex){
                                        System.out.println("Problemas tecnicos");
                                    }catch(URISyntaxException ex2){
                                     System.out.println("Problemas tecnicos");
                                   }
                                });
                                */
                        }
                        stageEditar.close();
                    }
                    if(borrar && (mesaBorrar!=null)){
                    Restaurant.borrarMesa(mesaBorrar);
                    
                    try {
                        MesasData.escribirMesas(Restaurant.mesas, "mesas.txt");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        System.out.println("Aqui1");
                    } catch (URISyntaxException ex) {
                        ex.printStackTrace();
                        System.out.println("Aqui2");
                    }
                    
                    
                    
                        fpPantallaAdmin.getChildren().clear();
                        
                        try {
                            // pane2.getChildren().clear();
                            disenoPlano();
                            
                            //vb1.getChildren().add(cbAccion);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        
                    
                    }
                });
                
                
                }
                
                    
        });
                        
                        
                        
                                   
                            
                        
                        }fpPantallaAdmin.getChildren().add(pane2);
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        // System.out.println(opcion);   
                    }else if (opcion.equals("Mover")){
                        pane1.getChildren().clear();
                        pane2.getChildren().clear();
                        opcion = null;
                        drag = true;
                        click = false;
                        
                   if (drag==true){
                       if (click==false){
                           
                       
                          fpPantallaAdmin.getChildren().clear();
                    try {
                        Restaurant.mesas= MesasData.leerMesas("mesas.txt");
                     //   System.out.println(Restaurant.getMesas());
                        for (Mesa m:mesas ){
                            StackPane sp = new StackPane();
                            double x= m.getX();
                            double y= m.getY();
                            
                            int numeroMesa= m.getNumero();
                            
                            Ellipse elipse = new Ellipse(50,50);
                            if(m.isOcupada()==true){
                                elipse.setFill(Color.RED);
                            }else{
                                elipse.setFill(Color.YELLOW);}
                            
                            Label lbNumMesa = new Label(String.valueOf(numeroMesa));
                            
                          sp.setLayoutX(x);
                           sp.setLayoutY(y);
                            
                            sp.getChildren().addAll(elipse,lbNumMesa);
                            pane.getChildren().add(sp);
                            
                            
                            
                            sp.setOnMouseDragged((MouseEvent ev) -> {
                                double xx = ev.getSceneX()-50;
                                //click=false;
                                double yy = ev.getSceneY()-85;
                                sp.setLayoutX(xx);
                                sp.setLayoutY(yy);
                                m.setX(xx);
                                m.setY(yy);
                                // System.out.println(Restaurant.getMesas());
                                //mesas.remove(mEliminar);
                                try {
                                    MesasData.escribirMesas(Restaurant.getMesas(), "mesas.txt");
                                    //   CargarMesasSinEventos();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }catch (URISyntaxException ex) {
                                    ex.printStackTrace();
                                }
                                //click=true;
                                
                            });
                        }
                        fpPantallaAdmin.getChildren().add(pane);
                        //opcion = null;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                       drag = false;
                   }
                   }
               opcion = null;
               
                }
                
            
            
        //cbAccion.getItems().clear();
         //   cbAccion.getItems().addAll(listaAcciones);
        
        });
        
   }
}


            
