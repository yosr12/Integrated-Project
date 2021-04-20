/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevjava.gui;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import pidevjava.utils.NavigationEntreInterfaces;

/**
 * FXML Controller class
 *
 * @author Abirn
 */
public class RegisterController implements Initializable {

    @FXML
    private ImageView import_btn;
    @FXML
    private Button back_btn;
    @FXML
    private ImageView image_vi;
    @FXML
    private JFXTextField name_txt;
    @FXML
    private JFXTextField fname_txt;
    @FXML
    private JFXDatePicker bday_dtp;
    @FXML
    private JFXRadioButton homme_rb;
    @FXML
    private ToggleGroup genre_tg;
    @FXML
    private JFXRadioButton femme_rb;
    @FXML
    private JFXTextField email_txt;
    @FXML
    private JFXTextField num_txt;
    @FXML
    private JFXPasswordField pwd_txt;
    @FXML
    private JFXPasswordField confirm_txt;
    @FXML
    private Button confirm_btn1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void chooseImage(MouseEvent event) {
    }

    @FXML
    private void back(ActionEvent event)throws IOException {
        NavigationEntreInterfaces nav = new NavigationEntreInterfaces();
        nav.navigate(event, "test", "/pidevjava/gui/AccueilPage.fxml");
    }

    @FXML
    private void EnvoyerCode(ActionEvent event) {
    }
    
}
