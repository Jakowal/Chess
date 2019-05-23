import javafx.scene.image.Image;

abstract class Piece {

  protected String colour;
  public Tile tile;
  public boolean isMoved = false;
  protected Image image;

  Piece(String c, Tile t) {
    colour = c;
    tile = t;
  }

  public void setTile(Tile n) {
    tile = n;
  }

  public String getColour() {
    return colour;
  }

  protected boolean place(Tile newTile, Tile oldTile) {
    if (newTile.getPiece() == null) {
      tile = newTile;
      newTile.setPiece(this);
      newTile.setImage(image);
      oldTile.setPiece(null);
      oldTile.setImage(null);
      isMoved = true;
      return true;
    }
    else if (newTile.getPiece().getColour() != colour) {
      tile = newTile;
      newTile.setPiece(this);
      newTile.setImage(image);
      oldTile.setPiece(null);
      oldTile.setImage(null);
      isMoved = true;
      return true;
    }
    return false;
  }

  public abstract boolean move(int oldX, int oldY, int newX, int newY, Tile newTile);

  public abstract String getSign();
}

class Pawn extends Piece {

  Pawn(String c, Tile t) {
    super(c,t);
    if (c.compareTo("White") == 0) {
      image = new Image("Images/WhitePawn.png", 25, 25, false, false);
    }
    else if (c.compareTo("Black") == 0) {
      image = new Image("Images/BlackPawn.png", 25, 25, false, false);
    }
    tile.setImage(image);
  }

  public String getSign() {
    String sign;
    if (colour.compareTo("White") == 0) {
      sign = "P";
    }
    else {
      sign = "p";
    }
    return sign;
  }

  public boolean move(int oldX, int oldY, int newX, int newY, Tile newTile) {
    if (oldY == 1 && colour.compareTo("Black") == 0 && newY == 3) { //Moving a black pawn two steps forwards as an initial move.
      return place(newTile, tile);
    }
    else if (oldY == 6 && colour.compareTo("White") == 0 && newY == 4) {
      return place(newTile, tile);
    }
    else if (newY == oldY + 1 && newX == oldX && colour == "Black" && newTile.getPiece() == null) { //Moving a black pawn one tile forwards.
      place(newTile, tile);
      crown(newY,newTile);
      return true;
    }
    else if (newY == oldY-1 && newX == oldX && colour == "White" && newTile.getPiece() == null) {
      place(newTile, tile);
      crown(newY,newTile);
      return true;
    }
    else if (newY == oldY - 1 && newX == oldX - 1 && colour == "White" && newTile.getPiece() != null) { //Attacking with a black piece.
      place(newTile, tile);
      crown(newY,newTile);
      return true;
    }
    else if (newY == oldY - 1 && newX == oldX + 1 && colour == "White" && newTile.getPiece() != null) {
      place(newTile, tile);
      crown(newY,newTile);
      return true;
    }
    else if (newY == oldY + 1 && newX == oldX - 1 && colour == "Black" && newTile.getPiece() != null) {
      place(newTile, tile);
      crown(newY,newTile);
      return true;
    }
    else if (newY == oldY + 1 && newX == oldX + 1 && colour == "Black" && newTile.getPiece() != null) {
      place(newTile, tile);
      crown(newY,newTile);
      return true;
    }
    return false;
  }

  private void crown(int newY, Tile newTile) {
    if (newY == 0 && colour == "White") {
      newTile.setPiece(new Queen("White",newTile));
      return;
    }
    if (newY == 7 && colour == "Black") {
      newTile.setPiece(new Queen("Black",newTile));
      return;
    }
  }
}

class Rook extends Piece {

  Rook(String c, Tile t) {
    super(c,t);
    if (c.compareTo("White") == 0) {
      image = new Image("Images/WhiteRook.png", 25, 25, false, false);
    }
    else if (c.compareTo("Black") == 0) {
      image = new Image("Images/BlackRook.png", 25, 25, false, false);
    }
    tile.setImage(image);
  }

  public String getSign() {
    String sign;
    if (colour.compareTo("White") == 0) {
      sign = "R";
    }
    else {
      sign = "r";
    }
    return sign;
  }

