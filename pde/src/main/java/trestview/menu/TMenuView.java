package trestview.menu;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

import java.util.Observable;
import java.util.Observer;

import persistence.loader.XmlRW;

/**
 * Created by pom on 07.02.2016.
 */
public class TMenuView extends MenuBar  implements Observer {

    private TMenuModel menuModel;

    @FXML
    private Menu OpenPerspective2;

    @FXML
    private Menu OpenPerspective;

    public TMenuView() {
    }

    public TMenuView(TMenuModel menuModel, TMenuController menuController ) {
        this.menuModel =menuModel;
        XmlRW.fxmlLoad (this, menuController,"trestview/menu/tMenuView.fxml", "ui", "trestview/menu/stylesMenu.css");
    }

    @Override
    public void update(Observable o, Object arg) {
        menuModel = (TMenuModel)o;
    }
}
