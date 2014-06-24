package inf_pp.td.model;

import inf_pp.td.Util;

import java.awt.geom.Point2D;

public class ProjectileTower extends BaseTower {
	
	public ProjectileTower(){
		range=2.5f;
	}

	@Override
	public int doFire(Game game){
		BaseCreep minCreep=Util.nearestCreep(Util.pointToFloat(position), game.getCreeps(), this.range);

		if(minCreep==null)
			return 0;
		GuidedProjectile p=new GuidedProjectile(new Point2D.Float(position.x,position.y),minCreep,0.002f,10);
		game.addProjectile(p);
		return 500;
	}
}
