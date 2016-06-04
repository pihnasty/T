package old.simulation.GUI.PBuilderRoutePanePack;

import javax.swing.*;
import java.awt.*;


/**
 * ��������� Button � ����������
 */
public class PButton <cL> extends JButton  {  
	public PButton (String imgName, int height, int width, String toolTipText) {
		super (new ImageIcon(imgName));	
		setPreferredSize(new Dimension(width, height));
		String  toolTipTextBegin = "<html>  <div style='background-color:yellow; width: 150px; '>  <font  color='black'> ";
		String  toolTipTextEnd =   "</font></div>    </html>";	
		setToolTipText(toolTipTextBegin+toolTipText+toolTipTextEnd);					
	}		
}