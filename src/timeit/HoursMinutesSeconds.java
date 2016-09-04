/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeit;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 *
 * @author essence
 */
public class HoursMinutesSeconds extends Application {

    final TextField txtsecond = new TextField("0");
    final Label lbtime = new Label("Time");
    final Label secondcountdown = new Label("countdown");

    @Override
    public void start(Stage primaryStage) {

        
        
        IntegerProperty timeseconds = new SimpleIntegerProperty(Integer.valueOf(txtsecond.getText()));
        

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), event -> setText(), new KeyValue(timeseconds, 0)));
        timeline.setCycleCount(Animation.INDEFINITE);
        //timeline.play();
        
        

        Button btn = new Button();
        btn.setText("Begin Timer");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {                
                timeline.play();                
            }
        });

        VBox vbmain = new VBox();
        vbmain.setSpacing(3.0);
        vbmain.getChildren().addAll(txtsecond, lbtime, secondcountdown, btn);

        StackPane root = new StackPane();

        root.getChildren().add(vbmain);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private String setText() {        
        //subtract from the initial value 
        
        Long fulltime = Long.valueOf(txtsecond.getText());
        long sec = TimeUnit.MILLISECONDS.toSeconds(fulltime) % TimeUnit.MINUTES.toSeconds(1);
        long min = TimeUnit.MILLISECONDS.toMinutes(fulltime) % TimeUnit.HOURS.toMinutes(1);
        long hr = TimeUnit.MILLISECONDS.toHours(fulltime);

        lbtime.setText("milsec: " + fulltime + "-> " + " - " + hr + "-" + min + "-" + sec);     
        System.out.println("milsec: " + fulltime + "-> " + " - " + hr + "-" + min + "-" + sec);     

        return "milsec: " + fulltime + "-> " + " - " + hr + "-" + min + "-" + sec;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void hms(long mseconds) {
        long millis = 3600000;
        
       
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
        
        

        System.out.println(hms);
   //     }

        //long millis = 3600000;
            /*
            
            
         String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
         TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
         TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
         */
    }

    public void cnvert(int seconds) {
        //take seconds
        System.out.println("hours: " + TimeUnit.SECONDS.toHours(seconds));
        System.out.println("minues: " + TimeUnit.SECONDS.toHours(seconds) % TimeUnit.SECONDS.toMinutes(1));

    }

    

}
