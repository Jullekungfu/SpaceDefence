package client;

import java.util.Map;

import org.newdawn.slick.geom.Vector2f;

/**
 * Contains gamestats for all players currently in game.
 * 
 * @author timdolck
 *
 */
public class GameStatsMonitor {
	
	private class GameStats{
		byte id;
		byte hp;
		byte lvl;
		Vector2f pos;
		
		GameStats(byte id){
			this.id = id;
			hp = 100;
			lvl = 1;
			//...
		}
	}
	
	private Map<Byte, GameStats> playerStats;

	public synchronized void addPlayer(byte id) {
		playerStats.put(id, new GameStats(id));
	}

}
