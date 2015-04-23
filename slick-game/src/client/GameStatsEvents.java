package client;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Contains gamestats for all players currently in game.
 * 
 * @author timdolck
 *
 */
public class GameStatsEvents {
	private ConcurrentMap<Byte, Queue<PlayerEvent>> events;
	
	public GameStatsEvents(){
		events = new ConcurrentHashMap<Byte, Queue<PlayerEvent>>();
	}

	public PlayerEvent pop(byte id) {
		Queue<PlayerEvent> queue = events.get(id);
		if (queue == null) {
			throw new IllegalArgumentException("Id does not exist, id: " + id);
		}
		if(queue.isEmpty()){
			return null;
		}
		return queue.poll();
	}
	
	public void put(byte id, PlayerEvent event){
		Queue<PlayerEvent> queue = this.events.get(id);
		if(queue == null){
			throw new IllegalArgumentException("Id does not exist, id: " + id);
		}
		
		queue.offer(event);
	}
	
	public boolean addPlayer(byte id){
		if(!events.containsKey(id)){
			return events.put(id, new LinkedList<PlayerEvent>()) != null;
		}
		return false;
	}
	
	

}
