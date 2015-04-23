package client;

import slimpleslickgame.Game;

public class InitThread extends Thread {
	private GameStatsEvents gameStatsMonitor;
	private ByteMonitor byteMonitor;
	private ParseBytes parseBytes;
	private ParseGameStats parseGameStats;
	private Game game;
	private String ipport;
	
	public InitThread(String ipport, Game game){
		this.ipport = ipport;
		this.game = game;
	}
	
	public void run(){
		byteMonitor = new ByteMonitor(ipport);
		gameStatsMonitor = new GameStatsEvents();
		parseBytes = new ParseBytes(gameStatsMonitor, byteMonitor);
		parseGameStats = new ParseGameStats(gameStatsMonitor, byteMonitor);
		game.addGSM(gameStatsMonitor);
	}
}
