import java.util.ArrayList;

public class FifteenSolver {
	ArrayList<SquarePiece> tileList;
	SquarePiece prevAssignedTile;
	SquarePiece gap;
	int redo = 0;
	
	public FifteenSolver(ArrayList<SquarePiece> pieces) {
		tileList = pieces;
	}

	public void solve(SquarePiece givenGap) {
		/*
		 * solving tricks:
		 * - line numbers up except last 2 of row/column
		 * - before last number at place of last number(top right corner) & last number below this
		 * - "rotate" the last 2 numbers
		 */
		gap = givenGap;
		
		// 4x4
		moveNumToPosition(placeOfValue(1), tileList.get(0)); 
		moveNumToPosition(placeOfValue(2), tileList.get(1));
		
		moveLastTwo(3,4);
		moveNumToPosition(placeOfValue(5), tileList.get(4));
		moveLastTwo(9,13);
		
		//3x3
		moveNumToPosition(placeOfValue(6), tileList.get(5));
		moveLastTwo(7,8);
		moveLastTwo(10,14);
		
		// 2x2
		while(tileList.get(10).getValue() != 11) {
			rotateNumbers("left");
		}
		finishSolvingIfPossible(12,15);
		
	}

	private void finishSolvingIfPossible(int value1, int value2) {
		if(tileList.get(value1-1).getValue() == value1 || tileList.get(value2-2).getValue() == value2) {
			String gapDirection = gap.getDirection(tileList.get(value2));
			
			if (gapDirection != null) {
				gapSwapTo(gapDirection);
				printGrid();
			}
			
			System.out.println("Computer finished solving the puzzle!");
		}else {
			System.out.println("Computer gave up on solving the puzzle.");
		}
	}

	private void moveLastTwo(int value1, int value2) {
		if(checkCorrectPlace(value1,value2)) {
			return;
		}
		
		int value2Place = checkColListPlace(value2); 
				
		for(int i = 0; i <5; i++) {
			boolean moveValue2Suceeds = moveNumToPosition(placeOfValue(value2), tileList.get(value2Place)); 
			boolean moveValue1Suceeds = moveNumToPosition(placeOfValue(value1), tileList.get(value1 + 4));
			
			if (!moveValue1Suceeds || !moveValue2Suceeds) {
				prepareRetryMoving(value1,value2);
			} else {
				break;
			}
		}
		rotateIntoPosition(value1,value2);
	}
	

	private int checkColListPlace(int value2) {
		boolean rowItem = (value2 -1) % 4 == 3;
		int colItemInc = 2;
		
		if(rowItem) { 
			return value2-1 + colItemInc * 4;
		} else {
			return value2-1 + colItemInc; 
		}
	}

	private boolean checkCorrectPlace(int value1, int value2) {
		if(placeOfValue(value1) == tileList.get(value1-1) && placeOfValue(value2) == tileList.get(value2-1)) {
			placeOfValue(value1).setMoveable(false);
			placeOfValue(value2).setMoveable(false);
			return true;
		}
		return false;
	}

	private void prepareRetryMoving(int value1, int value2) {
		placeOfValue(value1).setMoveable(true);
		placeOfValue(value2).setMoveable(true);
		
		if(tileList.get(value1+4).getValue() == value1) {
			rotateNumbers("left");
		} else {
			rotateNumbers("right");
		}
	}

	private SquarePiece placeOfValue(int i) {
		for (SquarePiece piece : tileList) {
			if(piece.getValue() == i) {
				return piece;
			}
		}
		return null;
	}

	private boolean moveGapToPosition(SquarePiece to) {
		SquarePiece previous = null;
		boolean stuckBefore = false;
		
		gap.setMoveable(true);
		
		for(int count = 1; gap != to; count++) {
			if(count > 20) {
				disableUnreachableTile(to,previous);
				return false;
			} 
			
			String direction = nextRouteDirection(gap,to); 
			if (direction.equals("stuck")) {
				stuckBefore = enableGoingBackOnce(stuckBefore, previous);
				if (!stuckBefore) {
					return false;
				}
			}else {
				previous = moveGap(previous, direction);
			}
		}
		
		if (previous != null) {
			previous.setMoveable(true);
		}
		return true;
	}

	private SquarePiece moveGap(SquarePiece previous, String direction) {
		if (previous != null) {
			previous.setMoveable(true);
		}
		previous = gap;
		gapSwapTo(direction);
		previous.setMoveable(false);
		return previous;
	}

	private boolean enableGoingBackOnce(boolean stuckBefore, SquarePiece previous) {
		if (previous != null) {
			previous.setMoveable(true);
			if (!stuckBefore) {
				return true;
			}
		}
		return false;
	}

