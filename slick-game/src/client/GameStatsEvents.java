package client;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Contains events for all players currently in game.
 * 
 * @author timdolck
 *
 */
public class GameStatsEvents {
	private ConcurrentMap<Byte, Queue<GameEvent>> events;
	
	public GameStatsEvents(){
		events = new ConcurrentHashMap<Byte, Queue<GameEvent>>();
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
	
	public boolean addPlayer(byte id){
		if(!events.containsKey(id)){
			//TODO: game.addPlayer(id);
			return events.put(id, new LinkedList<GameEvent>()) != null;
		}
		return false;
	}
	
	

}
