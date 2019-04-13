package javaTesting.Numbers;

public class FindReverseNumber {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//number defined
        int number = 1234;
        int reversedNumber = 0;
        int temp = 0;
       
        
        int n = number;
        while(n > 0)
        {                       
                //modulus operator used to strip off the last digit
                temp = n%10;
               
                //create reversed number
                reversedNumber = reversedNumber * 10 + temp;
                n = n/10;                         
        }
        
        
        String str =Integer.toString(number);
        int n1 = number;
        int t=0;
        int r=0;
        for(int i=1; i<=str.length(); i++)
        {
        	t= n1%10;
        	r= 10*r + t;
        	n1=n1/10;
        }
       
        //output
        System.out.println("Original Number is: " + number);
        System.out.println();
        System.out.println("Reversed Number Using While Loop is: " + reversedNumber);
        System.out.println("Reversed Number Using For Loop is: " + r);

	}

}
