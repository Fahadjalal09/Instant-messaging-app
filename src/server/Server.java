package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import application.model;

/**
 * the server side socket programming 
 * it handles the server 
 *
 */
public class Server implements Runnable {
	private static List<ServerClient> clients = new ArrayList<ServerClient>();
	private DatagramSocket socket;
	private int port;
	private boolean running = false;
	private Thread run, manage, send, receive;
	private List<Integer>client_response = new ArrayList<Integer> ();
	private model mod = new model();
	
	public Server(int port) {
		this.port = port;
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
			return;
		}
		run = new Thread(this, "Server");
		run.start();
	}

	/**
	 *this function is activating the server thread
	 */
	public void run() {
		running = true;
		System.out.println("Server started on port " + port);
		manageClients();
		receive();
		//// Server commands////
		Scanner input = new Scanner(System.in);
		while(running) 
		{
			String command = input.nextLine();
			if(command.equals("_show-Users")) 
			{
				System.out.println("============================= Clients =============================");
				for(int i =0; i<clients.size(); i++)
				{
					ServerClient c = clients.get(i);
					System.out.println("\tName: "+c.name+" ID: "+c.ID+" ( "+ c.address+" : "+c.port+" )");
				}
				System.out.println("===================================================================");
			}
			else if(command.equals("_help")) 
			{
				System.out.println("============================= Help ===============================");
				System.out.println("\t _show-Users => Show all online users.");
				System.out.println("\t _kick-User    => Kick user form server.");
				System.out.println("\t _del-User     => Delete user from database.");
				System.out.println("===================================================================");
			}
			else if(command.startsWith("_kick-User"))
			{
				String name = command.split(" ")[1];
					int id = -1;
					boolean num = false;
					try {
						id = Integer.parseInt(name);
						num = true;
					}
					catch(NumberFormatException e)
					{
						num = false;
					}
					if(num)
					{
						boolean present = false;
						for(int i = 0; i<clients.size(); i++)
						{
							if(clients.get(i).get_id()== id)
							{
								present = true;
								break;
							}
						}
						if(present)
						{
							disconnect(id, true);
						}
							
						else
							System.out.println("Client doen't exist!");
					}
					else
					{
						boolean present = false;
						for(int i = 0; i<clients.size(); i++)
						{
							ServerClient c = clients.get(i);
							if(name.equals(c.name))
							{
								disconnect(c.get_id(), true);
								present = true;
								break;
							}
						}
						if(!present)
							System.out.println("Client doen't exist!");
					}
			}
			else if(command.startsWith("_del-User"))
			{
				String name = command.split(" ")[1]; 
				String q = "DELETE FROM user WHERE first_name = '"+name+"'"; 
	//			System.out.println(q);
				
				mod.delete_user(q);	
			}
		}
		
	}

	/**
	 * this function is managing the clients 
	 */
	private void manageClients() {
		manage = new Thread("Manage") {
			public void run() {
				while (running) {
					send_status();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		manage.start();
	}

	/**
	 * this function is setting the status
	 * telling about the user online status 
	 */
	private void send_status() {
		if(clients.size()<=0) return;
		String online_users = "/user/";
		for(int i = 0; i<clients.size() - 1; i++)
		{
			online_users += clients.get(i).name+"/n/";
		}
		online_users += clients.get(clients.size()-1).name+"/end/";
//		System.out.println(online_users);
		send_to_all(online_users);
	}
	private void receive() {
		receive = new Thread("Receive") {
			public void run() {
				while (running) {
					byte[] data = new byte[1024];
					DatagramPacket packet = new DatagramPacket(data, data.length);
					try {
						socket.receive(packet);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					process(packet);
//					clients.add(new ServerClient("Name", packet.getAddress(), packet.getPort(), uniqueRendNo.get_identifier()));
//					System.out.println(clients.get(0).address.toString())
				}
			}
		};
		receive.start();
	}
	
	/**
	 * sending messages to the client 
	 * @param message
	 * @param address
	 * @param port
	 */
	private void send(String message, InetAddress address, int port) 
	{
		
		message = message+"/end/";
		//System.out.println(message);
		send(message.getBytes(), address, port);
		
	}
	
	/**
	 * @param packet
	 * it is processing users data  and checking whether is a conection packet or message packet 
	 */
	private void process(DatagramPacket packet)
	{
		String string = new String(packet.getData());
		if(string.startsWith("/connect/")) 
		{		
			string = string.split("/connect/|/end/")[1]; // /connect/ .....Fahad joined the chat...../end/
			System.out.println(string);					//  .....Fahad joined the chat.....
			string = string.split(" joined")[0];  //  //  .....Fahad joined the chat.....
			string =string.substring(6, string.length()); //  Fahad
			int id = uniqueRendNo.get_identifier();
			clients.add(new ServerClient(string, packet.getAddress(), packet.getPort(), id));
			String ID = "/connect/"+id;
			send(ID, packet.getAddress(),packet.getPort() );
		}
		 else if (string.startsWith("/dis/")) {
				String id = string.split("/dis/|/end/")[1];
		//		System.out.println(id);
				disconnect(Integer.parseInt(id), true);}
		else if(string.startsWith("/msg/"))
		{
			send_to_all(string);
		}
		else 
		{
			//System.out.println(string);
		}
			
	}

		/**
		 * this function is disconnecting the client from the server 
		 * @param id
		 * @param status
		 * 
		 */
		private void disconnect(int id, boolean status) {
			ServerClient c = null;
			boolean existed = false;
			for (int i = 0; i < clients.size(); i++) {
				if (clients.get(i).get_id() == id) {
					c = clients.get(i);
					System.out.println("\t\t..... "+c.name+" "+c.get_id()+" left.....\n");
					clients.remove(i);
					existed = true;
					break;
					
				}
			}
			
			if (!existed) return;
			if (status) {
				String dis_msg ="/msg/"+ "\t\t\t........ "+c.name+" " +  c.get_id()+" left ........\n";
				send_to_all(dis_msg);
			} 
			else 
			{
				String dis_msg ="/msg/"+ "\t\t\t........ "+c.name+" " +  c.get_id()+" left ........\n";
				send_to_all(dis_msg);
			}
			 
		}
	
	/**
	 * send messages to the all online users 
	 * @param message
	 */
	private void send_to_all(String message) {
		for(int i = 0; i<clients.size(); i++)
		{
			ServerClient client= clients.get(i);
			send(message.getBytes(), client.address, client.port);
		}
	
	}
	public static boolean check_connected(int id) {
		for(int i = 0; i<clients.size(); i++)
		{
			ServerClient client= clients.get(i);
			System.out.println(client.get_id());
			if (client.ID == id) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param data
	 * @param address
	 * @param port
	 */
	private void send(final byte[] data, final InetAddress address, final int port)
	{
		send = new Thread("Send"){
			public void run() {
				DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
				try {
					socket.send(packet);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		send.start();
	}
}
