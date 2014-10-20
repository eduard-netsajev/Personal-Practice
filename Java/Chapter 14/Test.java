import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
public class Test extends Application {
    public Test() {
        System.out.println("Test constructor is invoked");
    }
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        System.out.println("start method is invoked");
        Button btOK = new Button("Click me!");
        Scene scene = new Scene(btOK, 500, 650);
        primaryStage.setTitle("MyJavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        System.out.println("launch application");
        Application.launch(args);
    }
}