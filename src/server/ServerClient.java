package server;

import java.net.InetAddress;

public class ServerClient {
	public String name;
	public InetAddress address;
	public int port;
	public final int ID;
	public int attempt = 0;
	
	/**
	 * this function is setting the server client 
	 * @param name
	 * @param address
	 * @param port
	 * @param ID
	 */
	public ServerClient(String name, InetAddress address, int port, final int ID) 
	{
		this.ID = ID;
		this.name = name;
		this.address = address;
		this.port = port;
		
	}
	public int get_id() 
	{
		return ID;
		
	}
}
