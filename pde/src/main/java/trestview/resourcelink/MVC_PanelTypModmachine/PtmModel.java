package trestview.resourcelink.MVC_PanelTypModmachine;

import old.database.tabDataSet.RowModelmachine;
import old.database.tabDataSet.RowModelmachineTypemachine;
import old.database.tabDataSet.RowTypemachine;
import old.entityProduction.Modelmachine;
import old.entityProduction.Typemachine;
import old.simulation.GUI.MenuFrame;

import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author pom
 */
public class PtmModel extends AbstractPtmModel {

    private ArrayList<Modelmachine> modelmachines;
    private ArrayList<Typemachine> typemachines;
    private ArrayList<R> rTypeNames;                                            //  A graphical representation of objects of Types Name.
    private boolean currentOut = false;                                         //  The flag is true, if the modelMachine has crossed the border of the panel.

    /**
     * Returns the model of the machine with the specified numbered (id) or
     * null.
     *
     * @param id the unique index for the model of the machine.
     * @return the model of the machine.
     */
    public Modelmachine getModelmachineById(int id) {
        for (Modelmachine mm : modelmachines) {
            if (id == mm.getId()) {
                return mm;
            }
        }
        return null;
    }

    public PtmModel(MenuFrame fm) {
        super(fm, 30.0);
        setPanelWidth(fm.getWidth());
        ArrayList<RowTypemachine> tabTypemachines = fm.getDataSet().getTabTypemachines();
        ArrayList<RowModelmachineTypemachine> tabModelmachineTypemachines = fm.getDataSet().getTabModelmachineTypemachines();
        ArrayList<RowModelmachine> tabModelmachines = fm.getDataSet().getTabModelmachines();

        modelmachines = new ArrayList<Modelmachine>();
        typemachines = new ArrayList<Typemachine>();
        rTypeNames = new ArrayList<R>();

        int y = 20;
        int dy = 0;
        int x = 20;

        for (RowTypemachine t : tabTypemachines) {
            boolean flagTypemachine = false;
            for (RowModelmachineTypemachine tm : tabModelmachineTypemachines) {
                if (t.getId() == tm.getId2()) {
                    flagTypemachine = true;
                    modelmachines.add((Modelmachine) fm.getDataSet().createObject(tabModelmachines.get(0).getById(RowModelmachine.class, tabModelmachines, tm.getId())));
                }
            }
            if (flagTypemachine) {
                typemachines.add((Typemachine) fm.getDataSet().createObject(t));
            }
        }
        typemachines.sort(null);
        modelmachines.sort(null);

//  We form rs = new ArrayList<R>() for Modelmachine -------------------------//
        for (Typemachine t : getTypemachines()) {
            for (Modelmachine md : getModelmachines()) {
                if (t.getName() == md.getTypeMachine()) {

                    R r = new R(md.getId(), x, y, getPanelWidth(), 0, md);
                    getRs().add(r);
                    x = (int) (x + r.getDx() * 1.2);
                    dy = (int) (r.getDy() * 1.8);
                    if (x > fm.getWidth() * 0.6) {
                        y = y + dy;
                        x = -50;
                    }
                }
            }
            x = x + 70;
        }
//  We form rs = new ArrayList<R>() for typeMachine --------------------------//
        String typeMachine = "";
        for (R r : getRs()) {
            if (typeMachine != ((Modelmachine) r.getM()).getTypeMachine()) {
                typeMachine = ((Modelmachine) r.getM()).getTypeMachine();
                R rType = new R(1, r.getX(), r.getY() - 5, 0, 0, typeMachine);
                rTypeNames.add(rType);
            }
        }
    }

    public void setCurrentMove(Point ePoint) {
        if (getCurrent() != null) {
            getCurrent().set(ePoint.getX() + getCurrent().getDxError(), ePoint.getY() + getCurrent().getDyError());
        }
        changed();
    }

    public ArrayList<Typemachine> getTypemachines() {
        return typemachines;
    }

    public ArrayList<Modelmachine> getModelmachines() {
        return modelmachines;
    }

    public ArrayList<R> getrTypeNames() {
        return rTypeNames;
    }

    public void setrTypeNames(ArrayList<R> rTypeNames) {
        this.rTypeNames = rTypeNames;
    }

    public boolean isCurrentOut() {
        return currentOut;
    }

    public void setCurrentOut(boolean currentOut) {
        this.currentOut = currentOut;
    }

//  The method sends for  model a signal about the  border crossing.          //
    public int setCurrentOut() {
        if (current != null) {
            //   System.out.println("currentOut0=" + currentOut);
            if (current.getY() < 0 && !currentOut) {
                currentOut = !currentOut;
                return 1;                                                       //  We leave the panel (PtmModel) at top side.
            }
            if (current.getY() >= 0 && currentOut) {
                currentOut = !currentOut;
                return -1;                                                      //  We go into  the panel on the top side.
            }
        }
        return 0;
    }

}
