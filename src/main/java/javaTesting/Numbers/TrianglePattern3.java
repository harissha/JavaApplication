package javaTesting.Numbers;

public class TrianglePattern3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/* Display Triangle as follow

	    1

	    2 4

	    3 6 9

	    4 8 12 16 ... N (indicates no. of Rows) */

//	          int n = Integer.parseInt(args[0]);
	          
	          int n=4;

	                   for(int i=1;i<=n;i++){

	                     for(int j=1;j<=i;j++){

	                        System.out.print((i*j)+" ");

	                    }

	                    System.out.print("\n");

	                 }

	}

}
