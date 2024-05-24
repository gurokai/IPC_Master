/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;

/**
 * FXML Controller class
 *
 * @author Crish
 */
public class MainWindowViewController implements Initializable {

    @FXML
    private Button createCategoryButton;
    @FXML
    private Button addExpenseButton;
    @FXML
    private Button deleteCategoryButton;
    @FXML
    private Button deleteExpenseButton;
    
    private Acount acount;
    @FXML
    private MenuItem printOption;
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
    }    

    @FXML
    private void createCategoryOnAction(ActionEvent event) throws IOException {
        FXMLLoader myFXMLLoader = new FXMLLoader(getClass().getResource("/view/CreateCategoryView.fxml"));
        Parent root = myFXMLLoader.load();
        // Access to the persona data
        //RegistrationController registrationController = myFXMLLoader.getController();
        /*Persona person = personTableView.getSelectionModel().getSelectedItem();
        personController.initPerson(person); // send the data*/
        Scene scene = new Scene(root,500,300);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Create Category View");
        stage.initModality(Modality.APPLICATION_MODAL);
        //the main window is not active
        stage.showAndWait();
    }

    @FXML
    private void addExpenseOnAction(ActionEvent event) throws IOException {
        FXMLLoader myFXMLLoader = new FXMLLoader(getClass().getResource("/view/AddExpenseView.fxml"));
        Parent root = myFXMLLoader.load();
        // Access to the persona data
        //RegistrationController registrationController = myFXMLLoader.getController();
        /*Persona person = personTableView.getSelectionModel().getSelectedItem();
        personController.initPerson(person); // send the data*/
        Scene scene = new Scene(root,500,300);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Expense View");
        stage.initModality(Modality.APPLICATION_MODAL);
        //the main window is not active
        stage.showAndWait();    
    
    }

    @FXML
    private void deleteCategoryOnAction(ActionEvent event) {
    
    }

    @FXML
    private void deleteExpenseOnAction(ActionEvent event) {
    
    }

    @FXML
    private void printOnAction(ActionEvent event) {
        
    }

    @FXML
    private void editUserOnAction(ActionEvent event) throws IOException {
        FXMLLoader myFXMLLoader = new FXMLLoader(getClass().getResource("/view/EditUserView.fxml"));
        Parent root = myFXMLLoader.load();
        
        EditUserViewController editUserViewController = myFXMLLoader.getController();
        editUserViewController.initPerson(acount);
        /*Persona person = personTableView.getSelectionModel().getSelectedItem();
        personController.initPerson(person); // send the data*/
        
        Scene scene = new Scene(root,500,300);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Edit User View");
        stage.initModality(Modality.APPLICATION_MODAL);
        //the main window is not active
        stage.showAndWait();
    }
    
}
