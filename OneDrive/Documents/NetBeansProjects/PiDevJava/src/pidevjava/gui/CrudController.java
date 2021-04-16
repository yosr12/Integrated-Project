/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevjava.gui;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pidevjava.entities.Admin;
import pidevjava.entities.User;
import pidevjava.services.AdminService;
import pidevjava.services.UserService;

/**
 * FXML Controller class
 *
 * @author Abirn
 */
public class CrudController implements Initializable {

    @FXML
    private JFXTextField name_txt;
    @FXML
    private JFXTextField email_txt;
    @FXML
    private JFXTextField fname_txt;
    @FXML
    private JFXTextField num_txt;
    @FXML
    private RadioButton male_rb;
    @FXML
    private ToggleGroup gender_tg;
    @FXML
    private RadioButton female_rb;
    @FXML
    private JFXDatePicker bday_dtp;
    @FXML
    private TableView<User> user_table;
    @FXML
    private TableColumn<User, String> name_col;
    @FXML
    private TableColumn<User, String> fname_col;
    @FXML
    private TableColumn<User, String> bday_col;
    @FXML
    private TableColumn<User, String> email_col;
    @FXML
    private TableColumn<User, String> gender_col;
    @FXML
    private TableColumn<User, String> num_col;
    @FXML
    private Button ajouter_btn;
    @FXML
    private Button modif_btn;
    @FXML
    private Button Supp_btn;
    @FXML
    private JFXPasswordField mdp_txt;
    @FXML
    private JFXPasswordField confirm_txt;

    @FXML
    private JFXTextField rech_txt;
    @FXML
    private Button rech_btn;

    UserService us = new UserService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Afficher();
        // TODO
    }

    @FXML
    private void AjouterUser(ActionEvent event) {

        String genre = null;
        if (male_rb.isSelected()) {
            genre = "Male";
        } else if (female_rb.isSelected()) {
            genre = "Female";
        }
        int num = Integer.parseInt(num_txt.getText());
        Date bday = (Date.valueOf(bday_dtp.getValue()));

        User u = new User(name_txt.getText(), fname_txt.getText(), genre, num, email_txt.getText(), mdp_txt.getText(), bday);
        UserService us = new UserService();
        us.ajouterUser(u);
        Afficher();
    }

    @FXML
    private void ModifierUser(ActionEvent event) {

        String genre = null;
        if (male_rb.isSelected()) {
            genre = "Male";
            female_rb.setSelected(false);
        } else if (female_rb.isSelected()) {
            genre = "Female";
            male_rb.setSelected(false);
        }
        int num = Integer.parseInt(num_txt.getText());
        Date bday = (Date.valueOf(bday_dtp.getValue()));

        User u = new User();
        User c2 = user_table.getSelectionModel().getSelectedItem();
        int id_c = c2.getId();
        User c3 = new User(id_c, name_txt.getText(), fname_txt.getText(), genre, num, email_txt.getText(), mdp_txt.getText(), bday);
        us.updateUser(c3);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("User Updated");
        alert.showAndWait();
        Afficher();
    }

    @FXML
    private void SuppUser(ActionEvent event) {

        us.supprimerUser(user_table.getSelectionModel().getSelectedItems().get(0));
        try {
            javafx.scene.Parent tableview = FXMLLoader.load(getClass().getResource("Crud.fxml"));
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

        ObservableList<User> useList = FXCollections.observableArrayList();
        for (User u : us.displayUsers()) {
            useList.add(u);
        }
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        fname_col.setCellValueFactory(new PropertyValueFactory<>("fname"));
        email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        //image_col.setCellValueFactory(new PropertyValueFactory<>("image"));
        gender_col.setCellValueFactory(new PropertyValueFactory<>("gender"));
        num_col.setCellValueFactory(new PropertyValueFactory<>("num"));
        bday_col.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        user_table.setItems(useList);

    }

    @FXML
    private void Select(MouseEvent event) {

        User selected = user_table.getSelectionModel().getSelectedItem();
        name_txt.setText(String.valueOf(selected.getName()));
        fname_txt.setText(String.valueOf(selected.getFname()));
        email_txt.setText(String.valueOf(selected.getEmail()));
        mdp_txt.setText(String.valueOf(selected.getPassword()));
        num_txt.setText(String.valueOf(selected.getNum()));

        if (String.valueOf(selected.getGender()).equals("Female")) {
            female_rb.setSelected(true);
        } else if (String.valueOf(selected.getGender()).equals("Male")) {
            male_rb.setSelected(true);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(selected.getBirthday().toString(), formatter);
        bday_dtp.setValue(localDate);

    }

   

    @FXML
    private void Rechercher(ActionEvent event) {
         ObservableList<User> userList = FXCollections.observableArrayList();
        for (User u : us.RechercheUsers(rech_txt.getText())) {
            userList.add(u);
        }
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        fname_col.setCellValueFactory(new PropertyValueFactory<>("fname"));
        email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        bday_col.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        gender_col.setCellValueFactory(new PropertyValueFactory<>("gender"));
        num_col.setCellValueFactory(new PropertyValueFactory<>("num"));
        //colImage.setCellValueFactory(new PropertyValueFactory<>("image"));
        user_table.setItems(userList);
    }
    
}
