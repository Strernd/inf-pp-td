package inf_pp.td.model;

import inf_pp.td.TimeSource;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class GuidedProjectile extends BaseProjectile {
	private static final long serialVersionUID = -6307662759652027476L;
	
	/**
	 * the creep that this projectile targets
	 */
	protected BaseCreep target;
	
	//TODO: subclass damage?

	/**
	 * Construct a Guided Projectile
	 * @param position the position to set the projectile to
	 * @param target the creep that the projectile targets
	 */
	public GuidedProjectile(Point2D.Float position, BaseCreep target) {
		super(position);
		this.target=target;
		this.moveSpeed=0.0015f;
		this.damage=0;
	}
	
	/**
	 * Construct a Guided Projectile
	 * @param position the position to set the projectile to
	 * @param target the creep that the projectile targets
	 * @param moveSpeed the speed that this projectile travels at
	 * @param damage the damage that this projectile deals on impact
	 */
	public GuidedProjectile(Point2D.Float position,BaseCreep target, float moveSpeed, int damage) {
		this(position,target);
		this.moveSpeed=moveSpeed;
		this.damage=damage;
	}

	/* (non-Javadoc)
	 * @see inf_pp.td.model.BaseProjectile#doMove(inf_pp.td.TimeSource, inf_pp.td.model.Game)
	 */
	@Override
	protected boolean doMove(TimeSource time, Game game) {
		List<Point2D.Float> t=new ArrayList<Point2D.Float>();
		t.add(target.getPosition());
		//have we hit the creep?
		if(Util.move(moveSpeed*time.getMillisSinceLastTick(),position,t)>0){
			//if so, hit the creep and tell the game to remove this projectile
			hit(game, time);
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Do damage to the creep when hit
	 * @param game the game that this projectile belongs to
	 * @param time the current time
	 */
	protected void hit(Game game, TimeSource time){
		target.doDamage(damage,game);
	}
}
