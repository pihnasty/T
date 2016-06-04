package old.simulation.GUI;
/**
@version 1.21 2015-04-10
@author Pihnastyi Oleg
*/

import javax.swing.*;

public class MenuMain
{
    public static void start()
    {
    	JFrame.setDefaultLookAndFeelDecorated(true);
        MenuFrame frame = new MenuFrame();
  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
