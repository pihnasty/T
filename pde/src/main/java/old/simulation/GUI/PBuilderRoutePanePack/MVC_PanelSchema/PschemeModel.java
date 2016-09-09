package old.simulation.GUI.PBuilderRoutePanePack.MVC_PanelSchema;

import old.database.XmlRW;
import old.database.tabDataSet.RowMachine;
import old.database.tabDataSet.RowMachineModelmachine;
import old.database.tabDataSet.RowWorkMachine;
import old.entityProduction.Machine;
import old.entityProduction.Work;
import old.simulation.GUI.MenuFrame;
import old.simulation.GUI.PBuilderRoutePanePack.MVC_PanelTypModmachine.AbstractPtmModel;
import old.simulation.GUI.PBuilderRoutePanePack.MVC_PanelTypModmachine.PtmModel;
import old.simulation.GUI.PBuilderRoutePanePack.MVC_PanelTypModmachine.R;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author pom
 */
public class PschemeModel extends AbstractPtmModel implements Observer {

    public PschemeModel(Work w, MenuFrame fm) {
        super(fm, 10.0);
        this.fm = fm;
        double kW = 0.8;							//  коэффициент уменьшения области для схемы производства по сравнению с размером области компонента, в котором будем размещена схема
        setPanelHeight(fm.getHeight() * kW);
        panelWidth = fm.getWidth() * kW;
        this.w = w;
        try {
            imageScheme = ImageIO.read(new File(this.w.getScheme()));                //  We get the scheme of production.

        } catch (IOException exp) {
            System.out.println("No scheme of production.");

        }

        kScheme = ((double) imageScheme.getHeight(null) / getPanelHeight());	    	//  коэффициент ля масштабирования схемы производства
        if (kScheme < ((double) imageScheme.getWidth(null) / panelWidth)) {
            kScheme = (double) imageScheme.getWidth(null) / panelWidth;
        }
        imageScheme = imageScheme.getScaledInstance((int) (imageScheme.getWidth(null) / kScheme), (int) (imageScheme.getHeight(null) / kScheme), 1);	 // масшабируем схему производства по высоте панели		
        schemeWidth = imageScheme.getWidth(null);
        schemeHeight = imageScheme.getHeight(null);
        for (int i = 0; i < w.getMachines().size(); i++) {			//  We go by the images of machines.	    
            Image imgMachine = null;
            Machine machine = w.getMachines().get(i);
            if (machine.getLocationX() > 1.2) {
                machine.setLocationX(0.5);			//	удалить затем
            }
            if (machine.getLocationY() > 1.2) {
                machine.setLocationY(0.5);			//	удалить затем
            }
            R rm = new R(machine.getId(), machine.getLocationX() * schemeWidth, machine.getLocationY() * schemeHeight, schemeWidth, w.getOverallSize(), machine);
            getRs().add(rm);
            //  outMachine_toRowMachne();
        }
    }

    public void outMachine_toRowMachne() {
        for (RowMachine r : getFm().getDataSet().getTabMachines()) {             //Это сделано для того, чтобы старые данные перевести по новому формату. Цикл можно удалять после отладки старых данных
            for (Machine m : w.getMachines()) {
                if (m.getId() == r.getId()) {
                    XmlRW.FieldToField(r, m);                           //сохраненяем изменение координат объектов в DataSet
                }
            }
        }
    }

    public void setCurrentMove(Point ePoint) {

        if (getCurrent() != null) {
            getCurrent().set(ePoint.getX() + getCurrent().getDxError(), ePoint.getY() + getCurrent().getDyError());
            for (int i = 0; i < w.getMachines().size(); i++) {

                if (getCurrent().getId() == w.getMachines().get(i).getId()) {
                    w.getMachines().get(i).setLocationX(getCurrent().getX() / schemeWidth);
                    w.getMachines().get(i).setLocationY(getCurrent().getY() / schemeHeight);

                    // saveField(fm.getDataSet());
                }

                outMachine_toRowMachne();
            }
        }
        changed();
    }

    public Image getImageScheme() {
        return imageScheme;
    }

    public double getSchemeHeight() {
        return schemeHeight;
    }

