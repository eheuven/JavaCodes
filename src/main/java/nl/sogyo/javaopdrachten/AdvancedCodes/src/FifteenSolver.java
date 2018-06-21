import java.util.ArrayList;

public class FifteenSolver {
	ArrayList<SquarePiece> tileList;
	
	public FifteenSolver(ArrayList<SquarePiece> pieces) {
		tileList = pieces;
	}

	public void solve(SquarePiece gap) {
		/*
		 * solve first row first:
		 * - line numbers up except last 2
		 * - before last number at place of last number(top right corner) & last number below this
		 * - "rotate" the last 2 numbers
		 * solve first column the same way
		 * 2x2 puzzle: rotate clockwise or counterclockwise
		 */
		gap = moveNumToPosition(gap,placeOfValue(1), tileList.get(0)); 
		gap = moveNumToPosition(gap,placeOfValue(2), tileList.get(1));
		gap = moveNumToPosition(gap,placeOfValue(3), tileList.get(7));
		gap = moveNumToPosition(gap,placeOfValue(4), tileList.get(11));
		
		

		//System.out.println("print");
	}
	
	private SquarePiece placeOfValue(int i) {
		for (SquarePiece piece : tileList) {
			if(piece.getValue() == i) {
				return piece;
			}
		}
		return null;
	}

	private SquarePiece moveGapToPosition(SquarePiece from, SquarePiece to) {
		from.setMoveable(true);
		SquarePiece current = from;
		SquarePiece previous = null;
		boolean stuckBefore = false;
		
		for(int count = 1; current != to; count++) {
			if(count > 20) {
				to.setMoveable(false);
				break;
			} 
			
			String direction = nextRouteDirection(current,to); 
			if (direction.equals("stuck")) {
				if (previous != null) {
					previous.setMoveable(true);
					if (stuckBefore) {
						return null;
					}
					stuckBefore = true;
				}else {
					return null;
				}
				
			}else {
				SquarePiece movedPiece = current.moveAdjecentPiece(direction); 
				System.out.print(direction + " ");
				
				if(previous != null) {
					previous.setMoveable(true);
				}
				previous = current; current = movedPiece;
				previous.setMoveable(false);
			}
		}
		
		if(previous != null) {
			previous.setMoveable(true);
		}
		return current;
	}

	public String nextRouteDirection(SquarePiece from, SquarePiece destination) {
		String[] horMoves =  {"left","right"};
		String[] vertMoves =  {"above","under"};
		String direction = null;
		
		if(from != destination) {
			direction = nextDirection(from,from.getColNumber(),destination.getColNumber(), horMoves);
			if (direction == null || direction == "other") {
				direction = nextDirection(from,from.getRowNumber(),destination.getRowNumber(), vertMoves);
				if (direction == null) {
					direction = forceMove(from,vertMoves);
				}else if(direction == "other") {
					direction = forceMove(from,horMoves);
					if (direction == "stuck") {
						direction = forceMove(from,vertMoves);
					}
				}
			}
		}
		return direction;
	}

	private String nextDirection(SquarePiece piece, int thisNum, int destinationNum, String[] directions) {	
		if(thisNum == destinationNum) {
			return null;
		}
		if(thisNum > destinationNum && piece.adjacentPieceMoveable(directions[0])) {
			return directions[0];
		} else if(thisNum < destinationNum && piece.adjacentPieceMoveable(directions[1])) {
			return directions[1];
		} else {
			return "other";
			
		}
	}
	
	private String forceMove(SquarePiece piece, String[] directions) {
		if(piece.adjacentPieceMoveable(directions[0])) {
			return directions[0];
		}else if(piece.adjacentPieceMoveable(directions[1])){
			return directions[1];
		}else {
			return "stuck";
		}
	}
	

	private SquarePiece moveNumToPosition(SquarePiece gap, SquarePiece from, SquarePiece to) {
		
		SquarePiece current = from;
		SquarePiece prevPlaced = null;
		SquarePiece unreachableGap = null;
		current.setMoveable(false);
		
		while(current != to) {
			String nextDirection = nextRouteDirection(current,to);
			if(nextDirection.equals("stuck")){
				gap.setMoveable(true);
				nextDirection = nextRouteDirection(current,to);
			}
			SquarePiece nextTile = current.getAdjecentPiece(nextDirection);
						
			gap = moveGapToPosition(gap,nextTile);
			
			if (!nextTile.isMoveable()) {
				unreachableGap = nextTile;
			} else { 
				
				if(unreachableGap != null){
					unreachableGap.setMoveable(true);
					unreachableGap = null;
				}

				if(gap == null) {
					prevPlaced = placeOfValue(current.getValue()-1); 
					prevPlaced.setMoveable(true);
					gap = placeOfValue(0);
				} else {
					current.setMoveable(true); 
					
					String gapDirection = gap.getDirection(current);
					current = gap;
					gap = gap.moveAdjecentPiece(gapDirection);
					
					System.out.print(gapDirection + " ");
					for(SquarePiece piece: tileList) {
						piece.printGridPart();
					}
					
					gap.setMoveable(false);
					current.setMoveable(false);
				}
			}
		}
			
		if(prevPlaced == null) {
			return gap;
		}else {
			return moveNumToPosition(gap,placeOfValue(current.getValue()-1), prevPlaced);
		}
	}

	

}
