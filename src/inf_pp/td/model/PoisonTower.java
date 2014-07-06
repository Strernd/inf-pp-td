package inf_pp.td.model;

import inf_pp.td.intercom.TowerType;
import inf_pp.td.intercom.UpgradeType;

import java.awt.geom.Point2D;
import java.util.HashSet;

public class PoisonTower extends BaseTower {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4675002579136994099L;

	public PoisonTower(){
		ExponentialUpgrade up=new ExponentialUpgrade();
		upgradePolicy=up;
		up.setFunction(UpgradeType.DAMAGE,new ExponentialUpgrade.ExpFun(0,0.9f,1500));
		up.setFunction(UpgradeType.RANGE,new ExponentialUpgrade.ExpFun(.2f,1.1f,1));
		up.setFunction(UpgradeType.FIRERATE,new ExponentialUpgrade.ExpFun(33,0.75f,967));
		towerType=TowerType.POISON;
	}

	@Override
	public int doFire(Game game){
		//we want to prefer poisoning an unpoisoned creep, so ignore all creeps that are already being fired on by poison projectiels
		HashSet<BaseCreep> creeps=new HashSet<BaseCreep>(game.getCreeps());
		for (BaseProjectile p: game.getProjectiles()) {
			if(p instanceof PoisonProjectile) {
				creeps.remove(((GuidedProjectile)p).target);
			}
		}
		//and those which already have the poison debuff
		BaseCreep minCreep=Util.nearestCreep(Util.pointToFloat(position), creeps, (float)upgradePolicy.getValue(UpgradeType.RANGE));
		while(minCreep!=null && minCreep.hasBuff("Poisoned")){
			creeps.remove(minCreep);
			minCreep=Util.nearestCreep(Util.pointToFloat(position), creeps, (float)upgradePolicy.getValue(UpgradeType.RANGE));
		}
		//but if there is no unpoisoned creep, just target any creep. better than not firing at all
		if(minCreep==null)
			minCreep=Util.nearestCreep(Util.pointToFloat(position), game.getCreeps(), (float)upgradePolicy.getValue(UpgradeType.RANGE));
		//there is absolutely no creep in range
		if(minCreep==null)
			return 0;
		
		GuidedProjectile p=new PoisonProjectile(new Point2D.Float(position.x,position.y),minCreep,0.0025f,((Float)upgradePolicy.getValue(UpgradeType.DAMAGE)).intValue());
		game.addProjectile(p);
		return ((Float)upgradePolicy.getValue(UpgradeType.FIRERATE)).intValue();
	}
}
