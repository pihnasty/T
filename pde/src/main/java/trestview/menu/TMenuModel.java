package trestview.menu;

import trestmodel.TrestModel;

import java.util.Observable;
import java.util.Observer;

public class TMenuModel extends Observable {

    public MenuItemCall getMenuItemCall() { return menuItemCall;  }

    public void setMenuItemCall(MenuItemCall menuItemCall) { this.menuItemCall = menuItemCall; }

    private MenuItemCall menuItemCall = MenuItemCall.defaultItem;

    private Observable trestModel;

    public TMenuModel(Observable trestModel) {
        this.trestModel = trestModel;
    }

    public TrestModel getTrestModel() {
        return (TrestModel) trestModel;
    }

    public void setTrestModel(Observable trestModel) {
        this.trestModel = trestModel;
        changed();
    }

    public void changed() {
        setChanged();
        notifyObservers();
    }

    public void clickTestOfMachineItem() {
        this.menuItemCall = MenuItemCall.testOfMachineItem;
        changed();
    }

    public void clickResourcesLinksPerspectiveItem() {
        this.menuItemCall = MenuItemCall.resourcesLinksPerspectiveItem;
        changed();
    }

}
