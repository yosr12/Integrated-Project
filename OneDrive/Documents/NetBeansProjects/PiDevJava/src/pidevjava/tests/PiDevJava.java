/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevjava.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Abirn
 */
public class PiDevJava extends Application {

    public static Stage stage = null;

    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("/pidevjava/gui/Crud.fxml"));
//       Parent root = FXMLLoader.load(getClass().getResource("/pidevjava/gui/AdminCrud.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("/pidevjava/gui/ReclamatinCrud.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("/pidevjava/gui/Register.fxml"));
         Parent root = FXMLLoader.load(getClass().getResource("/pidevjava/gui/AccueilPage.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("/pidevjava/gui/Sidebar.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

//        java.util.Date date=new java.util.Date();
//        java.sql.Date d=new java.sql.Date(date.getTime());
    }

}
