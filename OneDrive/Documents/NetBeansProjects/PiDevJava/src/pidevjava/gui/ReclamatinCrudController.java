/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevjava.gui;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pidevjava.entities.Reclamation;
import pidevjava.services.ReclamationService;

/**
 * FXML Controller class
 *
 * @author Abirn
 */
public class ReclamatinCrudController implements Initializable {

    @FXML
    private JFXTextField sujet_txt;
    @FXML
    private TableView<Reclamation> reclam_table;
    @FXML
    private TableColumn<Reclamation, String> sujet_col;
    @FXML
    private TableColumn<?, ?> date_col;
    @FXML
    private TableColumn<Reclamation, String> descrip_col;
    @FXML
    private Button ajouter_btn;
    @FXML
    private Button modif_btn;
    @FXML
    private Button Supp_btn;
    @FXML
    private JFXTextField rech_txt;
    @FXML
    private Button rech_btn;
    @FXML
    private JFXTextField descrp_txt1;
    @FXML
    private JFXDatePicker date_dtp;

    ReclamationService reclam = new ReclamationService();
    @FXML
    private TableColumn<Reclamation, String> userid_col;
    @FXML
    private JFXTextField userid_txt1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Afficher();
    }

    @FXML
    private void Select(MouseEvent event) {

        Reclamation selected = reclam_table.getSelectionModel().getSelectedItem();

        sujet_txt.setText(String.valueOf(selected.getSujet()));
        descrp_txt1.setText(String.valueOf(selected.getDescription()));
        userid_txt1.setText(String.valueOf(selected.getUser_id()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(selected.getDate().toString(), formatter);
        date_dtp.setValue(localDate);

    }

    @FXML
    private void AjouterUser(ActionEvent event) {

        int userid = Integer.parseInt(userid_txt1.getText());
        Date dates = (Date.valueOf(date_dtp.getValue()));

        Reclamation r = new Reclamation(sujet_txt.getText(), descrp_txt1.getText(), dates, userid);
        ReclamationService rc = new ReclamationService();
        rc.ajouterReclamation(r);
        Afficher();
    }

    @FXML
    private void ModifierUser(ActionEvent event) {

        int userid = Integer.parseInt(userid_txt1.getText());
        Date dates = (Date.valueOf(date_dtp.getValue()));

        Reclamation r = new Reclamation();
        Reclamation r2 = reclam_table.getSelectionModel().getSelectedItem();
        int id_r = r2.getId();
        Reclamation c3 = new Reclamation(id_r, sujet_txt.getText(), descrp_txt1.getText(), dates, userid);
        reclam.updateReclamation(c3);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Reclamation Updated");
        alert.showAndWait();
        Afficher();
    }

    @FXML
    private void SuppUser(ActionEvent event) {
        reclam.supprimerReclamation(reclam_table.getSelectionModel().getSelectedItems().get(0));

        try {
            javafx.scene.Parent tableview = FXMLLoader.load(getClass().getResource("ReclamatinCrud.fxml"));
            Scene sceneview = new Scene(tableview);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(sceneview);
            window.show();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        Afficher();
    }

    public void Afficher() {

        ObservableList<Reclamation> reclamationList = FXCollections.observableArrayList();
        for (Reclamation rc : reclam.displayReclamations()) {
            reclamationList.add(rc);
        }
        sujet_col.setCellValueFactory(new PropertyValueFactory<>("sujet"));
        date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
        descrip_col.setCellValueFactory(new PropertyValueFactory<>("description"));
        userid_col.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        reclam_table.setItems(reclamationList);

    }

    @FXML
    private void recherche(ActionEvent event) {

        ObservableList<Reclamation> userList = FXCollections.observableArrayList();
        for (Reclamation r : reclam.RechercheReclamations(rech_txt.getText())) {
            userList.add(r);
        }
        sujet_col.setCellValueFactory(new PropertyValueFactory<>("sujet"));
        date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
        descrip_col.setCellValueFactory(new PropertyValueFactory<>("description"));
        userid_col.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        reclam_table.setItems(userList);
    }

    
}
