package slimpleslickgame;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import util.EventProtocol;
import client.ByteMonitor;
import client.GameStatsEvents;

public class Game extends BasicGameState {
	public static final int ID = 2;

	private List<Player> players;
	private GameStatsEvents gse;
	private GameContainer gc;
	private ByteMonitor bm;

	public void addGSM(GameStatsEvents gse, ByteMonitor byteMonitor) {
		this.gse = gse;
		this.bm = byteMonitor;
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
		// TODO: Update all logic
		for(Player p : players){
			p.update(delta);
		}
	}

	@Override
	public int getID() {
		return ID;
	}

	public void addPlayer(byte playerId) {
		OpponentPlayer op = new OpponentPlayer(this.gse, playerId);
		op.init();
		players.add(op);
	}
	
	public void addLocalPlayer(byte playerId){

		LocalPlayer player = new LocalPlayer(gc, bm, playerId);
		player.init();
		players.add(player);
		byte[] msg = {EventProtocol.OPPONENT_PLAYER_INIT};
		bm.putArrayToServer(msg, playerId);
	}
	
	/**
	 * Closes the connection held by the bytemonitorn.
	 */
	public void onClose(){
		this.bm.closeConnection();
	}
	
	

}
