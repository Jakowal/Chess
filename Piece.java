import java.util.ArrayList;

abstract class Piece {

  protected String colour;
  public Tile tile;

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

  protected boolean place(Tile newTile) {
    if (newTile.getPiece() == null) {
      tile = newTile;
      newTile.setPiece(this);
      return true;
    }
    else if (newTile.getPiece().getColour() != colour) {
      tile = newTile;
      newTile.setPiece(this);
      return true;
    }
    return false;
  }

  public abstract boolean move(int oldPos, int newPos, Tile newTile);

  public abstract String getSign();
}

class Pawn extends Piece {

  Pawn(String c, Tile t) {
    super(c,t);
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

  public boolean move(int oldPos, int newPos, Tile newTile) {
    if (oldPos == 8 && colour.compareTo("Black") == 0 && newPos == 24
    || oldPos == 9  && colour.compareTo("Black") == 0 && newPos == 25
    || oldPos == 10 && colour.compareTo("Black") == 0 && newPos == 26
    || oldPos == 11 && colour.compareTo("Black") == 0 && newPos == 27
    || oldPos == 12 && colour.compareTo("Black") == 0 && newPos == 28
    || oldPos == 13 && colour.compareTo("Black") == 0 && newPos == 29
    || oldPos == 14 && colour.compareTo("Black") == 0 && newPos == 30
    || oldPos == 15 && colour.compareTo("Black") == 0 && newPos == 31) {
      tile = newTile;
      newTile.setPiece(this);
      return true;
    }
    else if (oldPos == 48 && colour.compareTo("White") == 0 && newPos == 32
    || oldPos == 49 && colour.compareTo("White") == 0 && newPos == 33
    || oldPos == 50 && colour.compareTo("White") == 0 && newPos == 34
    || oldPos == 51 && colour.compareTo("White") == 0 && newPos == 35
    || oldPos == 52 && colour.compareTo("White") == 0 && newPos == 36
    || oldPos == 53 && colour.compareTo("White") == 0 && newPos == 37
    || oldPos == 54 && colour.compareTo("White") == 0 && newPos == 38
    || oldPos == 55 && colour.compareTo("White") == 0 && newPos == 39) {
      tile = newTile;
      newTile.setPiece(this);
      return true;
    }
    else if (newPos == oldPos - 8 && colour == "White" && newTile.getPiece() == null) {
      tile = newTile;
      crown(newPos,newTile);
      return true;
    }
    else if (newPos == oldPos + 8 && colour == "Black" && newTile.getPiece() == null) {
      tile = newTile;
      crown(newPos,newTile);
      return true;
    }
    else if (newPos == oldPos - 9 && colour == "White" && newTile.getPiece() != null) {
      if (newTile.getPiece().getColour() != this.getColour()) {
        tile = newTile;
        crown(newPos,newTile);
        return true;
      }
    }
    else if (newPos == oldPos - 7 && colour == "White" && newTile.getPiece() != null) {
      if (newTile.getPiece().getColour() != this.getColour()) {
        tile = newTile;
        crown(newPos,newTile);
        return true;
      }
    }
    else if (newPos == oldPos + 9 && colour == "Black" && newTile.getPiece() != null) {
      if (newTile.getPiece().getColour() != this.getColour()) {
        tile = newTile;
        crown(newPos,newTile);
        return true;
      }
    }
    else if (newPos == oldPos + 7 && colour == "Black" && newTile.getPiece() != null) {
      if (newTile.getPiece().getColour() != this.getColour()) {
        tile = newTile;
        crown(newPos,newTile);
        return true;
      }
    }
    return false;
  }

  private void crown(int newPos, Tile newTile) {
    for (int i = 0; i < 8; i++) {
      if (i == newPos && colour == "White") {
        newTile.setPiece(new Queen("White",newTile));
        return;
      }
    }
    for (int i = 56; i < 64; i++) {
      if (i == newPos && colour == "Black") {
        newTile.setPiece(new Queen("Black",newTile));
        return;
      }
    }
    newTile.setPiece(this);
  }
}

class Rook extends Piece {

