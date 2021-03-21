package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;

public class lobbyController implements Initializable{
	@FXML
	private TextField msg_writer;
	@FXML
	private TextArea Msg_id;
	@FXML
	private TextArea user_id;
	

    @FXML
    void goto_signin(ActionEvent event) {
    	try {
			Parent tableViewParent = FXMLLoader.load(getClass().getResource("root.fxml"));
	        Scene tableViewScene = new Scene(tableViewParent);
	        
	        //This line gets the Stage information
	        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
	        
	        window.setScene(tableViewScene);
	        window.show();
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void msg_send(KeyEvent event) {
        // enter
    	if(event.getCode().toString().equals("ENTER"))
    	{
    		String msg = msg_writer.getText();
    		if(!msg.equals(""))
    		{
    			send(msg+"\n");
    		}
    	}
    	
    }

    @FXML
    void send_msg(ActionEvent event) {
    	String msg = msg_writer.getText();
    	if(!msg.equals(""))
		{
			send(msg+"\n");
		}
    }
    
    
    void send(String _msg_) {
    	Msg_id.appendText("Me: "+_msg_);
    	msg_writer.clear();
    }
    
    
   
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
     

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
