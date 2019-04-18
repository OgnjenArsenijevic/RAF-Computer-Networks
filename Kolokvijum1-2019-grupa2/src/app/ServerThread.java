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
		System.out.println("Konektovao se klijent sa rednim brojem: " + rbr);
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
			out.println("Dobrodosli, Vas redni broj je: " + rbr);
			out.println("Unesite kljuc izmedju 1 i 19");
			while (true)
			{
				out.println("Unesite poruku za desifrovanje");
				String msg = in.readLine();
				System.out.println("Korisnik br: " + rbr + " unosi: " + msg);
				if (msg.equals("STOP"))
					break;
				String split[] = msg.split(","); // splitujem po zarezu posto je garantovano da u sifrovanoj poruci nece
													// biti zareza
				String ans = "";
				int idxNum = Integer.parseInt(split[1]);
				int k = Integer.parseInt(split[2]);
				int key = k - idxNum;
				for (int i = 0; i < split[0].length(); i++)
				{
					if (split[0].charAt(i) >= 'a' && split[0].charAt(i) <= 'z')
						ans += (char) (((int) split[0].charAt(i) - key - (int) 'a' + 26) % 26 + (int) 'a');
					else
					{
						if (split[0].charAt(i) >= 'A' && split[0].charAt(i) <= 'Z')
							ans += (char) (((int) split[0].charAt(i) - key - (int) 'A' + 26) % 26 + (int) 'A');
						else
							ans += split[0].charAt(i);
					}
				}
				System.out.println("Korisnik br: " + rbr + " desifrovano: " + ans);
				out.println(ans);
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
