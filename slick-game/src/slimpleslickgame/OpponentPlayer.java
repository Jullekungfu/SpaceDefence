package slimpleslickgame;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Shape;

import util.ColorSwitch;
import util.Logger;
import client.GameEvent;
import client.GameStatsEvents;

public class OpponentPlayer extends Player {

	public OpponentPlayer(byte id, GameStatsEvents gse) {
		super(id, gse);
	}

	@Override
	public void update(int delta, Shape containerShape) {
		if(dead)
			return;
		
		GameEvent e;
		int score = 0;
		while ((e = gse.pop(id)) != null) {
			switch (e.getRole()) {
				case CREEP: {
					if (e.isAlive()) {
						super.creeps.put(e.getId(), new Creep(e.getPosition(), ColorSwitch.getColorFromId(e.getSendId())));
					} else {
						super.creeps.remove(e.getId());
					}
					break;
				}
				case PLAYER: {
					if (!e.isAlive()){
						dead = true;
					}
					
					if(e.getPlayerHp() != -1){
						stats.setHP(e.getPlayerHp());
					}
					
					if (e.getPosition() != null) {
						super.position = e.getPosition();
					}
					if (e.getDirection() != null) {
						// super.direction = e.getDirection();
					}
					if (e.getScore() != 0) {
						score = e.getScore();
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
		stats.calcCreditsDiff(0, score);
		stats.update(delta);
		super.gun.update(delta);
	}
}
