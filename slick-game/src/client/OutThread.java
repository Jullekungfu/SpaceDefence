package client;

import java.io.OutputStream;

public class OutThread extends Thread {
	private OutputStream os;
	
	public OutThread(OutputStream os){
		this.os = os;
	}
	
	public void run(){
		//TODO: Implement.
	}
}