	private void disableUnreachableTile(SquarePiece to, SquarePiece previous) {
		to.setMoveable(false);
		
		if (previous != null) {
			previous.setMoveable(true);
		}
		
		printGrid();
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
	

	private boolean moveNumToPosition(SquarePiece from, SquarePiece to) {
		SquarePiece previousTile = prevAssignedTile;
		from.setMoveable(false);
		int prevNumber = findPrevNumber();
		prevAssignedTile = moveNumToNextPlace(from, to);
		return placeMovedPrevAssignedBack(previousTile, prevNumber);
	}

	private SquarePiece moveNumToNextPlace(SquarePiece current, SquarePiece to) {
		SquarePiece unreachableGap = null;
		while (current != to) {		
			String nextDirection = findNextDirection(current,to);	
			SquarePiece nextTile = current.getAdjecentPiece(nextDirection); 
			boolean gapMove = moveGapToPosition(nextTile); 
			
			if (!gapMove && !nextTile.isMoveable()) {
				unreachableGap = nextTile;
			} else if(!gapMove && prevAssignedTile != null) {
				enablePrevAsigned();
			} else {
				reachableAgain(unreachableGap);
				unreachableGap = null;
				current = moveTile(current);
			}
		}
		return current;
	}

	private int findPrevNumber() {
		if (prevAssignedTile != null) {
			return prevAssignedTile.getValue();
		}
		return 0;
	}

	private boolean placeMovedPrevAssignedBack(SquarePiece previousTile, int prevNumber) {
		if (redo > 5) {
			redo = 0;
			return false;
		}else if(previousTile != null) {
			if(previousTile.getValue() != prevNumber) {
				redo++;
				return moveNumToPosition(placeOfValue(prevNumber), previousTile);
			}
		}
		redo = 0;
		return true;
	}

	private String findNextDirection(SquarePiece current, SquarePiece to) {
		String nextDirection = nextRouteDirection(current,to);
		if(nextDirection.equals("stuck")){
			gap.setMoveable(true);
			nextDirection = nextRouteDirection(current,to);
		}
		return nextDirection;
	}

	private void enablePrevAsigned() {
		prevAssignedTile.setMoveable(true);
		gap.setMoveable(false);
	}

	private void reachableAgain(SquarePiece unreachableGap) {
		if(unreachableGap != null){
			unreachableGap.setMoveable(true);	
		}
	}

	private SquarePiece moveTile(SquarePiece tile) {
		tile.setMoveable(true); 
		SquarePiece movedTilePlace = gap;
		gapSwapTo(gap.getDirection(tile));
		
		gap.setMoveable(false);
		movedTilePlace.setMoveable(false);
		
		printGrid();
		return movedTilePlace;
	}

	private void rotateIntoPosition(int value1, int value2) {
		moveGapToPosition(tileList.get(value2-1));
		rotateOnce(value1,value2);
		
		moveGapToPosition(tileList.get(value1-1));
		rotateOnce(value1,value2);
		
		prevAssignedTile = placeOfValue(value2);
	}
	
	private void rotateOnce(int value1, int value2) {
		placeOfValue(value2).setMoveable(true);
		placeOfValue(value1).setMoveable(true);//
		
		gapSwapTo(gap.getDirection(placeOfValue(value1)));
		gapSwapTo(gap.getDirection(placeOfValue(value2)));
		
		placeOfValue(value1).setMoveable(false);
		placeOfValue(value2).setMoveable(false);//
		
		printGrid();
	}

	private void rotateNumbers(String direction) {
		prevAssignedTile = null;
		gap.setMoveable(true);
		SquarePiece end = findEnd(direction);
		
		if(gap.hasMoveableAdjecentPiece("above")) {
			direction = changeDirection(direction);
		}
		
		while(gap != end) {
			direction = rotateOnce(direction);
		}
		
		gap.setMoveable(false);
		printGrid();
	}
	
	private String rotateOnce(String direction) {
		if(gap.hasMoveableAdjecentPiece(direction)) {
			gapSwapTo(direction);
		} else {
			direction = changeDirection(direction);
			
			if(gap.hasMoveableAdjecentPiece("above")) {
				gapSwapTo("above");
			} else {
				gapSwapTo("under");
			}
		}
		return direction;
	}

	private String changeDirection(String direction) {
		if(direction == "left") {
			return "right";
		}else {
			return "left";
		}
	}
	
 	private SquarePiece findEnd(String direction) {
		
		if(gap.hasMoveableAdjecentPiece("right") && direction.equals("left")){
			return gap.getAdjecentPiece("right");
		}else if(gap.hasMoveableAdjecentPiece("left") && direction.equals("right")){
			return gap.getAdjecentPiece("left");
		} else if(gap.hasMoveableAdjecentPiece("above")) {
			return gap.getAdjecentPiece("above");
		} else {
			return gap.getAdjecentPiece("under");
		}
	}
	
	
	private void gapSwapTo(String direction){
		gap = gap.moveAdjecentPiece(direction); 
		System.out.print(direction + " ");
	}
	
	private void printGrid() {
		for(SquarePiece piece: tileList) {
			piece.printGridPart();
		}
	}
	
	
	
}