  public boolean move(int oldX, int oldY, int newX, int newY, Tile newTile) {
    if (newTile.getLetter() == tile.getLetter() && newTile.getNumber() != tile.getNumber() ||
    newTile.getLetter() != tile.getLetter() && newTile.getNumber() == tile.getNumber()) {
      if (newX > oldX && newY == oldY) { //Moving from A -> H.
        for (int i = oldX+1; i < newX; i++) {
          if (ChessBoard.getTile(i, newY).getPiece() != null) {
            return false;
          }
        }
        isMoved = true;
        return place(newTile, tile);
      }
      else if (newX < oldX && newY == oldY) { //Moving from H -> A.
        for (int i = oldX-1; i > newX; i--) {
          if (ChessBoard.getTile(i, newY).getPiece() != null) {
            return false;
          }
        }
        isMoved = true;
        return place(newTile, tile);
      }
      else if (newX == oldX && newY > oldY) { //Moving from 8 -> 1.
        for (int i = oldY+1; i < newY; i++) {
          if (ChessBoard.getTile(newX, i).getPiece() != null) {
            return false;
          }
        }
        isMoved = true;
        return place(newTile, tile);
      }
      else if (newX == oldX && newY < oldY) { //Moving from 1 -> 8.
        for (int i = oldY-1; i > newY; i--) {
          if (ChessBoard.getTile(newX, i).getPiece() != null) {
            return false;
          }
        }
        isMoved = true;
        return place(newTile, tile);
      }
    }
    return false;
  }
}

class Bishop extends Piece {

  Bishop(String c, Tile t) {
    super(c,t);
    if (c.compareTo("White") == 0) {
      image = new Image("Images/WhiteBishop.png", 25, 25, false, false);
    }
    else if (c.compareTo("Black") == 0) {
      image = new Image("Images/BlackBishop.png", 25, 25, false, false);
    }
    tile.setImage(image);
  }

  public String getSign() {
    String sign;
    if (colour.compareTo("White") == 0) {
      sign = "B";
    }
    else {
      sign = "b";
    }
    return sign;
  }

  public boolean move(int oldX, int oldY, int newX, int newY, Tile newTile) {
    if (newTile.getColour() == tile.getColour() && newX != oldX && newY != oldY) {
      if (newTile.getLetter().compareTo(tile.getLetter()) > 0 ) { //Moving to the right edge on the board.
        if (newTile.getNumber() > tile.getNumber()) { //Moving up the board.
          int h = oldY-1;
          for (int i = oldX+1; i < newX; i++) {
            if (ChessBoard.getTile(i, h).getPiece() != null) {
              return false;
            }
            h--;
          }
          return place(newTile, tile);
        }
        else if (newTile.getNumber() < tile.getNumber()) { //Moving down the board.
          int h = oldY+1;
          for (int i = oldX+1; i < newX; i++) {
            if (ChessBoard.getTile(i, h).getPiece() != null) {
              return false;
            }
            h++;
          }
          return place(newTile, tile);
        }
      }
      else if (newTile.getLetter().compareTo(tile.getLetter()) < 0) { //Moving to the left edge of the .
        if (newTile.getNumber() > tile.getNumber()) { //Moving up the board.
          int h = oldY-1;
          for (int i = oldX+1; i < newX; i++) {
            if (ChessBoard.getTile(i, h).getPiece() != null) {
              return false;
            }
            h--;
          }
          return place(newTile, tile);
        }
        else if (newTile.getNumber() < tile.getNumber()) { //Moving down the board.
          int h = oldY+1;
          for (int i = oldX+1; i < newX; i++) {
            if (ChessBoard.getTile(i, h).getPiece() != null) {
              return false;
            }
            h++;
          }
          return place(newTile, tile);
        }
      }
    }
    return false;
  }
}

class Knight extends Piece {

  Knight(String c, Tile t) {
    super(c,t);
    if (c.compareTo("White") == 0) {
      image = new Image("Images/WhiteKnight.png", 25, 25, false, false);
    }
    else if (c.compareTo("Black") == 0) {
      image = new Image("Images/BlackKnight.png", 25, 25, false, false);
    }
    tile.setImage(image);
  }

  public String getSign() {
    String sign;
    if (colour.compareTo("White") == 0) {
      sign = "K";
    }
    else {
      sign = "k";
    }
    return sign;
  }

