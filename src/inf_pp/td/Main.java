package inf_pp.td;

import javax.swing.JFrame;

import inf_pp.td.model.Game;
import inf_pp.td.view.Frame;

public class Main {

	public static void main(String[] args) {
		
		Game game=new Game();
		
		Frame frame=new Frame();
		frame.setSize(640, 480);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
