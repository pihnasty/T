package old.simulation.GUI;

import old.database.DataSet;
import old.database.XmlRW;
import old.database.tabDataSet.*;
import old.entityProduction.Line;
import old.entityProduction.Order;
import old.entityProduction.Work;
import old.simulation.GUI.PSplitPane.ForMakePane;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

public class PTablePanel<cL> extends JPanel {
	
	
	
	private JComboBox moonCombo;
	private static RowIdNameDescription rowIND;
	private Label labelWork;
	private MenuFrame fm;
	private Class cL;
	private static Class [] cLs;
	private int result = 0;
	private PTable table;
	private DataSet ds;
	private  ArrayList<cL> tab;
	private  PModelTable modelTable;
	private  ListSelectionModel selModel;
	private static int id1  = 0;
	private static int id2 	=-1;
	private static int id3	= 0;
	private int selIndex = 0;
	private int row;	
	private PSplitPane outerPane_12;	
	private static Object rootElement;
	private static Object w ; //Work  Order 
	private static Object  ord;	
	private int[] selectedRows;
	/**
	 * 
	 * @param id	Id для корневого элемента, который выбран пользователем при наличие comboBox  	
	 */
	public static void iniStaticId (int id) {	id1  = id; id2 	=-1; id3	= 0;	} 

	
	public  PTablePanel(Class _cL, MenuFrame _fm, Class ... _cLs)	{
		this.cL=_cL;			
		this.fm=_fm;
		this.ds= fm.getDataSet();
		this.cLs = _cLs;
		makeButton ();
        System.out.printf("PTablePanel 10: id1= %d  id2= %d  id3= %d   \n", id1,id2, id3);	    
	}	
 	
	public  PTablePanel(ArrayList<cL> _tab,  Class _cL, MenuFrame _fm, int _selIndex , Class ... _cLs)	{	this(_tab, _cL, _fm, _cLs); 	if (_tab.isEmpty() != true) { this.selIndex=_selIndex; table.setRowSelectionInterval(selIndex ,selIndex);} }
	
