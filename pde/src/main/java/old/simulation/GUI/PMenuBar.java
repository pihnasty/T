package old.simulation.GUI;

import old.database.DataSet;
import old.database.XmlRW;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class PMenuBar extends JMenuBar 
{
	private static final int Separator	 = 1;
	private static final int NoSeparator  = 0;
	private static final int heightIcon  =  16;	
	
	private MenuFrame fm;
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu settingMenu;
	private JMenu platformsMenu;
	private JMenu windowsMenu; 
		private JMenu OpenPerspectivesMenu;
	private JMenu helpMenu; 		
		
    /**
     * ����������� ��������� ���� 
     * 
     * @param menu		��� menu ��������
     * @param name		��� ������� ��� ��������
     * @param keyE		����������� ��� �������
     * @param inputE	����� ������ �������������
     * @param separator �������� "Separator"/"NoSeparator". �������� �������������� � ����������� ������ �� "Separator". 
     */
	
	/**
	 * 
	 * @param fm �������� ���� ��� ���� _fm
	 * @return �������������� ���� Default ��� ��������� ���� 
	 */
	
	/**
	 * �������������� ��������� ������ ���� � ������ ���� �� ���������
	 * @param fm	������� ���� ����������
	 */
	public PMenuBar(MenuFrame fm) 
	{	
		this.fm=fm;	

		fileMenu = new JMenu("File");
 	    makeMenu(fileMenu,"New", 		KeyEvent.VK_N,InputEvent.CTRL_MASK, NoSeparator,"Image/Icon/new.png");
 	    makeMenu(fileMenu,"Open",		KeyEvent.VK_O,InputEvent.CTRL_MASK, Separator,  "Image/Icon/open.png");
 	    makeMenu(fileMenu,"Save",		KeyEvent.VK_S,InputEvent.CTRL_MASK, NoSeparator,"Image/Icon/save.png");
 	    makeMenu(fileMenu,"Save As",	KeyEvent.VK_A,InputEvent.CTRL_MASK, NoSeparator,"Image/Icon/save_as.png");   
 	    makeMenu(fileMenu,"Save All",	KeyEvent.VK_L,InputEvent.CTRL_MASK, Separator,  "Image/Icon/save_all.png");        	     
 	    makeMenu(fileMenu,"Exit",		KeyEvent.VK_E,InputEvent.CTRL_MASK, NoSeparator,"Image/Icon/exit.png"); 
 	    
 	    editMenu = new JMenu("Edit");
 	    makeMenu(editMenu,"Cut", 		KeyEvent.VK_T,InputEvent.CTRL_MASK, NoSeparator,"Image/Icon/cut.png");
 	    makeMenu(editMenu,"Copy",		KeyEvent.VK_C,InputEvent.CTRL_MASK, NoSeparator,"Image/Icon/copy.png");
 	    makeMenu(editMenu,"Paste",		KeyEvent.VK_V,InputEvent.CTRL_MASK, NoSeparator,"Image/Icon/paste.png");
 	    
 	    settingMenu = new JMenu("Setting");	    
 	     /*	�������� ������������� ���� ��� �������� ����������	*/
 	     	platformsMenu = new JMenu("Platforms");
 	        UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
 	     	for (UIManager.LookAndFeelInfo info: infos) makeMenu(platformsMenu, info.getName(), info.getClassName());   
 	 	settingMenu.add(platformsMenu);
 	 		
 	    windowsMenu = new JMenu("Windows");
    		OpenPerspectivesMenu = new JMenu("Open Perspectives"); 
    		makeMenu(OpenPerspectivesMenu,"Default Perspective",KeyEvent.VK_D,InputEvent.CTRL_MASK, NoSeparator,"Image/Icon/default.png");
    	    makeMenu(OpenPerspectivesMenu,"Orders Perspective", KeyEvent.VK_W,InputEvent.CTRL_MASK, NoSeparator,"Image/Icon/work2.png");  
    	    makeMenu(OpenPerspectivesMenu,"Route Perspective",	KeyEvent.VK_R,InputEvent.CTRL_MASK, NoSeparator,"Image/Icon/work2.png");  
    	    makeMenu(OpenPerspectivesMenu,"Builder route", 		KeyEvent.VK_R,InputEvent.CTRL_MASK, NoSeparator,"Image/Icon/work2.png");      	    
    	    
    	    
    	    
 	    windowsMenu.add(OpenPerspectivesMenu); 	    
        helpMenu = new JMenu("Help");

 	    getMenuBarDefault();								//	������ MenuBar	�� ��������� 
	}
	
	public  JMenuBar getMenuBarDefault()
	{
		removeAll();
		add(fileMenu);    add(editMenu);     add(settingMenu);     add(windowsMenu);     add(helpMenu);
		return this;
	}
	/**
	 * 
	 * @param _fm �������� ���� ��� ���� _fm
	 * @return �������������� ���� Work ��� ��������� ���� 
	 */	
	public JMenuBar getPerspectiveWork()	
	{   
		removeAll();
		add(fileMenu);      add(editMenu);     add(windowsMenu);
     	return this;
	}
	
	public JMenuBar getMenuBarOrders()	
	{   
		removeAll();
		add(fileMenu);      add(editMenu);     add(windowsMenu);
     	return this;
	}	

     private void makeMenu(JMenu menu,String name, int keyE, int inputE, int separator, String linkIcon ) 
    {
	
    	imageScaled (heightIcon, heightIcon, linkIcon, heightIcon);						// �������� ����������� ������ � ���������
    	Action a = new TestAction(name); 									// ������� ���������� ������� action      
    	a.putValue(Action.SMALL_ICON, new ImageIcon(linkIcon)); 			// ���������� ����-�������� ��� ������

    	JMenuItem Item = menu.add(a);										// ������������� ��������� ���� ����� Action
    	
    	KeyStroke keyS = KeyStroke.getKeyStroke(keyE, inputE);		        // ������������� �������� ��������. ������: (����������� ��� �������) + (����� ������ �������������)
   		Item.setAccelerator(keyS);											// ?????????

   		if ( separator==1) menu.addSeparator();
    }
   
    public  void makeMenu (JMenu menu, String name, final String plafName)
    {  
 	   Action a = new TestAction(name); 									// ������� ���������� ������� action    
   	   a.putValue(Action.NAME, name);  	   
   	   a.putValue(Action.DEFAULT, "7"); 			
   	   a.putValue(TestAction.PLAFNAME, plafName); 
       JMenuItem  itm = menu.add(a);	
    }
    
    
    static public void imageScaled (int x, int y, String linkIcon, int heightMax)
    {
    	BufferedImage scaled = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
    	
        Graphics2D g = scaled.createGraphics();
        try
        {
            Image im = ImageIO.read(new File (linkIcon));			//	�������������� �� ����� �������    
	        g.drawImage(im, 0, 0, x,y, null);						//	���������� ���
	        g.dispose();
	        if(heightMax<im.getHeight(null) ) ImageIO.write(scaled, "gif", new File(linkIcon));		//	���������� ����������� ������� ������� � ����     	        	
        } 
        catch (IOException exp)    {  	exp.printStackTrace();   }
    }
  
     class TestAction extends AbstractAction
    {   
    	public static final String PLAFNAME = "PlafName";
       	public TestAction(String name) { super(name); }
/**
 * 
 */
       	/**
       	 * ���������� ���������� � *.xml ����. �������� � ��������� ������ ��� �����������
       	 * 1.��������� �������� ����������
       	 * 2.��������� ������������� ��� 
       	 */
       	public void saveData()
       	{		
       		try {	((MenuFrame)fm).getDataSet().saveDataset();	} catch (Throwable exp) { exp.printStackTrace();} 	// 	��������� ��� ���� DataSet �� ����										 
       	}
       	
       	/**
       	 * ���������� ����� �������� ���� � �� � congig.xml ���� � ����������� ��������� � DataSet �������� �� ��. 
       	 * @param XmlRW_pathDataWork	���� � �������� ��� ����������/������ ��
       	 * @param boolOpenDataSet		����. ��� boolOpenDataSet=true ���������� �������� ��, ������� ��������� �� ���������� ����. 
       	 */
       	public void savePathConfig(String XmlRW_pathDataWork, boolean boolOpenDataSet)
       	{
       		if (XmlRW_pathDataWork != "")						// ������� �� ������� ���� "" ��� ������� �� ����� ����������� ����
       		{
           		((MenuFrame)fm).getDataSet().setPathDataDefault(XmlRW_pathDataWork);	    	// ������������� ����� ��������, ������������� �� ���������, �������� ��������, ��� ��������� ����� � ��.  
           																						// ��� �������� ������������� ���������� �������� � ����� �����, ������ ������� ���������� � ������ 
           																						// ��� �������� ���� � ��������, � ���� ��������� �������������� ����� +"\\" ����� ������ �����
           		String pathSet =  ((MenuFrame)fm).getDataSet().getPathDataDefault();			// ���������� ����� �������� ���� � �� �� ��������� � ���������� ��� � ���������� (pathSet)
                ((MenuFrame)fm).getDataSet().tSettings.get(0).setSystemPath(pathSet);			// �������� ����� �������� ��� ���� � ��, ������� �������� � ���� ��������  ����� ������� tabSettings

                                // �1������ �������� �������� ���� �� ��������� � �� � ������� DataSet. ��� ����������, ����� �� ������� ����� ����� writeTab(DataSet.tSettings)
                                ((MenuFrame)fm).getDataSet().setPathDataDefault(DataSet.getPathConfig());		// ��� ����, ����� �������� ��������� � ���� ��������, ������������ ������� writeTab(DataSet.tabSettings)
                                    																			// ������� ����� �� �������, ������� ����� ��������� ���� PathDataDefault. �� ��� ��� ��� ���� �������� 
                                                                                                    			// � � ������� � �����, ���������� � PathConfig, �� ��������������� PathDataDefault=PathConfig.
                                                                                                    			// ����� �������� DataSet ���� ���� ����� ������� �� ������ � ����������������� �����               
                                ((MenuFrame)fm).getDataSet().writeTab(DataSet.tSettings);		    			// ���������� ����, ���������� ������ � ����������� (������� ���� ������� tabSettings) � ���������������� ����
                                //  �1End     ���� ������ � ���������������� ���� ��������������� �������� ��������  ���� � ��, ������� ���� �� ���������
                
                ((MenuFrame)fm).getDataSet().setPathDataDefault(pathSet);						// ������������� ����� ��������, ������������� �� ���������, �������� ��������, ��� ��������� ����� � ��.       

                if (boolOpenDataSet)	
                {
                    ((MenuFrame)fm).getDataSet().openDataSet();										// ��������� ���� dataSet � ((MenuFrame)fm).dataSet ������ ����������, � ������ ��������� ������������
                    ((MenuFrame)fm).setTrest(      ((MenuFrame)fm).getDataSet().getTrest(1)    );	// �������������� ������ ����� �� id
                }
       		}
       	}
       	
       	      	
       	
       	public void actionPerformed(ActionEvent event)
        {
          System.out.println(getValue(Action.NAME)+ " selected.");
          if (getValue(Action.NAME)=="Exit")
          {
        	    saveData(); 							                	               
                // for ( Work w :  ((MenuFrame)fm).getTrest().getWorks() )              	  w.show();
                System.exit(0);  
          }
          //***"Open"*********************************************************************************************************************************************       
          if (getValue(Action.NAME)=="Open")				// �������� ��� ������� ������ ���� "Open" (������� ����� ����)
          {
        	  String XmlRW_pathDataWork= XmlRW.pathDataWork();									// �������� ���� �� ��������� (���� � �������� ��������)
        	  saveData();   																	// ���������� ��������� ������ � �����   *.xml						                           
              savePathConfig(XmlRW_pathDataWork, true);											// ���������� ��������� ����������������� ����� (���������� ����� �������� ���� � ��)
          }         
          //***"End_Open"*****************************************************************************************************************************************           
          //***"Save"*********************************************************************************************************************************************        	
          if (getValue(Action.NAME)=="Save")				// �������� ��� ������� ������ ���� "Save" (�������� ����)
          {
        	saveData();																			// ���������� ������ �� ���� � ������� ����������						 							                	               
           //  for ( Work w :  ((MenuFrame)fm).getTrest().getWorks() )              	  w.show();	// ����� ������ �� ����� ��� ��������� ���������� ������
          }           
          //***"End_Save"*****************************************************************************************************************************************
          //***"SaveAs"*******************************************************************************************************************************************        	
          if (getValue(Action.NAME)=="Save As")				// �������� ��� ������� ������ ���� "Save As" (�������� ����)
          { 
        	  String XmlRW_pathDataWork= XmlRW.pathDataWork("");									// ������� ����� ���� �� ��������� ��� ���������� ������ �� ���� � ������� ����������
              savePathConfig(XmlRW_pathDataWork, false);			// ���������� ��������� ����������������� ����� (���������� ����� �������� ���� � ��)  
        	  saveData();																		// ���������� ������ �� ���� � ������� ����������
          }           
          //***"End_SaveAs"***************************************************************************************************************************************          

          if (getValue(Action.NAME)=="Default Perspective")  {
        	  fm.setJMenuBar( getMenuBarDefault()); 
              fm.setPanelSplitPane ( fm.getPanelSplitPane().getPSplitPaneTree () );
        	  fm.setVisible(true);
        	  }
          if (getValue(Action.NAME)=="Orders Perspective")   {
        	  fm.setJMenuBar( getMenuBarOrders() ); 
        	  PTablePanel.iniStaticId (0);		//	������������� ��������� ���������� ������ � ��������� ���������
              fm.setPanelSplitPane ( fm.getPanelSplitPane().getPSplitPaneOrderTableM (1, PSplitPane.getCLs(1)) );
        	  fm.setVisible(true);
        	  }    
          if (getValue(Action.NAME)=="Route Perspective") 	 {
        	  fm.setJMenuBar( getMenuBarOrders() ); 
        	  PTablePanel.iniStaticId (0);		//	������������� ��������� ���������� ������ � ��������� ���������
              fm.setPanelSplitPane ( fm.getPanelSplitPane().getPSplitPaneOrderTableM ( fm.getTrest(). getMinId(PSplitPane.getCLs(2)[0],  fm.getTrest().getWorks()),  PSplitPane.getCLs(2) ) );				 
        	  fm.setVisible(true);
    	  }        
          if (getValue(Action.NAME)=="Builder route") 	 {
        	  fm.setJMenuBar( getMenuBarOrders() ); 
        	  PTablePanel.iniStaticId (0);		//	������������� ��������� ���������� ������ � ��������� ���������
              fm.setPanelSplitPane ( fm.getPanelSplitPane().getPBuilderRoutePane () );				 
        	  fm.setVisible(true);
    	  }           
          if ((String)getValue(Action.DEFAULT) == "7")       {
              try  				 { UIManager.setLookAndFeel((String)getValue(PLAFNAME));}
              catch(Exception e) { e.printStackTrace(); }
              SwingUtilities.updateComponentTreeUI (fm);
              }       	   
        }
    }
}
