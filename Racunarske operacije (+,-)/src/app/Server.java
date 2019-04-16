package app;

import java.net.ServerSocket;
import java.net.Socket;

public class Server
{

	private int rbr=1;
	
	public Server() throws Exception
	{
		ServerSocket serverSocket = new ServerSocket(2019);
		System.out.println("Uspesno povezivanje na port 2019");
		
		while(true)
		{
			Socket socket = serverSocket.accept();
			ServerThread serverThread = new ServerThread(socket,rbr++);
			Thread thread = new Thread(serverThread);
			thread.start();
		}
	}
	
	public static void main(String[] args)
	{
		try
		{
			new Server();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
