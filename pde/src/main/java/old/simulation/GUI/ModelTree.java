/**
 *
 */
package old.simulation.GUI;

import old.entityProduction.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

/**
 * @author Oleg
 *
 */
public class ModelTree {

    /**
     *
     */
    public ModelTree() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Создает модель дерева по умолчанию для заданного треста: Трест ->
     * Производство (Оборудование, Персонал)
     *
     * @param trest	Трест, для которого строится модель дерева
     * @return	Модель дерева
     */

    static public DefaultTreeModel makeSampleTree2(Trest trest) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(trest);						// Создаем корневой узел -Трест
        for (Work w : trest.getWorks()) {
            DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(w);						// Создаем узел -Трест->Производство
            root.add(node1);
            DefaultMutableTreeNode wm = new DefaultMutableTreeNode("Оборудование");			// Создаем узел -Трест->Производство->Оборудование
            DefaultMutableTreeNode we = new DefaultMutableTreeNode("Персонал");				// Создаем узел -Трест->Производство->Персонал
            DefaultMutableTreeNode wo = new DefaultMutableTreeNode("Заказы");					// Создаем узел -Трест->Производство->Персонал

            node1.add(wm);
            wm.setAllowsChildren(true);										// Указываем, допустимо ли создание дочерник узлов. false - недопустимо
            node1.add(we);
            we.setAllowsChildren(true);
            node1.add(wo);
            wo.setAllowsChildren(true);
            for (Machine m : w.getMachines()) {
                wm.add(getNode(m, false));				// Создаем узел -Трест->Производство->Оборудование->Название машины
            }
            for (Employee e : w.getEmployees()) {
                we.add(getNode(e, false));				// Создаем узел -Трест->Производство->Оборудование->Имя сотрудника
            }
            for (Order o : w.getOrders()) {
                wo.add(getNode(o, false));				// Создаем узел -Трест->Производство->Оборудование->Название заказа		      
            }
        }
        DefaultTreeModel model = new DefaultTreeModel(root);
        model.setAsksAllowsChildren(true);
        return model;
    }

    /**
     * Инициализует узел с указанным именем, который является папкой или листом
     *
     * @param s	Имя узла
     * @param flag	Указатель на то, является узел папкой или листом ( false -
     * лист)
     * @return	Созданный узел со свойством папка/лист
     */
    static public DefaultMutableTreeNode getNode(String s, Boolean flag) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(s);
        node.setAllowsChildren(flag);					// Указываем, допустимо ли создание дочерник узлов. false - недопустимо
        return node;
    }

    /**
     * Инициализует узел с указанным именем, который является папкой или листом.
     * Используется для метода makeSampleTree2.
     *
     * @param s	Имя узла
     * @param flag	Указатель на то, является узел папкой или листом ( false -
     * лист)
     * @return	Созданный узел со свойством папка/лист
     */
    static public DefaultMutableTreeNode getNode(Object s, Boolean flag) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(s);
        node.setAllowsChildren(flag);
        return node;
    }
}

/**
 * Класс для отображения представления для модели дерева ( class ModelTree).
 * Решаем задачи:	метод getTreeCellRendererComponent(...) обеспечивает
 * оформление узла в соответствующем виде
 *
 */
class MyRenderer extends DefaultTreeCellRenderer {

    public MyRenderer() {
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        /* Обслуживание  метода getTreeCellRendererComponent */
        class ForMakeRender {

            /**
             * Имеет доступ к полям метода getTreeCellRendererComponent, создает
             * для узла иконку, путь к файлу картинки которой s
             *
             * @param s	Путь к файлу
             */
            public void makeIcon(String s) {
                ImageIcon ic = new ImageIcon(s);
                setIcon(ic);
            }
        }
        ForMakeRender f = new ForMakeRender();																									// Создаем обслуживающий объект (Объект внутреннего класса)
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);													// Созвращаем компонент, отображающий узел дерева

        if (Work.class == (Class) ((DefaultMutableTreeNode) value).getUserObject().getClass()) {
            f.makeIcon("Image/Icon/iWork/w01.png");		// Создаем вид отображения изображения для узла объекта Work.class
        }
        if (Machine.class == (Class) ((DefaultMutableTreeNode) value).getUserObject().getClass()) {
            f.makeIcon("Image/Icon/iMachine/m03.png");  // Создаем вид отображения изображения для узла объекта Machine.class    
        }
        if (Employee.class == (Class) ((DefaultMutableTreeNode) value).getUserObject().getClass()) {
            f.makeIcon("Image/Icon/iEmployee/e01.png"); // Создаем вид отображения изображения для узла объекта Employee.class   
        }
        if (Order.class == (Class) ((DefaultMutableTreeNode) value).getUserObject().getClass()) {
            f.makeIcon("Image/Icon/iOrder/o02.png"); 	// Создаем вид отображения изображения для узла объекта Order.class       
        }
        if (Trest.class == (Class) ((DefaultMutableTreeNode) value).getUserObject().getClass()) {
            f.makeIcon("Image/Icon/iTrest/t01.png"); 	// Создаем вид отображения изображения для узла объекта Trest.class          
        }
        return this;
    }
}
