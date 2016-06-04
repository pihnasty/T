package trestview.machinetest.module0;

import javafx.application.Platform;

import java.time.Duration;
import java.util.*;

/**
 * Created by Роман on 03.05.2016.
 */
public class Module0Model extends Observable {

//    private Deque<Double> randomVariablesList;

    private double randomVariable;

    private Timer timer;

    public Module0Model() {
//        this.randomVariablesList = new ArrayDeque<>();
//        this.groupedStatisticalSeries = new Hashtable<>();
        populateList(100);
    }

    public double getRandomVariable() {
        return randomVariable;
    }



//    public void setRandomValuesList(Deque<Double> randomVariablesList) {
//        this.randomVariablesList = randomVariablesList;
//
//    }

    //populate the List with a test data
    private void populateList(int limit) {

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        System.out.println("Timer is working");
                        double value = Math.random()*100;
//                        System.out.println(value+"\\(^-^)/");
                        randomVariable = value;

                        changed();
                    }
                });

            }
        },3000, 3000);//3000000, 300000000);

//        for (double i = 0; i < limit; i++) {
//            randomValuesList.add(Math.atan(i));
//        }
    }

    public void changed() {
        setChanged();
        notifyObservers();
    }

}
