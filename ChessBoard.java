import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.Node;
import javafx.event.*;

import java.util.*;
import java.io.*;

public class ChessBoard extends Application {

  public static final String[] letters = {"A","B","C","D","E","F","G","H"};
  public static final String[] numbers = {"8","7","6","5","4","3","2","1"};
  public static Text statusinfo = new Text("");
  public static boolean finished = false;
  private static Tile[][] board = new Tile[8][8];
  public static GridPane chessBoard;


  class Clickhandler implements EventHandler<ActionEvent> {

    public String turn = "White";
    private boolean firstClicked = false;
    private Tile fromTile;
    private int oldX;
    private int oldY;

    @Override
    public void handle(ActionEvent e) {
      if (!ChessBoard.finished) {
        Node source = (Node)e.getSource();
        int x = GridPane.getColumnIndex(source);
        int y = GridPane.getRowIndex(source);
        Tile tile = ChessBoard.getTile(x,y);
        if (!firstClicked && tile.getPiece() != null && tile.getPiece().getColour() != turn) {
          statusinfo.setText(turn + " player's turn - Pick a piece OF YOUR COLOUR");
        }
        else if (!firstClicked && tile.getPiece() != null) {
          firstClick(tile,x,y);
          statusinfo.setText(turn + " player's turn - Pick a location");
          firstClicked = true;
        }
        else if (firstClicked && x == oldX && y == oldY) {
          oldX = x;
          oldY = y;
        }
        else if (firstClicked) {
          secondClick(tile,x,y);
          firstClicked = false;
        }
        else if (!firstClicked) {
          statusinfo.setText(turn + " player's turn - Pick a piece");
          firstClicked = false;
        }
      }
    }
    private void firstClick(Tile t, int x, int y) {
      fromTile = t;
      oldX = x;
      oldY = y;
    }

    private boolean secondClick(Tile t, int x, int y) {
      boolean clicked = !fromTile.getPiece().move(oldX,oldY,x,y,t);
      if (turn == "White" && !clicked) {
        turn = "Black";
      }
      else if (!clicked) {
        turn = "White";
      }
      else {
        statusinfo.setText(turn + " player's turn - Pick a location");
        return false;
      }
      statusinfo.setText(turn + " player's turn - Pick a piece");
      return true;
    }
  }

