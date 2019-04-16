package app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client
{

	public Client() throws Exception
	{
		Socket socket=new Socket("192.168.56.1",2019);
		System.out.println("Uspesno povezivanje na server");
		BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
		Scanner keyboard=new Scanner(System.in);
		
		System.out.println(in.readLine());
		
		while(true)
		{
			System.out.println(in.readLine());
			String msg = keyboard.nextLine();
			out.println(msg);
			msg=in.readLine();
			System.out.println(msg);
			if(msg.contains("Cestitamo"))
				break;
		}
		
		socket.close();
		keyboard.close();
	}
	
	public static void main(String[] args)
	{
		try
		{
			new Client();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
