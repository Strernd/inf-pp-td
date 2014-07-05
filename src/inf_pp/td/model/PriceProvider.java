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
		switch(type){
		case DIRECT_DMG:
			return 100;
		case AREA_OF_EFFECT:
			return 200;
		case SLOW:
			return 200;
		case POISON:
			return 300;
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
		return (tower.getLevel(type)+2)*(PriceProvider.getTowerPrice(tower.getType())/4);
	}
}
