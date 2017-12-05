package TestSamples;

import java.util.Random;

public class ProgramWithNestedIfs {
	public enum Currency { 
		PENNY(1), NICKLE(5), DIME(10), QUARTER(25);
		private final int value;

		private Currency(int value) {
			this.value = value;
		}
		
        public static Currency getRandomCoin() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }
		
		public int getValue() {
	        return value;
	    }
	};
	
	public static void printCoinType(String type) {
		System.out.println(type + " coin");
	}
	
	public static void printError() {
		System.out.println("Not a coin");
	}

	public static void main(String[] args) {
		int sum = 0;
		for (int i = 0; i < 15; i++) {
			Currency usCoin = Currency.getRandomCoin();
			
			if(usCoin == Currency.PENNY) {
				sum += usCoin.getValue();
				printCoinType("Penny");
			} else if(usCoin == Currency.NICKLE) {
				sum += usCoin.getValue();
				printCoinType("Nickle");
			} else if (usCoin == Currency.DIME) {
				sum += usCoin.getValue();
				printCoinType("Dime");
			} else if (usCoin == Currency.QUARTER) {
				sum += usCoin.getValue();
				printCoinType("Quarter");
			} else {
				printError();
			}
		}
		
		System.out.println("The total sum of the coins are: " + sum);
	}
}
