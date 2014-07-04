package inf_pp.td.model;

import inf_pp.td.TimeSource;

import java.awt.geom.Point2D;

public class Slowjectile extends GuidedProjectile{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7387943865012730255L;
	final private float slowFactor;

	/**
	 * @param position
	 * @param target
	 */
	public Slowjectile(Point2D.Float position, BaseCreep target, float slowFactor) {
		super(position,target);
		this.slowFactor=slowFactor;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void hit(Game game) {
		Buff b=new Buff(){

			/**
			 * 
			 */
			private static final long serialVersionUID = -5710203032661040403L;

			@Override
			public Object apply(Object arg0, Type type, TimeSource time) {
				if(type==Buff.Type.MOVE_SPEED){
					return (float)arg0 * slowFactor;
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
