package javaTesting.Numbers;

public class FindLargestSmallestNumberInArray {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//numbers array
        int numbers[] = new int[]{55,32,45,98,82,11,9,39,50};
       
        //assign first element of an array to largest and smallest
        int smallest = numbers[0];
        int largest = numbers[0];
       
        for(int i=1; i< numbers.length; i++) 
        { 
        	if(numbers[i] > largest)
                  largest = numbers[i];
            else if (numbers[i] < smallest)
                  smallest = numbers[i];
               
        }
       
        System.out.println("Largest Number is : " + largest);
        System.out.println("Smallest Number is : " + smallest);

	}

}
