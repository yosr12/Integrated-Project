/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Entite.Tags;
import Service.TagsService;
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
 * @author MEGA-PC
 */
public class StattagsfxmlController implements Initializable {

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
     String query = "SELECT tags_id, COUNT(*) AS nb FROM sujet_tags GROUP BY tags_id";
   //  String Req = "select * from produit";
        
        Tags t = new Tags();
   
        XYChart.Series<String,Float> series = new XYChart.Series<>() ; 
        try { 
            Rs = Cox.createStatement().executeQuery(query) ;
            while (Rs.next()) {
                TagsService ts = new TagsService();
                t=ts.TrouverById(Integer.parseInt(Rs.getString(1))); 
                series.getData().add(new XYChart.Data<>(t.getTag(), Rs.getFloat(2))) ;
            }
            this.Barchar.getData().add(series); 
        } catch (SQLException ex) {
            Logger.getLogger(StattagsfxmlController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
