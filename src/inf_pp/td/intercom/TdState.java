package inf_pp.td.intercom;

import java.awt.Point;

public class TdState {

	private final Point selectedField;
	private final GameInterface game;
	
	public TdState(GameInterface game, Point selectedField) {
		super();
		this.selectedField = selectedField;
		this.game = game;
	}

	public Point getSelectedField() {
		return selectedField;
	}

	public GameInterface getGame() {
		return game;
	}
}
