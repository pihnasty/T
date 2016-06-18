package trestview.resourcelink.schemawork;

import entityProduction.Machine;
import entityProduction.Work;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.Cursor;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import persistence.loader.DataSet;
import trestview.resourcelink.ResourceLinkModel;
import trestview.table.tablemodel.TableModel;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Max on 02.05.2016.
 */
public class SchemaModel extends Observable  implements Observer{
    private Observable observableModel;
    private Rule rule;
    private DataSet dataSet;
    private Cursor cursor;
    private MouseEvent mouseEvent;
    private Work work;
    public Double kScale ;
    private List<Q> qs;
    Point p2;

    public SchemaModel(Observable observableModel, Rule rule) {
        this.observableModel = observableModel;
        this.rule = rule;
        this.qs = new ArrayList();
        this.dataSet = ((ResourceLinkModel)observableModel).getDataSet();

        if(this.rule== Rule.Work)  {
            if(!((ResourceLinkModel)observableModel).getTrest().getWorks().isEmpty())  {
                createDataSchemaModel (((ResourceLinkModel) observableModel).getTrest().getWorks().get(0));
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {


        if(this.rule== Rule.Work)  {
                createDataSchemaModel ((Work) ((TableModel<Work>)o).getSelectRow());
            }

        changed();
    }

    public void changed() {
        setChanged();
        notifyObservers();
    }

    public Work getWork()           {   return work;        }

    public void setWork(Work work)  {   this.work = work;   }

    public List<Q> getQs()          {   return qs;          }

    public void setQs(List<Q> qs)   {   this.qs = qs;       }

    private void createDataSchemaModel (Work work){
        qs.clear();
        this.work = work;
        for (Machine machine : work.getMachines()) {
            qs.add(new Q(machine));
        }  //  ArrayList<Machine> machines));

    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public void changeCursor(MouseEvent event) {
        this.mouseEvent =  event;
        if (find(new Point((int) mouseEvent.getX(), (int) mouseEvent.getY())) == null) {
            setCursor(Cursor.DEFAULT);
        } else {
            setCursor(Cursor.HAND);
        }
        changed();
    }

    public void changeLocation(MouseEvent event) {
        this.mouseEvent =  event;
        double eps = 0.01;   //   Error move
        Point p = new Point((int) mouseEvent.getX(), (int) mouseEvent.getY());
        Q q = find(p);


//        Point p = new Point((int) mouseEvent.getX(), (int) mouseEvent.getY());
//        find(p);



        double x = (p.getX() / kScale );
        double y = (p.getY() / kScale );

        ImageView imvWork = new ImageView();
        imvWork.setImage(new javafx.scene.image.Image("file:"+work.getScheme() ));

            if (q == null) {
                setCursor(Cursor.DEFAULT);
            } else {



                if(!isEmptyPoint(q))
                for (Machine m:  work.getMachines())
                    if (m.getId() == q.getIdQ()) {
                        double dxLocationX = x / imvWork.getImage().getWidth() - m.getLocationX();
                        double dyLocationY = y / imvWork.getImage().getHeight() - m.getLocationY();

                        if (Math.abs(dxLocationX) > eps) { dxLocationX = eps * Math.signum(dxLocationX);  m.setLocationX(m.getLocationX()+dxLocationX); }
                            else m.setLocationX(x / imvWork.getImage().getWidth());
                        if (Math.abs(dyLocationY) > eps) { dyLocationY = eps * Math.signum(dyLocationY);  m.setLocationY(m.getLocationY()+dyLocationY);}
                            else m.setLocationY(y / imvWork.getImage().getHeight());
                    }
                q.toFront();


                for (Q qw:  qs) {

             //       System.out.println("q.getBoundsInParent().contains(qw.getBoundsInParent())="+q.getBoundsInParent().intersects(qw.getBoundsInParent()));

                }

            }

        createDataSchemaModel (work);
        changed();
    }

    private boolean isEmptyPoint(Q q) {
        int i2 = 0;
                for (int i = 0; i<4; i++) {
                    Q q2 = find(findPoint(q)[i]);
                    if (q2!=null && q2.getIdQ()!=q.getIdQ()) return true;
                }


        return false;
    }

    public MouseEvent getMouseEvent() {
        return mouseEvent;
    }

    public void setMouseEvent(MouseEvent mouseEvent) {
        this.mouseEvent = mouseEvent;
    }

    public Double getkScale() {
        return kScale;
    }

    public void setkScale(Double kScale) {
        this.kScale = kScale;
    }

    public Q find(Point p) {
        for (int i = 0; i < qs.size(); i++) {
            Q q = qs.get(i);
            double x = (p.getX() / kScale - q.getLayoutX());
            double y = (p.getY() / kScale - q.getLayoutY());
            double t = 2.0 * Math.PI / 360;
            double xAngle = x * Math.cos(q.getAngle() * t) + y * Math.sin(q.getAngle() * t);
            double yAngle = -x * Math.sin(q.getAngle() * t) + y * Math.cos(q.getAngle() * t);
            if (q.getrOuter().contains(xAngle, yAngle)) {
                return q;
            }
        }
        return null;
    }

    public Point [] findPoint(Q q) {

        Point [] points = new Point[] {new Point(), new Point(), new Point(), new Point()};
        points[0] = coordinatesPointAfterRotation((int)(-q.getrInner().getWidth() / 2.0),(int) (-q.getrInner().getHeight() / 2.0),q.getAngle(),q.getLayoutX(),q.getLayoutY());
        points[1] = coordinatesPointAfterRotation((int)(-q.getrInner().getWidth() / 2.0),(int) ( q.getrInner().getHeight() / 2.0),q.getAngle(),q.getLayoutX(),q.getLayoutY());
        points[2] = coordinatesPointAfterRotation((int)( q.getrInner().getWidth() / 2.0),(int) (-q.getrInner().getHeight() / 2.0),q.getAngle(),q.getLayoutX(),q.getLayoutY());
        points[3] = coordinatesPointAfterRotation((int)( q.getrInner().getWidth() / 2.0),(int) ( q.getrInner().getHeight() / 2.0),q.getAngle(),q.getLayoutX(),q.getLayoutY());

        return points;
    }

    // The coordinates of the point after rotation
   public Point coordinatesPointAfterRotation (int xBefore, int yBefore, double angle , double xO, double yO) {
        double t = 2.0 * Math.PI / 360;
        int xAfter = (int) ((xBefore * Math.cos(angle * t) + yBefore * Math.sin(angle * t) + xO) * kScale);
        int yAfter = (int) ((-xBefore* Math.sin(angle * t) + yBefore * Math.cos(angle * t) + yO) * kScale);
       System.out.println("+++++++++++++++++++++"+ xAfter +"  " + yAfter+ "   -------------------------"+xO+"  "+yO);
        return new Point(xAfter,yAfter);
    }







}
