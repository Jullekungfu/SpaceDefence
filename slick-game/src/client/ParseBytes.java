package client;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.geom.Vector2f;

import util.EventProtocol;

/**
 * Thread to parse bytes to gamestats
 * 
 * @author timdolck
 *
 */
public class ParseBytes extends Thread {

	GameStatsEvents gsMonitor;
	ByteMonitor bMonitor;

	public ParseBytes(GameStatsEvents gsMonitor, ByteMonitor bMonitor) {
		this.gsMonitor = gsMonitor;
		this.bMonitor = bMonitor;
	}

	@Override
	public void run() {
		GameEvent event;
		while (true) {
			byte[] byteArray = bMonitor.readArrayFromServer();
			System.out.println("polled new msg:");
			for(byte b : byteArray){
				System.out.print(" "+b);
			}
			System.out.println();
			Queue<Byte> byteQueue = new LinkedList<Byte>();
			for (Byte b : byteArray) {
				byteQueue.add(b);
			}
			byte p = byteQueue.poll();
			if(p != EventProtocol.PLAYER_ID){
				System.err.println("ERROR IN PROTOCOL, recieved:" + p + " excpected:" + EventProtocol.PLAYER_ID);
				continue;
			}
				
			byte id = byteQueue.poll();
			event = new GameEvent(id);
			
			Byte b = null;
			if ((b = byteQueue.poll()) != null) {
				switch (b) {
					case EventProtocol.LOCAL_PLAYER_INIT:
						gsMonitor.addLocalPlayer(id);
						break;
					case EventProtocol.OPPONENT_PLAYER_INIT:
						System.out.println(gsMonitor.addOpponentPlayer(id));
						break;
					case EventProtocol.PLAYER_POS:
						float xpos = bytesToFloat(byteQueue);
						float ypos = bytesToFloat(byteQueue);
						Vector2f pos = new Vector2f(xpos, ypos);
						try {
							event.putPosition(pos);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case EventProtocol.PLAYER_DIR:
						float xdir = bytesToFloat(byteQueue);
						float ydir = bytesToFloat(byteQueue);
						Vector2f dir = new Vector2f(xdir, ydir);
						try {
							event.putDirection(dir);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;

				}
			}
			gsMonitor.put(id, event);
		}
	}

	private float bytesToFloat(Queue<Byte> byteQueue) {
		byte[] floatBytes = new byte[4];
		for (int i = 0; i < 4; i++) {
			floatBytes[i] = byteQueue.poll();
		}
		return ByteBuffer.wrap(floatBytes).asFloatBuffer().get();
	}

}
