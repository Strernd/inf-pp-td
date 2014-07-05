package inf_pp.td.intercom;

import java.awt.geom.Point2D;

/**
 * Interface to get the important attributes from a projectile
 */
public interface ProjectileInterface {

	/**
	 * @return the projectile's position
	 */
	public abstract Point2D.Float getPosition();

	/**
	 * Get the vector that this projectile moves along.
	 * May be of arbitrary length. Only respect the direction
	 * @return a vector (as position vector) that represents the movement of this projectile 
	 */
	public abstract Point2D.Float getMoveVector();

}