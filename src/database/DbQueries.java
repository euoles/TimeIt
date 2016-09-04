/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database;

import java.sql.*;        
import javax.swing.JOptionPane;

/**
 *
 * @author George
 */
public class DbQueries {
    
    private ResultSet rs;
    private PreparedStatement ps;
    private Statement st;
    
    
    
    DbConnection dbcon = new DbConnection();
    
    public DbQueries(){
        //on call just connect to database;
        //write all the details of the dbConnection constructor in this method
        //so that u cnt have too many classes
        
        //so now i have a connection
    }
    public ResultSet selectQuery(String str){
        
        try{
        rs = dbcon.statement.executeQuery(str);
        }catch(SQLException e){
             JOptionPane.showMessageDialog(null, e.getMessage());
        }        
        return rs;
    }
    
    public boolean saveQuery(String str){
        
        try{
        ps = dbcon.connection.prepareStatement(str);
        ps.executeUpdate();
        return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
        
    }
    public boolean updateQuery(String str){
        
        try{
            ps = dbcon.connection.prepareStatement(str);
            ps.executeUpdate();
            return true;            
        }catch(SQLException e){            
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean deleteQuery(String str){
        try{
            ps = dbcon.connection.prepareStatement(str);
            ps.executeUpdate();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    
    
}