  public boolean move(int oldX, int oldY, int newX, int newY, Tile newTile) {
    if (newX == oldX+2 && newY == oldY-1) {
      return place(newTile, tile);
    }
    else if (newX == oldX+2 && newY == oldY+1) {
      return place(newTile, tile);
    }
    else if (newX == oldX-2 && newY == oldY-1) {
      return place(newTile, tile);
    }
    else if (newX == oldX-2 && newY == oldY+1) {
      return place(newTile, tile);
    }
    else if (newX == oldX-1 && newY == oldY+2) {
      return place(newTile, tile);
    }
    else if (newX == oldX+1 && newY == oldY+2) {
      return place(newTile, tile);
    }
    else if (newX == oldX-1 && newY == oldY-2) {
      return place(newTile, tile);
    }
    else if (newX == oldX+1 && newY == oldY-2) {
      return place(newTile, tile);
    }
    return false;
  }
}

class Queen extends Piece {

  Queen(String c, Tile t) {
    super(c,t);
    if (c.compareTo("White") == 0) {
      image = new Image("Images/WhiteQueen.png", 25, 25, false, false);
    }
    else if (c.compareTo("Black") == 0) {
      image = new Image("Images/BlackQueen.png", 25, 25, false, false);
    }
    tile.setImage(image);
  }

  public String getSign() {
    String sign;
    if (colour.compareTo("White") == 0) {
      sign = "I";
    }
    else {
      sign = "i";
    }
    return sign;
  }

  public boolean move(int oldX, int oldY, int newX, int newY, Tile newTile) {
    if (newTile.getLetter() == tile.getLetter() && newTile.getNumber() != tile.getNumber() ||
    newTile.getLetter() != tile.getLetter() && newTile.getNumber() == tile.getNumber()) {
      return rookMove(oldX, oldY, newX, newY, newTile);
    }
    else if (newTile.getColour() == tile.getColour()) {
      return bishopMove(oldX, oldY, newX, newY, newTile);
    }
    return false;
  }

  private boolean rookMove(int oldX, int oldY, int newX, int newY, Tile newTile) {
      if (newX > oldX && newY == oldY) { //Moving from A -> H.
        for (int i = oldX+1; i < newX; i++) {
          if (ChessBoard.getTile(i, newY).getPiece() != null) {
            return false;
          }
        }
        isMoved = true;
        return place(newTile, tile);
      }
      else if (newX < oldX && newY == oldY) { //Moving from H -> A.
        for (int i = oldX-1; i > newX; i--) {
          if (ChessBoard.getTile(i, newY).getPiece() != null) {
            return false;
          }
        }
        isMoved = true;
        return place(newTile, tile);
      }
      else if (newX == oldX && newY > oldY) { //Moving from 8 -> 1.
        for (int i = oldY+1; i < newY; i++) {
          if (ChessBoard.getTile(newX, i).getPiece() != null) {
            return false;
          }
        }
        isMoved = true;
        return place(newTile, tile);
      }
      else if (newX == oldX && newY < oldY) { //Moving from 1 -> 8.
        for (int i = oldY-1; i > newY; i--) {
          if (ChessBoard.getTile(newX, i).getPiece() != null) {
            return false;
          }
        }
        isMoved = true;
        return place(newTile, tile);
      }
    return false;
  }

