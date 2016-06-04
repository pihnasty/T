package old.simulation.GUI;

import old.database.DataSet;
import old.database.XmlRW;
import old.database.tabDataSet.*;
import old.entityProduction.*;
import old.simulation.GUI.PBuilderRoutePanePack.MVC_PanelSchema.PschemeModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.InternationalFormatter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

public class PanelFragment {

    static Object o;								// объект, название полей и значения полей которого исследуем					
    static JLabel[] labelName;						// массив длиной XmlRW.fieldsCl(cL).length, содержащий названия полей объекта
    static Class cL = null;							// класс (тип данных) объекта. Поле не обязательное. Ввели для сокращения записи: cL = o.getClass();	
    static JFormattedTextField[] jFTF;				// массив длиной XmlRW.fieldsCl(cL).length для формирования значений полей объекта 					
    static JPanel mainPanel;
    static Work work;

    /**
     * GBL обслуживает функционал размещение компонентов в GridBagLayout
     */
    public class GBL extends GridBagConstraints {

        GBL() {
        }

        /**
         * Заполняет компонентом ячейку (X,Y)
         *
         * @param gridx	координата ячейки сетки X
         * @param gridy	координата ячейки сетки Y
         */
        GBL(int gridx, int gridy) {
            this.gridx = gridx;
            this.gridy = gridy;
        }

        /**
         * Заполняет компонентом ячейку (X,Y) высотой (gridHeight) клеток и
         * шириной (gridWidth)
         *
         * @param gridx	координата ячейки сетки X
         * @param gridy	координата ячейки сетки Y
         * @param gridWidth	ширина ячейки сетки в клетках
         * @param gridHeight	высота ячейки сетки в клетках
         */
        GBL(int gridx, int gridy, int gridWidth, int gridHeight) {
            this(gridx, gridy);
            this.gridwidth = gridWidth;
            this.gridheight = gridHeight;
        }

        /**
         * определяет в какой части ячейки находится элемент - на севере,
         * северо-востоке, востоке, юго-востоке, юге .....
         *
         * @param anchor
         * @return
         */
        GBL setAnchor(int anchor) {
            this.anchor = anchor;
            return this;
        }

        /**
         * задает стиль заполнения:
         *
         * @param anchor	раятягивает єлемент а) BOTH - на все пространства
         * ячейки; б) HORIZONTAL - только по горизонтали; в) VERTICAL - только
         * по вертикали
         * @return	текущая панель
         */
        public GBL setFill(int anchor) {
            this.fill = anchor;
            return this;
        }

        /**
         * задает распределение пустого пространства. Пусть х1j-это размер
         * элемента, х2j - это размер ячейки, z - размер области z=sum(x2j);
         * пустое пространство= sum(x2j-x1j). Она распределяется на каждую
         * ячейку пропорционально weightx - ячейки.
         *
         * @param
         * @param weighty
         * @return
         */
        public GBL setWeightXY(double weightx, double weighty) {
            this.weightx = weightx;
            this.weighty = weighty;
            return this;
        }
    }

    public PanelFragment() {
    }

