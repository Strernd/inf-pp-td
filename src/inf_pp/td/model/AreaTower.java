package inf_pp.td.model;

import inf_pp.td.intercom.TowerType;
import inf_pp.td.intercom.UpgradeType;

/**
 * A tower that does damage to all creeps in range periodically
 */
public class AreaTower extends BaseTower {
	private static final long serialVersionUID = -4110046028129614203L;

	/**
	 * Constructs an AreaTower
	 */
	public AreaTower(){
		ExponentialUpgrade up=new ExponentialUpgrade();
		upgradePolicy=up;
		up.setFunction(UpgradeType.DAMAGE,new ExponentialUpgrade.ExpFun(-5,1.3f,8));
		up.setFunction(UpgradeType.RANGE,new ExponentialUpgrade.ExpFun(.2f,1.1f,1));
		up.setFunction(UpgradeType.FIRERATE,new ExponentialUpgrade.ExpFun(333,0.75f,667));
		towerType=TowerType.AREA_OF_EFFECT;
	}

	/* (non-Javadoc)
	 * @see inf_pp.td.model.BaseTower#doFire(inf_pp.td.model.Game)
	 */
	@Override
	public int doFire(Game game) {
		boolean hasFired=false;
		for (BaseCreep c : game.getCreeps()) {
			if(c.getPosition().distance(this.position)<(float)upgradePolicy.getValue(UpgradeType.RANGE)) {
				c.doDamage(((Float)upgradePolicy.getValue(UpgradeType.DAMAGE)).intValue(),game);
				hasFired=true;
			}
		}
		return hasFired?((Float)upgradePolicy.getValue(UpgradeType.FIRERATE)).intValue():0;
	}
}