    @Override
    public void update(Observable o, Object arg) {
        R selectModelMachine = ((PtmModel) o).getCurrent();
        if (selectModelMachine == null) {
            machine = null;
            rowMachine = null;
            current = null;
        } else if (PtmModel.class == o.getClass()) {
            int counter = ((PtmModel) o).setCurrentOut();
            if (counter == 1) {
                int idMachine = fm.getDataSet().IdMax(RowMachine.class) + 1;

                rowMachine = new RowMachine(idMachine,
                        "Machine" + idMachine,
                        selectModelMachine.getX() / schemeWidth,
                        1 + selectModelMachine.getY() / schemeHeight,
                        1,
                        "Machine" + idMachine);

                machine = new Machine(rowMachine.getId(),
                        "Machine" + idMachine,
                        ((PtmModel) o).getModelmachineById((int) selectModelMachine.getId()),
                        selectModelMachine.getX() / schemeWidth,
                        1 + selectModelMachine.getY() / schemeHeight,
                        1,
                        "Machine" + idMachine);

                w.getMachines().add(machine);
                fm.getDataSet().getTabMachines().add(rowMachine);

                rowMachineModelmachine
                        = new RowMachineModelmachine(machine.getId(), (int) selectModelMachine.getId(), "IdMachine=" + machine.getId() + "IdModelMachine=" + selectModelMachine.getId());

                rowWorkMachine = new RowWorkMachine(w.getId(), machine.getId(), "IdWork=" + w.getId() + "IdMachine=" + machine.getId());

                fm.getDataSet().getTabMachineModelmachines().add(rowMachineModelmachine);
                fm.getDataSet().getTabWorksMachines().add(rowWorkMachine);

                R rm = new R(machine.getId(), machine.getLocationX() * schemeWidth, machine.getLocationY() * schemeHeight, schemeWidth, w.getOverallSize(), machine);

                current = rm;
                current.set(getCurrent().getX(), getCurrent().getY());
                getRs().add(rm);

                System.out.println("getCurrent()==================================" + getCurrent());

                changed();
                outMachine_toRowMachne();
                System.out.println("Пересекли границу--------------------------------------------------------------------+++++++++++++++++++++++++++");
            }
            if (counter == (-1)) {

                w.getMachines().remove(machine);
                fm.getDataSet().getTabMachines().remove(rowMachine);
                fm.getDataSet().getTabMachineModelmachines().remove(rowMachineModelmachine);
                fm.getDataSet().getTabWorksMachines().remove(rowWorkMachine);
                getRs().remove(current);

                machine = null;
                rowMachine = null;

                current = null;
                changed();
                System.out.println("Пересекли границу2--------------------------------------------------------------------+++++++++++++++++++++++++++");
            }

            if (counter == (0)) {
                if (getCurrent() != null) {
                    getCurrent().set(selectModelMachine.getX(), schemeHeight + selectModelMachine.getY());
                    setCurrentMove(new Point((int) selectModelMachine.getX(), (int) (schemeHeight + selectModelMachine.getY())));
                }
                changed();
            }
        }

    }

    /**
     * Here we define the removal rate between the two machines.
     *
     * @param r1 the machine r1
     * @param r2 the machine r2
     * @param powerSpeed The power speed
     * @return
     */
    public Point2D.Double speed(R r1, R r2, double powerSpeed) {
        double dist = distance(r1, r2);
        double vX = 5.0;
        double vY = 0.0;
        if (dist != 0) {
            vX = (r1.getX() - r2.getX()) / dist;
            vY = (r1.getY() - r2.getY()) / dist;
        }
        return new Point2D.Double(vX * powerSpeed, vY * powerSpeed) {
        };
    }

    public Point2D.Double speed(String s, double powerSpeed) {
        double vX = 0.0;
        double vY = 0.0;
        if (s == "top") {
            vY = 1.0;
        }
        if (s == "bottom") {
            vY = -1.0;
        }
        if (s == "left") {
            vX = 1.0;
        }
        if (s == "right") {
            vX = -1.0;
        }
        return new Point2D.Double(vX * powerSpeed, vY * powerSpeed) {
        };
    }

