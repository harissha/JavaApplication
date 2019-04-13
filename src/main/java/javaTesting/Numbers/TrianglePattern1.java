package javaTesting.Numbers;

public class TrianglePattern1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		/* Display Triangle as follow : BREAK DEMO.

	    1

	    2 3

	    4 5 6

	    7 8 9 10 ... N */

		
	          int c=0;

//	          int n = Integer.parseInt(args[0]);
	          
	          int n=10;

	         loop1: for(int i=1;i<=n;i++){

	         loop2: for(int j=1;j<=i;j++){

	                       if(c!=n){

	                            c++;

	                            System.out.print(c+" ");

	                       }

	                       else

	                           break loop1;

	                    }

	                    System.out.print("\n");

	                 }
	}

}
