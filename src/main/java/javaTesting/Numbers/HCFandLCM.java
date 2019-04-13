package javaTesting.Numbers;

public class HCFandLCM {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		//HCF
		int m=15,n=3;
		while(m!=n)
		{
			if(m>n)
			{
				m=m-n;
			}
			else
			{
				n=n-m;
			}
		}
		System.out.println(m+" "+n);
		
		//LCM
		int a=3,b=4;
		int m1,n1;

	    m1=a;
	    n1=b;

	    while(m1!=n1)
	    {
	    	if(m1 < n1)
	    	{
	    		m1=m1+a;
	    	}
	    	else
	    	{
	    		n1=n1+b;
	    	}
	    }
	    System.out.println(m1+" "+n1);

	}

}
