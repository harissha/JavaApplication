package javaTesting.Numbers;


public class FindMaximumSubarrayFromContiguousArray {
	
	/*Find the contiguous subarray within an array (containing at least one number) which has the largest sum.
	  For example, given the array [-2,1,-3,4,-1,2,1,-5,4], the contiguous subarray [4,-1,2,1] has the largest sum = 6.
    */
	
	public int maxSubArray(int[] A) {
		int max = A[0];
		int[] sum = new int[A.length];
		sum[0] = A[0];
 
		for (int i = 1; i < A.length; i++) {
			sum[i] = Math.max(A[i], sum[i - 1] + A[i]);
			max = Math.max(max, sum[i]);
		}
 
		return max;
	}
	
	/*public int maxSubArray(int[] A) {
	       int newsum=A[0];
	       int max=A[0];
	       for(int i=1;i<A.length;i++){
	           newsum=Math.max(newsum+A[i],A[i]);
	           max= Math.max(max, newsum);
	       }
	       return max;
	    }*/

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		FindMaximumSubarrayFromContiguousArray a=new FindMaximumSubarrayFromContiguousArray();
		
		int[] k={-2,1,-3,4,-1,2,1,-5,4};
		
		System.out.println(a.maxSubArray(k));
		
		
			
		
	}

}
