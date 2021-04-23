/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import entite.Transport;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.ZoomEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import service.transportService;
import utils.DataSource;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author user
 */
public class TransportFXMLController implements Initializable {

    @FXML
    private TextField DescriptionField;
    @FXML
    private TextField DisponibiliteField;
    @FXML
    private TextField PriceField;
    @FXML
    private TextField TypeField;

    private javafx.scene.image.Image image;
    private FileChooser filechooser;
    private File file;
    private ImageView img1;
    int index = -1;
    FilteredList<Transport> filter = new FilteredList<>(getlistASC(), e -> true);
    SortedList<Transport> sort = new SortedList<>(filter);

    @FXML
    private Button parcourir;
    @FXML
    private TableView<Transport> Table_transport;
    @FXML
    private TableColumn<?, ?> descriptionTransport;
    @FXML
    private TableColumn<?, ?> disponibiliteTransport;
    @FXML
    private TableColumn<?, ?> priceTransport;
    @FXML
    private TableColumn<?, ?> typeTransport;
    private TextField IdField;
    @FXML
    private Button Modifier_Transport;
    @FXML
    private Button pdf;
    private Connection conn;
    @FXML
    private TextField chercher;

    public TransportFXMLController() throws SQLException {

    }

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

    @FXML
    private void Ajouter_Transport(ActionEvent event) throws SQLException {
        transportService ts = new transportService();

        if (controleTextFieldNonNumerique(DescriptionField) || controleTextFieldNonNumerique(DisponibiliteField) || controleTextFieldNonNumerique(TypeField)); else {
            Transport t = new Transport(DescriptionField.getText(), DisponibiliteField.getText(), ParseDouble(PriceField.getText()), TypeField.getText());

            ts.insert(t);
            System.out.println("Transport ajoutééé");
            TrayNotification tr = new TrayNotification();
            AnimationType type = AnimationType.POPUP;
            tr.setAnimationType(type);
            tr.setTitle("Transport");
            tr.setMessage("Created succefully");
            tr.setNotificationType(NotificationType.SUCCESS);
            tr.showAndDismiss(Duration.millis(5000));
        }

        try {
            javafx.scene.Parent tableview = FXMLLoader.load(getClass().getResource("TransportFXML.fxml"));
            Scene sceneview = new Scene(tableview);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(sceneview);
            window.show();
            AfficherTableASC();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public boolean controleTextFieldNonNumerique(TextField textField) {
        if (!textField.getText().matches(".*[a-zA-Z].*")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Veuillez saisir des lettres");
            alert.showAndWait();
            return true;
        }
        return false;
    }

    @FXML
    private void Modifier_Transport(ActionEvent event) throws SQLException {
        Transport t = new Transport();
        if (controleTextFieldNonNumerique(DescriptionField) || controleTextFieldNonNumerique(DisponibiliteField) || controleTextFieldNonNumerique(TypeField)); else {
            t.setId(Table_transport.getSelectionModel().getSelectedItem().getId());
            t.setDescription(DescriptionField.getText());
            t.setDisponibilite(DisponibiliteField.getText());
            t.setPrice(ParseDouble(PriceField.getText()));
            t.setType(TypeField.getText());
            transportService ts = new transportService();
            ts.update(t);

            try {
                javafx.scene.Parent tableview = FXMLLoader.load(getClass().getResource("TransportFXML.fxml"));
                Scene sceneview = new Scene(tableview);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(sceneview);
                window.show();
                AfficherTableASC();
                TrayNotification tr = new TrayNotification();
                AnimationType type = AnimationType.POPUP;
                tr.setAnimationType(type);
                tr.setTitle("Transport");
                tr.setMessage("Updated succefully");
                tr.setNotificationType(NotificationType.SUCCESS);
                tr.showAndDismiss(Duration.millis(5000));
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @FXML
    private void Parcourir_Transport(ActionEvent event) {
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
                //ImageField.setText(F);
                //     image = new javafx.scene.image.Image(file.toURI().toString(), 150, 100, true, true);
                //     img1.setImage(image);

            } else {
                JOptionPane.showMessageDialog(null, "Impossible d'ajouter");
            }
        });

    }

    @FXML
    public void getSelected() {
        index = Table_transport.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        DescriptionField.setText(String.valueOf(descriptionTransport.getCellData(index)));
        DisponibiliteField.setText(String.valueOf(disponibiliteTransport.getCellData(index)));
        PriceField.setText(String.valueOf(priceTransport.getCellData(index)));
        TypeField.setText(String.valueOf(typeTransport.getCellData(index)));

    }

    @FXML
    private void Supprimer_Transport(ActionEvent event) throws SQLException {
        transportService ts = new transportService();
        ts.delete(Table_transport.getSelectionModel().getSelectedItem().getId());
        try {
            javafx.scene.Parent tableview = FXMLLoader.load(getClass().getResource("TransportFXML.fxml"));
            Scene sceneview = new Scene(tableview);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(sceneview);
            window.show();
            AfficherTableASC();
            TrayNotification tr=new TrayNotification();
            AnimationType type=AnimationType.POPUP;
            tr.setAnimationType(type);
            tr.setTitle("Transport");
            tr.setMessage("Deleted succefully");
            tr.setNotificationType(NotificationType.SUCCESS);
            tr.showAndDismiss(Duration.millis(5000));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ObservableList<Transport> getlist() throws SQLException {
        transportService ts = new transportService();
        ObservableList<Transport> listTransport = FXCollections.observableArrayList(ts.readAll());
        return listTransport;
    }

    public void AfficherTable() throws SQLException {
        ObservableList<Transport> list = getlist();

        Table_transport.setItems(list);
        //idTransport.setCellValueFactory(new PropertyValueFactory<>("id"));
        descriptionTransport.setCellValueFactory(new PropertyValueFactory<>("description"));
        disponibiliteTransport.setCellValueFactory(new PropertyValueFactory<>("disponibilite"));
        priceTransport.setCellValueFactory(new PropertyValueFactory<>("price"));
        typeTransport.setCellValueFactory(new PropertyValueFactory<>("type"));

    }

    public static ObservableList<Transport> getlistASC() throws SQLException {
        transportService ts = new transportService();
        ObservableList<Transport> listTransport = FXCollections.observableArrayList(ts.readAll());
        return listTransport;
    }

    public void AfficherTableASC() throws SQLException {
        ObservableList<Transport> list = getlist();
        Table_transport.setItems(list);
        //idTransport.setCellValueFactory(new PropertyValueFactory<>("id"));
        descriptionTransport.setCellValueFactory(new PropertyValueFactory<>("description"));
        disponibiliteTransport.setCellValueFactory(new PropertyValueFactory<>("disponibilite"));
        priceTransport.setCellValueFactory(new PropertyValueFactory<>("price"));
        typeTransport.setCellValueFactory(new PropertyValueFactory<>("type"));

    }

    @FXML
    private void makePDF(ActionEvent event) throws FileNotFoundException, SQLException, IOException {
        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("C:/test/example.pdf"));
            doc.open();
            doc.add(new Paragraph(" "));
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 28, Font.UNDERLINE, BaseColor.BLACK);
            Paragraph p = new Paragraph("Transports ", font);
            p.setAlignment(Element.ALIGN_CENTER);
            doc.add(p);
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph(" "));

            PdfPTable tabpdf = new PdfPTable(4);
            tabpdf.setWidthPercentage(100);

            PdfPCell cell;
            cell = new PdfPCell(new Phrase("Description", FontFactory.getFont("Times New Roman")));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.WHITE);
            tabpdf.addCell(cell);

            cell = new PdfPCell(new Phrase("Disponibilite", FontFactory.getFont("Times New Roman")));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.WHITE);
            tabpdf.addCell(cell);

