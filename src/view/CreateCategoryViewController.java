/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Acount;
import model.AcountDAOException;
import model.Category;

/**
 * FXML Controller class
 *
 * @author Crish
 */
public class CreateCategoryViewController implements Initializable {

    @FXML
    private Button createButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField descriptionField;
    @FXML
    private Button cancelButton;

    private Acount acount;
    private String name;
    private String description;
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
        createButton.disableProperty().bind(Bindings.or(nameField.textProperty().isEmpty(), descriptionField.textProperty().isEmpty()));
    }    

    @FXML
    private void createOnAction(ActionEvent event) throws AcountDAOException {
        name = nameField.textProperty().getValue();
        description = descriptionField.textProperty().getValue();
        List<Category> categories = acount.getUserCategories();
        boolean bingin = false;
        for(int i = 0; i < categories.size() && !bingin; i++){
            if(categories.get(i).getName().equals(name)){
                alerta = "Category provided already exists.";
                bingin = true;
            }
        }
        
        if(name.charAt(0) < 65 || name.charAt(0) > 90){
            alerta = "Name category must start at capital letter.";
            bingin = true;
        } else {
            for(int i = 0; i < categories.size() && !bingin; i++){
                if(categories.get(i).getName().equals(name)){
                    alerta = "Category provided already exists.";
                    bingin = true;
                }
            }
            if(!bingin){
                acount.registerCategory(name, description);
                //nameField.getScene().setCursor(Cursor.WAIT);
                nameField.getScene().getWindow().hide();
            }
        }
        if(bingin){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Impropper name");
            //alert.setHeaderText("Eres tonto");
            alert.setContentText(alerta);
            alert.showAndWait();
            event.consume();
        }
    }

    @FXML
    private void cancelOnAction(ActionEvent event) {
        nameField.getScene().getWindow().hide();
    }
    
}
