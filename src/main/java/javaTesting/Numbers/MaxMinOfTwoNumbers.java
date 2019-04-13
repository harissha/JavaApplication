package javaTesting.Numbers;

public class MaxMinOfTwoNumbers {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		//taking value as command line argument.

	      //Converting String format to Integer value

	      int i = Integer.parseInt(args[0]);

	      int j = Integer.parseInt(args[1]);

	      if(i > j)

	          System.out.println(i+" is greater than "+j);

	      else

	          System.out.println(j+" is greater than "+i);
	      
	    //taking value as command line argument.

	      //Converting String format to Integer value

	      int i1 = Integer.parseInt(args[0]);

	      int j1 = Integer.parseInt(args[1]);

	      int result = (i1<j1)?i1:j1;

	      System.out.println(result+" is a minimum value");

	}

}
