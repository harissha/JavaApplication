package javaTesting.Numbers;

import java.util.Scanner;

public class TrianglePyramidStar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//For Triangle *
		System.out.println("The Pattern is");
        for (int i = 0; i < 5; i++) 
        {
            for (int j = 0; j <= i; j++) 
            {
                System.out.print("* ");
            }
            System.out.println();
        }
        
        System.out.println();
        
        //For Reverse Triangle *
        for(int i=5;i>=1;i--)
        {
        	for(int j=1;j<=i;j++)
        	{
        		System.out.print("* ");
        	}
        	System.out.println();
        }
        
        System.out.println();
        
        // //For Reverse x inverse Triangle *
        for(int i=5;i>=1;i--)
        {
        	for(int j=5;j>i;j--)
        	{
        		System.out.print(" ");
        	}
        	for(int k=1;k<=i;k++)
        	{
        		System.out.print("*");
        	}
        	System.out.println();
        }
        
        System.out.println();
        
        //For Pyramid *  and also inverse triangle using comments
        for(int i=0;i<5;i++) 
        {
            for(int j=0;j<5-i;j++) 
            {
                System.out.print(" ");           //System.out.print(" ");
            }
           for(int k=0;k<=i;k++) 
           {
               System.out.print("* ");            //System.out.print("*");
           }
           System.out.println();  
        }
        
        System.out.println();

      //For Pyramid *
        for (int i=0; i<6; i++)
        {
           for (int k=0; k<6-i; k++)
           {
              System.out.print(" ");    //System.out.print("  ");
           }
           for (int j=0; j<i*2+1; j++)
           {
              System.out.print("*");    //System.out.print("* ");
           }
           System.out.println();
        }
        
        System.out.println();
        
      //For Reverse Pyramid *
        for(int i=5;i>=1;i--)
        {
        	for(int j=5;j>i;j--)
        	{
        		System.out.print(" ");     //System.out.print("  ");
        	}
        	for(int k=1;k<(i*2);k++)
        	{
        		System.out.print("*");     //System.out.print(" *");
        	}
        	System.out.println();
        }

        System.out.println();

        //For Diamond *
        for(int i=1;i<=5;i++)
        {
        	for(int j=i;j<5;j++)
        	{
        		System.out.print(" ");
        	}
        	for(int k=1;k<(i*2);k++)
        	{
        		System.out.print("*");
        	}
        	System.out.println();
        }
        for(int i=4;i>=1;i--)
        {
        	for(int j=5;j>i;j--)
        	{
        		System.out.print(" ");
        	}
        	for(int k=1;k<(i*2);k++)
        	{
        		System.out.print("*");
        	}
        	System.out.println();
        }
        
        System.out.println();
        
        //Pascal Triangle 
        int bin,p,q,r,x;
        @SuppressWarnings("resource")
		Scanner s=new Scanner(System.in);
        System.out.println("How Many Row Do you want to input: ");
        
        r=s.nextInt();
        bin=1;
        q=0;

        System.out.println("Pascal's Triangle: ");

        while(q<r)
        {
        	for(p=40-3*q;p>0;--p)
        		System.out.print(" ");
        	for(x=0;x<=q;++x)
        	{
        		if((x==0)||(q==0))
        			bin=1;
        		else
        			bin=(bin*(q-x+1))/x;
        		System.out.print("      ");
        		System.out.print(bin);
        	}
        	System.out.println("");
        	++q;
        }
        
        
        
	}

}
