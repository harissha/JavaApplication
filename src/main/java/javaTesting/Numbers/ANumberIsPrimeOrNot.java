package javaTesting.Numbers;

public class ANumberIsPrimeOrNot {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		/* Write a program to Find whether number is Prime or Not. */

//		          int num = Integer.parseInt(args[0]);
				
				int num=7;
		         int flag=0;

		         for(int i=2;i<num;i++){

		             if(num%i==0)

		              {

		                 System.out.println(num+" is not a Prime Number");

		                 flag = 1;

		                 break;

		              }

		         }

		         if(flag==0)

		             System.out.println(num+" is a Prime Number");
	}

}
