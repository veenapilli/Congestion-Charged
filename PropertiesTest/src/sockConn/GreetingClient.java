package sockConn;

import java.net.*;
import java.io.*;

public class GreetingClient
{
	Socket client  = null;
	protected void sendServer() throws Exception{
		OutputStream outToServer = client.getOutputStream();
		DataOutputStream out =
				new DataOutputStream(outToServer);

		out.writeUTF("Hello from "
				+ client.getLocalSocketAddress());
	}
	protected void readServer() throws Exception{
		InputStream inFromServer = client.getInputStream();
		DataInputStream in =
				new DataInputStream(inFromServer);
		System.out.println("Server says " + in.readUTF());
	}
	protected void connToServer() throws Exception{
		String serverName = "localhost";
		int port = Integer.parseInt("10000");
		try
		{
			System.out.println("Connecting to " + serverName
					+ " on port " + port);
			client = new Socket(serverName, port);
			System.out.println("Just connected to "
					+ client.getRemoteSocketAddress());
			sendServer();
			readServer();
			client.close();
		}catch(IOException e)
		{
			e.printStackTrace();
		}

	}
	public static void main(String [] args)
	{
		GreetingClient gc = new GreetingClient();
		try{
		gc.connToServer();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}