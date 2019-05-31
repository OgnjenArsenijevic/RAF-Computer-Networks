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
		this.socket=socket;
		this.rbr=rbr;
	}

	@Override
	public void run()
	{
		System.out.println("Povezao se klijent br: " + rbr + " sa adresom " + socket.getInetAddress().getHostAddress());
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
			
			out.println("Dobrodosli, Vas redni broj je: " + rbr);
			out.println("Unesite izraz: ");
			String msg=in.readLine();
			int ans=0,currOp=1;
			String tmp="";
			for(int i=0;i<msg.length();i++)
			{
				if(msg.charAt(i)!='+' && msg.charAt(i)!='-')
					tmp+=msg.charAt(i);
				else //radim pod pretpostavkom da je unet validan izraz pa zato hvatam exception-e
				{
					if(currOp==1)
						ans+=Integer.parseInt(tmp);
					else
						ans-=Integer.parseInt(tmp);
					tmp="";
					if(msg.charAt(i)=='+')
						currOp=1;
					else
						currOp=0;
				}
			}
			if(currOp==1)
				ans+=Integer.parseInt(tmp);
			else
				ans-=Integer.parseInt(tmp);
			out.println("Resenje je: " + ans);
			socket.close();
			System.out.println("Diskonektovao se klijent br " + rbr);
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

}
