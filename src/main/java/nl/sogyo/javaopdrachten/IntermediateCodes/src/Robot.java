import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Robot {
	int[] position = new int[2];
	String facing;
	ArrayList<String> orientations = new ArrayList<String>();
	ArrayList<Runnable> plannedMoves = new ArrayList<>();
	
	public Robot(int up, int right, String direction) {
		position[0] = up;
		position[1] = right;
		facing = direction;
		
		Collections.addAll(orientations,"North","East","South","West");
	}
	
	private int directionIndex() {
		return orientations.indexOf(facing);
	}

	private void turn(String direction) {
		int nextOrientation = 9;

		if (direction.equals("right")) {
			nextOrientation = (directionIndex() + 1) % 4;
		}else {
			nextOrientation = (directionIndex() + 3) % 4;
		}
		facing = orientations.get(nextOrientation);
	}
	
	private void addPosition(int[] place) {
		position[0] += place[0];
		position[1] += place[1];
	}
	
	private void move(int speed) {
		int[][] coordArray = {{1,0},{0,1},{-1,0},{0,-1}};
		ArrayList<int[]> coordList = new ArrayList<>(Arrays.asList(coordArray));
		int[] directionCoord = coordList.get(directionIndex());
		
		directionCoord[0] *= speed;
		directionCoord[1] *= speed;
		
		addPosition(directionCoord);
	}
	
	private void planMove(Runnable aMethod) {
        plannedMoves.add(aMethod);
    }
	
	public void printVariables() {
		System.out.printf("(%d,%d) %s%n", position[0], position[1],facing);
	}
	
	public void turnRight() {
		Runnable rightTurn = () -> turn("right");
		planMove(rightTurn);
	}
	
	public void turnLeft() {
		Runnable leftTurn = () -> turn("left");
		planMove(leftTurn);
	}
	
	public void forward() {
		Runnable moveForward = () -> move(1);
		planMove(moveForward);
	}
	
	public void backward() {
		Runnable moveBackward = () -> move(-1);
		planMove(moveBackward);
	}
	
	public void forward(int speed) {
		if (speed > 0 && speed <=3) {
			Runnable moveForward = () -> move(speed);
			planMove(moveForward);
		}else {
			System.out.println("Error: Wrong speed, only a value beteen 1 and 3 allowed");
		}
	}
	
	public void execute(){
        for(Runnable plannedMove : plannedMoves) {
            plannedMove.run();
        }
   }
	
	public static void main(String[] args) {
		Robot my_first_robot = new Robot(0, 1, "East");
		Robot my_second_robot = new Robot(1, 0, "West");
		
		my_first_robot.printVariables();
		my_second_robot.printVariables();
		
		my_first_robot.turnLeft();
		my_first_robot.turnLeft();
		my_second_robot.turnRight();
		
		my_first_robot.forward();
		my_second_robot.backward();
		
		my_first_robot.forward(3);
		my_second_robot.backward();
		
		my_first_robot.execute();
		
		my_first_robot.printVariables();
		my_second_robot.printVariables();
		
		my_second_robot.execute();
		
		my_first_robot.printVariables();
		my_second_robot.printVariables();
	}

}
