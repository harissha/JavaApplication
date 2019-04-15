package programs;

import java.util.Scanner;

public class NumberPalindrome {

    public static boolean isPalindrome(int integer) {
        int palindrome = integer;
        int reverse = 0;

        // Compute the reverse
        while (palindrome != 0) {
            int remainder = palindrome % 10;
            reverse = reverse * 10 + remainder;
            palindrome = palindrome / 10;
        }

        // The integer is palindrome if integer and reverse are equal
        return integer == reverse; // Improved by Peter Lawrey

    }

    public static boolean isPalindrome1(int integer) {
        String intStr = String.valueOf(integer);
        return intStr.equals(new StringBuilder(intStr).reverse().toString());
    }

    public static void main(String args[]){
        int r,sum=0,temp;
        int n=454;//It is the number variable to be checked for palindrome

        temp=n;
        while(n>0){
            r=n%10;  //getting remainder
            sum=(sum*10)+r;
            n=n/10;
        }
        if(temp==sum)
            System.out.println("palindrome number ");
        else
            System.out.println("not palindrome");




        System.out.println("Please enter an integer : ");
        int integer = new Scanner(System.in).nextInt();

        if(isPalindrome(integer)){
            System.out.println(integer + " is a palindrome");
        }else{
            System.out.println(integer + " is not a palindrome");
        }

    }

}
