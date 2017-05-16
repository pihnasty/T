package trestview.routeperspective.schemaroute;

import designpatterns.InitializableDS;
import designpatterns.ObservableDS;
import designpatterns.observerdsall.StackPaneObserverDS;
import entityProduction.Work;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import trestview.routeperspective.schemaroute.mesh.MeshView;

import java.util.List;
import java.util.Observable;

public class SchemaRouteView extends StackPaneObserverDS {

    private ImageView imageview = new ImageView();
    private Work work;
    private Double scaleScheme;

    private StackPane sp;

    private DoubleBinding kScale;
    private DoubleBinding hImv;
    private DoubleBinding wImv;
    private List<MeshView> meshes;

    public SchemaRouteView(ObservableDS observableDS, InitializableDS initializableDS) {
        super(observableDS, initializableDS);
        sp = new StackPane();
        sp.setAlignment(Pos.CENTER);
        getChildren().addAll(sp);
        meshes = ((SchemaRouteModel) observableDS).getMeshes();
        setStyle("-fx-background-color: #336699;");

        setkScaleDefault();
        repaint((SchemaRouteModel) this.observableDS);
        sp.scaleXProperty().bind(kScale);
        sp.scaleYProperty().bind(kScale);
    }

    private void repaint(SchemaRouteModel schemaModel) {

        this.sp.getChildren().clear();
        this.work = schemaModel.getWork();
        this.imageview.setImage(new Image("file:" + work.getScheme()));

        if (schemaModel.getMeshes() != null) {
            sp.getChildren().addAll(schemaModel.getMeshes());
        }

    }

    @Override
    public void update(Observable o, Object arg) {
        /*if (((SchemaRouteModel) o).getMouseEvent() != null) {
            if (((SchemaRouteModel) o).getMouseEvent().getEventType() == MouseEvent.MOUSE_MOVED) {
                setCursor(((SchemaRouteModel) o).getCursor());
                return;
            }
            if (((SchemaRouteModel) o).getMouseEvent().getEventType() == MouseEvent.MOUSE_PRESSED) {
                qs = ((SchemaRouteModel) o).getQs();
                return;
            }
        }*/
        repaint((SchemaRouteModel) o);
        setHeight(getHeight() + 1);
        setHeight(getHeight() - 1);

    }

    private void setkScaleDefault( ) {
        kScale = new DoubleBinding() {
            {
                super.bind(heightProperty());
            }

            @Override
            protected double computeValue( ) {
                if (observableDS instanceof SchemaRouteModel)
                    ((SchemaRouteModel) observableDS).setkScale(0.85 * getHeight() / imageview.getImage().getHeight());
                return 0.85 * getHeight() / imageview.getImage().getHeight();
            }
        };
    }

}
