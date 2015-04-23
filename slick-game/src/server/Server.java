package server;

import java.io.*;
import java.net.*;
import java.util.Vector;

import client.EventProtocol;

public class Server {
	private ServerSocket ss;

	public static void main(String[] args) {
		Server si = new Server();
		si.startServer();
	}

	public void startServer() {
		Socket connection;
		Statebox mb = new Statebox();
		UpdateToClient rt = new UpdateToClient(mb);
		rt.start();
		try {
			while (true) {
				connection = ss.accept();
				new PlayerParticipant(connection, mb).start();
				rt.addSocket(connection);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
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
		clients.add(s);
		byte[] idMessage = new byte[2];
		idMessage[0] = EventProtocol.PLAYER_ID;
		idMessage[1] = (byte) clients.size();
		try {
			sendMessage(idMessage, s.getOutputStream());
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public void sendMessage(byte[] msg, OutputStream writer) throws IOException{
		writer.write(msg);
		writer.flush();
	}

	public void run() {
		while (true) {
			msg = this.statebox.readMessage();

			if (!(msg.length < 2)) {
				for (int i = 0; i < clients.size(); i++) {
					try {
						sendMessage(msg, clients.get(i).getOutputStream());
					} catch (IOException ioe) {ioe.printStackTrace();}
				}
			}
		}
	}
}
