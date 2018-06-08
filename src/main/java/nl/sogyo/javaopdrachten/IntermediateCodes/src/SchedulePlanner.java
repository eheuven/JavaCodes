import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SchedulePlanner {

	public static void main(String[] args) {
		/**
		 * A week schedule that consists of five day schedules with an arbitrary amount of tasks
		 * input: weekday, task name, time in hours
		 * output: total time planned that day and in the week, overtime warnings
		 */
		Scanner sc = new Scanner(System.in);
		double totalTime = 0;
		double taskTime = 0;
		String taskName = "";
		
		HashMap<String,Double> weekdayTime = new HashMap<>();
		weekdayTime.put("Monday", 0.0);
		weekdayTime.put("Tuesday", 0.0);
		weekdayTime.put("Wednesday", 0.0);
		weekdayTime.put("Thursday", 0.0);
		weekdayTime.put("Friday", 0.0);
		
		HashMap<String,HashMap<String,Double>> tasks = new HashMap<>();
		tasks.put("Monday", new HashMap<String,Double>());
		tasks.put("Tuesday", new HashMap<String,Double>());
		tasks.put("Wednesday", new HashMap<String,Double>());
		tasks.put("Thursday", new HashMap<String,Double>());
		tasks.put("Friday", new HashMap<String,Double>());

		while(totalTime < 40) {
			// input
			String taskWeekday = "";
			while(!weekdayTime.containsKey(taskWeekday)) {
				if (taskWeekday != "") {
					System.out.println("Error: input is not a weekday");
				}
				System.out.printf("Enter a weekday to schedule a task ");
				taskWeekday = sc.next(); 
			}
			System.out.printf("Enter the time in hours spend on this task ");
			taskTime = sc.nextDouble(); 
			System.out.printf("Enter the description or name of this task ");
			taskName = sc.next();
			
			// update variables
			totalTime += taskTime;
			weekdayTime.put(taskWeekday, weekdayTime.get(taskWeekday)+taskTime);
			tasks.get(taskWeekday).put(taskName,taskTime);

			// print overview tasks and time spend
			System.out.printf("%nNew task added, overview tasks %s:%n",taskWeekday);
			for (Map.Entry<String,Double> task : tasks.get(taskWeekday).entrySet()) {
				System.out.printf("%s, hours spend: %.1f %n",task.getKey(),task.getValue());
			}
			System.out.printf("Time spend %s: %.1f hours, total time spend: %.1f hours%n%n",taskWeekday,weekdayTime.get(taskWeekday),totalTime);
			if(weekdayTime.get(taskWeekday) > 9) {
				System.out.printf("Overtime warning: more than 9 hours spend on %s%n%n",taskWeekday);
			}
		}
		if(totalTime == 40) {
			System.out.println("Schedule is full, 40 hours reached");
		}else {
			System.out.println("Overtime warning: more than 40 hours a week");
		}	
	}

}
