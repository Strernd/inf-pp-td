package inf_pp.td.model;

import inf_pp.td.intercom.TowerType;
import inf_pp.td.intercom.UpgradeType;

public class AreaTower extends BaseTower {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4110046028129614203L;

	public AreaTower(){
		ExponentialUpgrade up=new ExponentialUpgrade();
		upgradePolicy=up;
		up.setFunction(UpgradeType.DAMAGE,new ExponentialUpgrade.ExpFun(-5,1.3f,8));
		up.setFunction(UpgradeType.RANGE,new ExponentialUpgrade.ExpFun(.2f,1.1f,1));
		up.setFunction(UpgradeType.FIRERATE,new ExponentialUpgrade.ExpFun(333,0.75f,667));
		towerType=TowerType.AREA_OF_EFFECT;
	}

	@Override
	public int doFire(Game game) {
		boolean hasFired=false;
		for (BaseCreep c : game.getBaseCreeps()) {
			if(c.getPosition().distance(this.position)<(float)upgradePolicy.getValue(UpgradeType.RANGE)) {
				c.doDamage(((Float)upgradePolicy.getValue(UpgradeType.DAMAGE)).intValue(),game);
				hasFired=true;
				//System.out.println(((Float)upgradePolicy.getValue(UpgradeType.DAMAGE)).intValue());
			}
		}
		return hasFired?((Float)upgradePolicy.getValue(UpgradeType.FIRERATE)).intValue():0;
	}
}
