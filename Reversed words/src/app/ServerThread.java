package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collections;

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
			out.println("Dobrodosli, Vas redni broj je: " + rbr);
			while (true)
			{
				out.println("Unesite tekst za editovanje: ");
				String msg = in.readLine();
				String split[] = msg.split(" ");
				boolean flag = false;
				if (msg.toLowerCase().contains("exit"))
					flag = true;
				String ans = "";
				for (int i = 0; i < split.length; i++)
					ans += new StringBuffer(split[i]).reverse().toString() + (i < split.length - 1 ? " " : "");
				out.println(ans);
				if (flag)
					break;
			}

			socket.close();
			System.out.println("Diskonektovao se klijent br: " + rbr);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
