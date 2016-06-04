package trestview.resourcelink.schemawork;

import old.entityProduction.Machine;
import old.entityProduction.Work;
import old.simulation.GUI.MenuFrame;
import trestview.resourcelink.MVC_PanelTypModmachine.R;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class PschemeView extends JPanel implements Observer {

    @Override
    public void update(Observable o, Object arg) {

        if (pschemeModel.isTypeCursor()) {
            setCursor(Cursor.getDefaultCursor());
        } else {
            setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));          //  .CROSSHAIR_CURSOR 			.HAND_CURSOR
        }
        //PschemeViewAdd((PschemeModel) o);
        repaint();

    }

    public PschemeView(PschemeModel pschemeModel, JComponent comp) {
        PschemeViewAdd(pschemeModel);
    }

    public void PschemeViewAdd(PschemeModel pschemeModel) {
        this.pschemeModel = pschemeModel;
        this.rs = pschemeModel.getRs();
        this.imageScheme = pschemeModel.getImageScheme();
        this.schemeHeight = pschemeModel.getSchemeHeight();
        if (pschemeModel.isTypeCursor()) {
            setCursor(Cursor.getDefaultCursor());
        } else {
            setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));          //  .CROSSHAIR_CURSOR 			.HAND_CURSOR
        }
    }

    public void paintComponent(Graphics g) {
        this.g = g;
        Graphics2D g2 = (Graphics2D) g;
        font = new Font("Arial", Font.ITALIC, (int) (schemeHeight * kFont));							//	Определяем размер шрифта и его фонт возле изображения оборудования			
        super.paintComponent(g);
        if (imageScheme == null) {
            return;	   					// 	выходим, если отсутствует графический файл со схемой производства
        }
        g.setFont(font);
        g.drawImage(imageScheme, 0, 0, null);					//	выводим схему производства в панель	
        for (int i = 0; i < rs.size(); i++) {					//	проходимся по изображениям машин	  	    	
            R r = rs.get(i);
            g.drawImage(r.getIm(), (int) r.getX(), (int) r.getY(), null);	//	выводим изображение машины
            g.drawString(Integer.toString(((Machine) r.getM()).getId()), (int) r.getX(), (int) r.getY());
            Color oldColor = g.getColor();
            Color newColor = new Color(0, 0, 255);
            g.setColor(newColor);
            
            Stroke oldStroke = g2.getStroke();
            g2.setStroke(new BasicStroke(2.0f));  
            g.drawRect((int) r.getX(), (int) r.getY(), (int) r.getScaleOverallDimensionX(), (int) r.getScaleOverallDimensionY());
            g.drawRect((int) r.getX() + (int) r.getScaleOverallDimensionX() / 2 - (int) r.getScaleWorkSizeX() / 2,
                    (int) r.getY() + (int) r.getScaleOverallDimensionY() / 2 - (int) r.getScaleWorkSizeY() / 2,
                    (int) r.getScaleWorkSizeX(), (int) r.getScaleWorkSizeY());
             g.setColor(oldColor);
             g2.setStroke(oldStroke);
        }
    }

    private PschemeModel pschemeModel;
    private ArrayList<R> rs;	// все объекты схемы производства
    private Image imageScheme;                                                  // A scheme of a production. 
    private Work w;
    private Graphics g;
    private R current;
    private MenuFrame fm;
    private double k;                           //  коэффициент уменьшения оборудования
    private double kScheme;			//  коэффициент ля масштабирования схемы производства	
    private double panelHeight;			//  высота панели для размещения схемы производства
    private double panelWidth;			//  ширина панели для размещения схемы производства
    private double schemeHeight;		//  высота панели для размещения схемы производства
    private double schemeWidth;			//  ширина панели для размещения схемы производства	
    private Font font;				//  шрифт подписи к объектам схемы
    private double kFont = 0.02;		//  коэффициент, определяющий высоту шрифта подписи к объектам схемы. kFont = (Высота шрифта)/(Высота области, где размещена схема производства)

}
