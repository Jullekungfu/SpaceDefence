package client;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.newdawn.slick.geom.Vector2f;
import slimpleslickgame.Game;
import util.GameRole;

/**
 * Contains events for all players currently in game.
 * 
 * @author timdolck
 *
 */
public class GameStatsEvents {
	private ConcurrentMap<Byte, Queue<GameEvent>> events;
	private Game game;
	
	public GameStatsEvents(Game game){
		events = new ConcurrentHashMap<Byte, Queue<GameEvent>>();
		this.game = game;
	}

	public GameEvent pop(byte id) {
		Queue<GameEvent> queue = events.get(id);
		if (queue == null) {
			throw new IllegalArgumentException("Id does not exist, id: " + id);
		}
		if(queue.isEmpty()){
			return null;
		}
		return queue.poll();
	}
	
	public void put(byte id, GameEvent event){
		Queue<GameEvent> queue = this.events.get(id);
		if(queue == null){
			throw new IllegalArgumentException("Id does not exist, id: " + id);
		}
		
		queue.offer(event);
	}
	
	public boolean addOpponentPlayer(byte id){
		if(!events.containsKey(id)){
			System.out.println("added player " + id);
			events.put(id, new LinkedList<GameEvent>());
			game.addOpponentPlayer(id);
			return true;
		}
		return false;
	}

	public boolean addLocalPlayer(byte id) {
		if(!events.containsKey(id)){
			for(byte i = 1; i < id;i++){
				addOpponentPlayer(i);
			}
			events.put(id, new LinkedList<GameEvent>());
			game.addLocalPlayer(id);
			return true;
		}
		return false;
	}
	
	public boolean addCreep(byte playerId, byte creepId, Vector2f pos) {
		if(events.containsKey(playerId)){
			System.out.println("added creep" + creepId);
			GameEvent event = new GameEvent(GameRole.CREEP, playerId);
			try {
				event.putPosition(pos);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			events.get(playerId).add(event);
			return true;
		}
		return false;
	}

}
