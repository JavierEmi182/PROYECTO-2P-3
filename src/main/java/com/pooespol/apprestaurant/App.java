package com.pooespol.apprestaurant;

import com.pooespol.apprestaurant.data.ComidaData;
import com.pooespol.apprestaurant.data.CredencialesData;
import com.pooespol.apprestaurant.data.MesasData;
import com.pooespol.apprestaurant.modelo.Mesa;
import com.pooespol.apprestaurant.modelo.Restaurant;
import static com.pooespol.apprestaurant.modelo.Restaurant.mesas;
import com.pooespol.apprestaurant.modelo.comida.Comida;
import com.pooespol.apprestaurant.modelo.login.Administrador;
import com.pooespol.apprestaurant.modelo.login.Mesero;
import com.pooespol.apprestaurant.modelo.login.Usuario;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    

    @Override
    public void start(Stage stage) {
        
        //cargue el scene graph a partir del archivo repote.fxml
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Login.fxml"));                        
            Parent root = fxmlLoader.load();
            //cree el scene y fije como nodo raiz el objeto que cargo con el fxml
            scene = new Scene(root);
        } catch (IOException ex) {
            //si llegamos a este punto es porque no se pudo cargar del archivo
            //reporte.fxml el scenegraph
            //creamos con programacion un nuevo roort y lo fijamos a la scena
            VBox v = new VBox(new Label("No se cargo el archivo Login.fxml"));
            scene = new Scene(v);
            System.out.println(ex);
        }
        
        //fije la scena al primary stage
        stage.setScene(scene);
        
        //muestre la aplicacion
        stage.show();

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /*private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("vista/"+fxml+ ".fxml"));
        return fxmlLoader.load();
    }*/
    
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource(fxml + ".fxml"));
        return loader.load();
    }
    
    public static void initialize() throws IOException{
        
        ArrayList<Usuario> usuarios = Restaurant.getUsuarios();
        Administrador admin1 = new Administrador("admin@gmail.com","admin");
        usuarios.add(admin1);
        Mesero mesero1 = new Mesero("Javier","mesero@gmail.com","mesero");
        usuarios.add(mesero1);
        
        ArrayList<Comida> comidas = ComidaData.leerComida("comida.txt");
        for (Comida c: comidas){
            Restaurant.añadirComida(c);
           
        }
        ArrayList<Comida> comidasInventario = ComidaData.leerComida("comidaSinAgregar.txt");
        for (Comida c: comidasInventario){
            Restaurant.añadirComidaInventario(c);
        }
        mesas= MesasData.leerMesas("mesas.txt");
        /*
        ArrayList<Usuario> admin = CredencialesData.leerAdministradores("CredencialesAdmin.txt");
        ArrayList<Usuario> mesero = CredencialesData.leerMeseros("CredencialesMesero.txt");
        for (Usuario u : admin){
            Restaurant.añadirUsuario(u);
        }
        for (Usuario u:mesero){
            Restaurant.añadirUsuario(u);
        }*/
    }

    public static void main(String[] args) {
        try {
            
            initialize();
           // System.out.println(MesasData.leerMesas("mesas.txt"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        launch();
        
    }
}