package old.simulation.GUI;

import old._util._Date;
import old.database.XmlRW;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.InternationalFormatter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class PModelTable  <cL> extends DefaultTableModel 		// AbstractTableModel
{
	public ArrayList<cL> getTab()						{	return tab;				}
	public void setTab(ArrayList<cL> tab)				{	this.tab = tab;			}
	public ArrayList<Integer> getChangeRowS()			{	return changeRowS;		}
	public void setChangeRowS(ArrayList<Integer> changeRowS)	{		this.changeRowS = changeRowS;	}
	public JFormattedTextField[][] getjFTF()			{	return jFTF;			}
	public void setjFTF(JFormattedTextField[][] jFTF)	{	this.jFTF = jFTF;		}
	public Field[] getFields() 							{	return fields;			}
	public void setFields(Field[] fields) 				{	this.fields = fields;	}


	private ArrayList<cL> tab;
 
	private int rowCount;	
	private int colCount;	
	private Object [][] valueAt; 
	private int [] strLength;  
	private Map <String, String> colNameMap;	

	private	Field [] fields;
	private	JFormattedTextField [][] jFTF;
	private ArrayList<Integer> changeRowS;		 // ������ ������ ����� � �����������
	
	public PModelTable (ArrayList<cL> _tab, Class cL) 
	{	
		tab=_tab;  
		fields = XmlRW.fieldsCl(cL);		//	���������� ����	����������� �������  cL
		rowCount = tab.size();						//	���������� ���������� ����� � �������
		colCount = fields.length;					//	���������� ���������� �������� � �������
		valueAt = new Object [rowCount][colCount];  //  �������������� ������ � ������� �����
		colNameMap = new HashMap <String, String>();//	��������� Map � ������ = ��� ���� Class cL � ��������� = ������������ ����� ������� � ����������� �������
		
		changeRowS = new ArrayList<Integer> ();
 
		
		jFTF  = new JFormattedTextField [rowCount][colCount];
		
		/*���������������� �����: ���������� ������ ������� ������� */
		strLength = new int [colCount];				// ������, ������� �������� ������ �������	
		boolean flag=true;							// ����, ������� ��������� ������� �������� ����� ����� ������� (flag=true), � ����� �������� ����� ������� (flag=false)
		
		for (cL r : tab) 
		{		

			strLength = new int [colCount];			// ���������� ����������� ������� ��� �������� ������ � ������ ������ �������
			int i=0;
			if (flag) 
			{ 
				for (Field fd: fields) 
				{ 
					strLength[i]= fd.getName().length(); 
					colNameMap.put(fd.getName(),NameFieldToNameColumn(fd.getName(),""));
					i++;
				}     // ��������� ����� ������ ��������� ������� �������, ������� ���������� ��� ��������� + �������� �-��������  
				flag=false; 
			}	
			i=0;
 
			for (Field fd: fields) 	    			
			{
				Object fd_get = new Object();
			 	fd.setAccessible(true);			
				try {	fd_get=fd.get(r);	} catch (IllegalArgumentException exp) { exp.printStackTrace(); } catch (IllegalAccessException exp)	{	exp.printStackTrace();   }
			 	if (fd_get!=null) 
			 		if (fd_get.toString().length() >strLength[i] )	
			 		{
			 			if ( fd.getType() == Date.class )	strLength[i]= _Date.toStringForDate((Date)fd_get).length();
			 				else strLength[i]=fd_get.toString().length(); // 		��������� ����� ������ �������� �������, ������� ���������� ��� ��������� + �������� �-��������  
			 		}
			 	i++;
				fd.setAccessible(false); 
			}
		}

		/*�������� �����: ���� ������ ������� �������, ��������� ������� */		
		int j=0;
		flag=true;
		for (cL r : tab) 
		{		
			int i=0;

			for (Field fd: fields) 	    			
			{
				Object fd_get = new Object();
			 	fd.setAccessible(true);		
				try {  fd_get=fd.get(r);  } catch (IllegalArgumentException exp) { exp.printStackTrace(); } catch (IllegalAccessException exp)	{	exp.printStackTrace();   }

				jFTF [j][i] =  new IOrow2().setJFormattedTextField(fd, r);										// ��� ���� fd ���������� ������ ���������� ���� ����� ������ 	
				
		  					
				
					jFTF [j][i] .setValue(fd_get); 					// �����, ����� ������ ������ ���������� jFTF , ������� � ��������� jFTF ��������������� ��� ������ �� )

					i++; 
				fd.setAccessible(false); 
			}
			j++;
			
		}
		

		
	}

	@Override
    public int getRowCount()   				{	return rowCount;   		}
	@Override
    public int getColumnCount() 			{	return colCount;    	}

    public String getColumnName(int c)  	{ 	return  NameFieldToNameColumn( fields[c].getName(), "");    }	
/*
  	@Override
    public Class getColumnClass(int column) 
    { 
       fields[column].setAccessible(true);	
       try   {   return fields[column].get(tab.get(0)).getClass();} catch (IllegalArgumentException exp)  {  exp.printStackTrace();   } catch (IllegalAccessException exp)   {   exp.printStackTrace();    }
   	   fields[column].setAccessible(true);	
       return Object.class;
    }
    */

	
	@Override  
	public Object getValueAt(int rowIndex, int columnIndex) 				
	{	
		if ( jFTF [rowIndex][columnIndex].getValue().getClass() == Date.class )	return _Date.toStringForDate( (Date) jFTF [rowIndex][columnIndex].getValue());
		return   jFTF [rowIndex][columnIndex].getValue(); 
	}
	

	/**
	 * ������� �������� � ������ ������� � ������� ������� ������  
	 */
	@Override
	public void setValueAt(Object obj, int rowIndex, int columnIndex)  		
	{		
		 
			fields[columnIndex].setAccessible(true);
			jFTF [rowIndex][columnIndex].setValue(XmlRW.parseValue (fields[columnIndex], obj, "PModelTable") );			//	���������� �������� �� ������� � 	jFTF

			changeRow (rowIndex);
	        fields[columnIndex].setAccessible(false);	
     
	}
	
	public  void changeRow (Integer rowIndex)
	{
		if (! changeRowS.contains(rowIndex)) changeRowS.add(rowIndex);
		else System.out.println("PModelTable: ����������");
	}
	
	
	public int [] getColWidth() 											{	return strLength; 							}
	
	/**
	 * ������ � ������������ ��� getNameField	(�������� ���� ������) �������� ������� � �������
	 * @param getNameField	�������� ���� ������
	 * @param language		���� �������������������
	 * @return				�������� ������� � �������
	 */
	public String NameFieldToNameColumn(String getNameField, String language)
	{
		if (getNameField == "description") return "��������";
		return getNameField;
	}
	

	@Override	
	public boolean isCellEditable(int r, int c)
	{
    	ArrayList<String> a = new ArrayList<String>(colNameMap.keySet());    	
		return a.get(c)!="id";
	}

	

}


