package javaTesting.Arrays;

public class InitializingAccessingArrayelemets {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	/*	Examples:
		char[] refVar;
		int[] refVar;
		short[] refVar;
		long[] refVar;
		int[][] refVar; //two-dimensional array
	*/	
		
		int[] age1 = new int[5];    //5 is the size of array.
		
		int age[]={22,25,30,32,35};
		
		System.out.println(age1[2]);
		System.out.println(age[2]);

		
		int[] newArray = new int[5];
        // Initializing elements of array seperately
        for (int n = 0; n < newArray.length; n++) {
            newArray[n] = n;
        }
        System.out.println(newArray[2]); // Assigning 2nd element of array value

	}

}
