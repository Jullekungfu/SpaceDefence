package server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Statebox{
    private Queue<byte[]> messages;
	private ArrayList<Socket> clients;

    public Statebox(){
        this.messages = new LinkedList<byte[]>();
		this.clients = new ArrayList<Socket>();
    }
    
    public synchronized int getCurrentClients(){
    	return clients.size();
    }

    public synchronized byte[] readMessage(){
    	while(messages.isEmpty()){ 
            try {
                wait();
            } catch (InterruptedException e){e.printStackTrace();}
        }
    	
        byte[] temp = this.messages.poll();
        notifyAll();
        return temp;
    }

    public synchronized void writeMessage(byte[] msg){
        messages.offer(msg);
        notifyAll();
    }

	public synchronized void addClient(Socket s) {
		clients.add(s);
	}
	
	public synchronized Socket[] getClientSockets(){
		Socket[] ss = new Socket[clients.size()];
		return clients.toArray(ss);
	}
	
	public synchronized boolean removeClientSocket(Socket s){
		return clients.remove(s);
	}
}
