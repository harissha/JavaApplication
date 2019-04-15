package programs;

import java.math.BigInteger;
import java.util.Scanner;

public class Factorial {

    public static void main(String args[]){
        int i,fact=1;
        int number=5;//It is the number to calculate factorial
        for(i=1;i<=number;i++){
            fact=fact*i;
        }
        System.out.println("Factorial of "+number+" is: "+fact);
    }



}


//Factorial Program using recursion in java
class FactorialExample2{
    static int factorial(int n){
        if (n == 0)
            return 1;
        else
            return(n * factorial(n-1));
    }
    public static void main(String args[]){
        int i,fact=1;
        int number=4;//It is the number to calculate factorial
        fact = factorial(number);
        System.out.println("Factorial of "+number+" is: "+fact);
    }
}



//One line Solution (Using Ternary operator):
// Java program to find factorial
// of given number
class Factorial1 {

    int factorial(int n)
    {

        // single line to find factorial
        return (n == 1 || n == 0) ? 1 : n * factorial(n - 1);

    }


    // Driver Code
    public static void main(String args[])
    {
        Factorial1 obj = new Factorial1();
        int num = 5;
        System.out.println("Factorial of " + num +
                " is " + obj.factorial(num));
    }

}


class BigFactorial
{
    public static void main(String args[])
    {
        int n, c;
        BigInteger inc = new BigInteger("1");
        BigInteger fact = new BigInteger("1");

        Scanner input = new Scanner(System.in);

        System.out.println("Input an integer");
        n = input.nextInt();

        for (c = 1; c <= n; c++) {
            fact = fact.multiply(inc);
            inc = inc.add(BigInteger.ONE);
        }

        System.out.println(n + "! = " + fact);
    }
}