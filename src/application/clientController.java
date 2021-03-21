package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import server.Server;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Cliet.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;

/**
 * @author Fahad
 *version 1.0.0
 * class which is creating new client  it take ip and port address 
 */
/**
 * 
 *
 */
public class clientController implements Initializable, Runnable{
	public static String selected_user;

	@FXML
	private TextField msg_writer;
	@FXML
	private TextArea Msg_id;
	@FXML
    private ListView<String> user_id;
	 @FXML
	private Label name;
    /**
     * @param event
     */
    @FXML
    void show_user_info(ActionEvent event) {
		try {
			selected_user = user_id.getSelectionModel().getSelectedItem();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("user_info.fxml"));
			Parent root1 = (Parent)fxmlLoader.load();
			Stage stage = new Stage();
			
			stage.setTitle("User Information");
			stage.setScene(new Scene(root1));
			stage.show();
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    

	
	ObservableList list = FXCollections.observableArrayList() ;
	
	private static String user_name;
	private Thread run, listen;
	boolean running = false;
/*	boolean check_c = false;
	
	void check_u() {
		check_c = Server.check_connected(Client.get_ID());
    	System.out.println(""+Server.check_connected(Client.get_ID()));
    	
	}*/
    /**
     * @param event
     * sign in form 
     */
    @FXML
    void goto_signin(ActionEvent event) {
    	try {
    		String disconnect = "/dis/" + Client.get_ID() + "/end/";
    		System.out.println(disconnect);
			send(disconnect, false);
			running = false;
			Client.close();
    		
			Parent tableViewParent = FXMLLoader.load(getClass().getResource("root.fxml"));
	        Scene tableViewScene = new Scene(tableViewParent);
	        
	        //This line gets the Stage information
	        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
	        window.setTitle("User Information");
	        window.setScene(tableViewScene);
	        window.show();
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * @param event
     * this buutonis sending msgs
     */
    @FXML
    void msg_send(KeyEvent event) {
        // enter
    	if(event.getCode().toString().equals("ENTER"))
    	{
    		String msg = msg_writer.getText();
    		if(!msg.equals(""))
    		{
    				send(msg+"\n", true);
    		}
    	}
    	
    }

    @FXML
    void send_msg(ActionEvent event) {
    	String msg = msg_writer.getText();
    	if(!msg.equals(""))
		{
				send(msg+"\n", true);
		}
    }
    
    /**
     * take msg and send it to the server
     * @param _msg_
     * @param c
     */
    void send(String _msg_, boolean c) {
    	if(_msg_.equals("")) return;
    	
	    	if(c)
	    	{
	    		_msg_ = user_name+": "+_msg_;
	    		_msg_ = "/msg/"+_msg_;
	    	}
	    //	Msg_id.appendText(_msg_);
	    	Client.send(_msg_.getBytes());
	    	msg_writer.clear();
    	
    }
    
    
    /**
     * this continusly reading server msgs
     */
    public void listen() 
    {try {
    	new Thread("Listen") {
			public void run() {
				while (running) {
					String message = Client.receive();
					if (message.startsWith("/connect/")) {
						Client.set_ID(Integer.parseInt(message.split("/connect/|/end/")[1]), user_name);
						String con_msg ="/msg/"+ "\t\t........ "+user_name+" has joined the chat ........\n/end/";
						Client.send(con_msg.getBytes());
					} else if (message.startsWith("/msg/")) {
						String text = message.substring(5);
						text = text.split("/end/")[0];
							Msg_id.appendText(text);
					}  else if (message.startsWith("/user/")) {
						String[] u = message.split("/user/|/n/|/end/");
						ObservableList <String> list = FXCollections.observableArrayList() ;
						list.removeAll(list);
						for(int i=0; i<u.length;i++)
						{
							if(!u[i].equals("")) {
								list.add(u[i]);}
						}
						
							out_data(list);
					}
				}
			}
		}.start();}
		catch(IllegalStateException e)
		{
			System.out.println("################ DO NOTHING! ################");
		}
    }
    
    
    
	protected void out_data(ObservableList<String> list) {
		//list.remove(list.size()-1);
		
		user_id.setItems(list);
		
	}

	/**
	 *built  in function 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		new Client(user_name, "localhost", 8192); // server ip 
		running = true;
		run = new Thread(this, "Running");
		name.setText("             "+user_name);
		run.start();
		
	}
	
	/**
	 * this is setting the client name 
	 * @param name
	 */
	public static void get_user_name(String name)
	{
		user_name = name;
	}

	public void run() {
		listen();	
	}
	
	
     
}
