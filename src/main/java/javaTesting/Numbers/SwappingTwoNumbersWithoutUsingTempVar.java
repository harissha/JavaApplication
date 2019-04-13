package javaTesting.Numbers;

public class SwappingTwoNumbersWithoutUsingTempVar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int x = 10;
        int y = 20;
       
        System.out.println("Before Swapping");
        System.out.println("Value of x is :" + x);
        System.out.println("Value of y is :" +y);
       
        //swap the value
        swap(x, y);

	}
	
	private static void swap(int x, int y) {
	       
		x=x+y;
		y=x-y;
		x=x-y;
       
        System.out.println("After Swapping");
        System.out.println("Value of x is :" + x);
        System.out.println("Value of y is :" +y);
       

		}


}
