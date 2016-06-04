package trestview.resourcelink.MVC_PanelTypModmachine;

import trestview.resourcelink.schemawork.PschemeModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author pom
 */
public class PtmController extends MouseAdapter {

    public PtmController(AbstractPtmModel model) {
        this.model = model;
    }

    /**
     * There are we determine, to look the cursor at the subject. No - to look.
     */
    public void mouseMoved(MouseEvent event) {
        model.changeCursor(event.getPoint());
    }

    /**
     * перемещаем объект за курсором
     *
     * @param event
     */
    public void mouseDragged(MouseEvent event) {
        model.setCurrentMove(event.getPoint());
    }

    //  There have we define the coordinates of the clicked object.
    public void mousePressed(MouseEvent event) {
        model.setCurrent(event.getPoint());
        if (PtmModel.class == model.getClass()) {
            ((PtmModel) model).current_to_currentSave();

        }
    }

    public void mouseReleased(MouseEvent e) {
        if (PtmModel.class == model.getClass()) {
            model.currentSave_to_current();
            //       model.setCurrent(null);
            ((PtmModel) model).setCurrentOut(false);
        }
        if (PschemeModel.class == model.getClass()) {
            ((PschemeModel) model).testFiguresImposition();
        }
    }

    private AbstractPtmModel model;
}