    /**
     * Формируем панель для вывода на экран данных о полях класса. Входными
     * параметрами объект (Object _o). Получив (Object _o), мы определяем его
     * _o.getClass() и выводим в табличном представлении в первой колонке имя
     * поля, во второй колонке значения, хранящееся в поле. Значение полей можно
     * изменять. Для валидации данных используется Format данных, который для
     * каждого поля задается в описываемом классе. Введена кнопка
     * saveChangesButton ("Сохранить изменения"), нажатие которой позволяет
     * сохранить изменения полей в оперативной памяти.
     *
     * @param _o	исследуемый объект класса
     * @return	панель с данными полей объекта, которые можно редактировать
     * @throws IllegalAccessException
     */
    public static <cL> JPanel getPanel(Object _o, final MenuFrame fm) {
        /**
         * Обработчик события нажатия кнопки создания нового объекта
         */
        class CreateActionListener implements ActionListener {

            /**
             * Переопределяет класс объекта, который надо вставить в узел. Если
             * это не строка, то изменений не происходит. Если это одна ихз
             * строк, то ей ставится в соответствие нужный класс
             *
             * @param obj Объект пользователя, который содержится в выбранном
             * узле.
             */
            public void setClass(Object obj) {
                if (obj == "Оборудование") {
                    cL = Machine.class;
                }
                if (obj == "Персонал") {
                    cL = Employee.class;
                }
                if (obj == "Заказы") {
                    cL = Order.class;
                }

            }

            public CreateActionListener() {
            }

            public void actionPerformed(ActionEvent event) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) fm.getTree().getLastSelectedPathComponent();	//	определяем объект, который представляет текущий выбранный узел
                if (selectedNode == null) {
                    return;																				// 		
                }
                DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selectedNode.getParent();							// 	возвращаем родительский узел для текущего выбранныого узела
                if (parent == null) {
                    return;
                }

                Object parentO = new Object();
                if (parent.getUserObject().getClass() != String.class) {
                    parentO = parent.getUserObject();
                } else {
                    parentO = ((DefaultMutableTreeNode) parent.getParent()).getUserObject();
                }
                Object newO = new Object();
                Object rowO = new Object();
                setClass(selectedNode.getUserObject());

                try {
                    newO = cL.getConstructor(DataSet.class).newInstance(fm.getDataSet());	//	создаем новый объект типа    XXXX        (например, производство) и (читать ниже).
                    rowO = cL.getSuperclass().getConstructor().newInstance();				//	создаем новый объект типа RowXXXX (например, строка производство) и (читать ниже)   	

                } catch (InstantiationException exp) {
                    exp.printStackTrace();
                } catch (IllegalAccessException exp) {
                    exp.printStackTrace();
                } catch (IllegalArgumentException exp) {
                    exp.printStackTrace();
                } catch (InvocationTargetException exp) {
                    exp.printStackTrace();
                } catch (NoSuchMethodException exp) {
                    exp.printStackTrace();
                } catch (SecurityException exp) {
                    exp.printStackTrace();
                }

                //-------------------------------------------формируем объект Work     и делаем по нему все изменения в DataSet ----------------------------//
                if (cL == Work.class) {
                    //newO = new Work(fm.getDataSet());  					//	создаем новый объект Work (производство) (оставил для образца)  
                    fm.getTrest().getWorks().add((Work) newO);				//  помещаем его в коллекцию Trest.works
                    work = (Work) newO;
//RowWork r = new RowWork();							//	создаем новый объект RowWork (строка производство) (оставил для образца) 				            	 	
                    fm.getDataSet().getTabWorks().add((RowWork) rowO);				//  помещаем его в коллекцию DataSet.tabWorks

                    RowTrestWork rIdId = new RowTrestWork(fm.getTrest().getId(), ((RowIdNameDescription) newO).getId(), "   ");	//	Для связи по id строки RowWork таблицы tabWorks со строкой RowTrest таблицы tabTrests создаем строку реестра  RowTrestWork и (читать ниже)
                    fm.getDataSet().getTabTrestsWorks().add(rIdId);	            												//	помещаем ее в таблицу DataSet.tabTrestsWorks
                }
                int index = -1;
                //-------------------------------------------формируем объект Machine   и делаем по нему все изменения в DataSet ----------------------------//
                if (cL == Machine.class || selectedNode.getUserObject() == "Оборудование") {
                    for (Work w : fm.getTrest().getWorks()) {
                        if (((Work) parentO).getId() == w.getId()) {
                            index = fm.getTrest().getWorks().indexOf(w);  	// проходим по списку Trest.works и определяем индекс Work, в которое в дереве добавляем  Machine
                            work = w;
                        }
                    }
                    if (index != -1) {
                        fm.getTrest().getWorks().get(index).getMachines().add((Machine) newO); 											// добавили в найденный с нужным индексом Work объект  Machine

                    }
                    fm.getDataSet().getTabMachines().add((RowMachine) rowO);																		//  помещаем Machine в коллекцию DataSet.tabWorks
                    System.out.println("PanelFfagment 245= ((RowMachine) rowO).getId()= " + ((RowIdNameDescription) newO).getId());
                    RowWorkMachine rIdId = new RowWorkMachine(fm.getTrest().getWorks().get(index).getId(), ((RowIdNameDescription) newO).getId(), "   ");	//	Для связи по id строки RowMachine	 таблицы tabMachine	s со строкой RowWork таблицы tabWorks создаем строку реестра  RowWorkMachine и (читать ниже)
                    fm.getDataSet().getTabWorksMachines().add(rIdId);	            												//	помещаем ее в таблицу DataSet.tabWorksMachines

                }
                //-------------------------------------------формируем объект Employee  и делаем по нему все изменения в DataSet ----------------------------//
                if (cL == Employee.class || selectedNode.getUserObject() == "Персонал") {
                    for (Work w : fm.getTrest().getWorks()) {
                        if (((Work) parentO).getId() == w.getId()) {
                            index = fm.getTrest().getWorks().indexOf(w);  // проходим по списку Trest.employees и определяем индекс Work, в которое в дереве добавляем Employee	
                        }
                    }
                    if (index != -1) {
                        fm.getTrest().getWorks().get(index).getEmployees().add((Employee) newO); 	// добавили в найденный с нужным индексом Work объект  Employee
                    }
                    fm.getDataSet().getTabEmployees().add((RowEmployee) rowO);				//  помещаем Machine в коллекцию DataSet.tabEmployees

                    RowWorkEmployee rIdId = new RowWorkEmployee(fm.getTrest().getWorks().get(index).getId(), ((RowIdNameDescription) newO).getId(), "   ");	//	Для связи по id строки RowEmployee	 таблицы tabEmployee	s со строкой RowWork таблицы tabWorks создаем строку реестра  RowWorkEmployee и (читать ниже)
                    fm.getDataSet().getTabWorksEmployees().add(rIdId);	            												//	помещаем ее в таблицу DataSet.tabWorksEmployees
                }
                //-------------------------------------------формируем объект Order  и делаем по нему все изменения в DataSet ----------------------------//
                if (cL == Order.class || selectedNode.getUserObject() == "Заказы") {
                    for (Work w : fm.getTrest().getWorks()) {
                        if (((Work) parentO).getId() == w.getId()) {
                            index = fm.getTrest().getWorks().indexOf(w);  // проходим по списку Trest.employees и определяем индекс Work, в которое в дереве добавляем Employee	
                        }
                    }
                    if (index != -1) {
                        fm.getTrest().getWorks().get(index).getOrders().add((Order) newO); 	// добавили в найденный с нужным индексом Work объект  Employee
                    }
                    fm.getDataSet().getTabOrders().add((RowOrder) rowO);				//  помещаем в коллекцию DataSet.tabOrders

                    RowWorkOrder rIdId = new RowWorkOrder(fm.getTrest().getWorks().get(index).getId(), ((RowIdNameDescription) newO).getId(), "   ");	//	Для связи по id строки RowEmployee	 таблицы tabEmployee	s со строкой RowWork таблицы tabWorks создаем строку реестра  RowWorkEmployee и (читать ниже)
                    fm.getDataSet().getTabWorksOrders().add(rIdId);	            												//	помещаем ее в таблицу DataSet.tabWorksEmployees
                }

                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(newO);												// 	создаем новый узел

                if (cL == Work.class) {
                    work = (Work) newO;
                    DefaultMutableTreeNode wm = new DefaultMutableTreeNode("Оборудование");			// Создаем узел -Трест->Производство->Оборудование
                    DefaultMutableTreeNode we = new DefaultMutableTreeNode("Персонал");				// Создаем узел -Трест->Производство->Персонал
                    DefaultMutableTreeNode wo = new DefaultMutableTreeNode("Заказы");				// Создаем узел -Трест->Производство->Персонал
                    newNode.add(wm);
                    wm.setAllowsChildren(true);									// Указываем, допустимо ли создание дочерник узлов. false - недопустимо
                    newNode.add(we);
                    we.setAllowsChildren(true);
                    newNode.add(wo);
                    wo.setAllowsChildren(true);
                }

                int selectedIndex = parent.getIndex(selectedNode);																//	получаем индекс родительского узла		

                if (selectedNode.getUserObject() == "Оборудование"
                        || selectedNode.getUserObject() == "Персонал"
                        || selectedNode.getUserObject() == "Заказы") {
                    fm.getModelTree().insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());
                } else {
                    fm.getModelTree().insertNodeInto(newNode, parent, selectedIndex + 1);											// 	вставляем новый узел от родительского с индексом = (selectedIndex + 1). Если +1 не поставить , то вставка будет до узла. Если поставить , то вставка будет после узла.				 
                }

