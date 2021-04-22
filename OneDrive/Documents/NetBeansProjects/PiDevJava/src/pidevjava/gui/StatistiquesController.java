/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevjava.gui;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import pidevjava.utils.MyCnx;

/**
 * FXML Controller class
 *
 * @author Abirn
 */
public class StatistiquesController implements Initializable {

    @FXML
    private JFXComboBox<?> stat_cmb;
    @FXML
    private PieChart pieChart;
    private ObservableList data;
    Connection conn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        buildData();
        pieChart.getData().addAll(data);
        
    }

    @FXML
    private void selon(ActionEvent event) {
    }

  public void buildData() {
        conn = MyCnx.getInstance().getConnection();

        data = FXCollections.observableArrayList();
        try {
            //SQL FOR SELECTING NATIONALITY OF CUSTOMER
            String SQL = "SELECT COUNT(id), "
                    + "gender FROM user "
                    + "GROUP BY gender";

            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                //adding data on piechart data
                data.add(new PieChart.Data(rs.getString(2), rs.getDouble(1)));
            }
        } catch (Exception e) {
            System.out.println("Error on DB connection");
            return;
        }
  }
}
