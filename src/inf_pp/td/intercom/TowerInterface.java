package inf_pp.td.intercom;

import java.awt.Point;

public interface TowerInterface {

	/**
	 * @return this towers position
	 */
	public abstract Point getPosition();

	public abstract TowerType getType();

}