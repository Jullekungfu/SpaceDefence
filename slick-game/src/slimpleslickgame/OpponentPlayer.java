package slimpleslickgame;

import client.GameEvent;
import client.GameStatsEvents;

public class OpponentPlayer extends Player {

	private GameStatsEvents gse;
	private byte id;
	
	public OpponentPlayer(GameStatsEvents gse, byte id){
		this.gse = gse;
		this.id = id;
	}
	
	@Override
	public void update(int delta) {
		GameEvent e = gse.pop(id);
		if(e != null && e.getPosition() != null){
			System.out.println("x: " + e.getPosition().x);
			super.position = e.getPosition();
			super.updatePosition();
		}
	}

}
