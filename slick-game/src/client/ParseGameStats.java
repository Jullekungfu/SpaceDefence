package client;

/**
 * Thread to parse gamestats to bytes
 * 
 * @author timdolck
 *
 */
public class ParseGameStats extends Thread {
	
	GameStatsEvents gsMonitor;
	ByteMonitor bMonitor;
	
	public ParseGameStats(GameStatsEvents gsMonitor, ByteMonitor bMonitor){
		this.gsMonitor = gsMonitor;
		this.bMonitor = bMonitor;
	}
	
	@Override
	public void run(){
		//TODO: Parse gamestats to bytes!
		
	}

}