    public void testFiguresImposition() {
        //  intersectionExist = true, if the intersection exist
        boolean intersectionExist = true;
        int i = 1;

        double powerSpeed = 4.0;    //  This is the scaling factor for the speed.


        if (current != null) {
            while (intersectionExist) {

                Area currentRect = new Area(new Rectangle(
                        (int) current.getX() + (int) current.getScaleOverallDimensionX() / 2 - (int) current.getScaleWorkSizeX() / 2,
                        (int) current.getY() + (int) current.getScaleOverallDimensionY() / 2 - (int) current.getScaleWorkSizeY() / 2,
                        (int) current.getScaleWorkSizeX(), (int) current.getScaleWorkSizeY()));
                intersectionExist = false;
                for (R r : getRs()) {
                    if (r.getId() != current.getId()) {
                        Area r2Rect = new Area(new Rectangle(
                                (int) r.getX() + (int) r.getScaleOverallDimensionX() / 2 - (int) r.getScaleWorkSizeX() / 2,
                                (int) r.getY() + (int) r.getScaleOverallDimensionY() / 2 - (int) r.getScaleWorkSizeY() / 2,
                                (int) r.getScaleWorkSizeX(), (int) r.getScaleWorkSizeY()));
                        r2Rect.intersect(currentRect);
                        if (!r2Rect.isEmpty()) {
                            Point2D.Double v = speed(current, r, powerSpeed);
                            System.out.println("r Id=" + r.getId() + "   i=" + i++ + " + v.x=" + v.x);
                            current.set(current.getX() + v.x, current.getY() + v.y);
                            intersectionExist = true;
                        }
                    }
                }

                int schemeMargin = 40;  // margin: The size of the area which is outside the schema.
                int schemePadding = 2;  // margin: The size of the area that is in the schema.

                Area rTop = new Area(new Rectangle(0, -schemeMargin, (int) schemeWidth, schemeMargin + schemePadding));
                rTop.intersect(currentRect);
                if (!rTop.isEmpty()) {
                    Point2D.Double v = speed("top", powerSpeed);
                    current.set(current.getX() + v.x, current.getY() + v.y);
                    intersectionExist = true;
                }
                Area rBottom = new Area(new Rectangle(0, (int) schemeHeight - schemePadding, (int) schemeWidth, schemeMargin + schemePadding));
                rBottom.intersect(currentRect);
                if (!rBottom.isEmpty()) {
                    Point2D.Double v = speed("bottom", powerSpeed);
                    current.set(current.getX() + v.x, current.getY() + v.y);
                    intersectionExist = true;
                }
                Area rLeft = new Area(new Rectangle(-schemeMargin, 0, schemeMargin + schemePadding, (int) schemeHeight));
                rLeft.intersect(currentRect);
                if (!rLeft.isEmpty()) {
                    Point2D.Double v = speed("left", powerSpeed);
                    current.set(current.getX() + v.x, current.getY() + v.y);
                    intersectionExist = true;
                }
                Area rRight = new Area(new Rectangle((int) schemeWidth-schemePadding, 0, (int) schemeMargin + schemePadding, (int) schemeHeight));
                rRight.intersect(currentRect);
                if (!rLeft.isEmpty()) {
                    Point2D.Double v = speed("right", powerSpeed);
                    current.set(current.getX() + v.x, current.getY() + v.y);
                    intersectionExist = true;
                }

            }
        }


    }

    /**
     * It determines the distance between the two machines.
     *
     * @param r1 the machine r1
     * @param r2 the machine r2
     * @return the distance between the two machines (r1 and r2)
     */
    public double distance(R r1, R r2) {
        return Math.sqrt((r1.getX() - r2.getX()) * (r1.getX() - r2.getX()) + (r1.getY() - r2.getY()) * (r1.getY() - r2.getY()));
    }

    private Work w;
    private Image imageScheme;                                                  //  A scheme of a production. 
    private double panelWidth;                                                  //  The width of the panel to the location of the scheme of production. 
    private double kScheme;                                                     //  The scaling factor of the  scheme of the production.	
    private double schemeHeight;                                                //  The height of the scheme of production.
    private double schemeWidth;                                                 //  The width of the scheme of production. 
    private MenuFrame fm;
    private RowMachine rowMachine;
    private Machine machine;
    private RowMachineModelmachine rowMachineModelmachine;
    private RowWorkMachine rowWorkMachine;

}
