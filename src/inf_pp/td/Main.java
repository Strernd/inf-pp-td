package inf_pp.td;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import inf_pp.td.control.Controller;
import inf_pp.td.model.Game;
import inf_pp.td.view.Frame;

import javax.swing.JFrame;
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
		
		Game game=new Game(20);
		final Controller ctrl=new Controller();
		
		Frame frame=new Frame(game);
		frame.setSize(640, 480);
		frame.setVisible(true);
		//TODO: do we need to do some cleanup?
		//frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//frame.setModel(game);
		//frame.addListener(ctrl);
		
		//game.addObserver(frame);
		ctrl.setModel(game);
		ctrl.setView(frame);
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
					long t2=System.nanoTime();
					ctrl.tick();
				}
			}
		};
		t.start();
	}

}
