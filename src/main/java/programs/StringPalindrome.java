package programs;

import java.util.Scanner;


public class StringPalindrome {

    public static void checkPalindrome(String s)
    {
        // reverse the given String
        String reverse = new StringBuffer(s).reverse().toString();

        // check whether the string is palindrome or not
        if (s.equals(reverse))
            System.out.println("The string is a palindrome.");
        else
            System.out.println("The string isn't a palindrome.");
    }

    public static void main(String args[])
    {
        String original, reverse = ""; // Objects of String class
        Scanner in = new Scanner(System.in);

        System.out.println("Enter a string to check if it is a palindrome");
        original = in.nextLine();

        int length = original.length();

        for (int i = length - 1; i >= 0; i--)
            reverse = reverse + original.charAt(i);

        if (original.equals(reverse))
            System.out.println("The string is a palindrome.");
        else
            System.out.println("The string isn't a palindrome.");


        checkPalindrome("malayalam");
        checkPalindrome("GeeksforGeeks");

    }
}
