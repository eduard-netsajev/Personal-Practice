import javafx.application.Application;
import javafx.beans.binding.ObjectBinding;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class Kimp extends Application {

    private Path path;
    private Rectangle rect;

    private double rsX;
    private double rsY;

    private Group shapes;
    private static final Double DEFAULTSTROKE = 3.0;
    private static final Double MAXSTROKE = 30.0;
    private static final Double MINSTROKE = 1.0;
    private static final Integer DEFAULTRED = 0;
    private static final Integer DEFAULTGREEN = 0;
    private static final Integer DEFAULTBLUE = 255;
    private static final Integer MAXRGB = 255;
    private static final Integer MINRGB = 0;
    private boolean drawingShape = false;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Kimp");
        final BorderPane root = new BorderPane();

        Scene scene = new Scene(root, 800, 500);

        // A group to hold all the drawn shapes
        shapes = new Group();

        // ToggleGroup to hold the selected drawing mode
        ToggleGroup modeChoice = new ToggleGroup();
        ToggleButton tbS = new ToggleButton("Stroke");
        tbS.setSelected(true);
        ToggleButton tbR = new ToggleButton("Rectangle");
        tbS.setToggleGroup(modeChoice);
        tbR.setToggleGroup(modeChoice);

        // VBox for the toggle buttons
        VBox toggleBox = new VBox(5);
        toggleBox.getChildren().addAll(tbS, tbR);

        // Build the slider, label, and button and their VBox layout container 
        Button btnClear = new Button();
        btnClear.setText("Clear");
        btnClear.setOnAction(event ->
                shapes.getChildren().removeAll(shapes.getChildren()));

        Slider strokeSlider = new Slider(MINSTROKE, MAXSTROKE, DEFAULTSTROKE);
        Label labelStroke = new Label("Stroke Width");
        VBox utilBox = new VBox(10);
        utilBox.setAlignment(Pos.TOP_CENTER);
        utilBox.getChildren().addAll(btnClear, labelStroke, strokeSlider);

        // Build the RGB sliders, labels, and HBox containers
        Slider redSlider = new Slider(MINRGB, MAXRGB, DEFAULTRED);
        Label labelRed = new Label("R");
        HBox rhbox = new HBox(5);
        rhbox.getChildren().addAll(labelRed, redSlider);

        Slider greenSlider = new Slider(MINRGB, MAXRGB, DEFAULTGREEN);
        Label labelGreen = new Label("G");
        HBox ghbox = new HBox(5);
        ghbox.getChildren().addAll(labelGreen, greenSlider);

        Slider blueSlider = new Slider(MINRGB, MAXRGB, DEFAULTBLUE);
        Label labelBlue = new Label("B");
        HBox bhbox = new HBox(5);
        bhbox.getChildren().addAll(labelBlue, blueSlider);

        // Build the VBox container for all the slider containers        
        VBox colorBox = new VBox(10);
        colorBox.setAlignment(Pos.TOP_CENTER);
        colorBox.getChildren().addAll(rhbox, ghbox, bhbox);

        // Put all controls in one HBox
        HBox toolBox = new HBox(77);
        toolBox.setAlignment(Pos.TOP_CENTER);
        toolBox.getChildren().addAll(toggleBox, utilBox, colorBox);

        // Build a Binding object to compute a Paint object from the sliders
        ObjectBinding<Paint> colorBinding = new ObjectBinding<Paint>() {

            {
                super.bind(redSlider.valueProperty(),
                        greenSlider.valueProperty(),
                        blueSlider.valueProperty());
            }

            @Override
            protected Paint computeValue() {
                return Color.rgb(redSlider.valueProperty().intValue(),
                        greenSlider.valueProperty().intValue(),
                        blueSlider.valueProperty().intValue());
            }
        };

        // Build the sample line and its layout container
        final Line sampleLine = new Line(0, 0, 140, 0);
        sampleLine.strokeWidthProperty().bind(strokeSlider.valueProperty());
        StackPane stackpane = new StackPane();
        stackpane.setPrefHeight(MAXSTROKE);
        stackpane.getChildren().add(sampleLine);
        // Bind to the Paint Binding object
        sampleLine.strokeProperty().bind(colorBinding);


        // Build the canvas
        final Pane canvas = new Pane();
        canvas.setCursor(Cursor.CROSSHAIR);

        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
//                System.out.println("Click");
                if (drawingShape) {
                    drawingShape = false;
                    return;
                }
                double a = sampleLine.getStrokeWidth() / 2.0;
                Rectangle point = new Rectangle(me.getSceneX() - a,
                        me.getSceneY() - a, a * 2.0, a * 2.0);
                point.setFill(sampleLine.getStroke());
                shapes.getChildren().add(point);
            }
        });

        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent me) {

//                System.out.println("Press");

                if (modeChoice.getSelectedToggle() == tbS) {

                    path = new Path();
                    path.setMouseTransparent(true);
                    path.setStrokeWidth(sampleLine.getStrokeWidth());
                    path.setStroke(sampleLine.getStroke());
                    shapes.getChildren().add(path);
                    path.getElements().add(
                            new MoveTo(me.getSceneX(), me.getSceneY()));
                } else if (modeChoice.getSelectedToggle() == tbR) {

                    // Rectangle-Start
                    rsX = me.getSceneX();
                    rsY = me.getSceneY();

                    rect = new Rectangle(rsX, rsY, 0, 0);
                    shapes.getChildren().add(rect);
                    rect.setFill(sampleLine.getStroke());
//                    System.out.println("CREATE RECT");
                }
            }
        });

        canvas.setOnMouseReleased(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent me) {
                if (modeChoice.getSelectedToggle() == tbS) {
                    path = null;
                } else if (modeChoice.getSelectedToggle() == tbR) {
//                    shapes.getChildren().add(rect);
//                    System.out.println("SAVE RECT");
                    rect = null;
                }
//                System.out.println("Release");
            }
        });

        canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent me) {
//                System.out.println("Drag");
                drawingShape = true;

                if (modeChoice.getSelectedToggle() == tbS) {
                    // keep lines within rectangle
                    if (canvas.getBoundsInLocal().contains(me.getX(), me.getY())) {
                        path.getElements().add(new LineTo(me.getSceneX(), me.getSceneY()));
                    }
                } else if (modeChoice.getSelectedToggle() == tbR) {
                    // clicked (release)

                    double meX = me.getSceneX();
                    double meY = me.getSceneY();

                    if (rsX < meX) {
                        if (rsY < meY) {

                            rect.setX(rsX);
                            rect.setY(rsY);
                            rect.setWidth(meX - rsX);
                            rect.setHeight(meY - rsY);
                        } else {
                            rect.setX(rsX);
                            rect.setY(meY);
                            rect.setWidth(meX - rsX);
                            rect.setHeight(rsY - meY);
                        }
                    } else {
                        if (rsY < meY) {

                            rect.setX(meX);
                            rect.setY(rsY);
                            rect.setWidth(rsX - meX);
                            rect.setHeight(meY - rsY);
                        } else {
                            rect.setX(meX);
                            rect.setY(meY);
                            rect.setWidth(rsX - meX);
                            rect.setHeight(rsY - meY);
                        }
                    }
                }
            }
        });

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ESCAPE) {
                    System.exit(0);
                }
            }
        });

        // Build the VBox container for the toolBox, sampleline, and canvas
        VBox vb = new VBox(20);
        vb.setPrefWidth(scene.getWidth() - 20);
        vb.setLayoutY(20);
        vb.setLayoutX(10);
        vb.getChildren().addAll(toolBox, stackpane);
        root.setTop(vb);
        root.getChildren().addAll(shapes);

        root.setCenter(canvas);

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}