/**
* ��������������� �����, � ������� ������������ ������ ��� ����� �����
*/
 class IOrow2
{
	 /**
	  * ������ ������ ����� ��� ���� id. �� �������� ������, ��� ��������� ������� �����!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 
	  */
	 class IdFilter extends DocumentFilter
	 {
	    public void insertString(FilterBypass fb, int offset,String string, AttributeSet attr)throws BadLocationException {}  	
	    public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr)  throws BadLocationException { }
	    /**
	     * ������� �������� ������� �� ���������, �������������� � ���� �����
	     * @param o		�������� ���� field.get(o)
	     * @return		������� �������� �������  JFormattedTextField � �������� �������� � ������ new IdFilter() �������� insertString(...) �  replace(...)
	     */
	   public IdFilter()	{}
	   public JFormattedTextField getIdFilter(Object o)
	   {
		   JFormattedTextField idF = new JFormattedTextField ( new InternationalFormatter( NumberFormat.getIntegerInstance()) 	
                                                   				{   
	    																protected DocumentFilter getDocumentFilter() {return filter;}													
                                                   					private DocumentFilter filter  = new IdFilter();
                                                   				}
	    													  );
		   idF.setValue( o);
	   
		   return idF;
	    } 
	 }	
	 /**
	  * ������ ������ ����� ��� ���� ����
	  */
	 class DateFilter extends DocumentFilter
	 {
	    /**
	     * ������� �������� ������� ���� �� ���������, �������������� � ���� �����
	     * @param o		�������� ���� field.get(o)
	     * @return		���������� �������� �������� ���� �����, ��������������� �������� �������� � ������ new DateFilter() �������� insertString(...) �  replace(...)
	     */
	    public JFormattedTextField getDateFilter(Object o)
	    {
	        DateFormat format = DateFormat.getDateTimeInstance(); 			//getDateInstance(DateFormat.SHORT);   //getDateTimeInstance
	        format.setLenient(false);										// ���� true, �� 31.13.2015 ����� 31.01.2016   // ���� false, �� 31.13.2015 ����� 31.01.2015  
	        JFormattedTextField dateField = new JFormattedTextField( format);		        
		    dateField.setValue(o);	
	    	return dateField;
	    }
	 }			 
	 /**
	  * ������ ������ ����� ��� ���� Double
	  */
	 class DoubleFilter extends DocumentFilter
	 {
		 public DoubleFilter () {}
		 public void insertString(FilterBypass fb, int offset,String string, AttributeSet attr)throws BadLocationException 
		 {    	
	        super.insertString(fb, offset, toStringBuffer(string), attr);
	     }  	
	    public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr)  throws BadLocationException 
	    {
	        super.replace(fb, offset, length, toStringBuffer(string), attr);
	     }
	    public String toStringBuffer(String string) throws BadLocationException
	    {
	    	StringBuffer buffer = new StringBuffer(string);								// ��������� ����� ������
	        for (int i = buffer.length() - 1; i >= 0; i--)								// �������� �� ���� �������� ������
	        {
	           char ch = buffer.charAt(i);												// ���������� �� ������ buffer ������ ��� ������� "i"	 	              
	           if (!Character.isDigit(ch) && ch != ','   ) { buffer.deleteCharAt(i); } 	// ������� ��� �������, ����� ���� � �������	
	           //if ( ch == ','  &&  toComma(string)>0) buffer.deleteCharAt(i);   		// ��� ��������� ����� ��������� ������ toComma(String string)
	        }
	        return buffer.toString();		           
	    }
	    /**
	     * ����� toComma(String string) �� ��������. ���� ���������� ��� �������.
	     * ���������� ���� ���. 1.�������� , ��� ����� ������. ���� ����� ���(����� ����) ������� � ������ �������� �������, �� ��� ������, ��� ������� ���
	     * @param string
	     * @return
	     */
	    public int toComma(String string)
	    {
	    	int toComma =0;
	    	StringBuffer buffer = new StringBuffer(string);				
	        for (int i = buffer.length() - 1; i >= 0; i--)				
	        {
	           char ch = buffer.charAt(i);									 	              
	           if ( ch == ',' ) toComma++;	
	        }
	        return toComma;	
	    }
	    /**
	     * ������� �������� ������� �� ���������, �������������� � ���� �����
	     * @param o		�������� ���� field.get(o)
	     * @return		������� �������� �������  JFormattedTextField � �������� �������� � ������ new IdFilter() �������� insertString(...) �  replace(...)
	     */
	    public JFormattedTextField getDoubleFilter(Object o)
	    {
			 JFormattedTextField idF = new JFormattedTextField ( new InternationalFormatter( NumberFormat.getInstance()) 	
                                                   				{   
       																protected DocumentFilter getDocumentFilter() {return filter;}													
                                                   					private DocumentFilter filter  = new DoubleFilter();
                                                   				}
       													  );
	        idF.setValue(o);  
	    	return idF;
	    } 		    
	 }		 
	 /**
	  * ������ ������ ����� ��� ���� Integer
	  */
	 class IntegerFilter extends DocumentFilter
	 {
		 public IntegerFilter () {}
		 public void insertString(FilterBypass fb, int offset,String string, AttributeSet attr)throws BadLocationException 
		 {    	
	        super.insertString(fb, offset, toStringBuffer(string), attr);
	     }  	
	     public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr)  throws BadLocationException
	     {
	        super.replace(fb, offset, length, toStringBuffer(string), attr);
	     }
	     public String toStringBuffer(String string) throws BadLocationException
	     {
	    	StringBuffer buffer = new StringBuffer(string);								// ��������� ����� ������
	        for (int i = buffer.length() - 1; i >= 0; i--)								// �������� �� ���� �������� ������
	        {
	           char ch = buffer.charAt(i);												// ���������� �� ������ buffer ������ ��� ������� "i"	 	              
	           if (!Character.isDigit(ch) )  buffer.deleteCharAt(i); 				 	// ������� ��� �������, ����� ���� � �������	
	        }
	        return buffer.toString();		           
	     }

	    /**
	     * ������� �������� ������� �� ���������, �������������� � ���� �����
	     * @param o		�������� ���� field.get(o)
	     * @return		������� �������� �������  JFormattedTextField � �������� �������� � ������ new IdFilter() �������� insertString(...) �  replace(...)
	     */
	    public JFormattedTextField getIntegerFilter(Object o)
	    {
	    	JFormattedTextField idF = new JFormattedTextField ( new InternationalFormatter( NumberFormat.getIntegerInstance()) 	
                                                   				{   
	    																protected DocumentFilter getDocumentFilter() {return filter;}													
                                                   					private DocumentFilter filter  = new IntegerFilter();
                                                   				}
	    													  );
	        idF.setValue(o);  
	    	return idF;
	    } 		    
	 }	
	 /**
	  * ������ ������ ����� ��� ���� String
	  */
	 class StringFilter extends DocumentFilter
	 {
		 public StringFilter () {}
	    /**
	     * ������� �������� ������� �� ���������, �������������� � ���� �����
	     * @param o		�������� ���� field.get(o)
	     * @return		������� �������� �������  JFormattedTextField � �������� �������� � ������ new IdFilter() �������� insertString(...) �  replace(...)
	     */
	    public JFormattedTextField getStringFilter(Object o)
	    {
	    	JFormattedTextField idF = new JFormattedTextField();
	        idF.setValue(o);  
	    	return idF;
	    } 		    
	 }				 
	 /**
	  * ��������������� �����. � ����������� �� ���� ���� ��������� ������ JFormattedTextField. �������� ��� ���������� ����.
	  * @param field	������ JFormattedTextField
	  * @return
	  */
	 public JFormattedTextField setJFormattedTextField(Field field,Object o) {
		 try {
			 field.setAccessible(true);		
 
			 //System.out.println("PModelTable	field.get(o).getClass()="+field.get(o));	
			 if (field.getName()=="id")   { 	return new IOrow2().new IdFilter()		.getIdFilter(field.get(o));	 }
			 if (field.get(o).getClass()==Double.class) 	return new IOrow2().new DoubleFilter()	.getDoubleFilter(field.get(o));	
			 if (field.get(o).getClass()==Integer.class) 	return new IOrow2().new IntegerFilter()	.getIntegerFilter(field.get(o));	
			 if (field.get(o).getClass()==String.class) 	return new IOrow2().new StringFilter()	.getStringFilter(field.get(o));	
			 field.setAccessible(false);
		 } catch (IllegalArgumentException exp) {	exp.printStackTrace();	 } catch (IllegalAccessException exp)  {	 exp.printStackTrace();  }
		 return new JFormattedTextField();
   }
}
		




