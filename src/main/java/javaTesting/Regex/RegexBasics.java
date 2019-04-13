package javaTesting.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexBasics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		 Pattern p = Pattern.compile("a*b");
		 Matcher m = p.matcher("aaaaab");
		 boolean b = m.matches();
		 System.out.println(b);
		 
		 /*This pattern "\\b(?<word>\\w+)(\\s+\\k<word>\\b)+" with 
		   input = input.replaceAll(m.group(), m.group("word")) along 
		   with Pattern.CASE_INSENSITIVE is overall a better readable solution.

		 Just for kicks, if you wanna try lookbehind and lookahead features of regex, 
		 you can employ negative lookahead and negative lookbehind to determine the 
		 start and end of the word: "(?<!\\S)(?<word>\\S+)(\\s+\\k<word>(?!\\S))+"
		 */

	}

}
