module com.pooespol.apprestaurant {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.pooespol.apprestaurant to javafx.fxml;
    exports com.pooespol.apprestaurant;
}