                TreeNode[] nodes = fm.getModelTree().getPathToRoot(newNode);     												//	Если закоментировать, нет изменений
                TreePath path = new TreePath(nodes);				      														//	Если закоментировать, нет изменений
                fm.getTree().scrollPathToVisible(path);
                XmlRW.FieldToField(rowO, newO);									//  копируем в RowWork поля из Work (основное - это поля iD, остальные можно было не копировать ) и (читать ниже)   //	Если закоментировать, нет изменений. Скорее всего связано с автоматическим уведомлением.
                if (cL == Employee.class || selectedNode.getUserObject() == "Персонал" || cL == Machine.class || selectedNode.getUserObject() == "Оборудование" || cL == Order.class || selectedNode.getUserObject() == "Заказы") {
                    work = ((Work) parentO);
                    //  Перерисовываем панель после добавления элемента
                }	     													//  Перерисовываем панель после добавления элемента
                fm.getPanelSplitPane().getPanelScheme().PschemeViewAdd(new PschemeModel(work, fm));
                fm.getPanelSplitPane().getPanelScheme().repaint();

            }
        }
        /**
         * Обработчик события нажатия кнопки создания нового объекта
         */
        class DeleteActionListener implements ActionListener {

            public DeleteActionListener() {
            }

            ;
			/**
			 * 
			 * @param tab			коллекция объектов, один из которых подлежат удалению
			 * @param Rowtab		таблица (коллекция) строк в DataSet с уникальным id2, в которых хранятся данные для формирования объектов
			 * @param RowIdIdwtab   таблица (коллекция) реестров строк в DataSet с уникальным id и id2, в которых хранятся данные для формирования объектов
			 * @param id			id родителя
			 * @param id2			id2 дочернего элнмента
			 */
			public <cL, RowcL, RowIdIdcL> void delRow(ArrayList<cL> tab, ArrayList<RowcL> Rowtab, ArrayList<RowIdIdcL> RowIdIdtab, int id, int id2) {
                for (cL e : tab) {
                    if (id2 == ((RowIdNameDescription) e).getId()) {
                        tab.remove(e);
                        break;
                    }		// удаляем объект		
                }
                for (RowcL r : Rowtab) {
                    if (id2 == ((RowIdNameDescription) r).getId()) {
                        Rowtab.remove(r);
                        break;
                    }		// удаляем строку данных об объекте				
                }
                for (RowIdIdcL wr : RowIdIdtab) {
                    if (id2 == ((RowIdId2) wr).getId2() && id == ((RowIdId2) wr).getId()) {
                        RowIdIdtab.remove(wr);
                        break;
                    }		// удаляем строку реестра с данными об связи объектах
                }
            }

            /**
             * Удаляет все объекты из таблицы tab и приводит в соответствие с
             * изменением состояние таблиц в DataSet
             *
             * @param tab	коллекция объектов, элементы которой будут удалены
             * @param Rowtab	таблица (коллекция) строк в DataSet с уникальным
             * id2, в которых хранятся данные для формирования объектов. Каждому
             * объекту соответствует строка. При удалении объектов таблицы tab
             * из таблицы Rowtab удаляются все строки, соответствующие этому
             * объекту
             * @param RowIdIdwtab таблица (коллекция) реестров строк в DataSet с
             * уникальным id и id2, в которых хранятся данные для формирования
             * объектов. При удалении объектов таблицы tab из таблицы
             * RowIdIdwtab удаляются все строки реестра, соответствующие этому
             * объекту
             * @param id	id родителя
             * @param id2	id2 дочернего элнмента
             */
            public <cL, RowcL, RowIdIdcL> void delRowAll(ArrayList<cL> tab, ArrayList<RowcL> Rowtab, ArrayList<RowIdIdcL> RowIdIdtab, int id) {
                for (cL e : tab) {
                    for (int i = Rowtab.size() - 1; i >= 0; i--) {
                        if (((RowIdNameDescription) (Rowtab.get(i))).getId() == ((RowIdNameDescription) e).getId()) {
                            Rowtab.remove(i);    		// удаляем строку данных об объекте	
                        }
                    }
                    for (int i = RowIdIdtab.size() - 1; i >= 0; i--) {
                        if (((RowIdId2) (RowIdIdtab.get(i))).getId2() == ((RowIdNameDescription) e).getId()) {
                            RowIdIdtab.remove(i);    	// удаляем строку данных об объекте											
                        }
                    }
                }
                tab = new ArrayList<cL>();
            }

            public void actionPerformed(ActionEvent event) {

                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) fm.getTree().getLastSelectedPathComponent();	// возвращаем выделенный узел для текущего выбранныого узела
                if (selectedNode == null) {
                    return;																				// выходим, если узел не выбран		
                }
                DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selectedNode.getParent();						// возвращаем родительский узел для выделеного узела
                if (parent == null) {
                    return;																						// выходим, если узел родителя отсутствует
                }
                Object selectedO = selectedNode.getUserObject();						// получаем объект выделенного узла					
                Object parentO = parent.getUserObject();								// получаем объект родительского узла	

                if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(fm, "Вы действительно хотите удалить " + selectedO.toString(), "ПРЕДУПРЕЖДЕНИЕ: после удаления данные нельзя восстановить ", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE)) {
                    if (parentO.getClass() == String.class) {
                        parentO = ((DefaultMutableTreeNode) parent.getParent()).getUserObject(); //	проверяем, является ли объект, хранящийся в родительском узле строкой. Если да, то подымаемся на узел выше и извлекаем новый родительский объект
                    }
                    if (selectedNode != null && parent != null) // если узел выделен и для него существует родительский узел, то
                    {
                        int selectedIndex = parent.getIndex(selectedNode);					//	получаем индекс выделенного узла в родительском узле		   				
                        // TreePath tselPath =  fm.getTree().getSelectionPath();			//	запоминаем полный путь к выбранному узлу перед удалением.   ВАРИАНТ №1 дает полный путь.
                        fm.getModelTree().removeNodeFromParent(selectedNode);				//	1) удалаем узел и если объект узла является строка, возвращает узел без дочерних элементов
                        if (selectedO.getClass() == String.class) {
                            fm.getModelTree().insertNodeInto(new DefaultMutableTreeNode(selectedNode.toString()), parent, selectedIndex);
                        }
                        // 		fm.getTree().setSelectionPath(new TreePath(selectedNode));				// устанавливаем путь к выбранному узлу после вставки пустого узла типа "Персонал".  ВАРИАНТ №2 дает сокращенный путь при выводе.    			   	
                        // fm.getTree().setSelectionPath(tselPath);								// устанавливаем путь к выбранному узлу после вставки пустого узла типа "Персонал".  ВАРИАНТ №1 дает полный путь.
                        fm.getPanelSplitPane().getScrollPaneDescription().setViewportView(new JPanel());		//  2) а также ощищает панель, выводя вместо предыдущей пустую панель  new JPanel()
                    }

                    //-------------------------------------------удаляем объект Work  и делаем по нему все изменения в DataSet   ----------------------------//
                    if (cL == Work.class) {
                        delRow(((Trest) parentO).getWorks(),
                                fm.getDataSet().getTabWorks(),
                                fm.getDataSet().getTabTrestsWorks(),
                                ((Trest) parentO).getId(),
                                ((Work) selectedO).getId());
                    }
                    //-------------------------------------------удаляем объект Employee  и делаем по нему все изменения в DataSet   ----------------------------//
                    if (cL == Employee.class) {
                        delRow(((Work) parentO).getEmployees(),
                                fm.getDataSet().getTabEmployees(),
                                fm.getDataSet().getTabWorksEmployees(),
                                ((Work) parentO).getId(),
                                ((Employee) selectedO).getId());
                    }
                    //-------------------------------------------удаляем объект Employee  и делаем по нему все изменения в DataSet   ----------------------------//	               
                    if (selectedNode.getUserObject() == "Персонал") {
                        delRowAll(((Work) parentO).getEmployees(),
                                fm.getDataSet().getTabEmployees(),
                                fm.getDataSet().getTabWorksEmployees(),
                                ((Work) parentO).getId());
                    }
                    //-------------------------------------------удаляем объект Machine  и делаем по нему все изменения в DataSet -------------------------------//
                    if (cL == Machine.class) {
                        delRow(((Work) parentO).getMachines(),
                                fm.getDataSet().getTabMachines(),
                                fm.getDataSet().getTabWorksMachines(),
                                ((Work) parentO).getId(),
                                ((Machine) selectedO).getId());
                    }
                    //-------------------------------------------удаляем объект Machine  и делаем по нему все изменения в DataSet   -------------------------//	               
                    if (selectedNode.getUserObject() == "Оборудование") {
                        delRowAll(((Work) parentO).getMachines(),
                                fm.getDataSet().getTabMachines(),
                                fm.getDataSet().getTabWorksMachines(),
                                ((Work) parentO).getId());
                    }
                    //-------------------------------------------удаляем объект Order    и делаем по нему все изменения в DataSet -------------------------------//
                    if (cL == Order.class) {
                        delRow(((Work) parentO).getOrders(),
                                fm.getDataSet().getTabOrders(),
                                fm.getDataSet().getTabWorksOrders(),
                                ((Work) parentO).getId(),
                                ((Order) selectedO).getId());
                    }
                    //-------------------------------------------удаляем объект Order  и делаем по нему все изменения в DataSet   --------------------------//	               
                    if (selectedNode.getUserObject() == "Заказы") {
                        delRowAll(((Work) parentO).getOrders(),
                                fm.getDataSet().getTabOrders(),
                                fm.getDataSet().getTabWorksOrders(),
                                ((Work) parentO).getId());
                    }

                }
                if (cL == Employee.class || selectedNode.getUserObject() == "Персонал" || cL == Machine.class || selectedNode.getUserObject() == "Оборудование" || cL == Order.class || selectedNode.getUserObject() == "Заказы") {
                    work = ((Work) parentO);
                      																			//  Перерисовываем панель после добавления элемента
                }
                  fm.getPanelSplitPane().getPanelScheme().PschemeViewAdd(new PschemeModel(work, fm));    
            }

        }

        o = _o;														// объект, название полей и значения полей которого исследуем
        cL = _o.getClass();											// определяем класс (тип данных) объекта

        // if (cL==String.class) return null;							// Выход из программы в случае, если поле представляет название списка (например, персонал, оборудование, заказы)		
        int i = 0;
        mainPanel = new JPanel();									//	инициализируем панель
        mainPanel.setLayout(new GridBagLayout());					// помещаем в панель компановщик для работі с сеткой 
        Border borderClass;
        if (cL != String.class) {
            labelName = new JLabel[XmlRW.fieldsCl(cL).length];		// Объявляем массив длиной XmlRW.fieldsCl(cL).length для формирования названия полей объекта
            jFTF = new JFormattedTextField[XmlRW.fieldsCl(cL).length]; // Объявляем массив длиной XmlRW.fieldsCl(cL).length для формирования значений полей объекта

            /**
             * Вспомогательный класс, в котором определяются формат для полей
             * ввода
             */
            class IOrow {

                /**
                 * Задает формат ввода для поля id. Не продуман вопрос, как
                 * добавлять элемент
                 * новый!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                 */
                class IdFilter extends DocumentFilter {

                    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                    }

                    public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr) throws BadLocationException {
                    }

                    /**
                     * Возврат атрибута формата со значением, использующимся в
                     * поле ввода
                     *
                     * @param o	Значение поля field.get(o)
                     * @return	Возврат атрибута формата JFormattedTextField с
                     * заданным форматом в классе new IdFilter() методами
                     * insertString(...) и replace(...)
                     */
                    public IdFilter() {
                    }

                    public JFormattedTextField getIdFilter(Object o) {
                        JFormattedTextField idF = new JFormattedTextField(new InternationalFormatter(NumberFormat.getIntegerInstance()) {
                            protected DocumentFilter getDocumentFilter() {
                                return filter;
                            }
                            private DocumentFilter filter = new IdFilter();
                        }
                        );
                        idF.setValue(o);
                        return idF;
                    }
                }

                /**
                 * Задает формат ввода для поля даты
                 */
                class DateFilter extends DocumentFilter {

                    /**
                     * Возврат атрибута формата даты со значением,
                     * использующимся в поле ввода
                     *
                     * @param o	Значение поля field.get(o)
                     * @return	Возвращаем значение атрибута поля ввода,
                     * представленного заданным форматом в классе new
                     * DateFilter() методами insertString(...) и replace(...)
                     */
                    public JFormattedTextField getDateFilter(Object o) {
                        DateFormat format = DateFormat.getDateTimeInstance(); 			//getDateInstance(DateFormat.SHORT);   //getDateTimeInstance
                        format.setLenient(false);										// если true, то 31.13.2015 будет 31.01.2016   // если false, то 31.13.2015 будет 31.01.2015  
                        JFormattedTextField dateField = new JFormattedTextField(format);
                        dateField.setValue(o);
                        return dateField;
                    }
                }

                /**
                 * Задает формат ввода для поля Double
                 */
                class DoubleFilter extends DocumentFilter {

                    public DoubleFilter() {
                    }

                    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                        super.insertString(fb, offset, toStringBuffer(string), attr);
                    }

                    public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr) throws BadLocationException {
                        super.replace(fb, offset, length, toStringBuffer(string), attr);
                    }

                    public String toStringBuffer(String string) throws BadLocationException {
                        StringBuffer buffer = new StringBuffer(string);								// формируем новую строку
                        for (int i = buffer.length() - 1; i >= 0; i--) // проходим по всем символам строки
                        {
                            char ch = buffer.charAt(i);												// возвражаем из строки buffer символ под номером "i"	 	              
                            if (!Character.isDigit(ch) && ch != ',') {
                                buffer.deleteCharAt(i);
                            } 	// удаляем все символы, кроме цифр и запятой	
                            //if ( ch == ','  &&  toComma(string)>0) buffer.deleteCharAt(i);   		// Это проверять после доработки метода toComma(String string)
                        }
                        return buffer.toString();
                    }

                    /**
                     * Метод toComma(String string) не работает. Надо доработать
                     * или удалить. Доработать надо так. 1.Выяснить , где стоит
                     * курсор. Если перед ним(после него) запятая и начато
                     * удаление запятой, то это учесть, что запятой нет
                     *
                     * @param string
                     * @return
                     */
                    public int toComma(String string) {
                        int toComma = 0;
                        StringBuffer buffer = new StringBuffer(string);
                        for (int i = buffer.length() - 1; i >= 0; i--) {
                            char ch = buffer.charAt(i);
                            if (ch == ',') {
                                toComma++;
                            }
                        }
                        return toComma;
                    }

                    /**
                     * Возврат атрибута формата со значением, использующимся в
                     * поле ввода
                     *
                     * @param o	Значение поля field.get(o)
                     * @return	Возврат атрибута формата JFormattedTextField с
                     * заданным форматом в классе new IdFilter() методами
                     * insertString(...) и replace(...)
                     */
                    public JFormattedTextField getDoubleFilter(Object o) {
                        JFormattedTextField idF = new JFormattedTextField(new InternationalFormatter(NumberFormat.getInstance()) {
                            protected DocumentFilter getDocumentFilter() {
                                return filter;
                            }
                            private DocumentFilter filter = new DoubleFilter();
                        }
                        );
                        idF.setValue(o);
                        return idF;
                    }
                }

                /**
                 * Задает формат ввода для поля Integer
                 */
                class IntegerFilter extends DocumentFilter {

                    public IntegerFilter() {
                    }

                    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                        super.insertString(fb, offset, toStringBuffer(string), attr);
                    }

                    public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr) throws BadLocationException {
                        super.replace(fb, offset, length, toStringBuffer(string), attr);
                    }

                    public String toStringBuffer(String string) throws BadLocationException {
                        StringBuffer buffer = new StringBuffer(string);								// формируем новую строку
                        for (int i = buffer.length() - 1; i >= 0; i--) // проходим по всем символам строки
                        {
                            char ch = buffer.charAt(i);												// возвражаем из строки buffer символ под номером "i"	 	              
                            if (!Character.isDigit(ch)) {
                                buffer.deleteCharAt(i); 				 	// удаляем все символы, кроме цифр и запятой	
                            }
                        }
                        return buffer.toString();
                    }

                    /**
                     * Возврат атрибута формата со значением, использующимся в
                     * поле ввода
                     *
                     * @param o	Значение поля field.get(o)
                     * @return	Возврат атрибута формата JFormattedTextField с
                     * заданным форматом в классе new IdFilter() методами
                     * insertString(...) и replace(...)
                     */
                    public JFormattedTextField getIntegerFilter(Object o) {
                        JFormattedTextField idF = new JFormattedTextField(new InternationalFormatter(NumberFormat.getIntegerInstance()) {
                            protected DocumentFilter getDocumentFilter() {
                                return filter;
                            }
                            private DocumentFilter filter = new IntegerFilter();
                        }
                        );
                        idF.setValue(o);
                        return idF;
                    }
                }

                /**
                 * Задает формат ввода для поля String
                 */
                class StringFilter extends DocumentFilter {

                    public StringFilter() {
                    }

                    /**
                     * Возврат атрибута формата со значением, использующимся в
                     * поле ввода
                     *
                     * @param o	Значение поля field.get(o)
                     * @return	Возврат атрибута формата JFormattedTextField с
                     * заданным форматом в классе new IdFilter() методами
                     * insertString(...) и replace(...)
                     */
                    public JFormattedTextField getStringFilter(Object o) {
                        JFormattedTextField idF = new JFormattedTextField();
                        idF.setValue(o);
                        return idF;
                    }
                }

                /**
                 * Вспомогательный метод. В зависимости от типа поля формирует
                 * объект JFormattedTextField. Применен для сокращения кода.
                 *
                 * @param field	объект JFormattedTextField
                 * @return
                 */
                public JFormattedTextField setJFormattedTextField(Field field, Object o) {

                    try {
                        if (field.getName() == "id") {
                            return new IOrow().new IdFilter().getIdFilter(field.get(o));
                        }
                        if (field.get(o).getClass() == Date.class) {
                            return new IOrow().new DateFilter().getDateFilter(field.get(o));
                        }
                        if (field.get(o).getClass() == Double.class) {
                            return new IOrow().new DoubleFilter().getDoubleFilter(field.get(o));
                        }
                        if (field.get(o).getClass() == Integer.class) {
                            return new IOrow().new IntegerFilter().getIntegerFilter(field.get(o));
                        }
                        if (field.get(o).getClass() == String.class) {
                            return new IOrow().new StringFilter().getStringFilter(field.get(o));
                        }
                    } catch (IllegalArgumentException exp) {
                        exp.printStackTrace();
                    } catch (IllegalAccessException exp) {
                        exp.printStackTrace();
                    }
                    return null;
                }
            }

            borderClass = BorderFactory.createTitledBorder(cL.getSimpleName());	// определяем заголовок рамки панели
            mainPanel.setBorder(borderClass);													// задаем тип рамки для окаемки панели 
            Border borderName = BorderFactory.createEtchedBorder();						// задаем тип рамки для окаемки название поля класса cL и значения поля  

            for (Field field : XmlRW.fieldsCl(cL)) // Проходим по полям класса cL для вывода названия поля и его значения
            {
                field.setAccessible(true);
                labelName[i] = new JLabel(field.getName().toString());					// заполняем метку названием имени поля			
                jFTF[i] = new IOrow().setJFormattedTextField(field, o);				// заполняем метку значением имени поля		
                labelName[i].setBorder(borderName); 										// задаем рамку для имени поля								
                mainPanel.add(labelName[i], new PanelFragment().new GBL(0, i).setAnchor(GridBagConstraints.WEST).setWeightXY(1.0, 0.0).setFill(GridBagConstraints.BOTH));   // Добавляем в панель название поля
                if (jFTF[i] != null) {
                    mainPanel.add(jFTF[i], new PanelFragment().new GBL(1, i).setAnchor(GridBagConstraints.WEST).setWeightXY(20.0, 0.0).setFill(GridBagConstraints.BOTH));   // Добавляем в панель значение поля
                } else {   // else нужен только для того, чтобы выводились списки. Является не обязательным. Можно убрать. Если его убрать, то поля , которые относятся к спискам будут пустыми (например не будет выведен список машин) 
                    jFTF[i] = new JFormattedTextField();
                    try {
                        jFTF[i].setValue(field.get(o));
                    } catch (IllegalArgumentException exp) {
                        exp.printStackTrace();
                    } catch (IllegalAccessException exp) {
                        exp.printStackTrace();
                    }
                    mainPanel.add(jFTF[i], new PanelFragment().new GBL(1, i).setAnchor(GridBagConstraints.WEST).setWeightXY(20.0, 0.0).setFill(GridBagConstraints.BOTH));   // Добавляем в панель значение поля
                }
                try {
                    field.set(o, jFTF[i].getValue());
                } catch (IllegalArgumentException | IllegalAccessException exp) {
                    exp.printStackTrace();
                } 										// сохраняем изменения, внесенные в текстовые поля, в объекте о класcа  this 
                field.setAccessible(false);
                i++;
            }

            JButton saveChangesButton = new JButton("Сохранить изменения");    // Кнопка для сохранения изменений после перехода в другой узел дерева.
            saveChangesButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    saveField(fm.getDataSet());
