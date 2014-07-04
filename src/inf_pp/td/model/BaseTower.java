package inf_pp.td.model;

import inf_pp.td.TimeSource;
import inf_pp.td.intercom.TowerType;
import inf_pp.td.intercom.UpgradeType;

import java.awt.Point;
import java.util.Iterator;

public abstract class BaseTower implements java.io.Serializable {
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
	 * check if this tower is able to fire at the moment
	 * @return true if tower can fire
	 * @return false if tower can not fire
	 */
	@Deprecated
	public boolean canFire(){
		//TODO: remove?
		return cooldown<System.currentTimeMillis();
	}
	
	/**
	 * fires (a projectile), does damage
	 */
	public int doFire(Game game){
		//System.out.println(range);
		return 0;
	}
	
	public void fire(Game game, TimeSource time){
		if(time.getMillisSinceStart()>cooldown){
			cooldown=time.getMillisSinceStart()+doFire(game);
		}
	}
	
	public void remove(Game game){
		for(Iterator<BaseTower> it=game.getTowers().iterator();it.hasNext();){
			if(it.next().equals(this)){
				it.remove();
			}
		}
	}
	
	/**
	 * @return this towers position
	 */
	public Point getPosition() {
		return position;
	}
	
	/**
	 * sets the position of this tower
	 * @param position the new position
	 */
	public void setPosition(Point position) {
		this.position=position;
	}

	public void upgrade(UpgradeType type) {
		if(upgradePolicy!=null)
			upgradePolicy.upgrade(type);
	}
	
	public int getLevel(UpgradeType type) {
		if(upgradePolicy!=null)
			return upgradePolicy.getLevel(type);
		else
			return 0;
	}
	
	public TowerType getType() {
		return towerType;
	}
}
