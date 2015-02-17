package sockConn;


import java.net.*;
import java.io.*;

public class GreetingServer extends Thread
{
	private ServerSocket serverSocket;
	private Socket server = null;
	private int port = Integer.parseInt("10000");
	private int timeoutInt = 10000;
	
	public GreetingServer(int port) throws IOException
	{
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(timeoutInt);
	}
	protected void readClientData() throws Exception{
		DataInputStream in =
				new DataInputStream(server.getInputStream());
		System.out.println(in.readUTF());
	}

	protected void sendClientData() throws Exception{
		DataOutputStream out =
				new DataOutputStream(server.getOutputStream());
		out.writeUTF("Thank you for connecting to "
				+ server.getLocalSocketAddress() + "\nGoodbye!");
	}
	
	public void run()
	{
		while(true)
		{
			try
			{
				System.out.println("Waiting for client on port " +
						serverSocket.getLocalPort() + "...");
				server = serverSocket.accept();
				System.out.println("Just connected to "
						+ server.getRemoteSocketAddress());
				readClientData();
				sendClientData();
				server.close();
			}catch(SocketTimeoutException s)
			{
				System.out.println("Socket timed out!");
				break;
			}catch(IOException e)
			{
				e.printStackTrace();
				break;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	protected void launchServer(){
		try
		{
			Thread t = new GreetingServer(port);
			t.start();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String [] args)
	{
		int port = Integer.parseInt("10000");
		try{
			GreetingServer gs = new GreetingServer(port);
			gs.launchServer();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}