  Rook(String c,Tile t) {
    super(c,t);
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

  public boolean move(int oldPos, int newPos, Tile newTile) {
    Board brett = tile.getBoard();
    if (newTile.getLetter() == tile.getLetter() && newTile.getNumber() != tile.getNumber()) {
      if (newPos - oldPos > 0) {
        for (int i = (oldPos+8); i <= newPos; i+=8) {
          if ((brett.getTile(i)).getPiece() != null) {
            if (brett.getTile(i).getPiece().getColour() != this.colour && i == newPos) {
              tile = newTile;
              newTile.setPiece(this);
              return true;
            }
            return false;
          }
        }
        tile = newTile;
        newTile.setPiece(this);
        return true;
      }
      else if (newPos - oldPos < 0) {
        for (int i = (oldPos-8); i >= newPos; i-=8) {
          if (brett.getTile(i).getPiece() != null) {
            if (brett.getTile(i).getPiece().getColour() != this.colour && i == newPos) {
              tile = newTile;
              newTile.setPiece(this);
              return true;
            }
            return false;
          }
        }
        tile = newTile;
        newTile.setPiece(this);
        return true;
      }
    }
    else if (newTile.getLetter() != tile.getLetter() && newTile.getNumber() == tile.getNumber()) {
      if (newPos - oldPos > 0) {
        for (int i = (oldPos+1); i <= newPos; i++) {
          if ((brett.getTile(i)).getPiece() != null) {
            if (brett.getTile(i).getPiece().getColour() != this.colour && i == newPos) {
              tile = newTile;
              newTile.setPiece(this);
              return true;
            }
            return false;
          }
        }
        tile = newTile;
        newTile.setPiece(this);
        return true;
      }
      else if (newPos - oldPos < 0) {
        for (int i = (oldPos-1); i >= newPos; i--) {
          if (brett.getTile(i).getPiece() != null) {
            if (brett.getTile(i).getPiece().getColour() != this.colour && i == newPos) {
              tile = newTile;
              newTile.setPiece(this);
              return true;
            }
            return false;
          }
        }
        tile = newTile;
        newTile.setPiece(this);
        return true;
      }
    }
    return false;
  }
}

class Bishop extends Piece {

  Bishop(String c,Tile t) {
    super(c,t);
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

  public boolean move(int oldPos, int newPos, Tile newTile) {
    if (newTile.getColour() == tile.getColour()) {
      Board brett = tile.getBoard();
      if (brett.letterNumber(newTile.getLetter()) > brett.letterNumber(tile.getLetter())) {
        if (newTile.getNumber() > tile.getNumber()) {
          for (int i = (oldPos+9); i <= newPos; i+=9) {
            if (brett.getTile(i).getPiece() != null) {
              if (brett.getTile(i).getPiece().getColour() != this.colour && i == newPos) {
                tile = newTile;
                newTile.setPiece(this);
                return true;
              }
              return false;
            }
            else if (brett.getTile(i).getPiece() == null) {
              tile = newTile;
              newTile.setPiece(this);
              return true;
            }
          }
        }
        else {
          for (int i = (oldPos-7); i >= newPos; i-=7) {
            if (brett.getTile(i).getPiece() != null) {
              if (brett.getTile(i).getPiece().getColour() != this.colour && i == newPos) {
                tile = newTile;
                newTile.setPiece(this);
                return true;
              }
              return false;
            }
            else if (brett.getTile(i).getPiece() == null) {
              tile = newTile;
              newTile.setPiece(this);
              return true;
            }
          }
        }
      }
      else {
        if (newTile.getNumber() > tile.getNumber()) {
          for (int i = (oldPos+7); i <= newPos; i+=7) {
            if (brett.getTile(i).getPiece() != null) {
              if (brett.getTile(i).getPiece().getColour() != this.colour && i == newPos) {
                tile = newTile;
                newTile.setPiece(this);
                return true;
              }
              return false;
            }
            else if (brett.getTile(i).getPiece() == null) {
              tile = newTile;
              newTile.setPiece(this);
              return true;
            }
          }
        }
        else {
          for (int i = (oldPos-9); i >= newPos; i-=9) {
            if (brett.getTile(i).getPiece() != null) {
              if (brett.getTile(i).getPiece().getColour() != this.colour && i == newPos) {
                tile = newTile;
                newTile.setPiece(this);
                return true;
              }
              System.out.println(1);
              return false;
            }
            else if (brett.getTile(i).getPiece() == null) {
              tile = newTile;
              newTile.setPiece(this);
              return true;
            }
          }
        }
      }
    }
    return false;
  }
}

class Knight extends Piece {

