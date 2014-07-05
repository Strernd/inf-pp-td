package inf_pp.td.model;

import inf_pp.td.TimeSource;
import inf_pp.td.intercom.ProjectileInterface;

import java.awt.geom.Point2D;

/**
 * This base class represents one projectile
 */
public abstract class BaseProjectile implements java.io.Serializable, ProjectileInterface {
	private static final long serialVersionUID = -1262381424454347467L;
	
	/**
	 * the speed in fields/ms that this projectile travels at
	 */
	protected float moveSpeed;
	/**
	 * the current position of this projectile
	 */
	protected Point2D.Float position;
	/**
	 * the amount of damage that this projectile deals on impact
	 */
	protected int damage;
	/**
	 * the previous position; needed to calculate a directional vector
	 */
	private Point2D.Float lastPosition;
	
	/**
	 * Base Constructor
	 * @param position this projectile's starting position
	 */
	public BaseProjectile(Point2D.Float position){
		this.position=position;
		moveSpeed=0.0025f;
		damage=0;
	}
	
	/**
	 * Move this projectile towards its target
	 * @param time the current time
	 * @param game the game object that this projectile belongs to
	 * @return whether to remove this projectile or not after this call
	 */
	public final boolean move(TimeSource time, Game game){
		lastPosition=new Point2D.Float(position.x,position.y);
		return doMove(time,game);
	}

	/**
	 * @see #move
	 */
	protected boolean doMove(TimeSource deltaT, Game game) {
		return true;
	}

	/* (non-Javadoc)
	 * @see inf_pp.td.model.ProjectileInterface#getPosition()
	 */
	@Override
	public Point2D.Float getPosition() {
		return position;
	}
	
	/* (non-Javadoc)
	 * @see inf_pp.td.model.ProjectileInterface#getMoveVector()
	 */
	@Override
	public Point2D.Float getMoveVector() {
		//TODO: make this less hacky?
		return new Point2D.Float(position.x-lastPosition.x, position.y-lastPosition.y);
	}
}
