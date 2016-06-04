/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trestview.resourcelink.MVC_PanelTypModmachine;

import old.simulation.GUI.MenuFrame;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author pom
 */
public abstract class AbstractPtmModel extends Observable {

    private MenuFrame fm;
    private ArrayList<R> rs;                                                    //  A graphical representation of objects model of Machine.
    private boolean typeCursor;                                                 //  There are  we determine, to look the cursor  at the subject. If (typeCursor=No) - to look.   
    protected R current;                                                        //  Data on the selected object.
    private R currentSave;                                                      //  The stored data of the selected object.
    private double k;                                                           //  The scaling factor of the equipment.
    private double panelHeight;                                                 //  The height of the panel to the location of production.      
    private double panelWidth;                                                  //  The width of the panel to the location of production.    
       
    public AbstractPtmModel() {
    }

    public AbstractPtmModel(MenuFrame fm, double k) {
        this.fm = fm;
        this.k = k;
        rs = new ArrayList<R>();
    }

    public void changeCursor(Point ePoint) {
        if (find(ePoint) == null) {
            typeCursor = true;
        } else {
            typeCursor = false;
        }
        changed();
    }

    public void changed() {
        setChanged();
        notifyObservers();
    }

    public R find(Point2D p) {
        for (int i = 0; i < rs.size(); i++) {
            R r = (R) rs.get(i);
            if (new Rectangle2D.Double(r.getX(), r.getY(), r.getDx(), r.getDy()).contains(p)) {
                return r;
            }
        }
        return null;
    }

    public abstract void setCurrentMove(Point ePoint);

    public void setErrorCoordinate(Point ePoint) {
        if (this.current != null) {
            this.current.setError(current.getX() - ePoint.getX(), current.getY() - ePoint.getY());
        }
    }

    public ArrayList<R> getRs() {
        return rs;
    }

    public MenuFrame getFm() {
        return fm;
    }

    public void setRs(ArrayList<R> rs) {
        this.rs = rs;
    }

    public boolean isTypeCursor() {
        return typeCursor;
    }

    public void setCurrent(R current) {
        this.current = current;
    }

    public void setCurrent(Point ePoint) {
        this.current = find(ePoint);                                   // получаем координаты объекта, если мышь оказывается над объектом   
        
        setErrorCoordinate(ePoint);
        setCurrentMove(ePoint);
    }

    public R getCurrent() {
        return current;
    }

    public R getCurrentSave() {
        return currentSave;
    }

    public void setCurrentSave(R currentSave) {
        this.currentSave = currentSave;
    }

    public void current_to_currentSave() {
        if(getCurrent()!=null) setCurrentSave(getCurrent().clone());
    }

    public void currentSave_to_current() {
        //setCurrent(getCurrentSave().clone());                               //  This embodiment does not work.      
      if(getCurrent()!=null)  getCurrent().set(getCurrentSave().getX(), getCurrentSave().getY());     //  This embodiment work.         
        current = null;
        currentSave = null;
        changed();
    }

    public double getK() {
        return k;
    }

    public double getPanelHeight() {
        return panelHeight;
    }

    public void setPanelHeight(double panelHeight) {
        this.panelHeight = panelHeight;
    }

    public double getPanelWidth() {
        return panelWidth;
    }

    public void setPanelWidth(double panelWidth) {
        this.panelWidth = panelWidth;
    }
}
