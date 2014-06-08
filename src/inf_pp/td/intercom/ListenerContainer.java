package inf_pp.td.intercom;

import java.awt.event.ActionListener;

import javax.swing.event.MouseInputListener;

public interface ListenerContainer {
	/**
	 * @return a MouseInputListener-Instance to be used by the playing-field
	 */
	public MouseInputListener getFieldSelectListener();
	
	/**
	 * @return an ActionListener-Instance to be used by all build and upgrade buttons
	 */
	public ActionListener getButtonListener();
}
