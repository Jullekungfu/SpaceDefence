package slimpleslickgame;

import org.newdawn.slick.geom.Shape;

import client.GameEvent;
import client.GameStatsEvents;

public class OpponentPlayer extends Player {

	private GameStatsEvents gse;

	public OpponentPlayer(GameStatsEvents gse, byte id) {
		this.gse = gse;
		super.id = id;
	}

	@Override
	public void update(int delta, Shape containerShape) {
		GameEvent e;
		while ((e = gse.pop(id)) != null) {
			switch (e.getRole()) {
			case CREEP: {
				super.creeps.put((int) e.getId(), new Creep(e.getPosition()));
				System.out.println(e.getPosition().x);
				break;
			}
			case PLAYER: {
				if (e.getPosition() != null)
					super.position = e.getPosition();

				if (e.getDirection() != null) {
					// super.direction = e.getDirection();
				}
				super.updatePosition();
				break;
			}
			}
		}
		for(Creep c : super.creeps.values()){
			c.update(delta);
		}

	}

}
