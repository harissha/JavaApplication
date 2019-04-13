package javaTesting.Numbers;

public class DisplayMultiplicationTable {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/* Program to Display Multiplication Table */
		
//		      int num = Integer.parseInt(args[0]);
			
			int num=7;
		      System.out.println("*****MULTIPLICATION TABLE*****");

		      for(int i=1;i<=num;i++){

		         for(int j=1;j<=num;j++){

		            System.out.print(" "+i*j+" ");

		         }

		         System.out.print("\n");

		      }

	}

}
