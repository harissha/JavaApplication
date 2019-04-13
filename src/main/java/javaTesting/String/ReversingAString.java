package javaTesting.String;

public class ReversingAString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//using stringbuffer
		  String string="abcdef";
	      String reverse = new StringBuffer(string).reverse().toString();
	      System.out.println("\nString before reverse:"+string);
	      System.out.println("String after reverse:"+reverse);

	    //using stringbuilder
		  String string1="abcdef";
	      String reverse1 = new StringBuilder(string1).reverse().toString();
	      System.out.println("\nString before reverse:"+string1);
	      System.out.println("String after reverse:"+reverse1);
	}

}
