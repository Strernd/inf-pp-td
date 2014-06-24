package inf_pp.td.model;

import java.awt.Point;

public final class TowerFactory {
	/**
	 * All available tower types are listed here.
	 */
	public enum TowerType {
		DIRECT_DMG,AREA_OF_EFFECT,SLOW,P
	}
	
	/**
	 * Create an instance of a tower.
	 * The class of the tower and possibly any values to give to the tower
	 * will be determined by the type-parameter.
	 * @param type which type the tower is of
	 * @param position where to place the tower
	 * @return the created tower
	 */
	static BaseTower buildTower(TowerType type, Point position){
		BaseTower t;
		//TODO: implement different towers
		switch(type){
		case AREA_OF_EFFECT:
			t=new AreaTower();
			break;
		case SLOW:
			t=new SlowTower();
			break;
		case P:
			t=new PoisonTower();
			break;
		default:
			t=new ProjectileTower();
			break;
		}
		t.setPosition(position);
		return t;
	}
}
