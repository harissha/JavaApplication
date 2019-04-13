package javaTesting.Regex;

import java.util.*;
import java.util.regex.*;

public class PrintContentsOfValidHTMLorXMLtags {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*Sample Input:
		4
		<h1>Nayeem loves counseling</h1>
		<h1><h1>Sanjay has no watch</h1></h1><par>So wait for a while</par>
		<Amee>safat codes like a ninja</amee>
		<SA premium>Imtiaz has a secret crush</SA premium>
		
		Sample Output:
		Nayeem loves counseling
		Sanjay has no watch
		So wait for a while
		None
		Imtiaz has a secret crush
		*/
		
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
	      int testCases = Integer.parseInt(in.nextLine());
	      while(testCases>0){
	         String line = in.nextLine();
	         
	             //Write your code here
	          String pattern ="\\<(.+)\\>([^\\<\\>]+)\\<\\/\\1\\>";

	           int count = 0;

	            Pattern p = Pattern.compile(pattern);
	            Matcher m =  p.matcher(line);

	            while(m.find())
	            {
	            	//System.out.println(m.group(1));
	                System.out.println(m.group(2));
	                count++;
	            }
	            if(count == 0){
	                System.out.println("None");
	            }

	                 testCases--;
	       }

	}

}
