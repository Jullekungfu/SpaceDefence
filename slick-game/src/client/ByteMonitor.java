package client;

import java.util.LinkedList;


/**
 * Stores byte arrays sent from client/server
 * @author julian
 *
 */
public class ByteMonitor {
	private LinkedList<byte[]> fromServer;
	private LinkedList<byte[]> toServer;
	
	
	public ByteMonitor(){
		fromServer = new LinkedList<byte[]>();
		toServer = new LinkedList<byte[]>();
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
		notifyAll();
		return tmp;
	}
	
	

	/**
	 * Messages sent from client are put here.
	 * @param msg
	 */
	public synchronized void putArrayToServer(byte[] msg){
		toServer.add(msg);
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
