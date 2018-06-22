import java.util.ArrayList;

public class FifteenSolver {
	ArrayList<SquarePiece> tileList;
	SquarePiece prevAssignedTile;
	int redo = 0;
	
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
		// line numbers up except last 2
		gap = moveNumToPosition(gap,placeOfValue(1), tileList.get(1-1)); 
		gap = moveNumToPosition(gap,placeOfValue(2), tileList.get(2-1));
		
		// before last number at place of last number(top right corner) & last number below this then rotate into position
		gap = moveLastTwo(gap,3,4);
		//gap = rotateIntoPosition(gap,3,4);
		
		// first column
		gap = moveNumToPosition(gap,placeOfValue(5), tileList.get(5-1));
		gap = moveLastTwo(gap,9,13);
		//gap = rotateIntoPosition(gap,9,13);
		
		//3x3
		gap = moveNumToPosition(gap,placeOfValue(6), tileList.get(6-1));
		gap = moveLastTwo(gap,7,8);
		//gap = rotateIntoPosition(gap,7,8);
		
		gap = moveLastTwo(gap,10,14); // places wrong??
		/*
		if (gap != null) {
			gap = rotateIntoPosition(gap,10,14);
			System.out.println("computer solved it");
		} else {
			placeOfValue(14).setMoveable(true);
			placeOfValue(10).setMoveable(true);
			
			gap = rotateNumbers("left");
			gap = moveLastTwo(gap,10,14);

			if (gap != null) {
			System.out.println("computer solved it after rotation");
			}else {
				placeOfValue(14).setMoveable(true);
				placeOfValue(10).setMoveable(true);
				
				gap = rotateNumbers("right");
				gap = moveLastTwo(gap,10,14);
				
				if (gap!= null) {
				System.out.println("two rotations");
				}else {
					System.out.println("not solved");
				}
				
			}
		}*/
	}

	private SquarePiece moveLastTwo(SquarePiece gap, int value1, int value2) {
		int inc = 1;
		if((value2 -1) % 4 == 3) { // row +7, column +1
			inc = 7;
		}
		
		int i = 7;
		for(i = 0; i <5; i++) {
			gap = moveNumToPosition(gap,placeOfValue(value2), tileList.get(value2 + inc)); 
			gap = moveNumToPosition(gap,placeOfValue(value1), tileList.get(value1 + 4));
			
			if (gap == null) {
				gap = placeOfValue(0);
				placeOfValue(value1).setMoveable(true);
				placeOfValue(value2).setMoveable(true);
				
				if(tileList.get(value1-1).getValue() == value1) {
					gap = rotateNumbers("left");
					System.out.println("rotate left");
				} else {
					gap = rotateNumbers("right");
					System.out.println("rotate right");
				}
				gap = moveLastTwo(gap,value1,value2);
				
			} else {
				break;
			}
		}
		gap = rotateIntoPosition(gap,value1,value2);
		
		if(i>0) {
			System.out.printf("rotated %d times to solve",i);
		}else {
			System.out.println("solved");
		}
		
		return gap;// places wrong??
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
					System.out.printf("stuck, enable %d (moving backward)", previous.getValue());
					
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
			try {
			direction = nextDirection(from,from.getColNumber(),destination.getColNumber(), horMoves);
			}catch(Exception e) {
				System.out.println(from);
				System.out.println(from.getColNumber());
				System.out.println(destination.getColNumber());
			}
			
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
		if (redo > 5) {
			return null;
		}

		SquarePiece current = from;
		SquarePiece unreachableGap = null;
		current.setMoveable(false);
		int counter = 0;
		int prevNumber = 0;
		
		System.out.printf("start moving %d previous: ", from.getValue());
		if (prevAssignedTile != null) {
			prevNumber = prevAssignedTile.getValue();
			System.out.println(prevAssignedTile.getValue());
		}
		
		

		while(current != to) {
			if (prevAssignedTile != null) {
			System.out.printf("prevTile = %d%n",prevAssignedTile.getValue());
			}
			
			counter++;
			if (counter > 9) {
				System.out.println("move tile steps > 9");
				break;
			}
			
			String nextDirection = nextRouteDirection(current,to);
			System.out.println("nextD: " + nextDirection);
			if(nextDirection.equals("stuck")){
				gap.setMoveable(true);
				System.out.println("gap enabled");
				
				nextDirection = nextRouteDirection(current,to);
				System.out.println("new direction: " + nextDirection);
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

				if(gap == null && prevAssignedTile != null) {
					prevAssignedTile.setMoveable(true);
					prevNumber = prevAssignedTile.getValue();
					System.out.printf("%nstuck twice, enable moving: %d%n",prevNumber);
					gap = placeOfValue(0);
					gap.setMoveable(false);
					for(SquarePiece tile : gap.adjacentPieces.values()) {
						System.out.printf("(%d %b) ",tile.getValue(),tile.isMoveable());
					}
					
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
		
		SquarePiece previousTile = prevAssignedTile;
		prevAssignedTile = current;
		System.out.printf("%d placed correctely%n",current.getValue());
		
		if(previousTile != null) {
			System.out.printf("previous tile = %d%n",prevNumber); 
			
			if(previousTile.getValue() != prevNumber) {
				System.out.print("redo: ");
				System.out.println(prevNumber);
				redo++;
				return moveNumToPosition(gap,placeOfValue(prevNumber), previousTile);
			}
		}
		System.out.print("doorgaan");
		return gap;
		
	}

	private SquarePiece rotateIntoPosition(SquarePiece gap,int value1, int value2) {
		
		gap = moveGapToPosition(gap,tileList.get(value2-1));
		placeOfValue(value2).setMoveable(true);
		
		gap = swap(gap,value1);
		gap = swap(gap,value2);	
		
		placeOfValue(value1).setMoveable(false);
		
		gap = moveGapToPosition(gap,tileList.get(value1-1));
		placeOfValue(value2).setMoveable(true);
		
		gap = swap(gap,value1);
		gap = swap(gap,value2);
		placeOfValue(value1).setMoveable(false);
		
		prevAssignedTile = placeOfValue(value2);
		System.out.println(prevAssignedTile.getValue());
		
		
		return gap;
	}

	private SquarePiece swap(SquarePiece gap, int value) {
		
		try {
		String gapDirection = gap.getDirection(placeOfValue(value)); // zeropoint error hier
		System.out.print(gapDirection + " ");
		
		gap = gap.moveAdjecentPiece(gapDirection); 

		}catch(Exception e) {
			System.out.println(value);
			System.out.println(placeOfValue(value));
			System.out.println(gap.getDirection(placeOfValue(value)));
		}

		for(SquarePiece piece: tileList) {
			piece.printGridPart();
		}
		return gap;
	}
	
	private SquarePiece rotateNumbers(String direction) {
		
		System.out.println("rotate");
		
		String reverseDirection = "left";
		SquarePiece gap = placeOfValue(0);
		prevAssignedTile = null;
		gap.setMoveable(true);
		redo = 0;
		
		if (direction.equals("left")) {
			reverseDirection = "right";
		}
		
		SquarePiece end = null;
		if(gap.hasMoveableAdjecentPiece(reverseDirection)){
			end = gap.getAdjecentPiece(reverseDirection);
		} else if(gap.hasMoveableAdjecentPiece("above")) {
			end = gap.getAdjecentPiece("above");
		} else {
			end = gap.getAdjecentPiece("under");
		}
		
		int i = 0;
		while(gap != end) {
			i++;
			if (i>6) {
				System.out.println("rotate fails");
				break;
			}
			
			if(gap.hasMoveableAdjecentPiece(direction)) {
				gap = gap.moveAdjecentPiece(direction); 
				System.out.print(direction + " ");
			} else {
				if(direction == "left") {
					direction = "right";
				}else {
					direction = "left";
				}
				
				if(gap.hasMoveableAdjecentPiece("above")) {
					gap = gap.moveAdjecentPiece("above");
					System.out.print("above ");
				} else {
					gap = gap.moveAdjecentPiece("under");
					System.out.print("under ");
				}
			}
		}
		gap.setMoveable(false);

		for(SquarePiece piece: tileList) {
			piece.printGridPart();
		}
		
		return gap;
	}
	
	
}
