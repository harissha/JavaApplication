package javaTesting.Numbers;

public class GenerateTriangle {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*Write a program to generate a Triangle.

		  eg:

		  1

		  2 2

		  3 3 3

		  4 4 4 4 and so on as per user given number */

//		          int num = Integer.parseInt(args[0]);

				int num=4;
		          for(int i=1;i<=num;i++){

		             for(int j=1;j<=i;j++){

		                System.out.print(" "+i+" ");

		             }
		             System.out.print("\n");

		           } 

	}

}
