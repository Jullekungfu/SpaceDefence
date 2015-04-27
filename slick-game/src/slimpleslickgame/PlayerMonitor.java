package slimpleslickgame;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import util.EventProtocol;
import client.ByteMonitor;
import client.GameStatsEvents;

public class PlayerMonitor {
	
	private Queue<Player> players;
	private GameStatsEvents gse;
	
	public PlayerMonitor(GameStatsEvents gse){
		players = new LinkedList<Player>();
		this.gse = gse;
	}
	
	
	public synchronized void addPlayer(byte playerId) {
		OpponentPlayer op = new OpponentPlayer(this.gse, playerId);
		op.init();
		players.add(op);
	}
	
	public synchronized void addLocalPlayer(GameContainer gc, ByteMonitor bm, byte playerId){
		LocalPlayer player = new LocalPlayer(gc, bm, playerId);
		player.init();
		players.add(player);
		byte[] msg = {EventProtocol.OPPONENT_PLAYER_INIT};
		bm.putArrayToServer(msg, playerId);
	}
	
	public synchronized Player getPlayer(){
		if(!players.isEmpty()){
			Player returnPlayer = players.poll();
			return returnPlayer;
		}
		return null;
	}
		
	public synchronized void update(GameContainer gc, StateBasedGame arg1, int delta)
			throws SlickException {
		// TODO: Update all logic
		for(Player p : players){
			p.update(delta);
		}
	}
}
