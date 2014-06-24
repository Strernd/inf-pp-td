package inf_pp.td.model;

import inf_pp.td.model.Buff.Type;

import java.awt.geom.Point2D.Float;

public class PoisonProjectile extends GuidedProjectile {

	public PoisonProjectile(Float position, BaseCreep target) {
		super(position, target);
		// TODO Auto-generated constructor stub
	}

	public PoisonProjectile(Float position, BaseCreep target, float moveSpeed,
			int damage) {
		super(position, target, moveSpeed, damage);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void hit() {
		Buff b=new Buff(){
			
			long lastTime=0;

			@Override
			public Object apply(Object arg0, Type type, long time) {
				if(type==Buff.Type.DOT){
					int diff=0;
					if(lastTime+500<time){
						diff=5;
						lastTime=time;
					}
					return (int)arg0 - diff;
				}
				return arg0;
			}

			@Override
			public boolean canRemove() {
				// TODO Auto-generated method stub
				return false;
			}
			
		};
		target.addBuff("SlowjectileSlow",b);
	}
}
