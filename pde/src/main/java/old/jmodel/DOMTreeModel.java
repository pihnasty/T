package old.jmodel;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
This tree model describes the tree structure of a Java object. Children are the objects that are stored in instance variables.
*/
public class DOMTreeModel implements TreeModel
{ 
    public DOMTreeModel(Document aDoc)  {  doc = aDoc; }
/**
 * 	��������� �������� ������� ������    
 */
    public Object getRoot()	{   return doc.getDocumentElement();	}

    /**
     * @param parent ���������� ���������� ����� ���������. ���������� 0, ���� ���� �������� ������, ��� ���� �� �� ����� �����. �������� ������ ���� �����, ����� ���������� �� ����� ��������� ������.        
     * @return  
     */
    public int getChildCount(Object parent) {  
       Node node = (Node)parent;
       NodeList list = node.getChildNodes();
       return list.getLength();
    }

    /**
     * Returns the child of <code>parent</code> at index <code>index</code>
     * in the parent's
     * child array.  <code>parent</code> must be a node previously obtained
     * from this data source. This should not return <code>null</code>
     * if <code>index</code>
     * is a valid index for <code>parent</code> (that is <code>index >= 0 &&
     * index < getChildCount(parent</code>)).
     *
     * @param   parent  a node in the tree, obtained from this data source
     * @return  the child of <code>parent</code> at index <code>index</code>
     */    
    public Object getChild(Object parent, int index)  {  
       Node node = (Node)parent;
       NodeList list = node.getChildNodes();
       return list.item(index);
    }
    
    public int getIndexOfChild(Object parent, Object child)
    {  
       Node node = (Node)parent;
       NodeList list = node.getChildNodes();
       for (int i = 0; i < list.getLength(); i++)  if (getChild(node, i) == child)    return i;
       return -1;
    }
    
    public boolean isLeaf(Object node)
    {  
       return getChildCount(node) == 0;
    }
    
    public void valueForPathChanged(TreePath path,  Object newValue) {}
    
    public void addTreeModelListener(TreeModelListener l) {}
    
    public void removeTreeModelListener(TreeModelListener l) {}
    
    private Document doc;
}

