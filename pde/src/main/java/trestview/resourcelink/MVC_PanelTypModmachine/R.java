package trestview.resourcelink.MVC_PanelTypModmachine;

import old.entityProduction.Machine;
import old.entityProduction.Modelmachine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Intermediate class. Use for imaging an object on the panel. It is used to
 * scale the image to a predetermined height. It stores the location of the
 * object on the panel and the scaled image.
 *
 * @author Oleg Pignasty
 */
public class R<T> implements Cloneable {
    /**
     * 
     * @param id    This is the object identifier of the machine or the machine model.
     * @param x     The current coordinate for the image on the panel. 
     * @param y     The current coordinate for the image on the panel. 
     * @param panelWidth        This is the width of the panel with the scheme of production.
     * @param workOverallWidth  Overall size of the production area on the x-axis (according to the orientation of the scheme).
     * @param m     The machine or the machine model.
     */
    public R(int id, double x, double y, double panelWidth, double workOverallWidth, T m) {
        this.id = id;
        try {
            if (m.getClass() == Modelmachine.class) {
                this.imName = ((Modelmachine) m).getImg();
                this.im = ImageIO.read(new File(imName));       //  считываем изображение машины с объекта производства
                // This coefficient (double a) shows the number of times height of the model of the machine is less than the width of the panel.
                double a = 50.0;
                this.dy = panelWidth / a;															//	устанавливаем высоту машины, которая в к-раз меньше высоты панели
                this.dx = this.dy * im.getWidth(null) / im.getHeight(null);              //  устанавливаем ширину машины    
                im = im.getScaledInstance((int) this.dx, (int) this.dy, 1);                   //  масштабируем машину под рассчитанные высоту  imgMaciheHeight и ширину imgMaciheWidth            
            }
            if (m.getClass() == Machine.class) {
                this.imName = ((Machine) m).getModelmachine().getImg();
                im = ImageIO.read(new File(imName));       //  считываем изображение машины с объекта производства
                double a = workOverallWidth / ((Machine) m).getModelmachine().getOverallDimensionX();
                this.dx = panelWidth / a;															//	устанавливаем высоту машины, которая в к-раз меньше высоты панели
                this.dy = dx * im.getHeight(null) / im.getWidth(null);              //  устанавливаем ширину машины     
                im = im.getScaledInstance((int) dx, (int) dy, 1);                   //  масштабируем машину под рассчитанные высоту  imgMaciheHeight и ширину imgMaciheWidth            
                scaleOverallDimensionX = panelWidth / a;
                scaleOverallDimensionY = scaleOverallDimensionX/((Machine) m).getModelmachine().getOverallDimensionX()*((Machine) m).getModelmachine().getOverallDimensionY();
                scaleWorkSizeX = scaleOverallDimensionX/((Machine) m).getModelmachine().getOverallDimensionX()*((Machine) m).getModelmachine().getWorkSizeX();
                scaleWorkSizeY = scaleWorkSizeX / ((Machine) m).getModelmachine().getWorkSizeX()*((Machine) m).getModelmachine().getWorkSizeY();
            }
            this.x = x;
            this.y = y;
            this.m = m;
        } catch (IOException ex) {
            System.out.println("View_PTM 155 Пропущено изображение машины");
        }
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setError(double dxError, double dyError) {
        this.dxError = dxError;
        this.dyError = dyError;
    }

    public double getId() {
        return id;
    }

    public Image getIm() {
        return im;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public T getM() {
        return m;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDxError() {
        return dxError;
    }

    public double getDyError() {
        return dyError;
    }

    @Override
    public String toString() {
        return m.toString();
    }

    public String getImName() {
        return imName;
    }

    public double getScaleOverallDimensionX() {
        return scaleOverallDimensionX;
    }

    public double getScaleOverallDimensionY() {
        return scaleOverallDimensionY;
    }

    public double getScaleWorkSizeX() {
        return scaleWorkSizeX;
    }

    public double getScaleWorkSizeY() {
        return scaleWorkSizeY;
    }
     
    
    @Override
    public R clone() {
        R rCloned = null;
        try {
            rCloned = (R) super.clone();
            rCloned.x = this.x;
            rCloned.y = this.y;
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(R.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rCloned;
    }

    private double id;
    private double x;                                                           //  The current coordinate for the image on the panel. 
    private double y;                                                           //  The current coordinate for the image on the panel. 
    private double dx;                                                          //  The width of the image.
    private double dy;                                                          //  The error of the height of the image.
    private double dxError = 0;                                                 //  The error of width of the image.
    private double dyError = 0;                                                 //  The height of the image.    
    private Image im;                                                           //  The scaled image.
    private String imName;                                                      //  The scaled image.    
    private T m;                                                                //  The original object    

    /**
     * The Overall X-dimension of the machine on the scale
     */
    private double scaleOverallDimensionX = 0;

 
    /**
     * The Overall Y-dimension of the machine on the scale
     */
    private double scaleOverallDimensionY = 0;
    /**
     * The Overall X-dimension (scale) production area for the machine that is
     * needed.
     */
    private double scaleWorkSizeX = 0;
    /**
     * The Overall Y-dimension (scale) production area for the machine that is
     * needed.
     */
    private double scaleWorkSizeY = 0;

}
