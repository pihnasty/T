package old.simulation.GUI;


import old.database.DataSet;
import old.database.tabDataSet.RowSubject_labour;
import old.database.tabDataSet.RowUnit;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.EventObject;


public class PTable extends JTable
{

	public PModelTable getDm()				{	return dm;		}
	public void setDm(PModelTable _dm)		{	this.dm = _dm;	}
	

	
	private int kWidthMin = 10;				// ����������� �������� ����� ������ � �������� � ����� ������ � �������� ��� ������������ ��� ������ ��������. � ������ ������� ��� ������� ���� ����������� ����� ������ ��������  � �������� � ��������������� � ������������ � ������ ������������� � �������
	private int kWidthMax = 20;				// ����������� �������� ����� ������ � �������� � ����� ������ � �������� ��� ������������ ��� ������ ��������. ���������� .... kWidthMin
	private int		result;
	
	private PModelTable dm;
	private DataSet ds;
	
	public PTable()	{	}	

	public PTable(PModelTable dm, DataSet _ds)
	{
		super(dm);
		this.dm=dm;	this.ds=_ds;
		
		TableColumn [] tabcolumns = new TableColumn [dm.getColWidth().length];
		for ( int i =0; i< dm.getColWidth().length; i++) 						// ������������� max � min ������ ��������
		{
			tabcolumns [i] = this.getColumnModel().getColumn(i);		
			tabcolumns [i].setWidth(dm.getColWidth()[i]);
			tabcolumns [i].setResizable(true);
			tabcolumns [i].setMinWidth(kWidthMin*dm.getColWidth()[i]);
			tabcolumns [i].setMaxWidth(kWidthMax*dm.getColWidth()[i]);	
		}
		    

		int i = 0;
		for (Field fd: dm.getFields()) 
		{
			setDefaultRenderer(	fd.getClass(), 	new PTableCellRenderer( dm.getjFTF()[0][i]));
			setDefaultEditor(	fd.getClass(),  new PTableCellEditor(dm.getjFTF()[0][i]));
			i++;
		} 
		

		if (this.getRowCount()>0)
		{
    		 final JComboBox moonCombo = new JComboBox();
    		 

    		 Enumeration<TableColumn> columns=this.getColumnModel().getColumns();
    		 TableColumn column = null;
    		 
    		 while (columns.hasMoreElements())  { 
    			 column=columns.nextElement();
    			 if (column.getHeaderValue().toString()=="nameSubject_labour")  break;
    		 }
    		 if(ds!=null) for (RowSubject_labour sl	:	ds.getTabSubject_labours()) moonCombo.addItem(sl);
    		 moonCombo.addActionListener( new ActionListener() 	{
    			 									public void actionPerformed(ActionEvent event)
    			 									{
    			 										RowSubject_labour rsl = (RowSubject_labour) moonCombo.getSelectedObjects()[0];
    			 										result =   (int)  getValueAt(getSelectedRows()[0], 0);	
    			 										for ( int i=0; i<ds.getTabLineSubject_labours().size(); i++ ) 
    			 											if  ( ds.getTabLineSubject_labours().get(i).getId()== result ) ds.getTabLineSubject_labours().get(i).setId2(rsl.getId());
    			 									}
    		 										});
    	     if (column!=null)	column.setCellEditor(new DefaultCellEditor(moonCombo));	     
		}
		if (this.getRowCount()>0)
		{
    		 final JComboBox moonCombo2 = new JComboBox();
    		 

    		 Enumeration<TableColumn> columns=this.getColumnModel().getColumns();
    		 TableColumn column = null;
    		 
    		 while (columns.hasMoreElements())  { 
    			 column=columns.nextElement();
    			 if (column.getHeaderValue().toString()=="unit")  break;
    		 }
    		 if(ds!=null) for (RowUnit sl	:	ds.getTabUnits()) moonCombo2.addItem(sl);
    		 moonCombo2.addActionListener( new ActionListener() 	{
    			 									public void actionPerformed(ActionEvent event)
    			 									{
    			 										RowUnit rsl = (RowUnit) moonCombo2.getSelectedObjects()[0];
    			 										result =   (int)  getValueAt(getSelectedRows()[0], 0);	
    			 										for ( int i=0; i<ds.getTabLinesUnits().size(); i++ ) 
    			 											if  ( ds.getTabLinesUnits().get(i).getId()== result ) ds.getTabLinesUnits().get(i).setId2(rsl.getId());
    			 									}
    		 										});
    	     if (column!=null)	column.setCellEditor(new DefaultCellEditor(moonCombo2));	     
		}
		
	}
	
	
	

}


class PTableCellRenderer implements TableCellRenderer
{  
	public PTableCellRenderer( JFormattedTextField jftf) {	this.jftf	=	jftf;}
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)  { 	return jftf;	}
	private JFormattedTextField jftf;
}


class PTableCellEditor extends DefaultCellEditor  implements TableCellEditor
{  
	private JFormattedTextField jFTF;
	PTableCellEditor( JFormattedTextField jFTF)
	{
		 
		 super(jFTF);
		 jFTF=(JFormattedTextField)getComponent();
		this.jFTF=jFTF;

	}
  	@Override
   public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
   {  	  
	  jFTF.setValue(value);	
      return jFTF;
   }

   public boolean shouldSelectCell(EventObject anEvent)		    {   return true;				}
   public void cancelCellEditing()  							{   super.cancelCellEditing();  }

   public boolean stopCellEditing()   
   {
	   JFormattedTextField ftf = (JFormattedTextField)getComponent();
	   if (ftf.isEditValid()) try {  ftf.commitEdit();   } catch (java.text.ParseException exc) { }
	   return super.stopCellEditing(); 
   }
   public Object getCellEditorValue()   						{	return jFTF.getValue();   	}
}









