/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import database.DbQueries;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Activity;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author essence
 */
public class EventsController implements Initializable {

    @FXML
    private ComboBox<?> cmbActivities;
    @FXML
    private Button btnStartActivity;
    @FXML
    private Button btnDisplay;
    @FXML
    private TextField txtHrs;
    @FXML
    private TextField txtMins;
    @FXML
    private TextField txtElapseMins;
    @FXML
    private TextArea txtBroadCastMessage;
    @FXML
    private Button btnBroadCast;
    @FXML
    private Button btnStop;

    Integer STARTTIME;

    IntegerProperty timeSeconds;

    Timeline timeline = new Timeline();

    Stage stage = new Stage();

    Parent root;

    ObservableList<Activity> ActivityList = FXCollections.observableArrayList();
    ResultSet r;

    /*
     Declaring global values for Display    
     */
    Parent displayRoot;
    Text secondsTotal, remaingTime;
    Stage displayStage = new Stage();
    Scene displayScene;

    ObservableList<Node> displayChildren;

    /**
     * Initializes the controller class. set up just 1 Display page use those
     * values as global values so they can be accessed every where from code
     *
     *
     *
     *
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        timeSeconds = new SimpleIntegerProperty();

        loadActivities();
        try {
            root = FXMLLoader.load(getClass().getResource("/ui/display.fxml"));
        } catch (IOException e) {

        }

        /*
         working with global Display        
         */
        try {

            displayRoot = FXMLLoader.load(getClass().getResource("/ui/display.fxml"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        secondsTotal = new Text();
        remaingTime = new Text();
        displayChildren = displayRoot.getChildrenUnmodifiable();
        /*
         //- the children nodes are the main nodes (excluding the smaller ones in the main nodes)
        
         for (Node node : displayChildren) {
         if (node.getId().equalsIgnoreCase("txtdisplay")) {
         secondsTotal = (Text) node;
         }
         if (node.getId().equalsIgnoreCase("txtdisplay2")) {
         remaingTime = (Text) node;
         }
         }
         */

        VBox vb = new VBox();
        vb.setAlignment(Pos.CENTER);

        VBox tpCountdown = new VBox();       ///*

        for (Node node : displayChildren) {

            if (node.getId().equalsIgnoreCase("tpcountdown")) {
                tpCountdown = (VBox) node;
                ObservableList<Node> tpChildren = tpCountdown.getChildrenUnmodifiable();
                for (Node tpNode : tpChildren) {
                    if (tpNode.getId().equalsIgnoreCase("txtdisplay")) {
                        secondsTotal = (Text) tpNode;
                    }
                    if (tpNode.getId().equalsIgnoreCase("txtdisplay2")) {
                        remaingTime = (Text) tpNode;
                    }
                }
            }
        }
        //*/

        secondsTotal.textProperty().bind(timeSeconds.asString());
        TextChangeListener txtChangeListener = new TextChangeListener();
        secondsTotal.textProperty().addListener(txtChangeListener);

        displayScene = new Scene(displayRoot);

    }

    public void loadActivities() {
        DbQueries query = new DbQueries();
        String str = "select * from tb_activities";

        ObservableList activityNames = FXCollections.observableArrayList();

        r = query.selectQuery(str);

        try {

            if (r != null) {

                System.out.println("r.size: " + r.getFetchSize());
                int index = 0;
                //       r.first();

                while (r.next()) {
                    index = index + 1; //what is this 
                    Activity activity = new Activity();
                    activity.setId(r.getString("id"));
                    activity.setName(r.getString("name"));
                    activity.setDescription(r.getString("description"));
                    activity.setDuration(r.getString("duration"));

                    ActivityList.add(activity);
                    activityNames.add(activity.getName());
                };
            }

            cmbActivities.setItems(activityNames);

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }
    /*
     private void onAction(ActionEvent event) {
        
     System.out.println("onAction;");

     String selection = cmbActivities.getSelectionModel().getSelectedItem().toString();
     ObservableList ob = FXCollections.observableArrayList();

     for (Iterator<Activity> it = ActivityList.iterator(); it.hasNext();) {
     Activity a = it.next();
     if (a.getName().equalsIgnoreCase(selection)) {
     setDuration(a.getDuration());
     }
     }

     }
     */

    @FXML
    private void btnStartActivityAction(ActionEvent event) {
        /*
        
         after click set to pause to allow u pause the time
         start the timer
         validate all fields for data before running this function
        
         */

        if (cmbActivities.getSelectionModel().getSelectedIndex() != -1) {

            btnStop.setDisable(false);

            int pausedTime;
            Duration dur = null;

            if (btnStartActivity.getText().equalsIgnoreCase("start")) {

                STARTTIME = Integer.parseInt(getDuration());

                //Timeline timeline = new Timeline();
                timeSeconds.set(STARTTIME);

                //why this method ? understand it
                if (timeline != null) {
                    System.out.println("just run timeline!=null");
                    timeline.stop();

                }

                timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(STARTTIME + 1), events -> setText(), new KeyValue(timeSeconds, 0)));
                timeline.playFromStart();
                btnStartActivity.setText("Pause");

            } else if (btnStartActivity.getText().equalsIgnoreCase("pause")) {
                //change the text to pause
                timeline.pause();
                pausedTime = timeSeconds.get();
                System.out.println("Paused at: " + timeSeconds.get());
                dur = new Duration(pausedTime);
                btnStartActivity.setText("Resume");

            } else if (btnStartActivity.getText().equalsIgnoreCase("resume")) {
                //this happens when i click on resume

                System.out.println("Resumed at: " + dur);
                timeline.play();
                btnStartActivity.setText("Pause");

            }
        } else {
            System.out.println("Sorry nothing as been selected");
        }

    }//end start activity

    //setText currently not working, find out why
    private String setText() {
        //subtract from the initial value 
        System.out.println("blah blah blah");

        JOptionPane jp = new JOptionPane();
        jp.setVisible(true);

        /*
         Long fulltime = Long.valueOf(timeSeconds.getText());
         long sec = TimeUnit.MILLISECONDS.toSeconds(fulltime) % TimeUnit.MINUTES.toSeconds(1);
         long min = TimeUnit.MILLISECONDS.toMinutes(fulltime) % TimeUnit.HOURS.toMinutes(1);
         long hr = TimeUnit.MILLISECONDS.toHours(fulltime);
         lbtime.setText("milsec: " + fulltime + "-> " + " - " + hr + "-" + min + "-" + sec);     
         */
        return "";
    }

    @FXML
    private void btnDisplay(ActionEvent event) throws IOException {

        btnDisplayFunction2();

    }

    public void setDuration(String duration) {
        //break into hourse and mintues
        int time = Integer.parseInt(duration);
        int mins = time % 60;
        int hrs = (time - mins) / 60;

        txtHrs.setText(String.valueOf(hrs));
        txtMins.setText(String.valueOf(mins));
    }

    public String getDuration() {
        //validate the values 
        //make sure values are non alphabets or empty string

        int hrs = 0;
        int mins = 0;
        int elapse = 0;

        //biger validate for alphabets
        ChangeListener<String> checkNonInt = new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    String replaceAll = newValue.replaceAll("[^\\d]", "");
                }

            }
        };

        txtHrs.textProperty().addListener(checkNonInt);
        txtMins.textProperty().addListener(checkNonInt);
        txtElapseMins.textProperty().addListener(checkNonInt);

        if (!txtHrs.getText().isEmpty()) {

            hrs = Integer.parseInt(txtHrs.getText()) * 60;
        }

        if (!txtMins.getText().isEmpty()) {
            mins = Integer.parseInt(txtMins.getText());
        }

        if (!txtElapseMins.getText().isEmpty()) {
            elapse = Integer.parseInt(txtElapseMins.getText());
        }

        int time = ((hrs + mins) - elapse) * 60; // minus - the elapse time

        //returns seconds
        return String.valueOf(time);

    }

    @FXML
    private void btnBroadCastAction(ActionEvent event) {
        
    }

    @FXML
    private void btnStopAction(ActionEvent event) {
        /*
         - end the current running activity
         - set duration to initial values
         - u can use ths for switch tab.
         - take the global view
         */
        timeline.stop();
        btnStartActivity.setText("Start");
        btnStop.setDisable(true);
    }

    @FXML
    private void onCmbActivityChanged(ActionEvent event) {
        /*
         - is cmbActivityAction
         */
        System.out.println("cmbActivity Value has changed");

        String selection = cmbActivities.getSelectionModel().getSelectedItem().toString();
        ObservableList ob = FXCollections.observableArrayList();

        for (Iterator<Activity> it = ActivityList.iterator(); it.hasNext();) {
            Activity a = it.next();
            if (a.getName().equalsIgnoreCase(selection)) {
                setDuration(a.getDuration());
            }
        }
    }

    class TextChangeListener implements ChangeListener {

        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {

            //Long fulltime = Long.valueOf(newValue) * 1000;
            Long fulltime = Long.valueOf(newValue.toString()) * 1000;

            long sec = TimeUnit.MILLISECONDS.toSeconds(fulltime) % TimeUnit.MINUTES.toSeconds(1);
            long min = TimeUnit.MILLISECONDS.toMinutes(fulltime) % TimeUnit.HOURS.toMinutes(1);
            long hr = TimeUnit.MILLISECONDS.toHours(fulltime);
            System.out.println("Remaining : " + hr + " - " + min + " - " + sec);
            remaingTime.setText(hr + " - " + min + " - " + sec);

            /*
             - change txt2 to global textfield 
             - 
            
             */
        }

    }

    public void btnDisplayFunction2() {

        /*
         - check if there is already a display showing 
         - if not create new scene
         - else 
         - reset the values of the scene already to new values 
         - 
         */
        if (displayStage.isShowing()) { //rather check if it is showing not if it is null
            System.out.println("There is currently a display running");
        } else {

            /*
             for (Screen screen : Screen.getScreens()) {

             Rectangle2D bounds = screen.getVisualBounds();

             displayStage.setX(bounds.getMaxX());
             displayStage.setY(bounds.getMaxY());

             }
            
             */
            ObservableList screens = Screen.getScreens();
            Screen primaryScreen, secondaryScreen;

            if (screens.size() <= 1) {
                System.out.println("Number of Screens " + screens.size());
                primaryScreen = (Screen) screens.get(0);
                configureAndShowStage(primaryScreen);
            } else {
                System.out.println("Number of Screens " + screens.size());
                primaryScreen = (Screen) screens.get(0);
                secondaryScreen = (Screen) screens.get(1);
                configureAndShowStage(secondaryScreen);
            }

        }

    }

    public void configureAndShowStage(Screen screen) {
        /*
         - this shows the stage on the secondary screen        
         */

        Rectangle2D bounds = screen.getBounds();
        displayStage.setX(bounds.getMinX());
        displayStage.setY(bounds.getMinY());

        displayStage.setScene(displayScene);
        displayStage.show();

    }

}


/*
 ERROR

 - lost on going activity when u switch tabs and come back
 - the display button cant come on twice (fixed) 
 - write for start activity (resume, pause but not start) (done)
 - implement stop activity  (done)
 - change database to sqlite (done)
   


 */