	public  PTablePanel(ArrayList<cL> _tab,  Class _cL, MenuFrame _fm, Class ... _cLs) { // fm.getDataSet() DataSet _ds	
        System.out.printf("PTablePanel 106: id1= %d  id2= %d  id3= %d   \n", id1,id2, id3);	    
		this.fm=_fm;
		this.ds= fm.getDataSet();	this.tab= _tab;    this.cL=_cL;	this.cLs = _cLs;
		rootElement = PSplitPane.rootFindElement( id1, fm, cLs);

		Border borderClass 	=  BorderFactory.createTitledBorder(cL.getSimpleName());				// определяем заголовок рамки панели
		setBorder(borderClass);																		// задаем тип рамки для окаемки панели 
		modelTable = new PModelTable (tab,cL);							// Формируем модель таблицы  
		table = new PTable( modelTable,ds);								// Формируем таблицы
		table.setRowSelectionInterval(0 ,0 );								//	Задаем номер выделенной строки в таблице
		selModel = table.getSelectionModel();
        table.setRowSelectionAllowed(true);
        selModel.addListSelectionListener(new ListSelectionListener(){ 	
    										public void valueChanged(ListSelectionEvent e){  
											    selectedRows = table.getSelectedRows();
											    for(int i = 0; i < selectedRows.length; i++) {
                                                   selIndex = selectedRows[0];
                                                   result =   (int) modelTable.getValueAt(selIndex, 0);							// получаем значение для id для нажатой строки      
                                                   table.setRowSelectionInterval(selIndex,selIndex); 

                                                   if (cL==cLs[1]){
                                                       System.out.printf("PTablePanel 121:  cLs[1] %s id1= %d  id2= %d  id3= %d   \n",cLs[1], id1,id2, id3);
                                                   	   setId2(result);                                                	  
                                                	   setId3( ((RowIdNameDescription)w).getMinId (cLs[2], w_getOrders() ) );
    									     	       fm.getPanelSplitPane().setOuterPane_12 ( fm. getPanelSplitPane().getPSplitPane12(id1,id2, id3,-1, cLs)  );  
                                                   }
                                                   if (cL==cLs[2]) {
                                               	       if(id2==-1) setId2 ( ((RowIdNameDescription) rootElement).   getMinId(cLs[1], root_getWorks()) );	// указываем минимальный по значению id таблицы getWorks(), для которого будет выведена таблица getOrders()
                                               	       setId3(  result);                                                	     			                                           	   
      									     	       fm.getPanelSplitPane().setOuterPane_12 ( fm. getPanelSplitPane().getPSplitPane12(id1,id2, id3,-1, cLs)  );  
                                                   }
											    }	
											    MakePanelSplitPane();	//	Перерисовывает компоненты в панели после изменения данных
											}               
										});
		JScrollPane sp = new  JScrollPane(table);
		setLayout(new BorderLayout());
		add(sp, BorderLayout.NORTH);	
		
		makeButton ();

	    
	}
	class DeleteXXX_rowXXX implements ActionListener {		// Удаляем выделенную строку
		public void actionPerformed(ActionEvent e)   {
			if(tab!=null){
				if(id2==-1)	{
					setId2 ( ((RowIdNameDescription) rootElement).   getMinId(cLs[1], root_getWorks()) );
					setId3 ( ((RowIdNameDescription)w).getMinId (cLs[2], w_getOrders() ) );	    				// указываем минимальный по значению id таблицы getWorks(), для которого будет выведена таблица getOrders()
				}
				if (tab.size()>1) delRowCL ( cL);
				if (tab.size()>0) {
					modelTable = new PModelTable (tab,cL);																		// Формируем модель таблицы 
        			table = new PTable( modelTable,ds);																			// Формируем таблицу	
        		}			
    			if (cL==cLs[1])	{
    				if(selIndex>0)	{
    					fm.getPanelSplitPane().setScrollPane_2   ( new PTablePanel(tab,	cL,	fm, selIndex-1, cLs )  );   //	, selIndex-1		tab=fm.getTrest().getWorks() 
    					setId2 ( (int) modelTable.getValueAt(selIndex-1, 0) );		
    				}
    				else	{
    					fm.getPanelSplitPane().setScrollPane_2   ( new PTablePanel(tab,	cL,	fm, 0, cLs)  ); 			// tab=fm.getTrest().getWorks() 
    					setId2 (   (int) modelTable.getValueAt(0, 0));
    				}				
    				setId3( ((RowIdNameDescription)w).getMinId (cLs[2], w_getOrders() ));
    				System.out.println("PTAblePanel id2="+id2+"       PTAblePanel id3="+id3+"    select="+selIndex);
    				fm.getPanelSplitPane().setOuterPane_12   ( fm. getPanelSplitPane().getPSplitPane12(id1, id2, id3,0, cLs)  );  
    			}
    			if (cL==cLs[2])	{
    				if(selIndex>0)	{
    					fm.getPanelSplitPane().setScrollPane_121 ( new PTablePanel (w_getOrders(),				cL,	fm, selIndex-1, cLs)  );  
    					setId3(   (int) modelTable.getValueAt(selIndex-1, 0));	
    				}
    				else	{						
    					fm.getPanelSplitPane().setScrollPane_121 ( new PTablePanel (w_getOrders(),				cL,	fm, 0, cLs)  );  
    					setId3(   (int) modelTable.getValueAt(0, 0) );
    				}	
    		    fm.getPanelSplitPane().setOuterPane_12   ( fm. getPanelSplitPane().getPSplitPane12(id1, id2, id3,0, cLs)  );  
    			}				
    			if (cL==cLs[3])	{
        			setId3(id3);	 
    				if(selIndex>0)	selIndex=selIndex-1;																	
    				fm.getPanelSplitPane().setScrollPane_122 ( new PTablePanel (ord_getLines(),			cL,	fm, selIndex, cLs) );   
    				fm.getPanelSplitPane().setOuterPane_12   ( fm. getPanelSplitPane().getPSplitPane12(id1, id2, id3,  selIndex, cLs)  );  
    			}		
			MakePanelSplitPane();      	   
		 }					
		} 
		
