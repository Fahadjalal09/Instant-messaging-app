package Cliet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


/**
 * the socket programming of the client which links the client to the server
 *
 */
public class Client{

	private static String user_name;
	private static int port;
	public static DatagramSocket socket;
	private static InetAddress ip;
	private static int user_ID = -1;
	private static Thread send;

	public Client(String name, String address, int port) {
		this.user_name = name;
		this.port = port;
		boolean connect = openConnection(address);
		if (!connect) {
			System.err.println("Connection failed!");

		}
		String connection = "/connect/\t....."+name + " joined the chat...../end/";
		send(connection.getBytes());
	}

	/**
	 * @param address
	 * @return Boolean
	 * openinng sever connection 
	 */
	private boolean openConnection(String address) {
		try {
			socket = new DatagramSocket();
			ip = InetAddress.getByName(address);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (SocketException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * this function is receiving the messages from the server side
	 * @return string 
	 * 
	 */
	public static String receive() {
		byte[] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data, data.length);
		try {
			socket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String message = new String(packet.getData());
		return message;
	}

	/**
	 * continously sending data to the server 
	 * @param data
	 * 
	 */
	public static void send(final byte[] data) {
		send = new Thread("Send") {
			public void run() {
				DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
				try {
					socket.send(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		send.start();
	}
	
	/**
	 * 
	 * @param ID
	 * @param name
	 */
	public static void set_ID(int ID, String name) {
		user_ID = ID;
		user_name = name;
	}
	public static int get_ID() {
		return user_ID;
	}

	/**
	 * close running client socket 
	 */
	public static void close() {
		new Thread() {
			public void run() {
				synchronized (socket) {
					socket.close();
				}
			}
		}.start();
	}
}