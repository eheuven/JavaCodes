import java.util.ArrayList;

public class FifteenSolver {
	ArrayList<SquarePiece> tileList;
	
	public FifteenSolver(ArrayList<SquarePiece> pieces) {
		tileList = pieces;
	}

	public void nextMove(SquarePiece gap) {
		/*
		 * solve first row first:
		 * - line numbers up except last 2
		 * - before last number at place of last number(top right corner) & last number below this
		 * - "rotate" the last 2 numbers
		 * solve first column the same way
		 * 2x2 puzzle: rotate clockwise or counterclockwise
		 */
		
		gap = moveGapToPosition(gap,tileList.get(10));
		moveNumToPosition(gap,tileList.get(10), tileList.get(1));

		System.out.println("print");
	}
	
	private SquarePiece moveGapToPosition(SquarePiece from, SquarePiece to) {
		SquarePiece current = from;
		while(current != to) {
			current = current.switchPieces(nextRoutePiece(current,to));
		}
		return current;
	}

	public SquarePiece nextRoutePiece(SquarePiece piece, SquarePiece destination) {
		String[] horMoves =  {"left","right","above","under"};
		String[] vertMoves =  {"above","under","left","right"};
		
		if(piece != destination) {
			piece = moveDirection(piece,destination.getColNumber(), piece.getColNumber(),horMoves);
			piece = moveDirection(piece,destination.getRowNumber(), piece.getRowNumber(),vertMoves);
		}
		return piece;
	}

	private SquarePiece moveDirection(SquarePiece piece,int destinationNum,int thisNum, String[] directions) {	
		if(thisNum == destinationNum) {
			return piece;
		}
		if(thisNum > destinationNum && piece.adjacentPieceMoveable(directions[0])) {
			return piece.getAdjecentPiece(directions[0]);
		} else if(thisNum < destinationNum && piece.adjacentPieceMoveable(directions[1])) {
			return piece.getAdjecentPiece(directions[1]);
		} else {
			if(piece.adjacentPieceMoveable(directions[2])) {
				return piece.getAdjecentPiece(directions[2]);
			}else {
				return piece.getAdjecentPiece(directions[3]);
			}
		}
	}
	
	
	
	private void moveNumToPosition(SquarePiece gap, SquarePiece from, SquarePiece to) { // in progress
		// piece moves back one place in gap path if gap reaches
		// gap in front of next place --> switch (also switch forbidden coord)
		// forbidden coords: currentvaluecoord + placed list
		
		from.setMoveable(false);
		gap = moveGapToPosition(gap,nextRoutePiece(from,to));
		
		// movingCoord.moveable = false
		
		
		// gap.moveGapTo(movingCoordRoute[i]) <-- based on gap moving
		// movingCoord.moveable = true
		// movingCoord = gap.moveAdjecentPiece(movingCoord)
		//i++
		
	}

	

}
