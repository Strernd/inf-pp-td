package inf_pp.td.model;

import inf_pp.td.intercom.UpgradeType;

/**
 * Objects that implement the upgrading behaviour of towers, ie. its attributes, need to implement this interface
 */
public interface UpgradePolicy extends java.io.Serializable{
	
	/**
	 * Upgrade by one level
	 * @param type the type of the attribute
	 */
	public abstract void upgrade(UpgradeType type);
	
	/**
	 * Get an attribute's value
	 * @param type the attribute type
	 * @return the attribute's value
	 */
	public abstract Object getValue(UpgradeType type);

	/**
	 * Get the attribute's level
	 * @param type the attribute type
	 * @return the attribute's level
	 */
	public abstract int getLevel(UpgradeType type);
}
