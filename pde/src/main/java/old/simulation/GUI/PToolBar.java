package old.simulation.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * ������������ ������ ToolBar	
 * @author Oleg
 *
 */
public class PToolBar extends JPanel
{
	public PToolBar() 
	{
		JToolBar bar = new JToolBar("BAR");				// ������ ������ ToolBar
		bar.add(new JButton(""));
	       
	    JToolBar bar2 = new JToolBar("BAR2"); 			// ������ ������ ToolBar   
	    bar2.add(new JButton());
	       
	    setLayout(new GridBagLayout());					
	    
	    add(bar,  new PanelFragment().new GBL(0,0,1,1).setFill(GridBagConstraints.HORIZONTAL).setAnchor(GridBagConstraints.NORTH).setWeightXY(10.0,1.0));
	    add(bar2, new PanelFragment().new GBL(1,0,1,1).setFill(GridBagConstraints.HORIZONTAL).setAnchor(GridBagConstraints.NORTH).setWeightXY(1.0,1.0));
	}
	
}
