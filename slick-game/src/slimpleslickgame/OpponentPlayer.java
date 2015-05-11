package slimpleslickgame;

import org.newdawn.slick.geom.Shape;

import util.Logger;
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
				int cid = e.getId();
				if(e.isAlive()){
					super.creeps.put(cid, new Creep(e.getPosition()));
				} else {
					Logger.log("c id: " + cid);
					Logger.log("" + super.creeps.containsKey(cid));
					super.creeps.remove(cid);
				}
				break;
			}
			case PLAYER: {
				if (e.getPosition() != null)
					super.position = e.getPosition();

				if (e.getDirection() != null) {
					// super.direction = e.getDirection();
				}
				super.updatePosition(containerShape);
				break;
			} case BULLET: {
				if(e.isAlive()){
					super.gun.shoot(e.getPosition());
				} else {
					super.gun.delete(e.getId());
				}
				break;
			}
			}
		}
		for(Creep c : super.creeps.values()){
			c.update(delta);
		}
		super.gun.update(delta);
	}
}
