import java.util.*;
import java.io.*;

class Board {

  public static ArrayList<Tile> boardList = new ArrayList<Tile>();
  protected static String[] letters = {"A","B","C","D","E","F","G","H"};
  private static String turn;

  Board(boolean fromFile) {
    boolean lastWhite = false;
    for (int i = 0; i < 8; i++) {
      for (int h = 0; h < 8; h++) {
        String thisLetter = letters[h];
        if (lastWhite) {
          boardList.add(new BlackTile(thisLetter,i,this));
          lastWhite = false;
        }
        else {
          boardList.add(new WhiteTile(thisLetter,i,this));
          lastWhite = true;
        }
      }
      lastWhite = !lastWhite;
    }
    if (!fromFile) {
      addPieces();
    }
  }

  private static void addPieces() {
    //Pawns
    for (int i = 8; i < 16; i++) {
      boardList.get(i).setPiece(new Pawn("Black",boardList.get(i)));
    }
    for (int i = 48; i < 56; i++) {
      boardList.get(i).setPiece(new Pawn("White",boardList.get(i)));
    }
    //Rooks
    boardList.get(0).setPiece(new Rook("Black",boardList.get(0)));
    boardList.get(7).setPiece(new Rook("Black",boardList.get(7)));
    boardList.get(56).setPiece(new Rook("White",boardList.get(56)));
    boardList.get(63).setPiece(new Rook("White",boardList.get(63)));
    //Bishops
    boardList.get(2).setPiece(new Bishop("Black",boardList.get(2)));
    boardList.get(5).setPiece(new Bishop("Black",boardList.get(5)));
    boardList.get(58).setPiece(new Bishop("White",boardList.get(58)));
    boardList.get(61).setPiece(new Bishop("White",boardList.get(61)));
    //Knights
    boardList.get(1).setPiece(new Knight("Black",boardList.get(1)));
    boardList.get(6).setPiece(new Knight("Black",boardList.get(6)));
    boardList.get(57).setPiece(new Knight("White",boardList.get(57)));
    boardList.get(62).setPiece(new Knight("White",boardList.get(62)));
    //Queen
    boardList.get(4).setPiece(new Queen("Black",boardList.get(4)));
    boardList.get(60).setPiece(new Queen("White",boardList.get(60)));
    //King
    boardList.get(3).setPiece(new King("Black",boardList.get(3)));
    boardList.get(59).setPiece(new King("White",boardList.get(59)));
  }

  public static boolean move(String l, int n, String nl, int nn, boolean whiteTurn) {
    int oldPos = letterNumber(l) + numberNumber(n);
    int newPos = letterNumber(nl) + numberNumber(nn);

    Tile tile = boardList.get(oldPos);
    Tile newTile = boardList.get(newPos);
    Piece piece = tile.getPiece();
    if (!whiteTurn && piece.getColour() == "White" || whiteTurn && piece.getColour() == "Black") {
      System.out.println("Select a piece of the correct colour.");
      return false;
    }
    else if (piece.move(oldPos,newPos,newTile)) {
      tile.setPiece(null);
      return true;
    }
    else {
      System.out.println("Invalid positioning: Could not move piece.");
      return false;
    }
  }

  public static Tile getTile(int pos) {
    return boardList.get(pos);
  }

  public static int numberNumber(int n) {
    if (n == 1) {
      return 56;
    }
    else if (n == 2) {
      return 48;
    }
    else if (n == 3) {
      return 40;
    }
    else if (n == 4) {
      return 32;
    }
    else if (n == 5) {
      return 24;
    }
    else if (n == 6) {
      return 16;
    }
    else if (n == 7) {
      return 8;
    }
    else if (n == 8) {
      return 0;
    }
    return 1000;
  }

  public static int letterNumber(String l) {
    if (l.compareTo("A") == 0) {
      return 0;
    }
    else if (l.compareTo("B") == 0) {
      return 1;
    }
    else if (l.compareTo("C") == 0) {
      return 2;
    }
    else if (l.compareTo("D") == 0) {
      return 3;
    }
    else if (l.compareTo("E") == 0) {
      return 4;
    }
    else if (l.compareTo("F") == 0) {
      return 5;
    }
    else if (l.compareTo("G") == 0) {
      return 6;
    }
    else if (l.compareTo("H") == 0) {
      return 7;
    }
    return 1000;
  }

  public static boolean setTurn() {
    if (turn.compareTo("White") == 0) {
      return true;
    }
    else {
      return false;
    }

  }

