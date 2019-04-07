import java.util.*;
import java.io.*;

class PlayChess {

  public static void main(String[] args) throws UnsupportedEncodingException,FileNotFoundException {

    Board b;

    Scanner scanner = new Scanner(System.in);
    System.out.println("TYPE \"L\" TO LOAD SAVEGAME OR PRESS ENTER TO START A NEW GAME");
    String load = scanner.nextLine();
    String[] loadinfo = load.split(" ");
    boolean whiteTurn;
    if (loadinfo[0].equals("L")) {
      File indata = new File("Savegame.txt");
      b = Board.readFromFile(indata);
      whiteTurn = b.setTurn();
    }
    else {
      b = new Board(false);
      whiteTurn = true;
    }

    String[] info;

    do {
      if (whiteTurn) {
        System.out.println("|WHITE PLAYER'S TURN|");
      }
      else {
        System.out.println("|BLACK PLAYER'S TURN|");
      }
      System.out.println(b);
      String in = scanner.nextLine();
      info = in.split(" ");

      //if (info[0].equals("S")) { //AUTOSAVES BY COMMENTING THIS LINE OUT
        PrintWriter writer = new PrintWriter("Savegame.txt", "UTF-8");
        String turn;
        if (whiteTurn) {
          turn = "White";
        }
        else {
          turn = "Black";
        }
        writer.println(turn);
        writer.println("#, Pawns, {tileLetter,tileNumber,colour}");
        for(Tile t : b.boardList) {
          if (t.getPiece() instanceof Pawn) {
            int num = t.getNumber();
            writer.println(t.getLetter() + ", " + num + ", " + t.getPiece().getColour());
          }
        }
        writer.println("#, Rooks, {tileLetter,tileNumber,colour}");
        for(Tile t : b.boardList) {
          if (t.getPiece() instanceof Rook) {
            int num = t.getNumber();
            writer.println(t.getLetter() + ", " + num + ", " + t.getPiece().getColour());
          }
        }
        writer.println("#, Bishops, {tileLetter,tileNumber,colour}");
        for(Tile t : b.boardList) {
          if (t.getPiece() instanceof Bishop) {
            int num = t.getNumber();
            writer.println(t.getLetter() + ", " + num + ", " + t.getPiece().getColour());
          }
        }
        writer.println("#, Knights, {tileLetter,tileNumber,colour}");
        for(Tile t : b.boardList) {
          if (t.getPiece() instanceof Knight) {
            int num = t.getNumber();
            writer.println(t.getLetter() + ", " + num + ", " + t.getPiece().getColour());
          }
        }
        writer.println("#, Queens, {tileLetter,tileNumber,colour}");
        for(Tile t : b.boardList) {
          if (t.getPiece() instanceof Queen) {
            int num = t.getNumber();
            writer.println(t.getLetter() + ", " + num + ", " + t.getPiece().getColour());
          }
        }
        writer.println("#, Kings, {tileLetter,tileNumber,colour}");
        for(Tile t : b.boardList) {
          if (t.getPiece() instanceof King) {
            int num = t.getNumber();
            writer.println(t.getLetter() + ", " + num + ", " + t.getPiece().getColour());
          }
        }
        writer.close();
        /**System.out.println("Game saved as Savegame.txt");
      }

      else */if (in.length() == 0) {
        System.out.println(b);
      }

      else if (in.length() == 5) {
        String[] temp = info[0].split("");
        String[] temp2 = info[1].split("");
        String[] data = {temp[0],temp[1],temp2[0],temp2[1]};

        String cl = data[0];
        int cn = Integer.parseInt(data[1]);
        String nl = data[2];
        int nn = Integer.parseInt(data[3]);
        if (b.move(cl,cn,nl,nn,whiteTurn)) {
          whiteTurn = !whiteTurn;
        }
      }
      else {
        System.out.println("Incorrect input!");
      }
    } while (!info[0].equals("E"));
  }
}
