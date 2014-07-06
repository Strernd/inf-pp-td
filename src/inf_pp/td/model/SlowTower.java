package inf_pp.td.model;

import inf_pp.td.intercom.TowerType;
import inf_pp.td.intercom.UpgradeType;

import java.awt.geom.Point2D;
import java.util.HashSet;

public class SlowTower extends BaseTower {
	private static final long serialVersionUID = 5703318639305833649L;

	/**
	 * Construct the SlowTower
	 */
	public SlowTower(){
		ExponentialUpgrade up=new ExponentialUpgrade();
		upgradePolicy=up;
		up.setFunction(UpgradeType.DAMAGE,new ExponentialUpgrade.ExpFun(0,0.9f,0.8f));
		up.setFunction(UpgradeType.RANGE,new ExponentialUpgrade.ExpFun(.2f,1.1f,1));
		up.setFunction(UpgradeType.FIRERATE,new ExponentialUpgrade.ExpFun(333,0.75f,667));
		towerType=TowerType.SLOW;
	}

	/* (non-Javadoc)
	 * @see inf_pp.td.model.BaseTower#doFire(inf_pp.td.model.Game)
	 */
	@Override
	public int doFire(Game game){
		//slow tower prefers firing to an unslowed creep, so remove all creeps that have a slowjectile flying to them
		HashSet<BaseCreep> creeps=new HashSet<BaseCreep>(game.getCreeps());
		for (BaseProjectile p: game.getProjectiles()) {
			if(p instanceof Slowjectile) {
				creeps.remove(((Slowjectile)p).target);
			}
		}
		BaseCreep minCreep=Util.nearestCreep(Util.pointToFloat(position), creeps, (float)upgradePolicy.getValue(UpgradeType.RANGE));
		//and all creeps that are already slowed
		while(minCreep!=null && minCreep.hasBuff("SlowjectileSlow")){
			creeps.remove(minCreep);
			minCreep=Util.nearestCreep(Util.pointToFloat(position), creeps, (float)upgradePolicy.getValue(UpgradeType.RANGE));
		}
		//SlowTower waits instead of instantly reslowing a creep, to keep cooldown low
		//if(minCreep==null)
		//	minCreep=Util.nearestCreep(Util.pointToFloat(position), game.getCreeps(), (float)upgradePolicy.getValue(UpgradeType.RANGE));
		if(minCreep==null)
			return 0;
		
		GuidedProjectile p=new Slowjectile(new Point2D.Float(position.x,position.y),minCreep,(float)upgradePolicy.getValue(UpgradeType.DAMAGE));
		game.addProjectile(p);
		return ((Float)upgradePolicy.getValue(UpgradeType.FIRERATE)).intValue();
	}
}
