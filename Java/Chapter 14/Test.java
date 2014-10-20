import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Test extends Application {
    public Test() {
        System.out.println("Test constructor is invoked");
    }


    static boolean big;

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        System.out.println("start method is invoked");
        Button btOK = new Button("Click me!");
        Circle circle = new Circle(50);
        Button btShake = new Button("Shake!");

        Rectangle rect = new Rectangle(20, 40);

        Circle c = null;
        BorderPane bp = new BorderPane(circle, c, btShake, btOK, rect);

        btOK.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(big) {
                    circle.setRadius(50);
                }
                else {
                    circle.setRadius(150);
                }
                big = !big;
            }
        });

        btShake.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                circle.setCenterX((int) (Math.random() * 20) + 200);
                circle.setCenterY((int) (Math.random() * 20) + 200);

            }
        });


        Scene scene = new Scene(bp, 500, 650);
        primaryStage.setTitle("MyJavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        System.out.println("launch application");
        Application.launch(args);
    }
}