				/**
				 * 
				 * @param tab			коллекция объектов, один из которых подлежат удалению
				 * @param Rowtab		таблица (коллекция) строк в DataSet с уникальным id2, в которых хранятся данные для формирования объектов
				 * @param RowIdIdwtab   таблица (коллекция) реестров строк в DataSet с уникальным id и id2, в которых хранятся данные для формирования объектов
				 * @param id			id родителя
				 * @param id2			id2 дочернего элнмента
				 */
			public  <cL, RowcL, RowIdIdcL> void delRow (ArrayList <cL> tab, ArrayList <RowcL> Rowtab, ArrayList <RowIdIdcL> RowIdIdtab, int id, int id2){
				for ( cL e   :  tab ) 				if ( id2 == ((RowIdNameDescription) e).getId() )   {	tab.remove(e);			break;	}		// удаляем объект
				for ( RowcL r : Rowtab ) 			if ( id2 == ((RowIdNameDescription) r).getId() )   {	Rowtab.remove(r);		break;	}		// удаляем строку данных об объекте
				for ( RowIdIdcL wr : RowIdIdtab )	if ( id2 == ((RowIdId2) wr).getId2() && id == ((RowIdId2) wr).getId() )  {	Rowtab.remove(wr);		break;	}		// удаляем строку  связи объекта с родителем																								   {	RowIdIdtab.remove(wr);	break;	}		// удаляем строку реестра с данными об связи объектах
				}
			/**
			 * Таблица tab заполнена строками, производствами _cL=(Work.class). Выделена строка с конкретным производством, например Work№15. Удаляется эта строка из Trest.Works и данные из DataSet/
			 * @param _cL	Тип данных элементов коллекции, элемент которой мы выделили. 
			 */
			public void delRowCL ( Class _cL) {
				if (Arrays.asList(PSplitPane.getCLs(1)).equals(Arrays.asList(cLs)))	{	
    		       	if (_cL== Work.class)	delRow (tab, fm.getDataSet().getTabWorks(), fm.getDataSet().getTabTrestsWorks(), id1, id2);	// Рассмотрим операцию на примере елемента Work. Уничтожаем елемент Work в Trest.getWorks(), а также данные об элементе Work в  DataSet.getTabWorks() и его связи с родителем в DataSet.getTabTrestsWorks().
    		       	if (_cL== Order.class)	delRow (tab, fm.getDataSet().getTabOrders(),fm.getDataSet().getTabWorksOrders(), id2, id3);
    		       	if (_cL== Line.class)	delRow (tab, fm.getDataSet().getTabLines(), fm.getDataSet().getTabOrdersLines(), id3, (int) modelTable.getValueAt(selIndex, 0)	);
				}
				if (Arrays.asList(PSplitPane.getCLs(2)).equals(Arrays.asList(cLs)))	{	
    		       	if (_cL==PSplitPane.getCLs(2)[1])		delRow (tab, fm.getDataSet().getTabSubject_labours(), 	fm.getDataSet().getTabWorksSubject_labours(),   id1, id2);	// Рассмотрим операцию на примере елемента Work. Уничтожаем елемент Work в Trest.getWorks(), а также данные об элементе Work в  DataSet.getTabWorks() и его связи с родителем в DataSet.getTabTrestsWorks().
    		       	if (_cL==PSplitPane.getCLs(2)[2])		delRow (tab, fm.getDataSet().getTabRoutes(),			fm.getDataSet().getTabSubject_laboursRoutes(),  id2, id3);
    		       	if (_cL==PSplitPane.getCLs(2)[3])		delRow (tab, fm.getDataSet().getTabLineroutes(), 		fm.getDataSet().getTabRoutesLineroutes(), 		id3, (int) modelTable.getValueAt(selIndex, 0)	);
				}				
		    }
	}										 

