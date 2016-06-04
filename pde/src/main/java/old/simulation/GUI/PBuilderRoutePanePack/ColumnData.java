package old.simulation.GUI.PBuilderRoutePanePack;

import java.util.HashMap;


/**
 * Определяет содержание и тип данных заголовков таблицы
 */
public class ColumnData {
	private String columnName;
	private Class  columnClass;
	private int width;    	
	public int getWidth()										{	return width;					}
	public void setWidth(int width)								{	this.width = width;				}
	public Class getColumnClass()								{	return columnClass;				}
	public void setColumnClass(Class columnClass)				{	this.columnClass = columnClass;	}
	public String getColumnName()							    {   return columnName;				}
	public void setColumnName(String columnName)				{   this.columnName = columnName;   }
	public ColumnData(String columnName, Class  columnClass, int width)	{ 
		this.columnName=columnName; this.columnClass = columnClass; this.width= width;
	}
	
	public static HashMap <Integer, ColumnData > initColumnHeaderLineroute () {    	
        HashMap <Integer, ColumnData >  columnHeaderLineroute = new HashMap <Integer, ColumnData >();
	 	columnHeaderLineroute.put(0, new ColumnData("<html>Порядковый  <br>номер          <br>выполнения", 			Integer.class,  80) );
	 	columnHeaderLineroute.put(1, new ColumnData("<html>Название<br>технологической<br> операции", 				String. class, 140) );
	 	columnHeaderLineroute.put(2, new ColumnData("<html>iD операции 	   <br>по справочнику", 					Integer.class,  80) );	 	
	 	columnHeaderLineroute.put(3, new ColumnData("<html>Используемое<br>оборудование",							String. class ,120) );		 
	 	columnHeaderLineroute.put(4, new ColumnData("<html>Сотрудник   <br>(Ф.И.О.)" ,   							String. class, 100) );	
	 	columnHeaderLineroute.put(5, new ColumnData("<html>Минимальная <br>емкость        <br>входного <br>буфера", Integer.class ,100) );
	 	columnHeaderLineroute.put(6, new ColumnData("<html>Заполнение  <br>входного       <br>буфера", 				Integer.class, 100) );	 	
	 	columnHeaderLineroute.put(7, new ColumnData("<html>Максимальная<br>емкость 		  <br>входного <br>буфера", Integer.class, 100) );	 	 		 	
	 	columnHeaderLineroute.put(8, new ColumnData("<html>Минимальная <br>емкость 		  <br>выходного<br>буфера", Integer.class, 100) );
	 	columnHeaderLineroute.put(9, new ColumnData("<html>Заполнение  <br>выходного      <br>буфера",				Integer.class, 100) );	 	
	 	columnHeaderLineroute.put(10,new ColumnData("<html>Максимальная<br>емкость        <br>выходного<br>буфера", Integer.class, 100) );
	 	columnHeaderLineroute.put(11,new ColumnData("<html>Имя<br>позиции"										  ,	String. class , 70) );	   	 	
	 	columnHeaderLineroute.put(12,new ColumnData("<html>Описание<br>позиции"									  ,	String. class ,170) );	      	 	
    	return columnHeaderLineroute;
	}
	public static HashMap <Integer, ColumnData > initColumnHeaderLinespec () {    	
        HashMap <Integer, ColumnData >  columnHeaderLineroute = new HashMap <Integer, ColumnData >();
	 	columnHeaderLineroute.put(0, new ColumnData("<html>Название<br>технологического<br> ресурса", 				String. class, 180) );
	 	columnHeaderLineroute.put(1, new ColumnData("<html>iD <br>ресурса", 										Integer.class,  80) );	 	
	 	columnHeaderLineroute.put(2, new ColumnData("<html>Ед.<br>изм.",											String. class , 50) );		 
	 	columnHeaderLineroute.put(3, new ColumnData("<html>Норма    <br>расхода" ,   								Double. class, 100) );	
	 	columnHeaderLineroute.put(4, new ColumnData("<html>Ср.квадрат. <br>отклонение     <br>от нормы<br>расхода", Double. class ,100) );
	 	columnHeaderLineroute.put(5, new ColumnData("<html>Имя<br>позиции"										  ,	String. class ,150) );	   	 	
	 	columnHeaderLineroute.put(6, new ColumnData("<html>Описание<br>позиции"									  ,	String. class ,170) );	      	 	
    	return columnHeaderLineroute;
	}
}		
