package client;
import java.nio.ByteBuffer;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

import util.EventProtocol;


/**
 * Stores byte arrays sent from client/server
 * @author julian
 *
 */
public class ByteMonitor {
	private LinkedList<byte[]> fromServer;
	private LinkedList<byte[]> toServer;
	private Socket socket;
	private String ipport;
	private InThread inThread;
	private OutThread outThread;
	
	/**
	 * Init monitor
	 * @param ipport
	 */
	public ByteMonitor(String ipport){
		fromServer = new LinkedList<byte[]>();
		toServer = new LinkedList<byte[]>();
		this.ipport = ipport;
	}
	
	/**
	 * Init connection to server.
	 * @return success
	 */
	public boolean initConnection(){
		int split = ipport.indexOf(':');
		String ip = ipport.substring(0, split);
		int port = Integer.parseInt(ipport.substring(split+1));
		try {
			System.out.println("connecting to " + ip);
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
		return socket.isConnected();
	}
	
	/**
	 * Messages sent from server are put here.
	 * @param msg
	 */
	public synchronized void putArrayFromServer(byte[] msg){
		fromServer.add(msg);
		notifyAll();
	}
	
	/**
	 * Messages sent from server are read here.
	 * @return
	 */
	public synchronized byte[] readArrayFromServer(){
		while(fromServer.isEmpty()){
			try{
				wait();
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		byte[] tmp = fromServer.poll();
		
		System.out.println("Recieved msg from Server: " + tmp[0]);

		notifyAll();
		return tmp;
	}
	
	/**
	 * Messages sent from client are put here.
	 * @param msg
	 */
	public synchronized void putArrayToServer(byte[] msg, byte id){
		byte[] temp = new byte[msg.length + 6];
		byte[] msgLength = ByteBuffer.allocate(4).putInt(msg.length + 2).array(); 
		temp[0] = msgLength[0];
		temp[1] = msgLength[1];
		temp[2] = msgLength[2];
		temp[3] = msgLength[3];
		temp[4] = EventProtocol.PLAYER_ID;
		temp[5] = id;
		for(int i = 0; i < msg.length; i++){
			temp[i+6] = msg[i];
		}
		toServer.add(temp);
		notifyAll();
	}
	
	/**
	 * Messages sent from client are read here.
	 * @return
	 */
	public synchronized byte[] readArrayToServer(){
		while(toServer.isEmpty()){
			try{
				wait();
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		byte[] tmp = toServer.poll();
		notifyAll();
		return tmp;
	}
}
