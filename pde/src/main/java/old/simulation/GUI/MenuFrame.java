package old.simulation.GUI;


import old.database.DataSet;
import old.entityProduction.Trest;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;


public class MenuFrame extends JFrame
{  


	public PSplitPane getPanelSplitPane()				{	return panelSplitPane;	}
	public void setPanelSplitPane(PSplitPane panelSplitPane)	{	
											this.panelSplitPane = panelSplitPane;	}
	public DataSet getDataSet() 						{	return dataSet;			}
	public void setDataSet(DataSet dataSet)				{	this.dataSet = dataSet;	}
	public Trest getTrest() 							{	return trest;			}
	public void setTrest(Trest trest)					{	this.trest = trest;		}

/**
 * Параметры внешего Frame
 */
	public final int DEFAULT_WIDTH =  Toolkit.getDefaultToolkit().getScreenSize().width;
	public final int DEFAULT_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height; 
	
	public static final int Separator	 = 1;
	public static final int NoSeparator  = 0;
	public static final int heightIcon  =  16;	
 
	private DataSet dataSet;
    private Trest trest;
    
	public DefaultTreeModel getModelTree()				{	return modelTree;		}
	public void setModelTree(DefaultTreeModel modelTree){	this.modelTree = modelTree;	}
	public JTree getTree()								{	return tree;			}
	public void setTree(JTree tree)						{	this.tree = tree;		}	            

	private DefaultTreeModel modelTree; 
	private JTree tree ;
	
	private PSplitPane panelSplitPane;
	private PBuilderRoutePane builderRoutePane;
	
    public MenuFrame() 
    {
    	setTitle("Имитационное моделирование производственных систем");	//	инициализируем название фрейма
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);							//	инициализируем размеры на экран
        setIconImage(new ImageIcon("Image/Icon/work3.png").getImage());	//	инициализируем иконку для фрейма   
        setJMenuBar(new PMenuBar (this));								//	инициализуем menuBar по умолчанию

        dataSet = new DataSet();					// Инициализируем поля DataSet
        dataSet.openDataSet();						// Считываем данные из XML-файлов в поля DataSet. 

        trest= dataSet.getTrest(1);					// Инициализируем объект Трест по id=1
 
        panelSplitPane = new PSplitPane(this);		//	Формируем панель panelSplitPane с деревом
        getContentPane().add(new PToolBar(), BorderLayout.NORTH);							//	устанавливаем панель JMenuBar с установкой BorderLayout.NORTH
        getContentPane().add( panelSplitPane.getPSplitPaneTree (), BorderLayout.CENTER);		//	устанавливаем панель getPSplitPaneTree  в виде стартовой
    }  
 
}
