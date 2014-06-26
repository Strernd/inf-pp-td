/**
 * 
 */
package inf_pp.td.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marc
 *
 */
public class ExponentialUpgrade implements UpgradePolicy {
	
	private static class ExpFun implements java.io.Serializable {
		private final float constant;
		private final float base;
		private final float factor;
		private final float expScaler;
		private int level=0;
		ExpFun(float constant, float base, float factor,
				float expScaler) {
			super();
			this.constant = constant;
			this.base = base;
			this.factor = factor;
			this.expScaler = expScaler;
		}
		
		float getValue() {
			return (float)(constant+factor*Math.pow(base, level*expScaler));
		}
		
		void levelUp(){
			++level;
		}
	}
	
	Map<UpgradeType,ExpFun> upgrader=new HashMap<>();
	
	ExponentialUpgrade(){
		ExpFun f=new ExpFun(-5,1.2f,9,1);
		upgrader.put(UpgradeType.DAMAGE,f);
		f=new ExpFun(.5f,1.1f,1,1);
		upgrader.put(UpgradeType.RANGE,f);
		f=new ExpFun(333,0.75f,667,1);
		upgrader.put(UpgradeType.FIRERATE,f);
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
	
	public Object getValue(UpgradeType type){
		try{
			return upgrader.get(type).getValue();
		}catch(NullPointerException e){
			//TODO: display Error?
			return 0;
		}
	} 
}