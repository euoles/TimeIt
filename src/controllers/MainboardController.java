/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author essence
 */
public class MainboardController implements Initializable {

    @FXML
    Button btnDashBoard;
    @FXML
    private Button btnEvents;
    @FXML
    private Button btnSettings;
    @FXML
    private Pane mainPane;
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  


    @FXML
    private void btnEventAction(ActionEvent event) throws IOException{
        Parent events = FXMLLoader.load(getClass().getResource("/ui/events.fxml"));
        if(mainPane.getChildren().isEmpty()){
            mainPane.getChildren().add(events);
        }else{
            mainPane.getChildren().clear();
            mainPane.getChildren().add(events);
        }
    }

    @FXML
    private void btnSettings(ActionEvent event) throws IOException {
        Parent settings = FXMLLoader.load(getClass().getResource("/ui/settings.fxml"));
        if(mainPane.getChildren().isEmpty()){
            mainPane.getChildren().add(settings);
        }else{
            mainPane.getChildren().clear();
            mainPane.getChildren().add(settings);
        }
    }

    @FXML
    private void btnDashboardActionEvent(ActionEvent event) throws IOException{
        Parent dashboard = FXMLLoader.load(getClass().getResource("/ui/dashboard.fxml"));
        if(mainPane.getChildren().isEmpty()){
            mainPane.getChildren().add(dashboard);
        }else{
            mainPane.getChildren().clear();
            mainPane.getChildren().add(dashboard);
        }
    }
    
    
    
    
    
}
