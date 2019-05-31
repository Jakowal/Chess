import javafx.scene.image.Image;

abstract class Piece {

  protected String colour; //The colour of the piece. Will either be "White" or "Black".
  public Tile tile; //The tile this piece is placed on.
  public boolean isMoved; //This variable is used for castling with the king and rooks.
  protected Image image; //The image representing the piece. Every subclass sets its own image in the constructor.

  Piece(String c, Tile t) { //Upon creating a new piece, it needs to know its Colour and what tile it is placed on.
    colour = c;
    tile = t;
  }

  public void setTile(Tile n) { //Used for changing the tile of a piece.
    tile = n;
  }

  public String getColour() { //Return the colour.
    return colour;
  }

  protected boolean place(Tile newTile, Tile oldTile) { //This method is used when a piece is to be moved on the board.
    if (newTile.getPiece() == null) { //If no other piece is present where this one is being moved to, it is simply moved.
      tile = newTile;
      newTile.setPiece(this);
      newTile.setImage(image);
      oldTile.setPiece(null);
      oldTile.setImage(null);
      isMoved = true;
      return true;
    }
    else if (newTile.getPiece().getColour() != colour) { //If another piece is at the location, this piece will only be moved if it is of a different colour.
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

  public abstract boolean move(int oldX, int oldY, int newX, int newY, Tile newTile); //Every subclass defines its own way of movement.

  public abstract String getSign(); //This method remains from the version of the programme without GUI, but is used in some places.
}

class Pawn extends Piece { //Class for the Pawn type piece.

  Pawn(String c, Tile t) {
    super(c,t);
    if (c.compareTo("White") == 0) { //Sets the picture to black or white depending on the colour.
      image = new Image("Images/WhitePawn.png", 25, 25, false, false);
    }
    else if (c.compareTo("Black") == 0) {
      image = new Image("Images/BlackPawn.png", 25, 25, false, false);
    }
    tile.setImage(image);
  }

  public String getSign() { //Returns a sign based on the colour of the piece.
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
    else if (newY == oldY + 1 && newX == oldX && colour == "Black" && newTile.getPiece() == null) { //Moving a pawn one tile forwards.
      place(newTile, tile);
      crown(newY,newTile);
      return true;
    }
    else if (newY == oldY-1 && newX == oldX && colour == "White" && newTile.getPiece() == null) {
      place(newTile, tile);
      crown(newY,newTile);
      return true;
    }
    else if (newY == oldY - 1 && newX == oldX - 1 && colour == "White" && newTile.getPiece() != null) { //Attacking with a pawn.
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

  private void crown(int newY, Tile newTile) { //Used to check whether a pawn should be turned into a queen.
    if (newY == 0 && colour == "White") { //Checks for the location of the pawn.
      newTile.setPiece(new Queen("White",newTile)); //Creates a new queen of the same colour as this piece.
      return;
    }
    if (newY == 7 && colour == "Black") {
      newTile.setPiece(new Queen("Black",newTile));
      return;
    }
  }
}

class Rook extends Piece { //Class for the Rook type piece.

  Rook(String c, Tile t, boolean m) { //The variable m is used to set whether or not the piece has been moved. This is to make sure a new piece created during castling cannot be re-castled.
    super(c,t);
    isMoved = m;
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
    newTile.getLetter() != tile.getLetter() && newTile.getNumber() == tile.getNumber()) { //Makes sure the movement is along the x- or y-axis.
      if (newX > oldX && newY == oldY) { //Moving from A -> H.
        for (int i = oldX+1; i < newX; i++) { //Checks the tiles between the starting location and the new location.
          if (ChessBoard.getTile(i, newY).getPiece() != null) {
            return false;
          }
        }
        return place(newTile, tile);
      }
      else if (newX < oldX && newY == oldY) { //Moving from H -> A.
        for (int i = oldX-1; i > newX; i--) {
          if (ChessBoard.getTile(i, newY).getPiece() != null) {
            return false;
          }
        }
        return place(newTile, tile);
      }
      else if (newX == oldX && newY > oldY) { //Moving from 8 -> 1.
        for (int i = oldY+1; i < newY; i++) {
          if (ChessBoard.getTile(newX, i).getPiece() != null) {
            return false;
          }
        }
        return place(newTile, tile);
      }
      else if (newX == oldX && newY < oldY) { //Moving from 1 -> 8.
        for (int i = oldY-1; i > newY; i--) {
          if (ChessBoard.getTile(newX, i).getPiece() != null) {
            return false;
          }
        }
        return place(newTile, tile);
      }
    }
    return false;
  }
}

class Bishop extends Piece { //Class for the Bishop type piece.

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
    if (newTile.getColour() == tile.getColour() && newX != oldX && newY != oldY) { //Checks that the colour matches the starting tile.
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

class Knight extends Piece { //Class for the Knight type piece.

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

  public boolean move(int oldX, int oldY, int newX, int newY, Tile newTile) { //Checks for the exact locations the knight can move to.
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

class Queen extends Piece { //A class for the Queen type piece.

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

  public boolean move(int oldX, int oldY, int newX, int newY, Tile newTile) { //Uses the Rook and Bishop's moves.
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
      if (newX > oldX && newY == oldY) {
        for (int i = oldX+1; i < newX; i++) {
          if (ChessBoard.getTile(i, newY).getPiece() != null) {
            return false;
          }
        }
        isMoved = true;
        return place(newTile, tile);
      }
      else if (newX < oldX && newY == oldY) {
        for (int i = oldX-1; i > newX; i--) {
          if (ChessBoard.getTile(i, newY).getPiece() != null) {
            return false;
          }
        }
        isMoved = true;
        return place(newTile, tile);
      }
      else if (newX == oldX && newY > oldY) {
        for (int i = oldY+1; i < newY; i++) {
          if (ChessBoard.getTile(newX, i).getPiece() != null) {
            return false;
          }
        }
        isMoved = true;
        return place(newTile, tile);
      }
      else if (newX == oldX && newY < oldY) {
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
      if (newTile.getLetter().compareTo(tile.getLetter()) > 0 ) {
        if (newTile.getNumber() > tile.getNumber()) {
          int h = oldY-1;
          for (int i = oldX+1; i < newX; i++) {
            if (ChessBoard.getTile(i, h).getPiece() != null) {
              return false;
            }
            h--;
          }
          return place(newTile, tile);
        }
        else if (newTile.getNumber() < tile.getNumber()) {
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
      else if (newTile.getLetter().compareTo(tile.getLetter()) < 0) {
        if (newTile.getNumber() > tile.getNumber()) {
          int h = oldY-1;
          for (int i = oldX+1; i < newX; i++) {
            if (ChessBoard.getTile(i, h).getPiece() != null) {
              return false;
            }
            h--;
          }
          return place(newTile, tile);
        }
        else if (newTile.getNumber() < tile.getNumber()) {
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

class King extends Piece { //A class for the King type piece.

  King(String c, Tile t, Boolean m) {
    super(c,t);
    isMoved = m;
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

  public boolean move(int oldX, int oldY, int newX, int newY, Tile newTile) {
    if (newTile.getPiece() != null) { //If another piece is selected as a location to move, the piece first attempts to castle.
      if (!isMoved && colour == "White" && !newTile.getPiece().isMoved && newTile.getPiece().getSign() == "R") {
        return castleWhite(oldX, oldY, newX, newY, newTile);
      }
      else if (!isMoved && colour == "Black" && !newTile.getPiece().isMoved && newTile.getPiece().getSign() == "r") {
        return castleBlack(oldX, oldY, newX, newY, newTile);
      }
    }
    else if (newX == oldX-1 && newY == oldY || newX == oldX+1 && newY == oldY) { //Attempts to move the piece to a location.
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

  private boolean castleWhite(int oldX, int oldY, int newX, int newY, Tile newTile) { //Checks whether both pieces are of the same colour with no pieces between and neither has been moved.
    if (newX > oldX && newY == oldY) {
      for (int i = oldX+1; i < newX-2; i++) {
        if (ChessBoard.getTile(i, newY) != null) {
          return false;
        }
      }
      ChessBoard.getTile(oldX+2,newY).setPiece(new King(colour,ChessBoard.getTile(oldX+2,newY),true));
      ChessBoard.getTile(oldX+1,newY).setPiece(new Rook(colour,ChessBoard.getTile(oldX+1,newY),true));
      return castleMove(newX,newY);
    }
    else if (newX < oldX && newY == oldY) {
      for (int i = oldX-1; i > newX+2; i--) {
        if (ChessBoard.getTile(i, newY) != null) {
          return false;
        }
      }
      ChessBoard.getTile(oldX-2,newY).setPiece(new King(colour,ChessBoard.getTile(oldX-2,newY),true));
      ChessBoard.getTile(oldX-1,newY).setPiece(new Rook(colour,ChessBoard.getTile(oldX-1,newY),true));
      return castleMove(newX,newY);
    }
    return false;
  }

  private boolean castleBlack(int oldX, int oldY, int newX, int newY, Tile newTile) {
    if (newX > oldX && newY == oldY) {
      for (int i = oldX+1; i < newX-2; i++) {
        if (ChessBoard.getTile(i, newY) != null) {
          return false;
        }
      }
      ChessBoard.getTile(oldX+2,newY).setPiece(new King(colour,ChessBoard.getTile(oldX+2,newY),true));
      ChessBoard.getTile(oldX+1,newY).setPiece(new Rook(colour,ChessBoard.getTile(oldX+1,newY),true));
      return castleMove(newX,newY);
    }
    else if (newX < oldX && newY == oldY) {
      for (int i = oldX-1; i > newX+2; i--) {
        if (ChessBoard.getTile(i, newY) != null) {
          return false;
        }
      }
      ChessBoard.getTile(oldX-2,newY).setPiece(new King(colour,ChessBoard.getTile(oldX-2,newY),true));
      ChessBoard.getTile(oldX-1,newY).setPiece(new Rook(colour,ChessBoard.getTile(oldX-1,newY),true));
      return castleMove(newX,newY);
    }
    return false;
  }
  private boolean castleMove(int newX, int newY) {
    tile.setPiece(null);
    tile.setImage(null);
    ChessBoard.getTile(newX, newY).getPiece().isMoved = true;
    ChessBoard.getTile(newX, newY).setPiece(null);
    ChessBoard.getTile(newX, newY).setImage(null);
    isMoved = true;
    return true;
  }
}
