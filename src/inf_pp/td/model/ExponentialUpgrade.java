/**
 * 
 */
package inf_pp.td.model;

import inf_pp.td.intercom.UpgradeType;

import java.util.HashMap;
import java.util.Map;

/**
 * An upgradePolicy that uses exponential formulas to calculate attributes
 */
public class ExponentialUpgrade implements UpgradePolicy {
	private static final long serialVersionUID = 3530294941604608880L;

	/**
	 * an exponential function of form c+f*b^level
	 */
	public static class ExpFun implements java.io.Serializable {
		private static final long serialVersionUID = -883109936244651951L;
		
		/**
		 * the constant c in c+f*b^level
		 */
		private final float constant;
		/**
		 * the base b in c+f*b^level
		 */
		private final float base;
		/**
		 * the factor f in c+f*b^level
		 */
		private final float factor;
		/**
		 * the level of this attribute
		 */
		private int level=0;
		
		/**
		 * Construct an exponential formula
		 * @param constant c in c+f*b^level
		 * @param base b in c+f*b^level
		 * @param factor f in c+f*b^level
		 */
		public ExpFun(float constant, float base, float factor) {
			super();
			this.constant = constant;
			this.base = base;
			this.factor = factor;
		}
		
		/**
		 * Get the value with current level
		 * @return c+f*b^level
		 */
		protected float getValue() {
			//pretty straightforward calculation of c+f*b^level
			return (float)(constant+factor*Math.pow(base, level));
		}
		
		/**
		 * Increase the level by one
		 */
		private void levelUp(){
			++level;
		}
	}
	
	/**
	 * Holds all exponential functions to each attribute
	 */
	private Map<UpgradeType,ExpFun> upgrader=new HashMap<>();
	
	/**
	 * Constructs an ExponentialUpgrade
	 */
	ExponentialUpgrade(){
	}

	/* (non-Javadoc)
	 * @see inf_pp.td.model.UpgradePolicy#upgrade(inf_pp.td.model.UpgradeType)
	 */
	@Override
	public void upgrade(UpgradeType type) {
		try{
			upgrader.get(type).levelUp();
		} catch(NullPointerException e){
		}
	}
	
	/* (non-Javadoc)
	 * @see inf_pp.td.model.UpgradePolicy#getValue(inf_pp.td.intercom.UpgradeType)
	 */
	@Override
	public Object getValue(UpgradeType type){
		try{
			return upgrader.get(type).getValue();
		}catch(NullPointerException e){
			return 0;
		}
	}
	
	/* (non-Javadoc)
	 * @see inf_pp.td.model.UpgradePolicy#getLevel(inf_pp.td.intercom.UpgradeType)
	 */
	@Override
	public int getLevel(UpgradeType type){
		return upgrader.get(type).level;
	}
	
	/**
	 * Set the exponential function for a upgrade type
	 * @param type the type
	 * @param f the function to use for this type
	 */
	public void setFunction(UpgradeType type, ExpFun f) {
		upgrader.put(type, f);
	}
}
