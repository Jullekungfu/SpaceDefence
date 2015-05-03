package client;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import slimpleslickgame.Game;

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
	
	

}
