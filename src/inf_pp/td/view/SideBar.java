package inf_pp.td.view;

import inf_pp.td.model.Game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

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
	
	JLabel livesLabel;
	JLabel goldLabel;
	JLabel waveLabel;

	public SideBar() {
		this.setBackground(new Color(0xCCCCCC));
		this.setPreferredSize(new Dimension(160,0));
		this.setMinimumSize(new Dimension(160,0));
		BoxLayout layout = new BoxLayout(this,BoxLayout.Y_AXIS);
		this.setLayout(layout);
		JPanel twrPane=new JPanel();
		twrPane.setLayout(new GridLayout(2,2,4,4));
		twrPane.add(new JButton("DD"));
		twrPane.add(new JButton("AE"));
		twrPane.add(new JButton("SL"));
		twrPane.add(new JButton("P"));
		twrPane.setPreferredSize(new Dimension(160,160));
		twrPane.setMaximumSize(new Dimension(160,160));
		this.add(twrPane);
		
		this.add(Box.createVerticalGlue());
		
		JPanel uPane=new JPanel();
		uPane.setLayout(new GridLayout(2,2,4,4));
		uPane.add(new JButton("Damage"));
		uPane.add(new JButton("Range"));
		uPane.add(new JButton("Firerate"));
		uPane.add(new JButton("?"));
		uPane.setPreferredSize(new Dimension(160,160));
		uPane.setMaximumSize(new Dimension(160,160));
		this.add(uPane);
		
		this.add(Box.createVerticalGlue());
		
		JPanel iPane=new JPanel();
		//iPane.setBackground(new Color(0x0000FF));
		iPane.setOpaque(false);
		iPane.setLayout(new GridLayout(0,1));
		iPane.setPreferredSize(new Dimension(160,80));
		iPane.setMinimumSize(new Dimension(160,80));
		iPane.setMaximumSize(new Dimension(160,80));
		goldLabel=new JLabel("Gold:");
		iPane.add(goldLabel);
		livesLabel=new JLabel("Lives:");
		iPane.add(livesLabel);
		waveLabel=new JLabel("Wave:");
		iPane.add(waveLabel);
		this.add(iPane);
	}
	
	/**
	 * hacky method to bind a listener to all buttons in the sidebar
	 * @param l the listener to use
	 */
	void addActionListener(ActionListener l) {
		synchronized(this.getTreeLock()) {
			for(Component c: this.getComponents())
			{
				//TODO: make better
				if(c instanceof JButton) {
					((JButton)c).addActionListener(l);
				}
				else if(c instanceof JPanel) {
					synchronized(c.getTreeLock()) {
						for(Component d: ((JPanel)c).getComponents()) {
							if(d instanceof JButton) {
								((JButton)d).addActionListener(l);
							}
						}
					}
				}
			}
		}
	}
	
	void updateState(Game game){
		livesLabel.setText("Lives: "+game.getLives());
	}
}
