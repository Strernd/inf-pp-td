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

public class Main {

	public static void main(String[] args) {
		
		//Let's look native
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		final GameInterface game=new Game(20);
		final Controller ctrl=new Controller();
		
		//this is needed to assign
		final Frame[] frame=new Frame[1];
		try {
			SwingUtilities.invokeAndWait(new Runnable(){

				@Override
				public void run() {
					frame[0]=new Frame(game);
					//frame[0].setSize(660, 560);
					frame[0].getContentPane().setPreferredSize(new Dimension(680,480));
					frame[0].pack();
					frame[0].setVisible(true);
					frame[0].setTitle("Inf-PP Tower Defense Bernd Strehl/Marc André Wittorf");
					frame[0].setIconImage(Tiles.get(Tiles.TileId.WINDOW_ICON));
				}
				
			});
		} catch (InvocationTargetException | InterruptedException e) {
			// TODO show error?
			e.printStackTrace();
			System.exit(1);
		}
		
		//TODO: do we need to do some cleanup?
		//frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//frame.setModel(game);
		//frame.addListener(ctrl);
		
		//game.addObserver(frame);
		ctrl.setModel(game);
		ctrl.setView(frame[0]);
		//TODO: threads?
		//ctrl.setTickrate(64);
		//ctrl.start();
		
		//TODO: better jitter performance
		//TODO: move to own file
		Thread t=new Thread(){
			public void run(){
				while(true){
					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ctrl.tick();
				}
			}
		};
		t.start();
	}

}
