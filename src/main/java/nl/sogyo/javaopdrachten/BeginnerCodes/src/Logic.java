import java.util.Scanner;

public class Logic {

	public static boolean Palindrome(String word) {
		/**
		 * Checks if word is a palindrome
		 * Input: word
		 * Output: false or true
		 */
		char[] letterArray = word.toCharArray();
		for(int i = 0; i < letterArray.length / 2; i++) {
			if(letterArray[i] != letterArray[letterArray.length-1-i]) {//i from right == i from left
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter a word");
		String word = sc.next();
		System.out.println(Palindrome(word));
	}

}
