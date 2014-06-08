package inf_pp.td;

import inf_pp.td.control.Controller;
import inf_pp.td.model.Game;
import inf_pp.td.view.Frame;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		Game game=new Game();
		Controller ctrl=new Controller();		
		
		Frame frame=new Frame(game);
		frame.setSize(640, 480);
		frame.setVisible(true);
		//TODO: do we need to do some cleanup?
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setModel(game);
		frame.addListener(ctrl);
		
		game.addObserver(frame);
		ctrl.setModel(game);
		//TODO: threads?
		//ctrl.setTickrate(64);
		//ctrl.start();
		
		while(true){
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ctrl.tick();
		}
	}

}
