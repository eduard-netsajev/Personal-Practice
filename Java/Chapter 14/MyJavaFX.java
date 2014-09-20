import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MyJavaFX extends Application{

    @Override
    public void start(Stage primaryStage) {
        Button btOK = new Button("Click me!");
        Scene scene = new Scene(btOK, 500, 650);
        primaryStage.setTitle("MyJavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
