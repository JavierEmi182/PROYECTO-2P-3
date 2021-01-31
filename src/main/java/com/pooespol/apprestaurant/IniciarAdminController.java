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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fpPantallaAdmin.getChildren().clear();
        CargarMesasSinEventos();
        //CargarMesas(fpPantallaAdmin);
        
        //Thread t = new Thread(new cargarMesasRunnable());
        //t.start();
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
           ArrayList<Comida> comidas = ComidaData.leerComida("comida.txt"); 
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
        
    }
    public void PantallaEditarPlato(Comida comida) throws IOException {
           System.out.println(comida);
           System.out.println(Restaurant.getComidas());
           
           fpPantallaAdmin.getChildren().clear();
           fpPantallaAdmin.setAlignment(Pos.CENTER);
            //container principal
            
            
            VBox vcontainer = new VBox();
            vcontainer.setAlignment(Pos.CENTER);
            vcontainer.setSpacing(30);
            
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
           
            
    }
    public void PantallaCrearPlato(){
        try {
            fpPantallaAdmin.getChildren().clear();
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
    }
        EventHandler<MouseEvent> FiltrarVentas = new EventHandler<MouseEvent>(){  
        public void handle(MouseEvent event){
            fpPantallaAdmin.getChildren().clear();
            
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
                }};

    @FXML
    private void CreacionMesas(MouseEvent event) {
        
        
        fpPantallaAdmin.getChildren().clear();
        //Pane pane =CargarMesas(fpPantallaAdmin);
        Pane pane =CargarMesas();
        pane.setPrefHeight(fpPantallaAdmin.getHeight());
        pane.setPrefWidth(fpPantallaAdmin.getWidth());
  
        
        pane.setOnMouseClicked((MouseEvent) -> {
            VBox cuadro1 =  new VBox();
            
            Stage stage = new Stage();
            
            try{ 
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
                try {
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
                    }}
                    System.out.println(contador);
                    System.out.println(mesas.size());
                    System.out.println(posicion);          
                if(condicionDistancia&&(contador==mesas.size())){   
                Restaurant.mesas.add(new Mesa(Restaurant.mesas.size()+1,Integer.parseInt(capacidad.getText()), false, posicionx-50,posiciony-85));
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
                    
                    ArrastrarMesas(st,Restaurant.mesas.size()+1);
                    fpPantallaAdmin.getChildren().add(pane);
                    
                    
                 });
                
                stage.close();
                }else{
                        lbMensaje.setText("Porfavor ingrese la mesa mas distanciada de las otras");
                        cuadro1.getChildren().add(lbMensaje);
                    }
                    }catch (NumberFormatException ex) {
                        
                            lbMensaje1.setText("Ingrese un Valor Adecuado");
                            cuadro1.getChildren().add(lbMensaje1);
                        }
                
                 //Añadido for
            });
            mesas=MesasData.leerMesas("mesas.txt");
            }catch(RuntimeException e){
                System.out.println(e.getMessage());
                System.out.println("Aqui esta");
            }catch(Exception e){
                System.out.println("Error");
            }
            
        });
    }   
    //Evento Arrastrar
    public void ArrastrarMesas(StackPane sp, int nmesa){
        sp.setOnMouseDragged((MouseEvent ev) -> {
            double x = ev.getSceneX()-50;
            double y = ev.getSceneY()-85;
            sp.setLayoutX(x);
            sp.setLayoutY(y);
            for(Mesa m : Restaurant.getMesas()){
                if(m.getNumero()==nmesa){
                    m.setX(x);
                    m.setY(y);
                    try {
                            MesasData.escribirMesas(Restaurant.getMesas(), "mesas.txt");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }catch (URISyntaxException ex) {
                            ex.printStackTrace();
                    }
            }
            }
        });
    }
    //public Pane CargarMesas(Pane fp){
    public Pane CargarMesas(){
        fpPantallaAdmin.getChildren().clear();
        //fpPantallaAdmin.setAlignment(Pos.CENTER);
        
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
                    ArrastrarMesas(sp, numeroMesa);
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
        Platform.runLater(()->{
                fpPantallaAdmin.getChildren().clear();
               CargarMesasSinEventos();
            });
        
        Thread.sleep(12000);
        }catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
        //cargarMesasSinEventos();
    }}
    
    //public Pane CargarMesasSinEventos(Pane fp){
    public Pane CargarMesasSinEventos(){
        
        fpPantallaAdmin.getChildren().clear();
        //fp.getChildren().clear();
        //fpPantallaAdmin.setAlignment(Pos.CENTER);
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
    @FXML
   private void MostrarMonitoreo(MouseEvent event) {
       fpPantallaAdmin.getChildren().clear();
       //CargarMesasSinEventos(fpPantallaAdmin);
       CargarMesasSinEventos();
       /*System.out.println("Funciono");
       
       
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

        System.out.println("Termino");
        System.out.println(Restaurant.mesas);
        System.out.println(Restaurant.mesas.get(0).getMesero().getNombre());
        */
    }}


            
