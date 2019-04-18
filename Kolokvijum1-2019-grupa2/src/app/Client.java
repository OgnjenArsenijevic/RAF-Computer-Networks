package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client
{

	public Client() throws Exception
	{
		Socket socket = new Socket("192.168.17.41", 2029);
		System.out.println("Uspesno povezivanje na server");
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
		Scanner keyboard = new Scanner(System.in);

		System.out.println("Server: " + in.readLine());
		System.out.println("Server: " + in.readLine());
		int key = Integer.parseInt(keyboard.nextLine()); // ne hvatam exception jer racunam da se unosi validan broj
		while (key < 1 || key > 19)
		{
			System.out.println("Uneli ste neispravnu vrednost za kljuc, unesite kljuc ponovo");
			key = Integer.parseInt(keyboard.nextLine());
		}
		int k = key + 10; // 10 je moj br indeksa
		while (true)
		{
			System.out.println("Server: " + in.readLine());
			String msg = keyboard.nextLine();
			System.out.println("Korisnik unosi: " + msg);
			if (msg.equals("STOP"))
			{
				out.println(msg);
				break;
			}
			String cezar = "";
			for (int i = 0; i < msg.length(); i++)
			{
				if (msg.charAt(i) >= 'a' && msg.charAt(i) <= 'z')
					cezar += (char) (((int) msg.charAt(i) + key - (int) 'a') % 26 + (int) 'a');
				else
				{
					if (msg.charAt(i) >= 'A' && msg.charAt(i) <= 'Z')
						cezar += (char) (((int) msg.charAt(i) + key - (int) 'A') % 26 + (int) 'A');
					else
						cezar += msg.charAt(i);
				}
			}
			cezar += ",10," + k;
			out.println(cezar);
			String ans = in.readLine();
			System.out.println("Server: " + ans);
			if (msg.equals(ans))
				System.out.println("Poruka je ispravno desifrovana");
			else
				System.out.println("Poruka nije ispravno desifrovana");
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
