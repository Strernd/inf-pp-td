package inf_pp.td.model;

import inf_pp.td.TimeSource;
import inf_pp.td.intercom.ProjectileInterface;

import java.awt.geom.Point2D;

public abstract class BaseProjectile implements java.io.Serializable, ProjectileInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1262381424454347467L;
	protected float moveSpeed;
	protected Point2D.Float position;
	protected int damage;
	private Point2D.Float lastPosition;
	
	public BaseProjectile(Point2D.Float position){
		this.position=position;
		moveSpeed=0.0025f;
		damage=0;
	}
	
	public final boolean move(TimeSource deltaT, Game game){
		lastPosition=new Point2D.Float(position.x,position.y);
		return doMove(deltaT,game);
	}

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
	
	//TODO: this is hacky...
	/* (non-Javadoc)
	 * @see inf_pp.td.model.ProjectileInterface#getMoveVector()
	 */
	@Override
	public Point2D.Float getMoveVector() {
		return new Point2D.Float(position.x-lastPosition.x, position.y-lastPosition.y);
	}
}
