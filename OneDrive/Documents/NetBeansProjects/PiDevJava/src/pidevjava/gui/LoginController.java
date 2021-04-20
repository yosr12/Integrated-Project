/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevjava.gui;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import pidevjava.entities.User;
import pidevjava.services.UserService;
import pidevjava.utils.MyCnx;
import pidevjava.utils.NavigationEntreInterfaces;

/**
 * FXML Controller class
 *
 * @author Abirn
 */
public class LoginController implements Initializable {

    @FXML
    private Button back_btn;
    @FXML
    private Button oublie_btn;
    @FXML
    private Button ggl_btn;
    @FXML
    private Button twitter_btn;
    @FXML
    private Button fb_btn;
    @FXML
    private Button login_btn;
    @FXML
    private JFXTextField login_txt;
    @FXML
    private JFXPasswordField pwd_login_txt;

    UserService us = new UserService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        NavigationEntreInterfaces nav = new NavigationEntreInterfaces();
        nav.navigate(event, "test", "/pidevjava/gui/AccueilPage.fxml");
    }

    @FXML
    private void OnOublie(ActionEvent event) throws IOException {
        NavigationEntreInterfaces nav = new NavigationEntreInterfaces();
        nav.navigate(event, "test", "/pidevjava/gui/RecupMDP.fxml");
    }

    @FXML
    private void gglLogin(ActionEvent event) {
    }

    @FXML
    private void twitterLogin(ActionEvent event) {
    }

    @FXML
    private void fbLogin(ActionEvent event) {
    }

    @FXML
    private void OnLogin(ActionEvent event) throws IOException {
        try {
            if (validateInputs()) {

                String email = login_txt.getText();
                String mdp = pwd_login_txt.getText();
                User u = us.searchByPseudoPassU(email, mdp);

                if (u != null) {
                    us.loggedIn(u);
                    NavigationEntreInterfaces nav = new NavigationEntreInterfaces();
                    nav.navigate(event, "test", "/pidevjava/gui/Sidebar.fxml");

                } else {
                    Alert alert2 = new Alert(Alert.AlertType.WARNING);
                    alert2.setTitle("Erreur");
                    alert2.setContentText("Veuillez vérifier votre email ou mot de passe");
                    alert2.setHeaderText(null);
                    alert2.show();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean validateInputs() throws SQLException {

        if (login_txt.getText().isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Veuillez saisir votre email");
            alert1.setHeaderText("Controle de saisie");
            alert1.show();
            return false;
        }
        if (pwd_login_txt.getText().isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Veuillez saisir votre mot de passe");
            alert1.setHeaderText("Controle de saisie");
            alert1.show();
            return false;
        }
        return true;

    }
}
