package inf_pp.td.model;

public interface UpgradePolicy {
	
	void upgrade(UpgradeType type);
	
	float getRange();
	float getDamage();
	float getCooldown();
}
