package inf_pp.td.model;

import inf_pp.td.TimeSource;
import inf_pp.td.intercom.TowerInterface;
import inf_pp.td.intercom.TowerType;
import inf_pp.td.intercom.UpgradeType;

import java.awt.Point;
import java.util.Iterator;

public abstract class BaseTower implements java.io.Serializable, TowerInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = -270158376536766467L;

	protected TowerType towerType=null;
	
	protected Point position;
	protected long cooldown;
	@Deprecated
	protected float range;
	@Deprecated
	protected int fireRate;
	
	protected UpgradePolicy upgradePolicy;


	/**
	 * fires (a projectile), does damage
	 */
	int doFire(Game game){
		//System.out.println(range);
		return 0;
	}
	
	final void fire(Game game, TimeSource time){
		if(time.getMillisSinceStart()>cooldown){
			cooldown=time.getMillisSinceStart()+doFire(game);
		}
	}
	
	void remove(Game game){
		for(Iterator<BaseTower> it=game.getTowers().iterator();it.hasNext();){
			if(it.next().equals(this)){
				it.remove();
				return;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see inf_pp.td.model.TowerInterface#getPosition()
	 */
	@Override
	public Point getPosition() {
		return position;
	}
	
	/**
	 * sets the position of this tower
	 * @param position the new position
	 */
	void setPosition(Point position) {
		this.position=position;
	}

	void upgrade(UpgradeType type) {
		if(upgradePolicy!=null)
			upgradePolicy.upgrade(type);
	}
	
	int getLevel(UpgradeType type) {
		if(upgradePolicy!=null)
			return upgradePolicy.getLevel(type);
		else
			return 0;
	}
	
	/* (non-Javadoc)
	 * @see inf_pp.td.model.TowerInterface#getType()
	 */
	@Override
	public TowerType getType() {
		return towerType;
	}
}