  Knight(String c,Tile t) {
    super(c,t);
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

  public boolean move(int oldPos, int newPos, Tile newTile) {
    if (newPos == oldPos - 17) {
      return place(newTile);
    }
    else if (newPos == oldPos - 15) {
      return place(newTile);
    }
    else if (newPos == oldPos - 6) {
      return place(newTile);
    }
    else if (newPos == oldPos - 10) {
      return place(newTile);
    }
    else if (newPos == oldPos + 17) {
      return place(newTile);
    }
    else if (newPos == oldPos + 15) {
      return place(newTile);
    }
    else if (newPos == oldPos + 6) {
      return place(newTile);
    }
    else if (newPos == oldPos + 10) {
      return place(newTile);
    }
    return false;
  }
}

class Queen extends Piece {

  Queen(String c,Tile t) {
    super(c,t);
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

  public boolean move(int oldPos, int newPos, Tile newTile) {
    Board brett = tile.getBoard();
    if (newTile.getLetter() == tile.getLetter() && newTile.getNumber() != tile.getNumber()) {
      return rookMove1(oldPos,newPos,newTile,brett);
    }
    else if (newTile.getLetter() != tile.getLetter() && newTile.getNumber() == tile.getNumber()) {
      return rookMove2(oldPos,newPos,newTile,brett);
    }
    else if (newTile.getColour() == tile.getColour()) {
      return bishopMove(oldPos,newPos,newTile,brett);
    }
    return false;
  }

  private boolean rookMove1(int oldPos, int newPos, Tile newTile, Board brett) {
    if (newPos - oldPos > 0) {
      for (int i = (oldPos+8); i <= newPos; i+=8) {
        if ((brett.getTile(i)).getPiece() != null) {
          if (brett.getTile(i).getPiece().getColour() != this.colour && i == newPos) {
            tile = newTile;
            newTile.setPiece(this);
            return true;
          }
          return false;
        }
      }
      tile = newTile;
      return true;
    }
    else if (newPos - oldPos < 0) {
      for (int i = (oldPos-8); i >= newPos; i-=8) {
        if (brett.getTile(i).getPiece() != null) {
          if (brett.getTile(i).getPiece().getColour() != this.colour && i == newPos) {
            tile = newTile;
            newTile.setPiece(this);
            return true;
          }
          return false;
        }
      }
      tile = newTile;
      newTile.setPiece(this);
      return true;
    }
    return false;
  }

  private boolean rookMove2(int oldPos, int newPos, Tile newTile, Board brett) {
    if (newPos - oldPos > 0) {
      for (int i = (oldPos+1); i <= newPos; i++) {
        if ((brett.getTile(i)).getPiece() != null) {
          if (brett.getTile(i).getPiece().getColour() != this.colour && i == newPos) {
            tile = newTile;
            newTile.setPiece(this);
            return true;
          }
          return false;
        }
      }
      tile = newTile;
      newTile.setPiece(this);
      return true;
    }
    else if (newPos - oldPos < 0) {
      for (int i = (oldPos-1); i >= newPos; i--) {
        if (brett.getTile(i).getPiece() != null) {
          if (brett.getTile(i).getPiece().getColour() != this.colour && i == newPos) {
            tile = newTile;
            newTile.setPiece(this);
            return true;
          }
          return false;
        }
      }
      tile = newTile;
      newTile.setPiece(this);
      return true;
    }
    return false;
  }

  private boolean bishopMove(int oldPos, int newPos, Tile newTile, Board brett) {
    if (brett.letterNumber(newTile.getLetter()) > brett.letterNumber(tile.getLetter())) {
      if (newTile.getNumber() > tile.getNumber()) {
        for (int i = (oldPos+9); i <= newPos; i+=9) {
          if (brett.getTile(i).getPiece() != null) {
            if (brett.getTile(i).getPiece().getColour() != this.colour && i == newPos) {
              tile = newTile;
              newTile.setPiece(this);
              return true;
            }
            return false;
          }
          else if (brett.getTile(i).getPiece() == null) {
            tile = newTile;
            newTile.setPiece(this);
            return true;
          }
        }
      }
      else {
        for (int i = (oldPos-7); i >= newPos; i-=7) {
          if (brett.getTile(i).getPiece() != null) {
            if (brett.getTile(i).getPiece().getColour() != this.colour && i == newPos) {
              tile = newTile;
              newTile.setPiece(this);
              return true;
            }
            return false;
          }
          else if (brett.getTile(i).getPiece() == null) {
            tile = newTile;
            newTile.setPiece(this);
            return true;
          }
        }
      }
    }
    else {
      if (newTile.getNumber() > tile.getNumber()) {
        for (int i = (oldPos+7); i <= newPos; i+=7) {
          if (brett.getTile(i).getPiece() != null) {
            if (brett.getTile(i).getPiece().getColour() != this.colour && i == newPos) {
              tile = newTile;
              newTile.setPiece(this);
              return true;
            }
            return false;
          }
          else if (brett.getTile(i).getPiece() == null) {
            tile = newTile;
            newTile.setPiece(this);
            return true;
          }
        }
      }
      else {
        for (int i = (oldPos-9); i >= newPos; i-=9) {
          if (brett.getTile(i).getPiece() != null) {
            if (brett.getTile(i).getPiece().getColour() != this.colour && i == newPos) {
              tile = newTile;
              newTile.setPiece(this);
              return true;
            }
            System.out.println(1);
            return false;
          }
          else if (brett.getTile(i).getPiece() == null) {
            tile = newTile;
            newTile.setPiece(this);
            return true;
          }
        }
      }
    }
    return false;
  }
}

class King extends Piece {

  King(String c,Tile t) {
    super(c,t);
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

  public boolean move(int oldPos, int newPos, Tile newTile) {
    if (oldPos == 59 && newPos == 63 && colour == "White" && newTile.getPiece().getColour() == colour
    || oldPos == 59 && newPos == 56 && colour == "White" && newTile.getPiece().getColour() == colour) {
      newTile.setPiece(new King(colour, newTile));
      tile.setPiece(new Rook(colour,tile));
      return true;
    }
    else if (oldPos == 3 && newPos == 0 && colour == "Black" && newTile.getPiece().getColour() == colour
    || oldPos == 3 && newPos == 7 && colour == "Black" && newTile.getPiece().getColour() == colour) {
      newTile.setPiece(new King(colour, newTile));
      tile.setPiece(new Rook(colour,tile));
      return true;
    }
    else if (newPos == oldPos - 1 || newPos == oldPos + 1) {
      return place(newTile);
    }
    else if (newPos == oldPos - 9 || newPos == oldPos - 8 || newPos == oldPos - 7) {
      return place(newTile);
    }
    else if (newPos == oldPos + 7 || newPos == oldPos + 8 || newPos == oldPos + 9) {
      return place(newTile);
    }
    return false;
  }
}
