package inf_pp.td.intercom;

import java.awt.Point;

/**
 * Interface to get the important attributes of a tower
 */
public interface TowerInterface {

	/**
	 * @return this tower's position
	 */
	public abstract Point getPosition();

	/**
	 * @return this tower's type
	 */
	public abstract TowerType getType();

}