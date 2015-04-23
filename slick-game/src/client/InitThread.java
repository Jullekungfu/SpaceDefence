package client;

import slimpleslickgame.Game;

public class InitThread extends Thread {
	private GameStatsEvents gameStatsMonitor;
	private ByteMonitor byteMonitor;
	private ParseBytes parseBytes;
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
		parseBytes = new ParseBytes(gameStatsMonitor, byteMonitor);
		game.addGSM(gameStatsMonitor, byteMonitor);
	}
}
