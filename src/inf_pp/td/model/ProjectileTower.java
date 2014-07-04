package inf_pp.td.model;

import inf_pp.td.intercom.TowerType;

import java.awt.geom.Point2D;

public class ProjectileTower extends BaseTower {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 450440427807527071L;

	public ProjectileTower(){
		//range=2.5f;
		ExponentialUpgrade up=new ExponentialUpgrade();
		upgradePolicy=up;
		up.setFunction(UpgradeType.DAMAGE,new ExponentialUpgrade.ExpFun(-5,1.2f,9));
		up.setFunction(UpgradeType.RANGE,new ExponentialUpgrade.ExpFun(.5f,1.1f,1));
		up.setFunction(UpgradeType.FIRERATE,new ExponentialUpgrade.ExpFun(333,0.75f,667));
		towerType=TowerType.DIRECT_DMG;
	}

	@Override
	public int doFire(Game game){
		BaseCreep minCreep=Util.nearestCreep(Util.pointToFloat(position), game.getCreeps(), (float)upgradePolicy.getValue(UpgradeType.RANGE));
		//System.out.println((float)upgradePolicy.getValue(UpgradeType.RANGE));

		//System.out.println((int)Math.round((float)upgradePolicy.getValue(UpgradeType.DAMAGE)));
		if(minCreep==null)
			return 0;
		GuidedProjectile p=new GuidedProjectile(new Point2D.Float(position.x,position.y),minCreep,0.002f,(int)Math.round((float)upgradePolicy.getValue(UpgradeType.DAMAGE)));
		game.addProjectile(p);
		return (int)Math.round((float)upgradePolicy.getValue(UpgradeType.FIRERATE));
	}
}
