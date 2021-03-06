package pieces;

import java.util.ArrayList;

import chess.Color;
import chess.Move;
import chess.MoveEvent;
import chess.Piece;
import chess.Position;
import chess.Square;

public class Pawn extends Piece {

	public Pawn(int color) {
		super(color);
		value = 1;
	}
	
	public Pawn(Piece other) {
		super(other);
		value = 1;
	}

	@Override
	public String symbol() {
		if (color == Color.WHITE)
			return "\u2659";
		else if (color == Color.BLACK)
			return "\u265F";
		else {
			System.out.println("Invalid color for pawn: " + color);
			System.out.println(Thread.currentThread().getStackTrace());
			System.exit(-1);
		}
		return "";
	}

	public ArrayList<Move> pseudoLegalMoves(Piece[][] board, Square square) {
		ArrayList<Move> moves = new ArrayList<Move>();
		
		int col = square.getCol();
		int row = square.getRow();
		
		// Case: White Pawn
		if (color == Color.WHITE) {
			// Target square: single forward move (sf)
			int sfTargetCol = col;
			int sfTargetRow = row+1;
			if (board[sfTargetCol][sfTargetRow] == null) { // destination square has to be empty
				if (row != 6) { // not about to promote
					moves.add(new Move(this, new Square(square), new Square(sfTargetCol, sfTargetRow)));
				}
				else { // about to promote
					moves.add(new Move(this, new Square(square), new Square(sfTargetCol, sfTargetRow), new Knight(color)));
					moves.add(new Move(this, new Square(square), new Square(sfTargetCol, sfTargetRow), new Bishop(color)));
					moves.add(new Move(this, new Square(square), new Square(sfTargetCol, sfTargetRow), new Rook(color)));
					moves.add(new Move(this, new Square(square), new Square(sfTargetCol, sfTargetRow), new Queen(color)));
				}
			}
			// Target square: double forward move (df)
			if (row == 1) {
				int dfTargetCol = col;
				int dfTargetRow = row+2;
				int inBetweenCol = col;
				int inBetweenRow = row+1;
				if (board[inBetweenCol][inBetweenRow] == null && board[dfTargetCol][dfTargetRow] == null) { // if both squares are empty
					moves.add(new Move(this, new Square(square), new Square(dfTargetCol, dfTargetRow)));
				}
			}
			// Target square: diagonally ahead, left and right
			Square[] diagonalTargetSquares = new Square[]{new Square(col-1, row+1), new Square(col+1, row+1)};
			for (Square s : diagonalTargetSquares) {
				int targetCol = s.getCol();
				int targetRow = s.getRow();
				if (!(targetCol < 0 || targetCol > Position.BOARD_WIDTH-1)) {
					if (board[targetCol][targetRow] != null && board[targetCol][targetRow].getColor() != color) { // has to be of opposite color to be captured
						if (row != 6) { // not about to promote
							moves.add(new Move(this, new Square(square), new Square(targetCol, targetRow)));
						}
						else { // about to promote
							moves.add(new Move(this, new Square(square), new Square(targetCol, targetRow), new Knight(color)));
							moves.add(new Move(this, new Square(square), new Square(targetCol, targetRow), new Bishop(color)));
							moves.add(new Move(this, new Square(square), new Square(targetCol, targetRow), new Rook(color)));
							moves.add(new Move(this, new Square(square), new Square(targetCol, targetRow), new Queen(color)));
						}
					}
				}
			}
			// En passent
			if (row == 4) {
				if (col != 0 && board[col-1][row] instanceof Pawn && board[col-1][row].getColor() != color) {
					moves.add(new Move(this, new Square(square), new Square(col-1, row+1), MoveEvent.EN_PASSENT));
				}
				if (col != Position.BOARD_WIDTH-1 && board[col+1][row] instanceof Pawn && board[col+1][row].getColor() != color) {
					moves.add(new Move(this, new Square(square), new Square(col+1, row+1), MoveEvent.EN_PASSENT));
				}
			}
		}
		// Case: Black Pawn
		else if (color == Color.BLACK) {
			// Target square: single forward move (sf)
			int sfTargetCol = col;
			int sfTargetRow = row-1;
			if (board[sfTargetCol][sfTargetRow] == null) { // destination square has to be empty
				if (row != 1) { // not about to promote
					moves.add(new Move(this, new Square(square), new Square(sfTargetCol, sfTargetRow)));
				}
				else { // about to promote
					moves.add(new Move(this, new Square(square), new Square(sfTargetCol, sfTargetRow), new Knight(color)));
					moves.add(new Move(this, new Square(square), new Square(sfTargetCol, sfTargetRow), new Bishop(color)));
					moves.add(new Move(this, new Square(square), new Square(sfTargetCol, sfTargetRow), new Rook(color)));
					moves.add(new Move(this, new Square(square), new Square(sfTargetCol, sfTargetRow), new Queen(color)));
				}
			}
			// Target square: double forward move (df)
			if (row == 6) {
				int dfTargetCol = col;
				int dfTargetRow = row-2;
				int inBetweenCol = col;
				int inBetweenRow = row-1;
				if (board[inBetweenCol][inBetweenRow] == null && board[dfTargetCol][dfTargetRow] == null) { // if both squares are empty
					moves.add(new Move(this, new Square(square), new Square(dfTargetCol, dfTargetRow)));
				}
			}
			// Target square: diagonally ahead, left and right
			Square[] diagonalTargetSquares = new Square[]{new Square(col-1, row-1), new Square(col+1, row-1)};
			for (Square s : diagonalTargetSquares) {
				int targetCol = s.getCol();
				int targetRow = s.getRow();
				if (!(targetCol < 0 || targetCol > Position.BOARD_WIDTH-1)) {
					if (board[targetCol][targetRow] != null && board[targetCol][targetRow].getColor() != color) { // has to be of opposite color to be captured
						if (row != 1) { // not about to promote
							moves.add(new Move(this, new Square(square), new Square(targetCol, targetRow)));
						}
						else { // about to promote
							moves.add(new Move(this, new Square(square), new Square(targetCol, targetRow), new Knight(color)));
							moves.add(new Move(this, new Square(square), new Square(targetCol, targetRow), new Bishop(color)));
							moves.add(new Move(this, new Square(square), new Square(targetCol, targetRow), new Rook(color)));
							moves.add(new Move(this, new Square(square), new Square(targetCol, targetRow), new Queen(color)));
						}
					}
				}
			}
			// En passent
			if (row == 3) {
				if (col != 0 && board[col-1][row] instanceof Pawn && board[col-1][row].getColor() != color) {
					moves.add(new Move(this, new Square(square), new Square(col-1, row-1), MoveEvent.EN_PASSENT));
				}
				if (col != Position.BOARD_WIDTH-1 && board[col+1][row] instanceof Pawn && board[col+1][row].getColor() != color) {
					moves.add(new Move(this, new Square(square), new Square(col+1, row-1), MoveEvent.EN_PASSENT));
				}
			}
		}
		else {
			new Exception("Invalid color for pawn: " + color).printStackTrace();
			System.exit(-1);
		}
		
		return moves;
	}

	@Override
	public String toString() {
		return color + " pawn";
	}

}
