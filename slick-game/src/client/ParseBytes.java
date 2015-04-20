package client;


/**
 * Thread to parse bytes to gamestats
 * 
 * @author timdolck
 *
 */
public class ParseBytes extends Thread {
	
	GameStatsMonitor gsMonitor;
	ByteMonitor bMonitor;
	
	public ParseBytes(GameStatsMonitor gsMonitor, ByteMonitor bMonitor){
		this.gsMonitor = gsMonitor;
		this.bMonitor = bMonitor;
	}
	
	@Override
	public void run(){
		//Parse bytes to gamestats
	}

}
