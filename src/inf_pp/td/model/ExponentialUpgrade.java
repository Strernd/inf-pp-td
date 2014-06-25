/**
 * 
 */
package inf_pp.td.model;

/**
 * @author Marc
 *
 */
public class ExponentialUpgrade implements UpgradePolicy {
	
	@SuppressWarnings("unused")
	private static class Parameters{
		float constant=0;
		float base=(float)Math.E;
		float factor=1;
		float expScaler=1;
	}

	/* (non-Javadoc)
	 * @see inf_pp.td.model.UpgradePolicy#upgrade(inf_pp.td.model.UpgradeType)
	 */
	@Override
	public void upgrade(UpgradeType type) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see inf_pp.td.model.UpgradePolicy#getRange()
	 */
	@Override
	public float getRange() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see inf_pp.td.model.UpgradePolicy#getDamage()
	 */
	@Override
	public float getDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see inf_pp.td.model.UpgradePolicy#getCooldown()
	 */
	@Override
	public float getCooldown() {
		// TODO Auto-generated method stub
		return 0;
	}

}
