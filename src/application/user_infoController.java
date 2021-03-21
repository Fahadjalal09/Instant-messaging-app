package application;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 * extracting data from the data base 
 * 
 *
 */
public class user_infoController implements Initializable {
	@FXML
	private TextArea f_name;
	@FXML
	private TextArea l_name;
	@FXML
	private TextArea emzil_id;
	@FXML
	private TextArea g_box;
	
	private model mod = new model();
	private String user, first_name, last_name, email, gender ;
	
	
	/**
	 * getting data from the data base of specific user
	 */
	private void get_data() 
	{
		String q = "SELECT first_name, last_name, email, gender FROM user WHERE first_name = '"+user+"'";
//	   	JOptionPane.showMessageDialog(null,q);
	    String [] arry = new String[4];
	    arry = mod.get_user_data(q);
	    
	    first_name = arry[0];
	    last_name = arry[1];
	    email = arry[2];
	    gender = arry[3];
//	    JOptionPane.showMessageDialog(null,first_name+" , "+user);
		if(user.equals(first_name))
		{
			f_name.appendText(first_name);
			l_name.appendText(last_name);
			emzil_id.appendText(email);
			g_box.appendText(gender);
		}
		else {
			JOptionPane.showMessageDialog(null, "Something went wrong!");
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		user = clientController.selected_user;
		try {
			get_data();
		}		catch(NullPointerException e)
		{
			JOptionPane.showMessageDialog(null, "No user online.");
		}
		
	}
	
}
