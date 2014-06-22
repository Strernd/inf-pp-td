package inf_pp.td.model;

import java.util.LinkedList;

import inf_pp.td.Util;

public class AreaTower extends BaseTower {
	public AreaTower(){
		range=1.2f;
	}

	@Override
	public int doFire(Game game) {
		boolean hasFired=false;
		for (BaseCreep c : game.getCreeps()) {
			if(c.getPosition().distance(this.position)<this.range) {
				c.doDamage(10);
				hasFired=true;
			}
		}
		return hasFired?1000:0;
	}
}
