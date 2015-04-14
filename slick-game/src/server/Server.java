package server;

import java.io.*;
import java.net.*;
import java.util.Vector;
import java.lang.Math;
import client.Client;

public class Server {
	private ServerSocket ss;
	private Vector<Client> clients;

	public static void main(String[] args) {
		Server si = new Server();
		si.startServer();
	}

	public Server() {
		clients = new Vector<Client>();
		try {
			ss = new ServerSocket(30001);
			System.out.println("Server booting...");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void startServer() {
		Socket connection;
		Client cc;

		Statebox mb = new Statebox();

		ReadThread rt = new ReadThread(mb, clients);
		rt.start();

		try {
			while (true) {
				connection = ss.accept();

				cc = new Client(connection, mb, "User-" + clients.size());
				cc.start();
				clients.add(cc);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	class ReadThread extends Thread {
		private Statebox statebox;
		private byte[] msg;
		private Vector<Client> clients;

		public ReadThread(Statebox statebox, Vector<Client> clients) {
			this.statebox = statebox;
			this.clients = clients;
		}

		public void run() {
			while (true) {
				//TODO: Use wait-notify pattern!
				try {
					Thread.sleep((long) (Math.random() * 1000));
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
				msg = this.statebox.readMessage();
				if (!(msg.length < 2)) {
					for (int i = 0; i < clients.size(); i++) {
						clients.get(i).writeMessage(msg);
					}
				}
			}
		}
	}

}
