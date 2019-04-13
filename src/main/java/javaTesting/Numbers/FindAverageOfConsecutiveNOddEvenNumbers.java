package javaTesting.Numbers;

public class FindAverageOfConsecutiveNOddEvenNumbers {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*Write a program to find average of consecutive N Odd no. and Even no. */


//		      int n = Integer.parseInt(args[0]);
		      
		      int n=10;
		      
		      int cntEven=0,cntOdd=0,sumEven=0,sumOdd=0;

		      while(n > 0){

		           if(n%2==0){

		               cntEven++;

		               sumEven = sumEven + n;

		           }

		           else{

		               cntOdd++;

		               sumOdd = sumOdd + n;

		           }

		           n--;

		      }

		      int evenAvg,oddAvg;

		      evenAvg = sumEven/cntEven;

		      oddAvg = sumOdd/cntOdd;

		      System.out.println("Average of first N Even no is "+evenAvg);

		      System.out.println("Average of first N Odd no is "+oddAvg);
		
	}

}
