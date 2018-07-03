import java.util.ArrayList;
import java.util.Map.Entry;

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
		if(tileList.get(11).getValue() == 12 || tileList.get(14).getValue() == 15) {
			String gapDirection = gap.getDirection(tileList.get(15));
			
			if (gapDirection != null) {
				gapSwapTo(gapDirection);
				printGrid();
			}
			
			System.out.println("Computer finished solving the puzzle!");
		}else {
			System.out.println("Computer failed solving the puzzle.");
		}
		
		
	}

	private void moveLastTwo(int value1, int value2) {
		if(placeOfValue(value1) == tileList.get(value1-1) && placeOfValue(value2) == tileList.get(value2-1)) {
			placeOfValue(value1).setMoveable(false);
			placeOfValue(value2).setMoveable(false);
			return;
		}
		
		
		int inc = 1;
		if((value2 -1) % 4 == 3) { // row +7, column +1
			inc = 7;
		}
		
		int i = 7;
		for(i = 0; i <5; i++) {
			boolean move1 = moveNumToPosition(placeOfValue(value2), tileList.get(value2 + inc)); 
			boolean move2 = moveNumToPosition(placeOfValue(value1), tileList.get(value1 + 4));
			System.out.printf("move 1: %b, move 2: %b%n",move1,move2);
			
			if (!move1 || !move2) {
				placeOfValue(value1).setMoveable(true);
				placeOfValue(value2).setMoveable(true);
				
				if(tileList.get(value1+4).getValue() == value1) {
					System.out.println("value1 correct placed = right rotate");
					rotateNumbers("left");
				} else {
					rotateNumbers("right");
				}
				
			} else {
				break;
			}
		}
		rotateIntoPosition(value1,value2);

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
		gap.setMoveable(true);
		SquarePiece previous = null;
		boolean stuckBefore = false;
		
		for(int count = 1; gap != to; count++) {
			if(count > 20) {
				to.setMoveable(false);
				
				if (previous != null) {
					previous.setMoveable(true);
				}
				
				for(SquarePiece piece: tileList) {
					piece.printGridPart();
				}
				
				return false;
			} 
			
			String direction = nextRouteDirection(gap,to); 
			if (direction.equals("stuck")) {
				
				if (previous != null) {
					previous.setMoveable(true);
					if (stuckBefore) {
						return false;
					}
					stuckBefore = true;
				}else {
					return false;
				}
				
			}else {
				if (previous != null) {
					previous.setMoveable(true);
				}
				previous = gap;
				gapSwapTo(direction);
				previous.setMoveable(false);
			}
		}
		
		if (previous != null) {
			previous.setMoveable(true);
		}
		return true;
	}

	public String nextRouteDirection(SquarePiece from, SquarePiece destination) {
		
		String[] horMoves =  {"left","right"};
		String[] vertMoves =  {"above","under"};
		String direction = null;
		
		if(from != destination) {
			try {
			direction = nextDirection(from,from.getColNumber(),destination.getColNumber(), horMoves);
			} catch(Exception e) {
				from.getColNumber();
				destination.getColNumber();
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
					if (direction == "stuck") {
					System.out.println(direction);
					System.out.print("[left ");
					System.out.print(gap.hasMoveableAdjecentPiece("left"));
					System.out.print(" right ");
					System.out.print(gap.hasMoveableAdjecentPiece("right"));
					System.out.print(" up ");
					System.out.print(gap.hasMoveableAdjecentPiece("above"));
					System.out.print(" down ");
					System.out.print(gap.hasMoveableAdjecentPiece("under"));
					System.out.print("] ");}
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
		SquarePiece gapTile = gap;
		
		if (redo > 5) {
			redo = 0;
			return false;
		}

		SquarePiece current = from;
		SquarePiece unreachableGap = null;
		current.setMoveable(false);
		System.out.print(current.getValue()); System.out.println("not moveable");
		
		int counter = 0;
		int prevNumber = 0;
		
		if (prevAssignedTile != null) {
			prevNumber = prevAssignedTile.getValue();
		}

		while(current != to) {		
			counter++;
			if (counter > 9) {
				break;
			}
			
			String nextDirection = nextRouteDirection(current,to);
			System.out.println("next:"+nextDirection);
			
			if(nextDirection.equals("stuck")){
				gapTile.setMoveable(true);
				nextDirection = nextRouteDirection(current,to);
			}
			try {
				SquarePiece nextTile = current.getAdjecentPiece(nextDirection); //null
				if(prevAssignedTile != null) {
				System.out.println("prevAssignedtile:" + String.valueOf(prevAssignedTile.getValue()));
				}
				System.out.println("nexttile:" + String.valueOf(nextTile.getValue()));
				System.out.println("thistile:"+ String.valueOf(current.getValue()));
				System.out.println("gaptile:"+ String.valueOf(gapTile.getValue()));
				
			
				boolean gapMove = moveGapToPosition(nextTile); 
				gapTile = gap;
						
				System.out.println("gaptile:"+ String.valueOf(gapTile.getValue()));
				
				
				if (!nextTile.isMoveable()) {
					unreachableGap = nextTile;
					//enable previous place
					System.out.print(unreachableGap.getValue()); System.out.println(" unreachable");
				} else { 
					
					
	
					if(!gapMove && prevAssignedTile != null) {
						prevAssignedTile.setMoveable(true);
						prevNumber = prevAssignedTile.getValue();
						gapTile = placeOfValue(0);
						gapTile.setMoveable(false);
						System.out.println("stuck:enable " + String.valueOf(prevNumber));
						
						
					} else {
						
						if(unreachableGap != null){
							System.out.print(unreachableGap.getValue()); System.out.println(" moveable again");
							unreachableGap.setMoveable(true);
							unreachableGap = null;
							
						}
						
						current.setMoveable(true); 
						
						String gapDirection = gapTile.getDirection(current);
						current = gapTile;
						
						if(!gapTile.hasMoveableAdjecentPiece(gapDirection)) {
							throw new NullPointerException();
						}
						
						gapTile = gapTile.moveAdjecentPiece(gapDirection);
						gap = gapTile;
						
						System.out.print(gapDirection + " ");
						for(SquarePiece piece: tileList) {
							piece.printGridPart();
						}
						
						gapTile.setMoveable(false);
						current.setMoveable(false);
					}
					
					
				}
			} catch (Exception e) {
				System.out.print(nextDirection);
				
				
				System.out.print("left ");
				System.out.print(current.hasMoveableAdjecentPiece("left"));
				System.out.print(" right ");
				System.out.print(current.hasMoveableAdjecentPiece("right"));
				System.out.print(" up ");
				System.out.print(current.hasMoveableAdjecentPiece("above"));
				System.out.print(" down ");
				System.out.print(current.hasMoveableAdjecentPiece("under"));
				System.out.print(" ");
				
				throw e;
			}
		}
		
		gap = gapTile;
		SquarePiece previousTile = prevAssignedTile;
		prevAssignedTile = current;
		System.out.println(String.valueOf(current.getValue())+" placed");
		
		if(previousTile != null) {
			
			if(previousTile.getValue() != prevNumber) {
				redo++;
				return moveNumToPosition(placeOfValue(prevNumber), previousTile);
			}
		}
		
		redo = 0;
		
		return true;
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
		
		System.out.println("start rotation:" + gap.getDirection(placeOfValue(value1)));
		
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
		
		while(gap != end) {
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
		}
		gap.setMoveable(false);
		printGrid();
	}
	
	private String changeDirection(String direction) {
		if(direction == "left") {
			return "right";
		}else {
			return "left";
		}
	}
	
 	private SquarePiece findEnd(String direction) {
		if (direction.equals("left")) {
			if(gap.hasMoveableAdjecentPiece("right")){
				return gap.getAdjecentPiece("right");
			}
		}else {
			if(gap.hasMoveableAdjecentPiece("left")){
				return gap.getAdjecentPiece("left");
			}
		}	
			
		if(gap.hasMoveableAdjecentPiece("above")) {
			return gap.getAdjecentPiece("above");
		} else {
			return gap.getAdjecentPiece("under");
		}
	}
	
	
	private void gapSwapTo(String direction){
		try {
		if(!gap.hasMoveableAdjecentPiece(direction)) {
			throw new Exception();
		}}catch(Exception e) {
			System.out.println("something wrong in gapSwap");
			int i = 5/0;
		}
		
		
		gap = gap.moveAdjecentPiece(direction); 
		System.out.print(direction + " ");
	}
	
	private void printGrid() {
		for(SquarePiece piece: tileList) {
			piece.printGridPart();
		}
	}
	
	
	
}
