package app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{

	private int rbr = 1;

	public Server() throws IOException
	{
		ServerSocket serverSocket = new ServerSocket(2019);
		System.out.println("Server se povezao na port 2019");

		while (true)
		{
			Socket socket = serverSocket.accept();
			ServerThread serverThread = new ServerThread(socket, rbr++);
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
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
