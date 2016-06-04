package old.simulation.GUI;

import old.database.DataSet;
import old.database.tabDataSet.RowIdNameDescription;
import old.entityProduction.*;
import old.simulation.GUI.PBuilderRoutePanePack.ColumnData;
import old.simulation.GUI.PBuilderRoutePanePack.MVC_PanelSchema.PschemeModel;
import old.simulation.GUI.PBuilderRoutePanePack.MVC_PanelSchema.PschemeView;
import old.simulation.GUI.PBuilderRoutePanePack.MVC_PanelTypModmachine.PtmController;
import old.simulation.GUI.PBuilderRoutePanePack.MVC_PanelTypModmachine.PtmModel;
import old.simulation.GUI.PBuilderRoutePanePack.MVC_PanelTypModmachine.PtmView;
import old.simulation.GUI.PBuilderRoutePanePack.PButton;
import old.simulation.GUI.PBuilderRoutePanePack.PTableModelLineRoute;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class PBuilderRoutePane extends JSplitPane {

    /**
     * Формирует Box с внутренним содержанием сomboBox
     */
    class PBox<cL> extends Box {

        private JComboBox сomboBox;
        private int idB;

        public String getToolTipText(int id, String s) {
            String toolTipText = "";
            if (id == 1) {
                if (s == "add") {
                    toolTipText = "Создать новое производственное предприятие";
                }
                if (s == "remove") {
                    toolTipText = "Удалить производственное предприятие";
                }
                if (s == "save") {
                    toolTipText = "Сохранить изменения параметров производственного предприятия";
                }
                if (s == "edit") {
                    toolTipText = "Редактировать параметры производственного предприятие";
                }
            }
            if (id == 2) {
                if (s == "add") {
                    toolTipText = "Создать новую номенклатуру изделия";
                }
                if (s == "remove") {
                    toolTipText = "Удалить номенклатуру изделия";
                }
                if (s == "save") {
                    toolTipText = "Сохранить изменения параметров номенклатуры изделия";
                }
                if (s == "edit") {
                    toolTipText = "Редактировать параметры номенклатуры изделия";
                }
            }
            if (id == 3) {
                if (s == "add") {
                    toolTipText = "Создать новый технологический маршрут изготовления изделия";
                }
                if (s == "remove") {
                    toolTipText = "Удалить технологический маршрут изготовления изделия";
                }
                if (s == "save") {
                    toolTipText = "Сохранить изменения параметров технологического маршрута изготовления изделия";
                }
                if (s == "edit") {
                    toolTipText = "Редактировать параметры технологического маршрута изготовления изделия";
                }
            }
            return toolTipText;
        }

        public PBox(ArrayList<cL> tab, String nameBox, int idB) {
            super(BoxLayout.Y_AXIS);					//	устанавливаем вертикальное размещение элементов в PBox
            this.idB = idB;
            int height = 16;
            int width = 16;
            // задаем тип рамки для сomboBox

            JPanel panelButton = new JPanel();
            PButton buttonAdd = new PButton("Image/Icon/add.png", height, width, getToolTipText(idB, "add"));
            PButton buttonRemove = new PButton("Image/Icon/del.png", height, width, getToolTipText(idB, "remove"));
            PButton buttonSave = new PButton("Image/Icon/sav.png", height, width, getToolTipText(idB, "save"));
            PButton buttonEdit = new PButton("Image/Icon/edit.png", height, width, getToolTipText(idB, "edit"));

            panelButton.setLayout(new FlowLayout(FlowLayout.LEFT));
            panelButton.setSize(20, 20);
            panelButton.add(buttonAdd);
            panelButton.add(buttonRemove);
            panelButton.add(buttonSave);
            panelButton.add(buttonEdit);

            Border bname = BorderFactory.createTitledBorder(nameBox);	// определяем заголовок рамки панели
            setBorder(bname);

            сomboBox = new JComboBox();

            if (tab != null) {
                for (cL v : tab) {
                    сomboBox.addItem(((RowIdNameDescription) v));
                }
            }

            сomboBox.setMaximumRowCount(20);						// задаем количество элементов в раскрывающимся списке	для сomboBox	        
            Dimension preferredSize = new Dimension((Toolkit.getDefaultToolkit().getScreenSize().width) / 8, 20);	   // устанавливаем размер для сomboBox
            сomboBox.setPreferredSize(preferredSize);
            сomboBox.setMinimumSize(preferredSize);
            сomboBox.setMaximumSize(preferredSize);

            сomboBox.addActionListener(new ComboListener());

            add(panelButton);
            add(Box.createHorizontalGlue());

            JPanel panelBox = new JPanel();
            panelBox.setLayout(new FlowLayout(FlowLayout.LEFT));
            panelBox.add(сomboBox);
            add(panelBox);	//for (int i=0; i<40; i++) add(Box.createHorizontalGlue()); 			
        }

        class ComboListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                RowIdNameDescription rowSelected = (RowIdNameDescription) сomboBox.getSelectedItem();
                if (idB == 1) {
                    setIdW(rowSelected.getId());
                    setPanelLeft();
                }
                if (idB == 2) {
                    setIdSL(rowSelected.getId());
                    setPanelLeft();
                }
                if (idB == 3) {
                    setIdR(rowSelected.getId());
                    setPanelLeft();
                }
            }
        }
    }

    class PPanelTable<cLa> extends JPanel {

        private int iP;
        private int[] selectionRows;
        private PTableModelLineRoute tmLineRoute;
        private int selIndex;
        private int result = 0;
        private ListSelectionModel selModel;
        private JTable table;
        int height = 16;
        int width = 16;

        public String getToolTipText(int id, String s) {
            String toolTipText = "";
            if (id == 1) {
                if (s == "add") {
                    toolTipText = "Добавить новую технологическую операцию в технологический маршрут";
                }
                if (s == "remove") {
                    toolTipText = "Удалить технологическую операцию из технологического маршрута";
                }
                if (s == "save") {
                    toolTipText = "Сохранить изменения для технологических операций в технологическом маршруте";
                }
                if (s == "edit") {
                    toolTipText = "Редактировать параметры технологических операций в технологическом маршруте";
                }
            }
            if (id == 2) {
                if (s == "add") {
                    toolTipText = "Добавить технологический ресурс для технологической операции";
                }
                if (s == "remove") {
                    toolTipText = "Удалить технологический ресурс для технологической операции";
                }
                if (s == "save") {
                    toolTipText = "Сохранить изменения технологического ресурса для технологической операции";
                }
                if (s == "edit") {
                    toolTipText = "Редактировать параметры технологического ресурс для технологической операции";
                }
            }
            return toolTipText;
        }

        public PPanelTable(String name, ArrayList<cLa> tab, Class cL, HashMap<Integer, ColumnData> columnHeaderLineroute, int iP) {
            this.iP = iP;
            System.out.println("PBurderRoute 143 this.iB= " + this.iP);
            Border borderTable = BorderFactory.createTitledBorder(name);				// определяем заголовок рамки панели
            setBorder(borderTable);

            tmLineRoute = new PTableModelLineRoute(tab, cL, columnHeaderLineroute);
            table = new JTable(tmLineRoute);
            table.getTableHeader().setPreferredSize(new Dimension(0, 70));
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            JPanel panelButton = new JPanel();
            PButton buttonAdd = new PButton("Image/Icon/add.png", height, width, getToolTipText(iP, "add"));
            PButton buttonRemove = new PButton("Image/Icon/del.png", height, width, getToolTipText(iP, "remove"));
            PButton buttonSave = new PButton("Image/Icon/sav.png", height, width, getToolTipText(iP, "save"));
            PButton buttonEdit = new PButton("Image/Icon/edit.png", height, width, getToolTipText(iP, "edit"));
            panelButton.setLayout(new FlowLayout(FlowLayout.LEFT));
            panelButton.setSize(20, 20);
            panelButton.add(buttonAdd);
            panelButton.add(buttonRemove);
            panelButton.add(buttonSave);
            panelButton.add(buttonEdit);

            TableColumn[] tabcolumns = new TableColumn[columnHeaderLineroute.size()];
            for (int i = 0; i < tabcolumns.length; i++) {
                tabcolumns[i] = table.getColumnModel().getColumn(i);
                tabcolumns[i].setPreferredWidth(columnHeaderLineroute.get(i).getWidth());
            }
            table.setForeground(Color.red);
            table.setSelectionForeground(Color.blue);
            table.setSelectionBackground(Color.yellow);        //	    table.setShowGrid(false);  // задает/отключает рисование сетки 

            selModel = table.getSelectionModel();
            table.setRowSelectionAllowed(true);
            if (table.getRowCount() != 0) {
                table.setRowSelectionInterval(0, 0);								//	Задаем номер выделенной строки в таблице
            }
            JScrollPane sp = new JScrollPane(table);
            setLayout(new BorderLayout());

            Box box = new Box(BoxLayout.Y_AXIS);
            box.add(panelButton);
            box.add(sp);
            add(box);
            selModel.addListSelectionListener(new PTableListener());
        }

        class PTableListener implements ListSelectionListener {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectionRows = table.getSelectedRows();
                for (int i = 0; i < selectionRows.length; i++) {
                    selIndex = selectionRows[0];
                    table.setRowSelectionInterval(selIndex, selIndex);
                    System.out.println("PBuilder 192 selIndex=" + selIndex);
                    if (iP == 1) {
                        result = (int) tmLineRoute.getValueAt(selIndex, 2);							// получаем значение для id для нажатой строки      
                        setIdL(result);
                        setPanelLeft();
                        System.out.println("PBuilder 188 result=" + result);
                    }
                    if (iP == 2) {
                        result = (int) tmLineRoute.getValueAt(selIndex, 1);							// получаем значение для id для нажатой строки    
                        setIdSpec(result, false);
                        setPanelLeft();
                        System.out.println("PBuilder 192 result=" + result);
                    }

                }
            }
        }
    }

    public PBuilderRoutePane(MenuFrame fm) {
        this.panelLeft = new JPanel();
        this.panelLeft.setLayout(new BorderLayout());
        this.panelRight = new JPanel();
        this.panelRight.setLayout(new BorderLayout());

        columnHeaderLineroute = ColumnData.initColumnHeaderLineroute();
        columnHeaderLinespec = ColumnData.initColumnHeaderLinespec();
        this.fm = fm;
        this.ds = fm.getDataSet();
        setOneTouchExpandable(false);										// true - полное развертывание панели
        setContinuousLayout(true);											// true - перерисовка панели приперемещении границы панели
        setIdW(1);

        Border borderPanelLeft = BorderFactory.createTitledBorder("ФОРМИРОВАНИЕ ТЕХНОЛОГИЧЕСКОГО МАРШРУТА");	// определяем заголовок рамки панели
        panelLeft.setBorder(borderPanelLeft);

        b1 = new PBox(t.getWorks(), pboxName[1], 1);
        b2 = new PBox(w.getSubject_labours(), pboxName[2], 2);
        b3 = new PBox(sl.getRoutes(), pboxName[3], 3);

        panelTableLineroutes = new PPanelTable("Выбор тех.операции изготовления", r.getLineroutes(), Lineroute.class, columnHeaderLineroute, 1);
        panelTableResources = new PPanelTable("Выбор тех.ресурсов для операции изготовления", lr.getLinespecs(), Linespec.class, columnHeaderLinespec, 2);

        setPanelLeft();
        setLeftComponent(panelLeft);
        setRightComponent(panelRight);

        panelLeft.updateUI();
        panelRight.updateUI();
        panelRight.validate();

    }

    public JPanel getPanelLeft() {
        return panelLeft;
    }

    public void setPanelLeft() {
        panelLeft.removeAll();	// Удаляет содержимие. Если не вводить, то постоянно будет выводится содержимое старого размещения

//		System.out.printf("PBulderRoute 137  %s     %s    %s    \n",t,w,sl);
        Box b = new Box(BoxLayout.Y_AXIS);												//	помещаем bj в отдельный Box b	
        int intervalBetweenBoxes = 10;													//  задаем интервал между компоненами

        b.add(Box.createVerticalStrut(intervalBetweenBoxes));			//  помещаем распорки между элементами размером
        b.add(b1);
        b.add(Box.createVerticalStrut(intervalBetweenBoxes));
        b.add(b2);
        b.add(Box.createVerticalStrut(intervalBetweenBoxes));
        b.add(b3);
        b.add(Box.createVerticalStrut(intervalBetweenBoxes));
        b.add(Box.createVerticalStrut(intervalBetweenBoxes));

        b.add(panelTableLineroutes);
        b.add(Box.createVerticalStrut(intervalBetweenBoxes));
        b.add(panelTableResources);

        for (int i = 0; i < 100; i++) {
            b.add(Box.createVerticalGlue()); 		// добавляем пружины и прижимаем компоненты к верхней части
        }
        // 
        panelLeft.add(b);	//  Растягиваем на всю ширину. Если использовать panelLeft.add(b, BorderLayout.WEST);    , то прижмет компонент b к левой границе  левого компонента с рекомендованными размерами, установленными для содержащегося в нем 
        //  совместно с panelLeft.removeAll();	
        panelLeft.validate();   //  совместно с panelLeft.removeAll();	 производит перерисовку панели. Можно использовать panelLeft.updateUI();  panelLeft.repaint();  
        panelLeft.updateUI(); 	//	setLeftComponent(panelLeft );   //     fm.getPanelSplitPane( ).setLeftComponent  (this.getLeftComponent()  );	 

    }

    /* Задаем изображение правой панели для перспективы формирования технологических маршрутов	*/
    public void setPanelRight() {
        int intervalBetweenBoxes = 0;
        panelRight.removeAll();									// Удаляет содержимие. Если не вводить, то постоянно будет выводится содержимое старого размещения
        panelRight.setSize((new Dimension((int) (fm.DEFAULT_WIDTH) - panelLeft.getWidth(), (int) (fm.DEFAULT_HEIGHT))));	// Задаем размеры панель для схемы производства и помещаем схему    
        Box b = new Box(BoxLayout.Y_AXIS);

// Устанавливаем вертикальное размещение компонентов в Box
//------There is MVC for panel types and models of equipment------------------//           
        PschemeModel pschemeModel = new PschemeModel(w, fm);                    //  Here we create a model panel types and models of equipment.
        PschemeView pschemeView = new PschemeView(pschemeModel, panelRight);    //  Here we create a view of panel for the  types and models of equipment.
        PtmController pschemeController = new PtmController(pschemeModel);//Here we create a controller of panel for the  types and models of equipment

        pschemeModel.addObserver(pschemeView);                                  //  Here we add an observer to the model.        
        
        pschemeView.addMouseMotionListener(pschemeController);                          //  Here we add an observer (MouseMotionListener: mouseDragged, mouseMoved) for the view.
        pschemeView.addMouseListener(pschemeController);                                //  Here we add an observer (MouseListener:  mousePressed) for the view.





//------ There are the parametrs for panel types and models of equipment------//           
        
        Dimension preferredSize = new Dimension((Toolkit.getDefaultToolkit().getScreenSize().width) / 8, (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.8));
        pschemeView.setPreferredSize(preferredSize);
        pschemeView.setMinimumSize(preferredSize);

        b.add(pschemeView);
        b.add(Box.createVerticalStrut(intervalBetweenBoxes));

//------There is MVC for panel types and models of equipment------------------//        
        PtmModel ptmModel = new PtmModel(fm);                                   //  Here we create a model panel types and models of equipment.
        PtmView ptmView = new PtmView(ptmModel, panelRight);                    //  Here we create a view of panel for the  types and models of equipment.

        PtmController ptmController = new PtmController(ptmModel);              //  Here we create a controller of panel for the  types and models of equipment       

        ptmModel.addObserver(ptmView);                                          //  Here we add an observer-ptmView to the model. 
        ptmModel.addObserver(pschemeModel);                                     //  Here we add an observer-pschemeModel to the model.         

        ptmView.addMouseMotionListener(ptmController);                          //  Here we add an observer (MouseMotionListener: mouseDragged, mouseMoved) for the view.
        ptmView.addMouseListener(ptmController);                                //  Here we add an observer (MouseListener:  mousePressed) for the view.
//------ There are the parametrs for panel types and models of equipment------//         
        Dimension preferredSizeV = new Dimension((Toolkit.getDefaultToolkit().getScreenSize().width) / 8, (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.5));
        ptmView.setPreferredSize(preferredSize);
        ptmView.setMinimumSize(preferredSize);
        b.add(new JScrollPane(ptmView));

        this.panelRight.add(b);
        panelRight.validate();   //  совместно с panelLeft.removeAll();	 производит перерисовку панели. Можно использовать panelLeft.updateUI();  panelLeft.repaint();  
        panelRight.updateUI(); 	//	setLeftComponent(panelLeft );   //     fm.getPanelSplitPane( ).setLeftComponent  (this.getLeftComponent()  );

        /*	Указанные стрелкой методы для обновления не нужныt -->  this.panelRight.validate();		this.panelRight.updateUI();   */
    }

    public JPanel getPanelRight() {
        return panelRight;
    }

    public void setPanelRight(JPanel panelRight) {
        this.panelRight = panelRight;
    }

    /**
     * По заданному idW формирует нижестоящие PBox, предварительно определив
     * idW, idSL,	idR и соответствующие им w, sl, r
     *
     * @param idW	выбранный id элемента w
     */
    public void setIdW(int idW) {
        t = fm.getTrest();
        if (idW == 0) {
            this.idW = t.getMinId(Work.class, t.getWorks());
        } else {
            this.idW = idW;
        }
        w = t.getById(Work.class, t.getWorks(), this.idW);
        b3 = new PBox(null, pboxName[3], 3);
        if (w != null) {

            setPanelRight();

            b2 = new PBox(w.getSubject_labours(), pboxName[2], 2);
            idSL = w.getMinId(Subject_labour.class, w.getSubject_labours());
            setIdSL(idSL);
        }
    }

    /**
     * Задаем idSL предмета труда По заданному idSL формирует нижестоящие PBox,
     * предварительно определив idSL,	idR и соответствующие им sl, r
     *
     * @param idW	выбранный id элемента w
     */
    public void setIdSL(int idSL) {
        this.idSL = idSL;
        sl = null;
        sl = w.getById(Subject_labour.class, w.getSubject_labours(), this.idSL);
        panelTableLineroutes = new PPanelTable("Выбор тех.операции изготовления", null, Lineroute.class, columnHeaderLineroute, 1);
        panelTableResources = new PPanelTable("Выбор тех.ресурсов для операции изготовления", null, Linespec.class, columnHeaderLinespec, 2);
        if (sl != null) {
            b3 = new PBox(sl.getRoutes(), pboxName[3], 3);
            idR = sl.getMinId(Route.class, sl.getRoutes());
            setIdR(idR);
        }
    }

    /**
     * Задаем idR технологического маршрута
     *
     * @param idR технологического маршрута
     */
    public void setIdR(int idR) {
        this.idSL = idSL;
        r = null;
        r = sl.getById(Route.class, sl.getRoutes(), idR);
        panelTableResources = new PPanelTable("Выбор тех.ресурсов для операции изготовления", null, Linespec.class, columnHeaderLinespec, 2);
        if (r != null) {
            panelTableLineroutes = new PPanelTable("Выбор тех.операции изготовления", r.getLineroutes(), Lineroute.class, columnHeaderLineroute, 1);
            idL = r.getMinId(Lineroute.class, r.getLineroutes());
            setIdL(idL);
            System.out.println("PBuider 142 panelTableResources  lr.getLinespecs()=");
        }
    }

    /**
     * Задаем idL линии (для операции) технологического маршрута
     *
     * @param idL	выбранный id элемента
     */
    public void setIdL(int idL) {
        this.idL = idL;
        lr = r.getById(Lineroute.class, r.getLineroutes(), this.idL);
        panelTableResources = new PPanelTable("Выбор тех.ресурсов для операции изготовления", null, Linespec.class, columnHeaderLinespec, 2);
        if (lr != null) {
            idSpec = lr.getMinId(Linespec.class, lr.getLinespecs());
            setIdSpec(idSpec, true);
            panelTableResources.updateUI();
        }
    }

    /**
     * Задаем idSpec спецификации для операции технологического маршрута
     *
     * @param idSpec	выбранный id элемента
     * @param updatePanel	флаг, значение которого требует true - перерисовки
     * панели false - оставить панель без изменения
     */
    public void setIdSpec(int idSpec, boolean updatePanel) {
        this.idSpec = idSpec;
        if (updatePanel) {
            panelTableResources = new PPanelTable("Выбор тех.ресурсов для операции изготовления", lr.getLinespecs(), Linespec.class, columnHeaderLinespec, 2);
        }
        panelTableResources.updateUI();
    }

    private int idW;
    private int idSL;
    private int idR;
    private int idL;
    private int idSpec;

    private Trest t;
    private Work w;
    private Subject_labour sl;
    private Route r;
    private Lineroute lr;

    private JPanel panelLeft;
    private JPanel panelRight;
    private DataSet ds;
    private MenuFrame fm;

    private PBox b1;
    private PBox b2;
    private PBox b3;
    private PPanelTable panelTableLineroutes;
    private PPanelTable panelTableResources;

    private String[] pboxName = new String[]{" ", "Выбор производства  			", "Выбор предмета труда			", "Выбор тех.маршрута изготовления "};

    private HashMap<Integer, ColumnData> columnHeaderLineroute;
    private HashMap<Integer, ColumnData> columnHeaderLinespec;

}
