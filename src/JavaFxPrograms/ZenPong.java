package JavaFxPrograms;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ZenPong extends Application {
    
    // The center points of the moving ball
    DoubleProperty centerX = new SimpleDoubleProperty();
    DoubleProperty centerY = new SimpleDoubleProperty();
    
    // The Y coordinate of the left paddle
    DoubleProperty leftPaddleY = new SimpleDoubleProperty();
    
    // The Y coordinate of the rght paddle
    DoubleProperty rightPaddleY = new SimpleDoubleProperty();
    
    // The drag anchor for the left and right paddles
    double leftPaddleDragAnchorY;
    double rightPaddleDragAnchorY;
    
    // The initial translateY property for the left and right paddles
    double initLeftPaddleTranslateY;
    double initRightPaddleTranslateY;
    
    // The moving ball;
    Circle ball;
    
    // The group containign all of the walls, paddles and ball.
    // This also allows us to requestFocus for KeyEvents on the Group
    Group pongComponents;
    
    // The left and right paddles
    Rectangle leftPaddle;
    Rectangle rightPaddle;
    
    // The walls
    Rectangle topWall;
    Rectangle bottomWall;
    Rectangle rightWall;
    Rectangle leftWall;
    
    Button startButton;
    
    // Controls whether the start button is visible
    BooleanProperty startVisible =new SimpleBooleanProperty(true);
    
    // The animation of the ball
    Timeline pongAnimation;
    
    // Controls whether the ball is moving right
    boolean movingRight = true;
    
    // Controls whether the ball is moving down
    boolean movingDown = true;
    
    // Sets the initial positions of the ball and paddles
    
    void initialize() {
        centerX.setValue(250);
        centerY.setValue(250);
        leftPaddleY.setValue(235);
        rightPaddleY.setValue(235);
        startVisible.set(true);
        pongComponents.requestFocus();
    }
    
    /**Checks whether or not the ball has collided with either the paddles, 
     * topWall, or bottomWall. If the ball hits the wall behind the paddles, the game is over.
     * @param args 
     */
    
    void checkForCollision(){
        if(ball.intersects(rightWall.getBoundsInLocal())
                || ball.intersects(leftWall.getBoundsInLocal())){
            pongAnimation.stop();
            initialize();
        }
        else if(ball.intersects(topWall.getBoundsInLocal())
                || ball.intersects(bottomWall.getBoundsInLocal())){
            movingDown = !movingDown;
        }
        else if(ball.intersects(leftPaddle.getBoundsInParent()) && !movingRight){
            movingRight = !movingRight;
        }
        else if(ball.intersects(rightPaddle.getBoundsInParent()) && movingRight){
            movingRight = !movingRight;
        }
    }
    public static void main(String args[]){
        Application.launch(args);
    }
    
    @Override
    public void start(Stage stage){
        pongAnimation = new Timeline(
                new KeyFrame(new Duration(10.0), t -> {
                    checkForCollision();
                    int horzPixels = movingRight ? 1: -1;
                    int vertPixels = movingDown ? 1 : -1;
                    centerX.setValue(centerX.getValue() + horzPixels);
                    centerY.setValue(centerY.getValue() + vertPixels);
                })
        );
        pongAnimation.setCycleCount(Timeline.INDEFINITE);
        ball = new Circle(0, 0, 5, Color.WHITE);
        topWall = new Rectangle(0, 0, 500, 1);
        leftWall = new Rectangle(0, 0, 1, 500);
        rightWall = new Rectangle(500, 0, 1, 500);
        bottomWall = new Rectangle(0, 500, 500, 1);
        leftPaddle = new Rectangle(20, 0, 15, 50);
        leftPaddle.setFill(Color.LIGHTBLUE);
        leftPaddle.setCursor(Cursor.HAND);
        leftPaddle.setOnMousePressed(me -> {
            initLeftPaddleTranslateY = leftPaddle.getTranslateY();
            leftPaddleDragAnchorY = me.getSceneY();
        });
        leftPaddle.setOnMouseDragged(me ->{
            double dragY = me.getSceneY() - leftPaddleDragAnchorY;
            leftPaddleY.setValue(initLeftPaddleTranslateY + dragY);
        });
        
        rightPaddle = new Rectangle(470, 0, 15, 50);
        rightPaddle.setFill(Color.LIGHTBLUE);
        rightPaddle.setCursor(Cursor.CLOSED_HAND);
        rightPaddle.setOnMousePressed(me -> {
            initRightPaddleTranslateY = rightPaddle.getTranslateY();
            rightPaddleDragAnchorY = me.getSceneY();
        });
        rightPaddle.setOnMouseDragged(me ->{
            double dragY = me.getSceneY() - rightPaddleDragAnchorY;
            rightPaddleY.setValue(initRightPaddleTranslateY + dragY);
        });
        startButton = new Button("start");
        startButton.setLayoutX(225);
        startButton.setLayoutY(470);
        startButton.setOnAction(e ->{
            startVisible.set(false);
            pongAnimation.playFromStart();
            pongComponents.requestFocus();
        });
        pongComponents = new Group(
                ball,
                topWall,
                bottomWall,
                leftPaddle,
                rightPaddle,
                startButton,
                rightWall,
                leftWall
        );
        pongComponents.setFocusTraversable(true);
        pongComponents.setOnKeyPressed(i -> {
            //if(i.getCode() == KeyCode.DOWN && pongAnimation.statusProperty().equals(Animation.Status.STOPPED)){
             //   rightPaddleY.setValue(rightPaddleY.getValue() - 10);
            //}
            if(i.getCode() == KeyCode.ENTER)
                pongAnimation.playFromStart();
            else if(i.getCode() == KeyCode.DOWN && !rightPaddle.getBoundsInParent().intersects(topWall.getBoundsInLocal())){
                rightPaddleY.setValue(rightPaddleY.getValue() + 10);
            }
            else if(i.getCode() == KeyCode.W && !leftPaddle.getBoundsInParent().intersects(topWall.getBoundsInLocal())){
                leftPaddleY.setValue(leftPaddleY.getValue() - 10);
            }
            else if(i.getCode() == KeyCode.S && !leftPaddle.getBoundsInParent().intersects(bottomWall.getBoundsInLocal())){
                leftPaddleY.setValue(leftPaddleY.getValue() + 10);
            }
            else if(i.getCode() == KeyCode.UP && !rightPaddle.getBoundsInParent().intersects(topWall.getBoundsInLocal())){
                rightPaddleY.setValue(rightPaddleY.getValue() - 10);
            }
        });
        Scene scene = new Scene(pongComponents, 500, 500);
        scene.setFill(Color.GRAY);
        
        ball.centerXProperty().bind(centerX);
        ball.centerYProperty().bind(centerY);
        leftPaddle.translateYProperty().bind(leftPaddleY);
        rightPaddle.translateYProperty().bind(rightPaddleY);
        startButton.visibleProperty().bind(startVisible);
        
        stage.setScene(scene);
        initialize();
        stage.setTitle("ZenPong Game");
        stage.show();
    }
}
