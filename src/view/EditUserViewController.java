/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view;
import view.RegistrationController.ImagenTabCell;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import model.Acount;
import model.AcountDAOException;
import model.User;

/**
 * FXML Controller class
 *
 * @author Crish
 */
public class EditUserViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Acount acount;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox<Image> profileImages;
    @FXML
    private TextField emailField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private Label passwordProgress;
    
    private User usi;
    private String imagePath;
    
    private FXMLLoader myFXMLLoader;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        javafx.scene.image.Image avatar1 = new javafx.scene.image.Image("/images/blackbird.png");
        javafx.scene.image.Image avatar2 = new javafx.scene.image.Image("/images/panda-bear.png");
        javafx.scene.image.Image avatar3 = new javafx.scene.image.Image("/images/walrus.png");
        profileImages.getItems().addAll(avatar1,avatar2 , avatar3);
        try {
            // TODO
                acount = Acount.getInstance();
        } catch (AcountDAOException ex) {
                Logger.getLogger(view.RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
                Logger.getLogger(view.RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }    

    @FXML
    private void passwordKeyPressed(KeyEvent event) {
    }

    @FXML
    private void okOnAction(ActionEvent event) {
    }

    @FXML
    private void cancelOnAction(ActionEvent event) {
    }
    class ImagenTabCell extends ComboBoxListCell<String> {
        private ImageView view = new ImageView();
        private javafx.scene.image.Image image;

        @Override
        public void updateItem(String t, boolean empty) {
            super.updateItem(t, empty); 
            if (t == null || empty) {
                setText(null);
                setGraphic(null);
            } else {
                image = new javafx.scene.image.Image(t,25,25,true,true);
                view.setImage(image);
                setGraphic(view);
                setText(null);
            }
        }
    }
    
    public void initPerson( Acount a){
        usi = a.getLoggedUser();
        nameField.textProperty().setValue(usi.getName());
        surnameField.textProperty().setValue(usi.getSurname());
        emailField.textProperty().setValue(usi.getEmail());
        passwordField.textProperty().setValue(usi.getPassword());
        profileImages.valueProperty().setValue(usi.getImage());
    }
}
