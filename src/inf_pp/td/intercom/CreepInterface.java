package inf_pp.td.intercom;

import java.awt.geom.Point2D;

/**
 * Interface to access important attributes of a creep
 */
public interface CreepInterface {

	/**
	 * Returns the current position of a creep
	 * 
	 * @return position
	 */
	public abstract Point2D.Float getPosition();

	/**
	 * Returns if the creep is dead or not
	 * 
	 * @return is the creep dead?
	 */
	public abstract boolean isDead();

	/**
	 * Returns the health in absolute value
	 * 
	 * @return Health in Factor of maximum health
	 */
	public abstract float getHealthPercentage();

	public abstract String getType();

}