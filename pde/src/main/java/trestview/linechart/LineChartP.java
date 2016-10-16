package trestview.linechart;

import designpatterns.InitializableDS;
import designpatterns.ObservableDS;
import javafx.beans.Observable;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * Created by Maxim on 15.08.2016.
 */


public  class LineChartP extends HBox implements Observer{

    private LineChartInterface o;

     public LineChartP(ObservableDS o, InitializableDS initializableDS){
        this.o =(LineChartInterface)o;

        viewgrafic();

    }



    public void viewgrafic(){
        NumberAxis xAxis = new NumberAxis(o.getTitleX(), o.getxMin(),o.getxMax(), o.getxTickUnit());
        xAxis.setTickLabelFill(Color.BROWN);

        NumberAxis yAxis = new NumberAxis(o.getTitleY(),  o.getyMin(),o.getyMax(),o.getyTickUnit() );
        yAxis.setTickLabelFill(Color.BROWN);
        // yAxis.setTickLabelGap(1);
        yAxis.setSide(Side.LEFT);
        //yAxis.setAutoRanging(false);
 //       yAxis.setMinorTickCount(10);

        LineChart<Number,Number> chart = new LineChart<>(xAxis,yAxis);
        chart.setLayoutX(50);
        chart.setLayoutY(10);
        chart.setCursor(Cursor.CROSSHAIR);
        chart.setStyle("-fx-font:bold 12 Arial; -fx-text-fill:brown;");
        chart.setPrefSize(500, 400);
        chart.setTitle(o.getTitleGraph());
        chart.setTitleSide(Side.TOP);
        chart.setLegendVisible(true);
        chart.setLegendSide(Side.BOTTOM);
        chart.setAlternativeColumnFillVisible(true);
        chart.setAlternativeRowFillVisible(false);
        chart.setHorizontalGridLinesVisible(true);
        chart.setVerticalGridLinesVisible(true);
        chart.setCreateSymbols(false);

        XYChart.Series seriesAirTem= new XYChart.Series();
        //seriesAirTem.setName("Температура воздуха");
    /*Text text1=new Text("21");
    text1.setFill(Color.BROWN);
    Text text7=new Text("33");
    text7.setFill(Color.BROWN);
    Text text12=new Text("23");
    text12.setFill(Color.BROWN);
*/
        //seriesAirTem.getData().addAll(data1, new XYChart.Data(category.get(1),0), new XYChart.Data(category.get(2),24), new XYChart.Data(category.get(3),27), new XYChart.Data(category.get(4),30), new XYChart.Data(category.get(5),32), data7, new XYChart.Data(category.get(7),33), new XYChart.Data(category.get(8),31), new XYChart.Data(category.get(9),29), new XYChart.Data(category.get(10),26),data12);


        //seriesWaterTem.setName("Температура воды");
        int i = 0;
        for (List<Point2D.Double> list : o.getPullList())  {
            XYChart.Series line= new XYChart.Series();

            for (Point2D.Double p : list) {
                line.getData().add(new XYChart.Data(p.getX(), p.getY()));
            }
            line.setName(o.getListLegend().get(i)); i++;
            chart.getData().addAll(line);
        }

        this.getChildren().add(chart);
     }

    @Override
    public void update(java.util.Observable o, Object arg) {

    }
}