  private boolean bishopMove(int oldX, int oldY, int newX, int newY, Tile newTile) {
    if (newTile.getColour() == tile.getColour() && newX != oldX && newY != oldY) {
      if (newTile.getLetter().compareTo(tile.getLetter()) > 0 ) { //Moving to the right edge on the board.
        if (newTile.getNumber() > tile.getNumber()) { //Moving up the board.
          int h = oldY-1;
          for (int i = oldX+1; i < newX; i++) {
            if (ChessBoard.getTile(i, h).getPiece() != null) {
              return false;
            }
            h--;
          }
          return place(newTile, tile);
        }
        else if (newTile.getNumber() < tile.getNumber()) { //Moving down the board.
          int h = oldY+1;
          for (int i = oldX+1; i < newX; i++) {
            if (ChessBoard.getTile(i, h).getPiece() != null) {
              return false;
            }
            h++;
          }
          return place(newTile, tile);
        }
      }
      else if (newTile.getLetter().compareTo(tile.getLetter()) < 0) { //Moving to the left edge of the .
        if (newTile.getNumber() > tile.getNumber()) { //Moving up the board.
          int h = oldY-1;
          for (int i = oldX+1; i < newX; i++) {
            if (ChessBoard.getTile(i, h).getPiece() != null) {
              return false;
            }
            h--;
          }
          return place(newTile, tile);
        }
        else if (newTile.getNumber() < tile.getNumber()) { //Moving down the board.
          int h = oldY+1;
          for (int i = oldX+1; i < newX; i++) {
            if (ChessBoard.getTile(i, h).getPiece() != null) {
              return false;
            }
            h++;
          }
          return place(newTile, tile);
        }
      }
    }
    return false;
  }
}

class King extends Piece {

  King(String c, Tile t) {
    super(c,t);
    if (c.compareTo("White") == 0) {
      image = new Image("Images/WhiteKing.png", 25, 25, false, false);
    }
    else if (c.compareTo("Black") == 0) {
      image = new Image("Images/BlackKing.png", 25, 25, false, false);
    }
    tile.setImage(image);
  }

  public String getSign() {
    String sign;
    if (colour.compareTo("White") == 0) {
      sign = "O";
    }
    else {
      sign = "o";
    }
    return sign;
  }

  private boolean castleWhite(int oldX, int oldY, int newX, int newY, Tile newTile) {
    if (newX > oldX && newY == oldY) {
      for (int i = oldX+1; i < newX-1; i++) {
        if (ChessBoard.getTile(i, newY) != null) {
          return false;
        }
      }
      newTile.setPiece(new King(colour, ChessBoard.getTile(oldX+2,newY)));
      tile.setPiece(new Rook(colour,tile));
      return true;
    }
    else if (newX < oldX && newY == oldY) {
      for (int i = oldX-1; i > newX+1; i--) {
        if (ChessBoard.getTile(i, newY) != null) {
          return false;
        }
      }
      newTile.setPiece(new King(colour, ChessBoard.getTile(oldX-2,newY)));
      tile.setPiece(new Rook(colour,tile));
      return true;
    }
    return false;
  }

  private boolean castleBlack(int oldX, int oldY, int newX, int newY, Tile newTile) {
    if (newX > oldX && newY == oldY) {
      for (int i = oldX+1; i < newX-1; i++) {
        if (ChessBoard.getTile(i, newY) != null) {
          return false;
        }
      }
      newTile.setPiece(new King(colour, ChessBoard.getTile(oldX+2,newY)));
      tile.setPiece(new Rook(colour,tile));
      return true;
    }
    else if (newX < oldX && newY == oldY) {
      for (int i = oldX-1; i > newX+1; i--) {
        if (ChessBoard.getTile(i, newY) != null) {
          return false;
        }
      }
      newTile.setPiece(new King(colour, ChessBoard.getTile(oldX-2,newY)));
      tile.setPiece(new Rook(colour,tile));
      return true;
    }
    return false;
  }

  public boolean move(int oldX, int oldY, int newX, int newY, Tile newTile) {
    if (newTile.getPiece() != null) {
      if (!isMoved && colour == "White" && !newTile.getPiece().isMoved && newTile.getPiece().getSign() == "R") {
        castleWhite(oldX, oldY, newX, newY, newTile);
      }
      else if (!isMoved && colour == "Black" && !newTile.getPiece().isMoved && newTile.getPiece().getSign() == "r") {
        castleBlack(oldX, oldY, newX, newY, newTile);
      }
    }
    else if (newX == oldX-1 && newY == oldY || newX == oldX+1 && newY == oldY) {
      return place(newTile, tile);
    }
    else if (newX == oldX-1 && newY == oldY+1 || newX == oldX && newY == oldY+1 || newX == oldX+1 && newY == oldY+1) {
      return place(newTile, tile);
    }
    else if (newX == oldX-1 && newY == oldY-1 || newX == oldX && newY == oldY-1 || newX == oldX+1 && newY == oldY-1) {
      return place(newTile, tile);
    }
    return false;
  }
}
