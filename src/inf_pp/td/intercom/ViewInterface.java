package inf_pp.td.intercom;

import java.awt.Component;

public interface ViewInterface {

	/**
	 * Update the view
	 * @param state the TdState object to get the game's current state from
	 */
	public abstract void updateState(TdState state);

	/**
	 * binds all listeners
	 * @param l a ListenerContainer to get the listers from.
	 */
	public abstract void addListener(ListenerContainer l);

	/**
	 * Informs the view that a new game has started
	 * @param game the new model object
	 */
	public abstract void newGame(GameInterface game);

	/**
	 * Displays a warning to the user
	 * @param warning the warning to display
	 */
	public abstract void putWarning(String warning);
	
	/**
	 * Gets a Component to use for things like showing a JOptionPane
	 * @return an arbitrary Component
	 */
	public abstract Component getComponent();
}