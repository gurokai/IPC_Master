/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import model.Acount;
import model.AcountDAOException;

/**
 * FXML Controller class
 *
 * @author Crish
 */
public class MainWindowViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Acount acount;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
                acount = Acount.getInstance();
        } catch (AcountDAOException ex) {
                Logger.getLogger(view.RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
                Logger.getLogger(view.RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    
}
