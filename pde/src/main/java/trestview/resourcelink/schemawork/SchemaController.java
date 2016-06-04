package trestview.resourcelink.schemawork;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;


import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by Max on 02.05.2016.
 */
public class SchemaController implements Initializable, EventHandler<MouseEvent> {
    private SchemaModel observableModel;

    public SchemaController(SchemaModel observableModel) {
        this.observableModel= observableModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void handle(MouseEvent event) {
        if ( event.getEventType() == MouseEvent.MOUSE_MOVED) {
     //       observableModel.changeCursor(new Point((int) event.getX(), (int) event.getY()));
            observableModel.changeCursor(event);
        }
        if ( event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            observableModel.changeLocation(event);
        }
        System.out.println(event.getEventType());
    }

    /**
     * Invoked when the mouse cursor has been moved onto a component but no buttons have been pushed.
     * When the mouse cursor has been moved onto a component, the  image changes for cursor
     */
//     public void mouseMoved(MouseEvent event) {
//         //observableModel.changeCursor(  event.getX());
//
//    }
}
