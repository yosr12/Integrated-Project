/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevjava.gui;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import pidevjava.services.UserService;
import static pidevjava.utils.PatternEmail.validate;

/**
 * FXML Controller class
 *
 * @author Abirn
 */
public class ResetPWDController implements Initializable {

    @FXML
    private Button changer_btn;
    @FXML
    private JFXTextField email_txt;
    @FXML
    private JFXPasswordField nv_txt;
    @FXML
    private JFXPasswordField ancien_txt;
    @FXML
    private JFXPasswordField confirm_txt;
    
    UserService us = new UserService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void ChangerMdp(ActionEvent event) throws SQLException {

        if (validateInputs()) {

            String email = email_txt.getText();
            String mdp = nv_txt.getText();
            
            us.changePassword(mdp, email);
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setHeaderText("Modifié");
            alert.setContentText("Mot de passe changé avec succès");
            alert.showAndWait();
        }
    }

    private boolean validateInputs() throws SQLException {

        if (email_txt.getText().isEmpty() || ancien_txt.getText().isEmpty() || nv_txt.getText().isEmpty() || confirm_txt.getText().isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Veuillez remplir tous les champs");
            alert1.setHeaderText("Controle de saisie");
            alert1.show();
            return false;
            
        } else if (!(nv_txt.getText().equals(confirm_txt.getText()))) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Veuillez vérifier votre nouveau mot de passe");
            alert2.setHeaderText(null);
            alert2.show();
            return false;

        } else if (nv_txt.getText().length() < 6) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Mot de passe doit dépasser les 6 caractères");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
            
        } else if (!(validate(email_txt.getText()))) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Veuillez vérifier votre email");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
        }
        return true;

    }
}
