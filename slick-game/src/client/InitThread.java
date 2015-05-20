package client;

import slimpleslickgame.Game;

public class InitThread extends Thread {
	private GameStatsEvents gameStatsMonitor;
	private ByteMonitor byteMonitor;
	private ParseBytes parseBytes;
	private Game game;
	private String ip;
	
	public InitThread(String ip, Game game){
		this.ip = ip;
		this.game = game;
	}
	
	public void run(){
		byteMonitor = new ByteMonitor(ip);
		gameStatsMonitor = new GameStatsEvents(game);
		parseBytes = new ParseBytes(gameStatsMonitor, byteMonitor);
		game.addGSM(gameStatsMonitor, byteMonitor);
		parseBytes.start();
		byteMonitor.initConnection();
	}
}
