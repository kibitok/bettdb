/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bettdb;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private Label name, email, number;
     @FXML
    private TextField entername, enteremail, enternumber;
     
     
     
      final String JDBC_DRIVER = "org.sqlite.JDBC";  
    final String DB_URL = "jdbc:sqlite:profile.db";

   //  Database credentials
    final String USER = "username";
   static final String PASS = "password";
   
   private final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS profile('id'integer primary key autoincrement, 'name'text, 'email'text, 'phone'text)";

  private final String write = "insert into profile(name, email, phone) values(?,?,?)";
      
  Connection conn = null;
  Statement stmt = null;
     
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            //method to connect with database and create table
            connectdb();
            
       //updatedb (1,"Levit", 30);
       for(int i = 0; i<50;i++){
       //writedb("Name"+i,i);
       }
      // deletedb();
       readdb();
       
        } catch (ClassNotFoundException ex) {} catch (SQLException ex) {}
       
    }    

    @FXML
    private void save(ActionEvent event) throws SQLException {
        writedb();
        
         readdb();
    }

    @FXML
    private void clear(ActionEvent event) throws SQLException {
        entername.setText(null);
        enteremail.setText(null);
        enternumber.setText(null);
        
       
    }
    
    
     public void readdb() throws SQLException{
             
      String sql;
      sql = "SELECT * FROM profile";    
      
      ResultSet rs = stmt.executeQuery(sql);

      //STEP 5: Extract data from result set
      while(rs.next()){
         //Retrieve by column name
         int id  = rs.getInt("id");
         //int age = rs.getInt("age");
         name.setText(rs.getString("name"));
        // String last = rs.getString("last");
         email.setText(rs.getString("email"));
          number.setText(rs.getString("phone"));
        

         //Display values
        // System.out.println("id: " + id);
         //System.out.println(", age: " + age);
        // System.out.println(", name: " + first);
        // System.out.println(", Last: " + last);
      }
      //STEP 6: Clean-up environment
      rs.close();
      stmt.close();
   }
   
   
   
   
   
   
   public void writedb () throws SQLException {
        PreparedStatement ps = conn.prepareStatement(write);
      ps.setString(1, entername.getText());
       ps.setString(2, enteremail.getText());
        ps.setString(3, enternumber.getText());
     // ps.setInt(2, Integer.parseInt(enteremail.getText()));
      ps.executeUpdate();
      ps.close();
   }
   
   
    public void updatedb(int id,String name, int age) throws SQLException {
        String update = "update profile set name='"+name+"',age="+age+" where id="+id;
        PreparedStatement ps = conn.prepareStatement(update);
      ps.executeUpdate();
      ps.close();
   }
    
    
        public void deletedb() throws SQLException {
        String update = "delete from profile";
        PreparedStatement ps = conn.prepareStatement(update);
      ps.executeUpdate();
      ps.close();
   }
   
   
   public void connectdb () throws ClassNotFoundException, SQLException{
       
           //STEP 2: Register JDBC driver
           Class.forName("org.sqlite.JDBC");
             //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL/*,USER,PASS*/);
      
      
      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement(); 
      
      stmt.executeUpdate(CREATE_TABLE);
      

    
   }
    
}
