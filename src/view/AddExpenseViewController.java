/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import java.lang.NumberFormatException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.Acount;
import model.AcountDAOException;
import model.Category;

/**
 * FXML Controller class
 *
 * @author Crish
 */
public class AddExpenseViewController implements Initializable {


    private Acount acount;
    @FXML
    private DatePicker datePicker;
    private LocalDate date;
    @FXML
    private TextField categoryField;
    private String category;
    @FXML
    private TextField costField;
    private double cost;
    @FXML
    private TextField titleField;
    private String title;
    @FXML
    private TextField descriptionField;
    private String description;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField unitsField;
    private int units;
    
    private Image scanImage = null;
    private String alerta = null;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            // TODO
                acount = Acount.getInstance();
        } catch (AcountDAOException ex) {
                Logger.getLogger(view.RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
                Logger.getLogger(view.RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        datePicker.setDayCellFactory((DatePicker picker) -> {
            return new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();
                    setDisable(empty || date.compareTo(today) < 0 );
                }
            };
        });
        BooleanBinding aux = Bindings.or(categoryField.textProperty().isEmpty(), titleField.textProperty().isEmpty());
        aux = Bindings.or(aux, descriptionField.textProperty().isEmpty());
        aux = Bindings.or(aux, unitsField.textProperty().isEmpty());
        aux = Bindings.or(aux, costField.textProperty().isEmpty());
        aux = Bindings.or(aux, datePicker.valueProperty().isNull());
        okButton.disableProperty().bind(aux);
    }  

    @FXML
    private void okOnAction(ActionEvent event) throws AcountDAOException {
        title = titleField.textProperty().getValue();
        date = datePicker.valueProperty().getValue();
        description = descriptionField.textProperty().getValue();
        alerta = null;
        char aux = costField.textProperty().getValue().charAt(costField.textProperty().getValue().length() - 1);
        if(aux == 'd' || aux == 'f'){
            alerta =  "Cost is not correct.";
        }
        try{
            cost = Double.parseDouble(costField.textProperty().getValue());
        } catch(NumberFormatException nfe) {
            if(alerta != null){
                alerta += "\nCost is not a number.";
            } else {
                alerta = "Cost is not a number.";
            }
        }
        try{
            units = Integer.parseInt(unitsField.textProperty().getValue());
        } catch(NumberFormatException nfe){
            if(alerta != null){
                alerta += "\nUnits are not a number.";
            } else {
                alerta = "Units are not a number.";
            }
        }
        category = categoryField.textProperty().getValue();
        List<Category> categories = acount.getUserCategories();
        Category categorie = null;
        for(int i = 0; i < categories.size() && categorie == null; i++){
            if(categories.get(i).getName().equals(category)){
                categorie = categories.get(i);
            }
        }
        if(categorie == null){
            if(alerta == null){
                alerta = "There is no category.";
            } else {
                alerta += "\nThere is no category.";
            }
        }
        if(alerta == null){
            acount.registerCharge(title, description, cost, units, scanImage, date, categorie);
            titleField.getScene().getWindow().hide();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong expense creation");
            //alert.setHeaderText("Inappropiate category");
            alert.setContentText(alerta);
            alert.showAndWait();
            //event.consume();
        }
    }

    @FXML
    private void cancelOnAction(ActionEvent event) {
        titleField.getScene().getWindow().hide();
    }

    @FXML
    private void scanOnAction(ActionEvent event) throws IOException {
        FileChooser jf = new FileChooser();
        jf.getExtensionFilters().addAll(
        new ExtensionFilter(".jpeg", ".png", ".gif", ".bmp"));
        File seleccion = jf.showOpenDialog(titleField.getScene().getWindow());
        if(seleccion != null){
            String url = seleccion.getCanonicalPath();
            scanImage = new Image(new FileInputStream(url));
        }
    }
}