//                    fm.getPanelSplitPane().getPanelScheme().PschemeViewAdd(new PschemeModel(work, fm));		//	перерисовываем панель со схемой производства 
                }
            });

            mainPanel.add(saveChangesButton, new PanelFragment().new GBL(0, i, 2, 1).setFill(GridBagConstraints.HORIZONTAL).setAnchor(GridBagConstraints.NORTH).setWeightXY(1.0, 1.0)); // Добавляем в панель с кнопку "Сохранить изменения"

        } else {
            borderClass = BorderFactory.createTitledBorder(o.toString());	// определяем заголовок рамки панели
        }

        mainPanel.setBorder(borderClass);													// задаем тип рамки для окаемки панели 
        JButton createChangesButton = new JButton("Создать новый");    				// Кнопка для создания нового объекта, который показан в диалоговой панели  и узла дерева.
        createChangesButton.addActionListener(new CreateActionListener());
        JButton deleteChangesButton = new JButton("Удалить выбранный");    			// Кнопка для создания нового объекта, который показан в диалоговой панели  и узла дерева.
        deleteChangesButton.addActionListener(new DeleteActionListener());
        mainPanel.add(createChangesButton, new PanelFragment().new GBL(0, ++i, 2, 1).setFill(GridBagConstraints.HORIZONTAL).setAnchor(GridBagConstraints.NORTH).setWeightXY(1.0, 0.0)); // Добавляем в панель с кнопку "Сохранить изменения"		  
        mainPanel.add(deleteChangesButton, new PanelFragment().new GBL(0, ++i, 2, 1).setFill(GridBagConstraints.HORIZONTAL).setAnchor(GridBagConstraints.NORTH).setWeightXY(1.0, 0.0)); // Добавляем в панель с кнопку "Сохранить изменения"			  
        return mainPanel; // возвращаем сформированную диалоговую панель		  
    }

    /**
     * Осуществляем запись изменненных значений текстовых полей в оперативную
     * память. Объект трест изменяется.
     */
    public static void saveField(DataSet dataSet) {
        for (Field field : XmlRW.fieldsCl(cL)) // Проходим по полям класса cL для вывода названия поля и его значения
        {
            field.setAccessible(true);
            String a = "";
            for (int i = 0; i < XmlRW.fieldsCl(cL).length; i++) // проходи по всем полям объекта для сохранения изменений его значений, введенных через текстовые поля
            {
                if (field.getName() == labelName[i].getText()) {
                    try {	  // Запись в поле объекта

                        field.set(o, XmlRW.parseValue(field, jFTF[i].getValue(), "XmlRW"));

                    } catch (NumberFormatException exp1) {
                        exp1.printStackTrace();
                    } catch (IllegalArgumentException exp1) {
                        exp1.printStackTrace();
                    } catch (IllegalAccessException exp1) {
                        exp1.printStackTrace();
                    }
                }
            }
            field.setAccessible(false);
        }
        XmlRW.FieldToField_ifClass(dataSet, o);
    }

}
