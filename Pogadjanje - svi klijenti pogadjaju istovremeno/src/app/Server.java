package app;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server
{
	private int curr = 1;
	private int toGuess;
	private boolean gameOver = false;
	private String winner;

	public Server() throws Exception
	{
		ServerSocket serverSocket = new ServerSocket(2019);
		System.out.println("Server se povezao na port 2019");

		Random random = new Random();
		this.toGuess = random.nextInt(100);
		System.out.println("Broj za pogadjanje je: " + this.toGuess);

		while (true)
		{
			Socket socket = serverSocket.accept();
			ServerThread serverThread = new ServerThread(socket, this);
			Thread thread = new Thread(serverThread);
			thread.start();
		}
	}

	public String getWinner()
	{
		return winner;
	}

	public void setWinner(String winner)
	{
		this.winner = winner;
	}

	public int getCurr()
	{
		return curr;
	}

	public void setCurr(int curr)
	{
		this.curr = curr;
	}

	public int getToGuess()
	{
		return toGuess;
	}

	public boolean isGameOver()
	{
		return gameOver;
	}

	public void setGameOver(boolean gameOver)
	{
		this.gameOver = gameOver;
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
