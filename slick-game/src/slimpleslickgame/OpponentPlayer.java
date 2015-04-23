package slimpleslickgame;

import client.GameEvent;
import client.GameStatsEvents;

public class OpponentPlayer extends Player {

	private GameStatsEvents gse;
	
	public OpponentPlayer(GameStatsEvents gse, byte id){
		this.gse = gse;
		super.id = id;
	}
	
	@Override
	public void update(int delta) {
		GameEvent e = gse.pop(id);
		if(e != null){
			//pop event data
		}
		super.updatePosition();
	}

}
