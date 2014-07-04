package inf_pp.td.model;

import inf_pp.td.intercom.TowerType;
import inf_pp.td.intercom.UpgradeType;

final class PriceProvider {
	private PriceProvider(){}
	
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
	
	public static int getUpgradePrice(BaseTower tower, UpgradeType type) {
		return (tower.getLevel(type)+2)*(PriceProvider.getTowerPrice(tower.getType())/4);
	}
}
