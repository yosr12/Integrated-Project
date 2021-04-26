/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import utils.DataSource;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class EssaiiController implements Initializable {

    @FXML
    private BarChart<String, Float> Barchar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     ResultSet Rs;
     Connection Cox;
     Cox = DataSource.getInstance().getConn();
     String query = "SELECT produit_id, COUNT(*) AS nb FROM commande_produit GROUP BY produit_id";
   //  String Req = "select * from produit";

        XYChart.Series<String,Float> series = new XYChart.Series<>() ; 
        try { 
            Rs = Cox.createStatement().executeQuery(query) ;
            while (Rs.next()) {
                series.getData().add(new XYChart.Data<>(Rs.getString(1), Rs.getFloat(2))) ;
            }
            Barchar.getData().add(series) ; 
        } catch (SQLException ex) {
            Logger.getLogger(EssaiiController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }    
    
}
