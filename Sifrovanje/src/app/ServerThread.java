package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread implements Runnable
{

	private Socket socket;
	private int rbr;

	public ServerThread(Socket socket, int rbr)
	{
		this.socket = socket;
		this.rbr = rbr;
	}

	@Override
	public void run()
	{
		System.out.println("Povezao se klijent sa rednim brojem: " + rbr + " i adresom: "
				+ socket.getInetAddress().getHostAddress());
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
			out.println("Dobrodosli, Vas redni broj je " + rbr);
			while (true)
			{
				out.println("Unesite sifrovan tekst");
				String msg = in.readLine();
				String ans = "";
				for (int i = 0; i < msg.length(); i++)
				{
					if (msg.charAt(i) != ' ')
						ans += (char) (((int) msg.charAt(i) - 5 + 128) % 128);
					else
						ans += " ";
				}
				out.println("Desifrovano: " + ans);
				if (ans.toLowerCase().contains("exit"))
					break;
			}
			socket.close();
			System.out.println("Diskonektovao se klijent br: " + rbr);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

}
