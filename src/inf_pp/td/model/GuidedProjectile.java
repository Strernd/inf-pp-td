package inf_pp.td.model;

import inf_pp.td.Util;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;
import java.util.List;

public class GuidedProjectile extends BaseProjectile {
	protected BaseCreep target;

	public GuidedProjectile(Point2D.Float position,BaseCreep target) {
		super(position);
		this.target=target;
		this.moveSpeed=0.0015f;
		this.damage=0;
	}
	
	public GuidedProjectile(Point2D.Float position,BaseCreep target, float moveSpeed, int damage) {
		this(position,target);
		this.moveSpeed=moveSpeed;
		this.damage=damage;
	}

	@Override
	public boolean move(long deltaT) {
		List<Point2D.Float> t=new ArrayList<Point2D.Float>();
		t.add(target.getPosition());
		//hit?
		if(Util.move(moveSpeed*deltaT,position,t)>0){
			target.doDamage(damage);
			return true;
		}
		else
			return false;
		//return Util.move(moveSpeed*deltaT,position,t)>0;
	}
	
}