	class CreateXXX_rowXXX implements ActionListener {		// Создаем новую строку в таблице
		CreateXXX_rowXXX( Class _cL, MenuFrame _fm, PModelTable _modelTable)		{	this.cL=_cL; this.fm=_fm; this.modelTable=_modelTable;	}
		public void actionPerformed(ActionEvent event)	{

			  
			rootElement = PSplitPane.rootFindElement( id1, fm, cLs);	// Так как работаем со статическими  rootElement, id1, id2, id3 то приходится при новом входе инициализировать 	rootElement первоначальным значением, иначе используются предыдущие, приводит к ошибке	
			
			if(root_getWorks().isEmpty() != true || cL==cLs[1]){           
			int countTAB =0;		// устанавливаем флаг того, что первая таблица пустая
           	if (root_getWorks().isEmpty() != true) {
    			countTAB =1;
    			if (id2==-1)  {
    				setId2 ( ((RowIdNameDescription) rootElement).   getMinId(cLs[1], root_getWorks()) );
    				id3 = ((RowIdNameDescription)w).getMinId (cLs[2], w_getOrders() );	    				// указываем минимальный по значению id таблицы getWorks(), для которого будет выведена таблица getOrders()
                	System.out.println("PTablePane 233   id1="+id1+"    id2="+id2+ "    id3="+id3);	 
    			}
    			if (id3==0 )  setId3 ( ((RowIdNameDescription) w). 			getMinId(cLs[2], w_getOrders()) );
           	}
    			
        	System.out.println("PTablePane 243   id1="+id1+"    id2="+id2+ "    id3="+id3);	  		
        	
        	
			newO= new Object();
			rowO= new Object();	
			try  {
				newO = cL.getConstructor(DataSet.class).newInstance(fm.getDataSet());	//	создаем новый объект типа    XXXX        (например, производство) и (читать ниже).
				System.out.printf("PTablePanel	250  newO.id=%s \n",((RowIdNameDescription)newO).getId());
    			//// Можно импользовать альтернативу newO = new Work(fm.getDataSet()); 	//	создаем новый объект Work (производство) (оставил для образца)  
    			rowO = cL.getSuperclass().getConstructor().newInstance();				//	создаем новый объект типа RowXXXX (например, строка производство) и (читать ниже)  
    			System.out.printf("PTablePanel	235  newO.id=%s  \n",((RowIdNameDescription)newO).getId());
    			XmlRW.FieldToField(rowO, newO) ;										//  копируем в RowXXXX поля изXXXX (основное - это поля iD, остальные можно было не копировать ) и (читать ниже)
            }	catch (InstantiationException exp)  {  exp.printStackTrace();  } catch (IllegalAccessException exp)  { exp.printStackTrace(); } catch (IllegalArgumentException exp) { exp.printStackTrace(); } catch (InvocationTargetException exp)  { exp.printStackTrace(); } catch (NoSuchMethodException exp) {  exp.printStackTrace(); } catch (SecurityException exp) {  exp.printStackTrace(); }
    			//-------------------------------------------формируем объект Work     и делаем по нему все изменения в DataSet ----------------------------// 


  			
			createRowCL (cL);
			
			
			if (countTAB ==0) { setId2(   ((RowIdNameDescription)newO ) .getId());  setId3(Integer.MAX_VALUE); }
			
			
			
	 
        	System.out.println("PTablePane 265   id1="+id1+"    id2="+id2+ "    id3="+id3);	
            if (id3!= Integer.MAX_VALUE)   table = new PTable( modelTable,ds);									// Формируем таблицу		
            // Обратить внимание на то,что гипотеза - при добавлении новых таблиц эти три условия должны остаться неизменными
			if (cL==cLs[1] )	fm.getPanelSplitPane().setScrollPane_2   ( new PTablePanel (root_getWorks(),cL,	fm, selIndex, cLs)  );  
			if (cL==cLs[2] )	fm.getPanelSplitPane().setScrollPane_121 ( new PTablePanel (w_getOrders(),	cL,	fm, selIndex, cLs)  );       			       	       	     	      		
			if (id3!= Integer.MAX_VALUE)  if (cL==cLs[3] ) fm.getPanelSplitPane().setScrollPane_122 ( new PTablePanel (ord_getLines(),cL,	fm, selIndex, cLs)  );   					
			System.out.println("PTablePane 251   id1="+id1+"    id2="+id2+ "    id3="+id3);	 
			if (cL==cLs[3] ) fm.getPanelSplitPane().setOuterPane_12   ( fm. getPanelSplitPane().getPSplitPane12(id1, id2, id3,selIndex,cLs)  );		             
		    	else	{
                	System.out.println("PTablePane 254   id1="+id1+"    id2="+id2+ "    id3="+id3);	  	    	
					fm.getPanelSplitPane().setOuterPane_12   ( fm. getPanelSplitPane().getPSplitPane12(id1, id2, id3,-1, 	  cLs)  );
				}	  			
			MakePanelSplitPane();
		}
		}
		/**
		 * Создание элемента в зависимости от плана : Trest -> Work -> Order -> Line
		 * @param _cL	идентификация текущего элемента, таблица со строками которого выводится
		 */
		public  <cLv>  void createRowCL ( cLv _cL) {
 
			if (Arrays.asList(PSplitPane.getCLs(1)).equals(Arrays.asList(cLs)))	{
				if (cL==cLs[1]) {
				root_getWorks().add( (cLv)newO);											//  помещаем newO в коллекцию Trest.works	            	 	
					fm.getDataSet().getTabWorks().add((RowWork) rowO);						//  помещаем rowO в коллекцию DataSet.tabWorks
					RowTrestWork rIdId = new RowTrestWork(((RowIdNameDescription) rootElement).getId(),((RowIdNameDescription) rowO).getId(),"   ");	//	Для связи по id строки RowWork таблицы tabWorks со строкой RowTrest таблицы tabTrests создаем строку реестра  RowTrestWork и (читать ниже)
					fm.getDataSet().getTabTrestsWorks().add(rIdId);	            			//	помещаем ее в таблицу DataSet.tabTrestsWorks						
					modelTable = new PModelTable (root_getWorks(),cL);						// Формируем модель таблицы  	       		
				}
				if (cL== cLs[2] ){
					w_getOrders().add( (cLv)newO);											//  помещаем newO в коллекцию Trest.work[i].Orders							
					fm.getDataSet().getTabOrders().add((RowOrder) rowO);					//  помещаем rowO в коллекцию DataSet.tabWorks
					RowWorkOrder rIdId = new RowWorkOrder(  ((RowIdNameDescription) w).getId(),((RowIdNameDescription) rowO).getId(),"   ");		//	Для связи по id строки RowWork таблицы tabWorks со строкой RowTrest таблицы tabTrests создаем строку реестра  RowTrestWork и (читать ниже)
					fm.getDataSet().getTabWorksOrders().add(rIdId);	
					modelTable = new PModelTable (w_getOrders(),cL);						// Формируем модель таблицы  
					if (id3 == Integer.MAX_VALUE) id3=((RowIdNameDescription) rowO).getId();
				}
	            if (cL==cLs[3] ) {          	
	    			setId3(id3);	            
	            	if (ord==null)	return;
	            	ord_getLines().add((cLv)newO);
					fm.getDataSet().getTabLines().add((RowLine) rowO);
					RowOrderLine rIdId = new RowOrderLine(  ((RowIdNameDescription) ord).getId(),((RowIdNameDescription) rowO).getId(),"   ");		//	Для связи по id строки RowWork таблицы tabWorks со строкой RowTrest таблицы tabTrests создаем строку реестра  RowTrestWork и (читать ниже)
					fm.getDataSet().getTabOrdersLines().add(rIdId);	
					modelTable = new PModelTable (ord_getLines(),cL);				// Формируем модель таблицы  
		         }		
			}
			/*	Формируем второй тип представления  																				*/
			if (Arrays.asList(PSplitPane.getCLs(2)).equals(Arrays.asList(cLs)))	{
				if (cL==cLs[1]) {
				    root_getWorks().add( (cLv)newO);											//  помещаем newO в коллекцию Trest.works	            	 	
					fm.getDataSet().getTabSubject_labours().add((RowSubject_labour) rowO);						//  помещаем rowO в коллекцию DataSet.tabWorks
					RowWorkSubject_labour rIdId = new RowWorkSubject_labour(((RowIdNameDescription) rootElement).getId(),((RowIdNameDescription) rowO).getId(),"   ");	//	Для связи по id строки RowWork таблицы tabWorks со строкой RowTrest таблицы tabTrests создаем строку реестра  RowTrestWork и (читать ниже)
					fm.getDataSet().getTabWorksSubject_labours().add(rIdId);	            			//	помещаем ее в таблицу DataSet.tabTrestsWorks						
					modelTable = new PModelTable (root_getWorks(),cL);						// Формируем модель таблицы  	       		
				}		
				if (cL==cLs[2]) {
					w_getOrders().add( (cLv)newO);											//  помещаем newO в коллекцию Trest.work[i].Orders	 				
					fm.getDataSet().getTabRoutes().add((RowRoute) rowO);						//  помещаем rowO в коллекцию DataSet.tabWorks
					RowSubject_labourRoute rIdId = new RowSubject_labourRoute(((RowIdNameDescription) w).getId(),((RowIdNameDescription) rowO).getId(),"   ");	//	Для связи по id строки RowWork таблицы tabWorks со строкой RowTrest таблицы tabTrests создаем строку реестра  RowTrestWork и (читать ниже)
					fm.getDataSet().getTabSubject_laboursRoutes().add(rIdId);	            			//	помещаем ее в таблицу DataSet.tabTrestsWorks					
					modelTable = new PModelTable (w_getOrders(),cL);						// Формируем модель таблицы  
					if (id3 == Integer.MAX_VALUE) id3=((RowIdNameDescription) rowO).getId();
				}	
	            if (cL==cLs[3] ) {          	
	    			setId3(id3);	            
	            	if (ord==null)	return;
	            	ord_getLines().add((cLv)newO);
					fm.getDataSet().getTabLineroutes().add((RowLineroute) rowO);
					RowRouteLineroute rIdId = new RowRouteLineroute(  ((RowIdNameDescription) ord).getId(),((RowIdNameDescription) rowO).getId(),"   ");		//	Для связи по id строки RowWork таблицы tabWorks со строкой RowTrest таблицы tabTrests создаем строку реестра  RowTrestWork и (читать ниже)
					fm.getDataSet().getTabRoutesLineroutes().add(rIdId);	
					modelTable = new PModelTable (ord_getLines(),cL);				// Формируем модель таблицы  
		         }					
			}			
		}
		private Object newO;
		private Object rowO;	
		private Class 		cL;
		private MenuFrame 	fm;
		private PModelTable modelTable;
	}
	
	
	
