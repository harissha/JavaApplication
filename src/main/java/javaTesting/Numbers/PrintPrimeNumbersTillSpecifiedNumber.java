package javaTesting.Numbers;

public class PrintPrimeNumbersTillSpecifiedNumber {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int number=20;
		int count;
		
		System.out.println("Prime Numbers till "+number+" are:");

		for(int i=1; i<=number; i++)
		{
			count=0;
				for(int j=2; j<=i/2; j++)
				{
					if(i%j==0)
					{
						count++;
						break;
					}
				}
			if(count==0)
			{
				System.out.println(i);
			}
		}

	}

}
