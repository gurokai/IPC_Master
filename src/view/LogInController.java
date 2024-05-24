/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view;

import view.RegistrationController;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;

/**
 * FXML Controller class
 *
 * @author Crish
 */
public class LogInController implements Initializable {

    @FXML
    private Button registrationButton;
    @FXML
    private Button loginButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField nicknameField;
    
    private Acount acount;
    @FXML
    private Label registerLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            acount = Acount.getInstance();
        } catch (AcountDAOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
        loginButton.disableProperty().bind(Bindings.or(passwordField.textProperty().isEmpty(), nicknameField.textProperty().isEmpty()));
    }    

    @FXML
    private void registerOnAction(ActionEvent event) throws IOException{
        FXMLLoader myFXMLLoader = new FXMLLoader(getClass().getResource("/view/Registration.fxml"));
        Parent root = myFXMLLoader.load();
        RegistrationController registrationController = myFXMLLoader.getController();
        /*Persona person = personTableView.getSelectionModel().getSelectedItem();
        personController.initPerson(person); // send the data*/
        Scene scene = new Scene(root,600,400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Person detailed view");
        stage.initModality(Modality.APPLICATION_MODAL);
        //the main window is not active
        stage.showAndWait();
        if(RegistrationController.getIsOkPressed()){
            registerLabel.textProperty().setValue("El más basado");
            registerLabel.setStyle("-fx-text-fill: #00FF00");
        }
    }

    @FXML
    private void loginOnAction(ActionEvent event) throws AcountDAOException, IOException {
        String nick = nicknameField.getText();
        String pass = passwordField.getText();
        /*if(nick.length() == 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("User not found");
                alert.setHeaderText("User not found");
                alert.setContentText("User was not found. Check that the credentials were properly written");
                alert.showAndWait();
        }*/
        if(nick.length() > 0 && pass.length() > 0){
            boolean logged = acount.logInUserByCredentials(nick, pass);
            if(!logged){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("User not found");
                alert.setHeaderText("User not found");
                alert.setContentText("User was not found. Check that the credentials were properly written.");
                alert.showAndWait();
            } else {
                
                FXMLLoader myFXMLLoader = new FXMLLoader(getClass().getResource("/view/MainWindowView.fxml"));
                Parent root = myFXMLLoader.load();
                // Access to the persona data
                //RegistrationController registrationController = myFXMLLoader.getController();
                /*Persona person = personTableView.getSelectionModel().getSelectedItem();
                personController.initPerson(person); // send the data*/
                Scene scene = new Scene(root,600,400);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Main Window View");
                stage.initModality(Modality.APPLICATION_MODAL);
                //the main window is not active
                stage.showAndWait();
            }
        }
    }
    
}
