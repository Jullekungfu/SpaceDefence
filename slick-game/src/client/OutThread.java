package client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class OutThread extends Thread {
	private OutputStream os;
	private Socket socket;
	
	public OutThread(Socket s){
		this.socket = s;
		try {
			os = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		//TODO: Implement.
		while(!this.socket.isClosed()){
			
			
			
		}
	}
}
