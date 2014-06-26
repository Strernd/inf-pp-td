package inf_pp.td.model;

import inf_pp.td.Util;

import java.awt.geom.Point2D;

public class ProjectileTower extends BaseTower {
	
	public ProjectileTower(){
		//range=2.5f;
		upgradePolicy=new ExponentialUpgrade();
	}

	@Override
	public int doFire(Game game){
		BaseCreep minCreep=Util.nearestCreep(Util.pointToFloat(position), game.getCreeps(), (float)upgradePolicy.getValue(UpgradeType.RANGE));
		//System.out.println((float)upgradePolicy.getValue(UpgradeType.RANGE));

		System.out.println((int)Math.round((float)upgradePolicy.getValue(UpgradeType.DAMAGE)));
		if(minCreep==null)
			return 0;
		GuidedProjectile p=new GuidedProjectile(new Point2D.Float(position.x,position.y),minCreep,0.002f,(int)Math.round((float)upgradePolicy.getValue(UpgradeType.DAMAGE)));
		game.addProjectile(p);
		return (int)Math.round((float)upgradePolicy.getValue(UpgradeType.FIRERATE));
	}
}
