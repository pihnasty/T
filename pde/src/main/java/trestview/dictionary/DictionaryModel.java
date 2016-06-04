package trestview.dictionary;

import trestmodel.TrestModel;
import trestview.menu.TMenuModel;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.util.Observable;

/**
 * Created by pom on 07.02.2016.
 */
public class DictionaryModel extends Observable  {

    private TMenuModel menuModel;

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    private Rule rule;

    public Class gettClass() {
        return tClass;
    }

    public void settClass(Class tClass) {
        this.tClass = tClass;
    }

    private Class tClass;

     public DictionaryModel(Observable menuModel,Rule rule) {     this.rule = rule;        this.tClass = rule.getClassTab();
        this.menuModel = (TMenuModel)menuModel;

    }

    public TMenuModel getTMenuModel() {
        return menuModel;
    }

    public void setTMenuModel(TMenuModel menuModel) {
        this.menuModel = menuModel;
        changed();
    }

    public void changed() {
        setChanged();
        notifyObservers();
    }
}
