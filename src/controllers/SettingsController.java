/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import database.DbQueries;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import models.Activity;

/**
 * FXML Controller class
 *
 * @author essence
 */
public class SettingsController implements Initializable {

    @FXML
    private Button btnAddActivity;
    @FXML
    private TextField txtActivityName;
    @FXML
    private TextField txtHours;
    @FXML
    private TextField txtMinutes;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnSaveActivity;
    @FXML
    private TextArea txtDescription;
    @FXML
    private Text txtActivityID;

    //initialize all data u want in the program
    DbQueries queries = new DbQueries();
    ResultSet r;
    @FXML
    private TableView<?> tbActivities;
//    
//    private TableColumn<?, ?> colID;
//    
//    private TableColumn<?, ?> colActivity;
//    
//    private TableColumn<?, ?> colduration;
//    
//    private TableColumn<?, ?> coldescription;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadActivities();
    }

    @FXML
    private void btnAddActivityAction(ActionEvent event) {

        int recid = 0;
        //check for the last recordn add the new record
        String str = "select * from tb_activities";
        r = queries.selectQuery(str);
        try {
            if (r != null) {
                if (r.last()) {
                    recid = Integer.parseInt(r.getString("id"));
                    recid = recid + 1;
                    txtActivityID.setText(String.valueOf(recid));
                } else {
                    recid = recid + 1;
                    txtActivityID.setText(String.valueOf(recid));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void btnDeteteAction(ActionEvent event) {
        //delete with id
        String str = "delete from tb_activities where id='"+getActivityID()+"'";
        DbQueries queries = new DbQueries();
        
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> op = alert.showAndWait();
            if(op.get() == ButtonType.OK){
                queries.deleteQuery(str);
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.show();
            }
            
            loadActivities();
        

    }

    @FXML
    private void btnSaveActivityAction(ActionEvent event) {
        //save the details into the database
        //make sure id values are nt empty
        
        saveActivity();        
        loadActivities();

    }
    
    public void saveActivity(){
        DbQueries queries = new DbQueries();
        String str = "insert into tb_activities  ( name,duration,description ) values  ("
                + "'"+getActivityName()+"',"
                + "'"+Integer.parseInt(getDuration())+"',"
                + "'"+getDescription()+"')";
        
        if(queries.saveQuery(str)){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.show();
        }else{
            Alert alert  = new Alert(Alert.AlertType.ERROR);
            alert.show();
        }
    }
    
    public void loadActivities(){
        
        TableColumn colID = new TableColumn("id");
        TableColumn colActivity = new TableColumn("Activity");
        TableColumn coldescription = new TableColumn("Description");
        TableColumn colduration = new TableColumn("Duration");
        
        tbActivities.getColumns().addAll(colID, colActivity,colduration,coldescription);
         
        colID.setCellValueFactory(new PropertyValueFactory<Activity, String>("id"));        
        colActivity.setCellValueFactory(new PropertyValueFactory<Activity, String>("name"));        
        colduration.setCellValueFactory(new PropertyValueFactory<Activity, String>("duration"));
        coldescription.setCellValueFactory(new PropertyValueFactory<Activity, String>("description"));

//        ObservableList data = FXCollections.observableArrayList();
//        TableColumn idCol = new TableColumn("ID");
//        TableColumn surnameCol = new TableColumn("Surname");
//        TableColumn othernamesCol = new TableColumn("Other Names");
//        TableColumn gendarCol = new TableColumn("Gendar");
//            
//        tbActivities.getColumns().addAll(idCol, surnameCol, othernamesCol, gendarCol);
//
//        idCol.setCellValueFactory(new PropertyValueFactory<Activity, String>("id"));
//        surnameCol.setCellValueFactory(new PropertyValueFactory<Activity, String>("name"));
//        othernamesCol.setCellValueFactory(new PropertyValueFactory<Activity, String>("duration"));
//        gendarCol.setCellValueFactory(new PropertyValueFactory<Activity, String>("desription"));
//        

        
        
        DbQueries queries = new DbQueries();
        String str = "select * from tb_activities";
        r = queries.selectQuery(str);
        Activity activity;
        ObservableList activities = FXCollections.observableArrayList();
        
        try{
        
        if(r != null){
         //   r.first();
           /* 
            do{
                activity = new Activity();
                activity.setId(r.getString("id"));
                activity.setName(r.getString("name"));
                activity.setDuration(r.getString("duration"));
                activity.setDescription(r.getString("description"));
                activities.add(activity);
               // data.add(activity);
                System.out.println("records: "+activity.toString());               
                
            }while(!r.ast());
                   
           */
            
            while(r.next()){
                activity = new Activity();
                activity.setId(r.getString("id"));
                activity.setName(r.getString("name"));
                activity.setDuration(r.getString("duration"));
                activity.setDescription(r.getString("description"));
                activities.add(activity);
               // data.add(activity);
                System.out.println("records: "+activity.toString());               
                
            }
            
            
            //tbActivities.setItems(data);
            tbActivities.setItems(activities);
        }
        }catch(SQLException e){
            e.printStackTrace();
        }
       
    }
    
    public String getActivityID(){
        return txtActivityID.getText();
    }
    
    public String getActivityName(){
        return txtActivityName.getText();
    }
    public String getDescription(){
        return txtDescription.getText();
    }
    public String getDuration(){
        //validate the values 
        //make sure values are non alphabets or empty string
        
        int hrs =0;
        int mins = 0;
        

         hrs = Integer.parseInt(txtHours.getText()) * 60;        
         mins = Integer.parseInt(txtMinutes.getText());
        
        int time = hrs + mins;
        
        String duration = String.valueOf(time);
        return duration;
    }
    
    
    public void setActivityName(String name){
        txtActivityName.setText(name);
    }
    public void setDuration(String duration){
        //break into hourse and mintues
        int time = Integer.parseInt(duration);
        int mins = time % 60;
        int hrs = (time - mins)/60;
        
        txtHours.setText(String.valueOf(hrs));
        txtMinutes.setText(String.valueOf(mins));        
    }
    public void setActivityID(String id){
        txtActivityID.setText(id);
    }
    public void setDescription(String desc){
        txtDescription.setText(desc);
    }

    @FXML
    private void tableClicked(MouseEvent event) {
        
//        ObservableList<Activity> ob = (ObservableList) tbActivities.getSelectionModel().getSelectedItem();
        Activity act = (Activity) tbActivities.getSelectionModel().getSelectedItem();
        System.out.println("values: "+act.toString());
        
        setActivityID(act.getId());
        setActivityName(act.getName());
        setDuration(act.getDuration());
        setDescription(act.getDescription());        
        
    }
    
    
    

    
    
    

}
