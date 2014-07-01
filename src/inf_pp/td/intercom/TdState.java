package inf_pp.td.intercom;

import inf_pp.td.model.Game;

import java.awt.Point;

public class TdState {

	private final Point selectedField;
	private final Game game;
	
	public TdState(Game game, Point selectedField) {
		super();
		this.selectedField = selectedField;
		this.game = game;
	}

	public Point getSelectedField() {
		return selectedField;
	}

	public Game getGame() {
		return game;
	}
}
