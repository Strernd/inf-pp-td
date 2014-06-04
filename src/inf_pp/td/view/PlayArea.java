package inf_pp.td.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JPanel;

public class PlayArea extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6607180777510972878L;

	public PlayArea() {
		inf_pp.td.model.PlayArea pa=new inf_pp.td.model.PlayArea();
		int w=pa.getWidth();
		int h=pa.getHeight();
		
		this.setLayout(new GridLayout(h,w));
		for(int y=0;y<h;++y){
			for(int x=0;x<h;++x){
				JPanel wpPan = new JPanel();
				boolean found=false;
				for(Point wp : pa.getWaypoints())
				{
					if(wp.x==x&&wp.y==y)
					{
						found=true;
						break;
					}
				}
				if(found)
					wpPan.setBackground(new Color(0xaa8877));
				this.add(wpPan);
			}
		}
	}
}
