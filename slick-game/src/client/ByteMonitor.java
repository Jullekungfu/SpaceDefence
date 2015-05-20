package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

import util.Logger;

/**
 * Stores byte arrays sent from client/server
 * 
 * @author julian
 * 
 */
public class ByteMonitor {
	private LinkedList<byte[]> fromServer;
	private LinkedList<byte[]> toServer;
	private Socket socket;
	private String ip;
	private InThread inThread;
	private OutThread outThread;
	private boolean started = false;

	/**
	 * Init monitor
	 * 
	 * @param ipport
	 */
	public ByteMonitor(String ip) {
		fromServer = new LinkedList<byte[]>();
		toServer = new LinkedList<byte[]>();
		this.ip = ip;
	}

	/**
	 * Init connection to server.
	 * 
	 * @return success
	 */
	public synchronized boolean initConnection() {
		int port = 30000;
		try {
			Logger.log("connecting to " + ip);
			socket = new Socket(ip, port);
			outThread = new OutThread(this, socket);
			inThread = new InThread(this, socket);
			outThread.start();
			inThread.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		started = true;
		notifyAll();
		return socket.isConnected();
	}

	/**
	 * Messages sent from server are put here.
	 * 
	 * @param msg
	 */
	public synchronized void putArrayFromServer(byte[] msg) {
		fromServer.add(msg);
		notifyAll();
	}

	/**
	 * Messages sent from server are read here.
	 * 
	 * @return
	 */
	public synchronized byte[] readArrayFromServer() {
		while (fromServer.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		byte[] tmp = fromServer.poll();

		//Logger.log("Recieved msg from Server: " + tmp[0]);

		notifyAll();
		return tmp;
	}

	/**
	 * Messages sent from client are put here.
	 * 
	 * @param msg
	 */
	public synchronized void putArrayToServer(byte[] msg, byte id) {
		toServer.add(MessageWrapper.wrapMessageToServer(msg, id));
		notifyAll();
	}

	/**
	 * Messages sent from client are read here.
	 * 
	 * @return
	 */
	public synchronized byte[] readArrayToServer() {
		while (toServer.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		byte[] tmp = toServer.poll();
		notifyAll();
		return tmp;
	}

	/**
	 * Closes the socket-connection.
	 */
	public void closeConnection() {
		try {
			this.socket.close();
			Logger.log("ByteMonitor - Closing connection");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Returns if the socket is open or not.
	 * @return
	 */
	public synchronized boolean isOpen(){
		while(!started){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return !socket.isClosed();
	}
}
