package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class ServerThread implements Runnable
{

	private Socket socket;
	private int rbr;
	
	public ServerThread(Socket socket, int rbr)
	{
		this.socket=socket;
		this.rbr=rbr;
	}
	
	@Override
	public void run()
	{
		System.out.println("Povezao se klijent br " + rbr + " sa adresom: " + socket.getInetAddress().getHostAddress());
		try
		{
			BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
			out.println("Dobrodosli, Vas redni broj je: " + rbr);
			Random random=new Random();
			int toGuess=random.nextInt(100);
			System.out.println("Klijent br " + rbr + " treba da pogodi broj: " + toGuess);
			while(true)
			{
				out.println("Unesite broj izmedju 0 i 99");
				String msg=in.readLine();
				System.out.println("Klijent br " + rbr + " pokusava sa brojem " + msg);
				try
				{
					if(Integer.parseInt(msg)==toGuess)
					{
						System.out.println("Klijent br " + rbr + " je pogodio ");
						out.println("Cestitamo, pogodili ste trazeni broj");
						break;
					}
					else
						out.println("Niste pogodili, probajte opet");
						
				}
				catch (NumberFormatException e)
				{
					out.println("Niste uneli validan broj, pokusajte opet");
				}
				
			}
			socket.close();
			System.out.println("Diskonektovao se klijent " + rbr);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

}
