package slimpleslickgame;

import java.util.ArrayList;
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

	private List<Player> players;
	private GameStatsEvents gse;
	private ByteMonitor byteMonitor;

	public void addGSM(GameStatsEvents gsm, ByteMonitor byteMonitor) {
		this.gse = gsm;
		this.byteMonitor = byteMonitor;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame arg1)
			throws SlickException {
		// TODO: Setup game stuff
		
		players = new ArrayList<Player>();
		
		LocalPlayer player = new LocalPlayer(gc);
		player.init();
		players.add(player);
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
		players.add(new OpponentPlayer(this.gse, playerId));
	}
	
	
	
	

}
