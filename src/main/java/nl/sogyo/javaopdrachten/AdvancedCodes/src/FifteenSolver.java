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
		
		

		System.out.println("print");
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
		int stuckNr = 0;
		int count = 0;
		
		while(current != to) {
			count++;
			if(count > 20) {
				System.out.println("Error: space not reachable in 20 steps");
				break;
			} 
			
			String direction = nextRouteDirection(current,to); 
			if (direction.equals("stuck")) {
				stuckNr ++;
				System.out.print(stuckNr);
				if (previous != null) {
					if (stuckNr == 1 && previous != null) {
						previous.setMoveable(true); //stuck once: enable moving back and look again
					}else {
						break;
					}
				}else {
					stuckNr = 3;
					break;
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
		if(stuckNr > 1) {
			return null;
		}else if(previous != null) {
			previous.setMoveable(true);
		}
		return current;
	}

	public String nextRouteDirection(SquarePiece from, SquarePiece destination) {
		String[] horMoves =  {"left","right"};
		String[] vertMoves =  {"above","under"};
		String direction = "";
		
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
		current.setMoveable(false);
		int count = 0;
		
		while(current != to) {
			count++;
			if(count > 9) {
				System.out.println("Error: to many runs");
				break;
			} 
			gap = moveGapToPosition(gap,current.getAdjecentPiece(nextRouteDirection(current,to)));
			
			if(count > 7) {
				System.out.printf("%d %b%n",gap.getValue(),gap.isMoveable());
				for(SquarePiece piece : current.adjacentPieces.values()) {
					System.out.printf("%d %b",piece.getValue(),piece.isMoveable());
				}
			}
			
			if(gap == null) {
				prevPlaced = placeOfValue(current.getValue()-1); 
				prevPlaced.setMoveable(true);
				gap = placeOfValue(0);
				System.out.println(current.getValue());
			} else {

				current.setMoveable(true); // TODO disable gap before finding next of this number
				
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
		if(prevPlaced == null) {
			return gap;
		}else {
			return moveNumToPosition(gap,placeOfValue(current.getValue()-1), prevPlaced);
		}
	}

	

}
