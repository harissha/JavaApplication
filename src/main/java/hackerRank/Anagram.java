package hackerRank;

/*Alice recently started learning about cryptography and wants to create her own encryption method.
Alice decides to generate a random seed for her encryption by transforming two strings into anagrams
by removing characters from each string as necessary.

Two words are anagrams of each other if the first word's letters can be rearranged to form the second word.
So, the two strings must have the same characters (in any order) and the same length.
For instance, given the strings "ab" and "cba", Alice can remove the "c" from "cba" to have "ba"
which is an anagram of "ab". The minimum number of operations performed to create the anagram is 1,
so that will be her seed value.

Your challenge is to complete a line of code to calculate this seed value.
You will be given two strings and must cumulate the minimum number of characters
that must be removed from each string to create an anagram.

Notes

Your code should replace the text FILL THE MISSING LINE HERE
The provided code should not be modified.
Input Format

Two lines each containing a string.

Constraints

 length of
 and  will only consist of lowercase latin letters, .
Output Format

A single integer which is the number of character deletions.

Sample Input 0

cde
abc
Sample Output 0

4
Explanation 0

We need to delete 4 characters to make both strings anagram i.e. "d" and "e" from first string and "b" and "a" from second string.

*/


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Anagram {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String src = (in.nextLine());
        String tar = in.nextLine();
        int length = 0;

        Map<Character, Integer> an = new HashMap<Character, Integer>();

        for (int i = 0; i < src.length(); i++) {
            char c = src.charAt(i);
            if (an.containsKey(c))
                an.put(c, an.get(c) + 1);
            else
                an.put(c, 1);
        }

        for (int j = 0; j < tar.length(); j++) {
            char c = tar.charAt(j);
            if (an.containsKey(c) && an.get(c) != 0) {
                an.put(c, an.get(c) - 1);
                length += 2;
            }
        }

        //System.out.println(an);
        System.out.println(src.length() + tar.length() - length);



    }
}
