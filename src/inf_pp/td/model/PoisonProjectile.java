package inf_pp.td.model;

import inf_pp.td.TimeSource;

import java.awt.geom.Point2D.Float;

public class PoisonProjectile extends GuidedProjectile{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2319290502191194982L;
	int damageRate;

	public PoisonProjectile(Float position, BaseCreep target) {
		super(position, target);
		// TODO Auto-generated constructor stub
	}

	public PoisonProjectile(Float position, BaseCreep target, float moveSpeed,
			int damageRate) {
		super(position, target, moveSpeed, 0);
		this.damageRate=damageRate;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void hit(final Game game) {
		Buff b=new Buff(){
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -7153858387373125085L;
			long lastTime=0;

			@Override
			public Object apply(Object arg0, Type type, TimeSource time) {
				if(type==Buff.Type.DOT){
					//int diff=0;
					//TODO: much damage might skip ticks
					if(lastTime+damageRate<time.getMillisSinceStart()){
						//diff=1;
						((BaseCreep)arg0).doDamage(1, game);
						lastTime=time.getMillisSinceStart();
					}
					/*if((int)arg0>0&&(int)arg0 - diff<=0)
						game.addGold(100);*/
					return 0;
				}
				return arg0;
			}

			@Override
			public boolean canRemove() {
				// TODO Auto-generated method stub
				return false;
			}
			
		};
		target.addBuff("Poisoned",b);
	}
}
