package javaTesting.Numbers;

public class FindFibonacciSeries {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*Write a program to find FibonacciSeries series of a given no.

		  Example :

		        Input - 8

		        Output - 1 1 2 3 5 8 13 21

		*/

//		          int num = Integer.parseInt(args[0]);                        //taking no. as command line argument.

		          
		          int num=6;
		          System.out.println("*****FibonacciSeries Series*****");

		          int f1, f2=0, f3=1;

		          for(int i=1;i<=num;i++){

		             System.out.print(" "+f3+" ");

		             f1 = f2;

		             f2 = f3;

		             f3 = f1 + f2;

		          }

	}

}
