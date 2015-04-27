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
		if(e != null && e.getPosition() != null){
			//System.out.println("Got position: " + e.getPosition());
			super.position = e.getPosition();
			if(e.getDirection() != null){
				System.out.println("Gor direction");
				super.direction = e.getDirection();
			}
			super.updatePosition();
		}
	}

}
