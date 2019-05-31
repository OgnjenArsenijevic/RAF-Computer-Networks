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
	private Server server;
	private int rbr;

	public ServerThread(Socket socket, Server server)
	{
		this.socket = socket;
		this.server = server;
		rbr = server.getCurr();
		server.setCurr(rbr + 1);
	}

	@Override
	public void run()
	{
		System.out.println("Povezao se klijent broj " + rbr + " sa adresom " + socket.getInetAddress().getHostAddress());
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
			out.println("Dobrodosli, Vas redni broj je " + rbr);
			out.println("Unesite svoje ime: ");
			String name = in.readLine();
			String msg = "";
			
			while (true)
			{
				out.println("Unesite broj izmedju 0 i 99 : ");
				msg = in.readLine();
				if (server.isGameOver())
				{
					out.println("Igra je gotova, broj je vec pogodjen od strane " + server.getWinner());
					break;
				}
				System.out.println("Klijent " + rbr + " pokusava sa brojem " + msg);
				try
				{
					if (Integer.parseInt(msg) == server.getToGuess())
					{
						server.setGameOver(true);
						server.setWinner(name);
						System.out.println("Klijent " + rbr + " je pogodio broj ");
						out.println("Cestitamo, pogodili ste trazeni broj");
						break;
					}
					else
						out.println("Niste pogodili trazeni broj, probajte opet");
				}
				catch (NumberFormatException e)
				{
					out.println("Niste uneli validan broj, probajte opet");
				}
			}
			
			socket.close();
			System.out.println("Diskonektovao se klijent" + rbr);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
