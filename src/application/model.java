package application;

import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * 
 *this class is making the coonection to the data base. take and  input data from data base 
 */
public class model {
	 private Connection cn; 
	 private Statement stat;
	 
	 
	 public void connect() {
    	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			
		}
		try {
			cn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/chat_app?useTimezone=true&serverTimezone=UTC", "root", "");
			stat = cn.createStatement();
		//	JOptionPane.showMessageDialog(null, "Connected!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	 
	 /**
	 * @param query
	 * this is inserting data into the data base
	 */
	public void insert_data(String query) {
		 connect();
		 try {
			 //stat.execute(query);
//			 JOptionPane.showMessageDialog(null, query);
			 stat.executeUpdate(query);
			 JOptionPane.showMessageDialog(null, "Record Saved!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 /**
	 * @param query
	 * it is deleting the user from the data base 
	 */
	public void delete_user(String query) {
		 connect();
		 try {
			 
			 stat.execute(query);
			 System.out.println("\tRecord Deleted!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 
	 /**
	 * @param email
	 * @return boolean 
	 * it confirms the email from the data base that it is alreay registered or not 
	 */
	public boolean check_email(String email) {
		 String query = "SELECT email FROM user WHERE email = '"+email+"'"; 
		 connect();
		 String out = "";
		 try {
			 	ResultSet result = stat.executeQuery(query);
			
			if(!(result == null)) {
				while(result.next())
				{
					out = result.getString(1);
					if(out.equals(email))
						return true;
				}
				
			}
		 } catch (SQLException e1) {
			 JOptionPane.showMessageDialog(null, "Can't Run Query");
			 e1.printStackTrace();
		 }
		return false;
	 }
	 
	 public String[] get_user(String query) {
		 connect();
		 String [] arr = new String[3];
		 System.out.println(query);
		 try {
			 	ResultSet result = stat.executeQuery(query);
			
			if(!(result == null)) {
				while(result.next())
				{
					arr[0] = result.getString(1);
					arr[1] = result.getString(2);
					arr[2] = result.getString(3);
					}
				}
			else
			{
				JOptionPane.showMessageDialog(null, "User not registered!");
			}
		 } catch (SQLException e1) {
			 JOptionPane.showMessageDialog(null, "Can't Run Query");
			 e1.printStackTrace();
		 }
		 return arr;
	 }
	 
	 /**
	 * @param query
	 * @return String array 
	 * it is gettig the user information from the data base to user 
	 */
	public String[] get_user_data(String query) {
		 connect();
		 String [] arr = new String[4];
		 System.out.println(query);
		 try {
			 	ResultSet result = stat.executeQuery(query);
			
			if(!(result == null)) {
				while(result.next())
				{
					arr[0] = result.getString(1);
					arr[1] = result.getString(2);
					arr[2] = result.getString(3);
					arr[3] = result.getString(4);
					}
				}
			else
			{
				JOptionPane.showMessageDialog(null, "User not registered!");
			}
		 } catch (SQLException e1) {
			 JOptionPane.showMessageDialog(null, "Can't Run Query");
			 e1.printStackTrace();
		 }
		 return arr;
	 }


	 

	
	
}
