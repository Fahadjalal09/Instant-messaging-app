package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javafx.scene.control.PasswordField;

/**
 *this class is taking input and creating new user and it is a form window
 *
 */
public class register_windowController implements Initializable{
	@FXML
	private TextField first_name;
	@FXML
	private TextField last_name;
	@FXML
	private ComboBox<String> gender_box;
	@FXML
	private Button sign_in_btn;
	@FXML
	private TextField email_id;
	@FXML
	private PasswordField id_password;
	@FXML
    private AnchorPane register_window;
	
	private model mod = new model();
	
	/**
	 * @param event
	 *take user to the sign in page 
	 */
	@FXML
    void got_signin(ActionEvent event) {
		try {
			Parent tableViewParent = FXMLLoader.load(getClass().getResource("root.fxml"));
	        Scene tableViewScene = new Scene(tableViewParent);
	        
	        //This line gets the Stage information
	        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
	        window.setTitle("Sign In");
	        window.setScene(tableViewScene);
	        window.show();
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	
	/**
	 * @param event
	 * extracting data from registration form 
	 */
	@FXML
    void get_registeration(ActionEvent event) {
		String first_name1, last_name1, email, gender, pass ;
		
		first_name1 = first_name.getText() ;
		last_name1 =last_name.getText() ;
		email = email_id.getText() ;
		gender = gender_box.getSelectionModel().getSelectedItem() ;
		pass = id_password.getText() ;
		try {
			if(first_name1.equals("") && last_name1.equals("") && email.equals("") && gender.equals(null) && pass.equals("")){
				JOptionPane.showMessageDialog(null, "Please Enter all Values.");
			}
			else {
				if(first_name1.equals(""))
					JOptionPane.showMessageDialog(null, "Please Enter First Name.");
				else if (last_name1.equals(""))
						JOptionPane.showMessageDialog(null, "Please Enter Last Name.");
				else if (gender.equals("Select"))
					JOptionPane.showMessageDialog(null, "Please Select Gender.");
				else if (email.equals(""))
					JOptionPane.showMessageDialog(null, "Please Enter Email Address.");
				else if (pass.equals(""))
					JOptionPane.showMessageDialog(null, "Please Enter  Password.");
				else {
					if(!(email.contains("@nu.edu.pk")))
							JOptionPane.showMessageDialog(null, "Invalid email address.");
					else if(pass.length()<6)
						JOptionPane.showMessageDialog(null, "Password can't be less than 6 characters.");
					else
					{	
						if(mod.check_email(email) == false)
						{
							String q = "INSERT INTO user VALUES ('"+first_name1+"','"+last_name1+"','"+gender+"','"+email+"','"+pass+"')";
							mod.insert_data(q);
						}
						else
							JOptionPane.showMessageDialog(null, "Email already registered.");
					}
					
				}
			}
		}
		catch(NullPointerException e) {
			if(!first_name1.equals("") && !last_name1.equals("") && !email.equals("")  && !pass.equals(""))
				JOptionPane.showMessageDialog(null, "Please Select Gender.");
			else
				JOptionPane.showMessageDialog(null, "Please Enter all Values .");
		}
    }
	
	
	/**
	 *built in function which is initializing combox
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	
		//JOptionPane.showMessageDialog(null, "Initialized!");
		ObservableList<String> list = FXCollections.observableArrayList("Male","Female");
		gender_box.setId("Male");
		gender_box.setItems(list);
	};
		
}
