package inf_pp.td.model;

final class PriceProvider {
	private PriceProvider(){}
	
	public static int getTowerPrice(TowerType type) {
		return 100;
	}
	
	public static int getUpgradePrice(BaseTower tower, UpgradeType type) {
		return (tower.getLevel(type)+2)*10;
	}
}
