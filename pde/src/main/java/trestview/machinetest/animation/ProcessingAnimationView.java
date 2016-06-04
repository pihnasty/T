package trestview.machinetest.animation;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import trestview.machinetest.MachineTestController;

import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

/**
 * Created by Роман on 18.04.2016.
 */
public class ProcessingAnimationView extends Pane implements Observer {


    private ProcessingAnimationModel processingAnimationModel;
    private MachineTestController machineTestController;

    private ImageView imageViewTop;
    private ImageView imageViewBot;
    private ImageView imageViewLeftStorage;
    private ImageView imageViewRightStorage;
    private Image imgTop;
    private Image imgBot;
    Rectangle rectangle;
    Circle circle;
    Timeline timeline;
    TranslateTransition transitionCircle;
    TranslateTransition transitionRect;

    private Stack<Rectangle> products;

    public ProcessingAnimationView(ProcessingAnimationModel animationModel) {//ProcessingAnimationModel animationModel, MachineTestController testController) {
        this.processingAnimationModel = animationModel;
//        this.machineTestController = testController;

        imageViewTop = new ImageView();
        imageViewTop.setFitWidth(270);
        imageViewTop.setFitHeight(100);
        imageViewTop.setX(200);
        imgTop = new Image("file:Image\\animation\\presTop.png");
        imageViewTop.setImage(imgTop);

        imageViewBot = new ImageView();
        imageViewBot.setTranslateY(40);
        imageViewBot.setFitWidth(270);
        imageViewBot.setFitHeight(300);
        imgBot = new Image("file:Image\\animation\\presBot.png");
        imageViewBot.setPreserveRatio(true);
        imageViewBot.setX(200);
        imageViewBot.setImage(imgBot);

        Image imgStorage = new Image("file:Image\\animation\\storage.png");
        imageViewLeftStorage = new ImageView();
        imageViewLeftStorage.setFitWidth(200);
        imageViewLeftStorage.setFitHeight(80);
        imageViewLeftStorage.setY(185);
        imageViewLeftStorage.setImage(imgStorage);

        imageViewRightStorage = new ImageView();
        imageViewRightStorage.setFitWidth(200);
        imageViewRightStorage.setFitHeight(80);
        imageViewRightStorage.setY(185);
        imageViewRightStorage.setX(500);
        imageViewRightStorage.setImage(imgStorage);


        rectangle = new Rectangle(340, 185, 15, 5);
//        rectangle.setVisible(false);
        rectangle.setFill(Color.CORAL);
        circle = new Circle(190, 180, 10);
        circle.setFill(Color.CORAL);

        this.getChildren().addAll(imageViewLeftStorage, imageViewRightStorage, rectangle, circle, imageViewBot, imageViewTop);

        this.setMinSize(300, 300);

        timeline = new Timeline();
        transitionCircle = new TranslateTransition();
        transitionRect = new TranslateTransition();
        products = new Stack<>();
    }

    private void runProcessingAnimation(double duration) {
        timeline.setAutoReverse(true);
        timeline.setCycleCount(2);

//        transitionRect = new TranslateTransition(Duration.millis(duration), rectangle);
//        transitionCircle = new TranslateTransition(Duration.millis(duration), circle);
        transitionCircle.setDuration(Duration.millis(duration));
        transitionCircle.setNode(circle);
        transitionCircle.setAutoReverse(false);
        transitionCircle.setByX(150);
        transitionCircle.setFromX(0);

        transitionRect.setAutoReverse(false);
        transitionRect.setDuration(Duration.millis(duration));
        transitionRect.setNode(rectangle);
        transitionRect.setByX(150);
        transitionRect.setFromX(0);

        KeyValue keyValue = new KeyValue(imageViewTop.translateYProperty(), 90);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(duration), (ActionEvent)->{
            toggleHide(rectangle);
            transitionRect.play();
            transitionCircle.play();
            stackProducts(rectangle, imageViewRightStorage, this);

        },keyValue);

        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    private void toggleHide(Shape... shapes) {
        for(Shape shape : shapes) {
            if(shape.isVisible()) {
                shape.setVisible(false);
            } else {
                shape.setVisible(true);
            }
        }
    }

    private void stackProducts(Rectangle rectangle, ImageView box, Pane layout) {

        Rectangle rect = null;
        //if(rectangle.getClass() == Rectangle.class) {
        rect = new Rectangle();
        rect.setWidth(rectangle.getWidth());
        rect.setHeight(rectangle.getHeight());
        rect.setFill(rectangle.getFill());
        //}
        if(products.isEmpty()) {
            rect.setX(box.getX()+box.getFitWidth()-20);
            rect.setY(box.getY()-rect.getHeight());

        } else if(products.peek().getX() < box.getX()+20){
            rect.setY(products.peek().getY() - rect.getHeight() -2);
            rect.setX(box.getX()+box.getFitWidth()-20);

        } else {
            rect.setX(products.peek().getX()-rect.getWidth() - 2);
            rect.setY(products.peek().getY());
        }
        products.add(rect);
        layout.getChildren().add(rect);

    }

    @Override
    public void update(Observable o, Object arg) {
        if(o.getClass() == ProcessingAnimationModel.class) {
            toggleHide(rectangle);
            runProcessingAnimation(((ProcessingAnimationModel)o).getDuration());


        }
    }
}
