package inf_pp.td.model;

public interface UpgradePolicy extends java.io.Serializable{
	
	void upgrade(UpgradeType type);
	
	Object getValue(UpgradeType type);
}
