package inf_pp.td.model;

import inf_pp.td.intercom.UpgradeType;

public interface UpgradePolicy extends java.io.Serializable{
	
	void upgrade(UpgradeType type);
	
	Object getValue(UpgradeType type);

	int getLevel(UpgradeType type);
}
