package client;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.geom.Vector2f;

import util.EventProtocol;
import util.GameRole;
import util.Logger;

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
		while (bMonitor.isOpen()) {
			boolean gsEvent = false;
			byte[] byteArray = bMonitor.readArrayFromServer();
			
			Queue<Byte> byteQueue = new LinkedList<Byte>();
			for (Byte b : byteArray) {
				byteQueue.add(b);
			}
			byte p = byteQueue.poll();
			if (p != EventProtocol.PLAYER_ID) {
				Logger.log("ERROR IN PROTOCOL, recieved:" + p
						+ " excpected:" + EventProtocol.PLAYER_ID);
				continue;
			}

			byte id = byteQueue.poll();
			event = new GameEvent();

			Byte b = null;
			float xpos;
			float ypos;
			Vector2f pos;
			while ((b = byteQueue.poll()) != null) {
				switch (b) {
				case EventProtocol.GAME_STARTED:
					gsMonitor.startGame();
					gsEvent = true;
					break;
				case EventProtocol.LOCAL_PLAYER_INIT:
					Logger.log("Local player init");
					gsMonitor.addLocalPlayer(id);
					break;
				case EventProtocol.OPPONENT_PLAYER_INIT:
					Logger.log("opponent init");
					gsMonitor.addOpponentPlayer(id);
					break;
				case EventProtocol.PLAYER_LOST_CONNECTION:
					Logger.log("Player lost connection");
					gsMonitor.removePlayer(id);
					break;
				case EventProtocol.EVENT_POS:
					xpos = bytesToFloat(byteQueue);
					ypos = bytesToFloat(byteQueue);
					pos = new Vector2f(xpos, ypos);
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
						e.printStackTrace();
					}
					break;
				case EventProtocol.PLAYER_SCORE:
					event.setScore(this.bytesToInt(byteQueue));
					break;
				case EventProtocol.CREEP_INIT: {
					byteQueue.poll();// get rid of CREEP_ID byte
					int creepId = bytesToInt(byteQueue);
					event.setRole(GameRole.CREEP);
					event.setId(creepId);
					break;
				}
				case EventProtocol.CREEP_DIED: {
					byteQueue.poll();
					int creepId = bytesToInt(byteQueue);
					event.setRole(GameRole.CREEP);
					event.setId(creepId);
					event.setDead();
					break;
				}
				case EventProtocol.BULLET_INIT: {
					byteQueue.poll();// get rid of BULLET_ID byte
					int bulletId = bytesToInt(byteQueue);
					event.setRole(GameRole.BULLET);
					event.setId(bulletId);
					break;
				}
				case EventProtocol.BULLET_DIED: {
					byteQueue.poll(); 
					int bulletId = bytesToInt(byteQueue);
					event.setRole(GameRole.BULLET);
					event.setId(bulletId);
					event.setDead();
					break;
				}
				}
			}
			if(!gsEvent){
				gsMonitor.put(id, event);
			}
		}
		gsMonitor.removeLocalPlayer();
	}

	private float bytesToFloat(Queue<Byte> byteQueue) {
		byte[] floatBytes = new byte[4];
		for (int i = 0; i < 4; i++) {
			floatBytes[i] = byteQueue.poll();
		}
		return ByteBuffer.wrap(floatBytes).asFloatBuffer().get();
	}
	
	private int bytesToInt(Queue<Byte> byteQueue){
		byte[] intBytes = new byte[4];
		for (int i = 0; i < 4; i++) {
			intBytes[i] = byteQueue.poll();
		}
		return ByteBuffer.wrap(intBytes).asIntBuffer().get();

	}

}
