package server;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;
import java.util.Map.Entry;

import slimpleslickgame.Creep;
import util.Logger;

public class Statebox {
	private Queue<byte[]> messages;
	private HashMap<Integer, Socket> clients;

	public Statebox() {
		this.messages = new LinkedList<byte[]>();
		this.clients = new HashMap<Integer, Socket>();
	}

	public synchronized int getCurrentClients() {
		return clients.size();
	}

	public synchronized byte[] readMessage() {
		while (messages.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		byte[] temp = this.messages.poll();
		notifyAll();
		return temp;
	}

	public synchronized void writeMessage(byte[] msg) {
		messages.offer(msg);
		notifyAll();
	}

	public synchronized void addClient(Socket s) {
		clients.put(new Integer(clients.size() + 1), s);
	}

	public synchronized Socket[] getClientSockets(int originId) {
		Socket[] ss = new Socket[clients.size() - 1];
		HashMap<Integer, Socket> returnMap = new HashMap<Integer, Socket>();
		for (Entry<Integer, Socket> socket : clients.entrySet()) {
			if (originId != socket.getKey()) {
				returnMap.put(socket.getKey(), socket.getValue());
			}
		}
		return returnMap.values().toArray(ss);
	}

	public synchronized boolean removeClientSocket(Socket s) {
		for (Entry<Integer, Socket> socket : clients.entrySet()) {
			if (socket.equals(s)) {
				return clients.remove(socket.getKey()) != null;
			}
		}
		return false;
	}
}
