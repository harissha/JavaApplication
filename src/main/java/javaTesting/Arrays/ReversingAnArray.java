package javaTesting.Arrays;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ReversingAnArray {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//to reverse an int[]
		List<Integer> list = Arrays.asList(1, 4, 9, 16, 9, 7, 4, 9, 11);
		System.out.println(list);
		Collections.reverse(list);
		System.out.println(list);
		
		
		//to convert a string into string[], cloning a string[] and then reversing it
		String s="hdsjahsf";
		String[] s1=s.split("");
		String[] validData=s1.clone();
		
				//reversing logic
		for(int i = 0; i < validData.length / 2; i++)
		{
		    String temp = validData[i];
		    validData[i] = validData[validData.length - i - 1];
		    validData[validData.length - i - 1] = temp;
		}
		
		System.out.println(s1[0]);
		System.out.println(validData[0]);
		
		//to convert a string into a char[]
		String str = "aabbab";
		char[] chs = str.toCharArray();
		System.out.println(chs);
		
		//comparing two arrays using equals. Here a is anagram to b.
		String a="Sghhjh";
		String b="sHghjh";
		char [] arraya = a.toLowerCase().toCharArray();
        char [] arrayb = b.toLowerCase().toCharArray();
        Arrays.sort(arraya);
        Arrays.sort(arrayb);
        Boolean retValue = Arrays.equals(arraya, arrayb);
		System.out.println(retValue);
		

	}

}
