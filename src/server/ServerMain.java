package server;


/**
 * this is taking the port number and starting the server 
 *
 */
public class ServerMain {

	private int port;
	private Server server;

	public ServerMain(int port) {
		this.port = port;
		server = new Server(port);
	}

	public static void main(String[] args) {
		int port;
		System.out.print(args.length);
		if(args.length > 1)
		{
			System.out.print("ERROR!");
			return;
		}
		port = Integer.parseInt(args[0]);
		new ServerMain(port);
	}

}
