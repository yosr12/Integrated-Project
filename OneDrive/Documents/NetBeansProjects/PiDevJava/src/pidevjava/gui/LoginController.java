/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevjava.gui;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
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
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import org.controlsfx.control.Notifications;
import pidevjava.entities.Admin;
import pidevjava.entities.User;
import pidevjava.services.AdminService;
import pidevjava.services.UserService;
import pidevjava.utils.BCrypt;
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
    AdminService as = new AdminService();
    @FXML
    private JFXRadioButton admin_rb;
    @FXML
    private JFXRadioButton user_rb;
    private boolean admin;
    private boolean user;
    
    public static int userid;
    public static User usr;

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
        if(user){
        try {
            if (validateInputs()) {
                UserService us = new UserService();
                String email = login_txt.getText();
                String mdp = pwd_login_txt.getText();
                User u = us.searchByPseudoPassU(email, mdp);
                System.out.println(u);
                if (u != null && BCrypt.checkpw(pwd_login_txt.getText(), u.getPassword())) {
                    us.loggedIn(u);
                    NavigationEntreInterfaces nav = new NavigationEntreInterfaces();
                    nav.navigate(event, "Sidebar", "/pidevjava/gui/Sidebar.fxml");
                    System.out.println(u.getImage());
                    //API Notification lors de l'ajout d'un evenement
                    Notifications notificationBuilder = Notifications.create()
                            .title("Welcome  " + login_txt.getText())
                            .text("Bienvenue à Tabaani Travel Agency")
                            .hideAfter(javafx.util.Duration.seconds(4))
                            .position(Pos.TOP_CENTER);
                    notificationBuilder.show();
                     userid=u.getId();
                     usr=u;
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
        else if (admin){
             try {
            if (validateInputs()) {
                String email = login_txt.getText();
                String mdp = pwd_login_txt.getText();
                Admin a = as.searchByPseudoPassU(email, mdp);
                System.out.println(a);
                if (a != null && BCrypt.checkpw(pwd_login_txt.getText(), a.getPassword())) {
                    as.loggedIn(a);
                    NavigationEntreInterfaces nav = new NavigationEntreInterfaces();
                    nav.navigate(event, "Sidebar", "/pidevjava/gui/Back.fxml");
                    System.out.println(a.getImage());
                    //API Notification lors de l'ajout d'un evenement
                    Notifications notificationBuilder = Notifications.create()
                            .title("Welcome  " + login_txt.getText())
                            .text("Bienvenue à Tabaani Travel Agency")
                            .hideAfter(javafx.util.Duration.seconds(4))
                            .position(Pos.TOP_CENTER);
                    notificationBuilder.show();
                   
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

    @FXML
    private void loginAdmin(ActionEvent event) throws IOException {
        user=false;
        admin=true; 
    }

    @FXML
    private void loginUser(ActionEvent event) throws IOException {
        user=true;
        admin=false; 
    }
}
