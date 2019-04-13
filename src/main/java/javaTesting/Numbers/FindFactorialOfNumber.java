package javaTesting.Numbers;

public class FindFactorialOfNumber {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int number = 4;               
        int factorial = number;
       
        for(int i =(number - 1); i > 1; i--)
        {
                factorial = factorial * i;
        }
        
        
        int factorial1 = number;
        int factorial2=factorial1;
        while(factorial1 > 1)
        {
        	factorial2=factorial2*(factorial1-1);
        	
        	factorial1=factorial1-1;
        	
        }

        System.out.println("Factorial of "+number+" Using For is " + factorial);
        
        System.out.println("Factorial of "+number+" Using While is " + factorial2);


	}

}
