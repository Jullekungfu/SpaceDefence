package client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import util.Logger;


/**
 * @author nille
 *
 */
public class OutThread extends Thread {
	private OutputStream os;
	private Socket socket;
	private ByteMonitor bm;
	
	/**
	 * Constructor for OutThread. 
	 * @param bm The ByteMonitor connected to this OutThread
	 * @param s The socket in which the thread writes to.
	 */
	public OutThread(ByteMonitor bm, Socket s){
		this.socket = s;
		this.bm = bm;
		try {
			os = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Logger.log("Outthread created.");
	}
	
	public void run(){
		byte[] msg;
		while(!this.socket.isClosed()){
			msg = this.bm.readArrayToServer();
			try {
				os.write(msg);
				os.flush();
			} catch (IOException ioe) {
				Logger.log("Socket closed");
			}
		}
	}
}
