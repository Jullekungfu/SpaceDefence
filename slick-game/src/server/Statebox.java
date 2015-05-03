package server;

import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

//TODO: This should contain a better buffer. Maybe a ringbuffer? Maybe one buffer per client?

public class Statebox{
    private Queue<byte[]> messages;
	private Vector<Socket> clients;

    public Statebox(){
        this.messages = new LinkedList<byte[]>();
		this.clients = new Vector<Socket>();
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
