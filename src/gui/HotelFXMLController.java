/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entite.hotel;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ZoomEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import service.hotelService;

/**
 * FXML Controller class
 *
 * @author user
 */
public class HotelFXMLController implements Initializable {

    @FXML
    private TextField NomField;
    @FXML
    private TextField AdresseField;
    @FXML
    private TableView<hotel> Table_Hotel;
    @FXML
    private TableColumn<?, ?> nomHotel;
    @FXML
    private TableColumn<?, ?> adresseHotel;
    @FXML
    private TableColumn<?, ?> priceHotel;
    @FXML
    private TableColumn<?, ?> imageHotel;
    @FXML
    private TableColumn<?, ?> descriptionHotel;
    @FXML
    private TableColumn<?, ?> datedebutHotel;
    @FXML
    private TableColumn<?, ?> datefinHotel;
    @FXML
    private Button Ajouter_hotel;
    @FXML
    private TextField PriceField;
    @FXML
    private TextField ImageField;
    @FXML
    private TextField DescriptionField;
    @FXML
    private DatePicker Datedebut;
    @FXML
    private DatePicker Datefin;
    @FXML
    private Button Modifier_hotel;
    @FXML
    private Button Supprimer_hotel;
    @FXML
    private Button parcourir;
    private javafx.scene.image.Image image;
    private FileChooser filechooser;
    private File file;
    
    int index = -1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       try {
            AfficherTable();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void dut(ZoomEvent event) {
    }

    double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch (Exception e) {
                return -1;   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        } else {
            return 0;
        }
    }    
    public ObservableList<hotel> getlist() throws SQLException {
        hotelService hs = new hotelService();
        ObservableList<hotel> listhotel = FXCollections.observableArrayList(hs.readAll());
        return listhotel;
    }

    public void AfficherTable() throws SQLException {
        ObservableList<hotel> list = getlist();
        Table_Hotel.setItems(list);
        //idHotel.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomHotel.setCellValueFactory(new PropertyValueFactory<>("nom"));
        adresseHotel.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        priceHotel.setCellValueFactory(new PropertyValueFactory<>("price"));
        imageHotel.setCellValueFactory(new PropertyValueFactory<>("image"));
        descriptionHotel.setCellValueFactory(new PropertyValueFactory<>("description"));
        datedebutHotel.setCellValueFactory(new PropertyValueFactory<>("datedebut"));
        datefinHotel.setCellValueFactory(new PropertyValueFactory<>("datefin"));

    }

    @FXML
    private void getSelected(MouseEvent event) {
        index = Table_Hotel.getSelectionModel().getSelectedIndex();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (index <= -1) {
            return;
        }
        NomField.setText(String.valueOf(nomHotel.getCellData(index)));
        AdresseField.setText(String.valueOf(adresseHotel.getCellData(index)));
        PriceField.setText(String.valueOf(priceHotel.getCellData(index)));
        ImageField.setText(String.valueOf(imageHotel.getCellData(index)));
        DescriptionField.setText(String.valueOf(descriptionHotel.getCellData(index)));
        Datedebut.setValue(LocalDate.parse(datedebutHotel.getCellData(index).toString(), formatter));
        Datefin.setValue(LocalDate.parse(datefinHotel.getCellData(index).toString(), formatter));
    }
    


    @FXML
    private void Ajouter_Hotel(ActionEvent event) {
        hotelService ts = new hotelService();
        Double prix = ParseDouble(PriceField.getText());
        Date date_d = (Date.valueOf(Datedebut.getValue()));
        Date date_f = (Date.valueOf(Datefin.getValue()));
        hotel t = new hotel(NomField.getText(), AdresseField.getText(), prix, ImageField.getText(), DescriptionField.getText(), date_d, date_f);

        ts.insert(t);
        System.out.println("hotel ajoutéé");
        try {
            AfficherTable();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void Modifier_Hotel(ActionEvent event) {
        try {

            Date date_d = (Date.valueOf(Datedebut.getValue()));
            Date date_f = (Date.valueOf(Datefin.getValue()));
            int id = Table_Hotel.getSelectionModel().getSelectedItem().getId();
            hotel newH = new hotel(id, NomField.getText(), AdresseField.getText(), ParseDouble(PriceField.getText()), ImageField.getText(), DescriptionField.getText(), date_d, date_f);
            hotelService ts = new hotelService();
            ts.update(newH);
            AfficherTable();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void Supprimer_Hotel(ActionEvent event) throws SQLException {
        hotelService hs = new hotelService();
        hs.delete(Table_Hotel.getSelectionModel().getSelectedItem().getId());
        try {
            javafx.scene.Parent tableview = FXMLLoader.load(getClass().getResource("HotelFXML.fxml"));
            Scene sceneview = new Scene(tableview);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(sceneview);
            window.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        AfficherTable();
    }

    @FXML
    private void Parcourir_Hotel(ActionEvent event) {
        Stage primaryStage = new Stage();
        primaryStage.onShowingProperty();
        primaryStage.setTitle("selectionner une image !!!");
        filechooser = new FileChooser();
        filechooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files ", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        parcourir.setOnAction(e -> {
            file = filechooser.showOpenDialog(primaryStage);
            if (file != null) {
                //String s = file.getAbsolutePath();
                String F = file.toURI().toString();
                ImageField.setText(F);
           //     image = new javafx.scene.image.Image(file.toURI().toString(), 150, 100, true, true);
           //     img1.setImage(image);

            } else {
                JOptionPane.showMessageDialog(null, "Impossible d'ajouter");
            }
        });
    }
    
}
