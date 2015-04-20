package client;

import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.geom.Vector2f;


/**
 * Thread to parse bytes to gamestats
 * 
 * @author timdolck
 *
 */
public class ParseBytes extends Thread {
	
	GameStatsMonitor gsMonitor;
	ByteMonitor bMonitor;
	
	public ParseBytes(GameStatsMonitor gsMonitor, ByteMonitor bMonitor){
		this.gsMonitor = gsMonitor;
		this.bMonitor = bMonitor;
	}
	
	@Override
	public void run(){
		while(true){
			byte[] byteArray = bMonitor.readArrayFromServer();
			Queue<Byte> byteQueue = new LinkedList<Byte>();
			for(Byte b : byteArray){
				byteQueue.add(b);
			}
			
			byte id = byteQueue.poll();
			byte hp = byteQueue.poll();
			
			float xpos = bytesToFloat(byteQueue);
			float ypos = bytesToFloat(byteQueue);
			Vector2f pos = new Vector2f(xpos, ypos);
			
			//Parse bytes to gamestats	
		}
	}
	
	private float bytesToFloat(Queue<Byte> byteQueue){
		byte[] floatBytes = new byte[4];
		for(int i = 0; i < 4; i++){
			floatBytes[i] = byteQueue.poll();
		}
		
		int asInt = (floatBytes[0] & 0xFF) 
	            | ((floatBytes[1] & 0xFF) << 8) 
	            | ((floatBytes[2] & 0xFF) << 16) 
	            | ((floatBytes[3] & 0xFF) << 24);
		return Float.intBitsToFloat(asInt);
		
	}

}