            cell = new PdfPCell(new Phrase("Prix", FontFactory.getFont("Times New Roman")));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.WHITE);
            tabpdf.addCell(cell);

            cell = new PdfPCell(new Phrase("Type", FontFactory.getFont("Times New Roman")));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.WHITE);
            tabpdf.addCell(cell);

            Connection conn;
            conn = DataSource.getInstance().getCnx();
            String req = "SELECT * FROM transport order by description ASC";
            PreparedStatement pst = conn.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                cell = new PdfPCell(new Phrase(rs.getString("description"), FontFactory.getFont("Times New Roman", 11)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.WHITE);
                tabpdf.addCell(cell);

                cell = new PdfPCell(new Phrase(rs.getString("disponibilite"), FontFactory.getFont("Times New Roman", 11)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.WHITE);
                tabpdf.addCell(cell);

                cell = new PdfPCell(new Phrase(rs.getString("price"), FontFactory.getFont("Times New Roman", 11)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.WHITE);
                tabpdf.addCell(cell);

                cell = new PdfPCell(new Phrase(rs.getString("type"), FontFactory.getFont("Times New Roman", 11)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.WHITE);
                tabpdf.addCell(cell);
            }
            doc.add(tabpdf);
            doc.close();
            TrayNotification tr = new TrayNotification();
            AnimationType type = AnimationType.POPUP;
            tr.setAnimationType(type);
            tr.setTitle("PDF File");
            tr.setMessage("Created succefully");
            tr.setNotificationType(NotificationType.SUCCESS);
            tr.showAndDismiss(Duration.millis(5000));
            Desktop.getDesktop().open(new File("C:/test/example.pdf"));
        } catch (DocumentException e) {
            System.out.println("ERROR PDF");
            System.out.println(Arrays.toString(e.getStackTrace()));
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void recherche() {
        chercher.setOnKeyReleased(e -> {
            chercher.textProperty().addListener((observable, oldValue, newValue) -> {
                filter.setPredicate(t -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (t.getType().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        return false;
                    }
                });

            });
            sort.comparatorProperty().bind(Table_transport.comparatorProperty());
            Table_transport.setItems(sort);
        });
    }
}
