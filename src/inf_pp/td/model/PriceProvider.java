package inf_pp.td.model;

import inf_pp.td.intercom.TowerType;
import inf_pp.td.intercom.UpgradeType;

/**
 * A class to get prices for towers and upgrades
 */
final class PriceProvider {
	/**
	 * Do not instantiate this class
	 */
	private PriceProvider(){}
	
	/**
	 * Get the price of a tower
	 * @param type the type of the tower to get the price of
	 * @return the price of the tower
	 */
	public static int getTowerPrice(TowerType type) {
		//get price based on tower type
		switch(type){
		case DIRECT_DMG:
			return 100;
		case AREA_OF_EFFECT:
			return 200;
		case SLOW:
			return 140;
		case POISON:
			return 150;
		default:
			return 100;

		}
	}
	
	/**
	 * Get the price to upgrade a tower
	 * @param tower the tower
	 * @param type the attribute to upgrade
	 * @return the price to upgrade the tower
	 */
	public static int getUpgradePrice(BaseTower tower, UpgradeType type) {
		//use a formula to respect both tower type and upgraded level
		return (tower.getLevel(type)+2)*(PriceProvider.getTowerPrice(tower.getType())/4);
	}
}
