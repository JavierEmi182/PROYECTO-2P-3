package com.pooespol.apprestaurant;

import com.pooespol.apprestaurant.modelo.Restaurant;
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
    

    public static void main(String[] args) {
        launch();
    }
    /*
    public static void loadNewScene(String name,double width,double height){
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(name+".fxml"));
            try{
                Parent root = fxmlLoader.load();
                //crear el scene y fijar el root
            Scene sc = new Scene (root);
           
            //crear el stage con el scene
            Stage st = new Stage();
            st.setWidth(width);
            st.setHeight( height);
            st.setScene(sc);
            //mostrar el scene
            st.show();
            //la ventana no se puede cambiar de tamaño
            //st.setResizable(false);
            }catch(IOException ex){
                ex.printStackTrace();
            }
    }*/
    

}