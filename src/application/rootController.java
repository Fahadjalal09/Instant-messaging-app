package application;

import java.io.IOException;

import javax.swing.JOptionPane;

import Cliet.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * the first sign in window 
 *
 */
public class rootController {
	private model mod = new model();
	private clientController c = new clientController(); 
    
	@FXML
    private TextField email_id;

    @FXML
    private PasswordField id_password;
    
    @FXML
    private AnchorPane root_window;
    
    
    public void initialize() {
    	
    };
    
    private String user_name = "";
    
    
    @FXML
    private void loadSecond(ActionEvent event){
		try {
			Parent tableViewParent = FXMLLoader.load(getClass().getResource("register_window.fxml"));
	        Scene tableViewScene = new Scene(tableViewParent);
	        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
	        window.setTitle("Registration");
	        window.setScene(tableViewScene);
	        window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    private void loadSecond1(ActionEvent event){
    	if(email_id.getText().equals(""))
    		JOptionPane.showMessageDialog(null,"Please Enter Email Address");
    	else if(id_password.getText().equals(""))
    		JOptionPane.showMessageDialog(null,"Please Enter Password");
    	else if(!(email_id.getText().contains("@nu.edu.pk")))
    		JOptionPane.showMessageDialog(null,"Please Valid Email Address");
    	else if(id_password.getText().length()<6)
			JOptionPane.showMessageDialog(null, "Invalid Password.");
    	else {
    	String q = "SELECT first_name, email, password FROM user WHERE email = '"+email_id.getText()+"'";
    //	JOptionPane.showMessageDialog(null,q);
    	String [] arry = new String[3];
    
    	arry = mod.get_user(q);
    	this.user_name = arry[0];
//    	JOptionPane.showMessageDialog(null, "Wrong Email or Password "+arry[2]);
    	try {
		if(arry[2].equals(id_password.getText()))
		{
			try {
				c.get_user_name(user_name);
				Parent tableViewParent = FXMLLoader.load(getClass().getResource("client.fxml"));
		        Scene tableViewScene = new Scene(tableViewParent);
		        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		        window.setTitle("Chat Room");
		        window.setScene(tableViewScene);
		        window.show();
				} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else {
			JOptionPane.showMessageDialog(null, "Wrong Email or Password");
		} }
    	catch(NullPointerException e)
    	{
    		JOptionPane.showMessageDialog(null, "Wrong Email or Password");
    	}
	} }
    
    
}
