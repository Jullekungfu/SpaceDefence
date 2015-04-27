package slimpleslickgame;
import java.util.ArrayList;

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
	private PlayerMonitor playerMonitor;
	private ArrayList<Player> players;

	public void addGSM(GameStatsEvents gse, ByteMonitor byteMonitor, PlayerMonitor playerMonitor) {
		this.bm = byteMonitor;
		this. playerMonitor = playerMonitor;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame arg1)
			throws SlickException {
		// TODO: Setup game stuff
		this.gc = gc;
		players = new ArrayList<Player>();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)
			throws SlickException {
		// TODO:render all stuff here

		for(Player p : players){
			p.render(g);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int delta)
			throws SlickException {
		Player player = null;
		if(playerMonitor != null&&(player = playerMonitor.getPlayer()) != null){
			players.add(player);
		}
		for(Player p : players){
			p.update(delta);
		}
	}

	@Override
	public int getID() {
		return ID;
	}

	public void addPlayer(byte playerId) {
		playerMonitor.addPlayer(playerId);
	}
	
	public void addLocalPlayer(byte playerId){
		playerMonitor.addLocalPlayer(gc, bm, playerId);
	}
	/**
	 * Closes the connection held by the bytemonitorn.
	 */
	public void onClose(){
		this.bm.closeConnection();
	}
}
