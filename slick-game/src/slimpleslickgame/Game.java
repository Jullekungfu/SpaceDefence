package slimpleslickgame;
import java.util.concurrent.ConcurrentHashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import util.EventProtocol;
import client.ByteMonitor;
import client.Client;
import client.GameStatsEvents;

public class Game extends BasicGameState {
	public static final int ID = 2;

	private GameContainer gc;
	private ByteMonitor bm;
	private ConcurrentHashMap<Byte, GameInstance> instances;
	private GameStatsEvents gse;
	private Vector2f boardSize;
	private boolean gameStarted;
	private boolean gameOver;

	public void addGSM(GameStatsEvents gse, ByteMonitor byteMonitor) {
		this.bm = byteMonitor;
		this.gse = gse;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame arg1)
			throws SlickException {
		this.gc = gc;
		this.boardSize = new Vector2f(gc.getWidth()/4, gc.getHeight());
		instances = new ConcurrentHashMap<Byte, GameInstance>();
		gameStarted = false;
		gameOver = false;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)
			throws SlickException {
		if(gameStarted){
			if(gameOver){
				g.drawString("GAME IS OVER, PRESS ENTER TO EXIT", (Client.WIDTH/2)-150, (Client.HEIGHT/2)+200);
			} else {
				for(GameInstance gi : instances.values()){
					gi.render(g);
				}
			}
		} else {
			int th = g.getFont().getLineHeight();
			int tw = 100;
			
			g.drawString("PRESS ENTER TO START GAME\nPlayers in game: " + instances.size(), (Client.WIDTH/2)-tw, (Client.HEIGHT/2)-th);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int delta)
			throws SlickException {
		if(gameStarted){
			if(gameOver){
				if(gc.getInput().isKeyPressed(Input.KEY_ENTER)){
					System.exit(0);
				}
			} else {
				int totalP = 0;
				int deadP = 0;
				for(GameInstance gi : instances.values()){
					if (gi.isPlayerDead()){
						deadP++;
					}
					totalP++;
				}
				gameOver = (deadP >= (totalP-1));
				
				for(GameInstance gi : instances.values()){
					gi.update(delta);
				}
			}
		} else {
			if(gc.getInput().isKeyPressed(Input.KEY_ENTER)){
				byte[] bytes = new byte[]{EventProtocol.GAME_STARTED};
				bm.putArrayToServer(bytes, (byte) -1);
			}
		}
	}

	@Override
	public int getID() {
		return ID;
	}
	
	public void startGame(){
		gameStarted = true;
	}

	public void addOpponentPlayer(byte playerId) {
		addPlayer(new OpponentPlayer(playerId, this.gse));
	}
	
	public void addLocalPlayer(byte playerId){
		addPlayer(new LocalPlayer(gc, bm, gse, playerId));
	}
	
	private void addPlayer(Player player){
		GameInstance gs = new GameInstance(player, this.boardSize);
		instances.put(player.id, gs);
	}
	
	/**
	 * Closes the connection held by the bytemonitorn.
	 */
	public void onClose(){
		if(this.bm.isOpen())
			this.bm.closeConnection();
	}

	public void removePlayer(byte playerId) {
		//TODO: remove stuff inside instance?
		try{
			instances.remove(playerId);
		}catch(Exception e){
			//TODO: end game
		}
		
	}

	public boolean isStarted() {
		return gameStarted;
	}
}
