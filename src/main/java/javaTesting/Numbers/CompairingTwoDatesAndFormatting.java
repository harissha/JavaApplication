package javaTesting.Numbers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CompairingTwoDatesAndFormatting {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Instantiate a objects
		Date date1 = new Date();
		Date date2 = new Date();
	        
	  	if(date1.compareTo(date2)>0)
	  	{
			System.out.println("Date1 is after Date2");
		}
	  	else if(date1.compareTo(date2)<0)
	  	{
			System.out.println("Date1 is before Date2");
		}
	  	else
	  	{
			System.out.println("Date1 is equal to Date2");
		}	
	  	
	 // display current timestamp
	    System.out.println(date1.getTime());
	    System.out.println(date2.getTime());
	    
	 // display time and date
	    System.out.println(date1.toString());
	    System.out.println(date2.toString());
	    
	 // Date Formatting   
	    Date date = new Date( );
	    SimpleDateFormat dateFormat = new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");

	    System.out.println("Current Date: " + dateFormat.format(date));	
	}

}
