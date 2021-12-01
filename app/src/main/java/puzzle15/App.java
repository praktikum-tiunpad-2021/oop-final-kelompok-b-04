package puzzle15;

import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class App extends Application {
	static int lvl = 1;
	static boolean win = false;
	Label moves = new Label("Moves: 0");
	Label level = new Label();
	int nMoves = 1;
	static int puzzleSize = 4;

	@Override
	public void start(Stage primaryStage) {
		Logic lg = new Logic(4);

		VBox root = new VBox();

		HBox top = new HBox();

		Button newGame = new Button("Reset");
		newGame.setPrefHeight(30);
		newGame.setPrefWidth(100);// 80

		Button restartLevel = new Button("Restart Level");
		restartLevel.setPrefHeight(30);
		restartLevel.setPrefWidth(106);// 85

		Button quit = new Button("Quit");
		quit.setPrefHeight(30);
		quit.setPrefWidth(53);// 50

		// grid
		HBox stats = new HBox();
		GridPane grid = new GridPane();
		Button arr[][] = new Button[4][4];

		// assign grid
		int i, j;
		for (i = 0; i < 4; i++)
			for (j = 0; j < 4; j++) {
				arr[i][j] = new Button();
				grid.add(arr[i][j], j, i);
				arr[i][j].setPrefHeight(60);
				arr[i][j].setPrefWidth(60);
			}

		GeneratePuzzle(arr, lg);
		newGame.setOnAction(e -> {
			if (!win) {
				lvl = 1;
				GeneratePuzzle(arr, lg);
			}
		});

		restartLevel.setOnAction(e -> {
			if (!win)
				GeneratePuzzle(arr, lg);
		});
		quit.setOnAction(e -> System.exit(0));

		// hiasan doang
		HBox.setMargin(restartLevel, new Insets(0, 0, 0, 5));
		HBox.setMargin(quit, new Insets(0, 0, 0, 5));
		HBox.setMargin(moves, new Insets(0, 0, 0, 15));
		HBox.setMargin(level, new Insets(0, 0, 0, 143));
		top.setPadding(new Insets(10, 0, 5, 11));
		VBox.setMargin(grid, new Insets(5, 0, 0, 50));

		top.getChildren().addAll(newGame, restartLevel, quit);
		stats.getChildren().addAll(moves, level);
		root.getChildren().addAll(top, stats, grid);
		Scene scene = new Scene(root, 325, 350);

		primaryStage.setTitle("Fifteen Puzzle");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	void Slide(Button arr[][], Logic lg, int x, int y) {
		// cek apa button yang diclick adjacent dengan button kosong ""
		// kalau ada case yg belum dihandle^^ tambahin aja
		if (Math.abs(x - lg.getBlankX()) != 1 && Math.abs(y - lg.getBlankY()) != 1) {
			return;
		}
		if(lg.getBlankX() > x){
			lg.moveUp();
			GeneratePuzzle(arr, lg);
		}else if(lg.getBlankX() < x){
			lg.moveDown();
			GeneratePuzzle(arr, lg);
		}else if(lg.getBlankY() > y){
			lg.moveLeft();
			GeneratePuzzle(arr, lg);
		}
		else if(lg.getBlankY() < y){
			lg.moveRight();
			GeneratePuzzle(arr, lg);
		}

	}

	void GeneratePuzzle(Button arr[][], Logic lg) {
		win = false;
		moves.setText("Moves: 0");
		nMoves = 1;
		level.setText("Level: " + Integer.toString(lvl));

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (lg.getGridElem(i, j).intValue() == 0) {
					arr[i][j].setText("");
					continue;
				}
				arr[i][j].setText(lg.getGridElem(i, j).toString());
			}
		}

		for ( int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				int x = i; int y = j;
				arr[i][j].setOnAction(e -> Slide(arr, lg, x, y));
			}
		}
		Check(lg, arr);
	}

	void Check(Logic lg, Button[][] arr) {
		moves.setText("Moves: " + Integer.toString(nMoves++));
		if (lg.isSolved()) {
			Win(arr, lg);
		}
	}

	void Win(Button arr[][], Logic lg) {
		win = true;

		Stage finishStage = new Stage();
		VBox finishVBox = new VBox();

		Label finish = new Label("Successful");

		Button nextLevel = new Button("Next Level");

		Button quit = new Button("Quit");

		nextLevel.setOnAction(e2 -> {
			finishStage.close();
			lvl++;
			GeneratePuzzle(arr, lg);
		});

		quit.setOnAction(e -> System.exit(0));

		finishStage.setOnCloseRequest(e -> Win(arr, lg));

		finishVBox.setAlignment(Pos.TOP_CENTER);
		VBox.setMargin(finish, new Insets(10, 0, 0, 0));
		VBox.setMargin(nextLevel, new Insets(10, 0, 0, 0));
		VBox.setMargin(quit, new Insets(5, 0, 0, 0));

		finishVBox.getChildren().addAll(finish, nextLevel, quit);
		Scene finishScene = new Scene(finishVBox, 220, 110);
		finishStage.setTitle("Level Completed");
		finishStage.setResizable(false);
		finishStage.setAlwaysOnTop(true);
		finishStage.setScene(finishScene);
		finishStage.show();
	}

	public static void main(String[] args) {
		puzzleSize = 4;
		launch(args);
	}

}