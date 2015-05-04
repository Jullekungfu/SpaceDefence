package server;

import java.io.*;
import java.net.*;

import client.MessageWrapper;

import util.EventProtocol;

public class Server extends Thread {
	private ServerSocket ss;

	public static void main(String[] args) {
		int port = 30000;
		if (args.length > 0 && !args[0].isEmpty()) {
			port = Integer.parseInt(args[0]);
		}
		Server si = new Server(port);
		si.start();
	}

	public Server(int port) {
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
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

	public UpdateToClient(Statebox statebox) {
		this.statebox = statebox;
	}

	//TODO: (re)move this method
	public void addSocket(Socket s) {
		if(statebox.getCurrentClients() >= 4){
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		statebox.addClient(s);
		byte[] msg = { EventProtocol.LOCAL_PLAYER_INIT };
		byte[] idMessage = MessageWrapper.wrapMessageToServer(msg, (byte) statebox.getCurrentClients());
		try {
			sendMessage(idMessage, s.getOutputStream());
		} catch (IOException e) {e.printStackTrace();}
	}

	private void sendMessage(byte[] msg, OutputStream writer) throws IOException {
		
		writer.write(msg);
		writer.flush();
	}

	public void run() {
		while (true) {
			msg = this.statebox.readMessage();

			for (Socket s : statebox.getClientSockets()) {
				try {
					sendMessage(msg, s.getOutputStream());
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}

		}
	}
}
