package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
    this.pieceColor = pieceColor;
    this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */

    public boolean viableDestination(ChessBoard board, ChessPosition endPosition) {
        int row = endPosition.getRow();
        int col = endPosition.getColumn();
        if (row < 1 || row > 8 || col < 1 || col > 8){
            return false;
        }
        ChessPiece target = board.getPiece(endPosition);
        if (target == null) {
            return true;
        }
        else {
            return target.getTeamColor() != this.pieceColor;
        }
    }


    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);
        Collection<ChessMove> moves = new ArrayList<>();
        if (piece.getPieceType() == PieceType.KING) {

        }
        if (piece.getPieceType() == PieceType.QUEEN) {
            int[][] directions = {
                    {1, 1}, {1, -1}, {-1, 1}, {-1, -1}, {1, 0}, {0, 1}, {-1, 0}, {0, -1}
            };
            for (int[] dir : directions) {
                int row = myPosition.getRow();
                int col = myPosition.getColumn();
                while (true){
                    row += dir[0];
                    col += dir[1];
                    ChessPosition newPos = new ChessPosition(row, col);
                    if (!viableDestination(board, newPos)) {
                        break;
                    }
                    moves.add(new ChessMove(myPosition, newPos, null));
                    if (board.getPiece(newPos) != null) {
                        break;
                    }
                }
            }
        }
        if (piece.getPieceType() == PieceType.ROOK) {
            int[][] directions = {
                    {1, 0}, {0, 1}, {-1, 0}, {0, -1}
            };
            for (int[] dir : directions) {
                int row = myPosition.getRow();
                int col = myPosition.getColumn();
                while (true){
                    row += dir[0];
                    col += dir[1];
                    ChessPosition newPos = new ChessPosition(row, col);
                    if (!viableDestination(board, newPos)) {
                        break;
                    }
                    moves.add(new ChessMove(myPosition, newPos, null));
                    if (board.getPiece(newPos) != null) {
                        break;
                    }
                }
            }
        }
        if (piece.getPieceType() == PieceType.BISHOP) {
            int[][] directions = {
                    {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
            };
            for (int[] dir : directions) {
                int row = myPosition.getRow();
                int col = myPosition.getColumn();
                while (true){
                    row += dir[0];
                    col += dir[1];
                    ChessPosition newPos = new ChessPosition(row, col);
                    if (!viableDestination(board, newPos)) {
                        break;
                    }
                    moves.add(new ChessMove(myPosition, newPos, null));
                    if (board.getPiece(newPos) != null) {
                        break;
                    }
                }
            }
        }
        if (piece.getPieceType() == PieceType.KNIGHT) {

        }
        if (piece.getPieceType() == PieceType.PAWN) {

        }

        return moves;
    }
}
