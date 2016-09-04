/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeit;

import controllers.MainboardController;
import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author essence
 */
public class TimeIt extends Application {
    
    private Stage mainStage;
    
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        mainStage = primaryStage;                
        gotoMainPage();
        mainStage.show();        
        
    }
    
    public void gotoMainPage() throws IOException {
        MainboardController mainBoardController =(MainboardController) replaceSceneContent("/ui/mainboard.fxml");
        
        
        
    }
    
    private Initializable replaceSceneContent(String fxml)throws IOException{
        
        FXMLLoader loader = new FXMLLoader();
        InputStream in = TimeIt.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(TimeIt.class.getResource(fxml));
        AnchorPane page;
        try{
            page = (AnchorPane)loader.load(in);
        }finally{
            in.close();
        }
        Scene scene = new Scene(page, 600, 610);       
        mainStage.setResizable(false);        
        mainStage.setScene(scene);        
        //mainStage.sizeToScene(); 
        return (Initializable)loader.getController();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
