package client;

import slimpleslickgame.Game;
import slimpleslickgame.PlayerMonitor;

public class InitThread extends Thread {
	private GameStatsEvents gameStatsMonitor;
	private ByteMonitor byteMonitor;
	private ParseBytes parseBytes;
	private PlayerMonitor playerMonitor;
	private Game game;
	private String ipport;
	
	public InitThread(String ipport, Game game){
		this.ipport = ipport;
		this.game = game;
	}
	
	public void run(){
		byteMonitor = new ByteMonitor(ipport);
		byteMonitor.initConnection();
		gameStatsMonitor = new GameStatsEvents(game);
		playerMonitor = new PlayerMonitor(gameStatsMonitor);
		parseBytes = new ParseBytes(gameStatsMonitor, byteMonitor);
		game.addGSM(gameStatsMonitor, byteMonitor, playerMonitor);
		parseBytes.start();
	}
}
