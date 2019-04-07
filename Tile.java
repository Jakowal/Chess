abstract class Tile {

  protected Piece myPiece;
  protected String letter;
  protected int number;
  protected Board board;

  Tile(String l, int n, Board b) {
    letter = l;
    number = n;
    board = b;
  }


  public void setPiece(Piece piece) {
    myPiece = piece;
  }

  public Piece getPiece() {
    return myPiece;
  }

  public String getLetter() {
    return letter;
  }

  public int getNumber() {
    return number;
  }

  public Board getBoard() {
    return board;
  }

  public abstract String getColour();
  public abstract String toString1();
  public abstract String toString2();
}

class WhiteTile extends Tile {

  WhiteTile(String l, int n,Board b) {
    super(l,n,b);
  }

  public String getColour() {
    return "White";
  }

  public String toString1() {
    return "+++++++";
  }
  public String toString2() {
    String tileSign = " ";
    if (myPiece != null) {
      tileSign = myPiece.getSign();
    }
    return "++ " + tileSign + " ++";
  }
}

class BlackTile extends Tile {

  BlackTile(String l, int n, Board b) {
    super(l,n,b);
  }

  public String getColour() {
    return "Black";
  }

  public String toString1() {
    return "xxxxxxx";
  }
  public String toString2() {
    String tileSign = " ";
    if (myPiece != null) {
      tileSign = myPiece.getSign();
    }
    return "xx " + tileSign + " xx";
  }
}
