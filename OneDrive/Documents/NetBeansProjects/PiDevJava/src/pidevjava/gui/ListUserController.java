/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevjava.gui;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Abirn
 */
public class ListUserController implements Initializable {

    @FXML
    private TableView<?> list_tbl;
    @FXML
    private TableColumn<?, ?> colImage;
    @FXML
    private TableColumn<?, ?> nom_col;
    @FXML
    private TableColumn<?, ?> prenom_col;
    @FXML
    private TableColumn<?, ?> email_col;
    @FXML
    private TableColumn<?, ?> bday_col;
    @FXML
    private TableColumn<?, ?> genre_col;
    @FXML
    private TableColumn<?, ?> tel_col;
    @FXML
    private ImageView rech_btn;
    @FXML
    private JFXTextField rech_txt;
    @FXML
    private Button delete_btn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void OnSelect(MouseEvent event) {
    }

    @FXML
    private void Rechercher(MouseEvent event) {
    }

    @FXML
    private void OnSupp(ActionEvent event) {
    }
    
}