  public static Board readFromFile(File file) throws FileNotFoundException {
    Scanner scanner = null;
    try {
      scanner = new Scanner(file);
    }
    catch(FileNotFoundException e) {
      System.out.println("No savegame found, starting new game");
      return new Board(false);

    }
    turn = scanner.nextLine();
    String read = scanner.nextLine();
    Board b = new Board(true);

    while (scanner.hasNextLine()) {
      String[] data = read.split(", ");

      if (data[1].compareTo("Pawns") == 0) {
        while (scanner.hasNextLine()) {
          read = scanner.nextLine();
          if (read.charAt(0) == '#') {
              break;
          }
          String[] in = read.split(", ");
          int tileLetterNumber = letterNumber(in[0]);
          int tileNumber = (Integer.parseInt(in[1]))*8;
          String colour = in[2];
          b.getTile(tileLetterNumber+tileNumber).setPiece(new Pawn(colour,b.getTile(tileLetterNumber+tileNumber)));
        }

      }
      else if (data[1].compareTo("Rooks") == 0) {
        while (scanner.hasNextLine()) {
          read = scanner.nextLine();
          if (read.charAt(0) == '#') {
              break;
          }
          String[] in = read.split(", ");
          int tileLetterNumber = letterNumber(in[0]);
          int tileNumber = (Integer.parseInt(in[1]))*8;
          String colour = in[2];
          b.getTile(tileLetterNumber+tileNumber).setPiece(new Rook(colour,b.getTile(tileLetterNumber+tileNumber)));
        }

      }
      else if (data[1].compareTo("Bishops") == 0) {
        while (scanner.hasNextLine()) {
          read = scanner.nextLine();
          if (read.charAt(0) == '#') {
              break;
          }
          String[] in = read.split(", ");
          int tileLetterNumber = letterNumber(in[0]);
          int tileNumber = (Integer.parseInt(in[1]))*8;
          String colour = in[2];
          b.getTile(tileLetterNumber+tileNumber).setPiece(new Bishop(colour,b.getTile(tileLetterNumber+tileNumber)));
        }

      }
      else if (data[1].compareTo("Knights") == 0) {
        while (scanner.hasNextLine()) {
          read = scanner.nextLine();
          if (read.charAt(0) == '#') {
              break;
          }
          String[] in = read.split(", ");
          int tileLetterNumber = letterNumber(in[0]);
          int tileNumber = (Integer.parseInt(in[1]))*8;
          String colour = in[2];
          b.getTile(tileLetterNumber+tileNumber).setPiece(new Knight(colour,b.getTile(tileLetterNumber+tileNumber)));
        }

      }
      else if (data[1].compareTo("Queens") == 0) {
        while (scanner.hasNextLine()) {
          read = scanner.nextLine();
          if (read.charAt(0) == '#') {
              break;
          }
          String[] in = read.split(", ");
          int tileLetterNumber = letterNumber(in[0]);
          int tileNumber = (Integer.parseInt(in[1]))*8;
          String colour = in[2];
          b.getTile(tileLetterNumber+tileNumber).setPiece(new Queen(colour,b.getTile(tileLetterNumber+tileNumber)));
        }

      }
      else if (data[1].compareTo("Kings") == 0) {
        while (scanner.hasNextLine()) {
          read = scanner.nextLine();
          if (read.charAt(0) == '#') {
              break;
          }
          String[] in = read.split(", ");
          int tileLetterNumber = letterNumber(in[0]);
          int tileNumber = (Integer.parseInt(in[1]))*8;
          String colour = in[2];
          b.getTile(tileLetterNumber+tileNumber).setPiece(new King(colour,b.getTile(tileLetterNumber+tileNumber)));
        }

      }
    }

    return b;
  }

  public String toString() {
    String[] help = {"+ = White tile", "x = Black tile", "P/p = White/Black Pawn", "R/r = White/Black Rook", "K/k = White/Black Knight", "B/b = White/Black Bishop", "I/i = White/Black Queen", "O/o = White/Black King"};
    String boardString = "";
    int t = 0;
    int line = 8;
    boardString += "Move <Current letterCurrent number> <New letterNew number>  (Example: B2 B3) TYPE 'S' TO SAVE OR 'E' TO EXIT" + "\n";
    boardString += "    ";
    for (int y = 0; y < 8; y++) {
      boardString += " | " + letters[y] + " |  ";
    }
    boardString += "\n";
    for (int i = 0; i < 8; i++) {
      boardString += "   ";
      for (int x = 0; x < 65; x++) {
        boardString += "-";
      }
      boardString += "\n";
      boardString += "   ";
      boardString += "|";
      for (int h = 0; h < 8; h++) {
        boardString += (boardList.get(t+h)).toString1();
        boardString += "|";
      }
      boardString += "\n";
      boardString += "|" + line + "|";
      boardString += "|";
      for (int j = 0; j < 8; j++) {
        boardString += (boardList.get(t+j)).toString2();
        boardString += "|";
      }
      boardString  += " " + help[i];
      boardString += "\n";
      boardString += "   ";
      boardString += "|";
      for (int l = 0; l < 8; l++) {
        boardString += (boardList.get(t+l)).toString1();
        boardString += "|";
      }
      boardString += "\n";
      t += 8;
      line --;
    }
    boardString += "   ";
    for (int i = 0; i < 65; i++) {
      boardString += "-";
    }
    boardString += "\n";
    return boardString;
  }

}
