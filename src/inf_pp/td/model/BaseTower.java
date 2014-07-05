package inf_pp.td.model;

import inf_pp.td.TimeSource;
import inf_pp.td.intercom.TowerInterface;
import inf_pp.td.intercom.TowerType;
import inf_pp.td.intercom.UpgradeType;

import java.awt.Point;
import java.util.Iterator;

/**
 * This class represents a tower
 */
public abstract class BaseTower implements java.io.Serializable, TowerInterface {
	private static final long serialVersionUID = -270158376536766467L;

	/**
	 * this tower's type. Extending classes should set this
	 */
	protected TowerType towerType=null;
	
	/**
	 * this tower's position
	 */
	protected Point position;
	/**
	 * the next time that this tower can fire again
	 */
	protected long cooldown;
	
	/**
	 * an UpgradePolicy that handles leveling
	 */
	protected UpgradePolicy upgradePolicy;


	/**
	 * fires, does damage
	 * @param game the game that this tower belongs to
	 * @return the time in ms that this tower has to wait before firing again
	 */
	int doFire(Game game){
		return 0;
	}
	
	/**
	 * calls doFire, handles the cooldown
	 * @param game
	 * @param time
	 */
	final void fire(Game game, TimeSource time){
		//only fire if not on cooldown
		if(time.getMillisSinceStart()>cooldown){
			cooldown=time.getMillisSinceStart()+doFire(game);
		}
	}
	
	/**
	 * Removes this tower from the game
	 * @param game the game that this tower belongs to
	 */
	void remove(Game game){
		//loop through all towers and delete if found itself
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

	/**
	 * Upgrades an attribute of this tower by one level
	 * @param type the type of the attribute to upgrade
	 */
	void upgrade(UpgradeType type) {
		if(upgradePolicy!=null)
			upgradePolicy.upgrade(type);
	}

	/**
	 * Get the level of an attribute of this tower's
	 * @param type the attribute type.
	 * @return the level
	 */
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
