/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entite.Hotel;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TableView<Hotel> Table_Hotel;
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
    private String filePath;
    FilteredList<Hotel> filter = new FilteredList<>(getlistHotel(), e -> true);
    SortedList<Hotel> sort = new SortedList<>(filter);

    int index = -1;
    @FXML
    private Pagination pagination;
    @FXML
    private TextField rechercher;
    @FXML
    private ComboBox<?> combo;
    @FXML
    private TableColumn<?, ?> id_promo;

    public HotelFXMLController() throws SQLException {

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            AfficherTable();
            combo.getItems().addAll(getlistPromo());
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

    public static ObservableList<Hotel> getlistHotel() throws SQLException {
        hotelService hs = new hotelService();
        ObservableList<Hotel> listhotel = FXCollections.observableArrayList(hs.readAll());
        return listhotel;
    }

    public static List getlistPromo() {
        hotelService hs = new hotelService();
        List listpromo = FXCollections.observableArrayList(hs.readidPromo());
        return listpromo;
    }

    public void AfficherTable() throws SQLException {
        ObservableList<Hotel> list = getlistHotel();
        Table_Hotel.setItems(list);
        //idHotel.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomHotel.setCellValueFactory(new PropertyValueFactory<>("nom"));
        adresseHotel.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        priceHotel.setCellValueFactory(new PropertyValueFactory<>("price"));
        imageHotel.setCellValueFactory(new PropertyValueFactory<>("image"));
        descriptionHotel.setCellValueFactory(new PropertyValueFactory<>("description"));
        datedebutHotel.setCellValueFactory(new PropertyValueFactory<>("datedebut"));
        datefinHotel.setCellValueFactory(new PropertyValueFactory<>("datefin"));
        id_promo.setCellValueFactory(new PropertyValueFactory<>("id_promo"));

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
        //combo.setValue(String.valueOf(id_promo.getCellData(index)));
    }

    @FXML
    private void Ajouter_Hotel(ActionEvent event) throws SQLException {
        hotelService ts = new hotelService();
        Double prix = ParseDouble(PriceField.getText());
        Date date_d = (Date.valueOf(Datedebut.getValue()));
        Date date_f = (Date.valueOf(Datefin.getValue()));
        int id = (Integer) combo.getValue();

        if (combo.getValue() != null) {
            Hotel t = new Hotel(NomField.getText(), AdresseField.getText(), prix, ImageField.getText(), DescriptionField.getText(), date_d, date_f, id);
            ts.insert(t);

        } else if(combo.getValue() == null){
            Hotel t = new Hotel(NomField.getText(), AdresseField.getText(), prix, ImageField.getText(), DescriptionField.getText(), date_d, date_f);
            ts.insertwithoutPromo(t);

        }
        System.out.println("hotel ajoutéé");
        try {
            javafx.scene.Parent tableview = FXMLLoader.load(getClass().getResource("HotelFXML.fxml"));
            Scene sceneview = new Scene(tableview);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(sceneview);
            window.show();
            AfficherTable();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void Modifier_Hotel(ActionEvent event) {
        try {

            Date date_d = (Date.valueOf(Datedebut.getValue()));
            Date date_f = (Date.valueOf(Datefin.getValue()));
            int id = Table_Hotel.getSelectionModel().getSelectedItem().getId();
            Hotel newH = new Hotel(id, NomField.getText(), AdresseField.getText(), ParseDouble(PriceField.getText()), ImageField.getText(), DescriptionField.getText(), date_d, date_f);
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

    @FXML
    private void Rechercher(ActionEvent event) {
        rechercher.setOnKeyReleased(e -> {
            rechercher.textProperty().addListener((observable, oldValue, newValue) -> {
                filter.setPredicate(h -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (h.getNom().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        return false;
                    }
                });

            });
            sort.comparatorProperty().bind(Table_Hotel.comparatorProperty());
            Table_Hotel.setItems(sort);
        });
    }

    @FXML
    private void afficherPromo(ActionEvent event) {
    }

}
