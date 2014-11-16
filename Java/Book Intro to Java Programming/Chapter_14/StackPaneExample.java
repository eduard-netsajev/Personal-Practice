package Chapter_14;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

public class StackPaneExample extends Application {
    @Override
    public void start(Stage primaryStage) {
        StackPane pane = new StackPane();
        Circle circle = new Circle(100, Color.AQUA);
        pane.getChildren().add(circle);
        pane.getChildren().add(new Button("OK"));
        Circle circ = new Circle(50, Color.DEEPPINK);
        circ.radiusProperty().bind(pane.heightProperty().divide(5));
        circle.radiusProperty().bind(pane.widthProperty().divide(8));
        pane.getChildren().add(circ);
        Button btn = new Button("Okay");
        btn.setFont(Font.font("Times new Roman", FontWeight.LIGHT, FontPosture.ITALIC, 40.0));
        pane.getChildren().add(btn);
        Scene scene = new Scene(pane, 1400, 250);
        primaryStage.setTitle("Stack Pane");
        primaryStage.setScene(scene);
        pane.setRotate(30);
        primaryStage.show();
    }
}