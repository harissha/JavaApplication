package javaTesting.String;

public class StringFunctions {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// string intialization through char
		char[] nameArray = {'A', 'l', 'e', 'x'};
        String name = new String(nameArray);
        System.out.println(name);
        System.out.println();
        
        System.out.println(name.split(""));
        
        System.out.println();
        String[] nam=name.split("");
        System.out.println(nam[1]);
        System.out.println();
        
        for(String s:nam)
        {
        	System.out.println(s);
        }
        System.out.println();
        
        for(char ch:nameArray)
        {
        	System.out.println(ch);
        }
        System.out.println();
        
        
        //concatenating strings
        String str1 = "Hello ", str2 = "World!";
        System.out.println(str1.concat(str2));
        System.out.println("Hello," + " world" + "!");
        
        
        //Upper & Lower cases
        String str3 = "Hello";
        System.out.println(str3.toUpperCase());
        System.out.println(str3.toLowerCase());
        
        
        // Trim functions
        String str = " Hello ";
        System.out.println(str.trim());
        
        //String Length functions
        String str4 = "Cloud";
        System.out.println(str4.length());


	}

}
