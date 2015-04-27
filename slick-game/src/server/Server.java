package server;

import java.io.*;
import java.net.*;
import java.util.Vector;

import util.EventProtocol;

public class Server extends Thread {
	private ServerSocket ss;

	public static void main(String[] args) {
		int port = 30000;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}
		Server si = new Server(port);
		si.start();
	}

	public Server(int port) {
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		Socket connection;
		Statebox mb = new Statebox();
		UpdateToClient rt = new UpdateToClient(mb);
		rt.start();
		while (true) {
			try {
				connection = ss.accept();
				System.out.println("Client connected.");
				new PlayerParticipant(connection, mb).start();
				rt.addSocket(connection);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}

class UpdateToClient extends Thread {
	private Statebox statebox;
	private byte[] msg;
	private Vector<Socket> clients;

	public UpdateToClient(Statebox statebox) {
		this.statebox = statebox;
		this.clients = new Vector<Socket>();
	}

	public void addSocket(Socket s) {
		if(clients.size() >= 4){
			try {s.close();} catch (IOException e) {e.printStackTrace();}
			return;
		}
		clients.add(s);
		byte[] idMessage = new byte[4];
		idMessage[0] = EventProtocol.PLAYER_ID;
		idMessage[1] = (byte) clients.size();
		idMessage[2] = EventProtocol.LOCAL_PLAYER_INIT;
		try {
			sendMessage(idMessage, s.getOutputStream());
			System.out.println("Client id sent: " + idMessage[1]);
		} catch (IOException e) {e.printStackTrace();}
	}

	public void sendMessage(byte[] msg, OutputStream writer) throws IOException {
		
		
		writer.write(msg);
		writer.flush();
	}

	public void run() {
		while (true) {
			msg = this.statebox.readMessage();

			for (int i = 0; i < clients.size(); i++) {
				try {
					sendMessage(msg, clients.get(i).getOutputStream());
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}

		}
	}
}
