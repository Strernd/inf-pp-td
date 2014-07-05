package inf_pp.td.model;

import inf_pp.td.TimeSource;

import java.awt.geom.Point2D;

/**
 * A projectile that slows a creep on impact
 */
public class Slowjectile extends GuidedProjectile{
	private static final long serialVersionUID = -7387943865012730255L;
	
	/**
	 * The factor to multiply the movespeed by
	 */
	final private float slowFactor;

	/**
	 * Construct a Slowjectile
	 * @param position the position to set the projectile to
	 * @param target the creep to target
	 * @param slowFactor the factor to multiply the creep's movespeed by when hitting
	 */
	public Slowjectile(Point2D.Float position, BaseCreep target, float slowFactor) {
		super(position,target);
		this.slowFactor=slowFactor;
	}
	
	/* (non-Javadoc)
	 * @see inf_pp.td.model.GuidedProjectile#hit(inf_pp.td.model.Game, inf_pp.td.TimeSource)
	 */
	@Override
	protected void hit(Game game, final TimeSource time) {
		Buff b=
		/**
		 * A slowing debuff 
		 */
		new Buff(){
			private static final long serialVersionUID = -5710203032661040403L;
			
			/**
			 * the time that the buff will stop to function
			 */
			private final long expireTime=time.getMillisSinceStart()+5000;

			/* (non-Javadoc)
			 * @see inf_pp.td.model.Buff#apply(java.lang.Object, inf_pp.td.model.Buff.Type, inf_pp.td.TimeSource)
			 */
			@Override
			public Object apply(Object arg0, Type type, TimeSource time) {
				if(type==Buff.Type.MOVE_SPEED){
					return (float)arg0 * slowFactor;
				}
				return arg0;
			}

			/* (non-Javadoc)
			 * @see inf_pp.td.model.Buff#canRemove(inf_pp.td.TimeSource)
			 */
			@Override
			public boolean canRemove(TimeSource time) {
				return time.getMillisSinceStart()>expireTime;
			}
			
		};
		target.addBuff("SlowjectileSlow",b);
	}

}