	class ComboListener implements ActionListener {		//	сохраняем созданные изменения в DataSet
		@Override
        public void actionPerformed (ActionEvent e)  {
			rowIND= (RowIdNameDescription) moonCombo.getSelectedItem();
			iniStaticId (rowIND.getId());
         	fm.setPanelSplitPane ( fm.getPanelSplitPane().getPSplitPaneOrderTableM (rowIND.getId(), PSplitPane.getCLs(2)) );
         	fm.getPanelSplitPane();
         	fm.setVisible(true);
        }
	}

	
	
	class SaveXXX_rowXXX   implements ActionListener {		//	сохраняем созданные изменения в DataSet
		public void actionPerformed(ActionEvent event)	{
			if (tab !=null) {
			for (Integer rowIndex :  (ArrayList<Integer>)modelTable.getChangeRowS () )		{
    			for (int columnIndex=0; columnIndex<    modelTable.getFields().length; columnIndex++ )
                    try {
                    	modelTable.getFields()[columnIndex].setAccessible(true);
                    	modelTable.getFields()[columnIndex].set(tab.get(rowIndex), 	modelTable.getjFTF()  [rowIndex][columnIndex].getValue() );  // запись строки из jFTF [rowIndex][columnIndex]   в tab.get(rowIndex)				
                    	modelTable.getFields()[columnIndex].setAccessible(false);
                    }   catch (IllegalArgumentException exp)  {	exp.printStackTrace(); }  catch (IllegalAccessException exp)  { exp.printStackTrace();} 
    			XmlRW.FieldToField_ifClass(ds, tab.get(rowIndex)) ;
    			DataSet.showTab( tab,  tab.get(rowIndex).getClass());
    		}
    		modelTable.getChangeRowS ().clear();
		}   }
	}	
	public void MakePanelSplitPane() {
		fm. getPanelSplitPane().setOuterPane_1(
   			   ForMakePane.getSplitPane(JSplitPane.VERTICAL_SPLIT, fm. getPanelSplitPane().getOuterPane_12() 	,fm. getPanelSplitPane().getScrollPaneSummary()	   , true, true)
   			   );  	
		fm. getPanelSplitPane().setLeftComponent  ( fm. getPanelSplitPane().getScrollPane_2());
		fm. getPanelSplitPane().setRightComponent ( fm. getPanelSplitPane().getOuterPane_1() );			
	}
	public ArrayList  ord_getLines()			{	return PSplitPane.getTab_for_Element( ord, cLs );				}
	public static ArrayList  w_getOrders()		{	return PSplitPane.getTab_for_Element( w, cLs );					}
	public static ArrayList  root_getWorks() 	{	return PSplitPane.getTab_for_Element( rootElement, cLs );		}
	public PTable getTable()					{	return table;													}
	public void setTable(PTable table)			{	this.table = table;												}	
	public static void setId1(int id1)			{	PTablePanel.id1 = id1;											}	
	public static void setId2(int id2)			{	
		PTablePanel.id2 = id2;	 
		w = ((RowIdNameDescription) rootElement).getById (cLs[1], root_getWorks(), id2);	// находим по id2 производство
	}
	public static void setId3(int id3)		{	
		PTablePanel.id3 = id3;	 
		ord = ((RowIdNameDescription) w).getById(cLs[2], w_getOrders(), id3);										// находим по id3 Order
	}
	
