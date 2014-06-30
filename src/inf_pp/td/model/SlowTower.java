package inf_pp.td.model;

import inf_pp.td.Util;

import java.awt.geom.Point2D;
import java.util.HashSet;

public class SlowTower extends BaseTower {
	public SlowTower(){
		ExponentialUpgrade up=new ExponentialUpgrade();
		upgradePolicy=up;
		up.setFunction(UpgradeType.DAMAGE,new ExponentialUpgrade.ExpFun(0,0.9f,0.8f));
		up.setFunction(UpgradeType.RANGE,new ExponentialUpgrade.ExpFun(.2f,1.1f,1));
		up.setFunction(UpgradeType.FIRERATE,new ExponentialUpgrade.ExpFun(333,0.75f,667));
	}

	@Override
	public int doFire(Game game){
		BaseCreep minCreep=null;
		HashSet<BaseCreep> creeps=new HashSet<BaseCreep>(game.getCreeps());
		for (BaseProjectile p: game.getProjectiles()) {
			if(p instanceof Slowjectile) {
				creeps.remove(((Slowjectile)p).target);
			}
		}
		do {
			creeps.remove(minCreep);
			minCreep=Util.nearestCreep(Util.pointToFloat(position), creeps, (float)upgradePolicy.getValue(UpgradeType.RANGE));
		}while(minCreep!=null && minCreep.hasBuff("SlowjectileSlow"));
		if(minCreep==null)
			minCreep=Util.nearestCreep(Util.pointToFloat(position), game.getCreeps(), (float)upgradePolicy.getValue(UpgradeType.RANGE));
		if(minCreep==null)
			return 0;
		
		GuidedProjectile p=new Slowjectile(new Point2D.Float(position.x,position.y),minCreep,(float)upgradePolicy.getValue(UpgradeType.DAMAGE));
		game.addProjectile(p);
		return ((Float)upgradePolicy.getValue(UpgradeType.FIRERATE)).intValue();
	}
}
