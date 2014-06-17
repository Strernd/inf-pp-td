package inf_pp.td.model;

import java.awt.geom.Point2D;

public class BaseProjectile {
	protected float moveSpeed;
	protected Point2D.Float position;
	
	public BaseProjectile(Point2D.Float position){
		this.position=position;
		moveSpeed=0.0025f;
	}
	
	public boolean move(long deltaT){
		return true;
	}

	public Point2D.Float getPosition() {
		return position;
	}
}
