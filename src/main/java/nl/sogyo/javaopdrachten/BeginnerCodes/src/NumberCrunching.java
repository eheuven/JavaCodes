import java.util.ArrayList;
import java.util.Scanner;

public class NumberCrunching {
	
	public static boolean leapYear(int year) {
		/** 
		 * Checks if given year is leap year
		 * Input: year to check
		 * Output true or false
		 */
		if(year % 100 == 0) {
			if(year % 400 == 0) {
				return true;
			}
		}else {
			if(year % 4 == 0) {
				return true;
			}
		}
		return false;
	}
	
	public static void collatzSequence(int number) {
		/**
		 * Prints Collatz sequence starting from given number
		 * Input: starting number
		 * Output: none
		 */
		while(number != 1) {
			if(number % 2 ==0) {
				number /= 2; // divide by 2
			}else {
				number = 3 * number + 1;
			}
			System.out.println(number);
		}
	}
	
	public static int primeNumber(int index) {
		/**
		 * Calculates the prime number at given index 
		 * Input: index of desired prime number
		 * Output: prime number
		 */
		ArrayList<Integer> primeList = new ArrayList<>();
		primeList.add(2);
		
		for(int i = 2; primeList.size() < index; i++) {
			boolean prime = true;
			for(int n = 0; n < primeList.size(); n++) {
				if(i % primeList.get(n) == 0) {
					prime = false;
					break;
				}
			}
			if (prime) { // not divided
				primeList.add(i);
			}
		}
		return primeList.get(primeList.size() -1);
	}
	
	public static double kmToMile(double km) {
		/** converts km to miles */
		double mile = km / 1.609344;
		return mile;
	}
	
	public static double celciusToFahrenheid(double celcius) {
		/** converts celcius to fahrenheid */
		double fahrenheid = celcius * 9 / 5 + 32;
		return fahrenheid;
	}
	
	public static String BMI(double weight, double height) {
		/** 
		 * Makes diagnose based on calculated BMI 
		 * Input: weight (kg), height (m)
		 * Output: weight category 
		 * */
		double BMI = weight / Math.pow(height, 2);
		if(BMI < 18.5) {
			return "underweight"; 
		}else if(BMI < 25) {
			return "normal";
		}else if(BMI < 30) {
			return "overweight";
		}else {
			return "obese";
		}
	}
	
	public static void fibonacciSequence(int index) {
		ArrayList<Integer> sequence = new ArrayList <>();
		sequence.add(0);
		sequence.add(1);
		int evenSum = 0;
		
		for(int i = 2; i < index; i++) {
			int value = sequence.get(sequence.size()-1) + sequence.get(sequence.size()-2);
			sequence.add(value);
			if(value%2==0) {
				evenSum += value;
			}
		}
		
		System.out.printf("The value of the %dth term of the Fibonacci sequence is %d%n", index, sequence.get(sequence.size()-1));
		System.out.printf("The sum of all even values of the first %d terms is %d%n", index, evenSum);
	}
	
	
	public static void listsRequired() {
		/** manually find the highest or lowest value in a list */
		// random number list
		ArrayList<Integer> list = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			list.add((int)(Math.random()*100)); // random number [0,100)
		}
		System.out.println(list);
		
		// highest
		int highest = 0;
		for(int n = 0; n < list.size(); n++) {
			if(list.get(n) > highest) {
				highest = list.get(n);
			}
		}
		System.out.println(highest);
		
		// sum lowest two
		int lowest = highest;
		int low = highest;
		for(int a = 0; a < list.size(); a++) {
			if(list.get(a) < lowest) {
				low = lowest;
				lowest = list.get(a);
			}else if(list.get(a) < low) {
				low = list.get(a);
			}
		}
		System.out.println(low+lowest);
		
		// even values and four lists
		ArrayList<Integer> evenList = new ArrayList<>();
		ArrayList<Integer> threeList = new ArrayList<>();
		ArrayList<Integer> fiveList = new ArrayList<>();
		ArrayList<Integer> otherList = new ArrayList<>();
		
		for(int b = 0; b < list.size(); b++) {
			if(list.get(b) % 2 == 0) {
				evenList.add(list.get(b));
			}else if(list.get(b) % 3 == 0){
				threeList.add(list.get(b));
			}else if(list.get(b) % 5 == 0){
				fiveList.add(list.get(b));
			}else{
				otherList.add(list.get(b));
			}
		}
		System.out.println(evenList);
		System.out.println(threeList);
		System.out.println(fiveList);
		System.out.println(otherList);
		
		// sort
		int[] array = new int[list.size()];
		for(int c = 0; c < list.size(); c++) {
			array[c] = list.get(c);
		}
		bubbleSort(array);
		ArrayList<Integer> sortedList = new ArrayList<>();
		for(int item : array) { 
			sortedList.add(item);
		}
		System.out.println(sortedList);
	}
	
	static void bubbleSort(int[] arr) {  // standard BubbleSort algorithm
        int n = arr.length;  
        int temp = 0;  
         for(int i=0; i < n; i++){  
             for(int j=1; j < (n-i); j++){  
            	 if(arr[j-1] > arr[j]){  
            		 //swap elements  
                     temp = arr[j-1];  
                     arr[j-1] = arr[j];  
                     arr[j] = temp;  
                 }  
                          
             }  
         }
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in); // type input in console
	    
		System.out.println("Enter a year");
		int year = sc.nextInt();
	    System.out.println(leapYear(year));
	    
	    System.out.println("Enter starting number");
	    int coll = sc.nextInt();  
	    collatzSequence(coll);

		System.out.println(primeNumber(10001));
		
		System.out.println("Enter a distance in km");
		double km = sc.nextDouble();
		System.out.println(kmToMile(km));
		System.out.println("Enter a temperature in degrees Celcius");
		double celcius = sc.nextDouble();
		System.out.println(celciusToFahrenheid(km));
		
		System.out.println("Enter weight in kg and a height in meters");
		double weight = sc.nextDouble();
		double height = sc.nextDouble();
		System.out.println(BMI(weight,height));
		
		System.out.println("Give a number: ");
		int fibo = sc.nextInt();
		fibonacciSequence(fibo);
		
		listsRequired();
	}

}
