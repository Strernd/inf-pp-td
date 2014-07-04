package inf_pp.td.model;

import inf_pp.td.TimeSource;
import inf_pp.td.Util;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class GuidedProjectile extends BaseProjectile {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6307662759652027476L;
	protected BaseCreep target;
	
	//TODO: subclass damage?

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
	protected boolean doMove(TimeSource time, Game game) {
		List<Point2D.Float> t=new ArrayList<Point2D.Float>();
		t.add(target.getPosition());
		//hit?
		if(Util.move(moveSpeed*time.getMillisSinceLastTick(),position,t)>0){
			hit(game);
			return true;
		}
		else
			return false;
		//return Util.move(moveSpeed*deltaT,position,t)>0;
	}
	
	protected void hit(Game game){
		target.doDamage(damage,game);
	}
}
