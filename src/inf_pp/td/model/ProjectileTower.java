package inf_pp.td.model;

import inf_pp.td.intercom.TowerType;
import inf_pp.td.intercom.UpgradeType;

import java.awt.geom.Point2D;

public class ProjectileTower extends BaseTower {
	private static final long serialVersionUID = 450440427807527071L;

	/**
	 * Construct a ProjectileTower
	 */
	public ProjectileTower(){
		ExponentialUpgrade up=new ExponentialUpgrade();
		upgradePolicy=up;
		up.setFunction(UpgradeType.DAMAGE,new ExponentialUpgrade.ExpFun(-5,1.2f,9));
		up.setFunction(UpgradeType.RANGE,new ExponentialUpgrade.ExpFun(.5f,1.1f,1));
		up.setFunction(UpgradeType.FIRERATE,new ExponentialUpgrade.ExpFun(333,0.75f,667));
		towerType=TowerType.DIRECT_DMG;
	}

	/* (non-Javadoc)
	 * @see inf_pp.td.model.BaseTower#doFire(inf_pp.td.model.Game)
	 */
	@Override
	public int doFire(Game game){
		//this tower fires to the nearest creep in range
		BaseCreep minCreep=Util.nearestCreep(Util.pointToFloat(position), game.getCreeps(), (float)upgradePolicy.getValue(UpgradeType.RANGE));
		//as long as there are any creeps in range
		if(minCreep==null)
			return 0;
		
		GuidedProjectile p=new GuidedProjectile(new Point2D.Float(position.x,position.y),minCreep,0.002f,(int)Math.round((float)upgradePolicy.getValue(UpgradeType.DAMAGE)));
		game.addProjectile(p);
		return (int)Math.round((float)upgradePolicy.getValue(UpgradeType.FIRERATE));
	}
}
