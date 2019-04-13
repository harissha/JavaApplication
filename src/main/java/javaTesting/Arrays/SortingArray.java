package javaTesting.Arrays;

import java.util.Arrays;
import java.util.Scanner;

public class SortingArray {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int[] x={1, 4, 9, 16, 9, 7, 4, 9, 11};
		Arrays.sort(x);
		System.out.println(x);
		for(int y:x)
		{System.out.print(y+", ");}
		System.out.println();
		
		
		 int n, temp;
		 @SuppressWarnings("resource")
		 Scanner s = new Scanner(System.in);
		   System.out.print("Enter no. of elements you want in array:");
		   n = s.nextInt();
		   int a[] = new int[n];
		   System.out.println("Enter all the elements:");
		   for (int i=0; i<n; i++) 
		    {
			   a[i]=s.nextInt();
		    }
		   for (int i =0; i<n; i++) 
		    {
		    	for (int j=i+1; j<n; j++) 
		    	{
		    		if (a[i]>a[j])  // compare numbers
		    		{
		    			temp=a[i];
		    			a[i]=a[j];
		    			a[j]=temp;
		    		}
		    	}
		    }
		  System.out.print("Elements in Ascending Order:");
		  for (int i=0; i<n-1; i++) 
		  {
		  System.out.print(a[i]+ ", "); // print in same line and separate with comma
		 }
		  System.out.print(a[n-1]);

	}

}
