import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Square extends Pane {
	public Square(int row, int column, Game game) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Square.fxml"));
		loader.setRoot(this);
		final SquareController squareController = new SquareController(game, new Location(row, column));
		loader.setController(squareController);
		loader.load();
		GridPane.setColumnIndex(this, column);
		GridPane.setRowIndex(this, row);
	}
}
