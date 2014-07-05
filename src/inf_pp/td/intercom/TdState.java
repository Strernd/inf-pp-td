package inf_pp.td.intercom;

import java.awt.Point;

/**
 * Class to pass information from Controller to View
 */
public class TdState {

	/**
	 * the currently selected field
	 */
	private final Point selectedField;
	/**
	 * the model object
	 */
	private final GameInterface game;
	
	/**
	 * Constructs a TdState object
	 * @param game the model
	 * @param selectedField the currently selected field
	 */
	public TdState(GameInterface game, Point selectedField) {
		super();
		this.selectedField = selectedField;
		this.game = game;
	}

	/**
	 * @return this state's currently selected field
	 */
	public Point getSelectedField() {
		return selectedField;
	}

	/**
	 * @return this state's model object
	 */
	public GameInterface getGame() {
		return game;
	}
}
