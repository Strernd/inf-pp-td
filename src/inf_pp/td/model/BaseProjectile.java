package inf_pp.td.model;

import inf_pp.td.TimeSource;

import java.awt.geom.Point2D;

public class BaseProjectile implements java.io.Serializable {
	protected float moveSpeed;
	protected Point2D.Float position;
	protected int damage;
	
	public BaseProjectile(Point2D.Float position){
		this.position=position;
		moveSpeed=0.0025f;
		damage=0;
	}
	
	public boolean move(TimeSource deltaT){
		return true;
	}

	public Point2D.Float getPosition() {
		return position;
	}
}
