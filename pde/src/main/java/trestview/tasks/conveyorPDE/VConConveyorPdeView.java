package trestview.tasks.conveyorPDE;



        import javafx.scene.layout.BorderPane;

        import java.awt.geom.Point2D;
        import java.util.List;
        import java.util.Observable;
        import java.util.Observer;

public class VConConveyorPdeView extends BorderPane implements Observer {
   private Observable model;

    private    List<Point2D.Double> listConT;
    private    List<Point2D.Double> listConS;

    public  VConConveyorPdeView(Observable observeble){
        inizilize(observeble);



    }




    public void update(Observable observeble, Object arg) {
        inizilize(observeble);

    }

    private void inizilize(Observable observeble) {
        model=observeble;
        listConS=((VСonConveyorPdeModel)model).getListConS();
        listConT=((VСonConveyorPdeModel)model).getListConT();

    }




}