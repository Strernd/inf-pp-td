package inf_pp.td.model;

import inf_pp.td.Util;

import java.awt.geom.Point2D;

public class SlowTower extends BaseTower {
	public SlowTower(){
		range=2.5f;
	}

	@Override
	public int doFire(Game game){
		BaseCreep minCreep=Util.nearestCreep(Util.pointToFloat(position), game.getCreeps(), this.range);
		if(minCreep==null)
			return 0;
		GuidedProjectile p=new Slowjectile(new Point2D.Float(position.x,position.y),minCreep);
		game.addProjectile(p);
		return 250;
	}
}
