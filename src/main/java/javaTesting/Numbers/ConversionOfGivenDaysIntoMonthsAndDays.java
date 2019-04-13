package javaTesting.Numbers;

public class ConversionOfGivenDaysIntoMonthsAndDays {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		/* Write a program to convert given no. of days into months and days.

		  (Assume that each month is of 30 days)

		  Example :

		           Input - 69

		           Output - 69 days = 2 Month and 9 days */


//		      int num = Integer.parseInt(args[0]);
			
			int num=56;
		      int days = num%30;

		      int month = num/30;

		      System.out.println(num+" days = "+month+" Month and "+days+" days");
	}

}
