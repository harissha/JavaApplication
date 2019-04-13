package javaTesting.Numbers;

public class SumOfIntegerGreaterThan100LessThan200divBy7 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/* Write a program to find sum of all integers greater than 100 and

		   less than 200 that are divisible by 7 */


		      int result=0;

		      for(int i=100;i<=200;i++){

		           if(i%7==0)

		              result+=i;

		      }

		      System.out.println("Output of Program is : "+result);

	}

}
