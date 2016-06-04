package old.simulation.GUI.PBuilderRoutePanePack.MVC_PanelTypModmachine;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observer;

/**
 *
 * @author pom
 */
public class PtmView extends JPanel implements Observer {

    public PtmView(PtmModel m, JComponent comp) {
        this.m = m;
        rs = m.getRs();                                                         //  инициализируем массив с данными объектов и										
        rTypeNames = m.getrTypeNames();
        if (m.isTypeCursor()) {
            setCursor(Cursor.getDefaultCursor());
        } else {
            setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));          //  .CROSSHAIR_CURSOR 			.HAND_CURSOR
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font font = new Font("Arial", Font.ITALIC, 11);				//	Определяем размер шрифта и его фонт возле изображения оборудования			
        g.setFont(font);
        // font = new Font("Arial", Font.ITALIC, (int) (schemeHeight * kFont));	//	Определяем размер шрифта и его фонт возле изображения оборудования
        for (R r : rs) {
            g.drawImage(r.getIm(), (int) r.getX(), (int) r.getY(), null);       //  There display the image of cars.
        }
        for (R rType : rTypeNames) {
            g.drawString(rType.toString(), (int) rType.getX(), (int) rType.getY()); //  There display the name of the types of the cars.
        }
        if (current != null) {
            g.drawImage(current.getIm(), (int) current.getX(), (int) current.getY(), null);
        }
    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        if (m.isTypeCursor()) {
            setCursor(Cursor.getDefaultCursor());
        } else {
            setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));          //  .CROSSHAIR_CURSOR 			.HAND_CURSOR
        }
        repaint();
    }
    private PtmModel m;
    private R current;   // данные о выделенном объекте

    private ArrayList<R> rTypeNames;                                            //  A graphical representation of objects of Types Name.    
    private ArrayList<R> rs;                                                    //  A graphical representation of objects model of Machine. 
    private double k;                                                           //  The scaling factor of the equipment.
    private double panelHeight;                                                 //  The height of the panel to the location of production.   
}
