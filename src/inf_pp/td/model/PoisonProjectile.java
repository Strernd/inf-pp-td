package inf_pp.td.model;

import inf_pp.td.TimeSource;

import java.awt.geom.Point2D.Float;

public class PoisonProjectile extends GuidedProjectile{
	private static final long serialVersionUID = -2319290502191194982L;
	
	/**
	 * the time distance between two DOT-hits (in ms)
	 */
	int damageRate;

	/**
	 * Construct a Poison Projectile
	 * @param position the position to set the projectile to
	 * @param target the creep that the projectile targets
	 * @param moveSpeed the speed that this projectile travels at
	 * @param damageRate the time distance between two DOT-hits (in ms)
	 */
	public PoisonProjectile(Float position, BaseCreep target, float moveSpeed,
			int damageRate) {
		super(position, target, moveSpeed, 0);
		this.damageRate=damageRate;
	}

	/* (non-Javadoc)
	 * @see inf_pp.td.model.GuidedProjectile#hit(inf_pp.td.model.Game, inf_pp.td.TimeSource)
	 */
	@Override
	protected void hit(final Game game, TimeSource time) {
		Buff b=new Buff(){
			private static final long serialVersionUID = -7153858387373125085L;
			
			/**
			 * the time the DOT applied last
			 */
			long lastTime=0;

			/* (non-Javadoc)
			 * @see inf_pp.td.model.Buff#apply(java.lang.Object, inf_pp.td.model.Buff.Type, inf_pp.td.TimeSource)
			 */
			@Override
			public Object apply(Object arg0, Type type, TimeSource time) {
				if(type==Buff.Type.DOT){
					//TODO: much damage might skip ticks
					if(lastTime+damageRate<time.getMillisSinceStart()){
						((BaseCreep)arg0).doDamage(1, game);
						lastTime=time.getMillisSinceStart();
					}
					return 0;
				}
				return arg0;
			}

			/* (non-Javadoc)
			 * @see inf_pp.td.model.Buff#canRemove(inf_pp.td.TimeSource)
			 */
			@Override
			public boolean canRemove(TimeSource time) {
				//Poison is fatal
				return false;
			}
			
		};
		target.addBuff("Poisoned",b);
	}
}
