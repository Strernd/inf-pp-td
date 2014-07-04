/**
 * 
 */
package inf_pp.td.model;

import inf_pp.td.intercom.UpgradeType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marc
 *
 */
public class ExponentialUpgrade implements UpgradePolicy {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3530294941604608880L;

	public static class ExpFun implements java.io.Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -883109936244651951L;
		private final float constant;
		private final float base;
		private final float factor;
		private int level=0;
		public ExpFun(float constant, float base, float factor) {
			super();
			this.constant = constant;
			this.base = base;
			this.factor = factor;
		}
		
		protected float getValue() {
			return (float)(constant+factor*Math.pow(base, level));
		}
		
		private void levelUp(){
			++level;
		}
	}
	
	Map<UpgradeType,ExpFun> upgrader=new HashMap<>();
	
	ExponentialUpgrade(){
		/*ExpFun f=new ExpFun(-5,1.2f,9);
		upgrader.put(UpgradeType.DAMAGE,f);
		f=new ExpFun(.5f,1.1f,1);
		upgrader.put(UpgradeType.RANGE,f);
		f=new ExpFun(333,0.75f,667);
		upgrader.put(UpgradeType.FIRERATE,f);*/
	}

	/* (non-Javadoc)
	 * @see inf_pp.td.model.UpgradePolicy#upgrade(inf_pp.td.model.UpgradeType)
	 */
	@Override
	public void upgrade(UpgradeType type) {
		try{
			upgrader.get(type).levelUp();
		} catch(NullPointerException e){
			//e.printStackTrace();
		}
	}
	
	@Override
	public Object getValue(UpgradeType type){
		try{
			return upgrader.get(type).getValue();
		}catch(NullPointerException e){
			//TODO: display Error?
			return 0;
		}
	}
	
	@Override
	public int getLevel(UpgradeType type){
		return upgrader.get(type).level;
	}
	
	public void setFunction(UpgradeType type, ExpFun f) {
		upgrader.put(type, f);
	}
}
