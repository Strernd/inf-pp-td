package inf_pp.td;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;

import inf_pp.td.control.Controller;
import inf_pp.td.intercom.GameInterface;
import inf_pp.td.model.Game;
import inf_pp.td.view.Frame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * The Main class, program entry
 *
 */
public class Main {

	public static void main(String[] args) {
		boolean tshowNativeError=false;
		final boolean showNativeError;
		
		//Let's look native
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			tshowNativeError=true;
		}
		showNativeError=tshowNativeError;
		
		//Create a model and a controller instance
		final GameInterface game=new Game(20);
		final Controller ctrl=new Controller();
		
		//this is needed to assign the frame in invokeAndWait
		final Frame[] frame=new Frame[1];
		try {
			SwingUtilities.invokeAndWait(new Runnable(){

				@Override
				public void run() {
					//Create and initialize the View
					frame[0]=new Frame();
					frame[0].getContentPane().setPreferredSize(new Dimension(680,480));
					frame[0].pack();
					frame[0].setVisible(true);
					frame[0].setTitle("Inf-PP Tower Defense Bernd Strehl/Marc André Wittorf");
					frame[0].setIconImage(Tiles.get(Tiles.TileId.WINDOW_ICON));
					frame[0].setLocationRelativeTo(null);
					if(showNativeError)
						frame[0].putWarning("Could not set native Look and Feel");
				}
				
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		
		ctrl.setModel(game);
		ctrl.setView(frame[0]);

		ctrl.startGameThread();
		ctrl.startRenderingThread();
	}

}
