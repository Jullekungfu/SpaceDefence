package client;

/**
 * Thread to parse gamestats to bytes
 * 
 * @author timdolck
 *
 */
public class ParseGameStats extends Thread {
	
	GameStatsMonitor gsMonitor;
	ByteMonitor bMonitor;
	
	public ParseGameStats(GameStatsMonitor gsMonitor, ByteMonitor bMonitor){
		this.gsMonitor = gsMonitor;
		this.bMonitor = bMonitor;
	}
	
	@Override
	public void run(){
		//TODO: Parse gamestats to bytes!
		
	}

}
