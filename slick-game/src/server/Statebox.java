package server;

import java.net.Socket;
import java.util.Vector;

//TODO: This should contain a better buffer. Maybe a ringbuffer? Maybe one buffer per client?

public class Statebox{
    private byte[] message;
	private Vector<Socket> clients;

    public Statebox(){
        this.message = null;
		this.clients = new Vector<Socket>();
    }
    
    public synchronized int getCurrentClients(){
    	return clients.size();
    }

    public synchronized byte[] readMessage(){
    	while(message == null){ 
            try {
                wait();
            } catch (InterruptedException e){e.printStackTrace();}
        }
    	
        byte[] temp = this.message;
        this.message = null;;
        notifyAll();
        return temp;
    }

    public synchronized void writeMessage(byte[] msg){
       while(message != null){ 
            try {
                wait();
            } catch (InterruptedException e){e.printStackTrace();}
        }
        this.message = msg;
        notifyAll();
    }

	public synchronized void addClient(Socket s) {
		clients.add(s);
	}
	
	public synchronized Socket[] getClientSockets(){
		return (Socket[]) clients.toArray();
	}
	
	public synchronized boolean removeClientSocket(Socket s){
		return clients.remove(s);
	}
}
