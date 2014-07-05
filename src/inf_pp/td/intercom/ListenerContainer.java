package inf_pp.td.intercom;

import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.event.MouseInputListener;

/**
 * Interface to hold all the listeners to connect to a view
 */
public interface ListenerContainer {
	/**
	 * @return a MouseInputListener-Instance to be used by the playing-field
	 */
	public MouseInputListener getFieldSelectListener();
	
	/**
	 * @return an ActionListener-Instance to be used by all build and upgrade buttons
	 */
	public ActionListener getActionListener();
	
	/**
	 * @return a WindowListener to be used for window-related actions
	 */
	public WindowListener getWindowListener();
}
