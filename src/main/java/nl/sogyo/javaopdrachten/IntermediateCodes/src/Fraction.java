
public class Fraction {
	int numberator;
	int denominator;
	
	public Fraction(int top, int bottom) {
		numberator = top;
		denominator = bottom;
		minimizeDenominator();
	}

	public int getNumberator() {
		return numberator;
	}
	
	public int getDenominator(){
		return denominator;
	}
	
	private void minimizeDenominator() {
		int[] smallPrimes = {2,3,5,7};
		boolean change = true;
		
		while(change) {
			change = false;
			for(int prime : smallPrimes) {
				if (numberator % prime == 0 && denominator % prime == 0) {
					numberator /= prime;
					denominator /= prime;
					change = true;
					break;
				}
			}
		}
	}
	
	public double toDecimalNotation() {
		double decimalNumber = Math.pow(10, 5)* numberator / denominator;
		decimalNumber = Math.round(decimalNumber);
		decimalNumber /= Math.pow(10, 5);
		return decimalNumber;
	}
	
	public String toString() {
		return Integer.toString(numberator) + "/" + Integer.toString(denominator);
	}
	
	private int sharedDenominator(Fraction anotherFraction) {
		return denominator * anotherFraction.getDenominator();
	}
	
	public Fraction add(int number) {
		int addedNumberator = numberator + denominator * number;
		return new Fraction(addedNumberator,denominator);
	}
	
	public Fraction add(Fraction anotherFraction) {
		int addedNumberator = numberator * anotherFraction.getDenominator() + denominator * anotherFraction.getNumberator();
		return new Fraction(addedNumberator,sharedDenominator(anotherFraction));
	}
	
	public Fraction subtract(int number) {
		int subtractedNumberator = numberator - number * denominator;
		return new Fraction(subtractedNumberator,denominator);
	}
	
	public Fraction subtract(Fraction anotherFraction) {
		int subtractedNumberator = numberator * anotherFraction.getDenominator() - anotherFraction.getNumberator() * denominator;
		return new Fraction(subtractedNumberator,sharedDenominator(anotherFraction));
	}
	
	public Fraction multiply(int number) {
		int multipliedNumberator = numberator * number;
		return new Fraction(multipliedNumberator,denominator);
	}
	
	public Fraction multiply(Fraction anotherFraction) {
		int multipliedNumberator = numberator * anotherFraction.getNumberator();
		int multipliedDenominator = denominator * anotherFraction.getDenominator();
		return new Fraction(multipliedNumberator,multipliedDenominator);
	}
	
	public Fraction divide(int number) {
		int dividedDenominator = denominator * number;
		return new Fraction(numberator,dividedDenominator);
	}
	
	public Fraction divide(Fraction anotherFraction) {
		int dividedDenominator = denominator * anotherFraction.getNumberator();
		int dividedNumberator = numberator * anotherFraction.getDenominator();
		return new Fraction(dividedNumberator,dividedDenominator);
	}

	public static void main(String[] args) {
		
		Fraction oneThird = new Fraction(1,3);
		Fraction oneSixth = new Fraction(1,6);
		Fraction fourThird = new Fraction(4,3);
		Fraction oneHalf = new Fraction(1,2);
		Fraction threeFourth = new Fraction(3,4);
		Fraction twoFifth = new Fraction(2,5);
		Fraction threeHalf = new Fraction(3,2);
		Fraction threeTenth = new Fraction(3,10);
		
		System.out.println(oneThird.toDecimalNotation());
		System.out.println(oneThird.toString());
		
		System.out.println(oneThird.add(1));
		System.out.println(oneThird.add(oneSixth));
		System.out.println(fourThird.subtract(1));
		System.out.println(oneHalf.subtract(oneSixth));
		
		System.out.println(threeFourth.multiply(2));
		System.out.println(threeFourth.multiply(twoFifth));
		
		System.out.println(threeHalf.divide(2));
		System.out.println(threeTenth.divide(twoFifth));
		
	}

}