  class SaveHandler implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent e) {
      for (int i = 0; i < 6; i++) {
        try {
          saveGame();
        }
        catch (FileNotFoundException fe) { System.out.println("SaveHandler exception: " + fe);}
        catch (UnsupportedEncodingException ue) {System.out.println("SaveHandler exception: " + ue);}
      }
    }

    private void saveGame() throws FileNotFoundException, UnsupportedEncodingException {
      PrintWriter writer = new PrintWriter("Savegame.txt", "UTF-8");
      String turn;
      writer.println(new Clickhandler().turn);
      writer.println("#, Pawns, {tileLetter,tileNumber,colour}");
      for(Tile[] t : board) {
        for (Tile v : t) {
          if (v.getPiece() instanceof Pawn) {
            int num = Integer.parseInt(ChessBoard.numbers[v.getNumber()-1]);
            int count = 0;
            for (int i = 0; i < 8; i++) {
              if (ChessBoard.letters[i] == v.getLetter()) {
                count = i-1;
                break;
              }
            }
            int letter = count;
            writer.println(count + ", " + num + ", " + v.getPiece().getColour());
          }
        }
      }
      writer.println("#, Rooks, {tileLetter,tileNumber,colour}");
      for(Tile[] t : board) {
        for (Tile v : t) {
          if (v.getPiece() instanceof Rook) {
            int num = Integer.parseInt(ChessBoard.numbers[v.getNumber()-1]);
            int count = 0;
            for (int i = 0; i < 7; i++) {
              if (ChessBoard.letters[i] == v.getLetter()) {
                count = i-1;
                break;
              }
            }
            int letter = count;
            writer.println(count + ", " + num + ", " + v.getPiece().getColour());
          }
        }
      }
      writer.println("#, Bishops, {tileLetter,tileNumber,colour}");
      for(Tile[] t : board) {
        for (Tile v : t) {
          if (v.getPiece() instanceof Bishop) {
            int num = Integer.parseInt(ChessBoard.numbers[v.getNumber()-1]);
            int count = 0;
            for (int i = 0; i < 7; i++) {
              if (ChessBoard.letters[i] == v.getLetter()) {
                count = i-1;
                break;
              }
            }
            int letter = count;
            writer.println(count + ", " + num + ", " + v.getPiece().getColour());
          }
        }
      }
      writer.println("#, Knights, {tileLetter,tileNumber,colour}");
      for(Tile[] t : board) {
        for (Tile v : t) {
          if (v.getPiece() instanceof Knight) {
            int num = Integer.parseInt(ChessBoard.numbers[v.getNumber()-1]);
            int count = 0;
            for (int i = 0; i < 7; i++) {
              if (ChessBoard.letters[i] == v.getLetter()) {
                count = i-1;
                break;
              }
            }
            int letter = count;
            writer.println(count + ", " + num + ", " + v.getPiece().getColour());
          }
        }
      }
      writer.println("#, Queens, {tileLetter,tileNumber,colour}");
      for(Tile[] t : board) {
        for (Tile v : t) {
          if (v.getPiece() instanceof Queen) {
            int num = Integer.parseInt(ChessBoard.numbers[v.getNumber()-1]);
            int count = 0;
            for (int i = 0; i < 7; i++) {
              if (ChessBoard.letters[i] == v.getLetter()) {
                count = i-1;
                break;
              }
            }
            int letter = count;
            writer.println(count + ", " + num + ", " + v.getPiece().getColour());
          }
        }
      }
      writer.println("#, Kings, {tileLetter,tileNumber,colour}");
      for(Tile[] t : board) {
        for (Tile v : t) {
          if (v.getPiece() instanceof King) {
            int num = Integer.parseInt(ChessBoard.numbers[v.getNumber()-1]);
            int count = 0;
            for (int i = 0; i < 8; i++) {
              if (ChessBoard.letters[i] == v.getLetter()) {
                count = i-1;
                break;
              }
            }
            int letter = count;
            writer.println(count + ", " + num + ", " + v.getPiece().getColour());
          }
        }
      }
      writer.close();
    }
  }

  class Stophandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent e) {
      Platform.exit();
    }
  }

  class Loadhandler implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent e) {
      try {
        File savegame = new File("Savegame.txt");
        setBoard(readFromFile(savegame));
      }
      catch (FileNotFoundException fe) {
        System.out.println("File not found.");
      }
    }

    public GridPane readFromFile(File file) throws FileNotFoundException {
      Scanner scanner = null;
      try {
        scanner = new Scanner(file);
      }
      catch(FileNotFoundException e) {
        System.out.println("No savegame found, starting new game");
        return new GridPane();

      }
      String turn = scanner.nextLine();
      statusinfo.setText(turn + " player's turn - Pick a piece");
      String read = scanner.nextLine();
      GridPane b = ChessBoard.chessBoard;

      while (scanner.hasNextLine()) {
        String[] data = read.split(", ");

        if (data[1].compareTo("Pawns") == 0) {
          while (scanner.hasNextLine()) {
            read = scanner.nextLine();
            if (read.charAt(0) == '#') {
                break;
            }
            String[] in = read.split(", ");
            int tileX = Integer.parseInt(in[0]);
            int tileY = Integer.parseInt(in[1]);
            String colour = in[2];
            getTile(tileX, tileY).setPiece(new Pawn(colour,getTile(tileX, tileY)));
          }
        }
        else if (data[1].compareTo("Rooks") == 0) {
          while (scanner.hasNextLine()) {
            read = scanner.nextLine();
            if (read.charAt(0) == '#') {
                break;
            }
            String[] in = read.split(", ");
            int tileX = Integer.parseInt(in[0]);
            int tileY = Integer.parseInt(in[1]);
            String colour = in[2];
            getTile(tileX, tileY).setPiece(new Rook(colour,getTile(tileX, tileY)));
          }
        }
        else if (data[1].compareTo("Bishops") == 0) {
          while (scanner.hasNextLine()) {
            read = scanner.nextLine();
            if (read.charAt(0) == '#') {
                break;
            }
            String[] in = read.split(", ");
            int tileX = Integer.parseInt(in[0]);
            int tileY = Integer.parseInt(in[1]);
            String colour = in[2];
            getTile(tileX, tileY).setPiece(new Bishop(colour,getTile(tileX, tileY)));
          }
        }
        else if (data[1].compareTo("Knights") == 0) {
          while (scanner.hasNextLine()) {
            read = scanner.nextLine();
            if (read.charAt(0) == '#') {
                break;
            }
            String[] in = read.split(", ");
            int tileX = Integer.parseInt(in[0]);
            int tileY = Integer.parseInt(in[1]);
            String colour = in[2];
            getTile(tileX, tileY).setPiece(new Knight(colour,getTile(tileX, tileY)));
          }
        }
        else if (data[1].compareTo("Queens") == 0) {
          while (scanner.hasNextLine()) {
            read = scanner.nextLine();
            if (read.charAt(0) == '#') {
                break;
            }
            String[] in = read.split(", ");
            int tileX = Integer.parseInt(in[0]);
            int tileY = Integer.parseInt(in[1]);
            String colour = in[2];
            getTile(tileX, tileY).setPiece(new Queen(colour,getTile(tileX, tileY)));
          }
        }
        else if (data[1].compareTo("Kings") == 0) {
          while (scanner.hasNextLine()) {
            read = scanner.nextLine();
            if (read.charAt(0) == '#') {
                break;
            }
            String[] in = read.split(", ");
            int tileX = Integer.parseInt(in[0]);
            int tileY = Integer.parseInt(in[1]);
            String colour = in[2];
            getTile(tileX, tileY).setPiece(new King(colour,getTile(tileX, tileY)));
          }
        }
      }
      return b;
    }
  }

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage teater) throws FileNotFoundException {

    statusinfo.setText("White player's turn - Pick a piece");
	  statusinfo.setFont(new Font(15));
    statusinfo.setX(10);  statusinfo.setY(500);

    Button stopButton = makeStopButton();
    Button saveButton = makeSaveButton();
    Button loadButton = makeLoadButton();
    setBoard(makeBoard());

    Pane pane = new Pane();
    pane.setPrefSize(600, 600);
    pane.getChildren().add(chessBoard);
    pane.getChildren().add(statusinfo);
    pane.getChildren().add(stopButton);
    pane.getChildren().add(saveButton);
    pane.getChildren().add(loadButton);
    setText(pane);
    placePieces();

    Scene scene = new Scene(pane);
    teater.setTitle("Chess");
    teater.setScene(scene);
    teater.show();
  }

  public static Tile getTile(int l, int n) {
    return board[l][n];
  }

  public void setBoard(GridPane b) {
    chessBoard = b;
  }

  private Button makeStopButton() {
    Button stopbutton = new Button("Stop");
    stopbutton.setLayoutX(10);
    stopbutton.setLayoutY(520);
    Stophandler stop = new Stophandler();
    stopbutton.setOnAction(stop);
    return stopbutton;
  }

  private Button makeSaveButton() {
    Button savebutton = new Button("Save");
    savebutton.setLayoutX(60);
    savebutton.setLayoutY(520);
    SaveHandler save = new SaveHandler();
    savebutton.setOnAction(save);
    return savebutton;
  }

  private Button makeLoadButton() {
    Button loadbutton = new Button("Load");
    loadbutton.setLayoutX(110);
    loadbutton.setLayoutY(520);
    Loadhandler load = new Loadhandler();
    loadbutton.setOnAction(load);
    return loadbutton;
  }

  private void setText(Pane pane) {
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
    GridPane chessBoard = new GridPane();
    chessBoard.setGridLinesVisible(true);
    chessBoard.setLayoutX(30);  chessBoard.setLayoutY(30);
    Clickhandler click = new Clickhandler();
    boolean lastWhite = false;
    for (int i = 0; i < 8; i++) {
      for (int h = 0; h < 8; h++) {
        Tile tile;
        if (!lastWhite) {
          tile = new Tile("White", numbers[h], letters[i]);
          tile.setStyle("-fx-background-color: #faf5ef; -fx-border-color: #000000; -fx-border-width: 1px;");
          lastWhite = true;
        }
        else {
          tile = new Tile("Black", numbers[h], letters[i]);
          tile.setStyle("-fx-background-color: #3f2a14; -fx-border-color: #000000; -fx-border-width: 1px;");
          lastWhite = false;
        }
        board[i][h] = tile;
        board[i][h].setOnAction(click);
      }
      lastWhite = !lastWhite;
    }
    for (int i = 0; i < 8; i++) {
      for (int h = 0; h < 8; h++) {
        chessBoard.add(board[i][h], i, h);
      }
    }
    return chessBoard;
  }

  private void placePieces() {
    placePawns();
    placeRooks();
    placeKnights();
    placeBishops();
    placeQueens();
    placeKings();
  }

  private void placeKings() {
    board[4][0].setPiece(new King("Black", board[4][0]));
    board[4][7].setPiece(new King("White", board[4][7]));
  }

  private void placeQueens() {
    board[3][0].setPiece(new Queen("Black", board[3][0]));
    board[3][7].setPiece(new Queen("White", board[3][7]));
  }

  private void placeBishops() {
    board[2][0].setPiece(new Bishop("Black", board[2][0]));
    board[5][0].setPiece(new Bishop("Black", board[5][0]));
    board[2][7].setPiece(new Bishop("White", board[2][7]));
    board[5][7].setPiece(new Bishop("White", board[5][7]));
  }

  private void placeKnights() {
    board[1][0].setPiece(new Knight("Black", board[1][0]));
    board[6][0].setPiece(new Knight("Black", board[6][0]));
    board[1][7].setPiece(new Knight("White", board[1][7]));
    board[6][7].setPiece(new Knight("White", board[6][7]));
  }

  private void placeRooks() {
    board[0][0].setPiece(new Rook("Black", board[0][0]));
    board[7][0].setPiece(new Rook("Black", board[7][0]));
    board[0][7].setPiece(new Rook("White", board[0][7]));
    board[7][7].setPiece(new Rook("White", board[7][7]));
  }

  private void placePawns() {
    for (int i = 0; i < 8; i++) { //Setting the black pawn pieces.
      board[i][1].setPiece(new Pawn("Black", board[i][1]));
    }
    for (int i = 0; i < 8; i++) { //Setting the white pawn pieces.
      board[i][6].setPiece(new Pawn("White", board[i][6]));
    }
  }
}
