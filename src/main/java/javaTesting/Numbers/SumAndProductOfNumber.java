package javaTesting.Numbers;

public class SumAndProductOfNumber {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*Write a program to find SUM AND PRODUCT of a given Digit. */


//		            int num = Integer.parseInt(args[0]);         //taking value as command line argument.

					int num=6;
		            int temp = num,result=0;

		            
		            //Logic for sum of digit
		            while(temp>0)
		            {
		               result = result + temp;
		               temp--;
		            }
		            System.out.println("Sum of Digit for "+num+" is : "+result);

		            
		            
		            
		            //Logic for product of digit
		            temp = num;
		            result = 1;

		            while(temp > 0)
		            {
		                 result = result * temp;
		                 temp--;
		            }
		            System.out.println("Product of Digit for "+num+" is : "+result);

	}

}
