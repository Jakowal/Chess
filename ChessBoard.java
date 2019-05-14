import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.event.*;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.layout.HBox;

public class ChessBoard extends Application {

  class Clickhandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent e) {
      System.out.println(e.getSource());
    }
  }

  class Stophandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent e) {
      Platform.exit();
    }
  }

  class Tile extends Button {
    Color colour;
    Piece piece;

	  Tile() {
      super(" ");
      setFont(new Font(10));
      setMinSize(50, 50);
    }

    public void setPiece(Piece p) {
      piece = p;
    }

    public void setImage(Image i) {
      setGraphic(new ImageView(i));
    }
  }

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage teater) throws FileNotFoundException {

    Text statusinfo = new Text("White player's turn - Pick a piece");
	  statusinfo.setFont(new Font(15));
    statusinfo.setX(10);  statusinfo.setY(500);

    Button stopButton = makeStopButton();
    GridPane chessBoard = makeBoard();

    Pane pane = new Pane();
    pane.setPrefSize(600, 600);
    pane.getChildren().add(chessBoard);
    pane.getChildren().add(statusinfo);
    pane.getChildren().add(stopButton);
    setText(pane);

    Scene scene = new Scene(pane);
    teater.setTitle("Chess");
    teater.setScene(scene);
    teater.show();
  }

  private Button makeStopButton() {
    Button stopbutton = new Button("Stop");
    stopbutton.setLayoutX(10);
    stopbutton.setLayoutY(520);
    Stophandler stop = new Stophandler();
    stopbutton.setOnAction(stop);
    return stopbutton;
  }

  private void setText(Pane pane) {
    String[] letters = {"A","B","C","D","E","F","G","H"};
    String[] numbers = {"8","7","6","5","4","3","2","1"};
    for (int i = 0; i < 8; i++) { //Set letters on the top side.
      Text text = new Text(letters[i]);
      text.setFont(new Font(15));
      text.setX((i*50)+50);  text.setY(20);
      pane.getChildren().add(text);
    }
    for (int i = 0; i < 8; i++) { //Set letters on the bottom side.
      Text text = new Text(letters[i]);
      text.setFont(new Font(15));
      text.setX((i*50)+50);  text.setY(450);
      pane.getChildren().add(text);
    }
    for (int i = 0; i < 8; i++) { //Set numbers on the left side.
      Text text = new Text(numbers[i]);
      text.setFont(new Font(15));
      text.setX(15);  text.setY((i*50)+60);
      pane.getChildren().add(text);
    }
    for (int i = 0; i < 8; i++) { //Set numbers on the right side.
      Text text = new Text(numbers[i]);
      text.setFont(new Font(15));
      text.setX(440);  text.setY((i*50)+60);
      pane.getChildren().add(text);
    }
  }

  private GridPane makeBoard() {
    Tile[][] board = new Tile[8][8];
    GridPane chessBoard = new GridPane();
    chessBoard.setGridLinesVisible(true);
    chessBoard.setLayoutX(30);  chessBoard.setLayoutY(30);
    Clickhandler click = new Clickhandler();
    boolean lastWhite = false;
    for (int i = 0; i < 8; i++) {
      for (int h = 0; h < 8; h++) {
        Tile tile = new Tile();
        if (!lastWhite) {
          tile.setStyle("-fx-background-color: #faf5ef; -fx-border-color: #000000; -fx-border-width: 1px;");
          lastWhite = true;
        }
        else {
          tile.setStyle("-fx-background-color: #3f2a14; -fx-border-color: #000000; -fx-border-width: 1px;");
          lastWhite = false;
        }
        board[i][h] = tile;
  	    board[i][h].setOnAction(click);
      }
      lastWhite = !lastWhite;
    }
    for (int i = 0; i < 8; i++) {
      for (int h = 0; h< 8; h++) {
        chessBoard.add(board[i][h], i, h);
      }
    }
    return chessBoard;
  }
}
