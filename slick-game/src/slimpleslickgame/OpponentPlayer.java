package slimpleslickgame;

import org.newdawn.slick.geom.Shape;

import util.Logger;
import client.GameEvent;
import client.GameStatsEvents;

public class OpponentPlayer extends Player {

	private GameStatsEvents gse;

	public OpponentPlayer(GameStatsEvents gse, byte id) {
		super(id);
		this.gse = gse;
	}

	@Override
	public void update(int delta, Shape containerShape) {
		GameEvent e;
		int score = 0;
		while ((e = gse.pop(id)) != null) {
			switch (e.getRole()) {
				case CREEP: {
					if (e.isAlive()) {
						super.creeps.put(e.getId(), new Creep(e.getPosition()));
					} else {
						super.creeps.remove(e.getId());
					}
					break;
				}
				case PLAYER: {
					if (e.getPosition() != null) {
						super.position = e.getPosition();
					}
					if (e.getDirection() != null) {
						// super.direction = e.getDirection();
					}
					if (e.getScore() != 0) {
						score += e.getScore();
					}
					super.updatePosition(containerShape);
					break;
				}
				case BULLET: {
					if (e.isAlive()) {
						super.gun.shoot(e.getPosition());
					} else {
						super.gun.delete(e.getId());
					}
					break;
				}
			}
		}
		for (Creep c : super.creeps.values()) {
			c.update(delta);
		}
		super.gun.update(delta);
		stats.calcCreditsDiff(0, score);
		stats.update(delta);
	}
}
