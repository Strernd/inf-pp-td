package inf_pp.td.model;

import inf_pp.td.Util;

import java.awt.geom.Point2D;
import java.util.HashSet;

public class PoisonTower extends BaseTower {
	public PoisonTower(){
		range=2.5f;
	}

	@Override
	public int doFire(Game game){
		BaseCreep minCreep=Util.nearestCreep(Util.pointToFloat(position), game.getCreeps(), this.range);
		//HashSet creeps=new HashSet(game.getCreeps());
		/*for (BaseProjectile p: game.getProjectiles()) {
			if(p instanceof Slowjectile) {
				creeps.remove(((Slowjectile)p).target);
			}
		}
		do {
			creeps.remove(minCreep);
			minCreep=Util.nearestCreep(Util.pointToFloat(position), creeps, this.range);
		}while(minCreep!=null && minCreep.hasBuff("SlowjectileSlow"));
		if(minCreep==null)
			minCreep=Util.nearestCreep(Util.pointToFloat(position), game.getCreeps(), this.range);;*/
		if(minCreep==null)
			return 0;
		
		GuidedProjectile p=new PoisonProjectile(new Point2D.Float(position.x,position.y),minCreep);
		game.addProjectile(p);
		return 2000;
	}
}
