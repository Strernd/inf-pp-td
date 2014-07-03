package inf_pp.td.view;

import inf_pp.td.Tiles;
import inf_pp.td.intercom.TdState;
import inf_pp.td.model.Game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
		JButton b=new JButton();
		b.setActionCommand("build_dd");
		b.setToolTipText("Direct Damage Tower: Does single target damage");
		b.setIcon(new ImageIcon(Tiles.get(Tiles.TileId.TOWER_DD)));
		twrPane.add(b);
		b=new JButton("");
		b.setActionCommand("build_ae");
		b.setToolTipText("Area of Effect: Deals damage to all creeps in range");
		b.setIcon(new ImageIcon(Tiles.get(Tiles.TileId.TOWER_AE)));
		twrPane.add(b);
		b=new JButton("");
		b.setActionCommand("build_sl");
		b.setToolTipText("Slow Tower: Slows creeps");
		b.setIcon(new ImageIcon(Tiles.get(Tiles.TileId.TOWER_SL)));
		twrPane.add(b);
		b=new JButton("");
		b.setActionCommand("build_p");
		b.setToolTipText("Poison Tower: Afflicts poision to creeps and damages them over time");
		b.setIcon(new ImageIcon(Tiles.get(Tiles.TileId.TOWER_P)));
		twrPane.add(b);
		
		twrPane.setPreferredSize(new Dimension(160,160));
		twrPane.setMaximumSize(new Dimension(160,160));
		this.add(twrPane);
		
		this.add(Box.createVerticalGlue());
		
		JPanel uPane=new JPanel();
		uPane.setLayout(new GridLayout(2,2,4,4));
		b=new JButton("Damage");
		b.setActionCommand("upgrade_damage");
		uPane.add(b);
		b=new JButton("Range");
		b.setActionCommand("upgrade_range");
		uPane.add(b);
		b=new JButton("Fire Rate");
		b.setActionCommand("upgrade_firerate");
		uPane.add(b);
		b=new JButton("Sell");
		b.setActionCommand("sell_tower");
		uPane.add(b);
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
	
	void updateState(final TdState state){
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				livesLabel.setText("Lives: "+state.getGame().getLives());
				goldLabel.setText("Gold: "+state.getGame().getGold());
				waveLabel.setText("Wave: "+(state.getGame().getCurrentWaveIndex()+1)+"/"+state.getGame().getWaveCount()
						+" "+state.getGame().getCurrentWaveName());
			}
		});
	}
}