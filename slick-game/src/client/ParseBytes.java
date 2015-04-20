package client;

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
			byte[] bytes = bMonitor.readArrayFromServer();
			int parsed = 0;
			byte id = bytes[parsed++];
			byte hp = bytes[parsed++];
			
			byte[] floatBytes = new byte[4];
			for(int i = 0; i < 4; i++){
				floatBytes[i] = bytes[parsed++];
			}
			float xpos = bytesToFloat(floatBytes);
			for(int i = 0; i < 4; i++){
				floatBytes[i] = bytes[parsed++];
			}
			float ypos = bytesToFloat(floatBytes);
			Vector2f pos = new Vector2f(xpos, ypos);
			
			//Parse bytes to gamestats	
		}
	}
	
	private float bytesToFloat(byte[] floatBytes){
		int asInt = (floatBytes[0] & 0xFF) 
	            | ((floatBytes[1] & 0xFF) << 8) 
	            | ((floatBytes[2] & 0xFF) << 16) 
	            | ((floatBytes[3] & 0xFF) << 24);
		return Float.intBitsToFloat(asInt);
		
	}

}
