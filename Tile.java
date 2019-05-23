import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class Tile extends Button {
  String colour;
  String letter;
  int number;
  Piece piece;

  Tile(String c, String n, String l) {
    super(" ");
    setFont(new Font(10));
    setMinSize(50, 50);
    colour = c;
    number = Integer.parseInt(n);
    letter = l;
  }

  public void setPiece(Piece p) {
    piece = p;
  }

  public Piece getPiece() {
    return piece;
  }

  public int getNumber() {
    return number;
  }

  public String getLetter() {
    return letter;
  }

  public String getColour() {
    return colour;
  }

  public void setImage(Image i) {
    setGraphic(new ImageView(i));
  }
}
