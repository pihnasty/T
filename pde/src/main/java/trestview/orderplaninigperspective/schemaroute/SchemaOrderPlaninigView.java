package trestview.orderplaninigperspective.schemaroute;

import designpatterns.InitializableDS;
import designpatterns.ObservableDS;
import designpatterns.observerdsall.BorderPaneObserverDS;
import entityProduction.Work;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import trestview.resourcelink.schemawork.Q;

import java.util.List;
import java.util.Observable;

public class SchemaOrderPlaninigView extends BorderPaneObserverDS {

    private ImageView imageview = new ImageView();
    private Work work;
    private Double scaleScheme;

    private BorderPane bp;

    private DoubleBinding kScale;
    private DoubleBinding hImv;
    private DoubleBinding wImv;
    private List<Q> qs;

    public SchemaOrderPlaninigView(ObservableDS observableDS, InitializableDS initializableDS) {
        super(observableDS,initializableDS);
        bp = new BorderPane();
        getChildren().addAll(bp);
        qs = ((SchemaOrderPlaninigModel)observableDS).getQs();
        setStyle("-fx-background-color: #336699;");

        setkScaleDefault();
        repaint((SchemaOrderPlaninigModel) this.observableDS);
        bp.scaleXProperty().bind(kScale);
        bp.scaleYProperty().bind(kScale);
    }

    private void repaint(SchemaOrderPlaninigModel schemaModel) {
        this.bp.getChildren().clear();
        this.work = schemaModel.getWork();
        this.imageview.setImage(new Image("file:"+work.getScheme() ));
        for(Q q: schemaModel.getQs()) {
            q.setLayoutX(q.getX());
            q.setLayoutY(q.getY());
        }
        if (imageview != null)           bp.getChildren().addAll(imageview);
        if (schemaModel.getQs() != null) bp.getChildren().addAll(schemaModel.getQs());
    }
    @Override
    public void update(Observable o, Object arg) {
        if(( (SchemaOrderPlaninigModel) o).getMouseEvent() != null) {
            if (((SchemaOrderPlaninigModel) o).getMouseEvent().getEventType() == MouseEvent.MOUSE_MOVED)
                setCursor(((SchemaOrderPlaninigModel) o).getCursor());
            if (((SchemaOrderPlaninigModel) o).getMouseEvent().getEventType() == MouseEvent.MOUSE_PRESSED) {
                qs = ((SchemaOrderPlaninigModel) o).getQs();
            }
        }
        repaint((SchemaOrderPlaninigModel) o);
        setHeight(getHeight()+1);        setHeight(getHeight()-1);
    }

    private void setkScaleDefault() {
        kScale = new DoubleBinding() {
            { super.bind(heightProperty()); }
            @Override
            protected double computeValue() {
                if(observableDS instanceof SchemaOrderPlaninigModel)((SchemaOrderPlaninigModel) observableDS).setkScale(  0.85*getHeight()/imageview.getImage().getHeight() );
                return 0.85*getHeight()/imageview.getImage().getHeight() ;
            }};
    }

}
