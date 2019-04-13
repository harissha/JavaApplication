package javaTesting.Switch;

public class SwitchDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/* Write a program to display a greet message according to

		   Marks obtained by student.

		*/

//        int marks = Integer.parseInt(args[0]);         //taking value as command line argument.

		  int marks=60;
		         switch(marks/10){

		            case 10:

		            case 9:

		            case 8:

		                     System.out.println("Excellent");

		                     break;

		            case 7:

		                     System.out.println("Very Good");

		                     break;

		            case 6:

		                     System.out.println("Good");

		                     break;

		            case 5:

		                     System.out.println("Work Hard");

		                     break;

		            case 4:

		                     System.out.println("Poor");

		                     break;

		            case 3:

		            case 2:

		            case 1:

		            case 0:

		                     System.out.println("Very Poor");

		                     break;

		            default:

		                     System.out.println("Invalid value Entered");

		      }
	}

}
