package javaTesting.String;

class ConcatenateTwoStrings {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String a = "w3schools";
        String b = ".in";
        
        String c = a.concat(b);
        
        String d = a+b;
        
        System.out.println(c);
        System.out.println();
        System.out.println(d);
        
        /* Write a program to Concatenate  string using for Loop

        Example:

               Input - 5

               Output - 1 2 3 4 5 
         */

//           int num = Integer.parseInt(args[0]);
           
           
           int num=5;
           String result = " ";

           for(int i=1;i<=num;i++){

                result = result + i + " ";

           }

           System.out.println(result);

	}

}
