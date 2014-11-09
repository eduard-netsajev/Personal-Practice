import javafx.application.Application;
import javafx.beans.binding.ObjectBinding;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class Kimp extends Application {

    private final DropShadow shadow = new DropShadow(15, Color.BLACK);

    private Pane canvas;
    private Path path;
    private Rectangle rect;
    private Line sampleLine;

    private double rsX;
    private double rsY;

    private static final Double DEFAULTSTROKE = 3.0;
    private static final Double MAXSTROKE = 30.0;
    private static final Double MINSTROKE = 1.0;
    private static final Integer DEFAULTRED = 0;
    private static final Integer DEFAULTGREEN = 0;
    private static final Integer DEFAULTBLUE = 255;
    private static final Integer MAXRGB = 255;
    private static final Integer MINRGB = 0;
    private boolean drawingShape = false;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    private boolean dirMode = false;
    private ToggleGroup modeChoice;
    private ToggleButton tbS;
    private ToggleButton tbR;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Kimp");
        final BorderPane root = new BorderPane();

        Scene scene = new Scene(root, 1000, 800);


        // Build the canvas
        canvas = new Pane();
        canvas.setCursor(Cursor.CROSSHAIR);

        EventHandler<KeyEvent> filter = ke -> {
            if (ke.getCode() == KeyCode.SPACE) {
                ke.consume();
                redo();
            }
        };

        scene.addEventFilter(KeyEvent.KEY_PRESSED, filter);
        // ToggleGroup to hold the selected drawing mode
        modeChoice = new ToggleGroup();
        tbS = new ToggleButton("Stroke");
        tbS.setSelected(true);
        tbR = new ToggleButton("Rectangle");
        tbS.setToggleGroup(modeChoice);
        tbR.setToggleGroup(modeChoice);

        // VBox for the toggle buttons
        VBox toggleBox = new VBox(10);
        toggleBox.getChildren().addAll(tbS, tbR);

        // VBox for the buffer buttons
        VBox bufferBox = new VBox(10);
        Button unB = new Button("Undo");
        unB.setOnAction(event -> undo());

        Button reB = new Button("Redo");
        reB.setOnAction(event -> redo());
        bufferBox.getChildren().addAll(unB, reB);

        // Build the slider, label, and button and their VBox layout container 
        Button btnClear = new Button();
        btnClear.setText("Clear");
        btnClear.setOnAction(event ->
                canvas.getChildren().removeAll(canvas.getChildren()));

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
        toolBox.getChildren().addAll(bufferBox, toggleBox, utilBox, colorBox);

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
        sampleLine = new Line(0, 0, 140, 0);
        sampleLine.strokeWidthProperty().bind(strokeSlider.valueProperty());
        StackPane stackpane = new StackPane();
        stackpane.setPrefHeight(MAXSTROKE);
        stackpane.getChildren().add(sampleLine);
        // Bind to the Paint Binding object
        sampleLine.strokeProperty().bind(colorBinding);

        canvas.setOnMouseClicked(clickHandler);
        canvas.setOnMousePressed(pressHandler);
        canvas.setOnMouseReleased(releaseHandler);
        canvas.setOnMouseDragged(drugHandler);

        scene.setOnKeyPressed(ke -> {
            if (ke.getCode() == KeyCode.ESCAPE) {

                if (currentAction > 0) {
                    undo();
                } else {

                    Stage stage = new Stage();
                    stage.setTitle("Exit Confirmation");
                    // Set a scene with a button in the stage
                    VBox pane = new VBox(10);
                    Label question = new Label("Are you sure you want to leave the program?");

                    HBox choice = new HBox(10);
                    Button yes = new Button("Yes");
                    Button no = new Button("No");

                    no.setOnAction(event -> stage.close());
                    yes.setOnAction(event -> System.exit(0));

                    choice.getChildren().addAll(yes, no);
                    pane.getChildren().addAll(question, choice);

                    BorderPane pp = new BorderPane();
                    pp.setCenter(pane);
                    stage.setScene(new Scene(pp));
                    stage.show();
                }

            } else if (ke.getCode() == KeyCode.CONTROL) {
                dirMode = true;
            } else if (ke.getCode() == KeyCode.SPACE) {
                ke.consume();
                redo();
            }
        });

        scene.setOnKeyReleased(ke -> {
            if (ke.getCode() == KeyCode.CONTROL) {
                dirMode = false;
            }
        });

        // Build the VBox container for the toolBox and sampleline
        VBox vb = new VBox(20);
        vb.setPrefWidth(scene.getWidth() - 20);
        vb.setLayoutY(20);
        vb.setLayoutX(10);
        vb.getChildren().addAll(toolBox, stackpane);
        root.setTop(vb);
        //root.getChildren().addAll(shapes);

        root.setCenter(canvas);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent me) {
            if (dirMode) {
                if (me.getButton() == MouseButton.SECONDARY && me.getSource() instanceof Shape) {
                    buffer[currentAction] = new EraseAction((Shape) me.getSource());
                    canvas.getChildren().remove(me.getSource());
                    currentAction++;
                    maxAction = currentAction;
                }
            } else {
                if (me.getButton() != MouseButton.PRIMARY) return;
                if (drawingShape) {
                    drawingShape = false;
                    return;
                }

                double a = sampleLine.getStrokeWidth() / 2.0;
                Rectangle point = new Rectangle(me.getX() - a,
                        me.getY() - a, a * 2.0, a * 2.0);
                buffer[currentAction] = new DrawAction(point);
                currentAction++;
                maxAction = currentAction;
                point.setFill(sampleLine.getStroke());
                point.setOnMousePressed(pressHandler);
                point.setOnMouseDragged(drugHandler);
                point.setOnMouseEntered(enterHandler);
                point.setOnMouseExited(exitHandler);
                point.setOnMouseClicked(clickHandler);
                canvas.getChildren().add(point);
            }
        }
    };

    EventHandler<MouseEvent> drugHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent me) {
            if (me.getButton() != MouseButton.PRIMARY) return;
            if (dirMode) {
                double offsetX = me.getSceneX() - orgSceneX;
                double offsetY = me.getSceneY() - orgSceneY;
                double newTranslateX = orgTranslateX + offsetX;
                double newTranslateY = orgTranslateY + offsetY;



                if (me.getSource() instanceof Rectangle) {
                    ((Rectangle)(me.getSource())).setTranslateX(newTranslateX);
                    ((Rectangle)(me.getSource())).setTranslateY(newTranslateY);
                } else if (me.getSource() instanceof Path) {
                    ((Path)(me.getSource())).setTranslateX(newTranslateX);
                    ((Path)(me.getSource())).setTranslateY(newTranslateY);
                }

                if(me.getSource() instanceof Shape &&
                        buffer[currentAction -1] instanceof MoveAction) {
                    MoveAction ma = (MoveAction) buffer[currentAction -1];
                    ma.newX = newTranslateX;
                    ma.newY = newTranslateY;
                }
            } else {
                drawingShape = true;
                if (modeChoice.getSelectedToggle() == tbS && path != null) {
                    LineTo lineTo = new LineTo(me.getX(), me.getY());
                    path.getElements().add(lineTo);
                } else if (modeChoice.getSelectedToggle() == tbR && rect != null) {
                    // clicked (release)

                    double meX = me.getX();
                    double meY = me.getY();

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
        }
    };

    EventHandler<MouseEvent> pressHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent me) {
            if (me.getButton() != MouseButton.PRIMARY) return;
            if (dirMode) {
                orgSceneX = me.getSceneX();
                orgSceneY = me.getSceneY();

                if (me.getSource() instanceof Rectangle) {
                    orgTranslateX = ((Rectangle) (me.getSource())).getTranslateX();
                    orgTranslateY = ((Rectangle) (me.getSource())).getTranslateY();
                } else if (me.getSource() instanceof Path) {
                    orgTranslateX = ((Path) (me.getSource())).getTranslateX();
                    orgTranslateY = ((Path) (me.getSource())).getTranslateY();
                }
                if (me.getSource() instanceof Shape) {
                    MoveAction ma = new MoveAction((Shape) me.getSource());
                    ma.oldX = orgTranslateX;
                    ma.oldY = orgTranslateY;
                    buffer[currentAction] = ma;
                    currentAction++;
                    maxAction = currentAction;
                }
            } else {

                if (modeChoice.getSelectedToggle() == tbS) {

                    path = new Path();

                    buffer[currentAction] = new DrawAction(path);
                    currentAction++;
                    maxAction = currentAction;

                    path.setStrokeWidth(sampleLine.getStrokeWidth());
                    path.setStroke(sampleLine.getStroke());

                    path.setOnMousePressed(pressHandler);
                    path.setOnMouseDragged(drugHandler);
                    path.setOnMouseEntered(enterHandler);
                    path.setOnMouseExited(exitHandler);
                    path.setOnMouseClicked(clickHandler);

                    canvas.getChildren().add(path);
                    path.getElements().add(
                            new MoveTo(me.getX(), me.getY()));

                } else if (modeChoice.getSelectedToggle() == tbR) {

                    // Rectangle-Start
                    rsX = me.getX();
                    rsY = me.getY();

                    rect = new Rectangle(rsX, rsY, 0, 0);
                    rect.setFill(sampleLine.getStroke());
                    canvas.getChildren().add(rect);

                    buffer[currentAction] = new DrawAction(rect);
                    currentAction++;
                    maxAction = currentAction;

                    rect.setOnMousePressed(pressHandler);
                    rect.setOnMouseDragged(drugHandler);
                    rect.setOnMouseClicked(clickHandler);
                    rect.setOnMouseEntered(enterHandler);
                    rect.setOnMouseExited(exitHandler);
                }
            }
        }
    };

    EventHandler<MouseEvent> releaseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (modeChoice.getSelectedToggle() == tbS) {
                path = null;
            } else if (modeChoice.getSelectedToggle() == tbR) {
                rect = null;
            }
        }
    };

    EventHandler<MouseEvent> enterHandler = me -> {
        if (dirMode) {
            Object x = me.getSource();
            if (x instanceof Rectangle) {
                ((Rectangle) x).setEffect(shadow);
            } else if (x instanceof Path) {
                ((Path) x).setEffect(shadow);
            }
        }
    };

    EventHandler<MouseEvent> exitHandler = me -> {
        Object x = me.getSource();
        if (x instanceof Rectangle) {
            ((Rectangle) x).setEffect(null);
        } else if (x instanceof Path) {
            ((Path) x).setEffect(null);
        }
    };

    private int maxAction = 0;
    private int currentAction = 0;

    Action[] buffer = new Action[10000];

    private void redo() {
        if (currentAction < maxAction) {
            Action action = buffer[currentAction];
            if (action instanceof DrawAction) {
                DrawAction dA = (DrawAction) action;
                canvas.getChildren().add(dA.shape);
            } else if (action instanceof EraseAction) {
                EraseAction dA = (EraseAction) action;
                canvas.getChildren().remove(dA.shape);
            } else if (action instanceof MoveAction) {
                MoveAction dA = (MoveAction) action;
                Shape shape = dA.shape;
                shape.setTranslateX(dA.newX);
                shape.setTranslateY(dA.newY);
            }
            currentAction++;
        }
    }

    private void undo() {
        if (currentAction > 0) {
            currentAction--;
            Action action = buffer[currentAction];
            if (action instanceof DrawAction) {
                DrawAction dA = (DrawAction) action;
                canvas.getChildren().remove(dA.shape);
            } else if (action instanceof EraseAction) {
                EraseAction dA = (EraseAction) action;
                canvas.getChildren().add(dA.shape);
            } else if (action instanceof MoveAction) {
                MoveAction dA = (MoveAction) action;
                Shape shape = dA.shape;
                shape.setTranslateX(dA.oldX);
                shape.setTranslateY(dA.oldY);
            }
        }
    }
}

interface Action{}

class DrawAction implements Action {

    Shape shape;

    public DrawAction (Shape shape) {
        this.shape = shape;
    }
}

class EraseAction implements Action {

    Shape shape;

    public EraseAction (Shape shape) {
        this.shape = shape;
    }
}

class MoveAction implements Action {

    Shape shape;

    double oldX, oldY, newX, newY;

    public MoveAction (Shape shape) {
        this.shape = shape;
    }
}