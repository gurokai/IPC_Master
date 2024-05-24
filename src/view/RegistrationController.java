/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view;

import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Acount;
import model.AcountDAOException;
import model.User;

/**
 * FXML Controller class
 *
 * @author Crish
 */
public class RegistrationController implements Initializable {

    @FXML
    private TextField emailField;
    private TextField nicknameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField nameField;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox<String> profileImages;
    @FXML
    private TextField surnameField;

    private Acount acount;
    
    private String nick;
    private String name;
    private String password;
    private String email;
    private String surname;
    private String imagePath;
    private boolean whiteExists;
    private static boolean isOkPressed = false;
    private Label nickProgress;
    @FXML
    private Label passwordProgress;
    
    private String alerta = null;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
                acount = Acount.getInstance();
        } catch (AcountDAOException ex) {
                Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
                Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        profileImages.getItems().addAll("/images/blackbird.png", "/images/panda-bear.png", "/images/walrus.png");
        profileImages.setCellFactory(c -> new ImagenTabCell());
        
        /*nicknameField.textProperty().addListener((observable, oldvalue, newvalue) -> {
            whiteExists(nick);
            if(whiteExists){
                nickProgress.textProperty().setValue("Incorrect");
            } else {
                nickProgress.textProperty().setValue("Correct");
            }
        });*/
        BooleanBinding aux = Bindings.or(emailField.textProperty().isEmpty(), nameField.textProperty().isEmpty());
        aux = Bindings.or(aux, surnameField.textProperty().isEmpty());
        aux = Bindings.or(aux, passwordField.textProperty().isEmpty());
        aux = Bindings.or(aux, nicknameField.textProperty().isEmpty());
        okButton.disableProperty().bind(aux);
    }    

    @FXML
    private void okOnAction(ActionEvent event) throws FileNotFoundException, AcountDAOException {
       isOkPressed = true;
       nick = nicknameField.textProperty().getValue();
       name = nameField.textProperty().getValue();
       email = emailField.textProperty().getValue();
       password = passwordField.textProperty().getValue();
       surname = surnameField.textProperty().getValue();
       imagePath = profileImages.getValue();
       alerta = null;
       if(imagePath == null){
           imagePath = "/images/panda-bear.png";
       }
       javafx.scene.image.Image avatar = new javafx.scene.image.Image(imagePath);
       
       if(password.length() < 6){
            alerta = "The password must be of minimum length 6.";
       }
       
       whiteExists(nick);
       if(whiteExists){
            if(alerta != null){
                alerta += "\nThe Nickname must not contain white spaces.";
            } else {
                alerta = "The Nickname must not contain white spaces.";
            }
       }
       
       if(acount.existsLogin(nick)){
           if(alerta != null){
                alerta += "\nThe Nickname must not be one from another user.";
            } else {
                alerta = "The Nickname must not be one from another user.";
            }
       } 
       if(nick.length() > 0 && password.length() > 5
               && email.length() > 0 && name.length() > 0 && surname.length() > 0){
           acount.registerUser(name, surname, email, nick, password, avatar, LocalDate.now());
           nameField.getScene().getWindow().hide();
       } else {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Wrong registration");
            //alert.setHeaderText("Inappropiate category");
           alert.setContentText(alerta);
           alert.showAndWait();
       }
    }
    @FXML
    private void cancelOnAction(ActionEvent event) {
        isOkPressed = false;
        nameField.getScene().getWindow().hide();
    }

    private void onKeyPressed(KeyEvent event) {
        nick = nicknameField.textProperty().getValue();
        whiteExists(nick);
        if(event.getCode() == KeyCode.SPACE || whiteExists){
            nickProgress.textProperty().setValue("Cabronazo");
            nickProgress.setStyle("-fx-text-fill: #FF0000");
        } else if(!whiteExists && nick.length() > 0){
            nickProgress.textProperty().setValue("Mu' bien");
            nickProgress.setStyle("-fx-text-fill: #00FF00");
        } else if(nick.length() == 0){
            nickProgress.textProperty().setValue("Deme algo");
            nickProgress.setStyle("-fx-text-fill: #FF0000");
        }
        
    }

    @FXML
    private void passwordKeyPressed(KeyEvent event) {
        password = passwordField.textProperty().getValue();
        if(password.length() > 5){
            passwordProgress.textProperty().setValue("Mu' bien");
            passwordProgress.setStyle("-fx-text-fill: #00FF00");
        } else {
            passwordProgress.textProperty().setValue("Deme más");
            passwordProgress.setStyle("-fx-text-fill: #FF0000");
        }
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
    private void whiteExists(String kaka){
        whiteExists = false;
        if(kaka != null){
           for(int i = 0; i < kaka.length() && !whiteExists; i++){
               if(nick.charAt(i) == ' '){
                    whiteExists = true;
                }
            }
        }
    }
    public static boolean getIsOkPressed(){
        return isOkPressed;
    }
}
