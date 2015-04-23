package client;

public class InitThread extends Thread {
	private GameStatsMonitor gameStatsMonitor;
	private ByteMonitor byteMonitor;
	private ParseBytes parseBytes;
	private ParseGameStats parseGameStats;
	private String ipport;
	
	public InitThread(String ipport){
		this.ipport = ipport;
		
	}
	
	public void run(){
		byteMonitor = new ByteMonitor(ipport);
		gameStatsMonitor = new GameStatsMonitor();
		parseBytes = new ParseBytes(gameStatsMonitor, byteMonitor);
		parseGameStats = new ParseGameStats(gameStatsMonitor, byteMonitor);
	}
}
