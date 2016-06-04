package old.simulation.GUI;

import old.database.tabDataSet.RowIdNameDescription;
import old.entityProduction.*;
import old.simulation.GUI.PBuilderRoutePanePack.MVC_PanelSchema.PschemeModel;
import old.simulation.GUI.PBuilderRoutePanePack.MVC_PanelSchema.PschemeView;
import old.simulation.GUI.PBuilderRoutePanePack.MVC_PanelTypModmachine.PtmController;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PSplitPane extends JSplitPane {

    public PTablePanel getScrollPane_122() {
        return scrollPane_122;
    }

    public void setScrollPane_122(PTablePanel scrollPane_122) {
        this.scrollPane_122 = scrollPane_122;
    }

    public PTablePanel getScrollPane_121() {
        return scrollPane_121;
    }

    public void setScrollPane_121(PTablePanel scrollPane_121) {
        this.scrollPane_121 = scrollPane_121;
    }

    public PTablePanel getScrollPane_2() {
        return scrollPane_2;
    }

    public void setScrollPane_2(PTablePanel scrollPane_2) {
        this.scrollPane_2 = scrollPane_2;
    }

    public JScrollPane getScrollPaneSummary() {
        return scrollPaneSummary;
    }

    public void setScrollPaneSummary(JScrollPane scrollPaneSummary) {
        this.scrollPaneSummary = scrollPaneSummary;
    }

    public JSplitPane getOuterPane_1() {
        return outerPane_1;
    }

    public void setOuterPane_1(JSplitPane outerPane_1) {
        this.outerPane_1 = outerPane_1;
    }

    public JSplitPane getOuterPane_12() {
        return outerPane_12;
    }

    public void setOuterPane_12(JSplitPane outerPane_12) {
        this.outerPane_12 = outerPane_12;
    }

    public PschemeView getPanelScheme() {
        return pschemeView;
    }

    public void setPanelScheme(PschemeView pschemeView) {
        this.pschemeView = pschemeView;
    }

    public JScrollPane getScrollPaneScheme() {
        return scrollPaneScheme;
    }

    public void setScrollPaneScheme(JScrollPane scrollPaneScheme) {
        this.scrollPaneScheme = scrollPaneScheme;
    }

    public JScrollPane getScrollPaneDescription() {
        return scrollPaneDescription;
    }

    public void setScrollPaneDescription(JScrollPane scrollPaneDescription) {
        this.scrollPaneDescription = scrollPaneDescription;
    }

    public PBuilderRoutePane getP() {
        return p;
    }

    public void setP(PBuilderRoutePane p) {
        this.p = p;
    }

    private PBuilderRoutePane p;
    private MenuFrame fm;
    private JTree tree;
    private DefaultTreeModel modelTree;

    private JTextArea textArea;
    private JScrollPane scrollPaneDescription;
    private JScrollPane scrollPaneScheme;
    private PschemeView pschemeView;

    private JSplitPane outerPane_12;
    private PTablePanel scrollPane_2;
    private JSplitPane outerPane_1;
    private JScrollPane scrollPaneSummary;

    private PTablePanel scrollPane_121;																// Создаем таблицу на место PSplitPane.scrollPane_121
    private PTablePanel scrollPane_122;																// Создаем таблицу на место PSplitPane.scrollPane_122     

    private static Object rootElement;
    private Object w;
    private Object ord;
    
    private PschemeModel pschemeModel;

    public ArrayList root_getWorks(Class... cLs) {
        return PSplitPane.getTab_for_Element(rootElement, cLs);
    }   // возвращает элементы первой  таблицы

    public ArrayList w_getOrders(Class... cLs) {
        return PSplitPane.getTab_for_Element(w, cLs);
    }   // возвращает элементы второй  таблицы

    public ArrayList ord_getLines(Class... cLs) {
        return PSplitPane.getTab_for_Element(ord, cLs);
    }   // возвращает элементы третьей таблицы	
    private int id1;
    private int id2;
    private int id3;

    public void setId2(int id2, Class... cLs) {
        this.id2 = id2;
        w = ((RowIdNameDescription) rootElement).getById(cLs[1], root_getWorks(cLs), id2);	// находим по id2 производство
    }

    public void setId3(int id3, Class... cLs) {
        this.id3 = id3;
        if (id3 == Integer.MAX_VALUE) {
            ord = ((RowIdNameDescription) w).getById(cLs[2], w_getOrders(cLs), ((RowIdNameDescription) w).getMinId(cLs[2], w_getOrders(cLs)));   //	если в таблицу Orders добавляется только первый элемент. Это мы определяем по условию (id3 == Integer.MAX_VALUE)
        } else {
            ord = ((RowIdNameDescription) w).getById(cLs[2], w_getOrders(cLs), id3);			//	если в таблицу Orders добавляется второй первый элемент и более. В данном случае (id3 != Integer.MAX_VALUE)
        }
    }

    public PSplitPane(MenuFrame fm) {
        this.fm = fm;
    }

    public PSplitPane getPSplitPaneTree() {

        /**
         * Обработчик событий, возникающих при нажатии на узел дерева.
         * Обеспечивает возврат панели для просмотра данных об обекте, который
         * содержится в узле дерева. ДОРАБОТАТЬ: ПЕРЕДАТЬ ПАРАМЕТРАМИ tree,
         * textArea, scrollPaneDescription
         */
        class TreeSelectListener implements TreeSelectionListener {

            @Override
            public void valueChanged(TreeSelectionEvent e) // Обработка события выбора или отмена узла 
            {
                TreePath path = tree.getSelectionPath();							// выбор узла дерева
                if (path == null) {
                    return;
                }
                String description = path.toString();								// это убрать в дальнейшем
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
                textArea.setText(description);
                Work w = getWorkCurrent(path);										//	получаем объект производства, схема которого выводитсяв панель

//------There is MVC for panel types and models of equipment------------------//   
                pschemeModel = new PschemeModel(w, fm);
                pschemeView = new PschemeView(pschemeModel, scrollPaneScheme);
                PtmController pschemeController = new PtmController(pschemeModel);//Here we create a controller of panel for the  types and models of equipment
                pschemeModel.addObserver(pschemeView);                          //  Here we add an observer to the model.        
                pschemeView.addMouseMotionListener(pschemeController);          //  Here we add an observer (MouseMotionListener: mouseDragged, mouseMoved) for the view.
                pschemeView.addMouseListener(pschemeController);                //  Here we add an observer (MouseListener:  mousePressed) for the view.
//----------------------------------------------------------------------------//
                scrollPaneDescription.setViewportView(PanelFragment.getPanel(selectedNode.getUserObject(), fm));
                scrollPaneScheme.setViewportView(pschemeView);  				// panelScheme.getPanelSchemerepaint()
                repaint();

            }

            public Work getWorkCurrent(TreePath path) {
                Work w = new Work();
                DefaultMutableTreeNode Node = (DefaultMutableTreeNode) path.getLastPathComponent();								// возвращаем родительский узел для выделеного узела

                while (Node != null && Node.getUserObject().getClass() != Trest.class) {
                    if (Work.class == (Class) Node.getUserObject().getClass()) {
                        return w = (Work) Node.getUserObject(); // определяем класс объекта getUserObject(), который находится в узле
                    }
                    Node = (DefaultMutableTreeNode) Node.getParent();
                }
                return w;
            }

            public Work getWorkCurrent2(DefaultMutableTreeNode selectedNode) {
                Work w = new Work();
                DefaultMutableTreeNode cloneNode = (DefaultMutableTreeNode) selectedNode.clone();								// возвращаем родительский узел для выделеного узела
                DefaultMutableTreeNode cloneParent = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) (selectedNode.getParent())).clone();
                while (cloneNode != null && cloneParent != null && cloneParent.getUserObject().getClass() != Trest.class) {

                    if (Work.class == (Class) cloneNode.getUserObject().getClass()) // определяем класс объекта getUserObject(), который находится в узле
                    {
                        System.out.println("Сработал вход");
                        return w = (Work) cloneNode.getUserObject();
                    }
                    cloneNode = (DefaultMutableTreeNode) cloneNode.getParent();

                }

                return w;
            }

        }

        textArea = new JTextArea();
        JScrollPane scrollPaneSummary = new JScrollPane(textArea);	// Создаем панель для общего описания элемента и помещаем в нее общее описание элемента       
        scrollPaneDescription = new JScrollPane();						// Создаем панель для описания элемента и помещаем в нее описание элемента           
        scrollPaneScheme = new JScrollPane();						// Создаем панель для схемы производства и помещаем схему      
        scrollPaneScheme.setPreferredSize(new Dimension((int) (fm.DEFAULT_WIDTH * 0.7), (int) (fm.DEFAULT_HEIGHT * 0.8)));	// Задаем размеры панель для схемы производства и помещаем схему    

        //--------------------- Дерево. Вынести отдельным кодом при появлении еще деревьев   
        modelTree = ModelTree.makeSampleTree2(fm.getTrest());
        tree = new JTree(modelTree);

        fm.setTree(tree);
        fm.setModelTree(modelTree);

        JScrollPane scrollPaneTree = new JScrollPane(tree);		// Создаем панель для дерева и помещаем дерево в панель       
        tree.setEditable(true);											// Разрешение на редактирование названия узлов дерева  

        TreeSelectListener tsl = new TreeSelectListener();
        tree.addTreeSelectionListener(tsl);
        tree.setCellRenderer(new MyRenderer());
        int mode = TreeSelectionModel.SINGLE_TREE_SELECTION;
        tree.getSelectionModel().setSelectionMode(mode);
        //--End---------------- Дерево. Вынести отдельным кодом при появлении еще деревьев      	

        /* Формируем разделительные панели */
        JSplitPane outerPane2 = ForMakePane.getSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPaneScheme, scrollPaneDescription, true, true);
        JSplitPane outerPane1 = ForMakePane.getSplitPane(JSplitPane.VERTICAL_SPLIT, outerPane2, scrollPaneSummary, true, true);
        /* Формируем основную панель и возвращаем ее */
        setLeftComponent(scrollPaneTree);
        setRightComponent(outerPane1);
        setContinuousLayout(true);
        setOneTouchExpandable(true);
        setSize(500, 500);
        setOrientation(JSplitPane.HORIZONTAL_SPLIT);

        return this;
    }

    /**
     * Возвращаем соответствующий план для перспективы
     *
     * @param i	номер перспективы
     * @return	cLsR план перспективы
     */
    public static Class[] getCLs(int i) {
        Class[] cLsR;
        if (i == 1) {
            return cLsR = new Class[]{Trest.class, Work.class, Order.class, Line.class};		// план построения перспективы №1 	"Orders Perspective"
        }
        if (i == 2) {
            return cLsR = new Class[]{Work.class, Subject_labour.class, Route.class, Lineroute.class};		// план построения перспективы №2 	"Route Perspective"
        }
        return null;
    }

    /**
     * Определяем корневой элемент для выводы таблиц. Если корневой элемент
     * Trest, то вывод таблиц , например, такой: Work -> Order -> Line
     *
     * @param cL	тип корневого элемента
     * @param i	индекс корневого элемента, если конневой элемент входит в
     * коллекцию. Например, если тип корневого элемента Work для выхода таблиц:
     * Subject_labour -> ТехМаршрут -> ТехОперации, то необходимо знать номер i,
     * чтобы определить, какое у нас производство из коллекции Work.get(id). Так
     * как элемент типа Trest в одном экземпляре, то для него номер не нужен.
     * Итак вернется только один элемент из коллекции
     * @return
     */
    public static <cL> cL rootFindElement(int id, MenuFrame _fm, Class... cLs) {
        if (Arrays.asList(getCLs(1)).equals(Arrays.asList(cLs))) {
            return (cL) _fm.getTrest();	// Если корневой элемент Trest, то вывод таблиц , например, такой: Work -> Order -> Line
        }

        if (Arrays.asList(getCLs(2)).equals(Arrays.asList(cLs))) {
            // (  _fm.getTrest()).getById(cLs[0], tab, id)
            return (cL) (_fm.getTrest()).getById(cLs[0], _fm.getTrest().getWorks(), id);	 //_fm.getTrest(). getMinId(cLs[0],  _fm.getTrest().getWorks())
        }
									//public <cL> cL getById	(Class cL, Object tab, int id)

        //if (Arrays.asList(getCLs (2)).equals(Arrays.asList(cLs))) return ( (cL)  (  (Trest.class)_fm.getTrest() ). getMinId(cLs[0], root_getWorks(cLs)),cLs);
        //		if (Arrays.asList(cLs2).equals(Arrays.asList(cLs))) return ((Object)_fm.getTrest()). getMinId(cLs[0], root_getWorks(cLs)),cLs);	// Если корневой элемент Trest, то вывод таблиц , например, такой: Work -> Subject_labour -> Route -> Lineroute
        // для  Work :		если  id1 не находит, то возвращает минимальный элемент
        return null;
    }

    public static <cL> ArrayList getTab_for_Element(cL o, Class... cLs) {

        ArrayList tab = new ArrayList();

        if (Arrays.asList(getCLs(1)).equals(Arrays.asList(cLs))) {
            if (o.getClass() == cLs[0]) {
                tab = (ArrayList) ((Trest) o).getWorks();
            }
            if (o.getClass() == cLs[1]) {
                tab = (ArrayList) ((Work) o).getOrders();
            }
            if (o.getClass() == cLs[2]) {
                tab = (ArrayList) ((Order) o).getLines();
            }
        }
        if (Arrays.asList(getCLs(2)).equals(Arrays.asList(cLs))) {
            if (o.getClass() == cLs[0]) {
                tab = (ArrayList) ((Work) o).getSubject_labours();
            }
            if (o.getClass() == cLs[1]) {
                tab = (ArrayList) ((Subject_labour) o).getRoutes();
            }
            if (o.getClass() == cLs[2]) {
                tab = (ArrayList) ((Route) o).getLineroutes();
            }
        }
        return tab;
    }

    public PSplitPane getPSplitPaneOrderTableM(int id1, Class... cLs) {
        rootElement = rootFindElement(id1, fm, cLs);
        System.out.println("PSplitPane 265   id1=" + id1);

        /*Начало --		Алгоритм определения минимального id2 для таблицы getWorks() и минимального id3 для таблицы getOrders().						*/
        /*В качестве начальных условий решено взять минимальные значения id2 для таблицы getWorks() и id3 для таблицы getOrders().						*/
        if (root_getWorks(cLs).isEmpty() == true) {
            System.out.println("PSplitPanel	272  таблицап пуста");
            //	    	PTablePanel.iniStaticId (3);	
            scrollPane_2 = new PTablePanel(cLs[1], fm, cLs);				// Создаем панель для общего описания элемента и помещаем в нее	 	
            scrollPane_121 = new PTablePanel(cLs[2], fm, cLs);				//	если коллекция пуста, то выводим только кнопки без таблицы
            scrollPane_122 = new PTablePanel(cLs[3], fm, cLs);				//	если коллекция пуста, то выводим только кнопки без таблицы
            outerPane_12 = ForMakePane.getSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane_121, scrollPane_122, true, true);
            outerPane_12.repaint();
        } else {
            setId2(((RowIdNameDescription) rootElement).getMinId(cLs[1], root_getWorks(cLs)), cLs);		// -!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!------------
            //System.out.println("PSplitPanel	rootElement="+rootElement+"    id1="+id1+"    id2="+id2);	    
            setId3(((RowIdNameDescription) w).getMinId(cLs[2], w_getOrders(cLs)), cLs);
            /*конец  -- 	Алгоритм определения минимального id2 для таблицы getWorks() и минимального id2 для таблицы getOrders()							*/
            if ((w_getOrders(cLs)).isEmpty() == true) {
                scrollPane_121 = new PTablePanel(cLs[2], fm, cLs);				//	если коллекция пуста, то выводим только кнопки без таблицы
                scrollPane_122 = new PTablePanel(cLs[3], fm, cLs);				//	если коллекция пуста, то выводим только кнопки без таблицы
            } else {
                scrollPane_121 = new PTablePanel((ArrayList) w_getOrders(cLs), cLs[2], fm, cLs);									//	если коллекция непуста, Создаем панель для общего описания элемента и помещаем в нее	
                if (ord_getLines(cLs).isEmpty() == true) {
                    scrollPane_122 = new PTablePanel(cLs[3], fm, cLs);	//	если коллекция пуста, то выводим только кнопки без таблицы
                } else {
                    scrollPane_122 = new PTablePanel(ord_getLines(cLs), cLs[3], fm, cLs);	// Создаем панель для общего описания элемента и помещаем в нее  
                }
            }

            System.out.printf("PSplitPanel 295:  cLs[1] %s id1= %d  id2= %d  id3= %d   \n", cLs[1], id1, id2, id3);
            outerPane_12 = getPSplitPane12(
                    id1,
                    id2, // указываем минимальный по значению id таблицы getWorks(), для которого будет выведена таблица getOrders()  
                    id3 // минимальный по значению id таблицы getOrders(), для которого будет выведена таблица getLines()
                    , -1,
                    cLs);
            System.out.printf("PSplitPanel 302:  cLs[1] %s id1= %d  id2= %d  id3= %d   \n", cLs[1], id1, id2, id3);
            scrollPane_2 = new PTablePanel(root_getWorks(cLs), cLs[1], fm, cLs);			// Создаем панель для общего описания элемента и помещаем в нее	 		
            System.out.printf("PSplitPanel 304:  cLs[1] %s id1= %d  id2= %d  id3= %d   \n", cLs[1], id1, id2, id3);
        }
        scrollPaneSummary = new JScrollPane(textArea);	    	// Создаем панель для общего описания элемента и помещаем в нее  
        outerPane_1 = ForMakePane.getSplitPane(JSplitPane.VERTICAL_SPLIT, outerPane_12, scrollPaneSummary, true, true);
        System.out.printf("PSplitPanel 308:  cLs[1] %s id1= %d  id2= %d  id3= %d   \n", cLs[1], id1, id2, id3);
        setRightComponent(outerPane_1);
        setLeftComponent(scrollPane_2);
        setContinuousLayout(true);
        setOneTouchExpandable(true);
        setSize(500, 500);
        setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        return this;
    }

    /**
     * Формируем панели, которые требуется выводить
     *
     * @param id2	выделенный объект на первой панели. По нему выводится нужная
     * вторая панель
     * @param id3	выделенный объект на второй панели. По нему выводится нужная
     * третья панель
     * @return
     */
    public JSplitPane getPSplitPane12(int id1, int id2, int id3, int selIndex, Class... cLs) {
        System.out.printf("PSplitPanel 324:  cLs[1] %s id1= %d  id2= %d  id3= %d   \n", cLs[1], id1, id2, id3);
        setId1(id1);
        rootElement = rootFindElement(id1, fm, cLs);
        System.out.printf("PSplitPanel 327:  cLs[1] %s id1= %d  id2= %d  id3= %d   \n", cLs[1], id1, id2, id3);

        w = ((RowIdNameDescription) rootElement).getById(cLs[1], root_getWorks(cLs), id2); 			// находим по id2 производство
        if (w_getOrders(cLs).isEmpty() == true) { // если поле Orders у объекта Work w пусто, то создаем для этого поля пустую панель				 	
            setId2(id2, cLs);					// запоминаем id2 для выделенного объекта Work w. Это сделано для того, чтобы пока не будет выбран другой элемент в первой панели с отличным id2, панель Works и панель Orders меняться не будут			
            this.id3 = id3;					//	и запоминаем новое значение id2 выделенного объекта 
            scrollPane_121 = new PTablePanel(cLs[2], fm, cLs);				//	если коллекция пуста, то выводим только кнопки без таблицы
            scrollPane_122 = new PTablePanel(cLs[3], fm, cLs);				//	если коллекция пуста, то выводим только кнопки без таблицы
        } else {									//	если поле Orders у объекта Work w не пустое, то создаем для этого поля панель с данными, которые содержатся в поле  Orders (коллекция)	    			
            System.out.printf("PSplitPanel 338:  cLs[1] %s id1= %d  id2= %d  id3= %d   \n", cLs[1], id1, id2, id3);
            if (this.id2 != id2) {   			//	если выбран другой объект Work w, то для него выводим соотсетствующую ему панель объектов Orders
                //	условие if(this.id2!=id2) поставлено для того, чтобы при переходе с редактирования первой панели на вторую, вторая панель соответствовала состоянию до нажатия на второй панели. Если условия не поставить, то при нажадии перерисуются первая и вторая панель, изменяться ссылки и редактировать будет невозможно												   				 	    				

                scrollPane_121 = new PTablePanel(w_getOrders(cLs), cLs[2], fm, cLs);
                setId2(id2, cLs);				// запоминаем id2 для выделенного объекта Work w. Это сделано для того, чтобы пока не будет выбран другой элемент в первой панели с отличным id2, панель Works и панель Orders меняться не будут			
            }
            setId3(id3, cLs);					//	и запоминаем новое значение id2 выделенного объекта 
            System.out.printf("PSplitPanel 346:  cLs[1] %s id1= %d  id2= %d  id3= %d   \n", cLs[1], id1, id2, id3);
            if (ord != null) {
                if (ord_getLines(cLs).isEmpty() == true) {
                    scrollPane_122 = new PTablePanel(cLs[3], fm, cLs);						//	если коллекция пуста, то выводим только кнопки без таблицы
                } else if (selIndex == -1) {
                    scrollPane_122 = new PTablePanel(ord_getLines(cLs), cLs[3], fm, cLs);			    // Создаем панель для общего описания элемента и помещаем в нее  
                } else {
                    scrollPane_122 = new PTablePanel(ord_getLines(cLs), cLs[3], fm, selIndex, cLs);
                }
            }
            System.out.printf("PSplitPanel 350:  cLs[1] %s id1= %d  id2= %d  id3= %d   \n", cLs[1], id1, id2, id3);
        }
        JSplitPane outerPane_12 = ForMakePane.getSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane_121, scrollPane_122, true, true);
        outerPane_12.repaint();
        return outerPane_12;
    }

    public void setId1(int id1) {
        this.id1 = id1;
        PTablePanel.setId1(id1);
    }

    /**
     * Вспомогательный класс для инициализации панелей. Использйя два
     * компонента, составляем из них разделительную панель. Алгоритм: указваем
     * ориентацию->указываем, где какой компанент находится (слева или справа)
     * -> устанавливаем необходимость развертываения панели. Размер панели
     * ограничен методом splitPane.setSize(500, 500); в методе
     * getSplitPane(...). Введен для того, чтобы убрать повторение кода,
     * сократив его.
     *
     */
    static class ForMakePane {

        /**
         * Создает JSplitPane панель по заданным параметрам
         *
         * @param newOrientation	see new JSplitPane
         * @param newLeftComponent	see new JSplitPane
         * @param newRightComponent	see new JSplitPane
         * @param flagSetContinuousLayout	Устанавливаем пиктограммы полного
         * развертывания панелей (треугольнички для стиля Метал). See
         * setContinuousLayout(true)
         * @param flagSetOneTouchExpandable	Устанавливаем пиктограммы полного
         * развертывания панелей (треугольнички для стиля Метал). See
         * setContinuousLayout(true)
         *
         * @return	возвращает созданную панель по входным параметрам
         */
        public static JSplitPane getSplitPane(int newOrientation, Component newLeftComponent, Component newRightComponent, boolean flagSetContinuousLayout, boolean flagSetOneTouchExpandable) {
            JSplitPane splitPane = new JSplitPane(newOrientation, newLeftComponent, newRightComponent);
            splitPane.setContinuousLayout(flagSetContinuousLayout);		// Устанавливаем пиктограммы полного развертывания панелей (треугольнички для стиля Метал)    
            splitPane.setOneTouchExpandable(flagSetOneTouchExpandable);	// Перерисовываем содержимое при перемещении границы панели    
            splitPane.setSize(500, 500);
            return splitPane;
        }
    }

    /**
     *
     * @return	Панель PBuilderRoutePane для построения технологических маршрутов
     */
    public PSplitPane getPBuilderRoutePane() {

        p = new PBuilderRoutePane(fm);
        setLeftComponent(p.getLeftComponent());
        setRightComponent(p.getRightComponent());

        return this;
    }

}
