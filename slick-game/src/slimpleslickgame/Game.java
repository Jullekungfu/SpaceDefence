package slimpleslickgame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import client.ByteMonitor;
import client.GameStatsEvents;

public class Game extends BasicGameState {
	public static final int ID = 2;

	private GameContainer gc;
	private ByteMonitor bm;
	private List<Player> players;
	private GameStatsEvents gse;

	public void addGSM(GameStatsEvents gse, ByteMonitor byteMonitor) {
		this.bm = byteMonitor;
		this.gse = gse;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame arg1)
			throws SlickException {
		// TODO: Setup game stuff
		this.gc = gc;
		players = Collections.synchronizedList(new ArrayList<Player>());
	}

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)
			throws SlickException {
		// TODO:render all stuff here

		synchronized (players){
			for(Player p : players){
				p.render(g);
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int delta)
			throws SlickException {
		synchronized (players) {
			for(Player p : players){
				p.update(delta);
			}
		}
	}

	@Override
	public int getID() {
		return ID;
	}

	public void addOpponentPlayer(byte playerId) {
		addPlayer(new OpponentPlayer(this.gse, playerId));
	}
	
	public void addLocalPlayer(byte playerId){
		addPlayer(new LocalPlayer(gc, bm, playerId));
	}
	
	private void addPlayer(Player player){
		player.init();
		synchronized(players){
			players.add(player);
		}
	}
	
	/**
	 * Closes the connection held by the bytemonitorn.
	 */
	public void onClose(){
		this.bm.closeConnection();
	}
}