	/**
	 * Метод конфигурации кнопок в панели таблицы 
	 */
	public void makeButton () {
		JButton createChangesButton = new JButton("Добавить новую строку");
		createChangesButton.addActionListener( new CreateXXX_rowXXX(cL,fm, modelTable) );
	    		    
		JButton deleteChangesButton = new JButton("Удалить выделенную строку"); 
		deleteChangesButton.addActionListener(new DeleteXXX_rowXXX()); 
		    // добавляем кнопки и таблицу в панель содержимого 
		JPanel buttons = new JPanel(); 
		    buttons.add(createChangesButton); 
		    JButton saveChangesButton = new JButton("Сохранить изменения");    // Кнопка для сохранения изменений после перехода в другой узел дерева.
			saveChangesButton.addActionListener(new SaveXXX_rowXXX() );
		    buttons.add(saveChangesButton); 		
		    buttons.add(deleteChangesButton); 	  
		    if (cL==cLs[1] && Arrays.equals(cLs,PSplitPane.getCLs(2))) {
		    	
			    moonCombo = new JComboBox();  
			    
				if(ds!=null) for (RowWork sl	:	ds.getTabWorks())  {
					moonCombo.addItem(sl);
				}
				if (rowIND !=null ) moonCombo.setSelectedItem(rowIND); 
					else for (RowWork sl	:	ds.getTabWorks())  if (sl.getId()==((RowIdNameDescription)rootElement).getId() )	moonCombo.setSelectedItem( sl);
		
				labelWork =new Label("Производственные данные представлены для "); 
				moonCombo.setMaximumRowCount(20);
				
				
				
				Border bname;
				bname 	=  BorderFactory.createTitledBorder("Производственные данные представлены для ");	// определяем заголовок рамки панели
				Border borderName 	=  BorderFactory.createEtchedBorder();									// задаем тип рамки для окаемки название поля класса cL и значения поля  
				moonCombo.setBorder(bname);																	// задаем тип рамки для окаемки панели 
        	    
        
				 
				 
				moonCombo.addActionListener(new ComboListener() );
				add( moonCombo , BorderLayout.SOUTH);	 
				Box b = Box.createVerticalBox();
				b.add(buttons);		
				b.add(Box.createVerticalStrut(15));		
				b.add(moonCombo );	
				for (int i=0; i<100; i++) b.add(Box.createVerticalGlue()); 	
				add(b, BorderLayout.CENTER );
		    }
		    else add(buttons, BorderLayout.CENTER );
	}	
}
