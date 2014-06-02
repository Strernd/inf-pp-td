package inf_pp.td.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SideBar extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2976601330037398908L;

	public SideBar() {
		this.setBackground(new Color(0xFF0000));
		this.setPreferredSize(new Dimension(160,0));
		this.setMinimumSize(new Dimension(160,0));
		BoxLayout layout = new BoxLayout(this,BoxLayout.Y_AXIS);
		this.setLayout(layout);
		JPanel twrPane=new JPanel();
		twrPane.add(new JButton("SingleTargetTower"));
		twrPane.add(new JButton("AoeTower"));
		twrPane.add(new JButton("Slow-Tower"));
		this.add(twrPane);
		
		this.add(new JLabel("Gold:"));
		this.add(new JLabel("Leben:"));
		this.add(new JLabel("Welle:"));
		Box box=Box.createVerticalBox();
		
		//layout.
	}
}
